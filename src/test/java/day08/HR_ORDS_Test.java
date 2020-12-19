package day08;
import Pojo.Country;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import static io.restassured.RestAssured.* ;

import org.junit.jupiter.api.DisplayName;
import testBase.HR_ORDS_TestBase;

import java.util.List;

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
