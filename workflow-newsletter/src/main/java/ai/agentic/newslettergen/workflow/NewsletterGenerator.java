package ai.agentic.newslettergen.workflow;

import ai.agentic.newslettergen.codesample.CodeSampleSectionWriter;
import ai.agentic.newslettergen.editor.NewsletterEditor;
import ai.agentic.newslettergen.reference.ReferenceSectionWriter;
import ai.agentic.newslettergen.release.ReleaseSectionWriter;
import ai.agentic.newslettergen.statistics.StatisticsSectionWriter;
import dev.langchain4j.agentic.AgenticServices;
import dev.langchain4j.agentic.UntypedAgent;

import java.util.Map;

public class NewsletterGenerator {

    public static void main(String[] args) {

        Map<String, Object> input = Map.of(
            "fromLangchain4jVersion", "1.2",
            "toLangchain4jVersion", "1.5"
        );

        UntypedAgent newsletterGenerator = AgenticServices
            .sequenceBuilder()
            .subAgents(CodeSampleSectionWriter.class,
                ReleaseSectionWriter.class,
                ReferenceSectionWriter.class,
                StatisticsSectionWriter.class,
                NewsletterEditor.class)
            .outputKey("newsletter")
            .build();

        String newsletter = (String) newsletterGenerator.invoke(input);

        System.out.println(newsletter);
    }
}
