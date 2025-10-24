package ai.agentic.newslettergen.reference;

import static ai.agentic.newslettergen.commons.Utils.sout;
import static ai.agentic.newslettergen.reference.ReferenceSectionWriter.referenceSectionModel;
import dev.langchain4j.agentic.AgenticServices;
import dev.langchain4j.service.Result;

public class WriteReferenceSection {

    public static void main(String[] args) {

        ReferenceSectionWriter referenceSectionWriter = AgenticServices.agentBuilder(ReferenceSectionWriter.class)
            .chatModel(referenceSectionModel())
            .outputKey("newsletter")
            .build();

        Result<String> newsletterSection = referenceSectionWriter.write("1.5");

        sout(newsletterSection);
    }
}
