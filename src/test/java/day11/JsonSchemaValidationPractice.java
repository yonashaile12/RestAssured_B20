package day11;

import Utility.SpartanUtil;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import testBase.SpartanAdminTestBase;

import java.io.File;

import static io.restassured.RestAssured.*;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class JsonSchemaValidationPractice extends SpartanAdminTestBase {

    @DisplayName("Testing GET /spartans endpoint response json structure")
    @Test
    public void testAllSpartanResponseSchema(){
        given()
                .spec(adminReqSpec).
        when()
                .get("/spartans").
        then()
                .assertThat()
                .body(matchesJsonSchemaInClasspath("allSpartansSchema.json")) ;

    }

    @DisplayName("Testing POST /spartans endpoint response structure")
    @Test
    public void testPostSpartanResponseSchema(){

        // We can also use matchesJsonSchema method if we want to provide full path for this file
        File schemaFile = new File("src/test/resources/postSuccessResponseSchema.json");
        given()
                .spec(adminReqSpec)
                .contentType(ContentType.JSON)
                .body(SpartanUtil.getRandomSpartanPOJO_Payload() ).
        when()
                .post("/spartans").
        then()
                //.body(matchesJsonSchemaInClasspath("postSuccessResponseSchema.json")) ;
                // what if my schema file is somewhere else other than resource folder
                //then you need i provide full path and use different method
                .body(matchesJsonSchema(schemaFile) );
    }

    @DisplayName("Testing GET /spartans/search endpoint structure")
    @Test
    public void testSearchSpartanResponseSchema(){

        given()
                .spec(adminReqSpec)
                .queryParam("nameContains", "a")
                .queryParam("gender","Female").
        when()
                .get("/spartans/search").
        then()
                .body(matchesJsonSchemaInClasspath("searchSpartanSchema.json"))

        ;


    }


}
