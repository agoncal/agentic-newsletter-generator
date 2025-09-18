package ai.agentic.newslettergen.release;

import dev.langchain4j.agentic.Agent;
import dev.langchain4j.service.V;

public interface ReleaseAgent {

    @Agent(outputName = "releaseSection")
    String write(@V("fromLangchain4jVersion") String fromLangchain4jVersion,
                 @V("toLangchain4jVersion") String toLangchain4jVersion);
}
