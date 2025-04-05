package com.trustwallet.automation.pages;

import com.trustwallet.automation.base.BasePage;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.NoSuchElementException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class WalletHomePage extends BasePage {
    private static final Logger logger = LogManager.getLogger(WalletHomePage.class);

    // Locators
    private final By walletNameText = AppiumBy.androidUIAutomator("new UiSelector().resourceId(\"topBarWalletName\")");
    private final By mainBalanceText = AppiumBy.androidUIAutomator("new UiSelector().resourceId(\"mainBalance\")");
    private final By walletEmptyText = AppiumBy.androidUIAutomator("new UiSelector().text(\"Your wallet is empty.\")");
    private final By cryptoTab = AppiumBy.androidUIAutomator("new UiSelector().resourceId(\"Crypto\")");
    private final By nftsTab = AppiumBy.androidUIAutomator("new UiSelector().resourceId(\"NFTs\")");
    private final By sendButton = AppiumBy.androidUIAutomator("new UiSelector().resourceId(\"HomeSendButton\")");
    private final By receiveButton = AppiumBy.androidUIAutomator("new UiSelector().resourceId(\"HomeReceiveButton\")");
    private final By buyButton = AppiumBy.androidUIAutomator("new UiSelector().resourceId(\"HomeBuyButton\")");
    private final By sellButton = AppiumBy.androidUIAutomator("new UiSelector().resourceId(\"HomeSellButton\")");
    private final By homeNavButton = AppiumBy.androidUIAutomator("new UiSelector().resourceId(\"HomeNavigationButton\")");
    private final By trendingNavButton = AppiumBy.androidUIAutomator("new UiSelector().resourceId(\"TrendingTokenNavigationButton\")");
    private final By swapNavButton = AppiumBy.androidUIAutomator("new UiSelector().resourceId(\"SwapNavigationButton\")");
    private final By earnNavButton = AppiumBy.androidUIAutomator("new UiSelector().resourceId(\"EarnNavigationButton\")");
    private final By discoverNavButton = AppiumBy.androidUIAutomator("new UiSelector().resourceId(\"DiscoverNavigationButton\")");

    // What's New popup locators
    private final By whatsNewTitle = AppiumBy.androidUIAutomator("new UiSelector().text(\"What's New\")");
    private final By getStartedButton = AppiumBy.androidUIAutomator("new UiSelector().text(\"GET STARTED\")");

    public WalletHomePage(AppiumDriver driver) {
        super(driver);
        logger.debug("WalletHomePage initialized");
        // Check for and dismiss the "What's New" popup if it appears
        dismissWhatsNewPopupIfPresent();
    }

    /**
     * Checks if the Wallet Home page is displayed by verifying key elements.
     * Uses multiple detection strategies for better reliability.
     *
     * @return true if the page seems displayed, false otherwise.
     */
    public boolean isPageDisplayed() {
        try {
            if (isElementPresent(walletEmptyText)) {
                logger.debug("Detected wallet home page (empty wallet view)");
                return true;
            }

            if (isElementPresent(cryptoTab) || isElementPresent(nftsTab)) {
                logger.debug("Detected wallet home page (crypto/NFT tabs)");
                return true;
            }

            if (isElementPresent(sendButton) || isElementPresent(receiveButton) || 
                isElementPresent(buyButton) || isElementPresent(sellButton)) {
                logger.debug("Detected wallet home page (action buttons)");
                return true;
            }

            if (isElementPresent(homeNavButton)) {
                logger.debug("Detected wallet home page (bottom navigation)");
                return true;
            }
            
            logger.debug("Wallet home page not detected");
            return false;
        } catch (Exception e) {
            logger.error("Error while checking if wallet home page is displayed", e);
            return false;
        }
    }

    /**
     * Gets the displayed wallet name from the top bar.
     *
     * @return The wallet name string.
     */
    public String getWalletName() {
        logger.debug("Getting wallet name");
        return getText(walletNameText);
    }

    /**
     * Clicks the wallet name in the top bar to navigate to the Manage Wallets page.
     *
     * @return A new instance of ManageWalletsPage.
     */
    public ManageWalletsPage clickWalletName() {
        logger.info("Clicking wallet name to open Manage Wallets page");
        click(waitForElementToBeClickable(walletNameText));
        return new ManageWalletsPage(driver);
    }

    /**
     * Gets the main balance displayed.
     *
     * @return The balance string (e.g., "$0.00").
     */
    public String getMainBalance() {
        logger.debug("Getting main balance");
        return getText(mainBalanceText);
    }

    /**
     * Checks if the 'Wallet is empty' text is displayed.
     *
     * @return true if the empty text is visible, false otherwise.
     */
    public boolean isWalletEmptyMessageDisplayed() {
        logger.debug("Checking if wallet empty message is displayed");
        return isElementPresent(walletEmptyText);
    }

    /**
     * Helper method to get text safely.
     */
    private String getText(By locator) {
        try {
            WebElement element = waitForElementToBeVisible(driver.findElement(locator));
            return element.getText();
        } catch (NoSuchElementException e) {
            logger.error("Element not found for locator: {}", locator);
            return "";
        }
    }

    /**
     * Checks for and dismisses the "What's New" survey popup that appears intermittently
     * after creating a wallet.
     */
    public void dismissWhatsNewPopupIfPresent() {
        try {
            // Wait a short time for the popup to appear (if it's going to)
            Thread.sleep(2000);
            
            // Check if the popup is present
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
} 