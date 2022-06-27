package eu.tsystems.mms.tic.testerra.test.cucumber.pages;

import eu.tsystems.mms.tic.testframework.pageobjects.Page;
import eu.tsystems.mms.tic.testframework.pageobjects.UiElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class GoogleSearchPage extends Page {

    protected UiElement inputSearch = find(By.xpath("//input[@title='Suche']"));

    public GoogleSearchPage(WebDriver driver) {
        super(driver);
        this.acceptTerms();
    }

    private void acceptTerms() {
        UiElement acceptButton = find(By.xpath("//button/div[contains(text(), 'Ich stimme zu') or contains(text(), 'Alle akzeptieren')]"));
        if (acceptButton.waitFor().displayed(true)) {
            acceptButton.click();
        }
    }

    public GoogleSearchResultPage searchTerm(String term) {
        inputSearch.sendKeys(term);
        inputSearch.submit();
        return createPage(GoogleSearchResultPage.class);
    }
}
