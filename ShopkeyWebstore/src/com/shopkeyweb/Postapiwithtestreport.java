package com.shopkeyweb;

import org.json.simple.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class Postapiwithtestreport {

    private WebDriver driver;
    private ExtentReports extent;
    private ExtentTest test;

    @BeforeClass
    public void setUp() {
        // Set up WebDriver (you may need to set the path to your ChromeDriver executable)
    	System.setProperty("webdriver.chrome.driver", "C:\\Users\\Ajeet\\Documents\\chromedriver.exe");
        driver = new ChromeDriver();

        // Initialize Extent Reports
        ExtentSparkReporter reporter = new ExtentSparkReporter("Api_test-report.html");
        extent = new ExtentReports();
        extent.attachReporter(reporter);
    }

    @Test(priority = 1)
    public void testApiWithValidData() {
        // Create a new test entry in the report
        test = extent.createTest("API Test with Valid Data");

        // API Testing
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

        // Web Testing
        driver.get("https://reqres.in/");  // Replace with the URL of your web application
        WebElement element = driver.findElement(By.xpath(".//*[normalize-space(text()) and normalize-space(.)='Fake data'])[1]/preceding::h2[1]"));  // Replace with your element locator
        Assert.assertTrue(element.isDisplayed());

        // Log test status to the report
        if (statusCode == 201) {
            test.log(Status.PASS, "API test passed.");
        } else {
            test.log(Status.FAIL, "API test failed.");
        }
    }

    @Test(priority = 2)
    public void testApiWithInvalidData() {
        // Create a new test entry in the report
        test = extent.createTest("API Test with Invalid Data");

        // API Testing
        RestAssured.baseURI = "https://reqres.in/api";
        JSONObject request = new JSONObject();
        request.put("name", "John");  // Missing "job"

        Response response = RestAssured
            .given()
            .contentType(ContentType.JSON)
            .body(request.toJSONString())
            .post("/users");

        int statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode, 400);

        // Web Testing
        driver.get("https://reqres.in/");  // Replace with the URL of your web application
        WebElement element = driver.findElement(By.xpath(".//*[normalize-space(text()) and normalize-space(.)='Fake data'])[1]/preceding::h2[1]"));  // Replace with your element locator
        Assert.assertTrue(element.isDisplayed());

        // Log test status to the report
        if (statusCode == 400) {
            test.log(Status.PASS, "API test passed.");
        } else {
            test.log(Status.FAIL, "API test failed.");
        }
    }

    @AfterClass
    public void tearDown() {
        // Close the WebDriver instance
        driver.quit();

        // Flush the extent report to generate the final report
        extent.flush();
    }
}
