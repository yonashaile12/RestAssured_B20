package day05;

import Utility.ConfigurationReader;
import Utility.SpartanUtil;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;
import static io.restassured.RestAssured.* ;
import static org.hamcrest.Matchers.* ;
import org.junit.jupiter.api.DisplayName;

import java.util.Map;

@TestMethodOrder(MethodOrderer.DisplayName.class)
@DisplayName("Spartan App End to End CRUD Happy Path")
public class Spartan_E2E_HappyPath {

    private static Map<String, Object> payLoadMap;
    private static int newID;

    // CRUD operation
    @BeforeAll
    public static void setUp(){
        baseURI = ConfigurationReader.getProperty("spartan.base_url");
        basePath = "/api" ;
        payLoadMap = SpartanUtil.getRandomSpartanRequestPayLoad();
    }
    @AfterAll
    public static void tearDown(){
        reset();
    }
    @DisplayName("1. Testing POST /api/spartans/Endpoint")
    @Test
    public void testAddData(){
        newID =
        given()
                .auth().basic("admin", "admin")
                .contentType(ContentType.JSON)
                .body(payLoadMap)
                .log().all().
        when()
                .post("/spartans").
        then()
                .log().all()
                .assertThat()
                .statusCode(is(201))
                .contentType(ContentType.JSON)
                //assert the response body like name, gender, phone
                // is same as what faker generated
                .body("data.name", is(payLoadMap.get("name")))
                .body("data.gender", is(payLoadMap.get("gender")))
                .body("data.phone", equalTo(payLoadMap.get("phone")))
        .extract()
                .jsonPath()
                .getInt("data.id")

        ;
        System.out.println("newID = " + newID);

    }

    @DisplayName("2. Testing GET /api/spartans/Endpoint")
    @Test
    public void testGet1SpartanData(){

        given()
                .auth().basic("admin","admin")
                .pathParam("id",newID)
                .log().all().
        when()
                .get("/spartans/{id}").
        then()
                .log().all()
                .assertThat()
                .statusCode(is(200))
                .contentType(ContentType.JSON)
                .body("id", is(newID))
                .body("name", is(payLoadMap.get("name")))
                .body("gender", is(payLoadMap.get("gender")))
                .body("phone", is(payLoadMap.get("phone")))
        ;

    }

    @DisplayName("3. Testing Update /api/spartans/Endpoint")
    @Test
    public void testUpdate1SpartanData(){

        // We want to have different payload so we can update
        // Option is rerun the utility method to override
        //existing map object newly generated faker map object

        payLoadMap = SpartanUtil.getRandomSpartanRequestPayLoad();
        //System.out.println(payLoadMap);

        given()
                .auth().basic("admin","admin")
                .pathParam("id",newID)
                .contentType(ContentType.JSON)
                .body(payLoadMap)
                .log().all().
        when()
                .put("/spartans/{id}").
        then()
                .log().all()
                .assertThat()
                .statusCode(is(204))
                .body(emptyString() )
        ;
    // in order to make sure the update actually happened
    // I want to make another get request to this ID
        given()
                .auth().basic("admin","admin")
                .pathParam("id",newID)
                .log().all().
        when()
                .get("/spartans/{id}").
        then()
                .log().all()
                .assertThat()
                .statusCode(is(200))
                .contentType(ContentType.JSON)
                .body("id", is(newID))
                .body("name", is(payLoadMap.get("name")))
                .body("gender", is(payLoadMap.get("gender")))
                .body("phone", is(payLoadMap.get("phone")))
        ;

    }

    @DisplayName("4. Testing DELETE /api/spartans/Endpoint")
    @Test
    public void testDelete1SpartanData(){

        given()
                .auth().basic("admin","admin")
                .pathParam("id",newID)
                .log().all().
        when()
                .delete("/spartans/{id}").
        then()
                .log().all()
                .assertThat()
                .statusCode(is(204))
                .body(emptyString() )
        ;

        // in order to make sure the delete actually happened
        // i want to make another get request to this ID expect 404
        given()
                .auth().basic("admin","admin")
                .pathParam("id" , newID)
                .log().all().
                when()
                .get("/spartans/{id}").
                then()
                .log().all()
                .assertThat()
                .statusCode( is (404) ) ;

    }
}
