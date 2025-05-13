package edu.esprit.payment.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@RequiredArgsConstructor
public class SubscriptionRequest {
    @NotNull
    private String userId;
    @NotNull
    private String planId;
    private Integer trialPeriodDays;
}

