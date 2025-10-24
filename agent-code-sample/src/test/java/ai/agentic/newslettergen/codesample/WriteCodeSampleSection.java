package ai.agentic.newslettergen.codesample;

import static ai.agentic.newslettergen.codesample.CodeSampleSectionWriter.codeSampleSectionModel;
import static ai.agentic.newslettergen.commons.Utils.sout;
import dev.langchain4j.agentic.AgenticServices;
import dev.langchain4j.service.Result;

public class WriteCodeSampleSection {

    public static void main(String[] args) {

        CodeSampleSectionWriter codeSampleSectionWriter = AgenticServices.agentBuilder(CodeSampleSectionWriter.class)
            .chatModel(codeSampleSectionModel())
            .outputKey("newsletter")
            .build();

        Result<String> newsletterSection = codeSampleSectionWriter.write("1.5");

        sout(newsletterSection);
    }
}
