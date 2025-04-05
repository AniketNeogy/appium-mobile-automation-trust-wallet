package com.trustwallet.automation.tests;

import com.trustwallet.automation.pages.ChoosePasskeyPage;
import com.trustwallet.automation.pages.PasscodePage;
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
        // Step 1: Launch app and navigate to passcode screen
        WelcomePage welcomePage = new WelcomePage(driver);
        Assert.assertTrue(welcomePage.isPageDisplayed(), "Welcome page is not displayed.");
        logger.info("Welcome page verified");
        
        PasscodePage passcodePage = welcomePage.clickCreateNewWallet();
        Assert.assertTrue(passcodePage.isPageDisplayed(), "Passcode creation page is not displayed.");
        logger.info("Passcode page displayed");
        
        // Step 2: Enter initial passcode
        passcodePage.enterPasscode(initialPasscode);
        logger.info("Initial passcode entered");
        
        // Step 3: Enter different passcode for confirmation (should trigger error)
        passcodePage = passcodePage.confirmWithMismatchedPasscode(mismatchPasscode);
        logger.info("Mismatched passcode entered");
        
        // Step 4: Verify error message is displayed
        Assert.assertTrue(passcodePage.isPasscodeMismatchErrorDisplayed(), 
                          "Passcode mismatch error message is not displayed as expected");
        logger.info("Passcode mismatch error verified");
    }
} 