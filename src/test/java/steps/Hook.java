package steps;

import eu.tsystems.mms.tic.testframework.report.model.steps.TestStep;
import eu.tsystems.mms.tic.testframework.utils.UITestUtils;
import io.cucumber.core.api.Scenario;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.BeforeStep;

public class Hook {

    @Before
    public void init(Scenario scenario) {
        TestStep.begin("start scenario " + scenario.getName());
    }

    @BeforeStep
    public void startStep() {
        UITestUtils.takeScreenshot(StepDefinitions.driver, true);
    }

    @AfterStep
    public void endStep(Scenario scenario) {
        if (scenario.isFailed()) {
            UITestUtils.takeScreenshot(StepDefinitions.driver, true);
        }
    }
}
