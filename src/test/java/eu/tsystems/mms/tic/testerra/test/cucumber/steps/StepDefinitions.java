package eu.tsystems.mms.tic.testerra.test.cucumber.steps;

import eu.tsystems.mms.tic.testerra.test.cucumber.pages.GoogleSearchPage;
import eu.tsystems.mms.tic.testerra.test.cucumber.pages.GoogleSearchResultPage;
import eu.tsystems.mms.tic.testframework.annotations.Fails;
import eu.tsystems.mms.tic.testframework.pageobjects.factory.PageFactory;
import eu.tsystems.mms.tic.testframework.webdrivermanager.WebDriverManager;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

public class StepDefinitions {

    @When("the user searches for {string}")
    public void iSearchFor(String searchInput) {
        GoogleSearchPage googleSearchPage =
                PageFactory.create(GoogleSearchPage.class, WebDriverManager.getWebDriver());
        googleSearchPage.searchTerm(searchInput);
    }

    @Fails(description = "searching for the wrong value", ticketString = "TESTID-12345")
    @Then("an entry for {string} is shown")
    public void anEntryForIsShown(String resultEntryText) {
        GoogleSearchResultPage googleSearchResultPage = PageFactory.create(GoogleSearchResultPage.class, WebDriverManager.getWebDriver());
        Assert.assertTrue(googleSearchResultPage.containsResult(resultEntryText), "An entry for \"" + resultEntryText + "\" is shown");
    }

    @Then("it fails")
    public void itFails() {
        Assert.fail("This step is supposed to fail");
    }
}
