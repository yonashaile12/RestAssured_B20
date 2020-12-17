package day07;

import Pojo.Spartan;
import Utility.ConfigurationReader;
import Utility.SpartanUtil;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;
import static io.restassured.RestAssured.* ;
import static org.hamcrest.Matchers.* ;
import org.junit.jupiter.api.DisplayName;

import java.util.LinkedHashMap;
import java.util.Map;

public class PatchOneSpartanTest {

    @BeforeAll
    public static void setUp(){
        baseURI = ConfigurationReader.getProperty("spartan.base_url");
        basePath = "/api" ;
    }
    @AfterAll
    public static void tearDown(){
        reset();
    }

    @DisplayName("Patching one Data with Java Object")
    @Test
    public void testPatchOneDataPartialUpdate(){

        // we just want to update the name and phone number
        Map<String, Object> patchBodyMap = new LinkedHashMap<>();
        patchBodyMap.put("name", "B20 Voila");
        patchBodyMap.put("phone", 1234567890L);
        given()
                .auth().basic("admin", "admin")
                .log().all()
                .pathParam("id", 120)
                .contentType(ContentType.JSON)
                .body(patchBodyMap).
        when()
                .patch("/spartans/{id}").
        then()
                .log().all()
                .statusCode(is(204))      ;

    }


    @DisplayName("Patching one Data with Java Object")
    @Test
    public void testPatchOneDataPartialUpdateWithPOJO(){

        // we just want to update the name and phone number
        Spartan sp = new Spartan();
        sp.setName("B20 VOILA");
        sp.setPhone(9876543210L);
        // MAP is a better option with minimal effort
        // POJO class need some handling to ignore empty field values
        // when being serialized

        given()
                .auth().basic("admin", "admin")
                .log().all()
                .pathParam("id", 120)
                .contentType(ContentType.JSON)
                .body(sp).
        when()
                .patch("/spartans/{id}").
        then()
                .log().all()
                .statusCode(is(500))      ;

    }
}
