package edu.esprit.payment.services;

import edu.esprit.payment.clients.DeepSeekFeignClient;
import edu.esprit.payment.entities.Conversation;
import edu.esprit.payment.entities.Message;
import edu.esprit.payment.entities.Payment;
import edu.esprit.payment.exeception.BadRequestException;
import edu.esprit.payment.exeception.NotFoundException;
import edu.esprit.payment.repositories.ConversationRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class PaymentAssistanceChatbot {

    private static final Logger logger = LoggerFactory.getLogger(PaymentAssistanceChatbot.class);
    private static final String ERROR_MESSAGE = "Désolé, je rencontre des difficultés techniques. Veuillez réessayer plus tard.";

    private final PaymentService paymentService;
    private final ConversationRepository conversationRepository;
    private final DeepSeekFeignClient ollamaFeignClient;

    @Value("${ollama.api.model:deepseek/deepseek-llm}")
    private String ollamaModel;


    public String handleUserMessage(String userId, String userMessage, String paymentId) {
        try {
            logger.info("Handling message for user {} and payment {}", userId, paymentId);

            Conversation conversation = getOrCreateConversation(userId, paymentId);
            conversation.addMessage("user", userMessage);

            String context = createChatContext(conversation);

            // Call local Ollama API via Feign Client
            String aiResponse = callOllamaAPI(context);

            conversation.addMessage("assistant", aiResponse);
            conversationRepository.save(conversation);

            return aiResponse;
        } catch (Exception ex) {
            logger.error("Error processing message", ex);
            return ERROR_MESSAGE;
        }
    }

    private String callOllamaAPI(String prompt) {
        try {
            Map<String, Object> request = new HashMap<>();
            request.put("model", ollamaModel);
            request.put("prompt", prompt);
            request.put("stream", false);

            Map<String, Object> response = ollamaFeignClient.generateCompletion(request);

            return response.get("response").toString().trim();
        } catch (Exception ex) {
            logger.error("Error calling Ollama API", ex);
            throw new BadRequestException("Failed to get response from local AI");
        }
    }

    private Conversation getOrCreateConversation(String userId, String paymentId) {
        try {
            return conversationRepository.findByUserIdAndPaymentId(userId, paymentId)
                    .orElseGet(() -> {
                        logger.debug("Creating new conversation for user {} and payment {}", userId, paymentId);
                        Conversation newConversation = new Conversation();
                        newConversation.setUserId(userId);
                        newConversation.setPaymentId(paymentId);

                        String systemPrompt = createSystemPrompt(paymentId);
                        newConversation.addMessage("system", systemPrompt);
                        logger.debug("System prompt added to new conversation");

                        return conversationRepository.save(newConversation);
                    });
        } catch (Exception ex) {
            logger.error("Error in getOrCreateConversation", ex);
            throw ex;
        }
    }

    private String createSystemPrompt(String paymentId) {

            Payment payment = paymentService.getPaymentById(paymentId);
            if (payment == null) throw new NotFoundException("Payment not found please check the information provided");
            logger.debug("Retrieved payment details for payment {}", paymentId);

            return String.format("""
                            Vous êtes un assistant virtuel spécialisé dans l'aide aux paiements pour notre plateforme.
                            L'utilisateur a eu un problème avec le paiement suivant:
                            - Méthode: %s
                            - Montant: %s
                            - Statut: %s
                            - Description: %s
                            
                            Soyez courtois, professionnel et proposez des solutions concrètes.
                            Répondez en français et gardez vos réponses concises mais utiles.
                            """,
                    payment.getMethod().name(),
                    payment.getAmount().toString(),
                    payment.getStatus().name(),
                    payment.getDescription());
    }

    private String createChatContext(Conversation conversation) {
        try {
            StringBuilder context = new StringBuilder();

            for (Message message : conversation.getMessages()) {
                context.append(message.getRole())
                        .append(": ")
                        .append(message.getContent())
                        .append("\n");
            }

            return context.toString();
        } catch (Exception ex) {
            logger.error("Error in createChatContext for conversation {}", conversation.getId(), ex);
            throw ex;
        }
    }
}