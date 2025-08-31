package ai.agentic.newslettergen.statistics;

import dev.langchain4j.agentic.Agent;
import dev.langchain4j.service.V;

public interface StatisticsAgent {

    @Agent("blah blah blah")
    String foo(@V("topic") String topic);
}
