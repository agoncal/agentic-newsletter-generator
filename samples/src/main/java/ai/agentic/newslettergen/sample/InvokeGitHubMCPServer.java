package ai.agentic.newslettergen.sample;

import dev.langchain4j.mcp.McpToolProvider;
import dev.langchain4j.mcp.client.DefaultMcpClient;
import dev.langchain4j.mcp.client.McpClient;
import dev.langchain4j.mcp.client.transport.McpTransport;
import dev.langchain4j.mcp.client.transport.http.StreamableHttpMcpTransport;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.service.AiServices;

public class InvokeGitHubMCPServer {

    private static final String AZURE_AI_FOUNDRY_KEY = System.getenv("AZURE_AI_FOUNDRY_KEY");
    private static final String AZURE_AI_FOUNDRY_ENDPOINT = System.getenv("AZURE_AI_FOUNDRY_ENDPOINT");
    private static final String AGENT_CODE_SAMPLE_MODEL = System.getenv("AGENT_CODE_SAMPLE_MODEL");

    private static final boolean IS_LOGGING_ENABLED = true;

    public static void main(String[] args) {

        // GitHub MCP Server
        McpTransport transport = new StreamableHttpMcpTransport.Builder()
            .url("http://localhost:8780/mcp")
            .logRequests(IS_LOGGING_ENABLED)
            .logResponses(IS_LOGGING_ENABLED)
            .build();

        McpClient mcpClient = new DefaultMcpClient.Builder()
            .key("VintageStoreMCPClient")
            .transport(transport)
            .build();

        McpToolProvider mcpToolProvider = McpToolProvider.builder()
            .mcpClients(mcpClient)
            .build();

        OpenAiChatModel model = OpenAiChatModel.builder()
            .apiKey(AZURE_AI_FOUNDRY_KEY)
            .baseUrl(AZURE_AI_FOUNDRY_ENDPOINT)
            .modelName(AGENT_CODE_SAMPLE_MODEL)
            .temperature(0.3)
            .logRequests(IS_LOGGING_ENABLED)
            .logResponses(IS_LOGGING_ENABLED)
            .build();

        AIAssistant assistant = AiServices.builder(AIAssistant.class)
            .chatModel(model)
            .toolProvider(mcpToolProvider)
            .build();

        String completion = assistant.chat("What are the last 3 releases of LangChain4j?");

        System.out.println(completion);
    }
}
