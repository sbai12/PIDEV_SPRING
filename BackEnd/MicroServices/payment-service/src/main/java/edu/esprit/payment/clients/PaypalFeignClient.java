package edu.esprit.payment.clients;

import edu.esprit.payment.dto.paypal.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@FeignClient(name = "paypalClient", url = "https://api-m.sandbox.paypal.com")
public interface PaypalFeignClient {

    @PostMapping("/v1/notifications/verify-webhook-signature")
    Map<String, Object> verifyWebhookSignature(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody Map<String, Object> verificationPayload
    );

    @PostMapping("/v1/billing/plans")
    PlanResponse createPlan(@RequestHeader("Authorization") String token,
                            @RequestBody PlanRequest planRequest);

    @PatchMapping("/v1/billing/plans/{plan_id}")
    void activatePlan(@RequestHeader("Authorization") String token,
                      @PathVariable("plan_id") String planId,
                      @RequestBody List<Map<String, String>> patchRequest);

    @PostMapping("/v1/billing/subscriptions")
    Map<String, Object> createSubscription(@RequestHeader("Authorization") String token,
                                           @RequestBody PSubscriptionRequest request);

    @PostMapping("/v1/catalogs/products")
    ResponseEntity<PaypalProductResponse> createProduct(
            @RequestBody PaypalProductRequest request,
            @RequestHeader("Authorization") String accessToken
    );
}
