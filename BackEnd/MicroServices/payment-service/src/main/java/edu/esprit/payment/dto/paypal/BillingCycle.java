package edu.esprit.payment.dto.paypal;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class BillingCycle {
    private Frequency frequency;
    private String tenure_type; // "REGULAR"
    private int sequence;
    private int total_cycles;
    private PricingScheme pricing_scheme;
}
