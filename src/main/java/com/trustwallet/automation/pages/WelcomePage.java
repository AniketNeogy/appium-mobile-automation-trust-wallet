package com.trustwallet.automation.pages;

import com.trustwallet.automation.base.BasePage;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class WelcomePage extends BasePage {

    // Locators
    private final By createNewWalletButton = AppiumBy.androidUIAutomator(
            "new UiSelector().resourceId(\"CreateNewWalletButton\")");
    private final By importWalletButton = AppiumBy.androidUIAutomator(
            "new UiSelector().resourceId(\"ImportWalletButton\")");

    public WelcomePage(AppiumDriver driver) {
        super(driver);
    }

    /**
     * Checks if the Welcome page is currently displayed by verifying the presence
     * of the create new wallet button.
     *
     * @return true if the page is displayed, false otherwise.
     */
    public boolean isPageDisplayed() {
        try {
            waitForElementToBeVisible(driver.findElement(createNewWalletButton));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Clicks the 'Create new wallet' button.
     *
     * @return A new instance of the PasscodePage.
     */
    public PasscodePage clickCreateNewWallet() {
        click(driver.findElement(createNewWalletButton));
        return new PasscodePage(driver);
    }

    /**
     * Clicks the 'I already have a wallet' button.
     *
     * @return A new instance of the ImportWalletPage (not implemented).
     */
    public Object clickImportWallet() {
        click(driver.findElement(importWalletButton));
        System.out.println("Warning: ImportWalletPage not implemented. Returning null.");
        return null;
    }
} 