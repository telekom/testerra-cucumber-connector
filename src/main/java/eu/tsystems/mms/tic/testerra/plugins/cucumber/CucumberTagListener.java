package eu.tsystems.mms.tic.testerra.plugins.cucumber;

import com.google.common.eventbus.Subscribe;
import eu.tsystems.mms.tic.testerra.plugins.cucumber.handlers.FailsHandler;
import eu.tsystems.mms.tic.testframework.events.MethodEndEvent;
import eu.tsystems.mms.tic.testframework.report.model.context.MethodContext;
import io.cucumber.testng.PickleWrapper;

import java.util.List;
import java.util.Optional;

/**
 * Listener that gets registered by Scenarios with the @Fails tag.
 * <p>
 * Sets the method status to expected failed if the test failed.
 * <p>
 * If the test was successful it Adds an info with the scenario name to the dashboard and adds a priority message to the
 * method context.
 */
public class CucumberTagListener implements MethodEndEvent.Listener {

    @Subscribe
    @Override
    public void onMethodEnd(MethodEndEvent event) {
        MethodContext methodContext = event.getMethodContext();

        Optional pickle = methodContext.parameters.stream().filter(o -> o instanceof PickleWrapper).findFirst();
        if (pickle.isPresent()) {
            List<String> tags = ((PickleWrapper) pickle.get()).getPickle().getTags();
            tags.stream().forEach(e -> handleTag(e, event));
        }
    }

    private void handleTag(String tag, MethodEndEvent event) {
        if (tag.equals("@Fails")) {
            new FailsHandler().handle(event);
        }
    }

}
