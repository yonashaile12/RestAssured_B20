package day02;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.* ;
public class SpartanTest {

    @BeforeAll
    public static void setUp(){
        //baseURI and basePATH is static fields of RestAssured class
        //since we static imported RestAssured, we can access all static field
        // directly just like it's in our own class here
        // you can use static way as below
        //baseURI ="http://34.207.119.202:8000"
        //or you can directly use as below
       // RestAssured.baseURI = "http://34.207.119.202:8000";
       // RestAssured.basePath ="/api";
        baseURI = "http://34.207.119.202:8000";
        basePath = "/api";
        //baseURI + basePATH + whatever you provided in http method like get post
        //for example:
        //get("/spartans") -->> get(baseURI+basePATH + "/spartans")

    }

    @AfterAll
    public static void tearDown(){
        // it is just reseting to the original value or default value
        // reset the value of baseURI, basePATH to original value
        RestAssured.reset();
    }


    @DisplayName("Testing /api/spartans end point")
    @Test
    public void testGetAllSpartan(){
        // send a get request to above endpoint
        // save the response
        // print out the result
        // try to assert the status code
        // content type header

        Response response = get("/spartans");
        response.prettyPrint();

        assertThat(response.statusCode(), is(200));

        assertThat(response.contentType(), is(ContentType.JSON.toString()));

    }


    @DisplayName("Testing /api/spartans end point XML Response")
    @Test
    public void testGetAllSpartanXML(){

        /**
         * given
         *  -- RequestSpecification
         *  used to provide additional information about the request
         *  base url, base path
         *  header, query params, path variable, authentication authorization
         *  logging, cookie
         * when
         *  -- This is where you actually send the request with http method
         *  -- like GET POST DELETE .. with the URL
         *  -- you get reponse object after sending the request
         * then
         *  -- ValidatableResponse
         *  -- validate statusCode, header, payload(Body), cookie
         *  -- validate responseTime
         */
        //Response response = get("http://34.207.119.202:8000/api/spartans");
        given()
                .header("accept","application/xml").
        when()
                .get("/spartans").
        then()
//                .assertThat() // this is not required, but can be added to make it obvious that this is where we start assertions
                .statusCode(200)
//                .and() // this is not required at all, just for readability, optional
                .header("Content-Type", "application/xml")
        ;

// this will do the same exact thing as above in slightly different way
// since accept header and content type header is so common, RestAssured has good support or
// those header by providing method directly rather than using header method we used above

        given()
                .accept(ContentType.XML).
        when()
                .get("/spartans").
        then()
                .assertThat()
                .statusCode(is(200))
                .and()
                .contentType(ContentType.XML) ;

        //http://34.207.119.202:8000(baseURI)/api(the entry point for api)/spartans(endPoint)

    }
}
