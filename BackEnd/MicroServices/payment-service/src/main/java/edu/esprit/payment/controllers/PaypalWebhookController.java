package edu.esprit.payment.controllers;

import edu.esprit.payment.enums.PaymentStatus;
import edu.esprit.payment.services.PaymentService;
import edu.esprit.payment.services.PaypalWebhookService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
@Tag(name = "PayPal Webhook")
public class PaypalWebhookController {

    private final PaypalWebhookService paypalWebhookService;
    private final PaymentService paymentService;

    @PostMapping("/v1/notifications/verify-webhook-signature")
    public ResponseEntity<Void> handlePaypalWebhook(
            @RequestBody Map<String, Object> payload,
            HttpServletRequest request) {

        log.info(" Webhook PayPal reçu : {}", payload);

        try {
            boolean isValid = paypalWebhookService.verifyWebhookSignature(request, payload);
            if (!isValid) {
                log.warn(" Signature Webhook PayPal invalide !");
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }

            String eventType = (String) payload.get("event_type");
            Map<String, Object> resource = (Map<String, Object>) payload.get("resource");
            String transactionId = (String) resource.get("id");

            switch (eventType) {
                case "PAYMENT.CAPTURE.COMPLETED":
                    paymentService.updatePaymentStatus(transactionId, PaymentStatus.COMPLETED);
                    break;
                case "PAYMENT.CAPTURE.DENIED":
                    paymentService.updatePaymentStatus(transactionId, PaymentStatus.REFUSED);
                    break;
                case "PAYMENT.CAPTURE.FAILED":
                    paymentService.updatePaymentStatus(transactionId, PaymentStatus.FAILED);
                    break;
                case "PAYMENT.CAPTURE.REFUNDED":
                    paymentService.updatePaymentStatus(transactionId, PaymentStatus.REFUNDED);
                    break;
                default:
                    log.warn(" Type d’événement PayPal non géré : {}", eventType);
            }

            return ResponseEntity.ok().build();

        } catch (Exception e) {
            log.error(" Erreur lors du traitement du webhook PayPal", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}