package com.bestbuy.servicesinfo;

import com.bestbuy.constants.ServicesEndPoints;
import com.bestbuy.model.ServicesPojo;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;

import java.util.HashMap;

public class ServicesSteps {


    @Step("getting all information :{0}")
    public ValidatableResponse getAllservicesInfo() {
        return SerenityRest.given()
                .when()
                .get(ServicesEndPoints.GET_ALL_SERVICES)
                .then();

    }

    @Step("creating services with name :{0},id :{1}")
    public ValidatableResponse createservices(String name, String id) {
        ServicesPojo servicespojo = new ServicesPojo();
        servicespojo.setName(name);
        servicespojo.setID(id);
        return SerenityRest.given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(servicespojo)
                .when()
                .post(ServicesEndPoints.CREATE_NEW_SERVICES)
                .then().log().all().statusCode(201);
    }

    @Step("getting services info by name:{0}")
    public HashMap<String, Object> getservicesInfoByName(String name) {
        String part1 ="data.findAll{it.name='";
        String part2 ="'}.get(0)";

        return SerenityRest.given()
                .log().all()
                .when()
                .get(ServicesEndPoints.GET_ALL_SERVICES)
                .then()
                .statusCode(200)
                .extract().path(part1 + name + part2);
    }

    @Step("update services with name :{0},id :{1}")
    public ValidatableResponse updateservices(String name, int servicesId) {
        ServicesPojo servicespojo = new ServicesPojo();
        servicespojo.setName(name);

        return SerenityRest.given()
                .log().all()
                .contentType(ContentType.JSON)
                .pathParam("servicesID",servicesId)
                .body(servicespojo)
                .when()
                .put(ServicesEndPoints.UPDATE_SERVICES_BY_ID)
                .then();
    }

    @Step("deleteing services information with id:{0}")
    public ValidatableResponse deleteservicesInfoByID(int servicesId) {
        return SerenityRest.given()
                .pathParam("servicesID",servicesId)
                .when()
                .delete(ServicesEndPoints.DELETE_SERVICES_BY_ID)
                .then();
    }
    @Step("getting services info by id:{1}")
    public ValidatableResponse getservicesInfoByservicesID(int servicesId){
        return SerenityRest.given()
                .pathParam("servicesID",servicesId)
                .when()
                .get(ServicesEndPoints.GET_SINGLE_SERVICES_BY_ID)
                .then();
    }


}