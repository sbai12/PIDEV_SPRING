package tn.esprit.trainingmanagement.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.trainingmanagement.Entity.FctsAv.Evaluation;
import tn.esprit.trainingmanagement.Entity.Training;

import java.util.List;

public interface EvaluRep extends JpaRepository<Evaluation, Long> {
    List<Evaluation> findByTraining(Training training);

}
