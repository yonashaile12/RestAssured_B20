package day10;
import Pojo.ArticlePOJO;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class NewsApi_Homework {

    @DisplayName("GET all Articles author if source id is not null")
    @Test
    public void testGetAllArticleAuthor(){

        ///v2/top-headlines?country=us&apiKey=API_KEY"
        JsonPath jp =
        given()
                .log().uri()
                .baseUri("https://newsapi.org")
                .basePath("v2/")
                .header("Authorization","Bearer 4c0cad403a3046e6b1814715437bef9e")
               // .queryParam("apikey","4c0cad403a3046e6b1814715437bef9e")
                .queryParam("country","us").
        when()
                .get("/top-headlines")//.prettyPeek()
                .jsonPath();

        List<String> allAuthor = jp.getList("articles.findAll{ it.source.id != null }.author");
        System.out.println("allAuthor = " + allAuthor);
        System.out.println("allAuthor = " + allAuthor.size());
        List<String> allAuthorNoFilter = jp.getList("articles.author");
        System.out.println("allAuthorNoFilter = " + allAuthorNoFilter.size());
        System.out.println("allAuthorNoFilter = " + allAuthorNoFilter);




        // additional requirement - remove any author with null value

        List<String> allAuthorWithNoNull =
                jp.getList("articles.findAll{ it.source.id != null && it.author != null }.author");

        System.out.println("allAuthorWithNoNull = " + allAuthorWithNoNull.size());
        System.out.println("allAuthorWithNoNull = " + allAuthorWithNoNull);



        List<ArticlePOJO> allArticles =
                jp.getList("articles.findAll{ it.source.id != null && it.author != null }", ArticlePOJO.class);
        allArticles.forEach(System.out::println);



    }


}
