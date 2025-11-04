package ai.agentic.newslettergen.commons;

import io.github.cdimascio.dotenv.Dotenv;
import io.github.cdimascio.dotenv.DotenvEntry; // Added to access DotenvEntry values directly
import org.tinylog.Logger;

public class Constants {

    // Azure AI Foundry
    public static final String AZURE_AI_FOUNDRY_KEY = getenv("AZURE_AI_FOUNDRY_KEY");
    public static final String AZURE_AI_FOUNDRY_ENDPOINT = getenv("AZURE_AI_FOUNDRY_ENDPOINT");

    // Azure AI Foundry Models
    public static final String AGENT_CODE_SAMPLE_MODEL = "agent-code-sample-writer-model";
    public static final String AGENT_REFERENCE_MODEL = "agent-reference-writer-model";
    public static final String AGENT_RELEASE_MODEL = "agent-release-writer-model";
    public static final String AGENT_STATISTICS_MODEL = "agent-statistics-writer-model";
    public static final String AGENT_NEWSLETTER_MODEL = "agent-newsletter-editor-model";

    // GitHub MCP Server
    public static final String GITHUB_PERSONAL_ACCESS_TOKEN = getenv("GITHUB_PERSONAL_ACCESS_TOKEN");

    // LangChain4j
    public static final boolean IS_LOGGING_ENABLED = true;

    private static String getenv(String propertyName) {

        // Load environment variables from .env file in local directory
        Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();
        DotenvEntry localEntry = dotenv.entries(Dotenv.Filter.DECLARED_IN_ENV_FILE)
            .stream()
            .filter(e -> propertyName.equals(e.getKey()))
            .findFirst()
            .orElse(null);
        if (localEntry != null) {
            Logger.info("Environment variable {} found in local .env file", propertyName);
            return localEntry.getValue();
        }

        // Load environment variables from .env file in root directory
        dotenv = Dotenv.configure().directory("../").ignoreIfMissing().load();
        localEntry = dotenv.entries(Dotenv.Filter.DECLARED_IN_ENV_FILE)
            .stream()
            .filter(e -> propertyName.equals(e.getKey()))
            .findFirst()
            .orElse(null);
        if (localEntry != null) {
            Logger.info("Environment variable {} found in root .env file", propertyName);
            return localEntry.getValue();
        }

        // Load environment variables from system environment
        String propertyValue = System.getenv(propertyName);
        if (propertyValue != null && !propertyValue.isEmpty()) {
            Logger.info("Environment variable {} found in system environment", propertyName);
            return propertyValue;
        }

        Logger.error("ERROR: Missing environment variable:" + propertyName);
        Logger.error("   Set it in your .env file or export it");
        System.exit(1);
        return null;
    }
}
