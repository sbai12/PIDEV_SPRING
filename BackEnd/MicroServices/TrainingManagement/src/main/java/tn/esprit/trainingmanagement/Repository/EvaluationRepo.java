package tn.esprit.trainingmanagement.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.trainingmanagement.Entity.Evaluation;

import java.util.List;

@Repository
public interface EvaluationRepo extends JpaRepository<Evaluation, Long> {
    // Méthode pour récupérer toutes les évaluations d'une formation
    List<Evaluation> findByTrainingId(Long trainingId);

    // Méthode pour vérifier si un étudiant a déjà évalué cette formation
    Evaluation findByStudentIdAndTrainingId(Long studentId, Long trainingId);

}
