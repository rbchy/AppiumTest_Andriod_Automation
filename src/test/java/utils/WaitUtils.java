package utils;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class WaitUtils {

    public static WebElement waitForVisible(AndroidDriver driver, By locator, int timeoutSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public static WebElement waitForClickable(AndroidDriver driver, By locator, int timeoutSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    public static WebElement retryFindElement(AndroidDriver driver, By locator, int retries) {
        RuntimeException lastException = null;
        for (int attempt = 1; attempt <= retries; attempt++) {
            try {
                return driver.findElement(locator);
            } catch (NoSuchElementException | StaleElementReferenceException e) {
                lastException = e;
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                }
            }
        }
        throw lastException != null
                ? lastException
                : new NoSuchElementException("Element not found: " + locator);
    }
}
