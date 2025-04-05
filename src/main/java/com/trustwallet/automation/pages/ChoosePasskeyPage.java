package com.trustwallet.automation.pages;

import com.trustwallet.automation.base.BasePage;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.NoSuchElementException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// Renamed from CreateOptionsPage
public class ChoosePasskeyPage extends BasePage {
    private static final Logger logger = LogManager.getLogger(ChoosePasskeyPage.class);

    // Locators
    private final By pageTitle = AppiumBy.androidUIAutomator("new UiSelector().text(\"Create new wallet\")");
    private final By backButton = AppiumBy.androidUIAutomator("new UiSelector().description(\"Back\")");
    
    // Secret phrase section locators
    private final By secretPhraseCreateButton = AppiumBy.androidUIAutomator("new UiSelector().resourceId(\"secretPhraseCreateButton\")");
    private final By secretPhraseCreateButtonAlt = AppiumBy.androidUIAutomator("new UiSelector().text(\"Create\")");
    
    // Swift section locators
    private final By swiftCreateButton = AppiumBy.androidUIAutomator("new UiSelector().resourceId(\"swiftCreateButton\")");

    // Details sections
    private final By secretPhraseHeader = AppiumBy.androidUIAutomator("new UiSelector().text(\"Secret phrase\")");
    private final By swiftHeader = AppiumBy.androidUIAutomator("new UiSelector().text(\"Swift\")");
    
    private final By secretPhraseShowDetails = AppiumBy.androidUIAutomator("new UiSelector().text(\"Show details\")");
    private final By secretPhraseItemIcon = AppiumBy.androidUIAutomator("new UiSelector().resourceId(\"itemIcon\").instance(0)");
    private final By swiftItemIcon = AppiumBy.androidUIAutomator("new UiSelector().resourceId(\"itemIcon\").instance(1)");

    public ChoosePasskeyPage(AppiumDriver driver) {
        super(driver);
        logger.debug("ChoosePasskeyPage initialized");
    }

    /**
     * Checks if the Create Options page is displayed by verifying the page title.
     *
     * @return true if the page title is visible, false otherwise.
     */
    public boolean isPageDisplayed() {
        try {
            waitForElementToBeVisible(driver.findElement(pageTitle));
            logger.debug("Choose Passkey page is displayed");
            return true;
        } catch (Exception e) {
            logger.error("Choose Passkey page is not displayed", e);
            return false;
        }
    }

    /**
     * Expands the details section for Secret Phrase if it's not already expanded.
     */
    public void expandSecretPhraseDetails() {
        if (isElementPresent(secretPhraseShowDetails)) {
            logger.debug("Expanding Secret Phrase details");
            WebElement button = driver.findElement(secretPhraseItemIcon);
            click(button);
        }
    }

    /**
     * Expands the details section for Swift if it's not already expanded.
     */
    public void expandSwiftDetails() {
        if (isElementPresent(secretPhraseShowDetails)) {
            logger.debug("Expanding Swift details");
            WebElement button = driver.findElement(swiftItemIcon);
            click(button);
        }
    }

    /**
     * Clicks the 'Create' button for the Secret Phrase option.
     * Uses multiple locator strategies for reliability.
     *
     * @return A new instance of WalletHomePage.
     */
    public WalletHomePage clickSecretPhraseCreate() {
        logger.info("Clicking 'Create' for Secret Phrase");
        WebElement button = null;
        
        try {
            logger.debug("Trying direct resource ID locator");
            button = waitForElementToBeClickable(secretPhraseCreateButton);
        } catch (Exception e) {
            logger.debug("Primary locator failed, trying text locator");
            button = waitForElementToBeClickable(secretPhraseCreateButtonAlt);
        }
        
        click(button);
        
        // Check for the What's New popup before returning the WalletHomePage
        dismissWhatsNewPopupIfPresent();
        
        return new WalletHomePage(driver);
    }

    /**
     * Checks for and dismisses the "What's New" survey popup that might appear
     * after clicking create wallet button.
     */
    private void dismissWhatsNewPopupIfPresent() {
        try {
            // Wait a short time for the popup to appear (if it's going to)
            Thread.sleep(3000);
            
            // Check if the popup is present
            By whatsNewTitle = AppiumBy.androidUIAutomator("new UiSelector().text(\"What's New\")");
            By getStartedButton = AppiumBy.androidUIAutomator("new UiSelector().text(\"GET STARTED\")");
            
            if (isElementPresent(whatsNewTitle)) {
                logger.info("\"What's New\" popup detected. Dismissing it");
                
                // Click the GET STARTED button to dismiss the popup
                WebElement startButton = driver.findElement(getStartedButton);
                click(startButton);
                logger.debug("Successfully dismissed the \"What's New\" popup");
                
                // Wait for the popup to be dismissed
                Thread.sleep(1000);
            }
        } catch (Exception e) {
            logger.debug("No \"What's New\" popup detected or error dismissing it: {}", e.getMessage());
        }
    }

    /**
     * Clicks the 'Create' button for the Swift option.
     *
     * @return A new instance of SwiftSafetyTipsPage.
     */
    public SwiftSafetyTipsPage clickSwiftCreate() {
        logger.info("Clicking 'Create' for Swift");
        WebElement button = waitForElementToBeClickable(swiftCreateButton);
        click(button);
        return new SwiftSafetyTipsPage(driver);
    }

    /**
     * Clicks the back button in the toolbar.
     *
     * @return A new instance of WelcomePage (adjust if navigation leads elsewhere).
     */
    public WelcomePage clickBackButton() {
        logger.info("Clicking the back button");
        WebElement button = waitForElementToBeClickable(backButton);
        click(button);
        return new WelcomePage(driver);
    }
} 