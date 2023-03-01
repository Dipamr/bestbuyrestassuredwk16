package com.bestbuy.productinfo;

import com.bestbuy.Storesinfo.StoresSteps;
import com.bestbuy.testbase.TestBase;
import com.bestbuy.utils.TestUtils;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Title;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;

import static org.hamcrest.Matchers.hasValue;

@RunWith(SerenityRunner.class)
public class ProductCRUDTestWithSteps extends TestBase {
    static String name = "Kenwood 66" + TestUtils.getRandomValue();
    static String type = "heavy" + TestUtils.getRandomValue();
    static String manufacturer = "toshiba" + TestUtils.getRandomValue();
    static String image = "img/li" + TestUtils.getRandomValue();
    static String model = "45k6203" + TestUtils.getRandomValue();
    static String url = "toshiba.com" + TestUtils.getRandomValue();
    static String description = "golden" + TestUtils.getRandomValue();
    static String upc = "23501469" + TestUtils.getRandomValue();

    static int productsId;
    @Steps
    ProductsSteps productsSteps;

    @Title("this will create new store")
    @Test
    public void test001() {
        ValidatableResponse response = productsSteps.createproducts(name, type, manufacturer, image, model, url, description, upc);
        response.statusCode(201);
    }

    @Title("verify if products is created")
    @Test
    public void test002() {
        HashMap<String, Object> productsMapData = productsSteps.getproductsInfoByName(name);
        Assert.assertThat(productsMapData, hasValue(name));
        productsId = (int) productsMapData.get("id");
        System.out.println(productsId);
    }

    @Title("Update  products information")
    @Test
    public void test003() {
        name = name + "tosi 78";
        productsSteps.updateproducts(productsId, name);
        HashMap<String, Object> productsMapData = productsSteps.getproductsInfoByName(name);
        Assert.assertThat(productsMapData, hasValue(name));
    }

    @Title("delete store info storeId and verify its delete")
    @Test
    public void test004() {
        productsSteps.deleteproductsInfoByID(productsId).statusCode(200);
        productsSteps.getproductsInfoByproductsId(productsId).statusCode(404);
    }


}

