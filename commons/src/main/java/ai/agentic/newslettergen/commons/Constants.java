package ai.agentic.newslettergen.commons;

public class Constants {

    // Azure AI Foundry
    public static final String AZURE_AI_FOUNDRY_KEY = System.getenv("AZURE_AI_FOUNDRY_KEY");
    public static final String AZURE_AI_FOUNDRY_ENDPOINT = System.getenv("AZURE_AI_FOUNDRY_ENDPOINT");
    // Azure AI Foundry Models
    public static final String AGENT_CODE_SAMPLE_MODEL = "agent-code-sample-model";
    public static final String AGENT_REFERENCE_MODEL = "agent-reference-model";
    public static final String AGENT_RELEASE_MODEL = "agent-release-model";
    public static final String AGENT_STATISTICS_MODEL = "agent-statistics-model";
    public static final String AGENT_NEWSLETTER_MODEL = "agent-newsletter-model";
    // LangChain4j
    public static final boolean IS_LOGGING_ENABLED = true;

    public static final String LANGCHAIN4J_VERSTION = "1.4";
}
