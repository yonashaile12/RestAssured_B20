package day10;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import testBase.SpartanAdminTestBase;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.registerParser;
import static org.hamcrest.Matchers.is;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class JsonSchemaValidation extends SpartanAdminTestBase {


    @DisplayName("Testing the structure of GET /api/spartans/{id} response")
    @Test
    public void testGetSingleSchema(){
        given()
                .spec(adminReqSpec)
                .pathParam("id", 34).
        when()
                .get("/spartans/{id}").
        then()
                .log().body()
                .statusCode(is(200))
                .body(matchesJsonSchemaInClasspath("singleSpartanSchema.json"))
        ;


    }
}
