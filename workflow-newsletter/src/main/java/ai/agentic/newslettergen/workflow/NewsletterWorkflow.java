package ai.agentic.newslettergen.workflow;

import dev.langchain4j.agentic.Agent;
import dev.langchain4j.service.V;

public interface NewsletterWorkflow {

    @Agent("blah blah blah")
    String foo(@V("topic") String topic);
}
