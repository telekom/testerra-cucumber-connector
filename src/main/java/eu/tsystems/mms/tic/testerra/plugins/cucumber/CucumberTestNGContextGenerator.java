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

import eu.tsystems.mms.tic.testframework.logging.Loggable;
import eu.tsystems.mms.tic.testframework.report.utils.DefaultTestNGContextGenerator;
import io.cucumber.testng.FeatureWrapper;
import java.util.Arrays;
import java.util.Optional;
import org.testng.ITestResult;

public class CucumberTestNGContextGenerator extends DefaultTestNGContextGenerator implements Loggable {

    @Override
    public String getClassContextName(ITestResult testResult) {
        Optional<FeatureWrapper> feature = Arrays.stream(testResult.getParameters())
                .filter(o -> o instanceof FeatureWrapper)
                .map(o -> (FeatureWrapper) o)
                .findFirst();

        return feature
                .map(featureWrapper -> featureWrapper.toString().substring(1, featureWrapper.toString().length() - 1))
                .orElseGet(() -> super.getClassContextName(testResult));
    }

    @Override
    public String getMethodContextName(ITestResult testResult) {
        if (testResult.getMethod().isTest()) {
            Object[] parameterValues = testResult.getParameters();
            if (parameterValues.length > 0) {
                return "scenario: " + parameterValues[0].toString();
            } else {
                log().error("Could not create scenario name: No scenario parameter found");
            }
        }
        return super.getMethodContextName(testResult);
    }
}
