# Appium Mobile Automation Framework for Trust Wallet

A robust mobile automation framework for testing Trust Wallet mobile applications using Appium, Java, and TestNG.

## Test Documentation

Manual test cases covering the core Create Wallet flows, aiming for a balance between detail and conciseness:

-   [Detailed Test Cases](test-cases/create-wallet-test-cases.md) - 12 test cases covering Secret Phrase, Swift/Passkey, backups, and key negative/edge scenarios with adequate detail.
-   [Test Coverage Summary](test-cases/test-coverage-summary.md) - Overview of coverage, prioritization, and automation candidates for the test suite.

### Automated Test Cases Selected

From the Automation scope defined in `test-coverage-summary.md`, the following tests were chosen to be automated for this exercise for their value and stability on emulators:

1.  **TC-SP-01**: Create Secret Phrase Wallet (core happy path).
2.  **TC-GEN-01**: Passcode Mismatch validation.
3.  **TC-SK-03**: Swift Safety Tips Not Acknowledged validation.
4.  **TC-SK-02**: Swift Quiz Incorrect Answer validation.
5.  **TC-SK-03**: Swift Safety Tips Not Acknowledged validation.

**Rationale for Selection:**
-   **High Value & Critical Path:** Covering the primary Secret Phrase happy path (TC-SP-01) is essential.
-   **Feasibility & Stability:** Prioritizing validation logic (TC-GEN-01, TC-SK-02, TC-SK-03) and flows less dependent on external/OS interactions yields more stable automation.
-   **Regression Potential:** Core paths and validations are prone to regression.
-   **ROI:** Automating these frees up manual effort for potentially unstable flows and complex backup/recovery tests.

## Framework Structure

```
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com/trustwallet/automation
│   │   │       ├── base
│   │   │       │   ├── BaseDriver.java         # Driver initialization and configuration
│   │   │       │   └── BasePage.java           # Common page object methods and waits
│   │   │       ├── pages
│   │   │       │   ├── ChoosePasskeyPage.java  # Choose passkey/secret phrase page
│   │   │       │   ├── ManageWalletsPage.java  # Wallet management page 
│   │   │       │   ├── PasscodePage.java       # Passcode creation/entry page
│   │   │       │   ├── SetWalletNamePage.java  # Wallet naming page
│   │   │       │   ├── SwiftQuizPage.java      # Quiz verification for Swift wallets
│   │   │       │   ├── SwiftSafetyTipsPage.java # Safety tips for Swift wallets
│   │   │       │   ├── WalletHomePage.java     # Main wallet dashboard
│   │   │       │   └── WelcomePage.java        # Initial welcome screen
│   │   │       └── utils
│   │   │           ├── ExtentReportManager.java # HTML report generation
│   │   │           ├── TestListener.java        # TestNG listener for reporting
│   │   │           └── TestUtils.java           # Common test utilities
│   │   └── resources
│   │       └── log4j2.xml                      # Logging configuration
│   └── test
│       ├── java
│       │   └── com/trustwallet/automation
│       │       └── tests
│       │           ├── BaseTest.java           # Test setup and teardown
│       │           └── CreateWalletTest.java   # Wallet creation test cases
│       └── resources
│           └── config.properties               # Test configuration
├── test-cases
│   ├── create-wallet-test-cases.md             # Detailed test cases
│   └── test-coverage-summary.md                # Test coverage overview
├── pom.xml                                     # Maven dependencies
├── testng.xml                                  # TestNG configuration
└── README.md                                   # Project documentation
```

## Framework Improvements

This framework includes the following enhancements to the original sample:

1. **Optimized Locators**: Using simplified resource IDs for better reliability
2. **Improved Wait Strategies**: Enhanced element detection with multiple strategies
3. **Better Error Handling**: More robust error handling in page methods
4. **Code Organization**: Cleaner code structure with consistent documentation
5. **Performance Optimization**: Reduced test execution time
6. **Enhanced Reporting**: Implemented ExtentReports for detailed HTML test reports with screenshots
7. **Logging**: Configured Log4j2 for comprehensive application logging

## APK Handling

**Important**: The Trust Wallet APK exceeds GitHub's file size limit (100MB). To avoid issues when pushing to GitHub:

- The APK files are excluded from version control (added to `.gitignore`)
- You need to download the APK separately (see Setup Instructions below)

## Detailed Setup Guide

### Prerequisites

