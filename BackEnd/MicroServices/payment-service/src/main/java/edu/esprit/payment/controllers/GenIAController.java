package edu.esprit.payment.controllers;

import edu.esprit.payment.services.PaymentAssistanceChatbot;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/genia")
@RequiredArgsConstructor
@Tag(name = "Gen IA")
public class GenIAController {


    private final PaymentAssistanceChatbot chatbot;

    @PostMapping("/{userId}/{paymentId}")
    public String handleChatMessage(
            @PathVariable String userId,
            @PathVariable String paymentId,
            @RequestBody String userMessage) {
        return chatbot.handleUserMessage(userId, userMessage, paymentId);
    }
}
