package ai.agentic.newslettergen.reference;

import dev.langchain4j.agentic.Agent;
import dev.langchain4j.service.V;

public interface ReferenceAgent {

    @Agent(outputName = "referenceSection", description = "Curates comprehensive reference documentation, tutorials, learning resources, and community links related to LangChain4j to help newsletter readers discover valuable educational materials")
    String write(@V("toLangchain4jVersion") String toLangchain4jVersion);
}
