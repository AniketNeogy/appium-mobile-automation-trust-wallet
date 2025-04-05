package com.trustwallet.automation.pages;

import com.trustwallet.automation.base.BasePage;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SwiftSafetyTipsPage extends BasePage {
    private static final Logger logger = LogManager.getLogger(SwiftSafetyTipsPage.class);

    // Locators
    private final By pageTitle = AppiumBy.androidUIAutomator("new UiSelector().text(\"Safety tips\")");
    private final By backButton = AppiumBy.androidUIAutomator("new UiSelector().content-desc(\"Back\")");
    
    private final By mainTitle = AppiumBy.androidUIAutomator("new UiSelector().text(\"Passkey is the key to your wallet\")");
    private final By instructionText = AppiumBy.androidUIAutomator("new UiSelector().text(\"Tap on all checkboxes to confirm you understand the importance of passkeys.\")");
    
    // Checkbox locators
    private final By autoSyncCheckbox = AppiumBy.androidUIAutomator(
            "new UiSelector().text(\"Once Swift wallet is created, passkey info (fingerprint, face recognition) will auto-sync on your Google account.\")");
    
    private final By recoveryCheckbox = AppiumBy.androidUIAutomator(
            "new UiSelector().text(\"Passkeys allow you to recover the wallet in future, if your device is lost or replaced.\")");
    
    private final By importantWarningCheckbox = AppiumBy.androidUIAutomator(
            "new UiSelector().text(\"IMPORTANT: If passkey is deleted, you will lose access to the wallet and all funds.\")");
    
    private final By continueButton = AppiumBy.androidUIAutomator(
            "new UiSelector().text(\"Continue\")");
    
    private final By whatIsPasskeyLink = AppiumBy.androidUIAutomator(
            "new UiSelector().text(\"What is passkey?\")");

    /**
     * Constructor for SwiftSafetyTipsPage.
     *
     * @param driver The AppiumDriver instance.
     */
    public SwiftSafetyTipsPage(AppiumDriver driver) {
        super(driver);
        logger.debug("SwiftSafetyTipsPage initialized");
    }

    /**
     * Checks if the Swift Safety Tips page is displayed by verifying the page title.
     *
     * @return true if the page is displayed, false otherwise.
     */
    public boolean isPageDisplayed() {
        try {
            WebElement title = findElementIfVisible(pageTitle);
            WebElement header = findElementIfVisible(mainTitle);
            
            boolean displayed = (title != null || header != null);
            if (displayed) {
                logger.debug("Swift Safety Tips page is displayed");
            } else {
                logger.debug("Swift Safety Tips page is not displayed");
            }
            return displayed;
        } catch (NoSuchElementException e) {
            logger.error("Failed to check if Swift Safety Tips page is displayed", e);
            return false;
        }
    }
    
    /**
     * Checks if the Continue button is enabled (visually).
     *
     * @return true if the button is enabled, false otherwise.
     */
    public boolean isContinueButtonEnabled() {
        try {
            WebElement button = driver.findElement(continueButton);
            boolean isEnabled = button.isEnabled();
            logger.debug("Continue button is enabled: {}", isEnabled);
            return isEnabled;
        } catch (Exception e) {
            logger.error("Failed to check if Continue button is enabled", e);
            return false;
        }
    }
    
    /**
     * Checks if the Continue button is actually clickable (not just visually enabled).
     * This uses the WebDriverWait ExpectedConditions to verify the element is clickable.
     *
     * @return true if the button is clickable, false otherwise.
     */
    public boolean isContinueButtonClickable() {
        try {
            // Short timeout to quickly validate if element is clickable
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
            wait.until(ExpectedConditions.elementToBeClickable(continueButton));
            logger.debug("Continue button is clickable");
            return true;
        } catch (Exception e) {
            logger.debug("Continue button is not clickable: {}", e.getMessage());
            return false;
        }
    }
    
    /**
     * Clicks the auto-sync checkbox.
     *
     * @return this SwiftSafetyTipsPage instance for method chaining.
     */
    public SwiftSafetyTipsPage clickAutoSyncCheckbox() {
        logger.info("Clicking auto-sync checkbox");
        try {
            WebElement checkbox = waitForElementToBeClickable(autoSyncCheckbox);
            click(checkbox);
            return this;
        } catch (Exception e) {
            logger.error("Failed to click auto-sync checkbox", e);
            throw e;
        }
    }
    
    /**
     * Clicks the recovery checkbox.
     *
     * @return this SwiftSafetyTipsPage instance for method chaining.
     */
    public SwiftSafetyTipsPage clickRecoveryCheckbox() {
        logger.info("Clicking recovery checkbox");
        try {
            WebElement checkbox = waitForElementToBeClickable(recoveryCheckbox);
            click(checkbox);
            return this;
        } catch (Exception e) {
            logger.error("Failed to click recovery checkbox", e);
            throw e;
        }
    }
    
    /**
     * Clicks the important warning checkbox.
     *
     * @return this SwiftSafetyTipsPage instance for method chaining.
     */
    public SwiftSafetyTipsPage clickImportantWarningCheckbox() {
        logger.info("Clicking important warning checkbox");
        try {
            WebElement checkbox = waitForElementToBeClickable(importantWarningCheckbox);
            click(checkbox);
            return this;
        } catch (Exception e) {
            logger.error("Failed to click important warning checkbox", e);
            throw e;
        }
    }
    
    /**
     * Clicks all checkboxes in sequence.
     *
     * @return this SwiftSafetyTipsPage instance for method chaining.
     */
    public SwiftSafetyTipsPage clickAllCheckboxes() {
        logger.info("Clicking all checkboxes");
        try {
            clickAutoSyncCheckbox();
            clickRecoveryCheckbox();
            clickImportantWarningCheckbox();
            return this;
        } catch (Exception e) {
            logger.error("Failed to click all checkboxes", e);
            throw e;
        }
    }
    
    /**
     * Clicks the Continue button after accepting all safety tips.
     * Make sure all checkboxes are checked before calling this method.
     *
     * @return A new instance of SwiftQuizPage.
     */
    public SwiftQuizPage clickContinue() {
        logger.info("Clicking Continue button");
        try {
            if (!isContinueButtonClickable()) {
                logger.warn("Continue button is not clickable. Ensure all checkboxes are checked.");
                clickAllCheckboxes();
            }
            
            WebElement button = waitForElementToBeClickable(continueButton);
            click(button);
            return new SwiftQuizPage(driver);
        } catch (Exception e) {
            logger.error("Failed to click Continue button", e);
            throw e;
        }
    }
    
    /**
     * Clicks the "What is passkey?" link.
     *
     * @return A new page object for the passkey information page (not implemented yet).
     */
    public SwiftSafetyTipsPage clickWhatIsPasskeyLink() {
        logger.info("Clicking 'What is passkey?' link");
        try {
            WebElement link = waitForElementToBeClickable(whatIsPasskeyLink);
            click(link);
            // Note: Ideally we would return a new page object here, but for now we return the same page
            logger.warn("'What is passkey?' page not implemented yet, returning to same page");
            return this;
        } catch (Exception e) {
            logger.error("Failed to click 'What is passkey?' link", e);
            throw e;
        }
    }
    
    /**
     * Clicks the back button to return to the previous page.
     *
     * @return A new instance of ChoosePasskeyPage.
     */
    public ChoosePasskeyPage clickBackButton() {
        logger.info("Clicking back button");
        try {
            WebElement button = waitForElementToBeClickable(backButton);
            click(button);
            return new ChoosePasskeyPage(driver);
        } catch (Exception e) {
            logger.error("Failed to click back button", e);
            throw e;
        }
    }
    
    /**
     * Complete the safety tips page by checking all checkboxes and clicking continue.
     *
     * @return A new instance of SwiftQuizPage.
     */
    public SwiftQuizPage completeAllSafetyTips() {
        logger.info("Completing all safety tips");
        clickAllCheckboxes();
        return clickContinue();
    }
} 