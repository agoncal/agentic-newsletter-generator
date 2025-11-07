package ai.agentic.newslettergen.web;

import ai.agentic.newslettergen.codesample.CodeSampleSectionWriter;
import ai.agentic.newslettergen.editor.NewsletterEditor;
import ai.agentic.newslettergen.reference.ReferenceSectionWriter;
import ai.agentic.newslettergen.release.ReleaseSectionWriter;
import ai.agentic.newslettergen.statistics.StatisticsSectionWriter;
import dev.langchain4j.agentic.AgenticServices;
import dev.langchain4j.agentic.UntypedAgent;
import dev.langchain4j.service.Result;
import io.quarkiverse.renarde.Controller;
import io.quarkus.qute.CheckedTemplate;
import io.quarkus.qute.TemplateInstance;
import jakarta.transaction.Transactional;
import static jakarta.transaction.Transactional.TxType.NOT_SUPPORTED;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import org.jboss.logging.Logger;
import org.jboss.resteasy.reactive.RestForm;

import java.util.Map;

public class Application extends Controller {

    private static final Logger LOG = Logger.getLogger(Application.class);

    @CheckedTemplate(requireTypeSafeExpressions = false)
    static class Templates {
        public static native TemplateInstance form(String fromLangchain4jVersion, String toLangchain4jVersion);

        public static native TemplateInstance result(String newsletter, String fromLangchain4jVersion, String toLangchain4jVersion);
    }

    @GET
    @Path("/")
    public TemplateInstance index() {
        LOG.info("Entering form()");
        return Templates.form(null, null);
    }

    @GET
    @Path("/newsletter")
    public TemplateInstance form() {
        LOG.info("Entering form()");
        return Templates.form(null, null);
    }

    @POST
    @Path("/newsletter/generate")
    @Transactional(NOT_SUPPORTED)
    public TemplateInstance generate(
        @RestForm String fromLangchain4jVersion,
        @RestForm String toLangchain4jVersion) {

        LOG.infof("Generating newsletter from version %s to %s", fromLangchain4jVersion, toLangchain4jVersion);

        try {
            // Create input parameters
            Map<String, Object> input = Map.of(
                "fromLangchain4jVersion", fromLangchain4jVersion,
                "toLangchain4jVersion", toLangchain4jVersion
            );

            // Build the agent sequence
            UntypedAgent newsletterGenerator = AgenticServices
                .sequenceBuilder()
                .subAgents(
                    CodeSampleSectionWriter.class,
                    ReleaseSectionWriter.class,
                    ReferenceSectionWriter.class,
                    StatisticsSectionWriter.class,
                    NewsletterEditor.class
                )
                .outputKey("newsletter")
                .build();

            // Execute the workflow
            Result<String> newsletter = (Result<String>) newsletterGenerator.invoke(input);

            LOG.info("Newsletter generated successfully");
            LOG.info(newsletter);
            LOG.info("Newsletter content:");
            LOG.info(newsletter.content());

            return Templates.result(newsletter.content(), fromLangchain4jVersion, toLangchain4jVersion);

        } catch (Exception e) {
            LOG.error("Error generating newsletter", e);
            throw new RuntimeException("Failed to generate newsletter: " + e.getMessage(), e);
        }
    }
}
