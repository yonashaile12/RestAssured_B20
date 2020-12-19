package day08;
import Utility.DB_Utility;
import Pojo.Region;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import io.restassured.response.Response;
import testBase.HR_ORDS_TestBase;

import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class ORDS_API_DB_Test extends HR_ORDS_TestBase {

    @DisplayName("Testing the connection with query")
    @Test
    public void testDB_Connection(){
        DB_Utility.runQuery("SELECT * FROM REGIONS");
        DB_Utility.displayAllData();
    }

    /**
     * Send a GET/region/{region_id} request with region of 3
     * check the status code
     * save it Region POJO after status check
     * Get your expected result from DataBase query
     * SELECT * FROM REGIONS;
     * save the third row as a MAP
     * Verify the data from response match the data from database
     */

    @DisplayName("Testing GET/region/{region_id} Data Match Database Data")
    @Test
    public void testRegionDataFromReponseMatchDB_Data(){
        int myID = 3;
        Response response = given()
                                    .pathParam("region_id", myID).
                            when()
                                    .get("/regions/{region_id}").
                            then()
                                    .log().body()
                                    .statusCode(is(200))
                            .extract()
                                    .response();
        Region r3 = response.as(Region.class);
        System.out.println(r3);

        DB_Utility.runQuery("SELECT * FROM REGIONS WHERE REGION_ID = "+myID);
        Map<String, String> expectedResultMap = DB_Utility.getRowMap(1);
        System.out.println("expectedResultMap = " + expectedResultMap);
        // verify the actual result from api response match expected database result
       assertThat(r3.getRegion_id()+"", equalTo(expectedResultMap.get("REGION_ID")));
       assertThat(r3.getRegion_name(), is(expectedResultMap.get("REGION_NAME")));

    }

    @DisplayName("Testing GET/region/{region_id} Data Match Database Data With Both Maps")
    @Test
    public void testRegionDataFromReponseMatchDB_Data2() {
        int myID = 3;
        JsonPath jp = given()
                .pathParam("region_id", myID).
                        when()
                .get("/regions/{region_id}").
                        then()
                .log().body()
                .statusCode(is(200))
                .extract()
                .jsonPath();

        // save the response json as a map object
        // Here we are calling the overloaded version of getMap method with 3 params
        // 1. JsonPath string
        // 2. Data type Map key
        // 3. Data type map value
        // so we can make sure we get exactly what we asked for
        Map<String, String> actaulResultMap = jp.getMap("",String.class, String.class);
       // actaulResultMap.remove("links");
        System.out.println("actaulResultMap = " + actaulResultMap);
        // do not need to remove extra links from json result
        // because we are checking key value pair, anything we do not check will not matter

        DB_Utility.runQuery("SELECT * FROM REGIONS WHERE REGION_ID = "+myID);
        Map<String, String> expectedResultMap = DB_Utility.getRowMap(1);

        // since the keyname is different in bith map we can not directly
        // compare map to object
        // we have to compare the value of key step by step

        assertThat(actaulResultMap.get("region_id"),
                equalTo(expectedResultMap.get("REGION_ID")) );
        assertThat(actaulResultMap.get("region_name"),
                equalTo(expectedResultMap.get("REGION_NAME")) );
    }

    @DisplayName("Testing GET/region/{region_id} Data Match Database Data With Both Just Value by value")
    @Test
    public void testRegionDataFromReponseMatchDB_Data3() {
        int myID = 3;
        JsonPath jp = given()
                .pathParam("region_id", myID).
                        when()
                .get("/regions/{region_id}").
                        then()
                .log().body()
                .statusCode(is(200))
                .extract()
                .jsonPath();


        String actualRegionID = jp.getString("region_id");
        String actualRegionName = jp.getString("region_name");

        DB_Utility.runQuery("SELECT REGION_ID, REGION_NAME FROM REGIONS WHERE REGION_ID = "+myID);
        String expectedRegionID = DB_Utility.getColumnDataAtRow(1,"REGION_ID");
        String expectedRegionName = DB_Utility.getColumnDataAtRow(1,"REGION_NAME");
        //System.out.println(DB_Utility.getColumnNames());

       assertThat(actualRegionID,is(expectedRegionID));
       assertThat(actualRegionName, equalTo(expectedRegionName));



    }
}
