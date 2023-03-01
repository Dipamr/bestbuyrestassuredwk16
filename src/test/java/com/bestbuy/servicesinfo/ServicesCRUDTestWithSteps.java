package com.bestbuy.servicesinfo;

import com.bestbuy.testbase.TestBase;
import com.bestbuy.utils.TestUtils;
import cucumber.api.java.ca.I;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Title;
import org.jruby.ext.ripper.Warnings;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;

import static com.bestbuy.servicesinfo.ServicesCRUDTest.servicesID;
import static org.hamcrest.Matchers.hasValue;
@RunWith(SerenityRunner.class)
public class ServicesCRUDTestWithSteps extends TestBase {
    static String name = "talu" + TestUtils.getRandomValue();//talu53871
    static String id = "abc4125" + TestUtils.getRandomValue();//22
    static int servicesId;
    @Steps
    ServicesSteps servicesSteps;

    @Title("This will create a new services")
    @Test
    public void test001() {
        ValidatableResponse response = servicesSteps.createservices(name,id);
        response.statusCode(201);
    }

    @Title("verify if services is created")
    @Test
    public void test002() {
        HashMap<String, Object> servicesMapData = servicesSteps.getservicesInfoByName(name);
        Assert.assertThat(servicesMapData, hasValue(name));
        servicesId = (int) servicesMapData.get("id");
        System.out.println(servicesId);

    }

    @Title("update the user information")
    @Test
    public void test003() {
        name = name + "toni";

        servicesSteps.updateservices(name, servicesId);
        HashMap<String, Object> servicesMap = servicesSteps.getservicesInfoByName(name);
        Assert.assertThat(servicesMap, hasValue(name));
    }

    @Title("Delete services info by servicesID and verify its deleted")
    @Test
    public void test004() {
        servicesSteps.deleteservicesInfoByID(servicesId).statusCode(200);
        servicesSteps.getservicesInfoByservicesID(servicesId).statusCode(404);
    }
}