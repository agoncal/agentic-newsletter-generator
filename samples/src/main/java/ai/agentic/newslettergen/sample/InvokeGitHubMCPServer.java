package ai.agentic.newslettergen.sample;

import dev.langchain4j.mcp.McpToolProvider;
import dev.langchain4j.mcp.client.DefaultMcpClient;
import dev.langchain4j.mcp.client.McpClient;
import dev.langchain4j.mcp.client.transport.McpTransport;
import dev.langchain4j.mcp.client.transport.stdio.StdioMcpTransport;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.service.AiServices;

import java.util.List;

public class InvokeGitHubMCPServer {

    private static final String AZURE_AI_FOUNDRY_KEY = System.getenv("AZURE_AI_FOUNDRY_KEY");
    private static final String AZURE_AI_FOUNDRY_ENDPOINT = System.getenv("AZURE_AI_FOUNDRY_ENDPOINT");
    private static final String AGENT_RELEASE_MODEL = "agent-release-model";

    private static final boolean IS_LOGGING_ENABLED = true;

    public static void main(String[] args) {

        // GitHub MCP Server
        McpTransport transport = new StdioMcpTransport.Builder()
            .command(List.of("/usr/local/bin/docker", "run",
                "-e", "GITHUB_PERSONAL_ACCESS_TOKEN=<your_github_token>",
                "-e", "GITHUB_TOOLSETS=repos",
                "-e", "GITHUB_READ_ONLY=1",
                "-i", "ghcr.io/github/github-mcp-server"))
            .logEvents(IS_LOGGING_ENABLED)
            .build();

        McpClient mcpClient = new DefaultMcpClient.Builder()
            .key("GitHubMCPClient")
            .transport(transport)
            .build();

        McpToolProvider mcpToolProvider = McpToolProvider.builder()
            .mcpClients(mcpClient)
            .build();

        OpenAiChatModel model = OpenAiChatModel.builder()
            .apiKey(AZURE_AI_FOUNDRY_KEY)
            .baseUrl(AZURE_AI_FOUNDRY_ENDPOINT)
            .modelName(AGENT_RELEASE_MODEL)
            .temperature(1.0)
            .logRequests(IS_LOGGING_ENABLED)
            .logResponses(IS_LOGGING_ENABLED)
            .build();

        AIAssistant assistant = AiServices.builder(AIAssistant.class)
            .chatModel(model)
            .toolProvider(mcpToolProvider)
            .build();

        String completion = assistant.chat("What are the last 3 tags of the LangChain4j repository?");

        System.out.println(completion);
    }
}
