package ai.agentic.newslettergen.statistics;

import static ai.agentic.newslettergen.statistics.StatisticsSectionWriter.statisticsSectionModel;
import dev.langchain4j.agentic.AgenticServices;

public class WriteStatisticsSection {

    public static void main(String[] args) {

        StatisticsSectionWriter statisticsSectionWriter = AgenticServices.agentBuilder(StatisticsSectionWriter.class)
            .chatModel(statisticsSectionModel())
            .outputName("newsletter")
            .build();

        String newsletterSection = statisticsSectionWriter.write("1.5");

        System.out.println(newsletterSection);
    }
}
