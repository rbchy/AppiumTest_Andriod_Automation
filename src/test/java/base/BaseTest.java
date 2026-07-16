package base;

import config.DriverFactory;
import io.appium.java_client.android.AndroidDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

public class BaseTest {

    protected AndroidDriver driver;
    protected String deviceName = "emulator-5554";
    protected String platformVersion = "";
    protected String runMode = "local";

    @BeforeClass
    @Parameters({"runMode", "saucelabsUser", "saucelabsKey", "platformVersion"})
    public void setUp(
            @Optional("local") String runMode,
            @Optional("") String saucelabsUser,
            @Optional("") String saucelabsKey,
            @Optional("12") String platformVersion
    ) throws Exception {
        this.runMode = runMode;
        this.platformVersion = platformVersion;
        if ("saucelabs".equalsIgnoreCase(runMode)) {
            // SauceLabs session slot free না হলে retry করি
            Exception lastEx = null;
            for (int attempt = 1; attempt <= 3; attempt++) {
                try {
                    driver = DriverFactory.createSauceLabsDriver(saucelabsUser, saucelabsKey, platformVersion);
                    lastEx = null;
                    break;
                } catch (Exception e) {
                    lastEx = e;
                    System.out.println("[BaseTest] SauceLabs attempt " + attempt + "/3 failed — retrying in 15s...");
                    try { Thread.sleep(15000); } catch (InterruptedException ignored) {}
                }
            }
            if (lastEx != null) throw lastEx;
        } else {
            driver = DriverFactory.createLocalDriver(this.deviceName);
        }
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            try { driver.quit(); } catch (Exception e) {
                System.out.println("[BaseTest] tearDown quit failed (session may already be dead): " + e.getMessage());
            }
        }
    }
}
