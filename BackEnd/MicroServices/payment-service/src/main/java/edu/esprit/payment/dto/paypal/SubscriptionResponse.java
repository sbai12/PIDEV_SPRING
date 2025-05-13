package edu.esprit.payment.dto.paypal;

import lombok.Data;

@Data
public class SubscriptionResponse {
    private String id;
    private String status;
    private String create_time;
    private String update_time;
}
