package ai.agentic.newslettergen.workflow;

import ai.agentic.newslettergen.codesample.CodeSampleSectionWriter;
import dev.langchain4j.agentic.AgenticServices;

public class NewsletterGenerator {

    public static void main(String[] args) {

        CodeSampleSectionWriter codeSampleSectionWriter = AgenticServices
            .createAgenticSystem(CodeSampleSectionWriter.class);

        codeSampleSectionWriter.write("hi");

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
            .subAgents(codeSampleSectionWriter/*, referenceAgent, releaseAgent, statisticsAgent*/)
            .outputName("newsletter")
            .build();

        String newsletter = newsletterWorkflow.foo("");

        System.out.println(newsletter);
    }
}
