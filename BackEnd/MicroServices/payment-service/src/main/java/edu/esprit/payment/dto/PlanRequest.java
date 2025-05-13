package edu.esprit.payment.dto;

import edu.esprit.payment.dto.paypal.BillingCycle;
import edu.esprit.payment.dto.paypal.PaymentPreferences;
import edu.esprit.payment.enums.PlanType;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class PlanRequest {
    @NotNull
    private String name;
    private String description;
    @NotNull
    private Integer amount;
    @NotNull
    private String currency;
    @NotNull
    private PlanType type;
    @NotNull
    private String product_id;
    private List<BillingCycle> billing_cycles;
    private PaymentPreferences payment_preferences;

}