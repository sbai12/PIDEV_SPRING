package edu.esprit.payment.clients;


import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@FeignClient(name = "paypal-order", url = "${paypal.api-base}")
public interface PayPalOrderClient {

    @PostMapping(value = "/v2/checkout/orders", consumes = "application/json")
    Map<String, Object> createOrder(
            @RequestHeader("Authorization") String bearerToken,
            @RequestBody Map<String, Object> request
    );

    @PostMapping(value = "/v2/checkout/orders/{orderId}/capture", consumes = "application/json")
    @Headers("Accept: application/json")
    Map<String, Object> captureOrder(
            @PathVariable("orderId") String orderId,
            @RequestHeader("Authorization") String authorizationHeader,
            @RequestBody Map<String, Object> body
    );

}

