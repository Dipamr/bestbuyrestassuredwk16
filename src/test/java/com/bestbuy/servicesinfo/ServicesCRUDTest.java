package com.bestbuy.servicesinfo;

import com.bestbuy.constants.CategoriesEndPoints;
import com.bestbuy.constants.ServicesEndPoints;
import com.bestbuy.model.CategoriesPojo;
import com.bestbuy.model.ServicesPojo;
import com.bestbuy.testbase.TestBase;
import com.bestbuy.utils.TestUtils;
import io.restassured.http.ContentType;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Title;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;

import static org.hamcrest.Matchers.hasValue;

public class ServicesCRUDTest extends TestBase {
    static String name  = "talu"+ TestUtils.getRandomValue();//talu53871
    static String id = "abc4125"+TestUtils.getRandomValue();//22
    static Object servicesID;
    @Title("verify if sevices was created")
    @Test
    public void test001(){
        ServicesPojo servicespojo = new ServicesPojo();
        servicespojo.setName(name);
        servicespojo.setID(id);
        SerenityRest.given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(servicespojo)
                .when()
                .post(ServicesEndPoints.CREATE_NEW_SERVICES)
                .then().log().all().statusCode(201);
    }
    @Title("verify if services was created")
    @Test
    public void test002() {
        String part1 = "data.findAll{it.name='";
        String part2 = "'}.get(0)";
        HashMap<String, ?> servicesMapData = SerenityRest.given()
                .log().all()
                .when()
                .get(ServicesEndPoints.GET_ALL_SERVICES)
                .then().statusCode(200).extract().path(part1 + name + part2);
        Assert.assertThat(servicesMapData, hasValue(name));
        servicesID = servicesMapData.get("id");
        System.out.println(servicesMapData);
    }
    @Title("Update the user and verify the updated information")
    @Test
    public void test003() {
        name = name + "simpa";

        ServicesPojo pojo = new ServicesPojo();
       pojo.setName(name);
       pojo.setID(id);

        SerenityRest.given()
                .log().all()
                .contentType(ContentType.JSON)
                .pathParam("servicesID",servicesID)
                .body(pojo)
                .when()
                .patch(ServicesEndPoints.UPDATE_SERVICES_BY_ID)
                .then().statusCode(200);

        String part1 ="data.findAll{it.name='";
        String part2 ="'}.get(0)";
        HashMap<String,Object> servicesMapData = SerenityRest.given()
                .when()
                .get(ServicesEndPoints.GET_ALL_SERVICES)
                .then().statusCode(200).extract().path(part1 +name + part2);
        Assert.assertThat(servicesMapData,hasValue(name));
        //  categoriesId = categoriesMapData.get("id");
        // System.out.println(categoriesId);

    }
    @Title("Delete the services and verify if the services is deleted")
    @Test
    public void test004(){

        SerenityRest.given()
                .log().all()
                .pathParam("servicesID",servicesID)
                .when()
                .delete(ServicesEndPoints.DELETE_SERVICES_BY_ID)
                .then().statusCode(200);

        SerenityRest.given()
                .pathParam("servicesID",servicesID)
                .when()
                .get(ServicesEndPoints.GET_SINGLE_SERVICES_BY_ID)
                .then().log().all().statusCode(404);

    }
}
