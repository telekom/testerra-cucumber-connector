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

package eu.tsystems.mms.tic.testerra.plugins.cucumber.hooks;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import eu.tsystems.mms.tic.testerra.plugins.cucumber.CucumberTestNGContextGenerator;
import eu.tsystems.mms.tic.testframework.report.utils.TestNGContextNameGenerator;

public class CucumberConnectorHook extends AbstractModule{

    @Override
    protected void configure() {
        bind(TestNGContextNameGenerator.class).to(CucumberTestNGContextGenerator.class).in(Scopes.SINGLETON);
    }
}
