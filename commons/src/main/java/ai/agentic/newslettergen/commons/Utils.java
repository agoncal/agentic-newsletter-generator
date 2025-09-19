package ai.agentic.newslettergen.commons;

import dev.langchain4j.service.Result;

public class Utils {

    private static final String RED = "\u001B[31m";
    private static final String ORANGE = "\u001B[33m";
    private static final String GREEN = "\u001B[32m";
    private static final String RESET = "\u001B[0m";

    public static void sout(Result<String> result) {
        System.out.println("\n" + RED + "Tokens: Input (" + result.tokenUsage().inputTokenCount() + "), Output (" + result.tokenUsage().outputTokenCount() + "), Total (" + result.tokenUsage().totalTokenCount() + ")" + RESET);
        System.out.println(ORANGE + "Message: (" + result.content() + RESET);
    }
}
