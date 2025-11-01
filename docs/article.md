# Building Agents with LangChain4j

## What Are Agents?

An agent is a system that combines several capabilities to perform complex tasks autonomously. At its core, an agent merges a **system prompt**, a **user prompt**, a **model**, **memory**, retrieval-augmented generation (**RAG**), **tools**, and the Model Context Protocol (**MCP**) into a cohesive system. Think of an agent like a knowledgeable colleague who doesn't just answer questions—they think through problems, gather information from various sources, use external tools, and refine their approach based on feedback. 

## Agents in LangChain4j

LangChain4j provides the `langchain4j-agentic` module to build agentic systems. Agents can be built using two approaches:

- **Programmatic approach**: Build agents using fluent builders with `AgenticServices.agentBuilder()`, explicitly configuring each aspect (chat model, tools, memory, etc.)
- **Declarative approach**: Define agents as interfaces with annotations that encapsulate all configuration within the interface itself

This article focuses on the **declarative approach**, which offers a key advantage: it encapsulates the complete agent behavior—prompts, model configuration, tools, memory, and callbacks—into a single interface that can be packaged in a separate JAR file. This makes agents highly portable and reusable across different applications.

An agent in LangChain4j is defined as an interface with a single method annotated with `@Agent`. LangChain4j provides rich annotations for declarative agent configuration:

| Annotation                        | Purpose                                                                                                                                                              |
|-----------------------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **`@Agent`**                      | Marks the method as an agent. Includes `outputKey` (where results are stored in AgenticScope) and `description` (what this agent does—crucial for supervisor agents) |
| **`@UserMessage`**                | Defines the prompt template with placeholders like `{{variable}}`                                                                                                    |
| **`@SystemMessage`**              | Defines the system-level instructions that set the agent's role and behavior constraints                                                                             |
| **`@ChatModelSupplier`**          | Provides the LLM configuration for this agent                                                                                                                        |
| **`@ToolProviderSupplier`**       | Provides external tools (e.g., MCP servers, REST APIs)                                                                                                               |
| **`@ChatMemoryProviderSupplier`** | Provides chat memory for stateful conversations                                                                                                                      |
| **`@ContentRetrieverSupplier`**   | Provides content retrieval for RAG patterns                                                                                                                          |
| **`@RetrievalAugmentorSupplier`** | Configures the retrieval augmentation strategy                                                                                                                       |
| **`@BeforeAgentInvocation`**      | Callback invoked before agent execution                                                                                                                              |
| **`@AfterAgentInvocation`**       | Callback invoked after agent completion                                                                                                                              |

## Building Declarative Agents

A declarative agent is defined entirely through its interface annotations. All supplier methods (`@ChatModelSupplier`, `@ToolProviderSupplier`, etc.) must be `static` and typically take no arguments (except `@ChatMemoryProviderSupplier` which requires a memory ID parameter). This approach keeps all agent configuration in one place, making it easy to understand, test, and deploy.

Here's a complete example showing all available configuration options:

```java
public interface ReleaseSectionWriter {

    @SystemMessage("""
        You are a specialized release analysis agent.
        """)
    @UserMessage("""
        Analyze release notes from version {{fromLangchain4jVersion}}
        to {{toLangchain4jVersion}} and create a summary.
        """)
    @Agent(
        outputKey = "releaseSection",
        description = "Analyzes and summarizes software releases"
    )
    Result<String> write(@V("fromLangchain4jVersion") String fromVersion,
                         @V("toLangchain4jVersion") String toVersion);

    @ChatModelSupplier
    static ChatModel chatModel() {
        // Configure Chat Model
    }

    @ToolProviderSupplier
    static ToolProvider toolProvider() {
        // Configure MCP or other external tools
    }

    @ChatMemoryProviderSupplier
    static ChatMemory chatMemory(Object memoryId) {
        // Configure Memory
    }

    @RetrievalAugmentorSupplier
    static RetrievalAugmentor retrievalAugmentor() {
        // Configure RAG
    }

    @BeforeAgentInvocation
    static void beforeInvocation(AgentRequest request) {
        System.out.println("Starting agent: " + request.agentName());
    }

    @AfterAgentInvocation
    static void afterInvocation(AgentResponse response) {
        System.out.println("Completed: " + response.output());
    }
}
```

### Invoking Declarative Agents

When using declarative annotations, agent building is automatic:

```java
// Create the agent (configuration is pulled from annotations)
ReleaseSectionWriter releaseWriter = AgenticServices
    .agentBuilder(ReleaseSectionWriter.class)
    .build();

// Invoke the agent
Result<String> result = releaseWriter.write("1.2", "1.5");
String releaseSection = result.content();

// Access metadata
System.out.println("Tokens used: " + result.tokenUsage());
```

## Orchestrating Multiple Agents

