package edu.esprit.payment.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient(name = "TrainingServiceClient", url = "http://training-service:8085")
public interface TrainingServiceClient {
    @PostMapping("/api/payment-service/webhooks")
    ResponseEntity<String> notifyPaymentFailed(@RequestBody Map<String, Object> payload);
}
