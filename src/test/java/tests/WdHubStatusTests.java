package tests;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.hasKey;

public class WdHubStatusTests extends TestBase {
    @Test
    public void statusTest() {
        given()
                .log().all()
                .auth().basic("user1", "1234")
                .when()
                .get("https://selenoid.autotests.cloud/wd/hub/status")
                .then()
                .log().all()
                .statusCode(200);
    }

    @Test
    public void unauthorizedStatusTest() {
        given()
                .log().all()
                .when()
                .get("/wd/hub/status")
                .then()
                .log().all()
                .statusCode(401);
    }

    @Test
    public void incorrectPasswordStatusTest() {
        given()
                .log().all()
                .auth().basic("user1", "test")
                .when()
                .get("/wd/hub/status")
                .then()
                .log().all()
                .statusCode(401);
    }

    @Test
    public void wdHubStatusSchemaTest() {
        given()
                .log().all()
                .auth().basic("user1", "1234")
                .when()
                .get("/wd/hub/status")
                .then()
                .log().all()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("schemas/wd_hub_status_response_schema.json"));;
    }

    @Test
    public void requiredKeysTest() {
        given()
                .log().all()
                .auth().basic("user1", "1234")
                .when()
                .get("/wd/hub/status")
                .then()
                .log().all()
                .statusCode(200)
                .body("value", hasKey("message"))
                .body("value", hasKey("ready"));
    }
}