1. **Java Development Kit (JDK) 11**
   - Download and install from [Oracle JDK](https://www.oracle.com/java/technologies/javase/jdk11-archive-downloads.html) or use OpenJDK
   - Set JAVA_HOME environment variable:
     - Windows: Open System Properties > Advanced > Environment Variables > New System Variable
       - Variable name: `JAVA_HOME`
       - Variable value: `C:\Program Files\Java\jdk-11` (or your JDK installation path)
     - Add `%JAVA_HOME%\bin` to the Path variable

2. **Maven**
   - Download from [Maven website](https://maven.apache.org/download.cgi)
   - Extract to a directory (e.g., `C:\Program Files\Maven`)
   - Set environment variables:
     - Variable name: `M2_HOME`
     - Variable value: `C:\Program Files\Maven` (or your Maven installation path)
     - Add `%M2_HOME%\bin` to the Path variable

3. **Android Studio**
   - Download and install from [Android Developer website](https://developer.android.com/studio)
   - During installation, ensure you install:
     - Android SDK
     - Android SDK Platform
     - Android Virtual Device (AVD)
   - After installation, open Android Studio > Tools > SDK Manager and install:
     - Latest Android SDK Build-Tools
     - Android SDK Command-line Tools
     - Android SDK Platform-Tools
     - Android Emulator

4. **Configure Android Environment Variables**
   - Set ANDROID_HOME environment variable:
     - Variable name: `ANDROID_HOME`
     - Variable value: `C:\Users\<your-username>\AppData\Local\Android\Sdk` (or your SDK installation path)
   - Add these directories to the Path variable:
     - `%ANDROID_HOME%\tools`
     - `%ANDROID_HOME%\tools\bin`
     - `%ANDROID_HOME%\platform-tools`
     - `%ANDROID_HOME%\emulator`

5. **Create Android Virtual Device (Emulator)**
   - Open Android Studio > Device Manager
   - Click "Create Virtual Device"
   - Select a phone (e.g., Pixel 8)
   - Download a system image (e.g., Android 15)
   - Name your device and finish the setup

6. **Appium Setup**
   - Install Node.js and npm from [Node.js website](https://nodejs.org/)
   - Install Appium Server:
     ```
     npm install -g appium
     ```
   - Verify installation:
     ```
     appium --version
     ```
   - Install Appium Desktop from [GitHub releases](https://github.com/appium/appium-desktop/releases)
   - Install Appium Inspector from [GitHub releases](https://github.com/appium/appium-inspector/releases)
   - Install UIAutomator2 Driver:
     ```
     npm install -g appium-uiautomator2-driver
     ```

### Project Setup

1. **Download the Trust Wallet APK**
   ```bash
   # Create the apk directory if it doesn't exist
   mkdir -p apk
   
   # Download the Trust Wallet APK from the official website
   # Visit: https://trustwallet.com/
   ```
   Once downloaded, place the APK in the `apk/` directory with the filename `trust-wallet-latest.apk`.

2. **Install Dependencies**
   ```
   mvn clean install
   ```

3. **Configure the Application Properties**
   - Update `src/test/resources/config.properties` with your device details:
     ```
     appium.url=http://127.0.0.1:5/wd/hub
     platform.name=Android
     device.name=emulator-5554
     app.path=./apk/trust-wallet-latest.apk
     ```

### Running Tests

1. **Start the Android Emulator**
   - Using Android Studio:
     - Open Android Studio > Device Manager
     - Click the play button next to your virtual device
   - Using Command Line:
     ```
     emulator -avd <your_avd_name> -no-snapshot-load
     ```
   - Verify device is connected:
     ```
     adb devices
     ```

2. **Start Appium Server**
   - Using Command Line:
     ```
     appium -p 4725 -a 127.0.0.1 --base-path /wd/hub
     ```
   - Or using Appium Desktop:
     - Open Appium Desktop
     - Set host to 0.0.0.0 and port to 4725
     - Click "Start Server"

3. **Run Tests**
   - Run all tests:
     ```
     mvn clean test
     ```
   - Run specific test class:
     ```
     mvn clean test -Dtest=CreateWalletTest -DplatformName=Android
     ```
   - Run specific test method:
     ```
     mvn clean test -Dtest=CreateWalletTest#testCreateSecretPhraseWallet -DplatformName=Android
     ```
   - Run with specific platform (Android/iOS):
     ```
     mvn clean test -DplatformName=Android
     ```
   - Run tests with custom TestNG XML:
     ```
     mvn clean test -DsuiteXmlFile=testng.xml
     ```

   > **Note:** When running specific tests with `-Dtest=`, you must always include `-DplatformName=Android` parameter, as it's required by the BaseTest setup method but not automatically passed when bypassing the TestNG XML configuration.

### Appium Inspector Configuration

When using Appium Inspector to identify elements:

1. Set Remote Host: `127.0.0.1`
2. Set Port: `4725`
3. Set Path: `/wd/hub`
4. Configure Capabilities:
   ```json
   {
     "appium:automationName": "UiAutomator2",
     "appium:platformName": "Android",
     "appium:platformVersion": "13",
     "appium:deviceName": "emulator-5554",
     "appium:app": "<absolute_path_to_your_apk>"
   }
   ```
5. Click "Start Session"

## Reporting

The framework features an advanced reporting system using ExtentReports:

- **HTML Reports**: Detailed interactive HTML reports with test status, execution time, and environment info
- **Failure Screenshots**: Automatic capture of screenshots on test failures
- **Visual Test Status**: Color-coded test results (green for pass, red for fail)
- **Error Details**: Full stack traces and error messages for failed tests
- **Test Logs**: Chronological logs of test actions and verifications

### Viewing Reports

After test execution, you can find test reports in:

- **Test Reports**:
  - HTML Report: `test-output/ExtentReport.html` (Open in any web browser)
  - TestNG Reports: `target/surefire-reports/` (TestNG HTML reports)
- **Screenshots**:
  - Failure Screenshots: `screenshots/` (Named with test name and timestamp)
  
To view the ExtentReport:
```bash
# Windows
start test-output\ExtentReport.html

# Mac/Linux
open test-output/ExtentReport.html
```

The report includes:
- Dashboard with test summary statistics
- Detailed test case information with passed/failed steps
- Screenshots for failed tests
- Environment information
- System logs with timestamps

### Report Samples

Below are screenshots showing how the Extent Reports look:

<div align="center">
    <p><strong>Dashboard View</strong></p>
    <img src="artefacts/screencapture-ExtentReport-html-2025-04-06-02_44_54.png" width="700px" alt="Dashboard View" />
</div>

<div align="center">
    <p><strong>Test Details View</strong></p>
    <img src="artefacts/screencapture-ExtentReport-html-2025-04-06-02_45_02.png" width="700px" alt="Test Details View" />
</div>