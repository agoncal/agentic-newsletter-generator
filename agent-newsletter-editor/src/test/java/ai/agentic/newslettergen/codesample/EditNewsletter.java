package ai.agentic.newslettergen.codesample;

import ai.agentic.newslettergen.editor.NewsletterEditor;
import static ai.agentic.newslettergen.editor.NewsletterEditor.newsletterEditorModel;
import dev.langchain4j.agentic.AgenticServices;

public class EditNewsletter {

    public static void main(String[] args) {

        NewsletterEditor newsletterEditor = AgenticServices.agentBuilder(NewsletterEditor.class)
            .chatModel(newsletterEditorModel())
            .outputName("newsletter")
            .build();

        String newsletter = newsletterEditor.editAndCompileNewsletter(STATISTICS_SECTION, RELEASE_SECTION, REFERENCE_SECTION, CODE_SAMPLE_SECTION, "1.2", "1.5");

        System.out.println(newsletter);
    }

    private static final String STATISTICS_SECTION = """
        ## Some Numbers
        
        LangChain4j 1.5 continues to demonstrate robust growth and community engagement, reflecting its increasing adoption and integration within the Java ecosystem. Here's a snapshot of the project's health and reach:
        
        * [LangChain4j main GitHub repository](https://github.com/langchain4j/langchain4j):
          * GitHub repository created in May 2023
          * 2.5k stars, 350 watching, 1.2k forks
          * 45 contributors
          * 120 closed issues, 150 closed Pull Requests
          * Used by 1.8k other repositories
        
        * Integration:
          * Supports 5 model providers (Azure OpenAI, OpenAI, Amazon Bedrock, Google AI, Hugging Face)
          * Supports 4 embedding stores (Azure AI Search, Cassandra, MongoDB, Elasticsearch)
          * Integrates with 4 Java runtimes (Quarkus, SpringBoot, Helidon, Micronaut)
        
        These metrics highlight LangChain4j's expanding influence and the vibrant community contributing to its development. The project's integration capabilities across multiple platforms and providers underscore its versatility and appeal to developers seeking comprehensive solutions for language model integration.
        """;

    private static final String RELEASE_SECTION = "";

    private static final String REFERENCE_SECTION = "";

    public static final String CODE_SAMPLE_SECTION = """
        ## Some Code
        
        In this section, we'll explore how to use LangChain4j 1.5 to implement a simple Retrieval-Augmented Generation (RAG) pattern. This pattern is particularly useful for enhancing language models with external data sources, enabling more informed and contextually relevant responses. We'll demonstrate how to integrate a basic vector store with LangChain4j to retrieve relevant documents and use them to augment a language model's responses.
        
        ```java
        import org.langchain4j.core.model.chat.ChatLanguageModel;
        import org.langchain4j.core.model.chat.ChatLanguageModelFactory;
        import org.langchain4j.data.vectorstore.VectorStore;
        import org.langchain4j.data.vectorstore.memory.InMemoryVectorStore;
        import org.langchain4j.data.document.Document;
        import org.langchain4j.data.document.DocumentMetadata;
        import org.langchain4j.data.document.Metadata;
        import org.langchain4j.data.document.MetadataBuilder;
        import org.langchain4j.data.document.MetadataField;
        import org.langchain4j.data.document.DocumentStore;
        import org.langchain4j.data.document.memory.InMemoryDocumentStore;
        import org.langchain4j.data.document.DocumentMetadata;
        import org.langchain4j.data.document.Metadata;
        import org.langchain4j.data.document.MetadataBuilder;
        import org.langchain4j.data.document.MetadataField;
        import org.langchain4j.data.document.Document;
        import org.langchain4j.data.document.DocumentMetadata;
        import org.langchain4j.data.document.Metadata;
        import org.langchain4j.data.document.MetadataBuilder;
        import org.langchain4j.data.document.MetadataField;
        import org.langchain4j.model.retrieval.RetrievalAugmentedGeneration;
        import org.langchain4j.model.retrieval.RetrievalAugmentedGenerationFactory;
        
        import java.util.List;
        import java.util.Arrays;
        
        public class RAGExample {
        
            public static void main(String[] args) {
                // Initialize a simple in-memory vector store
                VectorStore vectorStore = new InMemoryVectorStore();
        
                // Create some documents to store in the vector store
                Document doc1 = new Document("Document 1", "This is the content of document 1.");
                Document doc2 = new Document("Document 2", "This is the content of document 2.");
        
                // Add documents to the vector store
                vectorStore.add(doc1);
                vectorStore.add(doc2);
        
                // Initialize a simple chat language model (mock implementation)
                ChatLanguageModel chatModel = ChatLanguageModelFactory.create();
        
                // Create a Retrieval-Augmented Generation (RAG) instance
                RetrievalAugmentedGeneration rag = RetrievalAugmentedGenerationFactory.create(chatModel, vectorStore);
        
                // Define a query
                String query = "Tell me about document 1";
        
                // Retrieve relevant documents and generate a response
                List<Document> retrievedDocs = rag.retrieve(query);
                String response = rag.generate(query, retrievedDocs);
        
                // Output the response
                System.out.println("Response: " + response);
            }
        }
        ```
        
        ### Explanation
        
        1. **Vector Store Setup**: We initialize an `InMemoryVectorStore` to store and retrieve documents. This is a simple in-memory implementation suitable for demonstration purposes.
        
        2. **Document Creation**: Two sample documents are created and added to the vector store. These documents serve as the knowledge base for the RAG pattern.
        
        3. **Chat Language Model**: A mock `ChatLanguageModel` is used to simulate the language model's behavior. In a real-world scenario, this would be replaced with an actual model.
        
        4. **RAG Integration**: The `RetrievalAugmentedGeneration` class is used to combine the retrieval of relevant documents with the generation of responses. This demonstrates how external data can be used to enhance the model's output.
        
        5. **Query and Response**: A query is defined, and the RAG instance retrieves relevant documents and generates a response based on the query and retrieved documents.
        
        This example provides a practical starting point for developers looking to implement RAG patterns using LangChain4j 1.5.
        """;
}
