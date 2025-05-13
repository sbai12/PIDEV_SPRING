package edu.esprit.payment.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public  class BillingAddress {
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String postalCode;
    private String country;
}
