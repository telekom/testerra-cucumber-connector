package eu.tsystems.mms.tic.testerra.plugins.cucumber.handlers;

import eu.tsystems.mms.tic.testframework.events.MethodEndEvent;
import eu.tsystems.mms.tic.testframework.info.ReportInfo;
import eu.tsystems.mms.tic.testframework.report.TestStatusController;
import eu.tsystems.mms.tic.testframework.report.model.context.MethodContext;
import org.testng.ITestResult;

import java.lang.reflect.Method;

public class FailsHandler implements TagHandler {
    @Override
    public void handle(MethodEndEvent event) {
        Method method = event.getMethod();
        MethodContext methodContext = event.getMethodContext();
        ITestResult testResult = event.getTestResult();
        if (testResult.isSuccess()) {
            // fails annotation is present but test is passed -> warn
            ReportInfo.getDashboardInfo().addInfo(0, "Repaired >" + methodContext.getName() + "< marked @Fails", "methods/" + methodContext.methodRunIndex + ".html");
            methodContext.addPriorityMessage("@Fails annotation can be removed");
        }

        if (event.isFailed()) {
            TestStatusController.setMethodStatus(methodContext, TestStatusController.Status.FAILED_EXPECTED, method);
        }
    }
}
