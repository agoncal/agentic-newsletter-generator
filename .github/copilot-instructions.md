# Copilot Instructions

## Build and Test Commands

```bash
# Build all modules
mvn clean compile

# Package all modules
mvn clean package

# Run all tests (unit + integration)
mvn clean verify

# Run unit tests for a single module
cd agent-statistics && mvn test

# Run integration tests for a single module
cd agent-statistics && mvn failsafe:integration-test

# Skip integration tests
mvn clean package -DskipITs

# Run a specific agent in Quarkus dev mode (hot reload)
cd agent-statistics && ./mvnw quarkus:dev

# Run the full newsletter workflow
cd newsletter-generator && mvn exec:java -Dexec.mainClass="ai.agentic.newslettergen.workflow.NewsletterGenerator"

# Build Docker image
mvn clean package -Dquarkus.container-image.build=true

# Native build
mvn package -Dnative
```

## Architecture

This is a multi-agent system that generates technology newsletters. It uses a multi-module Maven project where each agent is a self-contained Quarkus application with a dedicated Azure AI Foundry model deployment.

**Agent pipeline (sequential):**
1. `agent-code-sample` → generates Java code examples (output key: `codeSampleSection`)
2. `agent-release` → summarizes release notes (output key: `releaseSection`)
3. `agent-reference` → curates documentation/resources (output key: `referenceSection`)
4. `agent-statistics` → collects GitHub metrics via MCP (output key: `statisticsSection`)
5. `agent-newsletter-editor` → compiles all sections into the final newsletter

The `newsletter-generator` module orchestrates the pipeline using `AgenticServices.sequenceBuilder()` from LangChain4j Agentic. Each agent produces a named output that flows to subsequent agents. The `commons` module holds shared constants, environment variable loading (via dotenv-java), and model configuration.

**Key technologies:** Java 21, Quarkus 3.28.0, LangChain4j 1.8.0, LangChain4j Agentic, Azure AI Foundry, GitHub MCP Server (Docker stdio transport), Tinylog 2.7.0.

## Key Conventions

### Agent Definition Pattern

Agents are Java interfaces (not classes) with these annotations:
- `@UserMessage` — the agent's prompt template (supports `{{variable}}` placeholders)
- `@Agent(outputKey, name, description)` — marks the method as an agent invocation
- `@V("paramName")` — binds method parameters to prompt variables
- `@ChatModelSupplier` — static method returning the `ChatModel` (OpenAI-compatible via Azure)
- `@ToolProviderSupplier` — optional static method providing MCP tools (e.g., GitHub MCP Server)
- `@BeforeAgentInvocation` / `@AfterAgentInvocation` — lifecycle hooks
- Agent methods return `Result<String>`

### Environment Variables

Three required env vars, loaded from `.env` files or system environment via `Constants.java`:
- `AZURE_AI_FOUNDRY_KEY` — Azure AI API key
- `AZURE_AI_FOUNDRY_ENDPOINT` — Azure AI endpoint URL
- `GITHUB_PERSONAL_ACCESS_TOKEN` — for GitHub MCP Server

See `.env.example` for the template. The `Constants.java` class in `commons` searches for `.env` in both the current directory and parent directory.

### Model Configuration

Each agent has a dedicated Azure AI Foundry model deployment. Model names are hardcoded in `commons/Constants.java` (e.g., `agent-statistics-writer-model`). All agents use the `OpenAiChatModel` builder pointing at the Azure endpoint.

### Testing Individual Agents

Tests use `AgenticServices.agentBuilder(AgentInterface.class).build()` to instantiate and test a single agent in isolation, without the full workflow pipeline.

### Infrastructure

- `infrastructure/create-azure-ai-foundry-resources.sh` — provisions Azure resources
- `infrastructure/delete-azure-ai-foundry-resources.sh` — tears down Azure resources
- `infrastructure/docker-compose-*.yaml` — local Docker setup for the app and GitHub MCP Server
