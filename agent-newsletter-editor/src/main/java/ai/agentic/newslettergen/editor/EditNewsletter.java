package ai.agentic.newslettergen.editor;

import static ai.agentic.newslettergen.editor.NewsletterEditor.newsletterEditorModel;
import dev.langchain4j.agentic.AgenticServices;

public class EditNewsletter {

    public static void main(String[] args) {

        NewsletterEditor newsletterEditor = AgenticServices.agentBuilder(NewsletterEditor.class)
            .chatModel(newsletterEditorModel())
            .outputName("newsletter")
            .build();

        String newsletter = newsletterEditor.editAndCompileNewsletter(CODE_SAMPLE_SECTION, "1.2", "1.5");

        System.out.println(newsletter);
    }

    public static final String CODE_SAMPLE_SECTION = """
        LangChain4j is a Java library designed to facilitate the integration of language models into applications. As of version 1.4, it provides various features to streamline the process of working with language models, such as OpenAI's GPT models. Below are some practical Java code examples that showcase key features and capabilities of LangChain4j version 1.4.
        
        Here's how you can set up and initialize the API:
        
        ```java
        import dev.langchain4j.model.OpenAiModel;
        import dev.langchain4j.model.api.OpenAiClient;
        import dev.langchain4j.model.api.OpenAiResponse;
        
        public class LangChainExample {
            public static void main(String[] args) {
                // Initialize the OpenAI client with your API key
                OpenAiClient client = OpenAiClient.builder()
                        .apiKey("YOUR_OPENAI_API_KEY")
                        .build();
        
                // Create an instance of the OpenAI model
                OpenAiModel model = OpenAiModel.builder()
                        .client(client)
                        .model("text-davinci-003") // Specify the model
                        .build();
        
                // Prepare a prompt
                String prompt = "Translate the following English text to French: 'Hello, how are you?'";
        
                // Generate a response
                OpenAiResponse response = model.generate(prompt);
        
                // Print the response
                System.out.println("Response: " + response.getChoices().get(0).getText());
            }
        }
        ```
        """;
}
