package com.trustwallet.automation.pages;

import com.trustwallet.automation.base.BasePage;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.NoSuchElementException;

public class SetWalletNamePage extends BasePage {

    // Locators
    private String textUiSelector = "new UiSelector().text(\"%s\")";
    private String resourceIdUiSelector = "new UiSelector().resourceId(\"com.wallet.crypto.trustapp:id/%s\")";
    private String descriptionUiSelector = "new UiSelector().description(\"%s\")";
    private String pageTitleUiSelector = "new UiSelector().className(\"android.view.View\").description(\"Back\").xpath(\"./ancestor::*[contains(@resource-id, \'toolbar\')]/android.widget.TextView[1]\")";
    private final By backButton = AppiumBy.androidUIAutomator(String.format(descriptionUiSelector, "Back"));
    private final By walletNameField = AppiumBy.androidUIAutomator(String.format(resourceIdUiSelector, "walletNameField"));
    private final By clearNameButton = AppiumBy.androidUIAutomator(
            "new UiSelector().className(\"android.widget.EditText\").resourceIdMatches(\".*walletNameField\").childSelector(new UiSelector().className(\"android.view.View\"))");
    private final By deleteWalletButton = AppiumBy.androidUIAutomator(String.format(resourceIdUiSelector, "deleteWalletButton"));
    private final By googleDriveBackupRow = AppiumBy.androidUIAutomator(String.format(resourceIdUiSelector, "googleDriveBackupCell"));
    private final By manualBackupRow = AppiumBy.androidUIAutomator(String.format(resourceIdUiSelector, "manualBackupCell"));
    private final By googleDriveBackupStatus = AppiumBy.androidUIAutomator(
             "new UiSelector().resourceId(\"com.wallet.crypto.trustapp:id/googleDriveBackupCell\").childSelector(new UiSelector().text(\"Back up now\"))");
    private final By manualBackupStatus = AppiumBy.androidUIAutomator(
             "new UiSelector().resourceId(\"com.wallet.crypto.trustapp:id/manualBackupCell\").childSelector(new UiSelector().textMatches(\"Back up now|Active\"))");

    public SetWalletNamePage(AppiumDriver driver) {
        super(driver);
    }

    /**
     * Checks if the Set Wallet Name/Details page is displayed by verifying the presence
     * of the wallet name input field and the delete button.
     *
     * @return true if the page seems displayed, false otherwise.
     */
    public boolean isPageDisplayed() {
        return isElementPresent(walletNameField) && isElementPresent(deleteWalletButton);
    }

    /**
     * Gets the current wallet name from the input field.
     *
     * @return The current wallet name.
     */
    public String getCurrentWalletName() {
        return getText(walletNameField);
    }

    /**
     * Clears the current wallet name using the clear button, with fallback to backspace.
     */
    public void clearWalletName() {
        System.out.println("Clearing wallet name...");
        try {
            click(waitForElementToBeClickable(clearNameButton));
        } catch (Exception e) {
             System.err.println("Clear button not found or failed to click. Attempting to clear via Backspace. Error: " + e.getMessage());
             WebElement field = driver.findElement(walletNameField);
             String currentName = field.getText();
             if (currentName != null && !currentName.isEmpty()) {
                 field.click();
                 for (int i = 0; i < currentName.length(); i++) {
                     if (driver instanceof AndroidDriver) {
                         ((AndroidDriver) driver).pressKey(new KeyEvent(AndroidKey.DEL));
                     } else {
                         System.err.println("Driver is not an instance of AndroidDriver, cannot use pressKey method");
                     }
                     try { Thread.sleep(50); } catch (InterruptedException ignored) {}
                 }
             }
        }
    }

    /**
     * Sets a new wallet name after clearing the existing one.
     *
     * @param newName The desired wallet name.
     */
    public void setWalletName(String newName) {
        System.out.println("Clearing and setting wallet name to: " + newName);
        clearWalletName();
        WebElement field = driver.findElement(walletNameField);
        field.sendKeys(newName);
    }

    /**
     * Clicks the Google Drive backup row.
     *
     * @return The next Page Object (e.g., GoogleDriveBackupPage - to be created).
     */
    public Object clickGoogleDriveBackup() {
        System.out.println("Clicking Google Drive backup row...");
        click(waitForElementToBeClickable(googleDriveBackupRow));
        return null;
    }

    /**
     * Clicks the Manual backup row.
     *
     * @return The next Page Object (e.g., BackupPromptPage - placeholder).
     */
    public Object clickManualBackup() {
        System.out.println("Clicking Manual backup row...");
        click(waitForElementToBeClickable(manualBackupRow));
        return null;
    }

    /**
     * Gets the status text displayed for Manual backup ("Back up now" or "Active").
     *
     * @return The status text, or empty string if not found.
     */
    public String getManualBackupStatus() {
         return getTextFromElementIfExists(manualBackupStatus);
    }

    /**
     * Gets the status text displayed for Google Drive backup ("Back up now").
     *
     * @return The status text, or empty string if not found.
     */
    public String getGoogleDriveBackupStatus() {
        return getTextFromElementIfExists(googleDriveBackupStatus);
    }

    /**
     * Clicks the Back button, saving the name change implicitly.
     *
     * @return A new instance of ManageWalletsPage.
     */
    public ManageWalletsPage clickBackButtonAndSaveChanges() {
        System.out.println("Clicking Back button to save changes...");
        click(waitForElementToBeClickable(backButton));
        try { Thread.sleep(500); } catch (InterruptedException ignored) {}
        return new ManageWalletsPage(driver);
    }

    /**
     * Clicks the Delete Wallet button.
     *
     * @return The next Page Object (likely a confirmation dialog or ManageWalletsPage - needs verification).
     */
    public Object clickDeleteWallet() {
        System.out.println("Clicking Delete Wallet button...");
        click(waitForElementToBeClickable(deleteWalletButton));
        return null;
    }

    /**
     * Gets text from an element
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

    /**
     * Gets text from an element if it exists
     */
    private String getTextFromElementIfExists(By locator) {
        try {
            WebElement element = driver.findElement(locator);
            return element.getText();
        } catch (NoSuchElementException e) {
            return "";
        }
    }
} 