package com.trustwallet.automation.pages;

import com.trustwallet.automation.base.BasePage;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.NoSuchElementException;

public class WalletHomePage extends BasePage {

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

    public WalletHomePage(AppiumDriver driver) {
        super(driver);
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
                System.out.println("Detected wallet home page (empty wallet view)");
                return true;
            }

            if (isElementPresent(cryptoTab) || isElementPresent(nftsTab)) {
                System.out.println("Detected wallet home page (crypto/NFT tabs)");
                return true;
            }

            if (isElementPresent(sendButton) || isElementPresent(receiveButton) || 
                isElementPresent(buyButton) || isElementPresent(sellButton)) {
                System.out.println("Detected wallet home page (action buttons)");
                return true;
            }

            if (isElementPresent(homeNavButton)) {
                System.out.println("Detected wallet home page (bottom navigation)");
                return true;
            }
            
            return false;
        } catch (Exception e) {
            System.err.println("Error while checking if wallet home page is displayed: " + e.getMessage());
            return false;
        }
    }

    /**
     * Gets the displayed wallet name from the top bar.
     *
     * @return The wallet name string.
     */
    public String getWalletName() {
        return getText(walletNameText);
    }

    /**
     * Clicks the wallet name in the top bar to navigate to the Manage Wallets page.
     *
     * @return A new instance of ManageWalletsPage.
     */
    public ManageWalletsPage clickWalletName() {
        System.out.println("Clicking wallet name to open Manage Wallets page...");
        click(waitForElementToBeClickable(walletNameText));
        return new ManageWalletsPage(driver);
    }

    /**
     * Gets the main balance displayed.
     *
     * @return The balance string (e.g., "$0.00").
     */
    public String getMainBalance() {
        return getText(mainBalanceText);
    }

    /**
     * Checks if the 'Wallet is empty' text is displayed.
     *
     * @return true if the empty text is visible, false otherwise.
     */
    public boolean isWalletEmptyMessageDisplayed() {
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
            System.err.println("Element not found for locator: " + locator);
            return "";
        }
    }
} 