package ai.agentic.newslettergen.codesample;

import dev.langchain4j.agentic.Agent;
import dev.langchain4j.service.V;

public interface CodeSampleAgent {

    @Agent("blah blah blah")
    String foo(@V("topic") String topic);
}
