package day03;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.* ;

public class GitHubRestApiTest {

    // Create a test for testing github rest api users/user endpoint

    @DisplayName("Test GitHub GET /users/{username}")
    @Test
    public void testGitHub(){

        given()
                .pathParam("username", "yonashaile12").
        when()
                .get("https://api.github.com/users/{username}").
        then()
                .assertThat()
                .statusCode(is(200))
                .contentType(ContentType.JSON)
                .header("server", "GitHub.com")
                .body("login", is("yonashaile12"))

        ;


    }



}
