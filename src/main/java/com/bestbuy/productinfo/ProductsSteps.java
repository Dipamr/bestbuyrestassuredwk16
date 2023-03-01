package com.bestbuy.productinfo;

import com.bestbuy.constants.ProductEndPoints;
import com.bestbuy.model.ProductsPojo;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;

import javax.xml.ws.Endpoint;
import java.util.HashMap;

public class ProductsSteps {

    @Step("getting all information :{0}")
    public ValidatableResponse getAllproductsInfo() {
        return SerenityRest.given()
                .when()
                .get(ProductEndPoints.GET_ALL_PRODUCTS)
                .then();
    }

    @Step("creating products with name :{0},type :{1},manufacturer :{2},image :{3},model :{4},url : {5},description :{6},upc :{7}")
    public ValidatableResponse createproducts(String name, String type, String manufacturer, String image, String model, String url, String description, String upc) {
        ProductsPojo productsPojo = new ProductsPojo();
        productsPojo.setName(name);
        productsPojo.setType(type);
        productsPojo.setManufacturer(manufacturer);
        productsPojo.setImage(image);
        productsPojo.setModel(model);
        productsPojo.setUrl(url);
        productsPojo.setDescription(description);
        productsPojo.setUpc(upc);

        productsPojo.setDescription(description);
        return SerenityRest.given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(productsPojo)
                .when()
                .post(ProductEndPoints.CREATE_NEW_PRODUCTS)
                .then().statusCode(201);

    }

    @Step("getting products info by name:{0}")
    public HashMap<String, Object> getproductsInfoByName(String name) {
        String part1 = "data.findAll{it.name='";
        String part2 = "'}.get(0)";

        return SerenityRest.given()
                .log().all()
                .when()
                .get(ProductEndPoints.GET_ALL_PRODUCTS)
                .then()
                .statusCode(200)
                .extract().path(part1 + name + part2);
    }

    @Step("update products with name :{0},id :{1}")
    public ValidatableResponse updateproducts(int productId,String name) {
        ProductsPojo productsPojo = new ProductsPojo();
        productsPojo.setName(name);

        return SerenityRest.given()
                .log().all()
                .contentType(ContentType.JSON)
                .pathParam("productsID", productId)
                .body(productsPojo)
                .when()
                .put(ProductEndPoints.UPDATE_PROUCT_BY_ID)
                .then();
    }

    @Step("deleteing products information with Id:{1}")
    public ValidatableResponse deleteproductsInfoByID(int productsId) {
        return SerenityRest.given()
                .pathParam("productsID", productsId)
                .when()
                .delete(ProductEndPoints.DELETE_PRODUCT_BY_ID)
                .then();
    }
    @Step("getting products info By Id:{1}")
    public ValidatableResponse getproductsInfoByproductsId(int productsId){
        return SerenityRest.given()
                .pathParam("productsID",productsId)
                .when()
                .get(ProductEndPoints.GET_SINGLE_PRODUCT_BY_ID)
                .then();
    }

}
