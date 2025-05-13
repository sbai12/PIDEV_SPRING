package edu.esprit.payment.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

@FeignClient(name = "ollama-api", url = "${ollama.api.url:http://localhost:11434}")
public interface DeepSeekFeignClient {

    @PostMapping("/api/generate")
    Map<String, Object> generateCompletion(Map<String, Object> request);
}
