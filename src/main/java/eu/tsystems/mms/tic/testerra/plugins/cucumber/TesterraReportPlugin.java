package eu.tsystems.mms.tic.testerra.plugins.cucumber;

import eu.tsystems.mms.tic.testframework.events.ContextUpdateEvent;
import eu.tsystems.mms.tic.testframework.report.TesterraListener;
import eu.tsystems.mms.tic.testframework.report.model.context.MethodContext;
import eu.tsystems.mms.tic.testframework.report.model.steps.TestStep;
import io.cucumber.plugin.ConcurrentEventListener;
import io.cucumber.plugin.event.EventHandler;
import io.cucumber.plugin.event.EventPublisher;
import io.cucumber.plugin.event.PickleStepTestStep;
import io.cucumber.plugin.event.TestStepStarted;
import org.apache.commons.text.StringEscapeUtils;

public class TesterraReportPlugin implements ConcurrentEventListener {

    private EventHandler<TestStepStarted> testStepStartedEventHandler = event -> {
        if (event.getTestStep() instanceof PickleStepTestStep) {
            PickleStepTestStep step = (PickleStepTestStep) event.getTestStep();
            TestStep.begin(StringEscapeUtils.escapeHtml4(step.getStep().getText()));
        }
    };

    @Override
    public void setEventPublisher(EventPublisher publisher) {
        publisher.registerHandlerFor(TestStepStarted.class, testStepStartedEventHandler);
    }

    static {
        TesterraListener.getEventBus().register((ContextUpdateEvent.Listener) event -> {
            if (event.getContext() instanceof MethodContext) {
                MethodContext context = (MethodContext) event.getContext();
                if (context.methodType.equals(MethodContext.Type.TEST_METHOD)) {
                    context.name = "scenario: " + context.parameters.get(0).toString();
                }
            }
        });
    }

}
