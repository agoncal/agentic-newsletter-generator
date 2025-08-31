package ai.agentic.newslettergen.release;

import dev.langchain4j.agentic.Agent;
import dev.langchain4j.service.V;

public interface ReleaseAgent {

    @Agent("blah blah blah")
    String foo(@V("topic") String topic);
}
