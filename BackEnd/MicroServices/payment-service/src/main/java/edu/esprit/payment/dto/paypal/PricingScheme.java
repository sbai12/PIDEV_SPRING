package edu.esprit.payment.dto.paypal;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PricingScheme {
    private Money fixed_price;
}