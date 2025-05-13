package edu.esprit.payment.dto.paypal;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Subscriber {
    private Name name;
    private String email_address;
}
