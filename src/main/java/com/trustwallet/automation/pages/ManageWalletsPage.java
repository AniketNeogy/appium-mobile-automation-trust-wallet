package com.trustwallet.automation.pages;

import com.trustwallet.automation.base.BasePage;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.NoSuchElementException;

public class ManageWalletsPage extends BasePage {

    // Locators
    private String textUiSelector = "new UiSelector().text(\"%s\")";
    private String resourceIdUiSelector = "new UiSelector().resourceId(\"com.wallet.crypto.trustapp:id/%s\")";
    private String descriptionUiSelector = "new UiSelector().description(\"%s\")";
    private String classNameInstanceUiSelector = "new UiSelector().className(\"%s\").instance(%d)";
    private final By pageTitle = AppiumBy.androidUIAutomator(String.format(textUiSelector, "Wallets"));
    private final By backButton = AppiumBy.androidUIAutomator(String.format(descriptionUiSelector, "Back"));
    private final By addWalletButton = AppiumBy.androidUIAutomator(String.format(resourceIdUiSelector, "addWalletIconButton"));
    private final By settingsButton = AppiumBy.androidUIAutomator(String.format(resourceIdUiSelector, "topBarSettingsIcon"));
    private final By firstWalletName = AppiumBy.androidUIAutomator("new UiSelector().resourceId(\"com.wallet.crypto.trustapp:id/itemTitle\").instance(0)");
    private final By firstWalletDetailsButton = AppiumBy.androidUIAutomator(String.format(resourceIdUiSelector, "walletDetailsIconButton"));

    public ManageWalletsPage(AppiumDriver driver) {
        super(driver);
    }

    /**
     * Checks if the Manage Wallets page is displayed by verifying the page title
     * and the add wallet button.
     *
     * @return true if the page seems displayed, false otherwise.
     */
    public boolean isPageDisplayed() {
        boolean titleVisible = isElementPresent(pageTitle);
        boolean addButtonVisible = isElementPresent(addWalletButton);
        return titleVisible && addButtonVisible;
    }

    /**
     * Gets the name of the first wallet listed.
     *
     * @return The name string.
     */
    public String getFirstWalletName() {
        return getText(firstWalletName);
    }

    /**
     * Clicks the details (3-dots) button for the first wallet listed.
     *
     * @return A new instance of SetWalletNamePage.
     */
    public SetWalletNamePage clickFirstWalletDetails() {
        System.out.println("Clicking details (3-dots) for the first wallet...");
        click(waitForElementToBeClickable(firstWalletDetailsButton));
        return new SetWalletNamePage(driver);
    }

    /**
     * Clicks the 'Add wallet' button.
     *
     * @return A new instance of ChoosePasskeyPage.
     */
    public ChoosePasskeyPage clickAddWallet() {
        System.out.println("Clicking Add wallet button...");
        click(waitForElementToBeClickable(addWalletButton));
        return new ChoosePasskeyPage(driver);
    }

    /**
     * Clicks the Settings icon in the top bar.
     *
     * @return A new instance of SettingsPage (to be created).
     */
    public Object clickSettings() {
        System.out.println("Clicking Settings icon...");
        click(waitForElementToBeClickable(settingsButton));
        return null;
    }

    /**
     * Clicks the Back button.
     *
     * @return A new instance of WalletHomePage.
     */
    public WalletHomePage clickBackButton() {
        System.out.println("Clicking Back button...");
        click(waitForElementToBeClickable(backButton));
        return new WalletHomePage(driver);
    }

    /**
     * Helper to get text safely
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