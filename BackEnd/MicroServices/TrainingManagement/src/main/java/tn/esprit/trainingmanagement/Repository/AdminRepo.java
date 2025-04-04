package tn.esprit.trainingmanagement.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.trainingmanagement.Entity.Admin;

public interface AdminRepo extends JpaRepository<Admin, Long> {
}
