package ai.agentic.newslettergen.reference;

import dev.langchain4j.agentic.Agent;
import dev.langchain4j.service.V;

public interface ReferenceAgent {

    @Agent(outputName = "referenceSection")
    String write(@V("version") String langchain4jVersion);
}
