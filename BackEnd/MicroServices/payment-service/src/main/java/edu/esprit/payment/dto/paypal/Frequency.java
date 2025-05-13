package edu.esprit.payment.dto.paypal;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Frequency {
    private String interval_unit; // "MONTH"
    private int interval_count;
}
