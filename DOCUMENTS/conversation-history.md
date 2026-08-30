# Conversation Memory Testing

## Overview
The application now supports in-memory conversation history per session. Each conversation is identified by a `sessionId`, and all messages within a session are retained in memory (up to 20 most recent messages).

## Test Scenario: Tell Name → Ask Name

### First Request — Tell the system your name
```bash
curl -X POST http://localhost:8080/api/chat \
  -H "Content-Type: application/json" \
  -d '{"sessionId":"session1","prompt":"My name is Alice"}'
```

**Expected response:** Gemini acknowledges (e.g., "Nice to meet you, Alice!")

### Second Request — Ask it to recall your name (same sessionId)
```bash
curl -X POST http://localhost:8080/api/chat \
  -H "Content-Type: application/json" \
  -d '{"sessionId":"session1","prompt":"What is my name?"}'
```

**Expected response:** Gemini recalls "Your name is Alice" — proving it has access to the conversation history.

### Third Request — New session, no memory
```bash
curl -X POST http://localhost:8080/api/chat \
  -H "Content-Type: application/json" \
  -d '{"sessionId":"session2","prompt":"What is my name?"}'
```

**Expected response:** Gemini does NOT know your name — this is a fresh session with no prior context.

## How It Works

1. **Session Isolation:** Each unique `sessionId` gets its own `ChatMemory` stored in `memoryStore` (ConcurrentHashMap)
2. **LangChain4j Integration:** The `@MemoryId` annotation on the `ChatAssistant.chat()` method tells `AiServices` to route each call to the correct per-session memory
3. **Message Window:** `MessageWindowChatMemory.withMaxMessages(20)` keeps the 20 most recent messages to prevent unbounded growth
4. **Full Conversation Context:** When sending a request, LangChain4j automatically appends the entire conversation history before sending to Gemini

## Architecture

- **Controller** (web/ChatController.java): Receives `sessionId` + `prompt` from request body
- **Service** (service/GeminiService.java): Validates inputs and forwards to AI layer
- **AI Layer** (ai/ChatAssistant.java): LangChain4j proxy with `@MemoryId` parameter
- **Config** (ai/GeminiConfig.java): Wires `chatMemoryProvider` with `ConcurrentHashMap` backend
