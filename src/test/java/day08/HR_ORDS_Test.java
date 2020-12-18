package day08;
import Pojo.Spartan;
import Utility.ConfigurationReader;
import Utility.SpartanUtil;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import netscape.javascript.JSObject;
import org.junit.jupiter.api.*;
import static io.restassured.RestAssured.* ;
import static org.hamcrest.Matchers.* ;
import org.junit.jupiter.api.DisplayName;
import org.w3c.dom.stylesheets.LinkStyle;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import testBase.HR_ORDS_TestBase;

import java.util.List;

import static io.restassured.RestAssured.*;

public class HR_ORDS_Test extends HR_ORDS_TestBase {



    @DisplayName("Test GET/{countries_id} to POJO")
    @Test
    public void testCountryResponseToPOJO(){
      //  Response response = get("/countries/{country_id}", "AR").prettyPeek();

        Response response = given()
                                    .pathParam("country_id", "AR").
                            when()
                                    .get("/countries/{country_id}").prettyPeek();

        Country ar = response.as(Country.class);
        System.out.println("Argentina = " + ar);

        Country ar1 = response.jsonPath().getObject("", Country.class);
        System.out.println("Argentina with jsonPath = " + ar1);

    }

    @DisplayName("Test Get/ countries to List of POJO")
    @Test
    public void testAllCountriesToListOfPOJO(){
        Response response = get("/countries").prettyPeek();
        List<Country> countryList = response.jsonPath().getList("items",Country.class);

        countryList.forEach(System.out::println);
    }


}
