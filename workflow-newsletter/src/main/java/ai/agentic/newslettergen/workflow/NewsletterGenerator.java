package ai.agentic.newslettergen.workflow;

import ai.agentic.newslettergen.codesample.CodeSampleSectionWriter;
import static ai.agentic.newslettergen.codesample.CodeSampleSectionWriter.codeSampleSectionModel;
import ai.agentic.newslettergen.editor.NewsletterEditor;
import static ai.agentic.newslettergen.editor.NewsletterEditor.newsletterEditorModel;
import dev.langchain4j.agentic.AgenticServices;
import dev.langchain4j.agentic.UntypedAgent;

import java.util.Map;

public class NewsletterGenerator {

    public static void main(String[] args) {

        CodeSampleSectionWriter codeSampleSectionWriter = AgenticServices.agentBuilder(CodeSampleSectionWriter.class)
            .chatModel(codeSampleSectionModel())
            .build();

//        ReferenceSectionWriter referenceAgent = AgenticServices
//            .agentBuilder(ReferenceSectionWriter.class)
//            .outputName("newsletter")
//            .build();
//
//        ReleaseSectionWriter releaseAgent = AgenticServices
//            .agentBuilder(ReleaseSectionWriter.class)
//            .outputName("newsletter")
//            .build();
//
//        StatisticsSectionWriter statisticsAgent = AgenticServices
//            .agentBuilder(StatisticsSectionWriter.class)
//            .outputName("newsletter")
//            .build();

        NewsletterEditor newsletterEditor = AgenticServices.agentBuilder(NewsletterEditor.class)
            .chatModel(newsletterEditorModel())
            .build();

        Map<String, Object> input = Map.of(
            "fromLangchain4jVersion", "1.2",
            "toLangchain4jVersion", "1.5"
        );

        UntypedAgent newsletterGenerator = AgenticServices
            .sequenceBuilder()
            .subAgents(codeSampleSectionWriter, newsletterEditor/*, releaseAgent, referenceAgent, statisticsAgent*/)
            .outputName("newsletter")
            .build();

        String newsletter = (String) newsletterGenerator.invoke(input);

        System.out.println(newsletter);
    }
}
