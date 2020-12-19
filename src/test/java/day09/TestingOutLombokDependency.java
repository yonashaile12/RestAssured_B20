package day09;
import Utility.DB_Utility;
import Pojo.Country;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import testBase.HR_ORDS_TestBase;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import Pojo.Department;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

public class TestingOutLombokDependency extends HR_ORDS_TestBase{

    @Test
    public void testDepartmentPOJO(){

        Department d = new Department();

        d.setDepartment_id(100);
        System.out.println(d.getDepartment_id());

        Department d2 = new Department(100,"ABC",12,1700);
        System.out.println("d2 = " + d2);


    }

    @DisplayName("GET/ departments and save List of POJO")
    @Test
    public void testDepartmentJsonArrayToListOfPOJO(){

        List<Department> allDeps = get("/departments")
                                    .jsonPath().getList("items", Department.class);

        //allDeps.forEach(System.out::println);

        // copy the content of this list into new list
        // Only Print if the Dep manager id is not null

      //  allDeps.removeIf(eachDep->eachDep.getManager_id()!= 0);
       // allDeps.forEach(System.out::println);
        List<Department> allDepsCopy = new ArrayList<>(allDeps);
        allDepsCopy.removeIf(eachDep->eachDep.getManager_id() == 0);
        allDepsCopy.forEach(System.out::println);
    }

    @DisplayName("GET/ departments and filter the result with JsonPath groovy")
    @Test
    public void testFilterResultWithGroovy(){

        JsonPath jp = get("/departments").jsonPath();
        List<Department> allDeps =
                    jp.getList("items.findAll { it.manager_id > 0}", Department.class);

        //allDeps.forEach(System.out::println);
        // what if I just to get List<String> to store Departments

        List<String> depNames = jp.getList("items.department_name");
        System.out.println("depNames = " + depNames);

        // -->> items departement_name (all)
        //-->>  items.findAll{it.manager_id > 0}.department_name (filtered for manager-id more than 0)
        List<String> depNamesFiltered = jp.getList("items.findAll { it.manager_id > 0}.department_name");
        System.out.println("depNamesFiltered = " + depNamesFiltered);

        // Get all depratments ID if its more that 70

        List<Integer> allDepIds = jp.getList("items.department_id");
        System.out.println("allDepIds = " + allDepIds);

        List<Integer> allDepIdsFiltered =
                jp.getList("items.department_id.findAll{ it > 70}");

        System.out.println("allDepIdsFiltered = " + allDepIdsFiltered);

        // what if we have more than one condition for example : department_id between 70 ~ 100

        List<Integer> dep70to100 =
                    jp.getList("items.department_id.findAll{ it >= 70 && it <= 100}");
        System.out.println("dep70to100 = " + dep70to100);

        // get the name of departments if department_id 70 to 100

        List<String> allDep70to100 =
                jp.getList("items.findAll{it.department_id >= 70 && it.department_id <= 100}.department_name");

        System.out.println("allDep70to100 = " + allDep70to100);
        // allDeps70to100 = {Public Relations, Sales, Executive, Finance}

        // findAll --->> all return all matching result
        // find -->> will return first match for the condition

        String dep10 = jp.getString("items.find{ it.department_id == 10}.department_name");
        System.out.println("department 10 name = " + dep10);
        // department 10 name = Administration

        // sum / min / max / collect
        // get the sum of entire department_id
        int sumOfAllDepsID = jp.getInt("items.department_id.sum()");
        int sumOfAllDepsID2 = jp.getInt("items.sum{it.department_id}");

        System.out.println("sumOfAllDepsID = " + sumOfAllDepsID);
        //sumOfAllDepsID = 3017

        System.out.println("sumOfAllDepsID2 = " + sumOfAllDepsID2);
        //sumOfAllDepsID2 = 3017

        // get the lowest department_id
        int lowesDepsID = jp.getInt("items.department_id.min()");
        System.out.println("lowesDepsID = " + lowesDepsID);

        // get the highest department_id
        int highestDepsID = jp.getInt("items.department_id.max()");
        System.out.println("highestDepsID = " + highestDepsID);
        //lowesDepsID = 10
        //highestDepsID = 240

        // print number 5 dep ID
        System.out.println("number 5 dep ID = "+jp.getInt("items.department_id[4]"));
        // print number last dep ID
        System.out.println("last dep ID = "+jp.getInt("items.department_id[-1]"));

        // print 70 ~ 100 dep ID
        System.out.println("70 ~ 100 dep ID = "+jp.getList("items.department_id[7..10]"));
        //                          [70, 80, 90, 100]


    }
}
