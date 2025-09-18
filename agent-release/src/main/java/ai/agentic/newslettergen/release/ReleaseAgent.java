package ai.agentic.newslettergen.release;

import dev.langchain4j.agentic.Agent;
import dev.langchain4j.service.V;

public interface ReleaseAgent {

    @Agent(outputName = "releaseSection", description = "Analyzes and summarizes LangChain4j software releases including version updates, new features, bug fixes, breaking changes, and migration guidance from release notes and changelogs")
    String write(@V("fromLangchain4jVersion") String fromLangchain4jVersion,
                 @V("toLangchain4jVersion") String toLangchain4jVersion);
}
