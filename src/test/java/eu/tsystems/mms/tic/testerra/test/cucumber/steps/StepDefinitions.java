package eu.tsystems.mms.tic.testerra.test.cucumber.steps;

import eu.tsystems.mms.tic.testerra.test.cucumber.pages.GoogleSearchPage;
import eu.tsystems.mms.tic.testerra.test.cucumber.pages.GoogleSearchResultPage;
import eu.tsystems.mms.tic.testframework.annotations.Fails;
import eu.tsystems.mms.tic.testframework.logging.Loggable;
import eu.tsystems.mms.tic.testframework.pageobjects.factory.PageFactory;
import eu.tsystems.mms.tic.testframework.webdrivermanager.WebDriverManager;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

public class StepDefinitions implements Loggable {

    @When("the user searches for {string}")
    public void iSearchFor(String searchInput) {
        WebDriver driver = WebDriverManager.getWebDriver();
        GoogleSearchPage googleSearchPage = PageFactory.create(GoogleSearchPage.class, driver);

        googleSearchPage.searchTerm(searchInput);
    }

    @Then("an entry for {string} is shown")
    public void anEntryForIsShown(String resultEntryText) {
        WebDriver driver = WebDriverManager.getWebDriver();
        GoogleSearchResultPage googleSearchResultPage = PageFactory.create(GoogleSearchResultPage.class, driver);
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
