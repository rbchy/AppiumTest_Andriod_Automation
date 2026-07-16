package runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

/**
 * Entry point for the Cucumber (BDD) suite — separate from, and independent of, the
 * existing plain-TestNG suite in testng.xml. Run via:
 *   mvn clean test -DsuiteXmlFile=testng-cucumber.xml
 */
@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"stepdefinitions", "hooks"},
        plugin = {
                "pretty",
                "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"
        },
        monochrome = true
)
public class CucumberTestRunner extends AbstractTestNGCucumberTests {
}
