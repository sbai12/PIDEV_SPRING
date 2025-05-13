package edu.esprit.payment.entities;

import edu.esprit.payment.enums.PaymentMethod;
import edu.esprit.payment.enums.PaymentStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class Payment {
    @Id
    private String id;
    private String userId;
    private Integer amount;
    private String currency;
    private PaymentMethod method;
    private PaymentStatus status;
    private String transactionId;
    private String approvalUrl;
    private LocalDateTime paymentDate;
    private String description;
    private String invoiceId;
    private String planId;
}

