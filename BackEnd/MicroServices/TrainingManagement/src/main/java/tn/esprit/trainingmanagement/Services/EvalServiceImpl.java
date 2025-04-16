package tn.esprit.trainingmanagement.Services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.trainingmanagement.Entity.EnrollmentStatus;
import tn.esprit.trainingmanagement.Entity.FctsAv.Evaluation;
import tn.esprit.trainingmanagement.Entity.Student;
import tn.esprit.trainingmanagement.Entity.Training;
import tn.esprit.trainingmanagement.Entity.TrainingEnrollment;
import tn.esprit.trainingmanagement.Repository.EvaluRep;
import tn.esprit.trainingmanagement.Repository.StudentRepo;
import tn.esprit.trainingmanagement.Repository.TrainingEnrollmentRepo;
import tn.esprit.trainingmanagement.Repository.TrainingRepo;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class EvalServiceImpl implements IEvalService {

    public final EvaluRep evaluationRepository;
    public final StudentRepo studentRepository;
    public final TrainingRepo trainingRepository;
    public final TrainingEnrollmentRepo trainingEnrollmentRepository;

    // Liste simulée des étudiants ayant payé (à remplacer par une vraie logique de paiement plus tard)
    public final List<Long> etudiantsAyantPaye = List.of(1L, 2L, 3L); // TEMPORAIRE

    @Override
    public Evaluation ajouterEvaluation(Long idEtudiant, Long idFormation, int nombreEtoiles, String commentaire) {
        // Désactiver la vérification de paiement pour tester
        //if (!etudiantsAyantPaye.contains(idEtudiant)) {
        //    throw new RuntimeException("L’étudiant n’a pas encore payé cette formation.");
        //}

        // Récupérer l'étudiant et la formation
        Student student = studentRepository.findById(idEtudiant)
                .orElseThrow(() -> new RuntimeException("Étudiant non trouvé"));
        Training training = trainingRepository.findById(idFormation)
                .orElseThrow(() -> new RuntimeException("Formation non trouvée"));

        // Vérifier si l'étudiant est inscrit à la formation et a le statut CONFIRMED
        TrainingEnrollment enrollment = trainingEnrollmentRepository.findByStudentAndTraining(student, training)
                .orElseThrow(() -> new RuntimeException("L'étudiant n'est pas inscrit à cette formation."));

        if (enrollment.status != EnrollmentStatus.CONFIRMED) {
            throw new RuntimeException("L'étudiant n'est pas encore inscrit ou est dans un statut incorrect pour évaluer cette formation.");
        }

        // Créer l'objet Evaluation
        Evaluation evaluation = new Evaluation();
        evaluation.student = student;
        evaluation.training = training;
        evaluation.nombreEtoiles = nombreEtoiles;
        evaluation.commentaire = commentaire;
        evaluation.dateEvaluation = LocalDateTime.now();

        // Sauvegarder l'évaluation dans la base de données
        return evaluationRepository.save(evaluation);
    }

    @Override
    public double calculerMoyenneEtoiles(Long idFormation) {
        // Récupérer la formation
        Training training = trainingRepository.findById(idFormation)
                .orElseThrow(() -> new RuntimeException("Formation non trouvée"));

        // Récupérer toutes les évaluations pour cette formation
        List<Evaluation> evaluations = evaluationRepository.findByTraining(training);

        // Si aucune évaluation, retourner 0
        if (evaluations.isEmpty()) return 0.0;

        // Calculer la somme des étoiles
        double total = evaluations.stream().mapToInt(Evaluation::getNombreEtoiles).sum();

        // Calculer la moyenne
        return total / evaluations.size();
    }

    @Override
    public List<Evaluation> getEvaluationsByFormation(Long idFormation) {
        // Récupérer la formation
        Training training = trainingRepository.findById(idFormation)
                .orElseThrow(() -> new RuntimeException("Formation non trouvée"));

        // Récupérer toutes les évaluations pour cette formation
        return evaluationRepository.findByTraining(training);
    }
}
