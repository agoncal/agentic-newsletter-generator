package ai.agentic.newslettergen.sample;

import dev.langchain4j.service.UserMessage;

public interface AIAssistant {

    String chat(@UserMessage String userMessage);
}
