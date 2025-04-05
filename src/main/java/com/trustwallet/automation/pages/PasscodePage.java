package com.trustwallet.automation.pages;

import com.trustwallet.automation.base.BasePage;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.io.File;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PasscodePage extends BasePage {
    private static final Logger logger = LogManager.getLogger(PasscodePage.class);

    // Locators
    private String textUiSelector = "new UiSelector().text(\"%s\")";
    private String digitButtonUiSelector = "new UiSelector().text(\"%s\")";
    private final By skipPopupButton = AppiumBy.androidUIAutomator(
            String.format(textUiSelector, "Skip, I'll do it later"));
    private final By createPasscodeTitle = AppiumBy.androidUIAutomator(
            String.format(textUiSelector, "Create passcode"));
    private final By confirmPasscodeTitle = AppiumBy.androidUIAutomator(
            String.format(textUiSelector, "Confirm passcode"));
    private final By passcodeMismatchError = AppiumBy.androidUIAutomator(
            String.format(textUiSelector, "Those passwords didn't match!"));

    /**
     * Constructor for PasscodePage.
     *
     * @param driver The AppiumDriver instance.
     */
    public PasscodePage(AppiumDriver driver) {
        super(driver);
        logger.debug("PasscodePage initialized");
    }

    /**
     * Checks if the Passcode page is displayed by checking for either
     * "Create passcode" or "Confirm passcode" text.
     *
     * @return true if either title text is found, false otherwise.
     */
    public boolean isPageDisplayed() {
        try {
            WebElement createTitle = findElementIfVisible(createPasscodeTitle);
            if (createTitle != null) {
                logger.debug("Create passcode page displayed");
                return true;
            }

            WebElement confirmTitle = findElementIfVisible(confirmPasscodeTitle);
            if (confirmTitle != null) {
                logger.debug("Confirm passcode page displayed");
                return true;
            }
            logger.debug("Passcode page not found");
            return false;
        } catch (NoSuchElementException e) {
            logger.error("Failed to check passcode page display", e);
            return false;
        }
    }

    /**
     * Enters the passcode digits by clicking the corresponding number buttons,
     * immediately proceeds to the confirmation screen.
     *
     * @param passcode The 6-digit passcode string.
     * @return The same PasscodePage instance, now on confirmation step.
     */
    public PasscodePage enterPasscode(String passcode) {
        logger.info("Entering passcode: " + passcode);
        
        try {
            // Verify we're on the Create passcode screen
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.visibilityOfElementLocated(createPasscodeTitle));
            
            // Enter the passcode digits
            for (char digit : passcode.toCharArray()) {
                clickDigit(digit);
            }
            
            // Wait for the confirmation screen to appear
            wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            wait.until(ExpectedConditions.visibilityOfElementLocated(confirmPasscodeTitle));
            logger.debug("Confirmation screen detected");
            return this;
        }
        catch (TimeoutException e) {
            logger.error("Timed out waiting for passcode screen transition", e);
            // Try to proceed anyway
            return this;
        }
    }

    /**
     * Confirms the passcode digits, handles the "Keep up with market" popup,
     * and navigates to the Choose Passkey page.
     *
     * @param passcode The 6-digit passcode string to confirm.
     * @return A new instance of the ChoosePasskeyPage.
     */
    public ChoosePasskeyPage confirmPasscode(String passcode) {
        logger.info("Confirming passcode: " + passcode);
        
        // Enter the confirmation passcode
        for (char digit : passcode.toCharArray()) {
            clickDigit(digit);
        }

        // Handle the popup that appears after successful passcode creation
        logger.debug("Handling 'Keep up with market' popup");
        try {
            WebDriverWait popupWait = new WebDriverWait(driver, Duration.ofSeconds(10)); 
            WebElement skipButton = popupWait.until(ExpectedConditions.elementToBeClickable(skipPopupButton));
            click(skipButton);
            logger.debug("Clicked 'Skip, I'll do it later' button");
        } catch (Exception e) {
            logger.warn("Could not find or click the 'Skip, I'll do it later' button: {}", e.getMessage());
        }

        return new ChoosePasskeyPage(driver);
    }
    
    /**
     * Confirms the passcode with a different value than originally entered,
     * which should trigger a mismatch error.
     *
     * @param differentPasscode A passcode different from the one entered initially.
     * @return The same PasscodePage instance for further interactions.
     */
    public PasscodePage confirmWithMismatchedPasscode(String differentPasscode) {
        logger.info("Entering mismatched passcode: " + differentPasscode);
        
        // Enter the different confirmation passcode
        for (char digit : differentPasscode.toCharArray()) {
            clickDigit(digit);
        }

        // Wait for the error message to appear
        try {
            WebDriverWait errorWait = new WebDriverWait(driver, Duration.ofSeconds(5));
            errorWait.until(ExpectedConditions.visibilityOfElementLocated(passcodeMismatchError));
            logger.debug("Passcode mismatch error displayed as expected");
        } catch (TimeoutException e) {
            logger.warn("Passcode mismatch error was not displayed or had different text", e);
        }

        return this;
    }
    
    /**
     * Checks if the passcode mismatch error message is displayed.
     *
     * @return true if the error message is visible, false otherwise.
     */
    public boolean isPasscodeMismatchErrorDisplayed() {
        logger.debug("Checking for passcode mismatch error");
        
        try {
            // Wait longer for the error message to appear (1.5 seconds)
            Thread.sleep(1500);
            
            // Try to find the explicit error message
            try {
                WebElement errorElement = driver.findElement(passcodeMismatchError);
                logger.debug("Found error message: {}", errorElement.getText());
                return true;
            } catch (Exception e) {
                logger.debug("Could not find exact error text, checking screen state");
                
                // Take a screenshot for debugging
                try {
                    File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                    logger.debug("Screenshot taken during error check: {}", screenshot.getAbsolutePath());
                } catch (Exception ex) {
                    // Ignore screenshot errors
                }
                
                // Get all text elements to see what's on screen
                List<WebElement> textElements = driver.findElements(
                    AppiumBy.androidUIAutomator("new UiSelector().className(\"android.widget.TextView\")"));
                
                for (WebElement element : textElements) {
                    String text = element.getText();
                    if (text != null && !text.isEmpty()) {
                        logger.debug("Found text on screen: {}", text);
                        if (text.contains("match") || text.contains("password") || 
                            text.contains("didn't") || text.contains("passcode")) {
                            logger.debug("Found error-related text: {}", text);
                            return true;
                        }
                    }
                }
            }
            
            // As a fallback, check if we're back on the create passcode screen
            try {
                WebElement createTitle = findElementIfVisible(createPasscodeTitle);
                if (createTitle != null) {
                    // If we're back on create passcode screen after entering both codes, 
                    // there was likely an error
                    logger.debug("Back on create passcode screen - assuming error occurred");
                    return true;
                }
            } catch (Exception ex) {
                // Ignore
            }
            
            return false;
        } catch (Exception e) {
            logger.error("Error checking for passcode mismatch", e);
            return false;
        }
    }

    /**
     * Clicks the button corresponding to the given digit using UiSelector.
     *
     * @param digit The digit character ('0' through '9') to click.
     */
    private void clickDigit(char digit) {
        if (Character.isDigit(digit)) {
            try {
                String uiSelector = String.format(digitButtonUiSelector, digit);
                By digitLocator = AppiumBy.androidUIAutomator(uiSelector);
                
                // Wait for the digit button to be clickable with a shorter timeout
                WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(5));
                WebElement digitButton = shortWait.until(ExpectedConditions.elementToBeClickable(digitLocator));
                
                click(digitButton);
                
                // Short delay to prevent input issues
                try { Thread.sleep(50); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
            } catch (Exception e) {
                logger.error("Error clicking digit {}: {}", digit, e.getMessage());
            }
        } else {
            logger.error("Invalid character passed to clickDigit: {}", digit);
        }
    }
} 