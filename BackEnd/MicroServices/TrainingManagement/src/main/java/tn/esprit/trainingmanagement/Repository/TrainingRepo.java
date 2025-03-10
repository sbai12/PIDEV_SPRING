package tn.esprit.trainingmanagement.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.trainingmanagement.Entity.Training;

@Repository
    public interface TrainingRepo extends JpaRepository<Training, Long> {
    boolean existsByName(String name);

}
