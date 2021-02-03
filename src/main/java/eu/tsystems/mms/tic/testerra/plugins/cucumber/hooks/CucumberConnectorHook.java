package eu.tsystems.mms.tic.testerra.plugins.cucumber.hooks;

import eu.tsystems.mms.tic.testerra.plugins.cucumber.CucumberTestNGContextGenerator;
import eu.tsystems.mms.tic.testframework.hooks.ModuleHook;
import eu.tsystems.mms.tic.testframework.report.TesterraListener;

public class CucumberConnectorHook implements ModuleHook {

    @Override
    public void init() {
        TesterraListener.setContextGenerator(new CucumberTestNGContextGenerator());
    }

    @Override
    public void terminate() {

    }
}
