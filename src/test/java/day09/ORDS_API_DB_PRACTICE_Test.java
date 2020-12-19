package day09;
import Utility.DB_Utility;
import Pojo.Country;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import testBase.HR_ORDS_TestBase;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class ORDS_API_DB_PRACTICE_Test extends HR_ORDS_TestBase {

    @DisplayName("GET/countries/{country_id} Compare Result with DB")
    @Test
    public void testResponseMatchDataBaseData(){

        String myCountryID = "AR";
        // send request to /countries/{country_id} for AR
        Country arPOJO =  given().log().all()
                         .pathParam("country_id", myCountryID).
                 when()
                         .get("/countries/{country_id}").prettyPeek()
                         .as(Country.class) ;
        // here the shorter way of above code
        
//        Country arPOJO1 = get("/countries/{coutry_id}", myCountryID).as(Country.class);

        System.out.println("arPOJO = " + arPOJO);
        String query = "SELECT * FROM COUNTRIES WHERE COUNTRY_ID = '" + myCountryID + "'  " ;
        System.out.println("query= "+query);
        DB_Utility.runQuery(query);
        Map<String, String> dbResultMap = DB_Utility.getRowMap(1);

        // now start validation the actual response to expected result from database
        assertThat(arPOJO.getCountry_id(), is(dbResultMap.get("COUNTRY_ID")));
        assertThat(arPOJO.getCountry_name() ,is(dbResultMap.get("COUNTRY_NAME") ) );
        //assertThat(arPOJO.getRegion_id(), equalTo( Integer.parseInt( dbResultMap.get("REGION_ID"))));

        int expectedRegionID = Integer.parseInt(dbResultMap.get("REGION_ID"));

        assertThat(arPOJO.getRegion_id(), equalTo(expectedRegionID));
    }

    @DisplayName("GET /countries Capture All CountryID and Compare Result with DB")
    @Test
    public void testResponseAllCountryIDsMatchDatabaseData(){

        List<String> allCountriesID = get("/countries").jsonPath().getList("items.country_id");
//        allCountriesID.forEach(System.out::println);

        DB_Utility.runQuery("SELECT * FROM COUNTRIES");

        List<String> expectedListFromDB = DB_Utility.getColumnDataAsList("COUNTRY_ID");

//        expectedListFromDB.forEach(System.out::println);

        // assert both list has same information
        assertThat(allCountriesID,equalTo(expectedListFromDB));

    }

}
