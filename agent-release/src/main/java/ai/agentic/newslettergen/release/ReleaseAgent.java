package ai.agentic.newslettergen.release;

import dev.langchain4j.agentic.Agent;
import dev.langchain4j.agentic.declarative.ChatModelSupplier;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

import static ai.agentic.newslettergen.commons.Constants.*;

@SystemMessage("""
    You are a specialized Release agent for MediaConnector newsletter creation.
    Your primary role is to analyze and summarize recent software releases, focusing on version updates, new features, bug fixes, and breaking changes.
    You process release notes and changelog information to provide clear, concise, and digestible summaries of what's new in each release.

    Your responsibilities:
    - Review and interpret release notes and changelogs for the specified MediaConnector version
    - Summarize key updates, including:
        - Version number and release date
        - Major new features and enhancements
        - Notable bug fixes
        - Any breaking changes or important migration notes
    - Present the information in a way that is easy to understand for newsletter readers
    - Keep the summary concise, accurate, and focused on practical impact

    Output format:
    - Return well-formatted Markdown
    - Use bullet points or short paragraphs for clarity
    - Highlight the most important changes first
    - Avoid unnecessary technical jargon unless essential
    """)
public interface ReleaseAgent {

    @Agent(outputName = "releaseNotes", description = "Analyzes and summarizes MediaConnector release notes and changelogs for newsletter-ready updates, focusing on version changes, new features, bug fixes, and breaking changes.")
    @UserMessage("""
        Please analyze and summarize the release notes and changelog for MediaConnector version {{version}}.\s
        Focus on version updates, new features, bug fixes, and breaking changes.\s
        Provide a clear and concise summary suitable for a newsletter.
        
        Release notes:
        {{releaseNotes}}
   \s""")
    String write(
        @V("releaseNotes") String releaseNotes,
        @V("version") String version
    );

    @ChatModelSupplier
    static ChatModel releaseModel() {
        return OpenAiChatModel.builder()
            .apiKey(AZURE_AI_FOUNDRY_KEY)
            .baseUrl(AZURE_AI_FOUNDRY_ENDPOINT)
            .modelName(AGENT_RELEASE_MODEL)
            .temperature(1.0)
            .logRequests(IS_LOGGING_ENABLED)
            .logResponses(IS_LOGGING_ENABLED)
            .build();
    }
}
