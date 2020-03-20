package eu.tsystems.mms.tic.testerra.plugins.cucumber;

import cucumber.api.PickleStepTestStep;
import cucumber.api.event.ConcurrentEventListener;
import cucumber.api.event.EventHandler;
import cucumber.api.event.EventPublisher;
import cucumber.api.event.TestStepStarted;
import eu.tsystems.mms.tic.testframework.events.ITesterraEventType;
import eu.tsystems.mms.tic.testframework.events.TesterraEventDataType;
import eu.tsystems.mms.tic.testframework.events.TesterraEventService;
import eu.tsystems.mms.tic.testframework.events.TesterraEventType;
import eu.tsystems.mms.tic.testframework.report.model.MethodType;
import eu.tsystems.mms.tic.testframework.report.model.context.MethodContext;
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

    static {
        TesterraEventService.addListener(testerraEvent -> {
            ITesterraEventType iTesterraEventType = testerraEvent.getTesterraEventType();
            if (iTesterraEventType.equals(TesterraEventType.CONTEXT_UPDATE)
                    && testerraEvent.getData().get(TesterraEventDataType.CONTEXT) instanceof MethodContext) {
                MethodContext context = (MethodContext) testerraEvent.getData().get(TesterraEventDataType.CONTEXT);
                if (context.methodType.equals(MethodType.TEST_METHOD)) {
                    context.name = "scenario: " + context.parameters.get(0).toString();
                }
            }
        });
    }
}
