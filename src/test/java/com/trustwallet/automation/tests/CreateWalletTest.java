package com.trustwallet.automation.tests;

import com.trustwallet.automation.pages.ChoosePasskeyPage;
import com.trustwallet.automation.pages.PasscodePage;
import com.trustwallet.automation.pages.SwiftSafetyTipsPage;
import com.trustwallet.automation.pages.SwiftQuizPage;
import com.trustwallet.automation.pages.SetWalletNamePage;
import com.trustwallet.automation.pages.WalletHomePage;
import com.trustwallet.automation.pages.WelcomePage;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CreateWalletTest extends BaseTest {
    private static final Logger logger = LogManager.getLogger(CreateWalletTest.class);

    @Test(description = "TC-SP-01: Create Secret Phrase Wallet (Happy Path)")
    public void testCreateSecretPhraseWallet() {
        // Arrange
        String expectedDefaultWalletName = "Main Wallet 1";
        String passcode = "123456";
        logger.info("Starting test: Create Secret Phrase Wallet");

        // Act & Assert
        WelcomePage welcomePage = new WelcomePage(driver);
        Assert.assertTrue(welcomePage.isPageDisplayed(), "Welcome page is not displayed.");
        logger.info("Welcome page verified");

        PasscodePage passcodePage = welcomePage.clickCreateNewWallet();
        Assert.assertTrue(passcodePage.isPageDisplayed(), "Passcode creation page is not displayed.");
        logger.info("Passcode page displayed");

        // Enter and confirm passcode in sequence
        passcodePage.enterPasscode(passcode);
        ChoosePasskeyPage choosePasskeyPage = passcodePage.confirmPasscode(passcode);
        logger.info("Passcode entered and confirmed");

        Assert.assertTrue(choosePasskeyPage.isPageDisplayed(), "Choose Passkey page is not displayed.");
        logger.info("Choose Passkey page verified");

        WalletHomePage walletHomePage = choosePasskeyPage.clickSecretPhraseCreate();
        Assert.assertTrue(walletHomePage.isPageDisplayed(), "Wallet Home page is not displayed after creation.");
        logger.info("Wallet Home page verified");

        // Assert default wallet name is correct
        String actualWalletName = walletHomePage.getWalletName();
        Assert.assertEquals(actualWalletName, expectedDefaultWalletName, "Default wallet name is incorrect.");
        logger.info("Wallet name verified: {}", actualWalletName);

        // Optional: Add more assertions for home page elements if needed
        Assert.assertTrue(walletHomePage.isWalletEmptyMessageDisplayed(), "'Wallet is empty' message not found.");
        logger.info("Wallet empty message verified");
    }

    @Test(description = "TC-SP-02: Verify passcode mismatch handling")
    public void testPasscodeMismatchHandling() {
        // Arrange
        String initialPasscode = "123456";
        String mismatchPasscode = "654321"; // Different from initial passcode
        logger.info("Starting test: Verify passcode mismatch handling");
        
        // Act & Assert
        WelcomePage welcomePage = new WelcomePage(driver);
        Assert.assertTrue(welcomePage.isPageDisplayed(), "Welcome page is not displayed.");
        logger.info("Welcome page verified");
        
        PasscodePage passcodePage = welcomePage.clickCreateNewWallet();
        Assert.assertTrue(passcodePage.isPageDisplayed(), "Passcode creation page is not displayed.");
        logger.info("Passcode page displayed");
        
        passcodePage.enterPasscode(initialPasscode);
        logger.info("Initial passcode entered");
        
        //Enter different passcode for confirmation (should trigger error)
        passcodePage = passcodePage.confirmWithMismatchedPasscode(mismatchPasscode);
        logger.info("Mismatched passcode entered");
        
        // Verify error message is displayed
        Assert.assertTrue(passcodePage.isPasscodeMismatchErrorDisplayed(), 
                          "Passcode mismatch error message is not displayed as expected");
        logger.info("Passcode mismatch error verified");
    }
    
    @Test(description = "TC-SK-03: Verify Swift safety tips acknowledged validation")
    public void testSwiftSafetyTipsValidation() {
        // Arrange
        String passcode = "123456";
        logger.info("Starting test: Verify Swift safety tips validation");
        
        // Act & Assert
        WelcomePage welcomePage = new WelcomePage(driver);
        Assert.assertTrue(welcomePage.isPageDisplayed(), "Welcome page is not displayed.");
        logger.info("Welcome page verified");
        
        PasscodePage passcodePage = welcomePage.clickCreateNewWallet();
        Assert.assertTrue(passcodePage.isPageDisplayed(), "Passcode creation page is not displayed.");
        logger.info("Passcode page displayed");
        
        // Enter and confirm passcode
        passcodePage.enterPasscode(passcode);
        ChoosePasskeyPage choosePasskeyPage = passcodePage.confirmPasscode(passcode);
        logger.info("Passcode entered and confirmed");
        
        Assert.assertTrue(choosePasskeyPage.isPageDisplayed(), "Choose Passkey page is not displayed.");
        logger.info("Choose Passkey page verified");
        
        // Choose Swift option
        SwiftSafetyTipsPage safetyTipsPage = choosePasskeyPage.clickSwiftCreate();
        Assert.assertTrue(safetyTipsPage.isPageDisplayed(), "Swift Safety Tips page is not displayed.");
        logger.info("Swift Safety Tips page verified");
        
        // Verify Continue button behavior
        Assert.assertTrue(safetyTipsPage.isContinueButtonEnabled(), 
                        "Continue button should appear enabled by default");
        logger.info("Verified Continue button appears enabled by default");
        
        // Check if button is actually clickable before checking any boxes
        boolean isInitiallyClickable = safetyTipsPage.isContinueButtonClickable();
        logger.info("Continue button is initially clickable: {}", isInitiallyClickable);
        
        // Check all checkboxes
        safetyTipsPage.clickAutoSyncCheckbox();
        logger.info("Clicked auto sync checkbox");
        
        safetyTipsPage.clickRecoveryCheckbox();
        logger.info("Clicked recovery checkbox");
        
        safetyTipsPage.clickImportantWarningCheckbox();
        logger.info("Clicked important warning checkbox");
        
        // Verify continue button is clickable after checking all tips
        Assert.assertTrue(safetyTipsPage.isContinueButtonClickable(), 
                        "Continue button should be clickable after checking all tips");
        logger.info("Verified Continue button is clickable after checking all tips");
        
        // Click Continue to proceed to Swift Quiz page
        SwiftQuizPage quizPage = safetyTipsPage.clickContinue();
        Assert.assertTrue(quizPage.isPageDisplayed(), "Swift Quiz page is not displayed after accepting safety tips");
        logger.info("Successfully navigated to Swift Quiz page after accepting all safety tips");
    }
    
   @Test(description = "TC-SK-02: Verify Swift Quiz Incorrect Answer Validation")
    public void testSwiftQuizIncorrectAnswerValidation() {
        // Arrange
        String passcode = "123456";
        logger.info("Starting test: Verify Swift Quiz incorrect answer validation");
        
        // Act & Assert
        // Launch app and navigate to passcode screen
        WelcomePage welcomePage = new WelcomePage(driver);
        Assert.assertTrue(welcomePage.isPageDisplayed(), "Welcome page is not displayed.");
        logger.info("Welcome page verified");
        
        PasscodePage passcodePage = welcomePage.clickCreateNewWallet();
        Assert.assertTrue(passcodePage.isPageDisplayed(), "Passcode creation page is not displayed.");
        logger.info("Passcode page displayed");
        
        // Enter and confirm passcode
        passcodePage.enterPasscode(passcode);
        ChoosePasskeyPage choosePasskeyPage = passcodePage.confirmPasscode(passcode);
        logger.info("Passcode entered and confirmed");
        
        Assert.assertTrue(choosePasskeyPage.isPageDisplayed(), "Choose Passkey page is not displayed.");
        logger.info("Choose Passkey page verified");
        
        // Choose Swift option and complete safety tips
        SwiftSafetyTipsPage safetyTipsPage = choosePasskeyPage.clickSwiftCreate();
        Assert.assertTrue(safetyTipsPage.isPageDisplayed(), "Swift Safety Tips page is not displayed.");
        logger.info("Swift Safety Tips page verified");
        
        safetyTipsPage.clickAllCheckboxes();
        SwiftQuizPage quizPage = safetyTipsPage.clickContinue();
        Assert.assertTrue(quizPage.isPageDisplayed(), "Swift Quiz page is not displayed");
        logger.info("Swift Quiz page verified");
        
        // Verify the question text
        String questionText = quizPage.getQuestionText();
        Assert.assertEquals(questionText, "What happens if passkey is deleted?", "Quiz question text is incorrect");
        logger.info("Quiz question verified: {}", questionText);
        
        // Select incorrect answer and verify error popup
        quizPage.selectIncorrectAnswer();
        logger.info("Selected incorrect answer");
        
        // Click Try Again button to return to quiz
        quizPage.clickTryAgain();
        Assert.assertTrue(quizPage.isPageDisplayed(), "Quiz page is not displayed after clicking Try Again");
        logger.info("Successfully returned to quiz after trying incorrect answer");
        
        // Now select the correct answer and verify success popup
        quizPage.selectCorrectAnswer();
        logger.info("Selected correct answer");
        
        // Click Continue to proceed to Set Wallet Name page
        SetWalletNamePage setWalletNamePage = quizPage.clickContinue();
        Assert.assertTrue(setWalletNamePage.isPageDisplayed(), "Set Wallet Name page is not displayed");
        logger.info("Successfully navigated to Set Wallet Name page");
    }
    
    @Test(description = "TC-SK-01: Create Swift Wallet (Happy Path)")
    public void testCreateSwiftWallet() {
        // Arrange
        String passcode = "123456";
        String walletName = "My Swift Wallet";
        logger.info("Starting test: Create Swift Wallet (Happy Path)");
        
        // Act & Assert
        // Launch app and navigate to passcode screen
        WelcomePage welcomePage = new WelcomePage(driver);
        Assert.assertTrue(welcomePage.isPageDisplayed(), "Welcome page is not displayed.");
        logger.info("Welcome page verified");
        
        PasscodePage passcodePage = welcomePage.clickCreateNewWallet();
        Assert.assertTrue(passcodePage.isPageDisplayed(), "Passcode creation page is not displayed.");
        logger.info("Passcode page displayed");
        
        // Enter and confirm passcode
        passcodePage.enterPasscode(passcode);
        ChoosePasskeyPage choosePasskeyPage = passcodePage.confirmPasscode(passcode);
        logger.info("Passcode entered and confirmed");
        
        Assert.assertTrue(choosePasskeyPage.isPageDisplayed(), "Choose Passkey page is not displayed.");
        logger.info("Choose Passkey page verified");
        
        // Choose Swift option
        SwiftSafetyTipsPage safetyTipsPage = choosePasskeyPage.clickSwiftCreate();
        Assert.assertTrue(safetyTipsPage.isPageDisplayed(), "Swift Safety Tips page is not displayed.");
        logger.info("Swift Safety Tips page verified");
        
        // Accept all safety tips
        safetyTipsPage.clickAllCheckboxes();
        SwiftQuizPage quizPage = safetyTipsPage.clickContinue();
        Assert.assertTrue(quizPage.isPageDisplayed(), "Swift Quiz page is not displayed");
        logger.info("Swift Quiz page verified");
        
        // Answer the quiz correctly
        quizPage.selectCorrectAnswer();
        SetWalletNamePage setWalletNamePage = quizPage.clickContinue();
        Assert.assertTrue(setWalletNamePage.isPageDisplayed(), "Set Wallet Name page is not displayed");
        logger.info("Set Wallet Name page verified");
        
        // Set wallet name
        String helpText = setWalletNamePage.getHelpText();
        Assert.assertEquals(helpText, "Wallet name should be between 4 to 24 characters", "Help text is incorrect");
        logger.info("Help text verified: {}", helpText);
        
        // Check initial state of Done button (should be disabled with empty input)
        Assert.assertFalse(setWalletNamePage.isDoneButtonEnabled(), "Done button should be disabled initially");
        logger.info("Verified Done button is initially disabled");
        
        // Enter wallet name and verify Done button becomes enabled
        setWalletNamePage.enterWalletName(walletName);
        Assert.assertEquals(setWalletNamePage.getWalletName(), walletName, "Wallet name was not entered correctly");
        Assert.assertTrue(setWalletNamePage.isDoneButtonEnabled(), "Done button should be enabled after entering valid name");
        logger.info("Wallet name entered and Done button enabled");
        
        // Click Done to complete the flow
        WalletHomePage walletHomePage = setWalletNamePage.clickDone();
        Assert.assertTrue(walletHomePage.isPageDisplayed(), "Wallet Home page is not displayed after clicking Done");
        logger.info("Successfully navigated to Wallet Home page after clicking Done");
    }
} 