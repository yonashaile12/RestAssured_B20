import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class HelloTest {

    //Junit annotations
    //@BeforeAll @AfterAll @BeforeEach @AfterEach
    @BeforeAll
    public static void setUp(){
        System.out.println("@BeforeAll is running");
    }
    @AfterAll
    public static void tearDown(){
        System.out.println("@AfterAll is running");
    }

    @BeforeEach
    public void setUpTest(){
        System.out.println("@BeforeEach is running");
    }

    @AfterEach
    public void tearDownTest(){
        System.out.println("@AfterEach is running");
    }
    @Test
    public void test(){
        System.out.println("Test 1 is running");
        Assertions.assertEquals(4,1+3);
    }

    @Test
    public void test2(){
        System.out.println("test 2 is running");
        //assert 4 times 3 is 12
        assertEquals(12, 3*4);
    }

}
