package ai.agentic.newslettergen.workflow;

import ai.agentic.newslettergen.codesample.CodeSampleAgent;
import ai.agentic.newslettergen.reference.ReferenceAgent;
import ai.agentic.newslettergen.release.ReleaseAgent;
import ai.agentic.newslettergen.statistics.StatisticsAgent;
import dev.langchain4j.agentic.AgenticServices;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/workflow")
public class NewsletterResource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {

        CodeSampleAgent codeSampleAgent = AgenticServices
            .agentBuilder(CodeSampleAgent.class)
            .outputName("newsletter")
            .build();

        ReferenceAgent referenceAgent = AgenticServices
            .agentBuilder(ReferenceAgent.class)
            .outputName("newsletter")
            .build();

        ReleaseAgent releaseAgent = AgenticServices
            .agentBuilder(ReleaseAgent.class)
            .outputName("newsletter")
            .build();

        StatisticsAgent statisticsAgent = AgenticServices
            .agentBuilder(StatisticsAgent.class)
            .outputName("newsletter")
            .build();

        NewsletterWorkflow newsletterWorkflow = AgenticServices
            .sequenceBuilder(NewsletterWorkflow.class)
            .subAgents(codeSampleAgent, referenceAgent, releaseAgent, statisticsAgent)
            .outputName("newsletter")
            .build();

        String newsletter = newsletterWorkflow.foo("");

        return "Hello from Quarkus REST";
    }
}
