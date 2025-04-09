package tn.esprit.trainingmanagement.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tn.esprit.trainingmanagement.Entity.Training;

import java.util.List;

@Repository
    public interface TrainingRepo extends JpaRepository<Training, Long> {
    boolean existsByName(String name);
    @Query("SELECT t FROM Training t JOIN t.enrollments e JOIN e.student s WHERE s.email = :email")
    List<Training> findTrainingsEnrolledByStudentEmail(@Param("email") String email);
}



