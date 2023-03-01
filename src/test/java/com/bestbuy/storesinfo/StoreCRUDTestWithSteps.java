package com.bestbuy.storesinfo;

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
public class StoreCRUDTestWithSteps extends TestBase {
    static String name = "dezin" + TestUtils.getRandomValue();
    static String type = "small" + TestUtils.getRandomValue();
    static String address = "25 ring road" + TestUtils.getRandomValue();
    static String addree2 = "7 bridge road" + TestUtils.getRandomValue();
    static String city = "toun" + TestUtils.getRandomValue();
    static String state = "MB" + TestUtils.getRandomValue();
    static String zip = "4598723" + TestUtils.getRandomValue();
    static String hours = "Mon: 10-8;Tue:10-2" + TestUtils.getRandomValue();
    static int storesId;
    @Steps
    StoresSteps storesSteps;
    @Title("this will create new store")
    @Test
    public void test001(){
        ValidatableResponse response= storesSteps.createStores(name,type,address,addree2,city,state,zip,hours);
        response.statusCode(201);
    }
    @Title("verify if store is created")
    @Test
    public void test002(){
        HashMap<String,Object> storeMapData=storesSteps.getStoresInfoByName(name);
        Assert.assertThat(storeMapData,hasValue(name));
        storesId=(int)storeMapData.get("id");
        System.out.println(storesId);
    }
    @Title("Update  store information")
    @Test
    public void test003(){
        name = name +"buterly";
        storesSteps.UpdateStores(storesId,name);
        HashMap<String,Object> storeMapData=storesSteps.getStoresInfoByName(name);
        Assert.assertThat(storeMapData,hasValue(name));
    }
    @Title("delete store info storeId and verify its delete")
    @Test
    public void test004(){
        storesSteps.deletestoresInfoByID(storesId).statusCode(200);
        storesSteps.getstoresInfoBystoresId(storesId).statusCode(404);
//        storesSteps.deletestoresInfoByID(storesId).statusCode(200);
//        storesSteps.getstoresInfoBystoresId(storesId).statusCode(404);
    }


    }

