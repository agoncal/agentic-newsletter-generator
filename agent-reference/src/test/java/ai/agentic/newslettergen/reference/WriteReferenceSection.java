package ai.agentic.newslettergen.reference;

import static ai.agentic.newslettergen.reference.ReferenceSectionWriter.referenceSectionModel;
import dev.langchain4j.agentic.AgenticServices;

public class WriteReferenceSection {

    public static void main(String[] args) {

        ReferenceSectionWriter referenceSectionWriter = AgenticServices.agentBuilder(ReferenceSectionWriter.class)
            .chatModel(referenceSectionModel())
            .outputName("newsletter")
            .build();

        String newsletterSection = referenceSectionWriter.write("1.5");

        System.out.println(newsletterSection);
    }
}
