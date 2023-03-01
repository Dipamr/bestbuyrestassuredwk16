package com.bestbuy.Storesinfo;

import com.bestbuy.constants.ServicesEndPoints;
import com.bestbuy.constants.StoresEndPoints;
import com.bestbuy.model.StoresPojo;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;

import javax.xml.soap.Name;
import javax.xml.ws.Endpoint;
import java.util.HashMap;

public class StoresSteps {

    @Step("getting all information:{0}")
    public ValidatableResponse getAllStudentInfo() {
        return SerenityRest.given()
                .when()
                .get(StoresEndPoints.GET_ALL_STORES)
                .then();
    }

    @Step("creating store with name :{0},type :{1},address :{2},address2 :{3},city :{4},state :{5},zip :{6},hours :{7}")
    public ValidatableResponse createStores(String name, String type, String address, String address2, String city, String state, String zip, String hours) {

        StoresPojo storespojo = new StoresPojo();
        storespojo.setName(name);
        storespojo.setType(type);
        storespojo.setAddress(address);
        storespojo.setAddress2(address2);
        storespojo.setCity(city);
        storespojo.setState(state);
        storespojo.setZip(zip);
        storespojo.setHours(hours);

        return SerenityRest.given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(storespojo)
                .when()
                .post(StoresEndPoints.CREATE_NEW_STORES)
                .then().statusCode(201);
    }

    @Step("getting stores info by name :{0}")
    public HashMap<String, Object> getStoresInfoByName(String name) {
        String part1 ="data.findAll{it.name='";
        String part2 ="'}.get(0)";
        return SerenityRest.given()
                .log().all()
                .when()
                .get(StoresEndPoints.GET_ALL_STORES)
                .then()
                .statusCode(200)
                .extract().path(part1 + name + part2);
    }

    @Step("Update store with name :{0},id{1}")
    public ValidatableResponse UpdateStores(int storesId,String name) {
        StoresPojo storespojo = new StoresPojo();
        storespojo.setName(name);

        return SerenityRest.given()
                .log().all()
                .contentType(ContentType.JSON)
                .pathParam("storesID", storesId)
                .body(storespojo)
                .when()
                .put(StoresEndPoints.UPDATE_STORES_BY_ID)
                .then();


    }

    @Step("deleteing stores information with Id:{0}")
    public ValidatableResponse deletestoresInfoByID(int storesId) {
        return SerenityRest.given()
                .pathParam("storesID", storesId)
                .when()
                .delete(StoresEndPoints.DELETE_STORES_BY_ID)
                .then();
    }

    @Step("getting stores info By Id:{0}")
    public ValidatableResponse getstoresInfoBystoresId(int storesId) {
        return SerenityRest.given()
                .pathParam("storesID", storesId)
                .when()
                .get(StoresEndPoints.GET_SINGLE_STORES_BY_ID)
                .then();
    }

    }

