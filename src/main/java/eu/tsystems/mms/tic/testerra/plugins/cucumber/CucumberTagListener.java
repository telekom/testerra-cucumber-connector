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
import eu.tsystems.mms.tic.testframework.annotations.Fails;
import eu.tsystems.mms.tic.testframework.events.MethodEndEvent;
import eu.tsystems.mms.tic.testframework.logging.Loggable;
import eu.tsystems.mms.tic.testframework.report.model.context.MethodContext;
import io.cucumber.testng.PickleWrapper;

import java.lang.reflect.Method;
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
public class CucumberTagListener implements MethodEndEvent.Listener, Loggable {

    private final FailsHandler failsHandler = new FailsHandler();

    @Subscribe
    @Override
    public void onMethodEnd(MethodEndEvent event) {
        MethodContext methodContext = event.getMethodContext();
        Optional<PickleWrapper> pickle = methodContext.getParameterValues().stream()
                .filter(o -> o instanceof PickleWrapper).map(e -> (PickleWrapper) e)
                .findFirst();

        pickle.ifPresent(pickleWrapper -> {
            List<String> tags = pickle.get().getPickle().getTags();
            tags.forEach(e -> handleTag(e, event));
        });

        Throwable throwable = event.getTestResult().getThrowable();

        if (throwable != null) {
            findFailsAnnotationInStackTrace(throwable).ifPresent(fails -> {
                methodContext.addAnnotation(fails);
                failsHandler.handle(event);
            });
        }

    }

    private void handleTag(String tag, MethodEndEvent event) {
        // Direct cucumber fails annotation on scenario
        if (tag.equals("@Fails")) {
            failsHandler.handle(event);
        }
    }

    private Optional<Fails> findFailsAnnotationInStackTrace(Throwable throwable) {
        while (throwable != null) {
            for (StackTraceElement stackTraceElement : throwable.getStackTrace()) {
                try {
                    Class<?> cls = Class.forName(stackTraceElement.getClassName());
                    for (Method declaredMethod : cls.getDeclaredMethods()) {
                        if (declaredMethod.getName().equals(stackTraceElement.getMethodName())) {
                            if (declaredMethod.isAnnotationPresent(Fails.class)) {
                                return Optional.of(declaredMethod.getAnnotation(Fails.class));
                            }
                        }
                    }
                    ;
                } catch (Exception e) {
                    log().debug("Unable to trace " + Fails.class.getSimpleName() + " annotation");
                }
            }
            throwable = throwable.getCause();
        }
        return Optional.empty();
    }
}
