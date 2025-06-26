package com.nse.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentManager {

    private static ExtentReports extent;
    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();

    public static synchronized ExtentReports createInstance() {
        ExtentSparkReporter sparkReporter = new ExtentSparkReporter("reports/NSEReport.html");
        sparkReporter.config().setDocumentTitle("Automation Report");
        sparkReporter.config().setReportName("Selenium Test Results");
        sparkReporter.config().setTheme(Theme.DARK);
        sparkReporter.config().setTimelineEnabled(true);

        ExtentReports extent = new ExtentReports();
        extent.attachReporter(sparkReporter);

        extent.setSystemInfo("OS", System.getProperty("os.name"));
        extent.setSystemInfo("Tester", "Amitash Bhat");
        extent.setSystemInfo("Java Version", System.getProperty("java.version"));
        return extent;
    }

    public static synchronized ExtentReports getExtent() {
        return extent;
    }

    public static synchronized ExtentTest getTest() {
        return test.get();
    }

    public static synchronized void setTest(ExtentTest testInstance) {
        test.set(testInstance);
    }
}
