package tn.esprit.trainingmanagement.Services;

public interface IEvaluationService {
     void ajouterEvaluation(Long studentId, Long trainingId, int nombreEtoiles, String commentaire) ;
    double calculerMoyenneEtoiles(Long trainingId);

}
