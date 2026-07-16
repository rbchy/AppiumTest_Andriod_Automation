package listeners;

import io.appium.java_client.android.AndroidDriver;
import io.qameta.allure.Allure;
import org.openqa.selenium.OutputType;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * On test failure: saves a screenshot under test-output/screenshots/ (for quick local
 * inspection) and attaches the same image to the Allure report.
 *
 * Walks up the class hierarchy to find a `driver` field, since some test classes declare
 * it directly (e.g. InstallLifecycleTests) while others inherit it from BaseTest.
 */
public class ScreenshotListener implements ITestListener {

    @Override
    public void onTestFailure(ITestResult result) {
        try {
            AndroidDriver driver = findDriver(result.getInstance());
            if (driver == null) {
                System.out.println("Could not capture screenshot: no 'driver' field found on " + result.getInstance().getClass().getSimpleName());
                return;
            }

            byte[] screenshot = driver.getScreenshotAs(OutputType.BYTES);

            String folderPath = System.getProperty("user.dir") + "/test-output/screenshots/";
            new File(folderPath).mkdirs();
            String fileName = result.getMethod().getMethodName() + ".png";
            Files.write(Paths.get(folderPath + fileName), screenshot);
            System.out.println("Screenshot saved: " + folderPath + fileName);

            Allure.addAttachment(result.getMethod().getMethodName() + " — failure screenshot",
                    "image/png", new ByteArrayInputStream(screenshot), "png");
        } catch (Exception e) {
            System.out.println("Could not capture screenshot: " + e.getMessage());
        }
    }

    private AndroidDriver findDriver(Object testInstance) {
        Class<?> clazz = testInstance.getClass();
        while (clazz != null) {
            try {
                Field driverField = clazz.getDeclaredField("driver");
                driverField.setAccessible(true);
                Object value = driverField.get(testInstance);
                return value instanceof AndroidDriver ? (AndroidDriver) value : null;
            } catch (NoSuchFieldException e) {
                clazz = clazz.getSuperclass();
            } catch (IllegalAccessException e) {
                return null;
            }
        }
        return null;
    }
}
