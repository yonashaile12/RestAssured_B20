package day06;

import Pojo.Spartan;
import Utility.ConfigurationReader;
import Utility.SpartanUtil;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;
import static io.restassured.RestAssured.* ;
import static org.hamcrest.Matchers.* ;
import org.junit.jupiter.api.DisplayName;
import testBase.Spartan_TestBase;


public class PostWithCustomObject extends Spartan_TestBase {

    @DisplayName("Add one data with POJO as body")
    @Test
    public void testAddDataWithPOJO(){
        //Spartan sp1  = new Spartan("B20","Male", 1234567890L);
        Spartan sp1 = SpartanUtil.getRandomSpartanPOJO_Payload();
        System.out.println(sp1);

        given()
                .auth().basic("admin", "admin")
                .log().all()
                .contentType(ContentType.JSON)
                .body( sp1).
        when()
                .post("/spartans").
        then()
                .log().all()
                .assertThat()
                .statusCode(is(201))
                .body("success",is("A Spartan is Born!"))
                .body("data.name", is(sp1.getName()))
                .body("data.gender", is(sp1.getGender()))
                .body("data.phone", is(sp1.getPhone()))
        ;
    }

    // Java Object to text or Json is called serialization
    // text or Json to java object is called de-serialization

}
