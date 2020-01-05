package com.newur.selenium;

import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class WebTest {

    @Test
    //@Ignore
    public void basicTest() {
        FirefoxBinary firefoxBinary = new FirefoxBinary();
        firefoxBinary.addCommandLineOptions("--headless");
        System.setProperty("webdriver.gecko.driver", "target/classes/geckodriver");
        FirefoxOptions firefoxOptions = new FirefoxOptions();
        firefoxOptions.setBinary(firefoxBinary);
        FirefoxDriver driver = new FirefoxDriver(firefoxOptions);
        try {
            driver.get("http://www.google.com");
            driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
            WebDriverWait wait = new WebDriverWait(driver, 4);

            WebElement queryBox = driver.findElement(By.name("q"));
            queryBox.sendKeys("headless firefox");

            WebElement searchBtn = driver.findElement(By.name("btnK"));
            wait.until(ExpectedConditions.elementToBeClickable(searchBtn));
            searchBtn.click();

            WebElement searchResults = driver.findElement(By.className("r"));
            wait.until(ExpectedConditions.elementToBeClickable(searchResults));
            searchResults.findElements(By.tagName("a")).get(0).click();

            System.out.println(driver.getPageSource());
        } finally {
            driver.quit();
        }

    }
}
