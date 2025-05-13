package edu.esprit.payment.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.esprit.payment.clients.PayPalAuthClient;
import edu.esprit.payment.clients.PayPalOrderClient;
import edu.esprit.payment.clients.PayPalSubscriptionClient;
import edu.esprit.payment.clients.PaypalFeignClient;
import edu.esprit.payment.config.PayPalConfig;
import edu.esprit.payment.dto.paypal.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.Collections;
import java.util.Locale;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaypalService {

    public static final String BEARER = "Bearer ";
    private final PayPalConfig config;
    private final PayPalAuthClient authClient;
    private final PayPalOrderClient orderClient;
    private final PayPalSubscriptionClient subscriptionClient;
    private final PaypalFeignClient paypalFeignClient;

    public String getAccessToken() {
        String credentials = config.getClientId() + ":" + config.getSecret();
        String encoded = Base64.getEncoder().encodeToString(credentials.getBytes());
        String basicAuth = "Basic " + encoded;

        Map<String, Object> response = authClient.getAccessToken(basicAuth, "grant_type=client_credentials");
        return (String) response.get("access_token");
    }

    public Map<String, Object> createPayment(int amount, String currency, String description) throws JsonProcessingException {
        String accessToken = getAccessToken();
        String bearerToken = BEARER + accessToken;

        Map<String, Object> body = Map.of(
                "intent", "CAPTURE",
                "purchase_units", new Object[]{
                        Map.of(
                                "amount", Map.of(
                                        "currency_code", currency,
                                        "value", String.format(Locale.US,"%.2f", amount / 100.0)
                                ),
                                "description", description
                        )
                }
        );
        log.info("PAYPAL PAYMENT REQUEST : {}",new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(body));
        return orderClient.createOrder(bearerToken, body);
    }

    public PaypalProductResponse createProduct(PaypalProductRequest request) {
        String accessToken = getAccessToken();
        String bearer = "Bearer " + accessToken;
        return paypalFeignClient.createProduct(request, bearer).getBody();
    }

    public Map<String, Object> captureOrder(String orderId) {
        String authToken = getAccessToken();
        return orderClient.captureOrder(orderId, BEARER + authToken, Collections.emptyMap());

    }
    public PlanResponse createPlan(PlanRequest planRequest) {
        String token = getAccessToken();
        return subscriptionClient.createPlan(BEARER + token, planRequest);
    }
    public SubscriptionResponse createSubscription(PSubscriptionRequest subscriptionRequest) {
        String token = getAccessToken();
        return subscriptionClient.createSubscription(BEARER + token, subscriptionRequest);
    }
}
