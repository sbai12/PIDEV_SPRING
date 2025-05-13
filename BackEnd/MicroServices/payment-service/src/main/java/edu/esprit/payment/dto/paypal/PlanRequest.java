package edu.esprit.payment.dto.paypal;


import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PlanRequest {
    private String product_id;
    private String name;
    private String description;
    private String status;
    private List<BillingCycle> billing_cycles;
    private PaymentPreferences payment_preferences;
    private Taxes taxes;
}
