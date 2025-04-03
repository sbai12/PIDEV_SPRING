package tn.esprit.trainingmanagement.Entity.FctsAv;

public class EvaluationRequest {public Long idEtudiant;
    public Long idFormation;
    public int nombreEtoiles;
    public String commentaire;

    // Constructeur sans argument
    public EvaluationRequest() {}

    // Constructeur avec tous les paramètres
    public EvaluationRequest(Long idEtudiant, Long idFormation, int nombreEtoiles, String commentaire) {
        this.idEtudiant = idEtudiant;
        this.idFormation = idFormation;
        this.nombreEtoiles = nombreEtoiles;
        this.commentaire = commentaire;
    }

    // Getters et Setters

    public Long getIdEtudiant() {
        return idEtudiant;
    }

    public void setIdEtudiant(Long idEtudiant) {
        this.idEtudiant = idEtudiant;
    }

    public Long getIdFormation() {
        return idFormation;
    }

    public void setIdFormation(Long idFormation) {
        this.idFormation = idFormation;
    }

    public int getNombreEtoiles() {
        return nombreEtoiles;
    }

    public void setNombreEtoiles(int nombreEtoiles) {
        this.nombreEtoiles = nombreEtoiles;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }
}