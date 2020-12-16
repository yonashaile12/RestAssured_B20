package practice;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.reset;
import static org.hamcrest.Matchers.is;

public class Height {
    //private static Map<String, Object> payLoadMap;
/*
Using Star War API ,
Send a request to https://swapi.dev/api/people
Extract the field with the name height of all the people
Print the average height on the console (edited)
:alphabet-white-r
Follow up question :
Also capture all the hair_color of people and print only unique ones excluding the ones without hair ("N\A" or "none") (edited)
Above questions does not ask for an assertion ,
it's checking your knowledge on rest assured and jsonpath and java collection.
 */
    @DisplayName("Test /GET spartan")
    @BeforeAll
    public static void setUp(){
        RestAssured.baseURI = "https://swapi.dev/api/";
    }

    @AfterAll
    public static void tearDown(){
        reset();
    }

    @Test
    public void testHeight(){
        given()
                .contentType(ContentType.JSON)
                .log().all().
        when()
                .get("resource/schema").
        then()
                .log().all()
                .assertThat()
                .statusCode(is(200))
                .contentType(ContentType.JSON)
                //.body("height", is(payLoadMap.get("height"))) /api/<resource>/schema
                .extract()
                .jsonPath()
                .getInt("height")
        ;


    }

}
