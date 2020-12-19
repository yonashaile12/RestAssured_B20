package day09;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.*;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class JunitParameterizedTest {

    @ParameterizedTest
    @ValueSource(ints = {5,6,7,8,9})
    public void test1( int myNumber){

        System.out.println("myNumber = " + myNumber);
        // assert 5,6,7,8,9 all less that 10
        assertTrue(myNumber< 10);
    }

    //  using CSV file as source for parameterized test

    // using CSV file as source for parameterized test
    @ParameterizedTest
    @CsvFileSource(resources = "/zipcode.csv" , numLinesToSkip = 1)
    public void test2( String zip ){
        System.out.println("zip = " + zip);
        // sending request to zipcode endpoint here
        //api.zippopotam.us/us/{zipcode}
        // baseurl : api.zippopotam.us
        //  endpoint is /us/{zipcode}
        given()
                .log().uri()
                .baseUri("https://api.zippopotam.us")
                .pathParam("zipcode" , zip).
        when()
                .get("/us/{zipcode}").
        then()
                .statusCode(200) ;
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/country_zip.csv" , numLinesToSkip = 1)
    public void testCountryZipCodeCombo(String csvCountry, int csvZip){
        // https://api.zippopotam

        given()
                .log().uri()
                .baseUri("https://api.zippopotam.us")
                .pathParam("country",csvCountry)
                .pathParam("zipcode", csvZip).
        when()
                .get("/{country}/{zipcode}").
        then()
                .statusCode(is(200)) ;

    }
}
