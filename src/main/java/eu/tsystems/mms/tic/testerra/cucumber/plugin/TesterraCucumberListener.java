package eu.tsystems.mms.tic.testerra.cucumber.plugin;

import eu.tsystems.mms.tic.testframework.events.ITesterraEventType;
import eu.tsystems.mms.tic.testframework.events.TesterraEventDataType;
import eu.tsystems.mms.tic.testframework.events.TesterraEventService;
import eu.tsystems.mms.tic.testframework.events.TesterraEventType;
import eu.tsystems.mms.tic.testframework.report.TesterraListener;
import eu.tsystems.mms.tic.testframework.report.model.MethodType;
import eu.tsystems.mms.tic.testframework.report.model.context.MethodContext;

public class TesterraCucumberListener extends TesterraListener {

    static {
        TesterraEventService.addListener(testerraEvent -> {
            ITesterraEventType iTesterraEventType = testerraEvent.getTesterraEventType();
            if (iTesterraEventType instanceof TesterraEventType) {
                TesterraEventType testerraEventType = (TesterraEventType) iTesterraEventType;
                if (testerraEventType.equals(TesterraEventType.CONTEXT_UPDATE)) {
                    if (testerraEvent.getData().get(TesterraEventDataType.CONTEXT) instanceof MethodContext) {
                        MethodContext context = (MethodContext) testerraEvent.getData().get(TesterraEventDataType.CONTEXT);
                        if (context.methodType.equals(MethodType.TEST_METHOD)) {
                            context.name = "scenario: " + context.parameters.get(0).toString();
                        }
                    }
                }
            }
        });
    }
}
