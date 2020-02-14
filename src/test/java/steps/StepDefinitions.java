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

    @After
    public void closeDriver() {
        WebDriverManager.getWebDriver().close();
    }

    @When("the user searches for {string}")
    public void iSearchFor(String searchInput) {
        GoogleSearchPage googleSearchPage = PageFactory.create(GoogleSearchPage.class, WebDriverManager.getWebDriver());
        googleSearchPage.searchTerm(searchInput);
    }

    @Then("an entry for {string} is shown")
    public void anEntryForIsShown(String resultEntryText) {
        GoogleSearchResultPage googleSearchResultPage = PageFactory.create(GoogleSearchResultPage.class, WebDriverManager.getWebDriver());
        Assert.assertTrue(googleSearchResultPage.containsResult(resultEntryText), "An entry for \"" + resultEntryText + "\" is shown");
    }
}
