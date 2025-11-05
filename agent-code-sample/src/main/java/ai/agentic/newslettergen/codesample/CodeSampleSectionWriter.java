package ai.agentic.newslettergen.codesample;

import static ai.agentic.newslettergen.commons.Constants.AGENT_CODE_SAMPLE_MODEL;
import static ai.agentic.newslettergen.commons.Constants.AZURE_AI_FOUNDRY_ENDPOINT;
import static ai.agentic.newslettergen.commons.Constants.AZURE_AI_FOUNDRY_KEY;
import static ai.agentic.newslettergen.commons.Constants.IS_LOGGING_ENABLED;
import dev.langchain4j.agentic.Agent;
import dev.langchain4j.agentic.agent.AgentRequest;
import dev.langchain4j.agentic.declarative.BeforeAgentInvocation;
import dev.langchain4j.agentic.declarative.ChatModelSupplier;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.service.Result;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

import java.time.Duration;

public interface CodeSampleSectionWriter {

    @UserMessage("""
        You are a specialized Java code generation agent for the LangChain4j newsletter creation. Your role is to generate a short, standalone "Some Code" section that showcases practical Java code examples using LangChain4j {{toLangchain4jVersion}}.
        
        INSTRUCTIONS:
        * Replace all placeholder values [LIKE_THIS] with realistic current data
        * Complete Independence: Generate a self-contained section that needs no additional editing
        * Practical Focus: Show real-world usage that developers can immediately apply
        * Version Relevance: Highlight features specific to LangChain4j {{toLangchain4jVersion}}
        * Working Code: Provide compilable, functional Java code examples
        * Clear Context: Include brief explanatory text before the code
        * Proper Formatting: Use correct Markdown syntax with ```java code blocks
        
        EDITORIAL GUIDELINES:
        * Choose ONE specific LangChain4j feature to demonstrate
        * Possible topics: Chat API, Azure AI integrations, agent orchestration, embeddings, RAG patterns, tool calling
        * Use realistic configuration and connection patterns
        * Include necessary imports and setup code
        * Show practical applications developers would actually implement
        * Keep examples concise but complete (10-20 lines of code)
        * Use clear variable names and modern Java practices
        * TONE: Professional, educational, and immediately actionable for Java developers working with LangChain4j.
        
        Generate the complete "Some Code" section, ensuring it's ready for direct integration into the newsletter with the following structure:
        
        ## Some Code
        
        [WRITE_AN_INTRODUCTION]
        
        ```java
        [COMPLETE_WORKING_JAVA_CODE_EXAMPLE]
        ```
        
        """)
    @Agent(outputKey = "codeSampleSection", name = "codeSampleSectionWriter", description = "Generates a practical Java code example that demonstrates the latest LangChain4j features, best practices, and real-world usage patterns for newsletter readers")
    Result<String> write(@V("toLangchain4jVersion") String toLangchain4jVersion);

    @ChatModelSupplier
    static ChatModel codeSampleSectionModel() {
        return OpenAiChatModel.builder()
            .apiKey(AZURE_AI_FOUNDRY_KEY)
            .baseUrl(AZURE_AI_FOUNDRY_ENDPOINT)
            .modelName(AGENT_CODE_SAMPLE_MODEL)
            .temperature(1.0)
            .timeout(Duration.ofMinutes(1))
            .logRequests(IS_LOGGING_ENABLED)
            .logResponses(IS_LOGGING_ENABLED)
            .build();
    }

    @BeforeAgentInvocation
    static void beforeInvocation(AgentRequest request) {
        System.out.println("\n \u001B[32m  Invoking " + request.toString() + " \u001B[0m \n");
    }
}
