package com.shopkeyweb;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class Shopkeyurlvalidations {

    private WebDriver driver;
    private ExtentReports extent;
    private ExtentTest test;

    @BeforeSuite
    public void setUp() {
    	// Create a timestamp for unique report names
    	String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
    	String reportFileName = "test-output/ShopkeyWebStore_TestReport_" + timestamp + ".html";

    	// Initialize ExtentReports and ExtentHtmlReporter
    	ExtentSparkReporter htmlReporter = new ExtentSparkReporter(reportFileName);
    	extent = new ExtentReports();
    	extent.attachReporter(htmlReporter);
    	//With this corrected code, your Extent report file will be generated with a unique timestamp in the filename, ensuring that each test run produces a distinct report.
    }

    @BeforeClass
    public void initializeWebDriver() {
    	//initializing the driver
    	System.setProperty("webdriver.chrome.driver", "C:\\Users\\Ajeet\\Documents\\chromedriver.exe");
        driver = new ChromeDriver();
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @AfterSuite
//    public void flushReport() {
//        //extent.flush();
//    }

    @Test(priority = 1)
    public void testURLAccessibility() {
        test = extent.createTest("URL Accessibility Test");
        driver.get("https://test0506store1b.goshopkey.com/");
        Assert.assertEquals(driver.getTitle(), "Home | Store");
        test.log(Status.PASS, "URL is accessible, and title is as expected.");
    }

    @Test(priority = 1)
    public void testURL() {
        String urlToTest = "https://test0506store1b.goshopkey.com/";

        try {
            // Create a URL object
            URL url = new URL(urlToTest);

            // Open a connection to the URL
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Get the HTTP response code
            int responseCode = connection.getResponseCode();

            // Check for 400 or 500 error codes
            if (responseCode >= 400 && responseCode < 600) {
                // Report a failure if a 4xx or 5xx error code is received
                Assert.fail("Received a " + responseCode + " HTTP error for URL: " + urlToTest);
            }
        } catch (IOException e) {
            // Handle any IO exceptions
            e.printStackTrace();
            Assert.fail("An exception occurred while testing the URL.");
        }
    }

    @Test(priority = 3)
    public void testLoginFunctionality() {
        test = extent.createTest("Login Functionality Test");
        driver.get("https://test0506store1b.goshopkey.com/");
        WebElement loginButton = driver.findElement(By.id("loginButton"));
        loginButton.click();
        WebElement loginForm = driver.findElement(By.id("loginForm"));
        Assert.assertTrue(loginForm.isDisplayed());
        test.log(Status.FAIL, "Element 'nonExistentElement' not found");
    }

    @Test(priority = 4)
    public void testSearchFunctionality() {
        test = extent.createTest("Search Functionality Test");
        driver.get("https://test0506store1b.goshopkey.com/");
        WebElement searchInput = driver.findElement(By.id("searchInput"));
        searchInput.sendKeys("Test Product");
        WebElement searchButton = driver.findElement(By.id("searchButton"));
        searchButton.click();
        WebElement searchResults = driver.findElement(By.id("searchResults"));
        Assert.assertTrue(searchResults.isDisplayed());
        test.log(Status.FAIL, "Element 'nonExistentElement' not found");
    }

    // Add more test cases for various scenarios and functionalities

}
