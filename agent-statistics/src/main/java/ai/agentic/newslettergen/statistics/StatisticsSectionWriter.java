package ai.agentic.newslettergen.statistics;

import static ai.agentic.newslettergen.commons.Constants.AGENT_STATISTICS_MODEL;
import static ai.agentic.newslettergen.commons.Constants.AZURE_AI_FOUNDRY_ENDPOINT;
import static ai.agentic.newslettergen.commons.Constants.AZURE_AI_FOUNDRY_KEY;
import static ai.agentic.newslettergen.commons.Constants.GITHUB_PERSONAL_ACCESS_TOKEN;
import static ai.agentic.newslettergen.commons.Constants.IS_LOGGING_ENABLED;
import dev.langchain4j.agentic.Agent;
import dev.langchain4j.agentic.declarative.ChatModelSupplier;
import dev.langchain4j.agentic.declarative.ToolProviderSupplier;
import dev.langchain4j.mcp.McpToolProvider;
import dev.langchain4j.mcp.client.DefaultMcpClient;
import dev.langchain4j.mcp.client.McpClient;
import dev.langchain4j.mcp.client.transport.McpTransport;
import dev.langchain4j.mcp.client.transport.stdio.StdioMcpTransport;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

import java.util.List;

public interface StatisticsSectionWriter {

    @UserMessage("""
        You are a specialized statistics collection agent for LangChain4j newsletter creation. Your role is to generate a complete, standalone "Some Numbers" section that provides quantitative insights into LangChain4j {{toLangchain4jVersion}} project health and community engagement. Base your statistics on the LangChain4j GitHub repository located at https://github.com/langchain4j/langchain4j.
        
        Generate a complete statistics section with the following structure and content:
        
        ## Some Numbers
        
        [WRITE_AN_INTRODUCTION]
        
        * [LangChain4j main GitHub repository](https://github.com/langchain4j/langchain4j):
          * GitHub repository created in May 2023
          * [CURRENT_STARS]k stars, [WATCHING] watching, [FORKS]K forks
          * [CONTRIBUTORS] contributors
          * [CLOSED_ISSUES] closed issues, [CLOSED_PRS] closed Pull Requests
          * Used by [USED_BY]K other repositories
        * Integration:
          * Supports [MODEL_PROVIDERS] model providers (Azure OpenAI, OpenAI, Amazon Bedrock…)
          * Supports [EMBEDDING_STORES] embedding stores (Azure AI Search, Cassandra, MongoDB…)
          * Integrates with [JAVA_RUNTIMES] Java runtimes (Quarkus, SpringBoot, Helidon, Micronaut)
        
        INSTRUCTIONS:
        * Replace all placeholder values [LIKE_THIS] with realistic current data
        * Complete Independence: Generate a self-contained section that needs no additional editing
        * Current Metrics: Provide up-to-date GitHub repository statistics
        * Community Health: Include metrics that show project vitality and growth
        * Integration Scope: Cover the breadth of model providers and integration options
        * Proper Formatting: Use correct Markdown syntax for all headers and links
        
        EDITORIAL GUIDELINES:
        * GitHub Metrics: Current stars, forks, watchers, contributors, and usage statistics
        * Repository Health: Closed issues, merged PRs, and community engagement metrics
        * Integration Coverage: Number of supported model providers, embedding stores, and framework integrations
        * Growth Indicators: Metrics that demonstrate project adoption and community growth
        * Version Relevance: Ensure statistics are current and relevant to LangChain4j {{toLangchain4jVersion}}
        * Accuracy: Provide realistic and credible numbers that reflect actual project status
        
        OUTPUT STRUCTURE:
        * Start with GitHub repository statistics and community metrics
        * Include integration scope showing ecosystem breadth
        * Focus on numbers that demonstrate project health and adoption
        * Ensure all metrics are properly formatted and linked
        
        **TONE:** Factual, precise, and focused on providing quantitative insights into LangChain4j project success and community engagement.
        
        Generate the complete statistics section now, ensuring it's ready for direct integration into the newsletter:
        """)
    @Agent(outputName = "statisticsSection", description = "Collects and analyzes GitHub repository metrics including stars, forks, contributors, issues, and pull requests to provide quantitative insights into LangChain4j project health and community engagement")
    String write(@V("toLangchain4jVersion") String toLangchain4jVersion);

    @ChatModelSupplier
    static ChatModel statisticsSectionModel() {
        return OpenAiChatModel.builder()
            .apiKey(AZURE_AI_FOUNDRY_KEY)
            .baseUrl(AZURE_AI_FOUNDRY_ENDPOINT)
            .modelName(AGENT_STATISTICS_MODEL)
            .temperature(1.0)
            .logRequests(IS_LOGGING_ENABLED)
            .logResponses(IS_LOGGING_ENABLED)
            .build();
    }

    @ToolProviderSupplier
    static McpToolProvider releaseSectionMCP() {
        McpTransport transport = new StdioMcpTransport.Builder()
            .command(List.of("/usr/local/bin/docker", "run",
                "-e", "GITHUB_PERSONAL_ACCESS_TOKEN=" + GITHUB_PERSONAL_ACCESS_TOKEN,
                "-e", "GITHUB_TOOLSETS=repos",
                "-e", "GITHUB_READ_ONLY=1",
                "-i", "ghcr.io/github/github-mcp-server"))
            .logEvents(IS_LOGGING_ENABLED)
            .build();

        McpClient mcpClient = new DefaultMcpClient.Builder()
            .key("GitHubMCPClient")
            .transport(transport)
            .build();

        return McpToolProvider.builder()
            .mcpClients(mcpClient)
            .build();
    }
}
