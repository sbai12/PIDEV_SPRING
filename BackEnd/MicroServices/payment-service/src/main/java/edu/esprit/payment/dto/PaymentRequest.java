package edu.esprit.payment.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class PaymentRequest {
    @NotNull
    private String userId;
    @NotNull
    private Integer amount;
    @NotNull
    private String currency;
    @NotNull
    private String method;
    private String description;
    @NotNull
    private String planId;
    private BillingAddress billingAddress;
}

