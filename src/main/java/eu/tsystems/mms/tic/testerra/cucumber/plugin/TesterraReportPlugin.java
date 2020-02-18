package eu.tsystems.mms.tic.testerra.cucumber.plugin;

import cucumber.api.PickleStepTestStep;
import cucumber.api.event.ConcurrentEventListener;
import cucumber.api.event.EventHandler;
import cucumber.api.event.EventPublisher;
import cucumber.api.event.TestStepStarted;
import eu.tsystems.mms.tic.testframework.report.model.steps.TestStep;
import org.apache.commons.text.StringEscapeUtils;

public class TesterraReportPlugin implements ConcurrentEventListener {

    private EventHandler<TestStepStarted> testStepStartedEventHandler = event -> {
        if (event.testStep instanceof PickleStepTestStep) {
            PickleStepTestStep step = (PickleStepTestStep) event.testStep;
            TestStep.begin(StringEscapeUtils.escapeHtml4(step.getStepText()));
        }
    };

    @Override
    public void setEventPublisher(EventPublisher publisher) {
        publisher.registerHandlerFor(TestStepStarted.class, testStepStartedEventHandler);
    }


}
