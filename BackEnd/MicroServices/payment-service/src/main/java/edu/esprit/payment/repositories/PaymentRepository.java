package edu.esprit.payment.repositories;

import edu.esprit.payment.entities.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, String> {
    Optional<Payment> findByTransactionId(String transactionId);
    List<Payment> findByUserIdAndStatusIn(String userId, List<String> status);
}
