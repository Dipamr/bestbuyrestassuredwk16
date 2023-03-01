package com.bestbuy.categoriesinfo;

import com.bestbuy.testbase.TestBase;
import com.bestbuy.utils.TestUtils;
import cucumber.api.java.ca.I;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Title;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;

import static com.bestbuy.categoriesinfo.CategoriesCRUDTest.categoriesId;
import static javax.xml.ws.RespectBindingFeature.ID;
import static org.hamcrest.Matchers.hasValue;

@RunWith(SerenityRunner.class)
public class CategoriesCRUDTestWithSteps extends TestBase {

    static String name = "elanic" + TestUtils.getRandomValue();
     static String id = "abc4521" + TestUtils.getRandomValue();
    static String categoriesId;
    @Steps
    CategoriesSteps categoriesSteps;

    @Title("This will create a new categories")
    @Test
    public void test001() {
        ValidatableResponse response = categoriesSteps.createCategories(name,id);
        response.statusCode(201);

    }

    @Title("verify if categories is created")
    @Test
    public void test002() {
        HashMap<String, Object> categoriesMapData = categoriesSteps.getcategoriesInfoByName(name);
        Assert.assertThat(categoriesMapData, hasValue(name));
        categoriesId = (String) categoriesMapData.get("id");
        System.out.println(categoriesId);

    }

    @Title("update the user information")
    @Test
    public void test003() {
        name = name + "rani";
        categoriesSteps.updatecategories(name, id);
        HashMap<String, Object> categoriesMap = categoriesSteps.getcategoriesInfoByName(name);
        Assert.assertThat(categoriesMap, hasValue(name));
    }

    @Title("Delete categories info by categoriesID and verify its deleted")
    @Test
    public void test004() {

        categoriesSteps.deletecategoriesInfoByID(categoriesId).statusCode(200);
        categoriesSteps.getcategoriesInfoBycategoriesId(categoriesId).statusCode(404);
    }
}