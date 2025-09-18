package ai.agentic.newslettergen.statistics;

import static ai.agentic.newslettergen.statistics.StatisticsAgent.statisticsSectionModel;
import dev.langchain4j.agentic.AgenticServices;

public class WriteStatisticsSection {

    public static void main(String[] args) {

        StatisticsAgent statisticsSectionWriter = AgenticServices.agentBuilder(StatisticsAgent.class)
            .chatModel(statisticsSectionModel())
            .outputName("newsletter")
            .build();

        String newsletter = statisticsSectionWriter.write("1.5");

        System.out.println(newsletter);
    }
}
