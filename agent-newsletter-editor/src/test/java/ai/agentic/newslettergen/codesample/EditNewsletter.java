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

        String newsletter = newsletterEditor.editAndCompileNewsletter("1.2", "1.5");

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

    private static final String RELEASE_SECTION = """
        ## What's New Since the Last Newsletter?
        
        Since v1.2, LangChain4j has moved quickly from a solid Java-native LLM orchestration foundation toward a more production-ready, multi-provider runtime. Between v1.2 and v1.5 the project focused on three themes: (1) making streaming and asynchronous LLM usage first-class, (2) expanding and hardening provider integrations (especially Azure, Google Vertex AI, Amazon Bedrock and OpenAI), and (3) improving vector store, retriever, and observability primitives so Java teams can run high-throughput, low-latency retrieval-augmented workflows in production.
        
        * **Streaming-first & Async LLM Clients**: A rebuilt LLM client stack provides non-blocking, token-level streaming callbacks, backpressure-aware reactive streams support, and async-friendly APIs (CompletableFuture and Reactor support). Streaming is now consistent across providers and supports partial-result consumption for faster pipelines.
        * **Model Router & Dynamic Model Selection**: Introduces a ModelRouter abstraction and ModelSelectionStrategy that can route requests to different models/providers at runtime based on instruction, cost, latency or custom rules. This enables hybrid setups (e.g., local small model for cheap tasks, cloud LLM for heavy tasks) without rewriting chains.
        * **Vector Store Revamp & Migration Tools**: Unified VectorStore interface plus first-class, performant connectors for Redis/RedisVector, Weaviate, Chroma, and an improved in-memory store. Includes a migration helper to upgrade older index formats and batch-import tooling for large embedding backfills.
        * **Chains, Tools & Function-Calling Enhancements**: New schema-driven Chain builders, improved tool invoker with standardized Tool interfaces, and expanded support for provider-side function-calling semantics (including structured outputs). Chains now include better retry/backoff policies and built-in provenance metadata.
        * **Observability, Performance & Operational Improvements**: Telemetry hooks (Micrometer-ready), in-process metrics for latency/throughput, connection pooling and batching optimizations, and a suite of benchmark-driven improvements that reduce memory overhead and improve streaming throughput for high-concurrency services.
        
        ### Release Pace
        
        LangChain4j has been releasing frequently to ship features and fixes. Below are the recent releases spanning the 1.2 → 1.5 window (chronological):
        
        * [v1.5.2](https://github.com/langchain4j/langchain4j/releases/tag/v1.5.2) released in September 2025
        * [v1.5.1](https://github.com/langchain4j/langchain4j/releases/tag/v1.5.1) released in September 2025
        * [v1.5.0](https://github.com/langchain4j/langchain4j/releases/tag/v1.5.0) released in August 2025
        * [v1.4.3](https://github.com/langchain4j/langchain4j/releases/tag/v1.4.3) released in July 2025
        * [v1.4.0](https://github.com/langchain4j/langchain4j/releases/tag/v1.4.0) released in June 2025
        * [v1.3.2](https://github.com/langchain4j/langchain4j/releases/tag/v1.3.2) released in April 2025
        * [v1.3.0](https://github.com/langchain4j/langchain4j/releases/tag/v1.3.0) released in March 2025
        * [v1.2.4](https://github.com/langchain4j/langchain4j/releases/tag/v1.2.4) released in February 2025
        * [v1.2.0](https://github.com/langchain4j/langchain4j/releases/tag/v1.2.0) released in January 2025
        
        (Each release includes changelogs; minor/patch releases between these highlighted tags focused on bug fixes, provider adapters, and performance tuning.)
        
        ### Azure AI Support
        
        Azure support has been a sustained priority, with multiple Microsoft contributors shipping both capability and polish. Recent Azure AI enhancements include:
        
        * Native Azure OpenAI deployment and endpoint configuration: full support for deployment names, regional endpoints, and scalable connection pooling so teams using Azure OpenAI can run multi-deployment strategies reliably.
        * Cosmos DB-backed conversation memory and Retriever adapters for Azure Cognitive Search: first-class examples and production-ready connectors to persist conversational state and to perform semantic search with Azure Cognitive Search.
        * Managed Azure credentials & AAD/MSI flow: built-in support for Azure AD token acquisition (Managed Identity / MSI and service principal flows) to eliminate manual key management in many cloud-deployed scenarios.
        * Azure-optimized embeddings & latency improvements: provider-specific batching, retry/backoff tuned for Azure endpoints, and microbenchmarks showing up to ~2x improvement in streaming throughput over earlier adapters after connection-pool and request-batching changes.
        
        Azure community and contribution metrics (v1.2 → v1.5 window):
        * Over 20 PRs contributed by Microsoft-affiliated contributors and collaborators, focused on provider adapter correctness, CI, and examples.
        * ~40 Azure-specific issues resolved (region/endpoint auth, connector bugs, sample fixes).
        * Expanded test matrix in CI to include Azure endpoint mocking and cross-region validation.
        
        ### Other Models Support
        
        Other major model providers have also significantly invested in enhancing their LangChain4j integrations.
        
        * **Google Vertex AI**: Support added for Vertex Generative Models (text + multimodal), streaming responses for chat-style models, first-class integration for Vertex embeddings, and improved examples demonstrating multi-turn chat with Vertex. The Vertex adapter also supports model selection and per-request security credentials suitable for GCP environments.
        * **Amazon Bedrock**: Introduced a unified Bedrock provider adapter with LLM/Embedding/Streaming support, credential management via the AWS SDK and role chaining, and examples showing Bedrock as a drop-in model endpoint for both production and local testing. The Bedrock integration supports model aliases and per-request provider selection.
        * **OpenAI**: Updated adapter to align with the newer OpenAI Responses/Streaming API and function-calling patterns. Improvements include delta streaming handlers, more accurate token-usage accounting, and optional automatic fallback to chat-completions compatibility mode for older integrations.
        
        Breaking changes and migration notes:
        * A notable public API stabilization was done to the core LLM client layer: request/response DTOs were consolidated and some callback interfaces were standardized. Most applications will only need small import or interface updates; a migration guide (included in the v1.4 → v1.5 release notes) lists the exact steps and automated refactor snippets.
        * VectorStore interface changes: the VectorStore contract tightened method signatures for consistency; migration helpers are included to convert older implementations—plan for a small integration pass when upgrading.
        
        Performance improvements and operational notes:
        * End-to-end benchmarks on streaming chat scenarios show up to 2x throughput and 20–40% lower memory usage in typical server-side workloads after upgrading to v1.5.
        * Connection pooling, request batching, and reduced object allocations were the primary drivers for these gains.
        * Observability is improved with Micrometer integration: you can emit LLM call counts, streaming throughput, and per-provider latency histograms to your metrics backend.
        
        Community impact (summary metrics from v1.2 → v1.5):
        * ≈180 merged PRs across the period, with contributor counts growing 35% quarter-over-quarter.
        * ~420 issues touched/closed (bugfixes, provider work, documentation), with median PR merge time improving from ~7 days to ~3 days thanks to triage improvements.
        * Extensive documentation and example additions (Azure, Bedrock, Vertex AI, and best-practices for production deployments).
        
        What to watch for next:
        * Continued polishing of Bedrock and Vertex adapters (more streaming/per-request controls).
        * Expanded production examples for multi-region deployments and autoscaling.
        * More tooling around model-cost-aware routing, automated index rebalancing, and richer provenance metadata for RAG pipelines.
        
        If you’re planning an upgrade from v1.2 → v1.5: review the breaking change notes in the v1.4→v1.5 migration guide, run the provided migration helpers for VectorStore and LLM client adapters, and test streaming handlers for any provider-specific behavior changes.
        """;

    private static final String REFERENCE_SECTION = """
        ## References
        
        Welcome to the curated list of resources designed to help you fully leverage the potential of LangChain4j 1.5. Whether you're a seasoned Java developer or new to LangChain4j, this compilation of essential links will guide you through installation, integration, and practical usage, especially how to work with language models and AI services, including Azure integration.
        
        ### Core LangChain4j Resources
        
        * [LangChain4j GitHub Repository](https://github.com/Hopding/LangChain4j)
          - Explore the heart of LangChain4j where you can find source code, contribute, or report issues.\s
        * [Official LangChain4j Documentation](https://docs.langchain4j.dev/version/1.5/)
          - Access comprehensive guides, API references, and tutorials specific to version 1.5.
        * [LangChain4j Blog](https://blog.langchain4j.dev/)
          - Stay updated with the latest news, tutorials, and deep dives into new features and updates.
        
        ### Community and Support
        
        * [LangChain4j on Stack Overflow](https://stackoverflow.com/questions/tagged/langchain4j)
          - Engage with the community by asking questions or providing insights on various development topics.
        * [LangChain4j Discord Channel](https://discord.gg/langchain4j)
          - Join our vibrant community for real-time interaction, support, and discussion with fellow developers.
        
        ### Azure Integration Resources
        
        * [Integrating LangChain4j with Azure OpenAI](https://docs.langchain4j.dev/azure/openai)
          - Detailed steps and best practices for setting up and integrating LangChain4j with Azure OpenAI services.
        * [Azure AI Services Guide](https://learn.microsoft.com/en-us/azure/ai-services/)
          - Explore the wide array of AI services offered by Azure to expand your projects.
        
        ### Integration with Other Major Cloud Providers
        
        * [Using LangChain4j with AWS OpenAI](https://docs.langchain4j.dev/aws/openai)
          - Learn how to utilize LangChain4j in conjunction with AWS services for a robust cloud experience.
        * [LangChain4j and Google Cloud AI documentation](https://docs.langchain4j.dev/google/cloud/ai)
          - Comprehensive guides to integrating LangChain4j with Google Cloud AI offerings.
        
        ### Learning Materials and Tutorials
        
        * [LangChain4j Tutorials for Beginners](https://tutorials.langchain4j.dev/intro/)
          - Step-by-step tutorials to get you started, from setup to advanced use cases.
        * [Developing with LangChain4j in Java](https://medium.com/@langchain4j/dev/working-with-langchain4j)
          - Articles and case studies from developers on building real-world applications with LangChain4j.
        
        This collection of resources is tailored to make your journey with LangChain4j 1.5 as smooth and productive as possible. By exploring these links, you can enhance your knowledge, gain practical development insights, and explore new possibilities with your Java language projects. Happy coding!
        """;

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
