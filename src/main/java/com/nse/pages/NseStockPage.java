package com.nse.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class NseStockPage {

    private WebDriver driver;
    private WebDriverWait wait;

    private final By stockName = By.xpath("//h1[@id='quoteName']/span[1]");
    private final By currentPrice = By.xpath("//span[@id='quoteLtp']");
    private final By high52 = By.xpath("//span[text()='52 Week High ']/parent::td/following-sibling::td");
    private final By low52 = By.xpath("//span[text()='52 Week Low']/parent::td/following-sibling::td");

    public NseStockPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public String getStockName(){
        WebElement name = wait.until(ExpectedConditions.visibilityOfElementLocated( stockName));
        return name.getText();
    }

    public double getCurrentPrice(){
        String price = wait.until(ExpectedConditions.visibilityOfElementLocated(currentPrice)).getText().replace(",", "");
        return Double.parseDouble(price);
    }

    public String get52WeekHigh() {
        WebElement highElement = wait.until(ExpectedConditions.visibilityOfElementLocated(high52));
        return highElement.getText();
    }

    public String get52WeekLow() {
        WebElement lowElement = wait.until(ExpectedConditions.visibilityOfElementLocated(low52));
        return lowElement.getText();
    }
}