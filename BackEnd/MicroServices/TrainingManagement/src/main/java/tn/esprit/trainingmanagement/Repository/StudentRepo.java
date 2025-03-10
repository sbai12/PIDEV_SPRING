package tn.esprit.trainingmanagement.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.trainingmanagement.Entity.Student;
import tn.esprit.trainingmanagement.Entity.Training;

import java.util.Optional;

@Repository
public interface StudentRepo  extends JpaRepository<Student, Long> {
    Optional<Student> findByFirstNameAndLastName(String firstName, String lastName);
}
