package day03;

import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.*;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.List;

import static io.restassured.RestAssured.* ;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.* ;

public class JsonPathIntro {

    @BeforeAll
    public static void setUp(){
        baseURI = "http://34.207.119.202:8000";
        basePath = "/api";

    }

    @AfterAll
    public static void tearDown(){
        reset();
    }

    @DisplayName("Extracting data out of Spartan Json Object")
    @Test
    public void test1SpartanPayLoad(){

        // send request to Get 1 spartan
        // by providing path params with valid id
        // save it into Response object
        // NEW: create an object with type JsonPath
        // bt calling the method jsonPath() on response object
        // extract id, name, gender, phone
        // and save it into variable of correct type

        Response response = given()
                                    .pathParam("id", 34).
                            when()
                                    .get("/spartans/{id}")
                                    .prettyPeek()
                            ;
        //response.prettyPrint();
        // JsonPath is used to navigate through json payload
        // and extract the value according to the valid "jsonPath" provided
        JsonPath jp      = response.jsonPath();
        int myId         = jp.getInt("id");
        String myName    = jp.getString("name");
        String MyGender  = jp.get("gender");
        long MyPhone = jp.getLong("phone");

        System.out.println("myId = " + myId);
        System.out.println("myName = " + myName);
        System.out.println("MyGender = " + MyGender);
        System.out.println("MyPhone = " + MyPhone);
    }

    @DisplayName("Extracting data from json Array Response")
    @Test
    public void getAllSpartanExtractData(){
//        Response response = get("/spartans");
//        JsonPath jp = response.jsonPath();

        JsonPath jp = get("/spartans").jsonPath();

        // get the first json object name field
        System.out.println("jp.getString(\"name[0]\") = "
                + jp.getString("name[0]"));

        System.out.println("jp.getLong(\"phone[0]\") = "
                + jp.getLong("phone[0]"));

        // get the 7th json object gender field from json array
        System.out.println("jp.getString(\"gender[6]\") = "
                + jp.getString("gender[6]"));

        // get the last json object name field
        System.out.println("jp.getString(\"name[-1]\") = "
                + jp.getString("name[-1]"));

//        List<String> jsonResponse = jp.getList("$");
//        for ( Object each:jsonResponse) {
//            System.out.println(each.toString());
//        }



        // getting all the name field from the json Array response
        // and storing as a List

        List<String> allNames = jp.getList("name");
        System.out.println("allNames = "+allNames);

        // getting all the phone field from the json Array response
        // and storing as a List

        List<Long> allPhones = jp.getList("phone");
        System.out.println("allPhones = " + allPhones);
        //allPhones.forEach(System.out::print);

    }

    // send request to this request url
    //http://34.207.119.202:8000/api
    // (/spartans/search?nameContains=de&gender=Female)
    // get the name and save of first guy in the result
    // get the phone of 3rd gut inthe result
    // get all names, all phone save it as List
    // save the value of field called empty under pageagble in the response
    //print it out
    @DisplayName("Testing /spartan/search and Extracting data")
    @Test
    public void testSearch(){

        JsonPath jp = given()
                            .queryParam("nameContains", "ea")
                            .queryParam("gender", "Male").
                      when()
                            .get("/spartans/search") // this is where we get response
                             .jsonPath();
        System.out.println("First guy name "
                +jp.getString("content[0].name"));

        System.out.println("third guy phone Number "
                +jp.getLong("content[2].phone"));

        System.out.println("all Names " +jp.getList("content.name"));
        System.out.println("all phones " +jp.getList("content.phone"));

        System.out.println("value of field empty "
                +jp.getBoolean("pageable.sort.empty"));




    }


}
