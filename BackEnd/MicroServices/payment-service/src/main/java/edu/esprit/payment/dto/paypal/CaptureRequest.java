package edu.esprit.payment.dto.paypal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class CaptureRequest {
    private String amount;
    private String currencyCode;
}
