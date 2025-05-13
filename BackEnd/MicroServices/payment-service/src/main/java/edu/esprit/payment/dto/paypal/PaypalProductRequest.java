package edu.esprit.payment.dto.paypal;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaypalProductRequest {
    private String name;
    private String description;
    private String type;
    private String category;
    private String image_url;
}
