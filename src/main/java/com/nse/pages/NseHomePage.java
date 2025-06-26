package com.nse.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class NseHomePage {
    private WebDriver driver;

    public NseHomePage(WebDriver driver) {
        this.driver = driver;
    }

    private final By searchInput = By.xpath("//input[contains(@placeholder,'Search by Company name')]");
    private final By selectStock = By.xpath("//p[@class='line1']/span[2]");
    private final By name =By.xpath("//div[@id='quoteNav']/a");
    public void openHomePage() {
        driver.get("https://www.nseindia.com/");
    }

    public void searchStock(String stockName) throws InterruptedException {
        WebElement searchBox = driver.findElement(searchInput);
        searchBox.clear();
        searchBox.sendKeys(stockName);
        driver.findElement(selectStock).click();


    }

    public boolean isStockSearchSuccessful(String expectedStockName) {
        WebElement stockTitle = driver.findElement(name);
        return stockTitle.getText().contains(expectedStockName);
    }

}