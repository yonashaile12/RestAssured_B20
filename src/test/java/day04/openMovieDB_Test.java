package day04;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.* ;


public class openMovieDB_Test {

    //http://www.omdbapi.com/?t=The Orville&api=3f49c7c6

    @BeforeAll
    public static void setUp(){
        baseURI = "http://www.omdbapi.com" ;
    }

    @AfterAll
    public static void tearDown(){
        reset();
    }

    @DisplayName("Test Search Movie or OpenMovieDB test")
    @Test
    public void testMovie(){

        given()
                .queryParam("apikey", "3f49c7c6")
                .queryParam("t", "The Orville").
        when()
                .get().prettyPeek().// our request url is already complete, do not need to add anything
        then()
                .statusCode(is(200))
                .contentType(ContentType.JSON)
                .body("Title", is("The Orville"))
                .body("Ratings[0].Source", is("Internet Movie Database"))
        ;

    }


    @DisplayName("Getting the log of request and response")
    @Test
    public void testSendingRequestAndGetTheLog(){

        given()
                .queryParam("apikey", "3f49c7c6")
                .queryParam("t", "John Wick")
                // logging the response should be in given section
                .log().all().
        when()
                .get().

        then()
                // looging the response should be in then section
//                .log().all()
//                .log().status()
//                .log().body()
                .log().ifValidationFails()
                .statusCode(is(200) )
                .body("Plot",containsString("ex-hit-man"))
                // second rating source is rooten tomatoes
                .body("Ratings[1].Source",is("Rotten Tomatoes"))
                ;

    }














}
