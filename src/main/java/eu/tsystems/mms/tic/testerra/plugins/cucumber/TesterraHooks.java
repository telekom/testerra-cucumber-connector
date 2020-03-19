package eu.tsystems.mms.tic.testerra.plugins.cucumber;

import eu.tsystems.mms.tic.testframework.utils.UITestUtils;
import eu.tsystems.mms.tic.testframework.webdrivermanager.WebDriverManager;
import io.cucumber.core.api.Scenario;
import io.cucumber.java.After;

public class TesterraHooks {

    @After
    public void endStep(Scenario scenario) {
        if (scenario.isFailed() && WebDriverManager.hasSessionsActiveInThisThread()) {
            UITestUtils.takeScreenshot(WebDriverManager.getWebDriver(), true);
        }
    }
}
