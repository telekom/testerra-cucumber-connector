package eu.tsystems.mms.tic.testerra.cucumber.plugin;

import cucumber.api.PickleStepTestStep;
import cucumber.api.event.ConcurrentEventListener;
import cucumber.api.event.EventHandler;
import cucumber.api.event.EventPublisher;
import cucumber.api.event.TestCaseStarted;
import cucumber.api.event.TestStepFinished;
import cucumber.api.event.TestStepStarted;
import eu.tsystems.mms.tic.testframework.report.model.steps.TestStep;
import eu.tsystems.mms.tic.testframework.utils.UITestUtils;
import eu.tsystems.mms.tic.testframework.webdrivermanager.WebDriverManager;
import org.apache.commons.text.StringEscapeUtils;

public class TesterraReportPlugin implements ConcurrentEventListener {

    private EventHandler<TestCaseStarted> testCaseStartedEventHandler = event -> {
        event.testCase.getName();
    };

    private EventHandler<TestStepStarted> testStepStartedEventHandler = event -> {
        if (event.testStep instanceof PickleStepTestStep) {
            PickleStepTestStep step = (PickleStepTestStep) event.testStep;
            TestStep.begin(StringEscapeUtils.escapeHtml4(step.getStepText()));
            UITestUtils.takeScreenshot(WebDriverManager.getWebDriver(), true);
        }
    };

    private EventHandler<TestStepFinished> testStepFinishedEventHandler = event -> {
        if (event.testStep instanceof PickleStepTestStep) {
            UITestUtils.takeScreenshot(WebDriverManager.getWebDriver(), true);
        }
    };

    @Override
    public void setEventPublisher(EventPublisher publisher) {
        publisher.registerHandlerFor(TestStepStarted.class, testStepStartedEventHandler);
        publisher.registerHandlerFor(TestStepFinished.class, testStepFinishedEventHandler);
    }


}
