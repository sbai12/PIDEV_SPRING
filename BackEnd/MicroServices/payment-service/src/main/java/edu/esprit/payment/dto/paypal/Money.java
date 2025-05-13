package edu.esprit.payment.dto.paypal;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Money {
    private String currency_code; // "EUR"
    private String value;
}
