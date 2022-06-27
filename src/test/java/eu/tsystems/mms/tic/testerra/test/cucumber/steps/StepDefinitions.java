package eu.tsystems.mms.tic.testerra.test.cucumber.steps;

import eu.tsystems.mms.tic.testerra.test.cucumber.pages.GoogleSearchPage;
import eu.tsystems.mms.tic.testerra.test.cucumber.pages.GoogleSearchResultPage;
import eu.tsystems.mms.tic.testframework.annotations.Fails;
import eu.tsystems.mms.tic.testframework.logging.Loggable;
import eu.tsystems.mms.tic.testframework.testing.PageFactoryProvider;
import eu.tsystems.mms.tic.testframework.testing.WebDriverManagerProvider;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

public class StepDefinitions implements WebDriverManagerProvider, PageFactoryProvider, Loggable {

    @When("the user searches for {string}")
    public void iSearchFor(String searchInput) {
        WebDriver driver = WEB_DRIVER_MANAGER.getWebDriver();
        GoogleSearchPage googleSearchPage = PAGE_FACTORY.createPage(GoogleSearchPage.class, driver);

        googleSearchPage.searchTerm(searchInput);
    }

    @Then("an entry for {string} is shown")
    public void anEntryForIsShown(String resultEntryText) {
        WebDriver driver = WEB_DRIVER_MANAGER.getWebDriver();
        GoogleSearchResultPage googleSearchResultPage = PAGE_FACTORY.createPage(GoogleSearchResultPage.class, driver);
        Assert.assertTrue(googleSearchResultPage.containsResult(resultEntryText), "The search result " + resultEntryText + " should be present.");
    }

    @Then("it fails")
    public void itFails() {
        Assert.fail("This step is supposed to fail");
    }

    @When("the user does a step")
    public void theUserDoesAStep() {

    }

    @Fails(description = "This is supposed to fail", ticketString = "TESTID-12345")
    @Then("it fails expectedly")
    public void itFailsExpectedly() {
        Assert.fail("This step is supposed to fail with an expected fail");
    }

    @Fails(description = "This is supposed to work", ticketString = "TESTID-12345")
    @Then("it doesn't fails unexpectedly")
    public void itDoesnTFailsUnexpectedly() {
        Assert.assertTrue(true);
    }
}
