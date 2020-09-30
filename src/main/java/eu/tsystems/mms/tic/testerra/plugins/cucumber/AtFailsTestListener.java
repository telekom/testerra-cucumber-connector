package eu.tsystems.mms.tic.testerra.plugins.cucumber;

import com.google.common.eventbus.Subscribe;
import eu.tsystems.mms.tic.testframework.events.MethodEndEvent;
import eu.tsystems.mms.tic.testframework.info.ReportInfo;
import eu.tsystems.mms.tic.testframework.report.TestStatusController;
import eu.tsystems.mms.tic.testframework.report.TesterraListener;
import eu.tsystems.mms.tic.testframework.report.model.context.MethodContext;
import org.testng.ITestResult;

import java.lang.reflect.Method;

/**
 * Listener that gets registered by Scenarios with the @Fails tag.
 * <p>
 * Sets the method status to expected failed if the test failed.
 * <p>
 * If the test was successful it Adds an info with the scenario name to the dashboard and adds a priority message to the
 * method context.
 */
public class AtFailsTestListener implements MethodEndEvent.Listener {

    @Subscribe
    @Override
    public void onMethodEnd(MethodEndEvent event) {
        TesterraListener.getEventBus().unregister(this);
        ITestResult testResult = event.getTestResult();
        MethodContext methodContext = event.getMethodContext();
        Method method = event.getMethod();

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
