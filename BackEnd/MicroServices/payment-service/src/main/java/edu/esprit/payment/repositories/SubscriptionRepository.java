package edu.esprit.payment.repositories;

import edu.esprit.payment.entities.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface SubscriptionRepository extends JpaRepository<Subscription, String> {
    List<Subscription> findByStatus(String status);


    List<Subscription> findByUserIdAndStatusIn(String userId, Collection<String> statuses);
}

