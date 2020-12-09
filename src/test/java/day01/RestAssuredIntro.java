package day01;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.* ;
import static io.restassured.matcher.RestAssuredMatchers.* ;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.* ;

public class RestAssuredIntro {
     @DisplayName("Spartan GET /api/hello Endpoint Test")
     @Test
    public void TestHello(){
          //http://34.207.119.202:8000/api/hello
         // make sure this what's imported for data type response
         Response response = get("http://34.207.119.202:8000/api/hello") ;

         // get status code out of this Response object
         System.out.println("status code is: "+response.statusCode() );

         // assert the status code is 200
         assertThat(response.statusCode(), equalTo(200));
         // how to pretty print entire response object
         // prettyPrint() -- print and return the body as String or payload
         String payLoad = response.prettyPrint();
         // assertThat the body is Hello from Sparta

         assertThat(payLoad, is("Hello from Sparta"));

         // get the header called contentType

         System.out.println( response.getHeader("Content-Type"));
         System.out.println(response.getContentType());
         System.out.println(response.contentType());

         // assertThat Content-Type is text/plain;charset=UTF-8

         assertThat(response.contentType(), is("text/plain;charset=UTF-8"));

         //assert that Content-Type startWith text
         assertThat(response.contentType(),startsWith("text"));

         //Easy way to work with Content-Type without typing much
         // We cann use ContentType Enum from RestAssured to easily
         //ContentType.TEXT --->> text/plain
         assertThat(response.contentType(), startsWith( ContentType.TEXT.toString() ));
         assertThat(response.contentType(), is(not (ContentType.JSON) ));


     }
     @DisplayName("Common Matchers for Strings")
     @Test
    public void testString (){
         String str = "Rest Assured is cool so far";

         //assert the str is "Rest Assured is cool so far"
         assertThat(str, is("Rest Assured is cool so far"));

         //assert the str is "Rest Assured IS COOL so far" in case insensitive manner
         assertThat(str, equalToIgnoringCase("Rest Assured IS COOL so far"));

         //assert the str startWith "Rest"
         assertThat(str, startsWith("Rest"));

         //assert the str endsWith "so far"
         assertThat(str, endsWith("so far"));

         //assert the str contains "is cool"
            assertThat(str, containsString( "is cool"));

         //assert the str contains "IS COOL" case insensitve manner
         assertThat(str, containsStringIgnoringCase("IS COOL"));

     }



}
