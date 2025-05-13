package edu.esprit.payment.dto.paypal;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PSubscriptionRequest {
    private String plan_id;
    private ApplicationContext application_context;
    private Subscriber subscriber;
}
