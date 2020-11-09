package eu.tsystems.mms.tic.testerra.plugins.cucumber;

import com.google.common.eventbus.Subscribe;
import eu.tsystems.mms.tic.testerra.plugins.cucumber.handlers.FailsHandler;
import eu.tsystems.mms.tic.testframework.events.MethodEndEvent;
import eu.tsystems.mms.tic.testframework.report.model.context.ClassContext;
import eu.tsystems.mms.tic.testframework.report.model.context.MethodContext;
import io.cucumber.testng.FeatureWrapper;
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

        Optional<PickleWrapper> pickle = methodContext.parameters.stream().filter(o -> o instanceof PickleWrapper).map(e -> (PickleWrapper) e).findFirst();
        Optional<FeatureWrapper> feature = methodContext.parameters.stream().filter(o -> o instanceof FeatureWrapper).map(e -> (FeatureWrapper) e).findFirst();
        if (pickle.isPresent() && feature.isPresent()) {
            updateClassContext(event.getMethodContext(), feature.get().toString().substring(1, feature.get().toString().length() - 1));
            List<String> tags = pickle.get().getPickle().getTags();
            tags.forEach(e -> handleTag(e, event));
        }
    }

    private void handleTag(String tag, MethodEndEvent event) {
        if (tag.equals("@Fails")) {
            new FailsHandler().handle(event);
        }
    }

    private void updateClassContext(MethodContext methodContext, String classContextName) {
        Optional<ClassContext> first = methodContext.testContextModel.classContexts.stream().filter(e -> e.name.equals(classContextName)).findFirst();
        if (first.isPresent()) {
            methodContext.testContextModel.classContexts.stream().filter(e -> !e.equals(first.get())).forEach(e -> e.methodContexts.remove(methodContext));
            first.get().methodContexts.add(methodContext);
        } else {
            methodContext.testContextModel.classContexts.forEach(e -> e.methodContexts.remove(methodContext));
            ClassContext classContext = new ClassContext(methodContext.testContextModel, methodContext.executionContext);
            classContext.name = classContextName;
            classContext.fullClassName = classContext.name;
            classContext.simpleClassName = classContext.name;
            methodContext.classContext = classContext;
            classContext.swi = methodContext.testContextModel.classContexts.element().swi;
            classContext.methodContexts.add(methodContext);
            methodContext.testContextModel.classContexts.add(classContext);
        }
        Optional<ClassContext> runTesterraCucumberTest = methodContext.testContextModel.classContexts.stream().filter(e -> e.name.contains("RunTesterraCucumberTest")).findFirst();
        runTesterraCucumberTest.ifPresent(classContext -> methodContext.testContextModel.classContexts.remove(classContext));
    }
}
