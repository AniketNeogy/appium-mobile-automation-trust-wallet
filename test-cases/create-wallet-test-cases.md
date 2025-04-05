# Trust Wallet - Create Wallet Functionality Test Cases

## Scenario 1: Create Wallet via Secret Phrase

### TC-SP-01: Create Secret Phrase Wallet & Handle Initial Prompts
**Description:** Verify successful wallet creation via Secret Phrase, including passcode setup and handling initial Biometric/Notification prompts.

**Prerequisites:** Fresh app install.

**Steps:**
1. Launch App -> Tap "Create new wallet".
2. Set a 6-digit passcode -> Confirm the passcode.
3. Handle Biometric prompt (Tap "Confirm" and authenticate, or tap "Deny").
4. Handle Notifications prompt (Tap "Enable" or "Skip, I'll do it later").
5. On "Create new wallet" screen, ensure "Secret phrase" is selected -> Tap "Create".   

**Expected Results:** Wallet ("Main Wallet 1") created successfully. User lands on home screen. No backups are active yet.

### TC-SP-02: Perform Manual Secret Phrase Backup & Verification
**Description:** Verify the complete manual backup flow initiated from wallet settings, including viewing and verifying the phrase.         

**Prerequisites:** Secret Phrase wallet exists (e.g., from TC-SP-01).

