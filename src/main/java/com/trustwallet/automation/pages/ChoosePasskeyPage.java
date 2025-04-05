package com.trustwallet.automation.pages;

import com.trustwallet.automation.base.BasePage;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.NoSuchElementException;

// Renamed from CreateOptionsPage
public class ChoosePasskeyPage extends BasePage {

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
    }

    /**
     * Checks if the Create Options page is displayed by verifying the page title.
     *
     * @return true if the page title is visible, false otherwise.
     */
    public boolean isPageDisplayed() {
        try {
            waitForElementToBeVisible(driver.findElement(pageTitle));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Expands the details section for Secret Phrase if it's not already expanded.
     */
    public void expandSecretPhraseDetails() {
        if (isElementPresent(secretPhraseShowDetails)) {
            System.out.println("Expanding Secret Phrase details...");
            WebElement button = driver.findElement(secretPhraseItemIcon);
            click(button);
        }
    }

    /**
     * Expands the details section for Swift if it's not already expanded.
     */
    public void expandSwiftDetails() {
        if (isElementPresent(secretPhraseShowDetails)) {
            System.out.println("Expanding Swift details...");
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
        System.out.println("Clicking 'Create' for Secret Phrase...");
        WebElement button = null;
        
        try {
            System.out.println("Trying direct resource ID locator...");
            button = waitForElementToBeClickable(secretPhraseCreateButton);
        } catch (Exception e) {
            System.out.println("Primary locator failed, trying text locator...");
            button = waitForElementToBeClickable(secretPhraseCreateButtonAlt);
        }
        
        click(button);
        return new WalletHomePage(driver);
    }

    /**
     * Clicks the 'Create' button for the Swift option.
     *
     * @return A new instance of SwiftSafetyTipsPage.
     */
    public SwiftSafetyTipsPage clickSwiftCreate() {
        System.out.println("Clicking 'Create' for Swift...");
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
        System.out.println("Clicking the back button...");
        WebElement button = waitForElementToBeClickable(backButton);
        click(button);
        // Assuming back leads to WelcomePage, adjust if necessary
        return new WelcomePage(driver);
    }
} 