package edu.esprit.payment.dto.paypal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Taxes {
    private boolean percentage;
    private String amount; // not usually required unless you're collecting taxes
}
