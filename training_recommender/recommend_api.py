from flask import Flask, request, jsonify
import pickle
import numpy as np
from sklearn.metrics.pairwise import cosine_similarity
import mysql.connector
import pandas as pd

app = Flask(__name__)

# Charger le modèle
with open('model.pkl', 'rb') as f:
    model = pickle.load(f)

tfidf = model['tfidf']
trainings = model['trainings']
tfidf_matrix = model['tfidf_matrix']

# Connexion MySQL
def db_connection():
    return mysql.connector.connect(
        host='localhost',
        port=3306,
        user='root',
        password='',
        database='pi'
    )

# Récupérer les descriptions des formations bien notées par un étudiant
def get_user_preferred_descriptions(user_id):
    conn = db_connection()
    query = """
        SELECT t.description 
        FROM evaluation e
        JOIN training t ON e.id_formation = t.id_form
        WHERE e.id_etudiant = %s AND e.nombre_etoiles >= 3
    """
    df = pd.read_sql(query, conn, params=(user_id,))
    conn.close()
    return df['description'].dropna().tolist()

# Vérifier si la base est vide
def database_is_empty():
    conn = db_connection()
    cursor = conn.cursor()
    cursor.execute("SELECT COUNT(*) FROM student")
    students_count = cursor.fetchone()[0]
    cursor.execute("SELECT COUNT(*) FROM training")
    trainings_count = cursor.fetchone()[0]
    conn.close()
    return students_count == 0 or trainings_count == 0

@app.route('/recommend/<int:user_id>', methods=['GET'])
def recommend(user_id):
    if database_is_empty():
        return jsonify({"error": "Base de données vide."}), 400

    preferred_descriptions = get_user_preferred_descriptions(user_id)

    if not preferred_descriptions:
        # Fallback si aucune évaluation positive : suggestions aléatoires
        fallback_trainings = trainings.sample(n=5)
        recommendations = []
        for _, row in fallback_trainings.iterrows():
            recommendations.append({
                "id_form": int(row["id_form"]),
                "name": row["name"],
                "description": row["description"],
                "score": None
            })
        return jsonify({
            "message": "Aucune évaluation trouvée. Voici des formations proposées par défaut.",
            "recommendations": recommendations
        })

    # Générer le vecteur moyen et le convertir en array compatible
    user_vector_matrix = tfidf.transform(preferred_descriptions).mean(axis=0)
    user_vector = np.asarray(user_vector_matrix)

    # Calcul de la similarité cosinus
    similarities = cosine_similarity(user_vector, tfidf_matrix)[0]
    top_indices = similarities.argsort()[::-1][:5]

    # Créer la liste des recommandations
    recommendations = []
    for idx in top_indices:
        training = trainings.iloc[idx]
        recommendations.append({
            "id_form": int(training["id_form"]),
            "name": training["name"],
            "description": training["description"],
            "score": round(float(similarities[idx]), 4)
        })

    return jsonify(recommendations)

if __name__ == '__main__':
    app.run(debug=True, port=5002)
