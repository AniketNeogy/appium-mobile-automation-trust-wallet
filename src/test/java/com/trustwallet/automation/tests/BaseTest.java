package com.trustwallet.automation.tests;

import com.trustwallet.automation.base.BaseDriver;
import io.appium.java_client.AppiumDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;
import com.trustwallet.automation.utils.TestListener; // Assuming TestListener path

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

@Listeners(TestListener.class)
public abstract class BaseTest {

    protected AppiumDriver driver;
    protected Properties props;

    @Parameters({"platformName"})
    @BeforeMethod
    public void setUp(String platformName) throws IOException {
        // Load properties
        props = new Properties();
        FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + "/src/test/resources/config.properties");
        props.load(fis);

        // Use parameter from testng.xml if provided, otherwise use from config.properties
        String platform = platformName != null ? platformName : props.getProperty("platform.name", "Android");
        
        // Initialize driver from BaseDriver
        BaseDriver baseDriver = new BaseDriver();
        driver = baseDriver.initializeDriver(platform);

        // Set implicit wait
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS); // Adjust timeout as needed

        // Add driver to listener context (if listener needs it)
        TestListener.setDriver(driver);
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
         TestListener.setDriver(null); // Clear driver from listener
        System.out.println("Driver quit successfully.");
    }
} 