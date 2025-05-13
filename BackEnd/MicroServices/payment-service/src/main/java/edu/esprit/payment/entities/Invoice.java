package edu.esprit.payment.entities;

import edu.esprit.payment.enums.InvoiceStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Invoice {

    @Id
    private String id;

    private String transactionId;
    private Integer amount;
    private String currency;
    private LocalDateTime issueDate;
    private LocalDateTime dueDate;

    @Embedded
    private BillingAddress billingAddress;

    private InvoiceStatus status; // unpaid, paid, cancelled

    @Embeddable
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class BillingAddress {
        private String addressLine1;
        private String addressLine2;
        private String city;
        private String postalCode;
        private String country;
    }
    @Lob
    @Column(name = "pdf", columnDefinition = "BLOB")
    private  byte[] pdf;
}

