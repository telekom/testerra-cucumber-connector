package steps;

import eu.tsystems.mms.tic.testframework.pageobjects.factory.PageFactory;
import eu.tsystems.mms.tic.testframework.webdrivermanager.WebDriverManager;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import pages.GoogleSearchPage;
import pages.GoogleSearchResultPage;

public class StepDefinitions {

    public static WebDriver driver;

    @Before
    public void initDriver() {
        driver = WebDriverManager.getWebDriver();
    }

    @After
    public void closeDriver() {
        driver.close();
    }

    @When("the user searches for {string}")
    public void iSearchFor(String searchInput) {
        GoogleSearchPage googleSearchPage = PageFactory.create(GoogleSearchPage.class, driver);
        googleSearchPage.searchTerm(searchInput);
    }

    @Then("an entry for {string} is shown")
    public void anEntryForIsShown(String resultEntryText) {
        GoogleSearchResultPage googleSearchResultPage = PageFactory.create(GoogleSearchResultPage.class, driver);
        Assert.assertTrue(googleSearchResultPage.containsResult(resultEntryText), "An entry for \"" + resultEntryText + "\" is shown");
    }
}
