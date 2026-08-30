package com.example.geminidemo.service;

import com.example.geminidemo.ai.ChatAssistant;
import org.springframework.stereotype.Service;

@Service
public class GeminiService {

    private final ChatAssistant chatAssistant;

    public GeminiService(ChatAssistant chatAssistant) {
        this.chatAssistant = chatAssistant;
    }

    public String ask(String sessionId, String prompt) {
        if (prompt == null || prompt.isBlank()) {
            throw new IllegalArgumentException("prompt must not be blank");
        }
        if (sessionId == null || sessionId.isBlank()) {
            throw new IllegalArgumentException("sessionId must not be blank");
        }
        return chatAssistant.chat(sessionId, prompt);
    }
}
