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

**Rationale for Selection:**
-   **High Value & Critical Path:** Covering the primary Secret Phrase happy path (TC-SP-01) is essential.
-   **Feasibility & Stability:** Prioritizing validation logic (TC-GEN-01, TC-SK-03) yields more stable automation. The full Swift creation flow (TC-SK-01) and Swift Quiz validation (TC-SK-02) are currently excluded due to observed difficulties with system-level passkey prompts and quiz interactions on emulators.
-   **Regression Potential:** Core paths and validations are prone to regression.
-   **ROI:** Automating these frees up manual effort for potentially unstable flows and complex backup/recovery tests.

## Framework Structure

```
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com/trustwallet/automation
│   │   │       ├── base
│   │   │       │   ├── BaseDriver.java
│   │   │       │   └── BasePage.java
│   │   │       ├── pages
│   │   │       │   └── LoginPage.java
│   │   │       └── utils
│   │   │           ├── TestListener.java
│   │   │           └── TestUtils.java
│   │   └── resources
│   │       └── log4j2.xml
│   └── test
│       ├── java
│       │   └── com/trustwallet/automation
│       │       └── tests
│       │           └── LoginTest.java
│       └── resources
│           └── config.properties
├── test-cases
│   ├── create-wallet-test-cases.md
│   └── test-coverage-summary.md
├── pom.xml
├── testng.xml
└── README.md
```

## Framework Improvements

This framework includes the following enhancements to the original sample:

1. **Optimized Locators**: Using simplified resource IDs for better reliability
2. **Improved Wait Strategies**: Enhanced element detection with multiple strategies
3. **Better Error Handling**: More robust error handling in page methods
4. **Code Organization**: Cleaner code structure with consistent documentation
5. **Performance Optimization**: Reduced test execution time

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
     appium.url=http://127.0.0.1:4723/wd/hub
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
     appium server -p 4723 -a 127.0.0.1 -pa /wd/hub
     ```
   - Or using Appium Desktop:
     - Open Appium Desktop
     - Set host to 0.0.0.0 and port to 4723
     - Click "Start Server"

3. **Run Tests**
   - Run all tests:
     ```
     mvn clean test
     ```
   - Run specific test class:
     ```
     mvn clean test -Dtest=CreateWalletTest
     ```
   - Run with custom TestNG XML:
     ```
     mvn clean test -DsuiteXmlFile=testng.xml
     ```

### Appium Inspector Configuration

When using Appium Inspector to identify elements:

1. Set Remote Host: `127.0.0.1`
2. Set Port: `4723`
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

After test execution, you can find test reports in:
- `target/surefire-reports/`: TestNG HTML reports
- `screenshots/`: Screenshots taken during test failures

## Creating New Tests (For Create Wallet Flow)

1.  Create Page Objects for Create Wallet screens (e.g., `WelcomePage`, `PasscodePage`, `CreateOptionsPage`, `SwiftQuizPage`, `WalletHomePage`).
2.  Create TestNG test classes (e.g., `CreateWalletTest`).
3.  Implement `@Test` methods for automation candidates.
4.  Add new test classes to `testng.xml`.

## Framework Features & Best Practices

-   Page Object Model, Cross-platform base, TestNG, Maven, Log4j2, Screenshot on failure.
-   Separate concerns, use explicit waits, descriptive logging, parameterize configs.

## Troubleshooting

### Common Issues

- **Connection refused to Appium server**: Ensure Appium server is running on the correct port
- **Device not found**: Check that your Android device is connected and detected with `adb devices`
- **APK installation failed**: Make sure the APK is placed in the correct location and is not corrupted
- **File too large for Git**: Trust Wallet APK exceeds GitHub's file size limit - follow the setup instructions to handle this
