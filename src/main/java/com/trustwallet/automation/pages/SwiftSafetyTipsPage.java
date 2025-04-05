package com.trustwallet.automation.pages;

import com.trustwallet.automation.base.BasePage;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.NoSuchElementException;
import java.util.List;

public class SwiftSafetyTipsPage extends BasePage {

    // Locators
    private String textUiSelector = "new UiSelector().text(\"%s\")";
    private String descriptionUiSelector = "new UiSelector().description(\"%s\")";
    private String resourceIdUiSelector = "new UiSelector().resourceId(\"com.wallet.crypto.trustapp:id/%s\")";
    private String itemIconInstanceUiSelector = "new UiSelector().resourceId(\"com.wallet.crypto.trustapp:id/itemIcon\").instance(%d)";
    private final By pageTitle = AppiumBy.androidUIAutomator(String.format(textUiSelector, "Safety tips"));
    private final By backButton = AppiumBy.androidUIAutomator(String.format(descriptionUiSelector, "Back"));
    private final By continueButton = AppiumBy.androidUIAutomator(String.format(textUiSelector, "Continue"));
    private final By whatIsPasskeyLink = AppiumBy.androidUIAutomator(String.format(textUiSelector, "What is passkey?"));
    private final By firstCheckboxIcon = AppiumBy.androidUIAutomator(String.format(itemIconInstanceUiSelector, 1));
    private final By secondCheckboxIcon = AppiumBy.androidUIAutomator(String.format(itemIconInstanceUiSelector, 2));
    private final By thirdCheckboxIcon = AppiumBy.androidUIAutomator(String.format(itemIconInstanceUiSelector, 3));
    private final By firstCheckboxRow = AppiumBy.androidUIAutomator(
            "new UiSelector().resourceId(\"com.wallet.crypto.trustapp:id/itemIcon\").instance(1).fromParent(new UiSelector().className(\"android.view.View\"))");
    private final By secondCheckboxRow = AppiumBy.androidUIAutomator(
            "new UiSelector().resourceId(\"com.wallet.crypto.trustapp:id/itemIcon\").instance(2).fromParent(new UiSelector().className(\"android.view.View\"))");
    private final By thirdCheckboxRow = AppiumBy.androidUIAutomator(
            "new UiSelector().resourceId(\"com.wallet.crypto.trustapp:id/itemIcon\").instance(3).fromParent(new UiSelector().className(\"android.view.View\"))");

    public SwiftSafetyTipsPage(AppiumDriver driver) {
        super(driver);
    }

    /**
     * Checks if the Safety Tips page is displayed by verifying the page title.
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
     * Clicks the first safety tip checkbox/row.
     */
    public void clickFirstCheckbox() {
        click(waitForElementToBeClickable(firstCheckboxRow));
    }

    /**
     * Clicks the second safety tip checkbox/row.
     */
    public void clickSecondCheckbox() {
        click(waitForElementToBeClickable(secondCheckboxRow));
    }

    /**
     * Clicks the third safety tip checkbox/row.
     */
    public void clickThirdCheckbox() {
        click(waitForElementToBeClickable(thirdCheckboxRow));
    }

    /**
     * Clicks all three safety tip checkboxes.
     */
    public void clickAllCheckboxes() {
        System.out.println("Clicking all safety checkboxes...");
        clickFirstCheckbox();
        clickSecondCheckbox();
        clickThirdCheckbox();
    }

    /**
     * Checks if the Continue button is enabled.
     * NOTE: The XML shows enabled="false" initially. This check might need adjustment
     * based on how the app updates the state after checking boxes.
     *
     * @return true if the button is enabled, false otherwise.
     */
     public boolean isContinueButtonEnabled() {
         try {
             WebElement button = driver.findElement(continueButton);
             return Boolean.parseBoolean(button.getAttribute("enabled"));
         } catch (NoSuchElementException e) {
             return false;
         }
     }

    /**
     * Clicks the Continue button.
     * Assumes the button becomes enabled after checking all boxes.
     *
     * @return A generic Object as we're not implementing the full flow.
     */
    public Object clickContinue() {
        System.out.println("Clicking Continue button...");
        WebElement button = waitForElementToBeClickable(continueButton);
        click(button);
        return null;
    }

    /**
     * Clicks the "What is passkey?" link.
     * NOTE: This likely navigates outside the app (to a browser/webview).
     * Handling this requires context switching, which is not implemented here.
     */
    public void clickWhatIsPasskey() {
        System.out.println("Clicking 'What is passkey?' link...");
        click(waitForElementToBeClickable(whatIsPasskeyLink));
    }

    /**
     * Clicks the back button in the toolbar.
     *
     * @return A new instance of ChoosePasskeyPage (adjust if navigation leads elsewhere).
     */
    public ChoosePasskeyPage clickBackButton() {
        System.out.println("Clicking the back button...");
        click(waitForElementToBeClickable(backButton));
        return new ChoosePasskeyPage(driver);
    }
} 