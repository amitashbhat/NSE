package com.nse.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class NseHomePage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    public NseHomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // 10-second timeout
    }

    private final By searchInput = By.xpath("//input[contains(@placeholder,'Search by Company name')]");
    private final By selectStock = By.xpath("//p[@class='line1']/span[2]");
    private final By name = By.xpath("//div[@id='quoteNav']/a");
    private final By closeBtn = By.xpath("//button[@class='btn-close']");

    public void openHomePage() {
        driver.get("https://www.nseindia.com/");
    }

    public void searchStock(String stockName) {
        try {
            List<WebElement> closeButton = driver.findElements(closeBtn);
            if (!closeButton.isEmpty()) {
                wait.until(ExpectedConditions.elementToBeClickable(closeButton.get(0))).click();
            }

            WebElement searchBox = wait.until(ExpectedConditions.visibilityOfElementLocated(searchInput));
            searchBox.clear();
            searchBox.sendKeys(stockName);

            WebElement stockOption = wait.until(ExpectedConditions.elementToBeClickable(selectStock));
            stockOption.click();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isStockSearchSuccessful(String expectedStockName) {
        int attempts = 0;
        while (attempts < 2) {
            try {
                WebElement stockTitle = wait.until(ExpectedConditions.visibilityOfElementLocated(name));
                return stockTitle.getText().contains(expectedStockName);
            } catch (StaleElementReferenceException e) {
                attempts++;
            } catch (Exception e) {
                e.printStackTrace();
                break;
            }
        }
        return false;
    }

}