package com.example.geminidemo.ai;

import dev.langchain4j.service.AiServices;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.googleai.GoogleAiGeminiChatModel;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@Configuration
public class GeminiConfig {

    @Bean
        public ChatModel chatModel(
            @Value("${gemini.api-key}") String apiKey,
            @Value("${gemini.model:gemini-1.5-flash}") String modelName
    ) {
        return GoogleAiGeminiChatModel.builder()
                .apiKey(apiKey)
                .modelName(modelName)
                .build();
    }

   private final Map<Object, ChatMemory> memoryStore = new ConcurrentHashMap<>();

    @Bean
    public ChatAssistant chatAssistant(ChatModel chatModel) {
        return AiServices.builder(ChatAssistant.class)
                .chatModel(chatModel)
                .chatMemoryProvider(memoryId ->
                  memoryStore.computeIfAbsent(memoryId, id ->
                    MessageWindowChatMemory.withMaxMessages(20)))
                .build();
    }
}
