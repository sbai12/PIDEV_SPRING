package edu.esprit.payment.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import edu.esprit.payment.clients.TrainingServiceClient;
import edu.esprit.payment.dto.PaymentRequest;
import edu.esprit.payment.entities.*;
import edu.esprit.payment.enums.InvoiceStatus;
import edu.esprit.payment.enums.PaymentMethod;
import edu.esprit.payment.enums.PaymentStatus;
import edu.esprit.payment.exeception.BadRequestException;
import edu.esprit.payment.exeception.ResourceNotFoundException;
import edu.esprit.payment.mapper.PaymentMapper;
import edu.esprit.payment.repositories.PaymentRepository;
import edu.esprit.payment.repositories.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentService {

    private final PaymentMapper paymentMapper;
    private final PaymentRepository paymentRepository;
    private final PaypalService paypalService;
    private final InvoiceService invoiceService;


    public Payment create(PaymentRequest request) throws JsonProcessingException {
        Payment payment = paymentMapper.toPayment(request);
        payment.setId("pay_" + UUID.randomUUID());
        payment.setPaymentDate(LocalDateTime.now());
        if ("paypal".equalsIgnoreCase(request.getMethod())) {
            Map<String, Object> response = paypalService.createPayment(
                    request.getAmount(),
                    request.getCurrency(),
                    request.getDescription()
            );

            // Log or store PayPal order ID or status
            payment.setTransactionId((String) response.get("id"));
            payment.setApprovalUrl(extractApprovalUrl(response));
            payment.setStatus(PaymentStatus.PENDING);
        } else {
            payment.setTransactionId("manual_" + UUID.randomUUID());
            var invoice = Invoice
                    .builder()
                    .id("invoice_" + UUID.randomUUID())
                    .transactionId(payment.getTransactionId())
                    .amount(request.getAmount())
                    .currency(request.getCurrency())
                    .status(InvoiceStatus.PAID)
                    .issueDate(LocalDateTime.now())
                    .dueDate(LocalDateTime.now().plusDays(7))
                    .billingAddress(Invoice.BillingAddress
                            .builder()
                            .addressLine1(request.getBillingAddress().getAddressLine1())
                            .addressLine2(request.getBillingAddress().getAddressLine2())
                            .city(request.getBillingAddress().getCity())
                            .postalCode(request.getBillingAddress().getPostalCode())
                            .country(request.getBillingAddress().getCountry())
                            .build())
                    .build();
            payment.setInvoiceId(invoice.getId());
            invoiceService.createInvoice(invoice);
            payment.setStatus(PaymentStatus.COMPLETED);
        }

        return paymentRepository.save(payment);
    }

    public void updatePaymentStatus(String transactionId, PaymentStatus newStatus) {
        paymentRepository.findByTransactionId(transactionId).ifPresent(payment -> {
            payment.setStatus(newStatus);
            paymentRepository.save(payment);
            log.info(" Statut mis à jour pour {} : {}", transactionId, newStatus);
        });
    }

    public Payment getPaymentById(String id) {
        return paymentRepository.findById(id).orElse(null);
    }





    public Payment refundPayment(String paymentId) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found"));

        if (payment.getStatus().equals(PaymentStatus.REFUNDED)) {
            throw new IllegalStateException("Payment already refunded");
        }
        if (payment.getTransactionId() != null && payment.getMethod().equals(PaymentMethod.PAYPAL)) {
            var captureResponse = paypalService.captureOrder(payment.getTransactionId());
            log.info("capture order {}", captureResponse);
        }

        payment.setStatus(PaymentStatus.REFUNDED);
        return paymentRepository.save(payment);
    }

    public String approvePayment(String paymentId) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found"));

        if (payment.getStatus().equals(PaymentStatus.APPROVED)) {
            throw new IllegalStateException("Payment already approved");
        }
        if (payment.getApprovalUrl() == null || payment.getApprovalUrl().isEmpty()) {
            throw new BadRequestException("we have no approval url");
        }
        return payment.getApprovalUrl();
    }

    public static String extractApprovalUrl(Map<String, Object> response) {
        List<Map<String, Object>> links = (List<Map<String, Object>>) response.get("links");

        for (Map<String, Object> link : links) {
            String rel = (String) link.get("rel");
            if ("approve".equals(rel)) {
                return (String) link.get("href");
            }
        }

        return null;
    }
}
