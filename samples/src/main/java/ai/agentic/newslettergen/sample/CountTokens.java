package ai.agentic.newslettergen.sample;

import com.knuddels.jtokkit.Encodings;
import com.knuddels.jtokkit.api.Encoding;
import com.knuddels.jtokkit.api.EncodingRegistry;
import com.knuddels.jtokkit.api.ModelType;

import java.nio.file.Files;
import java.nio.file.Paths;

public class CountTokens {

    public static void main(String[] args) throws Exception {

        EncodingRegistry registry = Encodings.newDefaultEncodingRegistry();

        Encoding encoding = registry.getEncodingForModel(ModelType.GPT_4);

        System.out.println("Tokens of the total interaction: " + encoding.countTokens(Files.readString(Paths.get(CountTokens.class.getClassLoader().getResource("agent_release_total_interaction.json").toURI()))));

        System.out.println("Tokens of the question: " + encoding.countTokens("What are the last 3 tags of the LangChain4j repository at https://github.com/langchain4j/langchain4j?"));

        System.out.println("Tokens of the System Prompt: " + encoding.countTokens(Files.readString(Paths.get(CountTokens.class.getClassLoader().getResource("system_prompt.txt").toURI()))));

        System.out.println("Tokens of GitHub MCP Server: " + encoding.countTokens(Files.readString(Paths.get(CountTokens.class.getClassLoader().getResource("github_mcp_server_all.json").toURI()))));

        System.out.println("Tokens of GitHub MCP Server (only repos): " + encoding.countTokens(Files.readString(Paths.get(CountTokens.class.getClassLoader().getResource("github_mcp_server_repos.json").toURI()))));

        System.out.println("Tokens of GitHub MCP Server (repos + filtering): " + encoding.countTokens(Files.readString(Paths.get(CountTokens.class.getClassLoader().getResource("github_mcp_server_filter.json").toURI()))));

        System.out.println("Tokens of GitHub MCP Server (repos + filter contains): " + encoding.countTokens(Files.readString(Paths.get(CountTokens.class.getClassLoader().getResource("github_mcp_server_filter_contains.json").toURI()))));
    }

}
