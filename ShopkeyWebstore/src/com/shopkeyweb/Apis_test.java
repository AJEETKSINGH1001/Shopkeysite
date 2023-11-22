package com.shopkeyweb;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class Apis_test {

    @Test(priority = 1)
    public void testSuccessfulGetApi() {
        // Specify the base URI for the API
        RestAssured.baseURI = "https://reqres.in/api";

        // Send a GET request to the API endpoint
        Response response = RestAssured.get("/users?page=2");

        // Verify the response status code (e.g., 200 for success)
        int statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode, 200);

        // Validate the response content or structure as needed
        // Example: Assert.assertTrue(response.getBody().asString().contains("desired_value"));
    }

    @Test(priority = 2)
    public void testErrorGetApi() {
        // Specify the base URI for the API
        RestAssured.baseURI = "https://reqres.in/api";

        // Send a GET request to a non-existent API endpoint to simulate an error
        Response response = RestAssured.get("/nonexistent");

        int statusCode = response.getStatusCode();

        // Optionally, validate the error message or response structure as needed
        // Assert.assertTrue(response.getBody().asString().contains("Error response"));

        // Use an if-else condition to handle different scenarios
        if (statusCode == 404) {
            // Handle the "Not Found" scenario here
            System.out.println("API endpoint not found.");
            // You can perform additional actions specific to the "Not Found" scenario
        } else {
            // Handle other scenarios (e.g., server errors or other HTTP status codes)
            System.out.println("Other scenario.");
            // You can perform additional actions for other scenarios here
        }
    }
    
    @Test(priority = 3)
    public void testServerErrorsGetApi() {
        // Specify the base URI for the API
        RestAssured.baseURI = "https://reqres.in/api";

        // Send a GET request to an API endpoint that triggers a server error (e.g., 500 Internal Server Error)
        Response response = RestAssured.get("/server-error-endpoint");

        // Verify the response status code (e.g., 5xx for server errors)
        int statusCode = response.getStatusCode();
        if (statusCode >= 500 && statusCode < 600) {
            // Handle the server error scenario here
            System.out.println("Server Error occurred.");
            // You can perform additional actions specific to server errors
        } else {
            // Handle other scenarios (e.g., success or other non-server errors)
            System.out.println("Test Passed");
            // You can perform additional actions for non-server errors here
        }
    }
}
