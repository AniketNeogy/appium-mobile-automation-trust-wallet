package com.trustwallet.automation.pages;

import com.trustwallet.automation.base.BasePage;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.NoSuchElementException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SetWalletNamePage extends BasePage {
    private static final Logger logger = LogManager.getLogger(SetWalletNamePage.class);

    // Locators
    private final By pageTitle = AppiumBy.androidUIAutomator("new UiSelector().text(\"Set wallet name\")");
    private final By backButton = AppiumBy.androidUIAutomator("new UiSelector().content-desc(\"Back\")");
    private final By walletNameLabel = AppiumBy.androidUIAutomator("new UiSelector().text(\"Wallet name\")");
    private final By walletNameInputField = AppiumBy.androidUIAutomator("new UiSelector().className(\"android.widget.EditText\")");
    private final By helpText = AppiumBy.androidUIAutomator("new UiSelector().text(\"Wallet name should be between 4 to 24 characters\")");
    private final By doneButton = AppiumBy.androidUIAutomator("new UiSelector().text(\"Done\")");
    private final By clearInputIcon = AppiumBy.androidUIAutomator("new UiSelector().resourceId(\"itemIcon\")");

    /**
     * Constructor for SetWalletNamePage.
     *
     * @param driver The AppiumDriver instance.
     */
    public SetWalletNamePage(AppiumDriver driver) {
        super(driver);
        logger.debug("SetWalletNamePage initialized");
    }

    /**
     * Checks if the Set Wallet Name page is displayed by verifying the page title.
     *
     * @return true if the page is displayed, false otherwise.
     */
    public boolean isPageDisplayed() {
        try {
            WebElement title = findElementIfVisible(pageTitle);
            WebElement input = findElementIfVisible(walletNameInputField);
            
            boolean displayed = (title != null && input != null);
            if (displayed) {
                logger.debug("Set Wallet Name page is displayed");
            } else {
                logger.debug("Set Wallet Name page is not displayed");
            }
            return displayed;
        } catch (NoSuchElementException e) {
            logger.error("Failed to check if Set Wallet Name page is displayed", e);
            return false;
        }
    }
    
    /**
     * Enters a wallet name in the input field.
     *
     * @param walletName The wallet name to enter.
     * @return this SetWalletNamePage instance for method chaining.
     */
    public SetWalletNamePage enterWalletName(String walletName) {
        logger.info("Entering wallet name: {}", walletName);
        try {
            WebElement inputField = waitForElementToBeClickable(walletNameInputField);
            click(inputField);
            sendKeys(inputField, walletName);
            return this;
        } catch (Exception e) {
            logger.error("Failed to enter wallet name", e);
            throw e;
        }
    }
    
    /**
     * Clears the wallet name input field using the clear icon.
     *
     * @return this SetWalletNamePage instance for method chaining.
     */
    public SetWalletNamePage clearWalletName() {
        logger.info("Clearing wallet name");
        try {
            // First check if there's any text entered (clear icon only appears when text exists)
            if (isElementPresent(clearInputIcon)) {
                WebElement clearIcon = waitForElementToBeClickable(clearInputIcon);
                click(clearIcon);
            } else {
                // If no clear icon, just focus and clear the field
                WebElement inputField = waitForElementToBeClickable(walletNameInputField);
                click(inputField);
                inputField.clear();
            }
            return this;
        } catch (Exception e) {
            logger.error("Failed to clear wallet name", e);
            throw e;
        }
    }
    
    /**
     * Gets the current wallet name from the input field.
     *
     * @return The wallet name text.
     */
    public String getWalletName() {
        logger.debug("Getting wallet name");
        try {
            WebElement inputField = driver.findElement(walletNameInputField);
            String walletName = inputField.getText();
            if (walletName == null || walletName.isEmpty()) {
                walletName = inputField.getAttribute("text");
            }
            logger.debug("Current wallet name: {}", walletName);
            return walletName;
        } catch (Exception e) {
            logger.error("Failed to get wallet name", e);
            return "";
        }
    }
    
    /**
     * Checks if the Done button is enabled.
     *
     * @return true if the button is enabled, false otherwise.
     */
    public boolean isDoneButtonEnabled() {
        try {
            // Find the button container view
            WebElement buttonContainer = driver.findElement(By.xpath(
                "//android.widget.TextView[@text='Done']/parent::android.view.View"));
            
            // Check the enabled attribute
            String enabled = buttonContainer.getAttribute("enabled");
            boolean isEnabled = Boolean.parseBoolean(enabled);
            
            logger.debug("Done button is enabled: {}", isEnabled);
            return isEnabled;
        } catch (Exception e) {
            logger.error("Failed to check if Done button is enabled", e);
            return false;
        }
    }
    
    /**
     * Clicks the Done button to proceed with creating the wallet.
     * Note: This button is only enabled when a valid wallet name is entered.
     *
     * @return A new instance of WalletHomePage (assuming that's the next page).
     */
    public WalletHomePage clickDone() {
        logger.info("Clicking Done button");
        try {
            if (!isDoneButtonEnabled()) {
                logger.warn("Done button is not enabled. Check that a valid wallet name is entered.");
            }
            
            WebElement button = waitForElementToBeClickable(doneButton);
            click(button);
            return new WalletHomePage(driver);
        } catch (Exception e) {
            logger.error("Failed to click Done button", e);
            throw e;
        }
    }
    
    /**
     * Clicks the back button to return to the previous page.
     *
     * @return A new instance of SwiftQuizPage.
     */
    public SwiftQuizPage clickBackButton() {
        logger.info("Clicking back button");
        try {
            WebElement button = waitForElementToBeClickable(backButton);
            click(button);
            return new SwiftQuizPage(driver);
        } catch (Exception e) {
            logger.error("Failed to click back button", e);
            throw e;
        }
    }
    
    /**
     * Gets the help text for wallet name requirements.
     *
     * @return The help text string.
     */
    public String getHelpText() {
        logger.debug("Getting help text");
        try {
            return getText(driver.findElement(helpText));
        } catch (Exception e) {
            logger.error("Failed to get help text", e);
            return "";
        }
    }
} 