**Steps:**
1. Navigate: Settings -> Wallets -> Tap 3 dots next to wallet -> Manual Backup ("Back up now").
2. Acknowledge "Before you do it" prompt -> Acknowledge "This secret phrase..." prompt (check boxes) -> Continue.
3. Authenticate if prompted (Passcode/Biometrics).
4. Note the displayed 12-word phrase -> Tap Continue.
5. On verification screen, select the correct words for the given prompts (e.g., Word #1, #4, #7, #10) -> Tap Confirm.

**Expected Results:** Manual backup status changes to "Active". User must acknowledge warnings. Verification requires selecting specific words correctly.

### TC-SP-03: Perform Google Drive Secret Phrase Backup
**Description:** Verify the Google Drive backup flow initiated from wallet settings.

**Prerequisites:** Secret Phrase wallet exists, Google account signed in on device.

**Steps:**
1. Navigate: Settings -> Wallets -> Tap 3 dots next to wallet -> Google Drive Backup ("Back up now").
2. Authenticate if prompted.
3. Select the desired Google Account.
4. Set and Confirm a password specifically for this cloud backup.

**Expected Results:** Google Drive backup status changes to "Active". Process requires Google account selection and a separate backup password.

### TC-SP-04: Fail Manual Backup Verification Attempt
**Description:** Verify correct error handling when submitting an incorrect verification during manual backup.

**Prerequisites:** User is on the manual backup verification screen (TC-SP-02, Step 5).

**Steps:**
1. Intentionally select one or more incorrect words for the prompts -> Tap Confirm.

**Expected Results:** Clear error message displayed. Backup status does not become Active. User can retry verification.

## Scenario 2: Create Wallet via Swift/Passkey

### TC-SK-01: Create Swift/Passkey Wallet & Handle Prompts/Quiz
**Description:** Verify successful wallet creation via Swift/Passkey, including handling prompts, acknowledging tips, passing the quiz, saving the passkey, and naming the wallet.

**Prerequisites:** Fresh app install, device supports passkeys & has connection.

**Steps:**
1. Launch App -> Tap "Create new wallet".
2. Set & Confirm 6-digit passcode.
3. Handle Biometric & Notifications prompts.
4. Select "Swift (Beta)" -> Tap "Create".
5. On "Safety tips" screen, check all 3 confirmation checkboxes -> Tap Continue.
6. On "Quick quiz", select correct answer ("I'll lose access...") -> Tap "Got it, continue".
7. Follow system prompts to create/save the passkey (requires device auth/Google interaction).
8. On "Set wallet name" screen, enter a valid name -> Tap Done.

**Expected Results:** System passkey creation UI is triggered and completes. Wallet created with the custom name. User lands on home screen.

### TC-SK-02: Fail Swift/Passkey Quiz Attempt
**Description:** Verify behavior when the Swift/Passkey safety quiz is answered incorrectly.

**Prerequisites:** User is on the Swift/Passkey "Quick quiz" screen (TC-SK-01, Step 6).

**Steps:**
1. Select the incorrect answer ("I can recover...").
2. Observe "Oops, wrong answer" message -> Tap "Try again".
3. Select the correct answer -> Tap "Got it, continue".

**Expected Results:** Error message shown explaining consequence. User must answer correctly to proceed. "Try again" works.

### TC-SK-03: Fail Swift/Passkey Safety Tips Acknowledgement
**Description:** Verify user cannot proceed with Swift/Passkey creation without checking all safety tip boxes.

**Prerequisites:** User is on the Swift/Passkey "Safety tips" screen (TC-SK-01, Step 5).

**Steps:**
1. Leave at least one checkbox unchecked -> Attempt to tap Continue.

**Expected Results:** The Continue button should be visibly disabled or inactive. Tapping it has no effect.

## Scenario 3: General Flow & Negative Cases

### TC-GEN-01: Fail Passcode Confirmation (Mismatch)
**Description:** Verify error handling for non-matching passcode entries during initial setup.

**Prerequisites:** During passcode setup (e.g., TC-SP-01 Step 2).

**Steps:**
1. Enter initial 6-digit passcode.
2. Enter a different 6-digit passcode for confirmation.

**Expected Results:** Clear error message shown. User must re-enter confirmation code correctly to proceed.

### TC-GEN-02: Access Terms/Privacy Links from Welcome Screen
**Description:** Verify the Terms of Service and Privacy Policy links on the welcome screen are functional.

**Prerequisites:** App is at the initial welcome screen.

**Steps:**
1. Tap "Terms of Service" link -> Verify document/page loads -> Navigate back.
2. Tap "Privacy Policy" link -> Verify document/page loads.

**Expected Results:** Both links open the respective, readable policy documents/pages.

### TC-GEN-03: Cancel Creation Mid-Process Using Back Navigation
**Description:** Verify that using the back button during wallet creation cancels the flow cleanly.

**Prerequisites:** User is part-way through either creation flow (passcode, options, backup, quiz etc.).

**Steps:**
1. Use the device's back navigation button or any specific 'Back'/'Cancel' UI element.

**Expected Results:** The current creation flow is abandoned. No partial wallet data is saved. App returns gracefully to a previous stable screen (e.g., welcome screen or create options screen).

### TC-GEN-04: Offline Wallet Creation Attempt (Secret Phrase vs Swift)
**Description:** Verify distinct behaviors when attempting wallet creation offline for both methods.

**Prerequisites:** Device is offline (Airplane mode).

**Steps:**
1. Attempt Secret Phrase creation flow (TC-SP-01).
2. Attempt Swift/Passkey creation flow (TC-SK-01).

**Expected Results:** 
- Secret Phrase: Creation completes successfully (keys generated locally).
- Swift/Passkey: Flow fails at the system passkey saving step (Step 7 of TC-SK-01) as it requires connectivity. App likely returns to "Set wallet name" screen with an OS-level error/retry prompt. No app crash.
- Cloud backups (if attempted later) should fail or be disabled.

### TC-GEN-05: Interrupt Wallet Creation Process (Call, App Switch)
**Description:** Verify the app correctly resumes the creation flow after an OS-level interruption.

**Prerequisites:** User is mid-way through either creation flow.

**Steps:**
1. Simulate an interruption (e.g., receive a call, switch to another app).
2. Return focus to the Trust Wallet app.

**Expected Results:** App state is preserved. User can resume the creation process exactly where they left off without any data loss or unexpected behavior. 