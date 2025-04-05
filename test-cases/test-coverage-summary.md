# Test Coverage Summary for Trust Wallet's Create Wallet Functionality

## Overview

This document summarizes test coverage for Trust Wallet's "Create Wallet" functionality. It covers the core flows (Secret Phrase, Swift/Passkey), backup procedures, and key validation/negative scenarios across 12 test cases.

## Test Case Distribution

| Testing Category | Number of Test Cases | Percentage |
|-----------------------------|----------------------|------------|
| Secret Phrase Creation/Backup | 4 | 33.3% |
| Swift/Passkey Creation | 3 | 25.0% |
| General Flow & Negative Cases | 5 | 41.7% |
| **Total** | **12** | **100%** |

## Test Coverage Matrix

| Test Case ID | Scenario | Functionality | Security | Usability | Negative/Edge |
|--------------|-----------------------------|---------------|----------|-----------|---------------|
| TC-SP-01 | Secret Phrase (Create) | ✓ | ✓ | ✓ | |
| TC-SP-02 | Secret Phrase (Manual Backup) | ✓ | ✓ | ✓ | |
| TC-SP-03 | Secret Phrase (Google Backup) | ✓ | ✓ | ✓ | |
| TC-SP-04 | Secret Phrase (Backup Fail) | | ✓ | | ✓ |
| TC-SK-01 | Swift/Passkey (Create) | ✓ | ✓ | ✓ | |
| TC-SK-02 | Swift/Passkey (Quiz Fail) | | ✓ | | ✓ |
| TC-SK-03 | Swift/Passkey (Tips Fail) | | ✓ | ✓ | ✓ |
| TC-GEN-01 | General (Passcode Mismatch) | | ✓ | ✓ | ✓ |
| TC-GEN-02 | General (ToS/Privacy Links) | ✓ | | ✓ | |
| TC-GEN-03 | Negative (Cancel Mid-process) | ✓ | | ✓ | ✓ |
| TC-GEN-04 | Edge (Offline Creation) | ✓ | ✓ | | ✓ |
| TC-GEN-05 | Edge (Interruptions) | ✓ | | ✓ | ✓ |

## Key Testing Areas Covered

1.  **Core Creation Flows**: Secret Phrase and Swift/Passkey happy paths including prompts.
2.  **Backup Mechanisms**: Manual and Google Drive backup for Secret Phrase, including verification and failure.
3.  **Security Gates**: Passcode setup/mismatch, Biometrics prompt, Swift Safety Tips & Quiz validation, Backup phrase/password security.
4.  **Error Handling**: Incorrect backup verification, incorrect quiz answers, failure to acknowledge tips.
5.  **Basic Usability**: Accessing ToS/Policy, handling cancellation & interruptions.
6.  **Edge Conditions**: Offline behavior for both creation methods.

## Test Prioritization

### High Priority:
- TC-SP-01: Create Secret Phrase Wallet & Handle Initial Prompts
- TC-SP-02: Perform Manual Secret Phrase Backup & Verification
- TC-SK-01: Create Swift/Passkey Wallet & Handle Prompts/Quiz
- TC-GEN-01: Fail Passcode Confirmation (Mismatch)

### Medium Priority:
- TC-SP-03: Perform Google Drive Secret Phrase Backup
- TC-SP-04: Fail Manual Backup Verification Attempt
- TC-SK-02: Fail Swift/Passkey Quiz Attempt
- TC-SK-03: Fail Swift/Passkey Safety Tips Acknowledgement
- TC-GEN-03: Cancel Creation Mid-Process Using Back Navigation
- TC-GEN-05: Interrupt Wallet Creation Process (Call, App Switch)

### Lower Priority:
- TC-GEN-02: Access Terms/Privacy Links from Welcome Screen
- TC-GEN-04: Offline Wallet Creation Attempt (Secret Phrase vs Swift)

## Automation Candidates

Candidates focus on core paths and critical validations within the app's control:

1.  **TC-SP-01**: Create Secret Phrase Wallet (core happy path).
2.  **TC-SK-01**: Create Swift/Passkey Wallet (up to system passkey prompt).
3.  **TC-GEN-01**: Passcode Mismatch validation.
4.  **TC-SK-02**: Swift Quiz Incorrect Answer validation.
5.  **TC-SK-03**: Swift Safety Tips Not Acknowledged validation.

**Rationale for Selection:**
-   **High Value & Critical Path:** Covering primary happy paths (TC-SP-01, TC-SK-01) is essential.
-   **Feasibility & Stability:** Prioritizing validation logic (TC-GEN-01, TC-SK-02, TC-SK-03) and flows less dependent on external/OS interactions yields more stable automation.
-   **Regression Potential:** Core paths and validations are prone to regression.
-   **ROI:** Automating these frees up manual effort for complex backup/recovery tests.

## Conclusion

This suite of 12 test cases provides solid coverage for the Create Wallet functionality. It details the distinct flows, security checks, and common failure points clearly, facilitating review and effective testing. 