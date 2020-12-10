package day02;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;


import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.* ;

public class SingleSpartanTest {

    @BeforeAll
    public static void setUp(){
        baseURI = "http://34.207.119.202:8000";
        basePath = "/api";

    }

    @AfterAll
    public static void tearDown(){
        reset();
    }

    @DisplayName("Testing GET/spartan/{id} endpoint")
    @Test
    public void test1Spartan(){
        // I want to get json result out
        // when I send GET request to /spartans/{id} endpoint
        // and expecting 200 status code
        given()
                .accept(ContentType.JSON).
         when()
                 .get("/spartans/100").
         then()
                .statusCode(is(200))
                .contentType(ContentType.JSON)
         ;


        // I want to make obvious
        // the value 100 is path variable|params
        // the uniquely identify the resource

        // this will be the whole request URL this test
        //http://34.207.119.202:8000/api/spartans/100

        given()
                .accept(ContentType.JSON)
                .pathParam("id", 100).
        when()
                .get("/spartans/{id}").
        then()
                .assertThat()
                .statusCode(is(200))
                .contentType(ContentType.JSON)
        ;

        // this is the easies one, same result
        given()
                .accept(ContentType.JSON).
        when()
                .get("spartans/{id}", 100).
        then()
                .assertThat()
                .statusCode(is(200))
                .contentType(ContentType.JSON)
        ;

    }

    @DisplayName("Testing GET/spartan/{id} endpoint")
    @Test
    public void test1SpartanPayLoad(){
        /*
        {
            "id": 100,
            "name": "Terence",
            "gender": "Male",
            "phone": 1311814806
         }
         */
        given()
                .accept(ContentType.JSON).
        when()
                .get("spartans/{id}", 100).
        then()
                .assertThat()
                .statusCode(is(200))
                .contentType(ContentType.JSON)
                .body("id", is(100) )
                .body("name", equalTo("Terence"))
                .body("gender",is(equalTo("Male")))
                .body("phone",equalTo(1311814806))
        ;

    }



}
