package ai.agentic.newslettergen.workflow;

import ai.agentic.newslettergen.codesample.CodeSampleSectionWriter;
import static ai.agentic.newslettergen.codesample.CodeSampleSectionWriter.codeSampleSectionModel;
import dev.langchain4j.agentic.AgenticServices;

public class NewsletterGenerator {

    public static void main(String[] args) {

        CodeSampleSectionWriter codeSampleSectionWriter = AgenticServices.agentBuilder(CodeSampleSectionWriter.class)
            .chatModel(codeSampleSectionModel())
            .outputName("newsletter")
            .build();

//        ReferenceAgent referenceAgent = AgenticServices
//            .agentBuilder(ReferenceAgent.class)
//            .outputName("newsletter")
//            .build();
//
//        ReleaseAgent releaseAgent = AgenticServices
//            .agentBuilder(ReleaseAgent.class)
//            .outputName("newsletter")
//            .build();
//
//        StatisticsAgent statisticsAgent = AgenticServices
//            .agentBuilder(StatisticsAgent.class)
//            .outputName("newsletter")
//            .build();

        NewsletterWorkflow newsletterWorkflow = AgenticServices
            .sequenceBuilder(NewsletterWorkflow.class)
            .subAgents(codeSampleSectionWriter/*, releaseAgent, referenceAgent, statisticsAgent*/)
            .outputName("newsletter")
            .build();

        String newsletter = newsletterWorkflow.foo("");

        System.out.println(newsletter);
    }
}
