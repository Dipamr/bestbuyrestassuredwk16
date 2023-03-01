package com.bestbuy.productinfo;

import com.bestbuy.constants.CategoriesEndPoints;
import com.bestbuy.constants.ProductEndPoints;
import com.bestbuy.model.CategoriesPojo;
import com.bestbuy.model.ProductsPojo;
import com.bestbuy.testbase.TestBase;
import com.bestbuy.utils.TestUtils;
import io.restassured.http.ContentType;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Title;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;

import static org.hamcrest.Matchers.hasValue;

public class ProductCRUDTest extends TestBase {
    static String name = "Kenwood A4" + TestUtils.getRandomValue();
    static Object productsId;

    @Title("This will create a new products")
    @Test
    public void test001() {
        ProductsPojo pojo = new ProductsPojo();
        pojo.setName("Kenwood K48");
        pojo.setManufacturer("Kenwood");
        pojo.setDescription("light weight");
        pojo.setType("normal");
        pojo.setModel("BN0987K09");
        // pojo.setPrice(8.99);
       // pojo.setShipping(0);
        pojo.setUpc("5689712");
        pojo.setImage("img/jk");
        pojo.setUrl("amazon.co.uk");

        SerenityRest.given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(pojo)
                .when()
                .post(ProductEndPoints.CREATE_NEW_PRODUCTS)
                .then().log().all().statusCode(201);
    }

    @Title("verify if products was created")
    @Test
    public void test002() {
        String part1 = "data.findAll{it.name='";
        String part2 = "'}.get(0)";
        HashMap<String, ?> ProductsMapData = SerenityRest.given()
                .log().all()
                .when()
                .get(ProductEndPoints.GET_ALL_PRODUCTS)
                .then().statusCode(200).extract().path(part1 + name + part2);
        Assert.assertThat(ProductsMapData, hasValue(name));
        productsId = ProductsMapData.get("id");
        System.out.println(productsId);
    }

    @Title("Update the user and verify the updated information")
    @Test
    public void test003() {
        name = name + "samu";
        ProductsPojo pojo = new ProductsPojo();
        pojo.setName("Kenwood 87");
        pojo.setManufacturer("Kensz");
        pojo.setDescription("heavy weight");
        pojo.setType("simple");
        pojo.setModel("BK09UY43");
        // pojo.setPrice(8.99);
       // pojo.setShipping(0);
        pojo.setUpc("56231");
        pojo.setImage("img/rj");
        pojo.setUrl("liba.co.uk");

        SerenityRest.given()
                .log().all()
                .contentType(ContentType.JSON)
                .pathParam("productsID",productsId)
                .body(pojo)
                .when()
                .patch(ProductEndPoints.UPDATE_PROUCT_BY_ID)
                .then().statusCode(200);

        String part1 ="data.findAll{it.name='";
        String part2 ="'}.get(0)";
        HashMap<String,Object> productsMapData = SerenityRest.given()
                .when()
                .get(ProductEndPoints.GET_ALL_PRODUCTS)
                .then().statusCode(200).extract().path(part1 +name + part2);
        Assert.assertThat(productsMapData,hasValue(name));
    }
    @Title("Delete the products and verify if the products is deleted")
    @Test
    public void test004(){

        SerenityRest.given()
                .pathParam("productsID",productsId)
                .when()
                .delete(ProductEndPoints.DELETE_PRODUCT_BY_ID)
                .then().log().all().statusCode(200);

        SerenityRest.given()
                .pathParam("productsID",productsId)
                .when()
                .get(ProductEndPoints.GET_SINGLE_PRODUCT_BY_ID)
                .then().log().all().statusCode(404);

    }
}

