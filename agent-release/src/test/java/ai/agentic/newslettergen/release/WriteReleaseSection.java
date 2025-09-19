package ai.agentic.newslettergen.release;

import static ai.agentic.newslettergen.release.ReleaseSectionWriter.releaseSectionModel;
import dev.langchain4j.agentic.AgenticServices;

public class WriteReleaseSection {

    public static void main(String[] args) {

        ReleaseSectionWriter releaseSectionWriter = AgenticServices.agentBuilder(ReleaseSectionWriter.class)
            .chatModel(releaseSectionModel())
            .outputName("newsletter")
            .build();

        String newsletterSection = releaseSectionWriter.write("1.2", "1.5");

        System.out.println(newsletterSection);
    }
}
