package edu.esprit.payment.clients;

import edu.esprit.payment.dto.paypal.PSubscriptionRequest;
import edu.esprit.payment.dto.paypal.PlanRequest;
import edu.esprit.payment.dto.paypal.PlanResponse;
import edu.esprit.payment.dto.paypal.SubscriptionResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "paypal-subscription", url = "${paypal.api-base}")
public interface PayPalSubscriptionClient {
    @PostMapping(value = "/v1/billing/plans", consumes = MediaType.APPLICATION_JSON_VALUE)
    PlanResponse createPlan(@RequestHeader("Authorization") String bearerToken, @RequestBody PlanRequest planRequest);

    @PostMapping(value = "/v1/billing/subscriptions", consumes = MediaType.APPLICATION_JSON_VALUE)
    SubscriptionResponse createSubscription(@RequestHeader("Authorization") String bearerToken, @RequestBody PSubscriptionRequest subscriptionRequest);
}
