package com.shopkeyweb;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class WebsiteLauncher {
    public static void main(String[] args) {
        // Set the path to the ChromeDriver executable
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\Ajeet\\Documents\\chromedriver.exe");
        
        // Create a new instance of ChromeDriver
        WebDriver driver = new ChromeDriver();
        
        // Open a website
        String websiteURL = "https://www.flipkart.com/";
        driver.get(websiteURL);
        
        // Close the browser
        driver.quit();
    }
}




