package com.home;

import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class SimplyhireExtractLinks {
    public static void main(String[] args) {
        // Set the path to the ChromeDriver executable
        System.setProperty("webdriver.chrome.driver", "I:\\\\Devi\\\\ChromeDriver\\\\chromedriver-win64\\\\chromedriver.exe");

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless"); // Run in headless mode

        // Initialize the ChromeDriver
        WebDriver driver = new ChromeDriver();

        // List of URLs to visit
        List<String> urls = Arrays.asList(
                "https://www.simplyhired.com/search?q=java&l=");

        try {
            // Iterate over each URL
            for (String url : urls) {
                // Open the webpage
                driver.get(url);

                // Extract the title of the webpage
                String title = driver.getTitle();
                System.out.println("Page title: " + title);

                // Extract specific elements by tag, class, ID, etc.
                WebElement header = driver.findElement(By.tagName("div"));
                System.out.println("div text: " + header.getText());

                // Example: Extract all links from the page
                List<WebElement> links = driver.findElements(By.tagName("a"));
                for (WebElement link : links) {
                    System.out.println("Link: " + link.getAttribute("href"));
                }

                // Separator for clarity
                System.out.println("-------------------------------");
            }
        } finally {
            // Close the browser session
            driver.quit();


        }

    }
}
	 
	 
	 
