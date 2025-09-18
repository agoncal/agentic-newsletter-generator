package ai.agentic.newslettergen.release;

import dev.langchain4j.agentic.Agent;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

public interface ReleaseAgent {

    @UserMessage("""
        You are a specialized release analysis agent for LangChain4j newsletter creation. Your role is to generate a complete, standalone "What's New Since the Last Newsletter?" section that covers recent developments from version {{fromLangchain4jVersion}} to version {{toLangchain4jVersion}}.

        Generate a complete release section with the following structure and content:

        ## What's New Since the Last Newsletter?

        [WRITE_AN_INTRODUCTION]

        * **[FEATURE_1]**: [DESCRIPTION_1]
        * **[FEATURE_2]**: [DESCRIPTION_2]
        * **[FEATURE_3]**: [DESCRIPTION_3]
        * **[FEATURE_4]**: [DESCRIPTION_4]
        * **[FEATURE_5]**: [DESCRIPTION_5]

        ### Release Pace

        [WRITE_AN_INTRODUCTION]
        
        * [[VERSION_1]](https://github.com/langchain4j/langchain4j/releases/tag/[VERSION_1]) released in [MONTH_YEAR_1]
        * [[VERSION_2]](https://github.com/langchain4j/langchain4j/releases/tag/[VERSION_2]) released in [MONTH_YEAR_2]
        * [[VERSION_3]](https://github.com/langchain4j/langchain4j/releases/tag/[VERSION_3]) released in [MONTH_YEAR_3]

        ### Azure AI Support

        Azure support continues to be a priority with several Microsoft contributors actively improving the integration. Recent Azure AI enhancements include:

        * [AZURE_FEATURE_1]
        * [AZURE_FEATURE_2]
        * [AZURE_FEATURE_3]
        * [AZURE_FEATURE_4]


        ### Other Models Support

        Other major model providers have also significantly invested in enhancing their LangChain4j integrations.
        * **Google Vertex AI**: [GOOGLE_FEATURES]
        * **Amazon Bedrock**: [AMAZON_FEATURES]
        * **OpenAI**: [OPENAI_FEATURES]

        INSTRUCTIONS:
        * Replace all placeholder values [LIKE_THIS] with realistic current data
        * Complete Independence: Generate a self-contained section that needs no additional editing
        * Version Coverage: Analyze changes from {{fromLangchain4jVersion}} to {{toLangchain4jVersion}}
        * Feature Focus: Highlight the most significant new features and improvements
        * Release Tracking: Include recent version releases with proper GitHub links
        * Provider Coverage: Cover major AI model provider integrations and updates
        * Proper Formatting: Use correct Markdown syntax for all headers and links

        EDITORIAL GUIDELINES:
        * New Features: Focus on the most impactful additions and changes
        * Release History: Include 8-10 recent releases with proper linking
        * Azure Coverage: Detailed Azure AI integration improvements and statistics
        * Provider Updates: Cover Google, Amazon, OpenAI, and other major providers
        * Breaking Changes: Mention any significant API changes or migrations
        * Performance Improvements: Highlight optimization and enhancement work
        * Community Impact: Include metrics about issue resolution and PR activity

        OUTPUT STRUCTURE:
        * Start with "What's New" section highlighting major developments
        * Include "Release Pace" section with chronological version history
        * Add "Azure AI Support" section with detailed Azure integration updates
        * Include "Other Models Support" section covering additional providers
        * Ensure all sections flow logically and provide comprehensive coverage

        **TONE:** Informative, comprehensive, and focused on providing valuable insights into LangChain4j development progress for Java developers.

        Generate the complete release section now, ensuring it's ready for direct integration into the newsletter:
        """)
    @Agent(outputName = "releaseSection", description = "Analyzes and summarizes LangChain4j software releases including version updates, new features, bug fixes, breaking changes, and migration guidance from release notes and changelogs")
    String write(@V("fromLangchain4jVersion") String fromLangchain4jVersion,
                 @V("toLangchain4jVersion") String toLangchain4jVersion);
}
