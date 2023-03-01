package com.bestbuy.categoriesinfo;

import com.bestbuy.constants.CategoriesEndPoints;
import com.bestbuy.model.CategoriesPojo;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;

import java.util.HashMap;

public class CategoriesSteps {



    @Step("getting all information :{0}")
    public ValidatableResponse getAllcategoriesInfo() {
        return SerenityRest.given()
                .when()
                .get(CategoriesEndPoints.GET_ALL_CATEGORIES)
                .then();

    }

    @Step("creating categories with name :{0},id: {1}")
    public ValidatableResponse createCategories(String name, String id) {
        CategoriesPojo categoriespojo = new CategoriesPojo();
        categoriespojo.setName(name);
        categoriespojo.setID(id);
        return SerenityRest.given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(categoriespojo)
                .when()
                .post(CategoriesEndPoints.CREATE_NEW_CATEGORIES)
                .then().statusCode(201);
    }

    @Step("getting categories info by name:{0}")
    public HashMap<String, Object> getcategoriesInfoByName(String name) {
        String part1 = "data.findAll{it.name='";
        String part2 = "'}.get(0)";

        return SerenityRest.given()
                .log().all()
                .when()
                .get(CategoriesEndPoints.GET_ALL_CATEGORIES)
                .then()
                .statusCode(200)
                .extract().path(part1 + name + part2);
    }

    @Step("update categories with name :{0},id: {1}")
    public ValidatableResponse updatecategories(String name, String id) {
        CategoriesPojo categoriespojo = new CategoriesPojo();
        categoriespojo.setName(name);

        return SerenityRest.given()
                .log().all()
                .contentType(ContentType.JSON)
                .pathParam("categoriesID", id)
                .body(categoriespojo)
                .when()
                .patch(CategoriesEndPoints.UPDATE_CATEGORIES_BY_ID)
                .then();

    }
    @Step("deleteing categories information with id:{1}")
    public ValidatableResponse deletecategoriesInfoByID(String id){
        return SerenityRest.given()
                .pathParam("categoriesID",id)
                .when()
                .delete(CategoriesEndPoints.DELETE_CATEGORIES_BY_ID)
                .then();
    }
    @Step("getting categories info By id:{1}")
    public ValidatableResponse getcategoriesInfoBycategoriesId(String id){
        return SerenityRest.given()
                .pathParam("categoriesID", id)
                .when()
                .get(CategoriesEndPoints.GET_SINGLE_CATEGORIES_BY_ID)
                .then();
    }

}
