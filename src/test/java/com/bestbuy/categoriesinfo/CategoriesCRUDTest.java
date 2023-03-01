package com.bestbuy.categoriesinfo;

import com.bestbuy.constants.CategoriesEndPoints;
import com.bestbuy.model.CategoriesPojo;
import com.bestbuy.testbase.TestBase;
import com.bestbuy.utils.PropertyReader;
import com.bestbuy.utils.TestUtils;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Title;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.xml.ws.Endpoint;
import java.util.HashMap;

import static org.hamcrest.Matchers.hasValue;

@RunWith(SerenityRunner.class)
public class CategoriesCRUDTest extends TestBase {

    static String name = "ringan" + TestUtils.getRandomValue();
    static String id = "abc78921" + TestUtils.getRandomValue();
    static Object categoriesId;


    @Title("This will create a new categories")
    @Test
    public void test001() {
        CategoriesPojo categoriespojo = new CategoriesPojo();
        categoriespojo.setName(name);
        categoriespojo.setID(id);
        SerenityRest.given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(categoriespojo)
                .when()
                .post(CategoriesEndPoints.CREATE_NEW_CATEGORIES)
                .then().log().all().statusCode(201);
    }

    @Title("verify if categories was created")
    @Test
    public void test002() {
        String part1 = "data.findAll{it.name='";
        String part2 = "'}.get(0)";
        HashMap<String, ?> categoriesMapData = SerenityRest.given()
                .log().all()
                .when()
                .get(CategoriesEndPoints.GET_ALL_CATEGORIES)
                .then().statusCode(200).extract().path(part1 + name + part2);
        Assert.assertThat(categoriesMapData, hasValue(name));
        categoriesId = categoriesMapData.get("id");
        System.out.println(categoriesId);
    }

    @Title("Update the user and verify the updated information")
    @Test
    public void test003() {
        name = name + "rimpu";

        CategoriesPojo pojo = new CategoriesPojo();
        pojo.setName(name);
        pojo.setID(id);

        SerenityRest.given()
                .log().all()
                .contentType(ContentType.JSON)
                .pathParam("categoriesID",categoriesId)
                .body(pojo)
                .when()
                .patch(CategoriesEndPoints.UPDATE_CATEGORIES_BY_ID)
                .then().statusCode(200);

        String part1 ="data.findAll{it.name='";
        String part2 ="'}.get(0)";
        HashMap<String,Object> categoriesMapData = SerenityRest.given()
                .when()
                .get(CategoriesEndPoints.GET_ALL_CATEGORIES)
                .then().statusCode(200).extract().path(part1 +name + part2);
        Assert.assertThat(categoriesMapData,hasValue(name));
        //  categoriesId = categoriesMapData.get("id");
        // System.out.println(categoriesId);

    }
    @Title("Delete the categories and verify if the categories is deleted")
    @Test
    public void test004(){

        SerenityRest.given()
                .pathParam("categoriesID",categoriesId)
                .when()
                .delete(CategoriesEndPoints.DELETE_CATEGORIES_BY_ID)
                .then().log().all().statusCode(200);

        SerenityRest.given()
                .pathParam("categoriesID",categoriesId)
                .when()
                .get(CategoriesEndPoints.GET_SINGLE_CATEGORIES_BY_ID)
                .then().log().all().statusCode(404);

    }
}
