package day04;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import io.restassured.http.ContentType;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import testBase.Spartan_TestBase;

import java.io.File;


import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.* ;

public class spartanUpdatingTest extends Spartan_TestBase {

    @DisplayName("Add 1 Data with Raw Json String POST/api/spartans")
    @Test
    public void testAddOneData(){

        String newSpartanStr = "{\n" +
                "    \"name\": \"Magna\",\n" +
                "    \"gender\": \"Male\",\n" +
                "    \"phone\": 9876543210\n" +
                "  }";


        System.out.println(newSpartanStr);

        given()
                .log().all()
                .auth().basic("admin", "admin")
                .contentType(ContentType.JSON)
                .body(newSpartanStr).

                when()
                .post("/spartans").
                then()
                .log().all()
                .statusCode(is(201))
                .contentType(ContentType.JSON)
                .body("success", is("A Spartan is Born!"))
                .body("data.name", is("Magna") )
                .body("data.gender", is("Male") )
                .body("data.phone", is(9876543210L) )
        ;



    }

    @DisplayName("Testing PUT/api/spartans/{id} with string body")
    @Test
    public void testUpdatingSingleSpartanStringBody(){

        String updatingStrPayLoad = "{\n" +
                                    "    \"name\": \"B20 Vola\",\n" +
                                    "    \"gender\": \"Male\",\n" +
                                    "    \"phone\": 9876543210\n" +
                                    "  }";
        given()
                .log().all()
                .auth().basic("admin", "admin")
                .contentType(ContentType.JSON)
                .pathParam("id", 104)
                .body(updatingStrPayLoad).
        when()
                .put("/spartans/{id}").
        then()
                .log().all()
                .assertThat()
                .statusCode(is(204))
                // This is how we can check a header exists by checking the value
                // using notNullValue() matcher
                .header("Date", is(notNullValue()))
                // emptyString() is true on 204 status code because 204 does not have a body
                .body(emptyString())
                ;
    }

    @DisplayName("Testing Partial/api/spartans/{id} with string body")
    @Test
    public void testPartialSingleSpartanStringBody(){

        // update the name to B20 Patched
        //{"name" : "B20 Patched"}
        String patchBody = "{\"name\" : \"B20 Patched\"}";
        given()
                .log().all()
                .auth().basic("admin", "admin")
                .contentType(ContentType.JSON)
                .pathParam("id", 104)
                .body(patchBody).
        when()
                .patch("/spartans/{id}").
        then()
                .log().all()
                .assertThat()
                .statusCode(is(204))
                .body(emptyString())
        ;
    }

    @DisplayName("Testing DELETING/api/spartans/{id}")
    @Test
    public void testDeletingSingleSpartan(){

        given()
                .log().all()
                .auth().basic("admin", "admin")
                .pathParam("id", 104).
        when()
                .delete("/spartans/{id}").
        then()
                .log().all()
                .assertThat()
                .statusCode(is(204))
                .body(emptyString()) ;

    }
}
