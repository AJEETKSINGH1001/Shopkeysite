package com.shopkeyweb;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class Shopkey_home {

    private WebDriver driver;
    private ExtentReports extent;
    private ExtentTest test;

    @BeforeSuite
    public void setUpExtentReports() {
        // Initialize ExtentReports and specify the file path for the report
    	ExtentSparkReporter htmlReporter = new ExtentSparkReporter("test-output/extent-report.html");
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
    }

    @BeforeClass
    public void setUp() {
        // Set up WebDriver (you may need to set the path to your ChromeDriver executable)
    	System.setProperty("webdriver.chrome.driver", "C:\\Users\\Ajeet\\Documents\\chromedriver.exe");
        driver = new ChromeDriver();
    }

    @Test(priority = 1)
    public void testOpenWebsite() {
        // Create a new test entry in the report
        test = extent.createTest("Open Website Test");

        // Open the website
        driver.get("https://test0506store1b.goshopkey.com/");

        // Verify the title of the page
        String pageTitle = driver.getTitle();
        Assert.assertEquals(pageTitle, "Home | Store");

        // Log a message to the report
        test.log(Status.PASS, "Opened the website successfully.");
    }

    @Test(priority = 2)
    public void testLandingOnHomePage() {
        // Create a new test entry in the report
        test = extent.createTest("Landing on Home Page Test");

        // Perform actions to navigate to the home page
        // Example: Click on a link or button to go to the home page

        // Verify that you are on the home page
        WebElement homePageHeader = driver.findElement(By.xpath("//div[@id='__next']/div[2]/div/header/nav/div[4]/ul/li/button"));
        Assert.assertTrue(homePageHeader.isDisplayed());

        // Log a message to the report
        test.log(Status.PASS, "Successfully landed on the home page.");
    }
    
    @Test(priority = 3)
    public void testNavigateToProductPage() {
        // Create a new test entry in the report
        test = extent.createTest("Navigate to Product Page Test");

        // Perform actions to navigate to a product page
        // Example: Click on a product link or button

        // Verify that you are on the product page (you can customize this assertion)
        WebElement productTitle = driver.findElement(By.xpath("//h1[contains(text(),'Product Page')]"));
        Assert.assertTrue(productTitle.isDisplayed());

        // Log a message to the report
        test.log(Status.PASS, "Successfully navigated to the product page.");
    }

    @Test(priority = 4)
    public void testPerformSearch() {
        // Create a new test entry in the report
        test = extent.createTest("Perform Search Test");

        // Perform a search on the website (e.g., enter search query and click search button)

        // Verify search results (you can customize this assertion)
        List<WebElement> searchResults = driver.findElements(By.xpath("//div[@id='__next']/div[2]/div/header/nav/div[5]/div[2]/button/span[2]"));
        Assert.assertTrue(searchResults.size() > 0);

        // Log a message to the report
        test.log(Status.PASS, "Performed a search and found results.");
        
        WebElement searchBox = driver.findElement(By.xpath("//input[@value='']")); // Replace with your actual locator

        // Type the search term into the search box
        searchBox.sendKeys("test");

        // Simulate pressing Enter/Return key to perform the search
        searchBox.sendKeys(Keys.RETURN);
        
    }


    @AfterMethod
    public void afterMethod(ITestResult result) {
        // Capture a screenshot and log it in the report on test failure
        if (result.getStatus() == ITestResult.FAILURE) {
            test.log(Status.FAIL, "Test failed. See screenshot below:");
            try {
                // Capture and add screenshot to the report
                String screenshotPath = captureScreenshot(driver, result.getName());
                test.addScreenCaptureFromPath(screenshotPath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @AfterClass
    public void tearDown() {
        // Close the WebDriver instance
        driver.quit();
    }

    @AfterSuite
    public void tearDownExtentReports() {
        // Flush and close the report after all tests are executed
        extent.flush();
    }

    private String captureScreenshot(WebDriver driver, String screenshotName) throws IOException {
        // Capture screenshot and save it to a file
        // You may need to adjust the path where screenshots are saved
        TakesScreenshot ts = (TakesScreenshot) driver;
        File source = ts.getScreenshotAs(OutputType.FILE);
        String screenshotPath = "screenshots/" + screenshotName + ".png";
        File destination = new File(screenshotPath);
        org.apache.commons.io.FileUtils.copyFile(source, destination);
        return screenshotPath;
    }
}
