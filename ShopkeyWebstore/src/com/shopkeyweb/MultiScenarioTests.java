package com.shopkeyweb;
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

public class MultiScenarioTests {
    private WebDriver driver;
    private ExtentReports extent;
    private ExtentTest test;

    @BeforeSuite
    public void setUp() {
    	System.setProperty("webdriver.chrome.driver", "C:\\Users\\Ajeet\\Documents\\chromedriver.exe");
        driver = new ChromeDriver();

        String reportFileName = "test-output/MultiScenario_TestReport.html";
        ExtentSparkReporter htmlReporter = new ExtentSparkReporter(reportFileName);
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
    }

    @BeforeClass
    public void navigateToHomePage() {
        driver.get("https://test0506store1b.goshopkey.com/");
    }

    @Test(description = "Test Scenario 1: Open Home Page")
    public void testScenario1() {
        test = extent.createTest("Test Scenario 1: Open Home Page");
        driver.get("https://test0506store1b.goshopkey.com/");
        Assert.assertEquals(driver.getTitle(), "Home | Storekk");
        // Add test steps for scenario 1 (e.g., click on links, verify elements)
        // ...

        test.log(Status.PASS, "Scenario 1 tile of the web store home page passed");
    }

    @Test(description = "Test Scenario 2: Login form")
    public void testScenario2() {
        test = extent.createTest("Test Scenario 2: Perform Login");
        //test = extent.createTest("Login Functionality Test");
        driver.get("https://test0506store1bk.goshopkey.com/");
        WebElement loginButton = driver.findElement(By.id("loginButton"));
        loginButton.click();
        WebElement loginForm = driver.findElement(By.id("loginForm"));
        Assert.assertTrue(loginForm.isDisplayed());
        // Add test steps for scenario 2 (e.g., enter credentials, click login)
        // ...

        test.log(Status.WARNING, "Scenario 2 failed");
    }

    @Test(description = "Test Scenario 3: Search for Products")
    public void testScenario3() {
        test = extent.createTest("Test Scenario 3: Search for Products");
        // Add test steps for scenario 3 (e.g., enter search query, click search)
        // ...

        test.log(Status.PASS, "Scenario 3 passed");
    }

    @AfterClass
    public void afterClass() {
        // Additional cleanup if needed
    }

    @AfterSuite
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }

        extent.flush();
    }
}
