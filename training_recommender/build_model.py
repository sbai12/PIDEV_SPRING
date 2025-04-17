import mysql.connector
import pandas as pd
from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.metrics.pairwise import cosine_similarity
import pickle

# Connexion MySQL
conn = mysql.connector.connect(
    host='localhost',
    port=3306,
    user='root',
    password='',
    database='pi'
)

french_stop_words = [
    'et', 'à', 'le', 'la', 'les', 'un', 'une', 'des', 'de', 'du', 'en', 'dans', 'ce', 'cet', 'cette', 'il', 'elle', 'ils', 'elles',
    'nous', 'vous', 'je', 'tu', 'me', 'te', 'se', 'ne', 'pas', 'mais', 'ou', 'donc', 'or', 'ni', 'car', 'que', 'qui', 'quoi', 'dont',
    'où', 'au', 'aux', 'avec', 'pour', 'sur', 'par', 'comme', 'plus', 'moins', 'être', 'avoir', 'faire'
]


# Récupérer les formations
trainings = pd.read_sql("SELECT id_form, name, description FROM training", conn)

# Nettoyage
trainings['description'] = trainings['description'].fillna('')

# TF-IDF sur les descriptions
tfidf = TfidfVectorizer(stop_words=french_stop_words)
tfidf_matrix = tfidf.fit_transform(trainings['description'])

# Sauvegarde du modèle
with open('model.pkl', 'wb') as f:
    pickle.dump({
        'tfidf': tfidf,
        'trainings': trainings,
        'tfidf_matrix': tfidf_matrix
    }, f)

print("Modèle sauvegardé dans model.pkl")
