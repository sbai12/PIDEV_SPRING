package tn.esprit.trainingmanagement.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.trainingmanagement.Entity.Student;
import tn.esprit.trainingmanagement.Entity.Training;
import tn.esprit.trainingmanagement.Entity.TrainingEnrollment;

import java.util.Optional;

public interface TrainingEnrollmentRepo extends JpaRepository<TrainingEnrollment, Long> {
    Optional<TrainingEnrollment> findByStudentAndTraining(Student student, Training training);

}
