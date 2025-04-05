package com.trustwallet.automation.pages;

import com.trustwallet.automation.base.BasePage;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class PasscodePage extends BasePage {

    // Locators
    private String textUiSelector = "new UiSelector().text(\"%s\")";
    private String digitButtonUiSelector = "new UiSelector().text(\"%s\")";
    private final By skipPopupButton = AppiumBy.androidUIAutomator(
            String.format(textUiSelector, "Skip, I'll do it later"));
    private final By createPasscodeTitle = AppiumBy.androidUIAutomator(
            String.format(textUiSelector, "Create passcode"));
    private final By confirmPasscodeTitle = AppiumBy.androidUIAutomator(
            String.format(textUiSelector, "Confirm passcode"));

    /**
     * Constructor for PasscodePage.
     *
     * @param driver The AppiumDriver instance.
     */
    public PasscodePage(AppiumDriver driver) {
        super(driver);
    }

    /**
     * Checks if the Passcode page is displayed by checking for either
     * "Create passcode" or "Confirm passcode" text.
     *
     * @return true if either title text is found, false otherwise.
     */
    public boolean isPageDisplayed() {
        try {
            WebElement createTitle = findElementIfVisible(createPasscodeTitle);
            if (createTitle != null) return true;

            WebElement confirmTitle = findElementIfVisible(confirmPasscodeTitle);
            return confirmTitle != null;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    /**
     * Enters the passcode digits by clicking the corresponding number buttons,
     * immediately proceeds to the confirmation screen.
     *
     * @param passcode The 6-digit passcode string.
     * @return The same PasscodePage instance, now on confirmation step.
     */
    public PasscodePage enterPasscode(String passcode) {
        System.out.println("Entering passcode by clicking digits: " + passcode);
        
        try {
            // Verify we're on the Create passcode screen
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.visibilityOfElementLocated(createPasscodeTitle));
            
            // Enter the passcode digits
            for (char digit : passcode.toCharArray()) {
                clickDigit(digit);
            }
            
            // Wait for the confirmation screen to appear without using BasePage methods
            wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            wait.until(ExpectedConditions.visibilityOfElementLocated(confirmPasscodeTitle));
            System.out.println("Confirmation screen detected");
            return this;
        }
        catch (TimeoutException e) {
            System.err.println("Timed out waiting for passcode screen transition: " + e.getMessage());
            // Try to proceed anyway
            return this;
        }
    }

    /**
     * Confirms the passcode digits, handles the "Keep up with market" popup,
     * and navigates to the Choose Passkey page.
     *
     * @param passcode The 6-digit passcode string to confirm.
     * @return A new instance of the ChoosePasskeyPage.
     */
    public ChoosePasskeyPage confirmPasscode(String passcode) {
        System.out.println("Confirming passcode by clicking digits: " + passcode);
        
        // Enter the confirmation passcode directly without additional checks
        for (char digit : passcode.toCharArray()) {
            clickDigit(digit);
        }

        // Handle the popup that appears after successful passcode creation
        System.out.println("Handling 'Keep up with market' popup...");
        try {
            WebDriverWait popupWait = new WebDriverWait(driver, Duration.ofSeconds(10)); 
            WebElement skipButton = popupWait.until(ExpectedConditions.elementToBeClickable(skipPopupButton));
            click(skipButton);
            System.out.println("Clicked 'Skip, I'll do it later' button.");
        } catch (Exception e) {
            System.err.println("Could not find or click the 'Skip, I'll do it later' button. Proceeding anyway... Error: " + e.getMessage());
        }

        return new ChoosePasskeyPage(driver);
    }

    /**
     * Clicks the button corresponding to the given digit using UiSelector.
     *
     * @param digit The digit character ('0' through '9') to click.
     */
    private void clickDigit(char digit) {
        if (Character.isDigit(digit)) {
            try {
                String uiSelector = String.format(digitButtonUiSelector, digit);
                By digitLocator = AppiumBy.androidUIAutomator(uiSelector);
                
                // Wait for the digit button to be clickable with a shorter timeout
                WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(5));
                WebElement digitButton = shortWait.until(ExpectedConditions.elementToBeClickable(digitLocator));
                
                click(digitButton);
                
                // Short delay to prevent input issues
                try { Thread.sleep(50); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
            } catch (Exception e) {
                System.err.println("Error clicking digit " + digit + ": " + e.getMessage());
            }
        } else {
            System.err.println("Invalid character passed to clickDigit: " + digit);
        }
    }

    // Add methods for backspace or fingerprint if needed
} 