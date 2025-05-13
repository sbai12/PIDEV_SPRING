package edu.esprit.payment.repositories;

import edu.esprit.payment.entities.Plan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlanRepository extends JpaRepository<Plan, String> {
}
