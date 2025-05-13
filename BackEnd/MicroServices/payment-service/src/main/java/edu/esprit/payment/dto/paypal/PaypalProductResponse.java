package edu.esprit.payment.dto.paypal;

import lombok.Data;

@Data
public class PaypalProductResponse {
    private String id;
    private String name;
    private String description;
    private String type;
    private String category;
}
