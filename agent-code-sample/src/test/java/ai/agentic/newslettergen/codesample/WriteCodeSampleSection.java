package ai.agentic.newslettergen.codesample;

import static ai.agentic.newslettergen.codesample.CodeSampleSectionWriter.codeSampleSectionModel;
import dev.langchain4j.agentic.AgenticServices;

public class WriteCodeSampleSection {

    public static void main(String[] args) {

        CodeSampleSectionWriter codeSampleSectionWriter = AgenticServices.agentBuilder(CodeSampleSectionWriter.class)
            .chatModel(codeSampleSectionModel())
            .outputName("newsletter")
            .build();

        String newsletter = codeSampleSectionWriter.write("1.4");

        System.out.println(newsletter);
    }
}
