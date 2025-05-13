package edu.esprit.payment.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
public class Subscription {
    @Id
    private String id;
    private String userId;
    private String planId;
    private String invoiceId;
    private String transactionId;
    private String status;
    private LocalDateTime trialEndDate;
    private String paypalSubscriptionId;
    private String approvalUrl;
}
