package tn.esprit.trainingmanagement.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.trainingmanagement.Entity.Evaluation;
import tn.esprit.trainingmanagement.Entity.Student;
import tn.esprit.trainingmanagement.Entity.Training;
import tn.esprit.trainingmanagement.Repository.EvaluationRepo;
import tn.esprit.trainingmanagement.Repository.StudentRepo;
import tn.esprit.trainingmanagement.Repository.TrainingRepo;

import java.util.List;

@Service

public class EvaluationServiceImpl implements IEvaluationService {

    @Autowired
    EvaluationRepo evaluationRepo;

    @Autowired
    StudentRepo studentRepo;

    @Autowired
    TrainingRepo trainingRepo;

    @Override
    public void ajouterEvaluation(Long studentId, Long trainingId, int nombreEtoiles, String commentaire) {
        // Vérifier si l'étudiant existe
        Student student = studentRepo.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Étudiant non trouvé"));

        // Vérifier si la formation existe
        Training training = trainingRepo.findById(trainingId)
                .orElseThrow(() -> new RuntimeException("Formation non trouvée"));

        // Vérifier si l'étudiant a déjà évalué cette formation
        Evaluation existingEvaluation = evaluationRepo.findByStudentIdAndTrainingId(studentId, trainingId);
        if (existingEvaluation != null) {
            throw new RuntimeException("Vous avez déjà évalué cette formation.");
        }

        // Créer et enregistrer la nouvelle évaluation
        Evaluation evaluation = new Evaluation();
        evaluation.setStudent(student);
        evaluation.setTraining(training);
        evaluation.setNombreEtoiles(nombreEtoiles);
        evaluation.setCommentaire(commentaire);

        evaluationRepo.save(evaluation); // Sauvegarder l'évaluation dans la base de données
    }


    @Override
    public double calculerMoyenneEtoiles(Long trainingId) {
        // Récupérer toutes les évaluations pour la formation spécifiée
        List<Evaluation> evaluations = evaluationRepo.findByTrainingId(trainingId);

        // Calculer la somme des étoiles
        double totalEtoiles = 0;
        for (Evaluation evaluation : evaluations) {
            totalEtoiles += evaluation.getNombreEtoiles();
        }

        // Calculer la moyenne des étoiles
        return evaluations.isEmpty() ? 0 : totalEtoiles / evaluations.size();
    }
}
