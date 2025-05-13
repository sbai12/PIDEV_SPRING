package edu.esprit.payment.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InvoiceRequest {
    private Integer amount;
    private String currency;
    private BillingAddress billingAddress;


}
