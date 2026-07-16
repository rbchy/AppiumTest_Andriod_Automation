package hooks;

import config.DriverFactory;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;

/**
 * Appium session lifecycle for the Cucumber suite (runners/CucumberTestRunner.java).
 * Mirrors what base/BaseTest.java does for the plain-TestNG suite, but scoped per
 * scenario instead of per class, and reusing config/DriverFactory so the Appium
 * capability setup isn't duplicated between the two test styles.
 */
public class Hooks {

    @Before
    public void setUp() throws Exception {
        TestContext.setDriver(DriverFactory.createLocalDriver("emulator-5554"));
    }

    @After
    public void tearDown(Scenario scenario) {
        try {
            if (scenario.isFailed() && TestContext.getDriver() != null) {
                byte[] screenshot = TestContext.getDriver().getScreenshotAs(OutputType.BYTES);
                // Cucumber attachments are picked up by the AllureCucumber7Jvm plugin automatically.
                scenario.attach(screenshot, "image/png", scenario.getName() + " — failure screenshot");
            }
        } catch (Exception ignored) {
            // never let screenshot capture itself fail the teardown
        } finally {
            if (TestContext.getDriver() != null) {
                try {
                    TestContext.getDriver().quit();
                } catch (Exception ignored) {
                }
            }
            TestContext.clear();
        }
    }
}
