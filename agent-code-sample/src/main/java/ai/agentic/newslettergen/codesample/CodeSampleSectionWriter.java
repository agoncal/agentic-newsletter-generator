package ai.agentic.newslettergen.codesample;

import static ai.agentic.newslettergen.commons.Constants.AGENT_CODE_SAMPLE_MODEL;
import static ai.agentic.newslettergen.commons.Constants.AZURE_AI_FOUNDRY_ENDPOINT;
import static ai.agentic.newslettergen.commons.Constants.AZURE_AI_FOUNDRY_KEY;
import static ai.agentic.newslettergen.commons.Constants.IS_LOGGING_ENABLED;
import dev.langchain4j.agentic.Agent;
import dev.langchain4j.agentic.declarative.ChatModelSupplier;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

public interface CodeSampleSectionWriter {

    @UserMessage("""
        You are a specialized Java code generation agent for LangChain4j newsletter creation. Your role is to generate the "Some Code" section of the newsletter that showcases one practical, working Java code example using LangChain4j {{toLangchain4jVersion}}.
        
        Your responsibilities:
        - Generate one clean, compilable Java code snippet that demonstrates one LangChain4j feature
        - Focus on the latest features and best practices for the specified version
        - Create one short example that is educational and immediately usable
        - The sample can vary from: basic chat interactions, Azure integrations, or advanced features
        - Ensure the code example is properly formatted with syntax highlighting
        - Provide a short explanation of the example
        - Keep the example concise but complete enough to be functional
        
        Code style guidelines:
        - Use modern Java practices and clear variable names
        - Include necessary LangChain4j imports when relevant
        - Show realistic configuration and usage patterns
        - Focus on practical applications developers would actually use
        
        Output format:
        - Return well-formatted Markdown with proper code blocks
        - Use ```java for syntax highlighting
        - Include brief explanations before each code example
        - Structure examples from simple to more complex
        """)
    @Agent(outputName = "codeSampleSection", description = "Generates a practical Java code example that demonstrate the latest LangChain4j features, best practices, and real-world usage patterns for newsletter readers")
    String write(@V("toLangchain4jVersion") String toLangchain4jVersion);

    @ChatModelSupplier
    static ChatModel codeSampleSectionModel() {
        return OpenAiChatModel.builder()
            .apiKey(AZURE_AI_FOUNDRY_KEY)
            .baseUrl(AZURE_AI_FOUNDRY_ENDPOINT)
            .modelName(AGENT_CODE_SAMPLE_MODEL)
            .temperature(0.3)
            .logRequests(IS_LOGGING_ENABLED)
            .logResponses(IS_LOGGING_ENABLED)
            .build();
    }
}
