package edu.esprit.payment.dto.paypal;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApplicationContext {
    private String brand_name;
    private String locale;
    private String shipping_preference;
    private String user_action;
    private String return_url;
    private String cancel_url;
}
