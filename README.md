# 📱 Appium Mobile Automation Framework — ApiDemos

A Java-based mobile test automation framework built with **Appium**, **TestNG**, **Cucumber (BDD)**, and the **Page Object Model**, targeting Google's [ApiDemos](https://github.com/appium/android-apidemos) sample Android application. Reporting is centralized in **Allure**.
![Java](https://img.shields.io/badge/Java-17-orange?logo=java)
![Appium java-client](https://img.shields.io/badge/Appium--java--client-9.2.2-brightgreen?logo=appium)
![Selenium](https://img.shields.io/badge/Selenium-4.19.0-43B02A?logo=selenium)
![TestNG](https://img.shields.io/badge/TestNG-7.10.2-red)
![Maven](https://img.shields.io/badge/Maven-Build-C71A36?logo=apache-maven)
![ExtentReports](https://img.shields.io/badge/ExtentReports-5.x-blue)
![License](https://img.shields.io/badge/License-MIT-lightgrey)

**Author:** Ranajit Baran Chowdhury — Software Programmer & QA Automation Engineer
**Email:** chyranajit@gmail.com
**GitHub:** [github.com/rbchy](https://github.com/rbchy)
**Portfolio:** [rbc6543.wixsite.com/rbc-portfolio](https://rbc6543.wixsite.com/rbc-portfolio)


🌐 **Application Under Test:** [ApiDemos (Appium official test app)](https://github.com/appium/android-apidemos/releases)

## 🛠 Tech Stack

| Layer | Tool |
|---|---|
| Language | Java 17 |
| Mobile Driver | Appium 3.5.2 (UiAutomator2 Driver) |
| Driver client | Appium Java Client 9.5.0 |
| Web Driver Protocol | Selenium 4.32.0 |
| Test Runner | TestNG 7.10.2 |
| BDD | Cucumber 7.22.1 (`cucumber-java` + `cucumber-testng`) |
| Reporting | Allure 2.29.1 (`allure-testng` + `allure-cucumber7-jvm`) |
| API Testing | REST Assured 5.4.0 |
| Build Tool | Maven 3 (Surefire 3.2.5) |
| Device | Android Emulator, `emulator-5554` (tested on API 33) |

---

## 📁 Project Structure

```
AppiumTest_Android_Automation/
├── pom.xml
├── testng.xml                            # ✅ Active suite — "FullRegressionSuite" (6 classes)
├── testng-cucumber.xml                   # ✅ Active suite — Cucumber/BDD runner
├── testng-mydemo-all.xml                 # ❌ Broken — references 12 classes that don't exist (see Known Issues)
├── apks/                                 # ApiDemos-debug.apk goes here (gitignored, download fresh)
└── src/test/java/
    ├── config/
    │   └── DriverFactory.java            # Builds the AndroidDriver (local or SauceLabs) —
    │                                      # shared by BaseTest (TestNG) and Hooks (Cucumber)
    ├── base/
    │   ├── BaseTest.java                 # Session setup/teardown for the TestNG suite
    │   └── MyDemoBaseTest.java           # ⚠️ Orphaned — not used by any test class or suite XML
    ├── hooks/                            # Cucumber lifecycle
    │   ├── Hooks.java                    # @Before/@After — drives DriverFactory per scenario
    │   └── TestContext.java              # ThreadLocal holder sharing the driver with step defs
    ├── stepdefinitions/
    │   └── ApiDemosSteps.java            # Step defs for features/api_demos_navigation.feature
    ├── runners/
    │   └── CucumberTestRunner.java       # AbstractTestNGCucumberTests entry point
    ├── pages/                            # Page Object Model — shared by TestNG AND Cucumber
    │   ├── MainScreenPage.java
    │   ├── AccessibilityPage.java
    │   ├── AnimationPage.java
    │   ├── TextScreenPage.java
    │   ├── PermissionPage.java
    │   └── mydemo/                       # ⚠️ Orphaned — compiled, never referenced by a test
    ├── listeners/
    │   └── ScreenshotListener.java       # On failure: saves a PNG locally + attaches it to Allure
    ├── utils/
    │   └── WaitUtils.java                # Explicit wait + custom retry
    └── tests/
        ├── InstallLifecycleTests.java    # Install / upgrade / uninstall flows
        ├── CombinedApiDemosTests.java    # Core + advanced UI tests (POM, gestures, waits)
        ├── EnterpriseTestSuite.java      # Smoke, lifecycle, negative, security, a11y
        ├── InterruptSimulationTests.java # Call / SMS / network / battery interrupts
        ├── ApiIntegratedTests.java       # Backend API verification (REST Assured)
        ├── E2EJourneyTests.java          # End-to-end multi-step exploration journeys
        └── (ApiDemosTest.java, ApiDemosTestSuite.java, FirstAppiumTest.java — legacy/scratch, not wired into testng.xml)

src/test/resources/
├── allure.properties                     # Points Allure results at target/allure-results
└── features/
    └── api_demos_navigation.feature      # Example Cucumber scenarios
```

---

## ✅ Test Coverage

| Category | Status | Notes |
|---|---|---|
| Smoke | ✅ | App launch, basic navigation (`EnterpriseTestSuite`) |
| Regression | ✅ | Menu counts, text validation, back-nav (`CombinedApiDemosTests`) |
| App Lifecycle | ✅ | Kill/relaunch, background/foreground, rotation |
| Navigation | ✅ | Multi-step back button, rapid menu switching |
| Device Behavior | ✅ | Screen dimensions, platform version, orientation |
| Permissions | ✅ | Grant/deny flow handling (utility-level, `PermissionPage`) |
| Negative Testing | ✅ | Invalid element interaction, empty input handling |
| Data-Driven | ✅ | `@DataProvider`-based multi-screen navigation |
| Performance | ✅ | Scroll response time, element wait thresholds, launch time |
| Security | ✅ | Post-termination state clearing |
| Accessibility | ✅ | Content-desc labels, tap target sizing |
| Install/Upgrade/Uninstall | ✅ | Fresh install → reinstall → uninstall → reinstall cycle |
| Interrupt Simulation | ✅ | Incoming call, SMS, network loss, low battery (emulator-only) |
| API Integration | ✅ | REST Assured — status codes, field validation, negative 404 |
| BDD / Gherkin | ✅ | Cucumber suite covering main-screen load + menu navigation |
| Deep links / notifications / slow-network retry | 🗺️ Not implemented | No test class exists for these yet — see Known Limitations |
| Cross-Device / Parallel execution | ⚠️ Partially ready | `BaseTest`/`DriverFactory` already accept `deviceName`/`platformVersion`/SauceLabs params; no ready-to-run multi-device suite XML exists yet — see Cross-Device section |
| Auth / Cart / Checkout / Payment | 🚫 Not applicable | ApiDemos has no such flows — commented-out templates included in `EnterpriseTestSuite.java` for adapting to a real app |

---

## 📈 Latest Verified Run (2026-07-14, real emulator + Appium 3.5.2, API 33)

**TestNG suite** (`mvn clean test -DsuiteXmlFile=testng.xml`):

| Total | Passed | Failed | Skipped |
|---|---|---|---|
| 58 | 55 | 0 | 3 |

**BUILD SUCCESS.** The 3 skips are graceful, environment-dependent `SkipException`s — not assertion failures — raised when the ApiDemos app doesn't expose an `EditText` sub-screen under its "Text" menu on this API level (`CombinedApiDemosTests.testTextInputFieldAcceptsAndRetainsValue`, `testKeyboardShowsAndHidesOnTextField`, `EnterpriseTestSuite.negative_EmptyInputDoesNotCrashApp`). The framework detects this at runtime and skips with a clear reason rather than reporting a false failure.

**Cucumber suite** (`mvn clean test -DsuiteXmlFile=testng-cucumber.xml`):

| Total | Passed | Failed | Skipped |
|---|---|---|---|
| 4 | 4 | 0 | 0 |

**BUILD SUCCESS.** (1 scenario + a 3-row Scenario Outline: Accessibility / Animation / Graphics.)

---

## 🚀 Getting Started

### Prerequisites

1. **Java 17** (`java -version`) — `JAVA_HOME` must be set
2. **Maven 3** on PATH
3. **Node.js + Appium**:
   ```bash
   npm install -g appium
   appium driver install uiautomator2
   ```
4. **Android SDK** with `adb` on PATH, plus an emulator (Android Studio → Device Manager) running at `emulator-5554`
5. **ApiDemos APK** at `apks/ApiDemos-debug.apk` in the project root — download from [appium/android-apidemos releases](https://github.com/appium/android-apidemos/releases) (e.g. `https://github.com/appium/android-apidemos/releases/download/v6.0.10/ApiDemos-debug.apk`)
6. **Allure commandline** (only to *view* reports, not to run tests): `brew install allure`
7. Eclipse / IntelliJ with the TestNG plugin, if running from an IDE

### Run the full TestNG suite

```bash
# 1. Start Appium server
appium
# 2. Start your emulator and confirm it's visible
adb devices
# 3. Run the master suite
mvn clean test -DsuiteXmlFile=testng.xml
```

From Eclipse/IntelliJ: right-click `testng.xml` itself (not the project or a single class) → **Run As → TestNG Suite** — this ensures the `deviceName`/`platformVersion` parameters defined in the XML reach `BaseTest`.

### Run the Cucumber (BDD) suite

```bash
mvn clean test -DsuiteXmlFile=testng-cucumber.xml
```

`pom.xml` has no default for `suiteXmlFile`, so the `-D` flag is required either way — plain `mvn clean test` will fail.

### Run a single test class

Right-click the class → **Run As → TestNG Test** (only works for classes without required XML parameters, e.g. `InstallLifecycleTests`, `ApiIntegratedTests`).

---

## 📊 Reports

| Artifact | Location |
|---|---|
| Allure results (raw) | `target/allure-results/` |
| Allure report (viewable) | `allure serve target/allure-results` (or `allure generate target/allure-results -o target/allure-report --clean`) |
| Failure screenshots | `test-output/screenshots/<testMethodName>.png` **and** attached inline in the Allure report |
| Surefire raw output | `target/surefire-reports/` |

`ScreenshotListener` (TestNG) and `Hooks.tearDown` (Cucumber) both capture a screenshot on failure and attach it to Allure automatically — no manual report-logging calls in test code.

---

## Active Suite: `testng.xml` ("FullRegressionSuite")

| Class | What it covers |
|---|---|
| `InstallLifecycleTests` | Fresh install, reinstall over existing app, uninstall, reinstall-after-uninstall |
| `CombinedApiDemosTests` | Menu navigation, back button, data-driven screen navigation, soft assertions, keyboard behavior, orientation, app reset, retry/wait utilities |
| `EnterpriseTestSuite` | Tagged by category: `smoke_`, `lifecycle_`, `navigation_`, `device_`, `permissions_`, `negative_`, `performance_`, `security_`, `accessibility_` (plus commented `auth_`/`businessFlow_` templates) |
| `InterruptSimulationTests` | Incoming call, incoming SMS, network loss, low battery |
| `ApiIntegratedTests` | REST Assured checks against a demo API (independent of the emulator) |
| `E2EJourneyTests` | Full exploration journeys, including a rotation variant and a stress-journey variant |

Note: `testng.xml` passes `platformVersion="17"` as a suite parameter to several `<test>` blocks. This has **no effect on local runs** — `BaseTest.setupLocal()` never calls `options.setPlatformVersion(...)`; that only happens in the SauceLabs cloud path. Safe to ignore locally, worth removing eventually (Android has no "version 17" — the number looks copy-pasted from the Java version).

---

## Cucumber (BDD) Suite: `testng-cucumber.xml`

A second, independent way to write tests — the TestNG suite above is untouched and still runs exactly as before.

- **Runner:** `runners.CucumberTestRunner` (`AbstractTestNGCucumberTests`), wired into `testng-cucumber.xml`.
- **Features:** `src/test/resources/features/*.feature` — currently one example, `api_demos_navigation.feature`, covering main-screen load + menu navigation.
- **Step defs:** `stepdefinitions/ApiDemosSteps.java` — reuses `pages.MainScreenPage`, the same page object the TestNG suite uses.
- **Driver lifecycle:** `hooks/Hooks.java` starts/stops the Appium session per scenario via `config.DriverFactory` (the same factory `BaseTest` uses), sharing state with step defs through `hooks/TestContext` (a `ThreadLocal` — no DI framework needed at this scale).
- **Reporting:** the `AllureCucumber7Jvm` plugin (declared in `@CucumberOptions`) writes results straight to Allure; failure screenshots go through `scenario.attach(...)` in `Hooks.tearDown`, which Allure picks up automatically.

To add a new scenario: write Gherkin in a new/existing `.feature` file, then add matching `@Given/@When/@Then` methods to a step-definition class (new classes need to be added to `glue` in `CucumberTestRunner`).

---

## 🌐 Cross-Device / Cross-OS Testing

`BaseTest` and `DriverFactory` already accept `deviceName`/`platformVersion` as parameters, and `DriverFactory.createSauceLabsDriver(...)` is a working alternate path for cloud execution — so the groundwork for multi-device runs is in place. What's **not** built yet:

- A ready-to-run local multi-emulator suite XML (e.g. a second `<test>` block with `parallel="tests"` pointing at a second emulator on a different API level).
- A committed SauceLabs suite file — `testng-saucelabs.xml` and `testng-saucelabs-crossdevice.xml` exist as gitignored templates (see [SauceLabs Cloud](#saucelabs-cloud-optional) below) since they'd otherwise carry credentials.

Both are straightforward to add following the existing `BaseTest`/`DriverFactory` pattern, but are flagged here rather than claimed as done.

---

## 🎓 Key Engineering Practices Demonstrated

- **Page Object Model** for maintainable, reusable locators — shared between the TestNG suite and the Cucumber suite.
- **Shared driver factory** (`config/DriverFactory`) — Appium capability setup lives in one place instead of being duplicated between TestNG's `BaseTest` and Cucumber's `Hooks`.
- **Test isolation** via `ensureOnMainScreen()` — prevents one failing test from cascading into the next.
- **State-based assertions** over brittle literal-text matching — navigation verified by screen-state change, not hardcoded text.
- **Self-healing element discovery** for UI elements whose exact labels vary across APK builds, with scroll-based fallback search.
- **Soft vs. hard assertions** — multi-check validation without short-circuiting on first failure.
- **Custom retry & explicit-wait utilities** (`WaitUtils`) — replacing flaky `Thread.sleep()` calls.
- **Graceful skip handling** for environment-dependent scenarios (`SkipException` instead of false failures).
- **Maven `dependencyManagement` vs `dependencies`** used correctly to centralize version control.
- **Unified Allure reporting** across both TestNG and Cucumber, with automatic screenshot-on-failure attachment — one report format regardless of how the test was written.

### Other design decisions

**Fast app reset via ADB** — instead of `fullReset` (slow APK reinstall), tests favor targeted ADB commands (e.g. `adb -s emulator-5554 shell pm clear <package>`) for a clean state with much faster session startup.

**Avoid `driver.currentActivity()`** — it internally calls `adb dumpsys window displays`, which can hang 20+ seconds on a loaded emulator. Session/element checks use `driver.findElements()` instead — a real Appium round-trip with no ADB dependency.

---

## 📌 Known Limitations

- **ApiDemos has no login, cart, checkout, or push-notification-badge flows.** Templates for these are commented out in `EnterpriseTestSuite.java`, ready to adapt for a real production app.
- **The "Text" menu's EditText sub-screen isn't 100% consistent** across ApiDemos APK builds/API levels; the 3 affected tests use a scroll-based search with a graceful `SkipException` fallback rather than a hard failure when the exact screen can't be found.
- **Interrupt simulation commands** (`adb emu call/sms/power`) work only on the Android Emulator, not physical devices.
- **Deep links, app-generated notifications, and slow-network/retry scenarios are not yet covered** — no test class exists for these (unlike some earlier drafts of this README implied).
- **Cross-device/parallel execution** has the parameter plumbing in place (see above) but no ready-to-run suite file yet.

---

## Known Issues / Doc-vs-Code Drift

This section is kept as an audit trail — earlier drafts of this README described things that turned out not to match the actual code. Resolved items are marked ✅; still-open ones aren't.

| Issue | Detail |
|---|---|
| **`testng-mydemo-all.xml` won't run** | References 12 `MyDemo*Tests` classes that **don't exist** under `src/test/java/tests`. Running this suite file will fail to find the classes. |
| **Orphaned MyDemo infrastructure** | `base/MyDemoBaseTest.java` and `pages/mydemo/*` compile cleanly but are never referenced by any test class or suite XML — leftover scaffolding for a never-finished MyDemo suite. |
| **Stale "Run History" numbers** | An earlier README claimed a MyDemo suite run history (72 pass/0 fail/3 skip) with no corresponding report or source in this project. Removed. |
| ✅ **Fixed — hardcoded Windows APK paths** | Multiple classes pointed at `C:\Users\rezau\ApiDemos-debug.apk`, which can't exist on macOS. All now use the relative path `apks/ApiDemos-debug.apk`. |
| ✅ **Fixed — ExtentReports removed, replaced with Allure** | `utils/ExtentReportManager.java` deleted; `extentreports` dependency removed. All classes that referenced it now use `Allure.step(...)` instead of `test.log(Status.X, ...)`. |
| ✅ **Added — Cucumber BDD suite** | New, independent suite (`testng-cucumber.xml` → `runners.CucumberTestRunner`) alongside the TestNG suite. |
| ✅ **Refactored — shared driver setup** | Appium capability/session logic extracted into `config/DriverFactory`, used by both `BaseTest` and `Hooks`. |

**Recommendation:** either implement the 12 missing MyDemo test classes (using the existing `MyDemoBaseTest` + page objects) and fix `testng-mydemo-all.xml`, or delete `testng-mydemo-all.xml`, `MyDemoBaseTest.java`, and `pages/mydemo/*` if that suite is no longer planned.

---

## SauceLabs Cloud (Optional)

Suite files for SauceLabs are **excluded from Git** via `.gitignore` because they contain credentials:

```
testng-saucelabs.xml
testng-saucelabs-crossdevice.xml
testng-saucelabs-mydemo.xml
testng-mydemo-local.xml
```

To run on SauceLabs, create your own copy based on `testng.xml` and add your credentials as parameters. **Never commit credential files.**

Credentials can also be passed as environment variables:

```bash
export SAUCE_USERNAME=your-username
export SAUCE_ACCESS_KEY=your-key
```

---

## 🤝 Contributing / Adapting to Your Own App

This framework's architecture (`config/` + `base/` + `pages/` + `utils/` + `tests/`, with `hooks/`/`stepdefinitions/`/`runners/` for the Cucumber side) is intentionally app-agnostic. To point it at a real application:

1. Replace `appPackage` / `appActivity` / APK path in `config/DriverFactory.java` (used by both TestNG and Cucumber).
2. Add new Page Object classes under `pages/` for your app's screens.
3. Uncomment and implement the `auth_*` / `businessFlow_*` templates in `EnterpriseTestSuite.java`.
4. Add corresponding `.feature` files and step definitions if you want BDD coverage for the same flows.

---

## 📞 Contact

**Ranajit Baran Chowdhury** QA Automation Engineer/SDET
Email: chyranajit@gmail.com  Phone: +1 267-342-5565 LinkedIn:(https://www.linkedin.com/in/rbchy/)GitHub:(https://github.com/rbchy)

---

## 📄 License

This project is intended as a learning/portfolio reference implementation. Feel free to fork and adapt.
