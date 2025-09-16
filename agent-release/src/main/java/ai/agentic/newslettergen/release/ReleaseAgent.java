package ai.agentic.newslettergen.release;

import dev.langchain4j.agentic.Agent;
import dev.langchain4j.service.V;

public interface ReleaseAgent {

    @Agent(outputName = "releaseSection")
    String write(@V("version") String langchain4jVersion);
}
