package eu.tsystems.mms.tic.testerra.cucumber.plugin;

import eu.tsystems.mms.tic.testframework.common.PropertyManager;
import eu.tsystems.mms.tic.testframework.utils.UITestUtils;
import eu.tsystems.mms.tic.testframework.webdrivermanager.WebDriverManager;
import io.cucumber.core.api.Scenario;
import io.cucumber.java.After;

public class TesterraHooks {

    @After("@gui")
    public void endStep(Scenario scenario) {
        if (scenario.isFailed() && PropertyManager.getBooleanProperty("tt.cucumber.takeAutomaticScreenshot")) {
            UITestUtils.takeScreenshot(WebDriverManager.getWebDriver(), true);
        }
    }
}
