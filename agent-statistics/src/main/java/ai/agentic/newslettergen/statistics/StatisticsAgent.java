package ai.agentic.newslettergen.statistics;

import dev.langchain4j.agentic.Agent;
import dev.langchain4j.service.V;

public interface StatisticsAgent {

    @Agent(outputName = "statisticsSection", description = "Collects and analyzes GitHub repository metrics including stars, forks, contributors, issues, and pull requests to provide quantitative insights into LangChain4j project health and community engagement")
    String write(@V("toLangchain4jVersion") String toLangchain4jVersion);
}
