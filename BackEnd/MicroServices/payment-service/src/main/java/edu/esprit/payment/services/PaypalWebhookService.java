package edu.esprit.payment.services;

import edu.esprit.payment.clients.PaypalFeignClient;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaypalWebhookService {

    private final PaypalFeignClient paypalFeignClient;

    @Value("${paypal.client-id}")
    private String clientId;

    @Value("${paypal.secret}")
    private String secret;

    //@Value("${paypal.webhook-id}")
    private String webhookId;

    public boolean verifyWebhookSignature(HttpServletRequest request, Map<String, Object> payload) throws IOException {
        String transmissionId = request.getHeader("Paypal-Transmission-Id");
        String transmissionTime = request.getHeader("Paypal-Transmission-Time");
        String transmissionSig = request.getHeader("Paypal-Transmission-Sig");
        String certUrl = request.getHeader("Paypal-Cert-Url");
        String authAlgo = request.getHeader("Paypal-Auth-Algo");

        String rawBody = getRawRequestBody(request);
        log.info("Raw body: {}", rawBody);

        Map<String, Object> verificationPayload = new HashMap<>();
        verificationPayload.put("auth_algo", authAlgo);
        verificationPayload.put("cert_url", certUrl);
        verificationPayload.put("transmission_id", transmissionId);
        verificationPayload.put("transmission_sig", transmissionSig);
        verificationPayload.put("transmission_time", transmissionTime);
        verificationPayload.put("webhook_id", webhookId);
        verificationPayload.put("webhook_event", payload);

        String basicAuth = getBasicAuthHeader(clientId, secret);

        Map<String, Object> response = paypalFeignClient.verifyWebhookSignature(basicAuth, verificationPayload);
        String status = (String) response.get("verification_status");

        return "SUCCESS".equals(status);
    }

    private String getBasicAuthHeader(String clientId, String secret) {
        String creds = clientId + ":" + secret;
        return "Basic " + Base64.getEncoder().encodeToString(creds.getBytes(StandardCharsets.UTF_8));
    }

    private String getRawRequestBody(HttpServletRequest request) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = request.getReader();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        return sb.toString();
    }
}

