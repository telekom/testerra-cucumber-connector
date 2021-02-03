/*
 * Testerra
 *
 * (C) 2021, Mike Reiche, T-Systems Multimedia Solutions GmbH, Deutsche Telekom AG
 *
 * Deutsche Telekom AG and all other contributors /
 * copyright owners license this file to you under the Apache
 * License, Version 2.0 (the "License"); you may not use this
 * file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

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
        Optional<PickleWrapper> pickle = methodContext.getParameterValues().stream().filter(o -> o instanceof PickleWrapper).map(e -> (PickleWrapper) e).findFirst();

        pickle.ifPresent(pickleWrapper -> {
            List<String> tags = pickle.get().getPickle().getTags();
            tags.forEach(e -> handleTag(e, event));
        });
    }

    private void handleTag(String tag, MethodEndEvent event) {
        if (tag.equals("@Fails")) {
            new FailsHandler().handle(event);
        }
    }
//
//    private void updateClassContext(MethodContext methodContext, String classContextName) {
//        Optional<ClassContext> first = methodContext.testContextModel.classContexts.stream().filter(e -> e.name.equals(classContextName)).findFirst();
//        if (first.isPresent()) {
//            methodContext.testContextModel.classContexts.stream().filter(e -> !e.equals(first.get())).forEach(e -> e.methodContexts.remove(methodContext));
//            first.get().methodContexts.add(methodContext);
//        } else {
//            methodContext.testContextModel.classContexts.forEach(e -> e.methodContexts.remove(methodContext));
//            ClassContext classContext = new ClassContext(methodContext.testContextModel, methodContext.executionContext);
//            classContext.name = classContextName;
//            classContext.fullClassName = classContext.name;
//            classContext.simpleClassName = classContext.name;
//            methodContext.classContext = classContext;
//            classContext.swi = methodContext.testContextModel.classContexts.element().swi;
//            classContext.methodContexts.add(methodContext);
//            methodContext.testContextModel.classContexts.add(classContext);
//        }
//        Optional<ClassContext> runTesterraCucumberTest = methodContext.testContextModel.classContexts.stream().filter(e -> e.name.contains("RunTesterraCucumberTest")).findFirst();
//        runTesterraCucumberTest.ifPresent(classContext -> methodContext.testContextModel.classContexts.remove(classContext));
//    }
}
