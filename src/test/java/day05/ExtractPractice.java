package day05;

import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import io.restassured.http.ContentType;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import testBase.Spartan_TestBase;

import java.io.File;
import java.util.List;


import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.* ;

public class ExtractPractice extends Spartan_TestBase {

    /*
        extract() method of RestAssured
        enable you to extract data after validation
        in then section of the method chaining
    */
    @DisplayName("Test GET /api/spartan/search with Basic auth")
    @Test
    public void testSearchAddExtractOneData(){

        // search for nameContains : a , gender Female
        //verify status code is 200
        // extract jsonPath object after validation
        // use that jsonPath object to get the list of all results
        // and get the numberOfElements field value
        //compare those 2
        JsonPath jp = given()
                            .auth().basic("admin", "admin")
                            .queryParam("nameContains", "a")
                            .queryParam("gender", "Female").
                       when()
                            .get("/spartans/search").
                       then()
                            .assertThat()
                            .statusCode(is(200))
                            .extract()
                            .jsonPath();
        // get the list of names in string
        List<String> allNames = jp.getList("content.name");
        System.out.println("allNames = " + allNames);

        int numOfElements = jp.getInt("numberOfElements");
        System.out.println("numOfElements = " + numOfElements);

        //assertThat(numOfElements, equalTo( allNames.size() ));
        assertThat(allNames.size(), equalTo( numOfElements ));

        //using hamcrest matcher collection support for asserting the list size
        assertThat(allNames, hasSize(numOfElements));




    }


}
