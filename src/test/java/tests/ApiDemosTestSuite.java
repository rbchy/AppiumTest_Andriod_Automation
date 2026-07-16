package tests;

import io.qameta.allure.Allure;

import base.BaseTest;
import pages.AccessibilityPage;
import pages.AnimationPage;
import pages.MainScreenPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class ApiDemosTestSuite extends BaseTest {


    @BeforeMethod
    public void setupReport(java.lang.reflect.Method method) {
    }

    @Test(priority = 1)
    public void testMainScreenHasMenuItems() {
        MainScreenPage mainPage = new MainScreenPage(driver);
        int count = mainPage.getMenuItemCount();

        Allure.step("Total menu items found: " + count);
        Assert.assertTrue(count > 0, "Main screen should have menu items");
        Allure.step("Main screen has menu items");
    }

    @Test(priority = 2)
    public void testAccessibilityMenuIsVisible() {
        MainScreenPage mainPage = new MainScreenPage(driver);
        boolean visible = mainPage.isMenuItemVisible("Accessibility");

        Assert.assertTrue(visible, "Accessibility menu item should be visible");
        Allure.step("Accessibility menu is visible");
    }

    @Test(priority = 3, dependsOnMethods = "testAccessibilityMenuIsVisible")
    public void testNavigateToAccessibilityScreen() {
        MainScreenPage mainPage = new MainScreenPage(driver);
        mainPage.clickMenuItemByText("Accessibility");

        AccessibilityPage accessibilityPage = new AccessibilityPage(driver);
        Assert.assertTrue(accessibilityPage.isAccessibilityScreenDisplayed(),
                "Should navigate to Accessibility screen");

        Allure.step("Sub-menu items: " + accessibilityPage.getSubMenuItemCount());
        Allure.step("Navigated to Accessibility screen successfully");

        accessibilityPage.goBack();
    }

    @Test(priority = 4)
    public void testNavigateToAnimationScreen() {
        MainScreenPage mainPage = new MainScreenPage(driver);
        mainPage.clickMenuItemByText("Animation");

        AnimationPage animationPage = new AnimationPage(driver);
        Assert.assertTrue(animationPage.isAnimationScreenDisplayed(),
                "Should navigate to Animation screen");

        Allure.step("Sub-menu items: " + animationPage.getSubMenuItemCount());
        Allure.step("Navigated to Animation screen successfully");

        animationPage.goBack();
    }
}