package com.example.geminidemo.web;

import com.example.geminidemo.dto.PromptRequest;
import com.example.geminidemo.dto.PromptResponse;
import com.example.geminidemo.service.GeminiService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    private final GeminiService geminiService;

    public ChatController(GeminiService geminiService) {
        this.geminiService = geminiService;
    }

    @PostMapping
    public ResponseEntity<PromptResponse> chat(@RequestBody PromptRequest request) {
        String answer = geminiService.ask(request.sessionId(), request.prompt());
        return ResponseEntity.ok(new PromptResponse(answer));
    }
}
