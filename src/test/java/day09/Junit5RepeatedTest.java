package day09;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;

public class Junit5RepeatedTest {

    //@DisplayName("Repeated Test Demo")

    @RepeatedTest(10)
    public void testRepeating(){

        //
        Faker faker = new Faker();
        //System.out.println("Name is "+ faker.funnyName().name());
        System.out.println(faker.chuckNorris().fact());
    }
}
