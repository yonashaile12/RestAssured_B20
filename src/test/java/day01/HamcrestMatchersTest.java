package day01;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.* ;

// hamcrest assertion library already part of
// our rest assured depdency in POM file
// No specific dependency needed
public class HamcrestMatchersTest {

    @DisplayName("Test 1 + 3 = 4")
    @Test
    public void test1(){

        assertThat(1+3, is(4) );
        assertThat(1+3, equalTo(4) );

        // add some error message if it fails
        //assertThat("Wrong result", 1+3, equalTo(5) );

        // test 1+3 is not 5
        assertThat(1+3, not(5) );
        // we can nest a matcher inside another matcher
        assertThat(1+3, is(not(5)) );
        assertThat(1+3, not(equalTo(5) ));

        // test 1+3 is less than 5
        assertThat(1+3, lessThan(5) );

        // test 1+3 is more than 2
        assertThat(1+3, greaterThan(2) );


    }
}
