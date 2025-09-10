package ai.agentic.newslettergen.sample;

import dev.langchain4j.model.openai.OpenAiChatModel;

public class InvokeAzureFoundryAgentCodeSampleModel {

    private static final String AZURE_AI_FOUNDRY_KEY = System.getenv("AZURE_AI_FOUNDRY_KEY");
    private static final String AZURE_AI_FOUNDRY_ENDPOINT = System.getenv("AZURE_AI_FOUNDRY_ENDPOINT");
    private static final String AGENT_CODE_SAMPLE_MODEL = System.getenv("AGENT_CODE_SAMPLE_MODEL");

    public static void main(String[] args) {

        OpenAiChatModel model = OpenAiChatModel.builder()
            .apiKey(AZURE_AI_FOUNDRY_KEY)
            .baseUrl(AZURE_AI_FOUNDRY_ENDPOINT)
            .modelName(AGENT_CODE_SAMPLE_MODEL)
            .temperature(0.3)
            .logRequests(true)
            .logResponses(true)
            .build();

        String completion = model.chat("When was the first Rolling Stones album released?");

        System.out.println(completion);
    }
}
