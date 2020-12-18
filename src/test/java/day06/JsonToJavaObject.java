package day06;

import Pojo.Spartan;
import Pojo.SpartanRead;
import Utility.ConfigurationReader;
import Utility.SpartanUtil;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import static io.restassured.RestAssured.* ;
import static org.hamcrest.Matchers.* ;
import org.junit.jupiter.api.DisplayName;
import testBase.Spartan_TestBase;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.*;

public class JsonToJavaObject extends Spartan_TestBase {
    //        deserialization

    @DisplayName("Get 1 Data with save Response Json As Java Object")
    @Test
    public void getOneSpartanAbdSaveResponseJsonAsMap(){

        Response response =
                             given()
                                    .auth().basic("admin", "admin")
                                    .log().all()
                                    .pathParam("id", 121).
                             when()
                                    .get("/spartans/{id}").prettyPeek()

                            ;
        // get jsonpath object
        JsonPath jp = response.jsonPath();
         Map<String, Object> responseMap =  jp.getMap("");
        System.out.println(responseMap);

        SpartanRead sp = jp.getObject("",SpartanRead.class);
        System.out.println("sp = " + sp);

        /**
         * {
         *    "id": 121,
         *     "name": "Hugh",
         *     "gender": "Female",
         *     "phone": 5811421775
         * }
         * jsonPath to get whole json object is just empty string
         * assume this is a car object
         *
         *      {
         *         "make":"Honda"
         *         "color":"white"
         *         "engine": {
         *                      "type": "v8"
         *                      "horsePower" 350
         *                      }
         *      }
         *
         *      jsonPath for horse power ---> engine.horsePower
         *      jsonPath for engine itself --->> engine
         *      jsonPath for entire car jsonObject --->> ""
         *
         *
          */

    }

    @DisplayName("Get All Data with save Response JsonArray As Java Object")
    @Test
    public void getOneSpartanAbdSaveResponseJsonAsJavaObject(){

        Response response = given()
                                 .auth().basic("admin", "admin").
                            when()
                                .get("/spartans")
                ;

        JsonPath jp = response.jsonPath();

        List<SpartanRead> allSpartansPOJO = jp.getList("", SpartanRead.class);

        allSpartansPOJO.forEach(System.out::println);
    }
}
