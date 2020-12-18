package testBase;

import Utility.ConfigurationReader;
import Utility.DB_Utility;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

import static io.restassured.RestAssured.*;

public abstract class HR_ORDS_TestBase {

    //http://54.90.101.103:1000/ords/hr/countries/AR
    @BeforeAll
    public static void setUP(){
        baseURI = ConfigurationReader.getProperty("ords.baseURL");
        basePath = ConfigurationReader.getProperty("ords.basePath");

        // create DB connection here
        DB_Utility.createConnection(ConfigurationReader.getProperty("hr.database.url"),
                                    ConfigurationReader.getProperty("hr.database.username"),
                                    ConfigurationReader.getProperty("hr.database.password"));
    }

    @AfterAll
    public static void tearDown(){
        reset();
        DB_Utility.destroy();
    }

}
