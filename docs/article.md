# Building Agents with LangChain4j

## What Are Agents?

An agent is a system that combines several capabilities to perform complex tasks autonomously. At its core, an agent merges a system prompt, a user prompt, a language model, memory, retrieval-augmented generation (RAG), tools, and the Model Context Protocol (MCP) into a cohesive system. Rather than making a single LLM call and accepting the response, agents reason through problems iteratively, breaking tasks into steps, invoking tools when needed, and adapting based on results.

Think of an agent like a knowledgeable colleague who doesn't just answer questions—they think through problems, gather information from various sources, use external tools, and refine their approach based on feedback. When you ask an agent to accomplish something, it observes the current state, reasons about what needs to happen next, takes action, and repeats this cycle until the task completes or it recognises completion is not possible.

The key difference between a simple LLM call and an agent is autonomy. With an LLM, you ask a question and get an answer. With an agent, you assign a task and the agent figures out the steps needed to accomplish it.

## Agents in LangChain4j

LangChain4j provides the `langchain4j-agentic` module (currently experimental) to build agentic systems. An agent in LangChain4j is defined as an interface with a single method annotated with `@Agent`. This simple design makes agents feel natural to Java developers.

Here's a minimal agent definition:

```java
public interface ReleaseSectionWriter {

    @UserMessage("""
            Analyze the following LangChain4j release notes and create a concise summary.
            Include version number, major features, bug fixes, and breaking changes.
            Release notes: {{releaseNotes}}
            """)
    @Agent(
        outputKey = "releaseSection",
        description = "Analyzes and summarizes LangChain4j software releases including "
            + "version updates, new features, bug fixes, breaking changes, and "
            + "migration guidance from release notes and changelogs"
    )
    String writeReleaseSection(@V("releaseNotes") String releaseNotes);
}
```

Notice the key annotations. The `@Agent` annotation marks this method as an agent and provides two important properties: `outputKey` (the name under which this agent's result will be stored) and `description` (what this agent does—crucial for orchestration). The `@V` annotation binds parameters to named variables that the agent can access.

To create an instance of this agent, you use `AgenticServices`:

```java
ReleaseSectionWriter releaseWriter = AgenticServices
    .agentBuilder(ReleaseSectionWriter.class)
    .chatModel(chatModel)
    .outputKey("releaseSection")
    .build();
```

## Building Agents

Building an agent involves three steps: defining the agent interface, creating the agent instance, and invoking it with input data.

### Step 1: Define the Agent Interface

Your agent interface specifies what it does through prompts and parameters:

```java
public interface CodeSampleSectionWriter {

    @UserMessage("""
            Based on the latest LangChain4j release features: {{features}},
            generate a practical Java code example that demonstrates how to use them.
            The example should be concise, focused, and show best practices.
            """)
    @Agent(
        outputKey = "codeSampleSection",
        description = "Generates a practical Java code example that demonstrates "
            + "the latest LangChain4j features, best practices, and real-world "
            + "usage patterns for newsletter readers"
    )
    String generateCodeSample(@V("features") String features);
}
```

### Step 2: Build the Agent

Create an agent instance using the agent builder:

```java
CodeSampleSectionWriter codeSampleWriter = AgenticServices
    .agentBuilder(CodeSampleSectionWriter.class)
    .chatModel(chatModel)
    .outputKey("codeSampleSection")
    .build();
```

Optionally, you can add memory, tools, or observability:

```java
CodeSampleSectionWriter codeSampleWriter = AgenticServices
    .agentBuilder(CodeSampleSectionWriter.class)
    .chatModel(chatModel)
    .outputKey("codeSampleSection")
    .beforeAgentInvocation(request -> 
        System.out.println("Generating code sample with features: " + request.inputs().get("features")))
    .afterAgentInvocation(response -> 
        System.out.println("Code sample generated successfully"))
    .build();
```

### Step 3: Invoke the Agent

Call your agent like you would any method:

```java
String releaseSection = releaseWriter.writeReleaseSection(releaseNotes);
String codeSample = codeSampleWriter.generateCodeSample(features);
```

## Orchestrating Multiple Agents

The real power of agents emerges when you combine multiple agents into a workflow. LangChain4j provides several orchestration patterns. For our newsletter example, we'll use a **sequential workflow**—where agents execute one after another, passing results between them.

### The Newsletter Generator Example

Imagine building an automated newsletter about LangChain4j releases. You need one agent to write about the release, another to create code samples, and a coordinator to orchestrate them.

First, define your orchestration interface:

```java
public interface NewsletterGenerator {

    @Agent
    String generateNewsletter(@V("releaseNotes") String releaseNotes, 
                              @V("features") String features);
}
```

Then orchestrate the agents into a sequence:

```java
ReleaseSectionWriter releaseWriter = AgenticServices
    .agentBuilder(ReleaseSectionWriter.class)
    .chatModel(chatModel)
    .outputKey("releaseSection")
    .build();

CodeSampleSectionWriter codeSampleWriter = AgenticServices
    .agentBuilder(CodeSampleSectionWriter.class)
    .chatModel(chatModel)
    .outputKey("codeSampleSection")
    .build();

NewsletterGenerator newsletterGenerator = AgenticServices
    .sequenceBuilder(NewsletterGenerator.class)
    .subAgents(releaseWriter, codeSampleWriter)
    .outputKey("newsletter")
    .output(agenticScope -> {
        String releaseSection = agenticScope.readState("releaseSection", "");
        String codeSampleSection = agenticScope.readState("codeSampleSection", "");
        
        return "# LangChain4j Newsletter\n\n"
            + "## Release Updates\n" + releaseSection + "\n\n"
            + "## Code Sample\n" + codeSampleSection;
    })
    .build();

String newsletter = newsletterGenerator.generateNewsletter(releaseNotes, features);
```

Here's what happens:

1. You invoke `generateNewsletter()` with release notes and features.
2. LangChain4j creates an `AgenticScope`—a shared data store for all agents in the workflow.
3. The `releaseWriter` agent executes first, analysing the release notes and storing its result under the key `"releaseSection"`.
4. The `codeSampleWriter` agent executes next, generating code examples and storing its result under the key `"codeSampleSection"`.
5. The `output` function combines both results into a formatted newsletter.

### Understanding AgenticScope

The `AgenticScope` is the glue that connects agents. It's a shared map of variables that agents read from and write to. When you invoke an agent with `@V("releaseNotes") String releaseNotes`, LangChain4j reads that value from the `AgenticScope`. When an agent completes, its `outputKey` determines which variable in the scope receives the result.

This design allows agents to collaborate without direct knowledge of each other—they simply consume and produce data in a shared workspace.

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

The newsletter generator example demonstrates these concepts in practice—multiple focused agents collaborating to produce a polished output. As your needs grow, you can add more agents (an editor agent for final polish, a distribution agent to send emails) and compose them with additional workflow patterns.

Start by building a single agent for a focused task. Once you're comfortable, orchestrate multiple agents into workflows. You'll find that agents offer a powerful, intuitive way to build sophisticated AI applications in Java.