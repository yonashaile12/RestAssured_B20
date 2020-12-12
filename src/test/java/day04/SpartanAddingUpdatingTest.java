package day04;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.* ;
public class SpartanAddingUpdatingTest {

    @BeforeAll
    public static void setUp(){
        baseURI = "http://54.90.101.103:8000";
        basePath = "/api";

    }

    @AfterAll
    public static void tearDown(){
        reset();
    }

    @DisplayName("Get All Spartan with basic auth")
    @Test
    public void testAllSpartanWithBasicAuth(){

        given()
                .log().all()
                .auth().basic("admin", "admin").
        when()
                .get("/spartans").
        then()
                .log().all()
                .statusCode(is(200)) ;


    }

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

    @DisplayName("Add 1 Data with Map Object POST/api/spartans")
    @Test
    public void testAddOneDataWithMapAsBody(){
        Map<String,Object> payLoadMap = new LinkedHashMap<>();

        payLoadMap.put("name", "Tucky");
        payLoadMap.put("gender", "Male");
        payLoadMap.put("phone", 9876543210L);

        System.out.println(payLoadMap);

        given()
                .log().all()
                .auth().basic("admin", "admin")
                .contentType(ContentType.JSON)
                .body(payLoadMap).

        when()
                .post("/spartans").

        then()
                .log().all()
                .statusCode(is(201))
                .contentType(ContentType.JSON)
                .body("success", is("A Spartan is Born!"))
                .body("data.name", is("Tucky") )
                .body("data.gender", is("Male") )
                .body("data.phone", is(9876543210L) )
        ;



    }



}
