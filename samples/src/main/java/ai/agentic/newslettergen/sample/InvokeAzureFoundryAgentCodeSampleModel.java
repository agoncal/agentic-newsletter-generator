package ai.agentic.newslettergen.sample;

import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.service.AiServices;

public class InvokeAzureFoundryAgentCodeSampleModel {

    private static final String AZURE_AI_FOUNDRY_KEY = System.getenv("AZURE_AI_FOUNDRY_KEY");
    private static final String AZURE_AI_FOUNDRY_ENDPOINT = System.getenv("AZURE_AI_FOUNDRY_ENDPOINT");
    private static final String AGENT_CODE_SAMPLE_MODEL = "agent-code-sample-model";

    private static final boolean IS_LOGGING_ENABLED = true;

    public static void main(String[] args) {

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
            .build();

        String completion = assistant.chat("Write an Hello World in Java");

        System.out.println(completion);
    }
}
