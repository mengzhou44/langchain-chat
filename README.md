# Chat (Spring Boot + LangChain4j)

A minimal Java Spring Boot project in `ai/chat` that accepts a user prompt and returns a Gemini response through LangChain4j.

## Prerequisites

- Java 17+
- Maven 3.9+
- Gemini API key

## Configure API Key (.env)

```bash
cat > .env << 'EOF'
GEMINI_API_KEY=your_gemini_api_key
EOF
```

The app loads `.env` automatically via Spring config import in `application.yaml`.

## Run

```bash
mvn spring-boot:run
```

## Test Endpoint

```bash
curl -X POST http://localhost:8080/api/chat \
  -H "Content-Type: application/json" \
  -d '{"prompt":"What is capital of China?"}'
```

Example response:

```json
{"answer":"Hello! Nice to meet you."}
```
