package testBase;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

import static io.restassured.RestAssured.*;

public abstract class HR_ORDS_TestBase {

    //http://54.90.101.103:1000/ords/hr/countries/AR
    @BeforeAll
    public static void setUP(){
        baseURI = "http://54.90.101.103:1000/";
        basePath = "ords/hr";
    }

    @AfterAll
    public static void tearDown(){
        reset();
    }

}
