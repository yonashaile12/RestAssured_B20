package testBase;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

import static io.restassured.RestAssured.*;

public class Spartan_TestBase {

    @BeforeAll
    public static void setUp(){
        baseURI = "http://18.232.120.236:8000";
        basePath = "/api";
        //18.232.120.236
        //54.90.101.103
    }

    @AfterAll
    public static void tearDown(){
        reset();
    }
}
