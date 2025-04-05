package com.trustwallet.automation.utils;

import io.appium.java_client.AppiumDriver;
import com.trustwallet.automation.base.BaseDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

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
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        logger.info("Test passed: " + result.getName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        logger.error("Test failed: " + result.getName());
        
        // Take screenshot on failure
        try {
            String screenshotPath = TestUtils.takeScreenshot(
                BaseDriver.getDriver(),
                "failure_" + result.getName()
            );
            logger.info("Screenshot taken: " + screenshotPath);
        } catch (Exception e) {
            logger.error("Failed to take screenshot on test failure", e);
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        logger.info("Test skipped: " + result.getName());
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        logger.info("Test failed but within success percentage: " + result.getName());
    }

    @Override
    public void onStart(ITestContext context) {
        logger.info("Starting test suite: " + context.getName());
    }

    @Override
    public void onFinish(ITestContext context) {
        logger.info("Test suite finished: " + context.getName());
        logger.info("Passed tests: " + context.getPassedTests().size());
        logger.info("Failed tests: " + context.getFailedTests().size());
        logger.info("Skipped tests: " + context.getSkippedTests().size());
    }
} 