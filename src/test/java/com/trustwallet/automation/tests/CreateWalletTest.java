package com.trustwallet.automation.tests;

import com.trustwallet.automation.pages.ChoosePasskeyPage;
import com.trustwallet.automation.pages.PasscodePage;
import com.trustwallet.automation.pages.WalletHomePage;
import com.trustwallet.automation.pages.WelcomePage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CreateWalletTest extends BaseTest {

    @Test(description = "TC-SP-01: Create Secret Phrase Wallet (Happy Path)")
    public void testCreateSecretPhraseWallet() {
        // Arrange
        String expectedDefaultWalletName = "Main Wallet 1";
        String passcode = "123456"; // Example passcode

        // Act & Assert
        WelcomePage welcomePage = new WelcomePage(driver);
        Assert.assertTrue(welcomePage.isPageDisplayed(), "Welcome page is not displayed.");

        PasscodePage passcodePage = welcomePage.clickCreateNewWallet();
        Assert.assertTrue(passcodePage.isPageDisplayed(), "Passcode creation page is not displayed.");

        // Enter and confirm passcode in sequence without redundant check
        passcodePage.enterPasscode(passcode);
        ChoosePasskeyPage choosePasskeyPage = passcodePage.confirmPasscode(passcode);

        Assert.assertTrue(choosePasskeyPage.isPageDisplayed(), "Choose Passkey page is not displayed.");

        WalletHomePage walletHomePage = choosePasskeyPage.clickSecretPhraseCreate();
        Assert.assertTrue(walletHomePage.isPageDisplayed(), "Wallet Home page is not displayed after creation.");

        // Assert default wallet name is correct
        String actualWalletName = walletHomePage.getWalletName();
        Assert.assertEquals(actualWalletName, expectedDefaultWalletName, "Default wallet name is incorrect.");

        // Optional: Add more assertions for home page elements if needed
        Assert.assertTrue(walletHomePage.isWalletEmptyMessageDisplayed(), "'Wallet is empty' message not found.");
    }
} 