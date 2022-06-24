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
import eu.tsystems.mms.tic.testframework.annotations.Fails;
import eu.tsystems.mms.tic.testframework.events.MethodEndEvent;
import eu.tsystems.mms.tic.testframework.logging.Loggable;
import eu.tsystems.mms.tic.testframework.report.Status;
import eu.tsystems.mms.tic.testframework.report.model.context.MethodContext;
import io.cucumber.testng.PickleWrapper;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Optional;

/**
 * Listener that gets registered by Scenarios with the @Fails tag.
 * <p>
 * Sets the method status to expected failed if the test failed.
 */
public class CucumberTagListener implements MethodEndEvent.Listener, Loggable {

    private final Fails emptyFailsAnnotation = new Fails() {
        @Override
        public Class<? extends Annotation> annotationType() {
            return Fails.class;
        }

        @Override
        public int ticketId() {
            return 0;
        }

        @Override
        public String ticketString() {
            return null;
        }

        @Override
        public String description() {
            return null;
        }

        @Override
        public boolean intoReport() {
            return false;
        }

        @Override
        public String[] validFor() {
            return new String[0];
        }
    };

    @Subscribe
    @Override
    public void onMethodEnd(MethodEndEvent event) {
        MethodContext methodContext = event.getMethodContext();
        methodContext.getParameterValues().stream()
                .filter(o -> o instanceof PickleWrapper)
                .map(e -> (PickleWrapper) e)
                .findFirst()
                .ifPresent(pickleWrapper -> {
                    List<String> tags = pickleWrapper.getPickle().getTags();
                    tags.forEach(e -> handleCucumberTag(e, event));
                });

        Throwable throwable = event.getTestResult().getThrowable();

        if (throwable != null) {
            /**
             * Search for {@link Fails} in steps code
             */
            findFailsAnnotationInStackTrace(throwable).ifPresent(fails -> {
                /**
                 * When found, add it to annotations of the method context
                 */
                methodContext.addAnnotation(fails);

                markAsExpectedFailedIfPossible(event);
            });
        }
    }

    private void handleCucumberTag(String tag, MethodEndEvent event) {
        /**
         * When the cucumber @Fails annotation is present on a scenario
         */
        if (tag.equals("@Fails")) {
            /**
             * The cucumber @Fails annotation doesn't provide any more information,
             * thats why we just map it to an empty {@link Fails}
             */
            event.getMethodContext().addAnnotation(emptyFailsAnnotation);
            markAsExpectedFailedIfPossible(event);
        }
    }

    /**
     * Mark the method as {@link Status#FAILED_EXPECTED} when it actually failed
     */
    private void markAsExpectedFailedIfPossible(MethodEndEvent event) {
        if (event.isFailed()) {
            MethodContext methodContext = event.getMethodContext();
            methodContext.setStatus(Status.FAILED_EXPECTED);
        }
    }

    /**
     * This method search recursive for {@link Fails} annotations in the
     * given stack trace of {@link Throwable} and all it's root causes.
     */
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
