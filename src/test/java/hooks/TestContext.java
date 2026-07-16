package hooks;

import io.appium.java_client.android.AndroidDriver;

/**
 * Shares the Appium driver between hooks/Hooks.java and stepdefinitions/*.java for a
 * single scenario. Cucumber instantiates glue classes independently unless a DI
 * framework (e.g. cucumber-picocontainer) is added, so a ThreadLocal holder is the
 * simplest way to pass state between them without pulling in another dependency.
 */
public final class TestContext {

    private static final ThreadLocal<AndroidDriver> DRIVER = new ThreadLocal<>();

    private TestContext() {
    }

    public static void setDriver(AndroidDriver driver) {
        DRIVER.set(driver);
    }

    public static AndroidDriver getDriver() {
        return DRIVER.get();
    }

    public static void clear() {
        DRIVER.remove();
    }
}
