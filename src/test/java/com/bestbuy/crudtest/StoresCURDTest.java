package com.bestbuy.crudtest;

import com.bestbuy.model.StorePojo;
import com.bestbuy.utils.TestUtils;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

import static io.restassured.RestAssured.given;

public class StoresCURDTest {
    static ValidatableResponse response;
    static int storeId;
    static String name = "mani" + TestUtils.getRandomValue();
    static String UpdatedName = "UpdatedName" + TestUtils.getRandomValue();
    static String type = "Testing";
    static String address = "home";

    static String address2 = "sudbury";
    static String city = "Diu";

    static String state = "Gujarat";
    static String zip = "362530";

    static List<String> services;

    @BeforeClass
    public static void inIt() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 3030;
        //  RestAssured.basePath = "/stores";
    }

    @Test
    public void test001() {

        StorePojo storePojo = new StorePojo();
        storePojo.setName(name);
        storePojo.setType(type);
        storePojo.setAddress(address);
        storePojo.setAddress2(address2);
        storePojo.setCity(city);
        storePojo.setState(state);
        storePojo.setZip(zip);

        Response response = given()
                .header("Content-Type", "application/json")
                .when()
                .body(storePojo)
                .post("/stores");
        response.then().log().all().statusCode(201);
        String responseBody = response.getBody().asString();
        JsonPath jsonPath = new JsonPath(responseBody);
        storeId = jsonPath.getInt("id");
        //  System.out.println("=================" + user_id);
    }

    @Test
    public void test002() {
        System.out.println("=============" + storeId);
        response = given()
                .when()
                .get("/stores/" + storeId)
                .then().statusCode(200);
    }

    @Test
    public void test003() {

        StorePojo storePojo = new StorePojo();
        storePojo.setName(UpdatedName);
        storePojo.setType(type);
        storePojo.setAddress(address);
        storePojo.setAddress2(address2);
        storePojo.setCity(city);
        storePojo.setState(state);
        storePojo.setZip(zip);

        Response response = given()
                .header("Content-Type", "application/json")
                .when()
                .body(storePojo)
                .patch("/stores/" + storeId);
        response.then().log().all().statusCode(200);

    }

    @Test
    public void test004() {

        given()
                .pathParam("id", storeId)
                .when()
                .delete("/stores/{id}")
                .then()
                .statusCode(200);
    }
}
