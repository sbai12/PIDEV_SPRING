package tn.esprit.trainingmanagement.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.trainingmanagement.Entity.User;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
}
