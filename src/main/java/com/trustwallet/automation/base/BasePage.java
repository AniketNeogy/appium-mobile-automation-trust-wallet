package com.trustwallet.automation.base;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.Duration;

public class BasePage {
    protected AppiumDriver driver;
    protected WebDriverWait wait;
    private static final Logger logger = LogManager.getLogger(BasePage.class);
    private static final int DEFAULT_WAIT_SECONDS = 15;

    /**
     * Constructor to initialize the page with AppiumDriver
     * @param driver AppiumDriver instance
     */
    public BasePage(AppiumDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_WAIT_SECONDS));
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
        logger.debug("BasePage initialized with driver: " + driver);
    }

    /**
     * Click on an element after waiting for it to be clickable
     * @param element WebElement to click
     */
    protected void click(WebElement element) {
        waitForClickability(element).click();
        logger.debug("Clicked on element: " + element);
    }

    /**
     * Enter text in an input field after waiting for it to be visible
     * @param element WebElement to enter text into
     * @param text Text to enter
     */
    protected void sendKeys(WebElement element, String text) {
        waitForVisibility(element).clear();
        element.sendKeys(text);
        logger.debug("Entered text '" + text + "' in element: " + element);
    }

    /**
     * Get text from an element after waiting for it to be visible
     * @param element WebElement to get text from
     * @return Text from the element
     */
    protected String getText(WebElement element) {
        String text = waitForVisibility(element).getText();
        logger.debug("Got text '" + text + "' from element: " + element);
        return text;
    }


    /**
     * Check if an element is displayed
     * @param element WebElement to check
     * @return true if element is displayed, false otherwise
     */
    protected boolean isElementDisplayed(WebElement element) {
        try {
            boolean isDisplayed = element.isDisplayed();
            logger.debug("Element " + element + " is displayed: " + isDisplayed);
            return isDisplayed;
        } catch (Exception e) {
            logger.debug("Element " + element + " is not displayed");
            return false;
        }
    }

    /**
     * Find element by locator
     * @param locator By locator
     * @return WebElement
     */
    protected WebElement findElement(By locator) {
        logger.debug("Finding element by locator: " + locator);
        return driver.findElement(locator);
    }

    /**
     * Wait for element to be visible
     * @param element WebElement to wait for
     * @return WebElement once it's visible
     */
    protected WebElement waitForVisibility(WebElement element) {
        logger.debug("Waiting for element visibility: " + element);
        return wait.until(ExpectedConditions.visibilityOf(element));
    }

    /**
     * Wait for element to be visible (Alias for waitForVisibility)
     * @param element WebElement to wait for
     * @return WebElement once it's visible
     */
    protected WebElement waitForElementToBeVisible(WebElement element) {
        return waitForVisibility(element);
    }

    /**
     * Wait for element to be visible with custom timeout
     * @param element WebElement to wait for
     * @param timeoutInSeconds Custom timeout in seconds
     * @return WebElement once it's visible
     */
    protected WebElement waitForVisibility(WebElement element, int timeoutInSeconds) {
        WebDriverWait customWait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
        logger.debug("Waiting for element visibility with timeout " + timeoutInSeconds + " seconds: " + element);
        return customWait.until(ExpectedConditions.visibilityOf(element));
    }

    /**
     * Wait for element to be clickable
     * @param element WebElement to wait for
     * @return WebElement once it's clickable
     */
    protected WebElement waitForClickability(WebElement element) {
        logger.debug("Waiting for element clickability: " + element);
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    /**
     * Wait for element to be clickable (Alias for waitForClickability)
     * @param element WebElement to wait for
     * @return WebElement once it's clickable
     */
    protected WebElement waitForElementToBeClickable(WebElement element) {
        return waitForClickability(element);
    }

    /**
     * Check if element is present without waiting
     * @param locator By locator to find element
     * @return true if element is present, false otherwise
     */
    protected boolean isElementPresent(By locator) {
        try {
            driver.findElement(locator);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Find element if visible, returns null otherwise
     * @param locator By locator to find element
     * @return WebElement if visible, null otherwise
     */
    protected WebElement findElementIfVisible(By locator) {
        try {
            return waitForElementToBeVisible(locator);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Wait for element to be visible (By locator version)
     * @param locator By locator to find element
     * @return WebElement once it's visible
     */
    protected WebElement waitForElementToBeVisible(By locator) {
        logger.debug("Waiting for element visibility by locator: " + locator);
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    /**
     * Wait for element to be clickable (By locator version)
     * @param locator By locator to find element
     * @return WebElement once it's clickable
     */
    protected WebElement waitForElementToBeClickable(By locator) {
        logger.debug("Waiting for element clickability by locator: " + locator);
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }
} 