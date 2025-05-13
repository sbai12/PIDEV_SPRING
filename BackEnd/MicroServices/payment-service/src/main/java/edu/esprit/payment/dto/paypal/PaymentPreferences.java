package edu.esprit.payment.dto.paypal;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaymentPreferences {
    private Boolean auto_bill_outstanding;
    private Money setup_fee;
    private String setup_fee_failure_action;
    private Integer payment_failure_threshold;
}
