package com.trustwallet.automation.base;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import java.net.URL;
import java.time.Duration;
import java.util.Properties;
import java.io.FileInputStream;
import java.io.IOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BaseDriver {
    private static AppiumDriver driver;
    private static final Logger logger = LogManager.getLogger(BaseDriver.class);
    private static Properties config;

    /**
     * Initialize the Appium driver based on platform type
     * @param platformName Android or iOS
     * @return AppiumDriver instance
     */
    public static AppiumDriver initializeDriver(String platformName) {
        try {
            loadConfig();
            DesiredCapabilities capabilities = new DesiredCapabilities();
            
            String appiumServerUrl = config.getProperty("appium.server.url", "http://127.0.0.1:4723/wd/hub");
            
            capabilities.setCapability("platformName", platformName);
            
            if (platformName.equalsIgnoreCase("Android")) {
                capabilities.setCapability("deviceName", config.getProperty("android.device.name"));
                capabilities.setCapability("automationName", "UiAutomator2");
                capabilities.setCapability("appPackage", config.getProperty("android.app.package"));
                capabilities.setCapability("appActivity", config.getProperty("android.app.activity"));
                
                if (Boolean.parseBoolean(config.getProperty("use.app.file", "false"))) {
                    capabilities.setCapability("app", config.getProperty("android.app.path"));
                }
                
                driver = new AndroidDriver(new URL(appiumServerUrl), capabilities);
            } else if (platformName.equalsIgnoreCase("iOS")) {
                capabilities.setCapability("deviceName", config.getProperty("ios.device.name"));
                capabilities.setCapability("automationName", "XCUITest");
                capabilities.setCapability("bundleId", config.getProperty("ios.bundle.id"));
                
                if (Boolean.parseBoolean(config.getProperty("use.app.file", "false"))) {
                    capabilities.setCapability("app", config.getProperty("ios.app.path"));
                }
                
                driver = new IOSDriver(new URL(appiumServerUrl), capabilities);
            } else {
                throw new IllegalArgumentException("Invalid platform name: " + platformName);
            }
            
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            logger.info("Driver initialized successfully for platform: " + platformName);
            return driver;
            
        } catch (Exception e) {
            logger.error("Failed to initialize driver", e);
            throw new RuntimeException("Failed to initialize driver", e);
        }
    }
    
    /**
     * Get the current driver instance
     * @return AppiumDriver instance
     */
    public static AppiumDriver getDriver() {
        if (driver == null) {
            throw new IllegalStateException("Driver has not been initialized. Call initializeDriver() first.");
        }
        return driver;
    }
    
    /**
     * Quit the driver instance
     */
    public static void quitDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
            logger.info("Driver quit successfully");
        }
    }
    
    /**
     * Load configuration from properties file
     */
    private static void loadConfig() {
        config = new Properties();
        try {
            FileInputStream fis = new FileInputStream("src/test/resources/config.properties");
            config.load(fis);
            fis.close();
        } catch (IOException e) {
            logger.error("Failed to load config.properties", e);
            throw new RuntimeException("Failed to load config.properties", e);
        }
    }
} 