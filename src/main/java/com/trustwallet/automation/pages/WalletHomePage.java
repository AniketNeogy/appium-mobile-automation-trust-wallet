package com.trustwallet.automation.pages;

import com.trustwallet.automation.base.BasePage;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.NoSuchElementException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.Set;

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
     * Checks if the Wallet Home page is displayed by looking for key elements.
     * It looks for multiple components, as some may depend on the state of the wallet.
     *
     * @return true if wallet home page elements are found, false otherwise.
     */
    public boolean isPageDisplayed() {
        try {
            // Handle any potential redirection to the survey webpage
            if (handleWebSurveyRedirectIfPresent()) {
                // If we handled a redirection, wait a moment for the wallet home page to load
                Thread.sleep(1000);
            }
            
            // Handle "What's New" popup if present
            dismissWhatsNewPopupIfPresent();
            
            // First check: Look for empty wallet view (for new wallets)
            boolean isEmptyWalletView = isElementPresent(walletEmptyText);
            if (isEmptyWalletView) {
                logger.debug("Detected wallet home page (empty wallet view)");
                return true;
            }
            
            // Second check: Look for Crypto/NFT tabs (for all wallets)
            boolean hasTabs = isElementPresent(cryptoTab) && 
                              isElementPresent(nftsTab);
            if (hasTabs) {
                logger.debug("Detected wallet home page (crypto/NFT tabs)");
                return true;
            }
            
            // Third check: Look for action buttons (for all wallets)
            boolean hasActionButtons = isElementPresent(sendButton) && 
                                      isElementPresent(receiveButton) && 
                                      isElementPresent(buyButton);
            if (hasActionButtons) {
                logger.debug("Detected wallet home page (action buttons)");
                return true;
            }
            
            // Fourth check: Look for bottom navigation (for all wallet states)
            boolean hasBottomNav = isElementPresent(homeNavButton);
            if (hasBottomNav) {
                logger.debug("Detected wallet home page (bottom navigation)");
                return true;
            }
            
            // If we haven't found any identifying elements
            logger.error("Wallet home page not detected.");
            return false;
        } catch (Exception e) {
            logger.error("Error checking if wallet home page is displayed", e);
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
    
    /**
     * Handles redirection to short.trustwallet.com web survey page that occasionally
     * occurs after wallet creation. If detected, will navigate back to the wallet home page.
     * 
     * @return true if the redirection was detected and handled, false otherwise
     */
    public boolean handleWebSurveyRedirectIfPresent() {
        try {
            // Wait a short time to see if redirection occurs
            Thread.sleep(3000);
            
            // We can't easily switch contexts with the current driver,
            // so we'll check for visual elements that indicate we're on the survey webpage
            
            // Check page source for indicators of survey page
            String pageSource = driver.getPageSource();
            boolean redirectDetected = false;
            
            if (pageSource.contains("short.trustwallet.com") || 
                pageSource.contains("What Should We Build Next") ||
                pageSource.contains("Let's go")) {
                
                logger.info("Detected Trust Wallet survey webpage via page content");
                redirectDetected = true;
            }
            
            // Look for visual indicators of the survey page
            By webURLBar = By.xpath("//*[contains(@text, 'short.trustwallet.com')]");
            By nextButton = By.xpath("//*[contains(@text, 'Next')]");
            By whatShouldWeBuild = By.xpath("//*[contains(@text, 'What Should We Build Next')]");
            
            if (isElementPresent(webURLBar) || isElementPresent(nextButton) || isElementPresent(whatShouldWeBuild)) {
                logger.info("Detected Trust Wallet survey webpage via UI elements");
                
                // Press the back button to return to the app
                driver.navigate().back();
                logger.info("Navigated back from survey webpage to wallet home page");
                
                // Wait for the app to return to wallet home page
                Thread.sleep(2000);
                return true;
            }
            
            if (redirectDetected) {
                // Press the back button to return to the app
                driver.navigate().back();
                logger.info("Navigated back from survey webpage to wallet home page");
                
                // Wait for the app to return to wallet home page
                Thread.sleep(2000);
                return true;
            }
            
            return false;
        } catch (Exception e) {
            logger.debug("Error checking for survey webpage redirection: {}", e.getMessage());
            return false;
        }
    }
} 