package edu.esprit.payment.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient(name = "paypal-auth", url = "${paypal.api-base}")
public interface PayPalAuthClient {

    @PostMapping(value = "/v1/oauth2/token", consumes = "application/x-www-form-urlencoded")
    Map<String, Object> getAccessToken(
            @RequestHeader("Authorization") String basicAuth,
            @RequestBody String grantType
    );
}
