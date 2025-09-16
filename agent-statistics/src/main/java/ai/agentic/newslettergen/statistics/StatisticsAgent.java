package ai.agentic.newslettergen.statistics;

import dev.langchain4j.agentic.Agent;
import dev.langchain4j.service.V;

public interface StatisticsAgent {

    @Agent(outputName = "statisticsSection")
    String write(@V("version") String langchain4jVersion);
}
