package day11;

import io.restassured.http.ContentType;
import io.restassured.path.xml.XmlPath;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import testBase.SpartanAdminTestBase;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.is;

public class test_XML_Response extends SpartanAdminTestBase {

    // get XML response for GET / spartans
    @DisplayName("get XML response for GET / spartans")
    @Test
    public void testXML(){
        XmlPath xp =
                        given()
                                .spec(adminReqSpec)
                                .accept(ContentType.XML).
                        when()
                                 .get("/spartans").
                        then()
                                .log().all()
                                .statusCode(200)
                                //verify first person name is Adam
                                .body("List.item[0].name", is("Adam Jones"))
                                // verify first person id is 102
                                .body("list.item[0].id", is("1") )
                                //.body("list.item[0].id.toInteger()",is(1))
                                .contentType(ContentType.XML)
                                .extract()
                                .xmlPath()
                                ;
        // get the name of the first person in the reponse
        System.out.println("xp.getString(\"List.item[0].name\") = "
                            + xp.getString("list.item[0].name"));
        // get the id of 3 person
        System.out.println("xp.getString(\"list.item[2].id\") = "
                            + xp.getString("list.item[2].id"));

        System.out.println("xp.getString(\"list.item[-1].id\") = "
                + xp.getString("list.item[-1].id"));

        System.out.println("xp.getString(\"list.item[-1].phone\") = "
                + xp.getLong("list.item[-1].phone"));


    }
}
