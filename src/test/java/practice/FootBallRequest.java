package practice;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class FootBallRequest {
    /*
    "id": 2271,
            "name": "Zanzibar",
            "countryCode": "ZAN",
            "ensignUrl": null,
            "parentAreaId": 2001,
            "parentArea": "Africa"
     */

    @DisplayName("Test GET/ areas with POJO")
    @Test
    public void testResponseMatchData(){
        //https://api.football-data.org/v2/
        String myCountryID = "ZAN";
        given()
                .log().all()
                .pathParam("/areas", myCountryID);


    }
}
