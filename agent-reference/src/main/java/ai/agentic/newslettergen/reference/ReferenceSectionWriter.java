package ai.agentic.newslettergen.reference;

import static ai.agentic.newslettergen.commons.Constants.AGENT_REFERENCE_MODEL;
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

public interface ReferenceSectionWriter {

    @UserMessage("""
        You are a specialized reference curation agent for the LangChain4j newsletter creation. Your role is to generate a complete, standalone "References" section that provides valuable learning resources and links for LangChain4j {{toLangchain4jVersion}} users.
        
        INSTRUCTIONS:
        * Replace all placeholder values [LIKE_THIS] with realistic current data
        * Comprehensive Coverage: Include essential resources for LangChain4j {{toLangchain4jVersion}} users
        * Current Links: Ensure all URLs are accurate and functional
        * Organized Structure: Group related resources logically
        * Value Focus: Prioritize the most helpful and relevant resources
        * Proper Formatting: Use correct Markdown syntax for all links
        
        EDITORIAL GUIDELINES:
        * Core Resources: GitHub repository, official documentation, community channels
        * Azure Integration: Specific Azure AI services and integration guides
        * Version Relevance: Include resources relevant to LangChain4j {{toLangchain4jVersion}}
        * Learning Materials: Tutorials, guides, and educational content
        * Community Support: Discord, forums, and support channels
        * Integration Examples: Links to specific provider integrations and examples
        
        OUTPUT STRUCTURE:
        * Start with introductory text explaining the purpose
        * Core LangChain4j resources (GitHub, docs, community)
        * Provider-specific sections (Azure, AWS, Google, etc.) as relevant
        * Additional learning resources and tutorials
        * Ensure all links are properly formatted and functional
        
        Generate the complete "References" section, ensuring it's ready for direct integration into the newsletter with the following structure:
        
        ## References
        
        [WRITE_AN_INTRODUCTION]
        
        * [TITLE_1](URL_1)
        * [TITLE_2](URL_2)
        * [TITLE_3](URL_3)
        * [TITLE_4](URL_4)
        * [TITLE_5](URL_5)
        """)
    @Agent(outputKey = "referenceSection", name = "referenceSectionWriter", description = "Curates comprehensive reference documentation, tutorials, learning resources, and community links related to LangChain4j to help newsletter readers discover valuable educational materials")
    Result<String> write(@V("toLangchain4jVersion") String toLangchain4jVersion);

    @ChatModelSupplier
    static ChatModel referenceSectionModel() {
        return OpenAiChatModel.builder()
            .apiKey(AZURE_AI_FOUNDRY_KEY)
            .baseUrl(AZURE_AI_FOUNDRY_ENDPOINT)
            .modelName(AGENT_REFERENCE_MODEL)
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
