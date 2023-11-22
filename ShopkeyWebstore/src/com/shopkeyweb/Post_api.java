package com.shopkeyweb;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

public class Post_api {

    @Test(priority = 1)
    public void testPostApiWithValidData() {
        RestAssured.baseURI = "https://reqres.in/api";

        JSONObject request = new JSONObject();
        request.put("name", "John");
        request.put("job", "Engineer");

        Response response = RestAssured
            .given()
            .contentType(ContentType.JSON)
            .body(request.toJSONString())
            .post("/users");

        int statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode, 201);

        String responseBody = response.getBody().asString();
        Assert.assertTrue(responseBody.contains("John"));
        Assert.assertTrue(responseBody.contains("Engineer"));
    }

    @Test(priority = 2)
    public void testPostApiWithInvalidData() {
        RestAssured.baseURI = "https://reqres.in/api";

        JSONObject request = new JSONObject();
        request.put("name", "John");

        Response response = RestAssured
            .given()
            .contentType(ContentType.JSON)
            .body(request.toJSONString())
            .post("/users");

        int statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode, 400);
    }

    @Test(priority = 3)
    public void testPostApiWithEmptyData() {
        RestAssured.baseURI = "https://reqres.in/api";

        Response response = RestAssured
            .given()
            .contentType(ContentType.JSON)
            .body(new JSONObject().toJSONString())
            .post("/users");

        int statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode, 400);
    }
}
