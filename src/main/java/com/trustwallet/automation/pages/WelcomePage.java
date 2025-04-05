package com.trustwallet.automation.pages;

import com.trustwallet.automation.base.BasePage;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class WelcomePage extends BasePage {
    private static final Logger logger = LogManager.getLogger(WelcomePage.class);

    // Locators
    private final By createNewWalletButton = AppiumBy.androidUIAutomator(
            "new UiSelector().resourceId(\"CreateNewWalletButton\")");
    private final By importWalletButton = AppiumBy.androidUIAutomator(
            "new UiSelector().resourceId(\"ImportWalletButton\")");

    public WelcomePage(AppiumDriver driver) {
        super(driver);
        logger.debug("WelcomePage initialized");
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
            logger.debug("Welcome page is displayed");
            return true;
        } catch (Exception e) {
            logger.error("Welcome page is not displayed", e);
            return false;
        }
    }

    /**
     * Clicks the 'Create new wallet' button.
     *
     * @return A new instance of the PasscodePage.
     */
    public PasscodePage clickCreateNewWallet() {
        logger.info("Clicking 'Create new wallet' button");
        click(driver.findElement(createNewWalletButton));
        return new PasscodePage(driver);
    }

    /**
     * Clicks the 'I already have a wallet' button.
     *
     * @return A new instance of the ImportWalletPage (not implemented).
     */
    public Object clickImportWallet() {
        logger.info("Clicking 'I already have a wallet' button");
        click(driver.findElement(importWalletButton));
        logger.warn("ImportWalletPage not implemented. Returning null.");
        return null;
    }
} 