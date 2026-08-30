package com.example.geminidemo.ai;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.UserMessage;

public interface ChatAssistant {

    String chat(@MemoryId String memoryId, @UserMessage  String prompt);
}
