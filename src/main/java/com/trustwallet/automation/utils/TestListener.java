package com.trustwallet.automation.utils;

import io.appium.java_client.AppiumDriver;
import com.trustwallet.automation.base.BaseDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import com.aventstack.extentreports.Status;
import org.testng.annotations.Test;

import java.io.File;
import java.util.Arrays;

public class TestListener implements ITestListener {
    private static final Logger logger = LogManager.getLogger(TestListener.class);
    private static AppiumDriver driver;
    
    /**
     * Set the AppiumDriver for screenshot capturing
     * @param appiumDriver The AppiumDriver instance
     */
    public static void setDriver(AppiumDriver appiumDriver) {
        driver = appiumDriver;
    }
    
    /**
     * Get the AppiumDriver for screenshot capturing
     * @return The AppiumDriver instance
     */
    public static AppiumDriver getDriver() {
        return driver;
    }

    @Override
    public void onTestStart(ITestResult result) {
        logger.info("Starting test: " + result.getName());
        
        // Get test method
        Test testMethod = result.getMethod().getConstructorOrMethod().getMethod().getAnnotation(Test.class);
        String testDescription = testMethod != null ? testMethod.description() : "";
        if (testDescription.isEmpty()) {
            testDescription = result.getName();
        }
        
        // Create test in Extent Reports
        ExtentReportManager.createTest(result.getName(), testDescription);
        ExtentReportManager.log(Status.INFO, "Test started: " + result.getName());
        
        // Log test parameters if any
        if (result.getParameters().length > 0) {
            ExtentReportManager.log(Status.INFO, "Test Parameters: " + Arrays.toString(result.getParameters()));
        }
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        logger.info("Test passed: " + result.getName());
        ExtentReportManager.log(Status.PASS, "Test passed: " + result.getName());
        
        // Log test execution time
        long duration = result.getEndMillis() - result.getStartMillis();
        ExtentReportManager.log(Status.INFO, String.format("Test duration: %.2f seconds", duration / 1000.0));
        
        // No screenshots for successful tests
    }

    @Override
    public void onTestFailure(ITestResult result) {
        logger.error("Test failed: " + result.getName());
        
        // Log error details
        if (result.getThrowable() != null) {
            ExtentReportManager.log(Status.FAIL, "Test failed with exception: " + result.getThrowable().getMessage());
            ExtentReportManager.getTest().fail(result.getThrowable());
            logger.error("Exception: ", result.getThrowable());
        } else {
            ExtentReportManager.log(Status.FAIL, "Test failed");
        }
        
        // Take screenshot on failure
        try {
            String screenshotPath = TestUtils.takeScreenshot(
                BaseDriver.getDriver(),
                "failure_" + result.getName() + "_" + TestUtils.getCurrentDateTime("yyyyMMdd_HHmmss")
            );
            if (screenshotPath != null) {
                // Make sure the path is absolute for the report
                File screenshotFile = new File(screenshotPath);
                String absolutePath = screenshotFile.getAbsolutePath();
                
                // Add to the report
                ExtentReportManager.addScreenshot(absolutePath, "Failure Screenshot");
                logger.info("Screenshot taken: " + absolutePath);
            }
        } catch (Exception e) {
            logger.error("Failed to take screenshot on test failure", e);
            ExtentReportManager.log(Status.WARNING, "Failed to capture screenshot: " + e.getMessage());
        }
        
        // Log test execution time
        long duration = result.getEndMillis() - result.getStartMillis();
        ExtentReportManager.log(Status.INFO, String.format("Test duration: %.2f seconds", duration / 1000.0));
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        logger.info("Test skipped: " + result.getName());
        ExtentReportManager.log(Status.SKIP, "Test skipped: " + result.getName());
        
        // Log reason for skipping if available
        if (result.getThrowable() != null) {
            ExtentReportManager.log(Status.SKIP, "Reason: " + result.getThrowable().getMessage());
        }
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        logger.info("Test failed but within success percentage: " + result.getName());
        ExtentReportManager.log(Status.WARNING, "Test failed but within success percentage: " + result.getName());
    }

    @Override
    public void onStart(ITestContext context) {
        logger.info("Starting test suite: " + context.getName());
        // Initialize the ExtentReports instance
        ExtentReportManager.getInstance();
    }

    @Override
    public void onFinish(ITestContext context) {
        logger.info("Test suite finished: " + context.getName());
        logger.info("Passed tests: " + context.getPassedTests().size());
        logger.info("Failed tests: " + context.getFailedTests().size());
        logger.info("Skipped tests: " + context.getSkippedTests().size());
        
        // Add summary to the report
        ExtentReportManager.getInstance().setSystemInfo("Total Tests", String.valueOf(context.getAllTestMethods().length));
        ExtentReportManager.getInstance().setSystemInfo("Passed Tests", String.valueOf(context.getPassedTests().size()));
        ExtentReportManager.getInstance().setSystemInfo("Failed Tests", String.valueOf(context.getFailedTests().size()));
        ExtentReportManager.getInstance().setSystemInfo("Skipped Tests", String.valueOf(context.getSkippedTests().size()));
        
        // Flush the report to generate HTML
        ExtentReportManager.flushReport();
        
        logger.info("Extent Report generated successfully at: " + new File("test-output/ExtentReport.html").getAbsolutePath());
    }
} 