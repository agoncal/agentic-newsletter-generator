package ai.agentic.newslettergen.statistics;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
class StatisticsAgentTest {
    @Test
    void testHelloEndpoint() {
        given()
          .when().get("/statistics")
          .then()
             .statusCode(200)
             .body(is("Hello from Quarkus REST"));
    }

}