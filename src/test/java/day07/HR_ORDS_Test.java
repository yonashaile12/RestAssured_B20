package day07;

import Pojo.Region;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import static io.restassured.RestAssured.* ;
import static org.hamcrest.Matchers.* ;
import org.junit.jupiter.api.DisplayName;
import testBase.HR_ORDS_TestBase;

import java.util.List;

public class HR_ORDS_Test extends HR_ORDS_TestBase {

    //http://54.90.101.103:1000/ords/hr/regions/3

    @DisplayName("Testing /regions/{region_id}")
    @Test
    public void testThirdRegionIsAsia(){
        given()
                .pathParam("region_id", 3)
                .log().all().
        when()
                .get("/regions/{region_id}").
        then()
                .log().all()
                .assertThat()
                .statusCode(is(200))
                .contentType(ContentType.JSON)
                .body("region_name", is("Asia"))
        ;

    }

    @DisplayName("Save GET /regions/{region_id} treponse as POJO")
    @Test
    public void testSingleRegionToPOJO(){

        Response response = given()
                .pathParam("region_id", 3)
                .log().all().
                        when()
                .get("/regions/{region_id}")
                .prettyPeek();

        JsonPath jp = response.jsonPath();
        Region r3 = jp.getObject("", Region.class);
        System.out.println(r3);

        Region r4 = response.as(Region.class);
        System.out.println("r4 = " + r4);
    }

    @DisplayName("Save GET /regions/{region_id} treponse as POJO")
    @Test
    public void testAllRegionsToListOfPOJO(){
        Response response = get("/regions").prettyPeek();
        JsonPath jp = response.jsonPath();

        List<Region> allRegions = jp.getList("items", Region.class);

        allRegions.forEach(System.out::println);
    }

}
