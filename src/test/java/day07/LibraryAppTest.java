package day07;

import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.*;
import sun.plugin2.message.JavaScriptBaseMessage;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

@TestMethodOrder(MethodOrderer.DisplayName.class)
public class LibraryAppTest {


    private static String myToken;

    @BeforeAll
    public static void setUp(){
        baseURI = "http://library1.cybertekschool.com";
        basePath = "/rest/v1";

    }

    @AfterAll
    public static void tearDown(){
        reset();
    }

    @DisplayName("1. Testing POST /login EndPoint")
    @Test
    public void testLogin(){
        /*
        Librarian1  username: librarian69@library
        Librarian1 password	: KNPXrm3S
         */
        myToken =
        given()
                .log().all()
                .contentType(ContentType.URLENC)
                .formParam("email","librarian69@library")
                .formParam("password", "KNPXrm3S").
        when()
                .post("/login").
        then()
                .log().all()
                .assertThat()
                .statusCode(is(200))
                .contentType(ContentType.JSON)
                .body("token", is(not( emptyString() ) ))
        .extract()
                .jsonPath()
                .getString("token")
        ;
        System.out.println("myToken = \n" + myToken);
        // how to extract some data out of the response object
        // after doing validation in then section
        // without breaking the chain --->> use extract method that return

    }

    @DisplayName("2. Testing GET /dashboard_stats EndPoint")
    @Test
    public void zTestDashboard_stats(){

        given()
                .log().all()
                .header("x-library-token",myToken).
        when()
                .get("/dashboard_stats").

        then()
                .log().all()
                .assertThat()
                .statusCode(is(200))
                .contentType(ContentType.JSON)
                ;
    }

    @DisplayName("3. Save the result of GET dashboard stat as Map Object")
    @Test
    public void testGetDashBoardStatAsMap(){
        /*
        {
            "book_count": "1162",
            "borrowed_books": "650",
            "users": "7411"
        }
         */
        JsonPath jp =
                        given()
                                .log().all()
                                .header("x-library-token", myToken).
                        when()
                                .get("/dashboard_stats")
                                .jsonPath();
        // Get the response as a map and print it out
        Map<String, Object> responseJsonMap = jp.getMap("");
        System.out.println(responseJsonMap);
        //{book_count=1162, borrowed_books=650, users=7411}
    }

    @DisplayName("4. Save /get_book_categories response as POJO")
    @Test
    public void testGetBookCategoriesAsPOJO(){

        JsonPath jp = given()
                            .log().all()
                            .header("x-library-token", myToken).
                        when()
                            .get("/get_book_categories").prettyPeek()
                            .jsonPath();

        List<BookCategory> allCategory = jp.getList("",BookCategory.class);
        allCategory.forEach(System.out::println);

        // without using above list
        // use jsonpath to get number 5 item in jsonArray
        // and save it as Book Category Object

        BookCategory num5BC = jp.getObject("[4]", BookCategory.class);
        System.out.println("num5BC = " + num5BC);
        //num5BC = BookCategory{id='5', name='Crime and Detective'}



    }

}
