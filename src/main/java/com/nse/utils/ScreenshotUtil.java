package com.nse.utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class ScreenshotUtil {

    public static String captureScreenshot(WebDriver driver, String fileName) {
        try {
            String dirPath = System.getProperty("user.dir") + "/reports/screenshots/";
            File screenshotDir = new File(dirPath);
            if (!screenshotDir.exists()) {
                screenshotDir.mkdirs();
            }

            File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            String fullPath = dirPath + fileName + ".png";
            File dest = new File(fullPath);
            FileUtils.copyFile(src, dest);

            return "screenshots/" + fileName + ".png";
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
