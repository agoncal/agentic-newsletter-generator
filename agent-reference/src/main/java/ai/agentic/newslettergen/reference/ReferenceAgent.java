package ai.agentic.newslettergen.reference;

import dev.langchain4j.agentic.Agent;
import dev.langchain4j.service.V;

public interface ReferenceAgent {

    @Agent("blah blah blah")
    String foo(@V("topic") String topic);
}
