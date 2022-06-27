package eu.tsystems.mms.tic.testerra.test.cucumber.pages;

import eu.tsystems.mms.tic.testframework.pageobjects.UiElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class GoogleSearchResultPage extends GoogleSearchPage {

    private UiElement resultElements = find(By.cssSelector("#rso .g"));

    public GoogleSearchResultPage(WebDriver driver) {
        super(driver);
    }

    public boolean containsResult(String resultText) {
        for (UiElement uiElement : resultElements.list()) {
            boolean resultFound = uiElement.find(By.cssSelector("a h3")).waitFor().text().matches(resultText).is(true);
            if (resultFound) {
                return true;
            }
        }
        return false;
    }
}
