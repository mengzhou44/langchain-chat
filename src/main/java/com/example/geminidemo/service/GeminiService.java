package com.example.geminidemo;

import org.springframework.stereotype.Service;

@Service
public class GeminiService {

    private final ChatAssistant chatAssistant;

    public GeminiService(ChatAssistant chatAssistant) {
        this.chatAssistant = chatAssistant;
    }

    public String ask(String prompt) {
        if (prompt == null || prompt.isBlank()) {
            throw new IllegalArgumentException("prompt must not be blank");
        }
        return chatAssistant.chat(prompt);
    }
}
