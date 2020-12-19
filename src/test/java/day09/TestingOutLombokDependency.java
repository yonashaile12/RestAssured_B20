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

        allDeps.forEach(System.out::println);
        // what if I just to get List<String> to store Departments

        List<String> depNames = jp.getList("items.department_name");
        System.out.println("depNames = " + depNames);

        // -->> items departement_name (all)
        //-->>  items.findAll{it.manager_id > 0}.department_name (filtered for manager-id more than 0)
        List<String> depNamesFiltered = jp.getList("items.findAll { it.manager_id > 0}.department_name");
        System.out.println("depNamesFiltered = " + depNamesFiltered);




    }
}
