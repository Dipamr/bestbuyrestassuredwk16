package com.bestbuy.storesinfo;

import com.bestbuy.constants.ProductEndPoints;
import com.bestbuy.constants.StoresEndPoints;
import com.bestbuy.model.ProductsPojo;
import com.bestbuy.model.StoresPojo;
import com.bestbuy.testbase.TestBase;
import com.bestbuy.utils.TestUtils;
import io.restassured.http.ContentType;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Title;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;

import static org.hamcrest.Matchers.hasValue;

public class StoresCURDTest extends TestBase {
    static String name = "Ramba" + TestUtils.getRandomValue();
    static Object storesID;

    @Title("This will create a new stores")
    @Test
    public void test001() {
        StoresPojo pojo = new StoresPojo();
        pojo.setName("Reny");
        pojo.setAddress("21 ring road");
        pojo.setAddress2("45 wings road");
        pojo.setCity("Anja");
        pojo.setState("zomgu");
        pojo.setType("tiny");
        pojo.setZip("23561");
       // pojo.setLat(5219);
      //  pojo.setLng(631);
        pojo.setHours("Mon:10-4,Fri:11-2");

        SerenityRest.given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(pojo)
                .when()
                .post(StoresEndPoints.CREATE_NEW_STORES)
                .then().log().all().statusCode(201);
    }

    @Title("verify if stores was created")
    @Test
    public void test002() {
        String part1 = "data.findAll{it.name='";
        String part2 = "'}.get(0)";
        HashMap<String, ?> StoresMapData = SerenityRest.given()
                .log().all()
                .when()
                .get(StoresEndPoints.GET_ALL_STORES)
                .then().statusCode(200).extract().path(part1 + name + part2);
        Assert.assertThat(StoresMapData, hasValue(name));
        storesID = StoresMapData.get("id");
        System.out.println(storesID);
    }

    @Title("Update the user and verify the updated information")
    @Test
    public void test003() {
        name = name + "sril";
        StoresPojo pojo = new StoresPojo();
        pojo.setName("Zingy");
        pojo.setAddress("56 ring road");
        pojo.setAddress2("41 huji road");
        pojo.setCity("Majurai");
        pojo.setState("ringui");
        pojo.setType("little");
        pojo.setZip("56847");
       // pojo.setLat(2546);
      //  pojo.setLng(982);
        pojo.setHours("Mon:11-2,Fri:10-5");

        SerenityRest.given()
                .log().all()
                .contentType(ContentType.JSON)
                .pathParam("storesID",storesID)
                .body(pojo)
                .when()
                .patch(StoresEndPoints.UPDATE_STORES_BY_ID)
                .then().statusCode(200);

        String part1 ="data.findAll{it.name='";
        String part2 ="'}.get(0)";
        HashMap<String,Object> storesMapData = SerenityRest.given()
                .when()
                .get(StoresEndPoints.GET_ALL_STORES)
                .then().statusCode(200).extract().path(part1 +name + part2);
        Assert.assertThat(storesMapData,hasValue(name));

    }
    @Title("Delete the stores and verify if the stores is deleted")
    @Test
    public void test004(){

        SerenityRest.given()
                .pathParam("storesID",storesID)
                .when()
                .delete(StoresEndPoints.DELETE_STORES_BY_ID)
                .then().log().all().statusCode(200);

        SerenityRest.given()
                .pathParam("storesID",storesID)
                .when()
                .get(StoresEndPoints.GET_SINGLE_STORES_BY_ID)
                .then().log().all().statusCode(404);

    }
}

