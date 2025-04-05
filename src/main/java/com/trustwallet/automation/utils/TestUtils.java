package com.trustwallet.automation.utils;

import io.appium.java_client.AppiumDriver;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

public class TestUtils {
    private static final Logger logger = LogManager.getLogger(TestUtils.class);
    
    /**
     * Take a screenshot and save it to the screenshots directory
     * @param driver AppiumDriver instance
     * @param screenshotName Name of the screenshot
     * @return Path to the screenshot file
     */
    public static String takeScreenshot(AppiumDriver driver, String screenshotName) {
        String timestamp = getCurrentDateTime("yyyyMMdd_HHmmss");
        String fileName = screenshotName;
        
        // Add timestamp if not already included in the name
        if (!screenshotName.contains(timestamp)) {
            fileName = screenshotName + "_" + timestamp;
        }
        fileName += ".png";
        
        String directory = "screenshots/";
        File directory_path = new File(directory);
        
        // Create screenshots directory if it doesn't exist
        if (!directory_path.exists()) {
            directory_path.mkdir();
        }
        
        String filePath = directory + fileName;
        
        try {
            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(screenshot, new File(filePath));
            logger.info("Screenshot saved: " + filePath);
            return filePath;
        } catch (IOException e) {
            logger.error("Failed to take screenshot", e);
            return null;
        }
    }
    
    /**
     * Pause execution for the specified number of seconds
     * @param seconds Number of seconds to wait
     */
    public static void wait(int seconds) {
        try {
            logger.debug("Waiting for " + seconds + " seconds");
            Thread.sleep(seconds * 1000L);
        } catch (InterruptedException e) {
            logger.error("Thread sleep interrupted", e);
            Thread.currentThread().interrupt();
        }
    }
    
    /**
     * Get the current date and time in the specified format
     * @param format Date format pattern
     * @return Formatted date and time string
     */
    public static String getCurrentDateTime(String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(new Date());
    }
    
    /**
     * Calculate the duration between two times in seconds
     * @param startTimeMillis Start time in milliseconds
     * @param endTimeMillis End time in milliseconds
     * @return Duration in seconds
     */
    public static double calculateDurationInSeconds(long startTimeMillis, long endTimeMillis) {
        return (endTimeMillis - startTimeMillis) / 1000.0;
    }
} 