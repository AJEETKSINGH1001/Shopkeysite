package com.shopkeyweb;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class Shopkeywebstore_homepage {
    private WebDriver driver;
    private ExtentReports extent;
    private ExtentTest test;
    private int runCounter = 0;

    @BeforeMethod
    public void setUp() {
        // Increment the run counter
        runCounter++;

        // Generate a timestamp for the report file name
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        Date date = new Date();
        String timestamp = dateFormat.format(date);

        // Set the report file name with the timestamp and run number
        String reportFileName = "ShopkeyWebstore_Reoprt_" + timestamp + "_Run" + runCounter + ".html";

        // Initialize Extent Reports
        extent = new ExtentReports();
        ExtentSparkReporter htmlReporter = new ExtentSparkReporter("test-output/" + reportFileName);
        extent.attachReporter(htmlReporter);

        // Create a test
        test = extent.createTest("Openingshopkeywebstore", "Verify that the website opens successfully (Run " + runCounter + ")");
        //test = extent.createTest("Homepage", "Home page loaded.(Run " + runCounter + ")");
    }

    @Test
    public void Openingshopkeywebstore() {
        try {
        	System.setProperty("webdriver.chrome.driver", "C:\\Users\\Ajeet\\Documents\\chromedriver.exe");
            // Initialize WebDriver and open the website
            driver = new ChromeDriver();
            driver.get("https://test0506store1b.goshopkey.com/");

            // Perform your validations here
            String expectedTitle = "Home | Store";
            String actualTitle = driver.getTitle();
            if (expectedTitle.equals(actualTitle)) {
                test.log(Status.PASS, "Website opened successfully.");
            } else {
                test.log(Status.FAIL, "Website did not open successfully.");
            }
        } catch (Exception e) {
            test.log(Status.FAIL, "An error occurred: " + e.getMessage());
        } finally {
            if (driver != null) {
                // Close the browser
                driver.quit();
            }
        }
    }
    
    @Test
    public void Homepage(){
    	test = extent.createTest("Home page Test");
        // Initialize the WebDriver
        WebDriver driver = new ChromeDriver();
    	//clicking the home page to verify the link of the page which opens
    	 driver.get("https://test0506store1b.goshopkey.com/");
    	 WebElement homePageLink = driver.findElement(By.xpath("//div[@id='__next']/div[2]/div/header/nav/div[4]/ul/li/button")); // Replace with the actual link text

          // Click the "home page" link
         homePageLink.click();
         String currentPageUrl = driver.getCurrentUrl();
         String expectedUrl = "https://test0506store1b.goshopkey.com/"; // Replace with the expected URL after clicking the link
         if (currentPageUrl.equals(expectedUrl)) {
             System.out.println("Validation Passed: Clicking 'Beranda' link navigated to the expected URL.");
         } else {
             System.err.println("Validation Failed: Clicking 'Beranda' link did not navigate to the expected URL.");
         }
         test.log(Status.INFO, "Home page loaded.");
         // Optionally, you can add more validations or test steps as needed.
         // Close the WebDriver
         driver.quit();	
    	
    }
    
    @Test
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


    @AfterMethod
    public void tearDown() {
        // Flush the Extent Reports and save the report
        extent.flush();
    }
}
