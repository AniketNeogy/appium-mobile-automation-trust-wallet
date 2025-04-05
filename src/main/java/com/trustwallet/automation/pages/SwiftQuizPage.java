package com.trustwallet.automation.pages;

import com.trustwallet.automation.base.BasePage;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.NoSuchElementException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SwiftQuizPage extends BasePage {
    private static final Logger logger = LogManager.getLogger(SwiftQuizPage.class);

    // Locators
    private final By pageTitle = AppiumBy.androidUIAutomator("new UiSelector().text(\"Quick quiz\")");
    private final By backButton = AppiumBy.androidUIAutomator("new UiSelector().content-desc(\"Back\")");
    
    // Quiz question and answers
    private final By quizQuestion = AppiumBy.androidUIAutomator("new UiSelector().text(\"What happens if passkey is deleted?\")");
    private final By incorrectAnswerOption = AppiumBy.androidUIAutomator("new UiSelector().text(\"I can recover my wallet later\")");
    private final By correctAnswerOption = AppiumBy.androidUIAutomator("new UiSelector().text(\"I'll lose access to my wallet and funds\")");
    private final By whatIsPasskeyLink = AppiumBy.androidUIAutomator("new UiSelector().text(\"What is passkey?\")");
    
    // Incorrect answer popup
    private final By wrongAnswerText = AppiumBy.androidUIAutomator("new UiSelector().text(\"Oops, wrong answer\")");
    private final By tryAgainButton = AppiumBy.androidUIAutomator("new UiSelector().text(\"Try again\")");
    
    // Correct answer popup
    private final By correctAnswerText = AppiumBy.androidUIAutomator("new UiSelector().text(\"Correct!\")");
    private final By continueButton = AppiumBy.androidUIAutomator("new UiSelector().text(\"Got it, continue\")");
    private final By learnMoreAboutPasskey = AppiumBy.androidUIAutomator("new UiSelector().text(\"Learn more about passkey\")");
    private final By closeSheetOverlay = AppiumBy.androidUIAutomator("new UiSelector().content-desc(\"Close sheet\")");

    /**
     * Constructor for SwiftQuizPage.
     *
     * @param driver The AppiumDriver instance.
     */
    public SwiftQuizPage(AppiumDriver driver) {
        super(driver);
        logger.debug("SwiftQuizPage initialized");
    }

    /**
     * Checks if the Swift Quiz page is displayed by verifying the page title.
     *
     * @return true if the page is displayed, false otherwise.
     */
    public boolean isPageDisplayed() {
        try {
            WebElement title = findElementIfVisible(pageTitle);
            WebElement question = findElementIfVisible(quizQuestion);
            
            boolean displayed = (title != null || question != null);
            if (displayed) {
                logger.debug("Swift Quiz page is displayed");
            } else {
                logger.debug("Swift Quiz page is not displayed");
            }
            return displayed;
        } catch (NoSuchElementException e) {
            logger.error("Failed to check if Swift Quiz page is displayed", e);
            return false;
        }
    }
    
    /**
     * Gets the current quiz question text.
     *
     * @return The question text.
     */
    public String getQuestionText() {
        logger.debug("Getting quiz question text");
        try {
            return getText(driver.findElement(quizQuestion));
        } catch (Exception e) {
            logger.error("Failed to get quiz question text", e);
            return "";
        }
    }
    
    /**
     * Selects the incorrect answer option ("I can recover my wallet later").
     * This will trigger the "Wrong answer" popup.
     *
     * @return this SwiftQuizPage instance for method chaining.
     */
    public SwiftQuizPage selectIncorrectAnswer() {
        logger.info("Selecting incorrect answer: 'I can recover my wallet later'");
        try {
            WebElement option = waitForElementToBeClickable(incorrectAnswerOption);
            click(option);
            
            // Wait for the wrong answer popup to appear
            waitForElementToBeVisible(wrongAnswerText);
            logger.debug("Wrong answer popup displayed");
            
            return this;
        } catch (Exception e) {
            logger.error("Failed to select incorrect answer", e);
            throw e;
        }
    }
    
    /**
     * Selects the correct answer option ("I'll lose access to my wallet and funds").
     * This will trigger the "Correct!" popup.
     *
     * @return this SwiftQuizPage instance for method chaining.
     */
    public SwiftQuizPage selectCorrectAnswer() {
        logger.info("Selecting correct answer: 'I'll lose access to my wallet and funds'");
        try {
            WebElement option = waitForElementToBeClickable(correctAnswerOption);
            click(option);
            
            // Wait for the correct answer popup to appear
            waitForElementToBeVisible(correctAnswerText);
            logger.debug("Correct answer popup displayed");
            
            return this;
        } catch (Exception e) {
            logger.error("Failed to select correct answer", e);
            throw e;
        }
    }
    
    /**
     * Clicks the "Try again" button on the wrong answer popup to return to the quiz.
     *
     * @return this SwiftQuizPage instance for method chaining.
     */
    public SwiftQuizPage clickTryAgain() {
        logger.info("Clicking 'Try again' button");
        try {
            WebElement button = waitForElementToBeClickable(tryAgainButton);
            click(button);
            return this;
        } catch (Exception e) {
            logger.error("Failed to click 'Try again' button", e);
            throw e;
        }
    }
    
    /**
     * Clicks the "Got it, continue" button on the correct answer popup to proceed.
     *
     * @return A new instance of SetWalletNamePage.
     */
    public SetWalletNamePage clickContinue() {
        logger.info("Clicking 'Got it, continue' button");
        try {
            WebElement button = waitForElementToBeClickable(continueButton);
            click(button);
            return new SetWalletNamePage(driver);
        } catch (Exception e) {
            logger.error("Failed to click 'Got it, continue' button", e);
            throw e;
        }
    }
    
    /**
     * Clicks the "What is passkey?" link to learn more about passkeys.
     *
     * @return this SwiftQuizPage instance for method chaining.
     */
    public SwiftQuizPage clickWhatIsPasskeyLink() {
        logger.info("Clicking 'What is passkey?' link");
        try {
            WebElement link = waitForElementToBeClickable(whatIsPasskeyLink);
            click(link);
            logger.debug("'What is passkey?' link clicked");
            return this;
        } catch (Exception e) {
            logger.error("Failed to click 'What is passkey?' link", e);
            throw e;
        }
    }
    
    /**
     * Clicks the "Learn more about passkey" link in the answer popup.
     *
     * @return this SwiftQuizPage instance for method chaining.
     */
    public SwiftQuizPage clickLearnMoreAboutPasskey() {
        logger.info("Clicking 'Learn more about passkey' link");
        try {
            WebElement link = waitForElementToBeClickable(learnMoreAboutPasskey);
            click(link);
            logger.debug("'Learn more about passkey' link clicked");
            return this;
        } catch (Exception e) {
            logger.error("Failed to click 'Learn more about passkey' link", e);
            throw e;
        }
    }
    
    /**
     * Clicks the back button to return to the previous page.
     *
     * @return A new instance of SwiftSafetyTipsPage.
     */
    public SwiftSafetyTipsPage clickBackButton() {
        logger.info("Clicking back button");
        try {
            WebElement button = waitForElementToBeClickable(backButton);
            click(button);
            return new SwiftSafetyTipsPage(driver);
        } catch (Exception e) {
            logger.error("Failed to click back button", e);
            throw e;
        }
    }
    
    /**
     * Dismisses a popup by clicking the transparent overlay (Close sheet).
     *
     * @return this SwiftQuizPage instance for method chaining.
     */
    public SwiftQuizPage dismissPopup() {
        logger.info("Dismissing popup via overlay");
        try {
            WebElement overlay = waitForElementToBeClickable(closeSheetOverlay);
            click(overlay);
            return this;
        } catch (Exception e) {
            logger.error("Failed to dismiss popup", e);
            throw e;
        }
    }
    
    /**
     * Completes the quiz by selecting the correct answer and continuing.
     *
     * @return A new instance of SetWalletNamePage.
     */
    public SetWalletNamePage completeQuizCorrectly() {
        logger.info("Completing quiz with correct answer");
        selectCorrectAnswer();
        return clickContinue();
    }
} 