package config;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;

import java.io.File;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

/**
 * Single place that knows how to build an AndroidDriver (local emulator or SauceLabs cloud).
 * Shared by the classic TestNG {@code base.BaseTest} and the Cucumber {@code hooks.Hooks} class
 * so the Appium capability setup isn't duplicated between the two test styles.
 */
public class DriverFactory {

    private static final String APPIUM_URL = "http://127.0.0.1:4723";
    private static final String APK_PATH = "apks/ApiDemos-debug.apk";
    private static final String APP_PACKAGE = "io.appium.android.apis";
    private static final String APP_ACTIVITY = "io.appium.android.apis.ApiDemos";

    private DriverFactory() {
    }

    public static AndroidDriver createLocalDriver(String deviceName) throws Exception {
        File apkFile = new File(APK_PATH);

        UiAutomator2Options options = new UiAutomator2Options();
        options.setDeviceName(deviceName);
        options.setPlatformName("Android");
        options.setAutomationName("UiAutomator2");
        options.setAppPackage(APP_PACKAGE);
        options.setAppActivity(APP_ACTIVITY);
        options.setNewCommandTimeout(Duration.ofSeconds(120));
        options.setUiautomator2ServerLaunchTimeout(Duration.ofSeconds(60));
        options.setUiautomator2ServerInstallTimeout(Duration.ofSeconds(60));

        if (apkFile.exists()) {
            System.out.println("[DriverFactory] APK found — installing: " + APK_PATH);
            options.setApp(APK_PATH);
            options.setNoReset(false);
        } else {
            System.out.println("[DriverFactory] APK not found — launching already-installed app");
            options.setNoReset(true);
        }

        System.out.println("[DriverFactory] Connecting to Appium — device: " + deviceName);
        AndroidDriver driver = new AndroidDriver(new URL(APPIUM_URL), options);
        System.out.println("[DriverFactory] Driver created successfully");
        Thread.sleep(2000);
        return driver;
    }

    public static AndroidDriver createSauceLabsDriver(String username, String accessKey, String platformVersion) throws Exception {
        if (username == null || username.isEmpty()) username = System.getenv("SAUCE_USERNAME");
        if (accessKey == null || accessKey.isEmpty()) accessKey = System.getenv("SAUCE_ACCESS_KEY");
        if (username == null || username.isEmpty() || accessKey == null || accessKey.isEmpty()) {
            throw new IllegalStateException(
                "[DriverFactory] SauceLabs credentials missing. " +
                "Set saucelabsUser/saucelabsKey in testng-saucelabs.xml " +
                "or export SAUCE_USERNAME / SAUCE_ACCESS_KEY.");
        }

        Map<String, Object> sauceOpts = new HashMap<>();
        sauceOpts.put("username", username);
        sauceOpts.put("accessKey", accessKey);
        sauceOpts.put("deviceOrientation", "PORTRAIT");
        sauceOpts.put("name", "ApiDemos Regression Suite");
        sauceOpts.put("build", "build-" + System.currentTimeMillis());

        UiAutomator2Options options = new UiAutomator2Options();
        options.setPlatformName("Android");
        options.setAutomationName("UiAutomator2");
        options.setDeviceName("Android GoogleAPI Emulator");
        options.setPlatformVersion(platformVersion);
        options.setApp("storage:22cf786b-cedf-4fac-82bd-aa3361162dcf");
        options.setAppPackage(APP_PACKAGE);
        options.setAppActivity(APP_ACTIVITY);
        options.setNewCommandTimeout(Duration.ofSeconds(120));
        options.setCapability("sauce:options", sauceOpts);

        String sauceUrl = "https://ondemand.us-west-1.saucelabs.com:443/wd/hub";
        System.out.println("[DriverFactory] Connecting to SauceLabs — user: " + username);
        AndroidDriver driver;
        try {
            driver = new AndroidDriver(new URL(sauceUrl), options);
            System.out.println("[DriverFactory] SauceLabs session: " + driver.getSessionId());
        } catch (Exception e) {
            System.out.println("[DriverFactory] SauceLabs connection FAILED: " + e.getMessage());
            throw e;
        }
        Thread.sleep(3000);
        return driver;
    }
}
