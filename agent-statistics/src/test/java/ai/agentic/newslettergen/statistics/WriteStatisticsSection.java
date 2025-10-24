package ai.agentic.newslettergen.statistics;

import static ai.agentic.newslettergen.commons.Utils.sout;
import static ai.agentic.newslettergen.statistics.StatisticsSectionWriter.statisticsSectionMCP;
import static ai.agentic.newslettergen.statistics.StatisticsSectionWriter.statisticsSectionModel;
import dev.langchain4j.agentic.AgenticServices;
import dev.langchain4j.service.Result;

public class WriteStatisticsSection {

    public static void main(String[] args) {

        StatisticsSectionWriter statisticsSectionWriter = AgenticServices.agentBuilder(StatisticsSectionWriter.class)
            .chatModel(statisticsSectionModel())
            .toolProvider(statisticsSectionMCP())
            .outputKey("newsletter")
            .build();

        Result<String> newsletterSection = statisticsSectionWriter.write("1.5");

        sout(newsletterSection);
    }
}