The real power of agents emerges when you combine multiple agents into a workflow. LangChain4j provides several orchestration patterns. For our newsletter example, we'll use a **sequential workflow**—where agents execute one after another, passing results between them.  The actual newsletter generator orchestrates five specialized agents in sequence. Here's the real implementation:

```java
public class NewsletterGenerator {

    public static void main(String[] args) {

        Map<String, Object> input = Map.of(
            "fromLangchain4jVersion", "1.2",
            "toLangchain4jVersion", "1.5"
        );

        UntypedAgent newsletterGenerator = AgenticServices
            .sequenceBuilder()
            .subAgents(CodeSampleSectionWriter.class,
                ReleaseSectionWriter.class,
                ReferenceSectionWriter.class,
                StatisticsSectionWriter.class,
                NewsletterEditor.class)
            .outputKey("newsletter")
            .build();

        String newsletter = (String) newsletterGenerator.invoke(input);

        System.out.println(newsletter);
    }
}
```

Here's what happens:

1. You create input parameters in a `Map<String, Object>` with version information.
2. LangChain4j creates an `AgenticScope`—a shared data store for all agents in the workflow.
3. The `CodeSampleSectionWriter` agent executes first, generating code examples and storing its result under `"codeSampleSection"`.
4. The `ReleaseSectionWriter` agent executes next, analyzing releases using GitHub MCP tools and storing under `"releaseSection"`.
5. The `ReferenceSectionWriter` agent gathers documentation links and stores under `"referenceSection"`.
6. The `StatisticsSectionWriter` agent collects GitHub statistics using MCP tools and stores under `"statisticsSection"`.
7. The `NewsletterEditor` agent executes last, reading all previous sections from the scope and compiling them into the final newsletter.
8. The final newsletter is returned as a string.

Notice that we pass agent **classes** (like `CodeSampleSectionWriter.class`) to the sequence builder, not instances. LangChain4j builds and configures each agent automatically using their `@ChatModelSupplier` and `@ToolProviderSupplier` methods.

### Understanding AgenticScope

The `AgenticScope` is the glue that connects agents. It's a shared map of variables that agents read from and write to. Here's how it works in the newsletter generator:

1. **Initial Input**: The `Map<String, Object>` input is loaded into the scope with keys like `"fromLangchain4jVersion"` and `"toLangchain4jVersion"`.

2. **Agent Reads**: When an agent method has parameters like `@V("toLangchain4jVersion") String toLangchain4jVersion`, LangChain4j reads that value from the scope.

3. **Agent Writes**: When an agent completes, its result is stored in the scope under its `outputKey`. For example, `CodeSampleSectionWriter` stores its output under `"codeSampleSection"`.

4. **Final Compilation**: The `NewsletterEditor` agent can access all previous outputs via template variables in its `@UserMessage` prompt:
   ```java
   @UserMessage("""
       Compile the following sections into a complete newsletter:

       {{codeSampleSection}}
       {{releaseSection}}
       {{referenceSection}}
       {{statisticsSection}}
       """)
   ```

This design allows agents to collaborate without direct knowledge of each other—they simply consume and produce data in a shared workspace. Each agent is independently testable and can be developed in isolation.

### Workflow Patterns

Beyond sequential workflows, LangChain4j supports:

- **Loop workflows**: Invoke an agent repeatedly until a condition is met (e.g., iteratively refining a newsletter until quality meets a threshold).
- **Parallel workflows**: Execute multiple agents simultaneously (e.g., gathering release notes and analysing dependencies in parallel).
- **Conditional workflows**: Route to different agents based on previous results (e.g., different newsletter formats for major vs. minor releases).
- **Pure agentic workflows**: Let a supervisor agent autonomously decide which agent to invoke next, without predetermined steps.

## Conclusion

Agents transform how you build AI applications. Instead of stringing together LLM calls, you define focused agents that specialise in specific tasks, then orchestrate them into workflows that solve complex problems.

With LangChain4j's agent framework, you get:

- **Simplicity**: Agents are just Java interfaces with annotations.
- **Composability**: Combine agents into workflows using builders—no complex wiring required.
- **Flexibility**: Mix sequential, parallel, conditional, and autonomous patterns.
- **Observability**: Track what agents do through callbacks and logging.
- **Safety**: Built-in error handling and recovery mechanisms.

The newsletter generator example demonstrates these concepts in practice—five focused agents collaborating to produce a polished output. Each agent has a specific role: generating code samples, analyzing releases, gathering references, collecting statistics, and editing the final newsletter. As your needs grow, you can add more agents (a distribution agent to send emails, a quality assurance agent to verify content) and compose them with additional workflow patterns.

Start by building a single agent for a focused task. Once you're comfortable, orchestrate multiple agents into workflows. You'll find that agents offer a powerful, intuitive way to build sophisticated AI applications in Java.