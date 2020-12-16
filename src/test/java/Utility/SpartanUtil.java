package Utility;

import Pojo.Spartan;
import com.github.javafaker.Faker;
import java.util.LinkedHashMap;
import java.util.Map;

public class SpartanUtil {

    public static Map<String, Object> getRandomSpartanRequestPayLoad(){

        Faker faker = new Faker();
        Map<String, Object> payLoadMap = new LinkedHashMap<>();
        payLoadMap.put("name", faker.name().firstName() );
        payLoadMap.put("gender", faker.demographic().sex() );
        payLoadMap.put("phone", faker.number().numberBetween(5000000000L, 9999999999L));

        return payLoadMap;
    }

    public static Spartan getRandomSpartanPOJO_Payload(){
        Faker faker = new Faker();

        Spartan randomSpartan = new Spartan();
        randomSpartan.setName(faker.name().firstName());
        randomSpartan.setGender(faker.demographic().sex());
        randomSpartan.setPhone(faker.number().numberBetween(5000000000L, 9999999999L));

        return randomSpartan;
    }




}
