package ai.agentic.newslettergen.sample;

import static ai.agentic.newslettergen.commons.Constants.AGENT_CODE_SAMPLE_MODEL;
import static ai.agentic.newslettergen.commons.Constants.AZURE_AI_FOUNDRY_ENDPOINT;
import static ai.agentic.newslettergen.commons.Constants.AZURE_AI_FOUNDRY_KEY;
import static ai.agentic.newslettergen.commons.Constants.IS_LOGGING_ENABLED;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.service.AiServices;

import java.time.Duration;

public class InvokeAzureFoundryAgentCodeSampleModel {

    public static void main(String[] args) {

        OpenAiChatModel model = OpenAiChatModel.builder()
            .apiKey(AZURE_AI_FOUNDRY_KEY)
            .baseUrl(AZURE_AI_FOUNDRY_ENDPOINT)
            .modelName(AGENT_CODE_SAMPLE_MODEL)
            .temperature(1.0)
            .timeout(Duration.ofMinutes(1))
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
