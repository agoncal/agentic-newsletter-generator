package ai.agentic.newslettergen.release;

import static ai.agentic.newslettergen.commons.Utils.sout;
import static ai.agentic.newslettergen.release.ReleaseSectionWriter.releaseSectionMCP;
import static ai.agentic.newslettergen.release.ReleaseSectionWriter.releaseSectionModel;
import dev.langchain4j.agentic.AgenticServices;
import dev.langchain4j.service.Result;

public class WriteReleaseSection {

    public static void main(String[] args) {

        ReleaseSectionWriter releaseSectionWriter = AgenticServices.agentBuilder(ReleaseSectionWriter.class)
            .chatModel(releaseSectionModel())
            .toolProvider(releaseSectionMCP())
            .outputKey("newsletter")
            .build();

        Result<String> newsletterSection = releaseSectionWriter.write("1.2", "1.5");

        sout(newsletterSection);
    }
}
