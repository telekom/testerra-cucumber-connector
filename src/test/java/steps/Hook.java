package steps;

import eu.tsystems.mms.tic.testframework.report.model.steps.TestStep;
import eu.tsystems.mms.tic.testframework.utils.UITestUtils;
import eu.tsystems.mms.tic.testframework.webdrivermanager.WebDriverManager;
import io.cucumber.core.api.Scenario;
import io.cucumber.java.AfterStep;

public class Hook {

    @AfterStep
    public void endStep(Scenario scenario) {
        if (scenario.isFailed()) {
            UITestUtils.takeScreenshot(WebDriverManager.getWebDriver(), true);
        }
    }
}
