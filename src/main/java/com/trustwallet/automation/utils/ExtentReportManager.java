package com.trustwallet.automation.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Manager class for ExtentReports
 * Handles the creation and configuration of the HTML report
 */
public class ExtentReportManager {
    private static ExtentReports extentReports;
    private static Map<Long, ExtentTest> extentTestMap = new HashMap<>();
    
    private static final String REPORT_DIR = "test-output/";
    private static final String REPORT_FILE = "ExtentReport.html";
    private static final String REPORT_NAME = "Trust Wallet Automation Test Report";
    private static final String REPORT_TITLE = "Trust Wallet Mobile Automation Results";
    
    /**
     * Initialize ExtentReports
     */
    public static synchronized ExtentReports getInstance() {
        if (extentReports == null) {
            createInstance();
        }
        return extentReports;
    }
    
    /**
     * Create an instance of ExtentReports
     */
    private static ExtentReports createInstance() {
        // Create the report directory if it doesn't exist
        File reportDir = new File(REPORT_DIR);
        if (!reportDir.exists()) {
            reportDir.mkdirs();
        }
        
        // Configure the HTML reporter
        ExtentSparkReporter sparkReporter = new ExtentSparkReporter(REPORT_DIR + REPORT_FILE);
        sparkReporter.config().setReportName(REPORT_NAME);
        sparkReporter.config().setDocumentTitle(REPORT_TITLE);
        sparkReporter.config().setTheme(Theme.STANDARD);
        sparkReporter.config().setEncoding("utf-8");
        sparkReporter.config().setTimeStampFormat("MMM dd, yyyy HH:mm:ss");
        
        extentReports = new ExtentReports();
        extentReports.attachReporter(sparkReporter);
        
        // Add system info
        extentReports.setSystemInfo("OS", System.getProperty("os.name"));
        extentReports.setSystemInfo("Java Version", System.getProperty("java.version"));
        extentReports.setSystemInfo("Platform", System.getProperty("platformName", "Android"));
        extentReports.setSystemInfo("Environment", "QA");
        
        return extentReports;
    }
    
    /**
     * Get the current ExtentTest for the thread
     */
    public static synchronized ExtentTest getTest() {
        return extentTestMap.get(Thread.currentThread().getId());
    }
    
    /**
     * Create a new ExtentTest and add it to the map
     */
    public static synchronized ExtentTest createTest(String testName, String description) {
        ExtentTest test = getInstance().createTest(testName, description);
        extentTestMap.put(Thread.currentThread().getId(), test);
        return test;
    }
    
    /**
     * Log information to the ExtentTest
     */
    public static synchronized void log(Status status, String message) {
        getTest().log(status, message);
    }
    
    /**
     * Add a screenshot to the report
     */
    public static synchronized void addScreenshot(String screenshotPath, String title) {
        try {
            getTest().addScreenCaptureFromPath(screenshotPath, title);
        } catch (Exception e) {
            getTest().log(Status.WARNING, "Failed to add screenshot: " + e.getMessage());
        }
    }
    
    /**
     * Flush the ExtentReports instance
     */
    public static synchronized void flushReport() {
        getInstance().flush();
    }
} 