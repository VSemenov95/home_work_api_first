package tests;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.is;

public class StatusTests extends TestBase {
    @Test
    public void totalAmountTest() {
        get("/status")
                .then()
                .body("total", is(5));
    }

    @Test
    public void totalAmountTest_withResponseLogs() {
        get("/status")
                .then()
                .log().all()
                .body("total", is(5));
    }

    @Test
    public void totalAmountTest_withAllLogs() {
        given()
                .log().all()
                .when()
                .get("/status")
                .then()
                .log().all()
                .body("total", is(5));
    }

    @Test
    public void status200Test() {
        given()
                .log().all()
                .when()
                .get("/status")
                .then()
                .log().all()
                .statusCode(200);
    }

    @Test
    public void browserVersionTest() {
        given()
                .log().all()
                .when()
                .get("/status")
                .then()
                .log().all()
                .statusCode(200);
    }

    @Test
    public void requiredKeysTest() {
        given()
                .log().all()
                .when()
                .get("/status")
                .then()
                .log().all()
                .statusCode(200)
                .body("", hasKey("total"))
                .body("", hasKey("used"))
                .body("", hasKey("queued"))
                .body("", hasKey("pending"))
                .body("", hasKey("browsers"));
    }

    @Test
    public void chromeVersionsTest() {
        given()
                .log().all()
                .when()
                .get("/status")
                .then()
                .log().all()
                .statusCode(200)
                .body("browsers.chrome", hasKey("127.0"))
                .body("browsers.chrome", hasKey("128.0"));
    }

    @Test
    public void statusSchemaTest() {
        given()
                .log().all()
                .when()
                .get("/status")
                .then()
                .log().all()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("schemas/status_response_schema.json"));
    }

    @Test
    public void bestTotalAmountTest() {
        given()
                .log().all()
                .when()
                .get("/status")
                .then()
                .log().all()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("schemas/status_response_schema.json"))
                .body("total", is(5));
    }
}