package com.nse.tests;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.nse.base.BaseTest;
import com.nse.pages.NseHomePage;
import com.nse.pages.NseStockPage;
import com.nse.utils.ExtentManager;
import com.nse.utils.ScreenshotUtil;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.Properties;

public class NseStockPageTest extends BaseTest {

    ExtentReports extent;
    Properties props = new Properties();

    @BeforeClass
    public void setup() {
        extent = ExtentManager.createInstance();

        try (InputStream input = Files.newInputStream(Paths.get("src/test/resources/testdata.properties"))) {
            props.load(input);
        } catch (IOException ex) {
            logger.error("Failed to load test data", ex);
        }
    }

    @DataProvider(name = "stockData")
    public Object[][] getStockData() {
        return new Object[][]{
                {"TATAMOTORS"},
                {"INFY"},
                {"RELIANCE"}
        };
    }

    @Test(dataProvider = "stockData")
    public void testStockInfo(String stockName) {
        ExtentTest test = extent.createTest("Verify Stock information Test Case for stock - " + stockName);
        ExtentManager.setTest(test);
        logger.info("Starting test for stock: ", stockName);

        double purchasePrice = Double.parseDouble(props.getProperty("stock.purchasedPrice"));

        try {
            NseHomePage nseHomePage = new NseHomePage(driver);
            NseStockPage stockPage = new NseStockPage(driver);

            nseHomePage.openHomePage();
            String beforeScreenshot = ScreenshotUtil.captureScreenshot(driver, "before_" + stockName);
            ExtentManager.getTest().info("<b>Opened NSE homepage</b><br><img src='" + beforeScreenshot + "' height='200' width='300'/>");

            nseHomePage.searchStock(stockName);
            Thread.sleep(3000);

            if (!nseHomePage.isStockSearchSuccessful(stockName)) {
                ExtentManager.getTest().fail("Stock search failed for " + stockName);
                Assert.fail("Stock search failed for " + stockName);
            }

            String name = stockPage.getStockName();
            double currentPrice = stockPage.getCurrentPrice();
            String high = stockPage.get52WeekHigh();
            String low = stockPage.get52WeekLow();

            ExtentManager.getTest().info("Purchased Price of " + name + " is " + purchasePrice);
            ExtentManager.getTest().info("Current Price of " + name + " is " + currentPrice);

            if (currentPrice > purchasePrice) {
                ExtentManager.getTest().pass("Profit: Current price is higher than purchase price.");
            } else {
                ExtentManager.getTest().fail("Loss: Current price is lower than purchase price.");
            }

            if (isInvalid(high) || isInvalid(low)) {
                logger.error("Invalid 52 Week High or Low for {}", stockName);
                ExtentManager.getTest().fail("Invalid 52 Week High or Low for " + stockName);
                Assert.fail("Invalid 52 Week High or Low for " + stockName);
            } else {
                logger.info("52 Week High for " + stockName + ": " + high);
                logger.info("52 Week Low for " + stockName + ": " + low);

                ExtentManager.getTest().pass("52 Week High: " + high);
                ExtentManager.getTest().pass("52 Week Low: " + low);
            }

            String afterScreenshot = ScreenshotUtil.captureScreenshot(driver, "after_" + stockName);
            ExtentManager.getTest().info("<b>After Searching for stock " + stockName + "</b><br><img src='" + afterScreenshot + "' height='200' width='300'/>");

        } catch (Exception e) {
            ExtentManager.getTest().fail("Exception occurred: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private boolean isInvalid(String value) {
        return value == null || value.trim().isEmpty() || value.equals("-");
    }

    @AfterClass
    public void flushReport() {
        extent.flush();
        logger.info("Extent report flushed.");
    }
}