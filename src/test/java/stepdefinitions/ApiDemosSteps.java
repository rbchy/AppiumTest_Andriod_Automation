package stepdefinitions;

import hooks.TestContext;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;
import pages.MainScreenPage;

/**
 * Step definitions for src/test/resources/features/api_demos_navigation.feature.
 * Reuses the existing pages.MainScreenPage page object — same one the TestNG suite uses —
 * so BDD scenarios and plain TestNG tests share the same UI abstraction layer.
 */
public class ApiDemosSteps {

    private MainScreenPage mainPage;

    private MainScreenPage mainPage() {
        if (mainPage == null) {
            mainPage = new MainScreenPage(TestContext.getDriver());
        }
        return mainPage;
    }

    @Given("the ApiDemos app is launched")
    public void the_apidemos_app_is_launched() {
        mainPage().ensureOnMainScreen(TestContext.getDriver());
    }

    @When("I open the {string} menu item")
    public void i_open_the_menu_item(String menuItem) {
        mainPage().clickMenuItemByText(menuItem);
    }

    @When("I navigate back")
    public void i_navigate_back() {
        TestContext.getDriver().navigate().back();
    }

    @Then("the main screen should show {int} menu items")
    public void the_main_screen_should_show_menu_items(int expectedCount) {
        Assert.assertEquals(mainPage().getMenuItemCount(), expectedCount,
                "Main screen menu item count mismatch");
    }

    @Then("the app package should still be {string}")
    public void the_app_package_should_still_be(String expectedPackage) {
        Assert.assertEquals(TestContext.getDriver().getCurrentPackage(), expectedPackage,
                "App navigated away from the expected package");
    }
}
