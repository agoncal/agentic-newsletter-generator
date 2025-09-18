package ai.agentic.newslettergen.editor;

import static ai.agentic.newslettergen.commons.Constants.AGENT_NEWSLETTER_MODEL;
import static ai.agentic.newslettergen.commons.Constants.AZURE_AI_FOUNDRY_ENDPOINT;
import static ai.agentic.newslettergen.commons.Constants.AZURE_AI_FOUNDRY_KEY;
import static ai.agentic.newslettergen.commons.Constants.IS_LOGGING_ENABLED;
import dev.langchain4j.agentic.Agent;
import dev.langchain4j.agentic.declarative.ChatModelSupplier;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

public interface NewsletterEditor {

    @UserMessage("""
        The current date is {{current_date}}
        
        You are a professional newsletter editor specializing in LangChain4j technology publications. Your role is to compile, edit, and format a comprehensive LangChain4j newsletter (from version {{fromLangchain4jVersion}} to version {{toLangchain4jVersion}}) by integrating content from specialized agents.
        
        # LangChain4j Newsletter – [CURRENT_MONTH] [CURRENT_YEAR]
        
        This newsletter helps you stay tuned for the latest features of LangChain4j
        
        ## What is LangChain4j?
        [LangChain4j](https://docs.langchain4j.dev/) is an open-source library that simplifies the integration of AI and Large Language Models (LLMs) into Java applications. It provides a unified API for interacting with various model providers and embedding stores, along with tools for prompt templating, memory management, and high-level patterns like RAG (Retrieval-Augmented Generation).
        
        ## Some Numbers
        
        {{statisticsSection}}
        
        ## What's New Since the Last Newsletter?
        
        {{releaseSection}}
        
        ## Some Code
        
        {{codeSampleSection}}
        
        ## References
        
        {{referenceSection}}
        
        INSTRUCTIONS:
        * Follow the EXACT structure and Markdown formatting shown above
        * Replace all placeholder values [LIKE_THIS] with realistic current data
        * Maintain professional tone and accurate information
        * Ensure all links are properly formatted
        * Create compelling content that matches the established newsletter style
        * Ensure the newsletter flows naturally and provides valuable information to Java developers
        
        EDITORIAL GUIDELINES:
        * Integration: Seamlessly combine all sections into a cohesive narrative
        * Consistency: Ensure uniform tone, style, and formatting throughout
        * Deduplication: Remove any repeated information between sections
        * Flow: Add smooth transitions and connecting text where needed
        * Accuracy: Maintain factual accuracy and fix any inconsistencies
        * Formatting: Ensure proper Markdown headers, links, and code blocks
        * Completeness: Include current month/year in title and ensure all content is relevant
        * Quality: Produce a polished, professional newsletter ready for distribution
        """)
    @Agent(outputName = "newsletter", description = "Compiles, edits, and formats the complete monthly newsletter by integrating content from all specialized agents, ensuring consistency, removing duplications, and producing a polished Markdown publication ready for distribution")
    String editAndCompileNewsletter(
        @V("statisticsSection") String statisticsSection,
        @V("releaseSection") String releaseSection,
        @V("referenceSection") String referenceSection,
        @V("codeSampleSection") String codeSampleSection,
        @V("fromLangchain4jVersion") String fromLangchain4jVersion,
        @V("toLangchain4jVersion") String toLangchain4jVersion
    );

    @ChatModelSupplier
    static ChatModel newsletterEditorModel() {
        return OpenAiChatModel.builder()
            .apiKey(AZURE_AI_FOUNDRY_KEY)
            .baseUrl(AZURE_AI_FOUNDRY_ENDPOINT)
            .modelName(AGENT_NEWSLETTER_MODEL)
            .temperature(0.3)
            .logRequests(IS_LOGGING_ENABLED)
            .logResponses(IS_LOGGING_ENABLED)
            .build();
    }
}
