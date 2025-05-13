package edu.esprit.payment.repositories;

import edu.esprit.payment.entities.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ConversationRepository extends JpaRepository<Conversation, Long> {
    Optional<Conversation> findByUserIdAndPaymentId(String userId, String paymentId);
}
