## Testerrra Cucumber Connector

Module to run Cucumber test cases with Testerra. While the test execution of Cucumber test cases is easily achievable, 
some adjustments need to be made to improve reporting. The `TesterraReportPlugin` is required if the report should
automatically write cucumber steps as test steps to the report. The `TesterraCucumberListener` is needed to replace the
name of a test method with the name of the executed scenario.

It is also possible to run Cucumber tests with Testerra without the usage of the `TesterraReportPlugin` and by using the
basic `TesterraListener` instead of the `TesterraCucumberListener`. The generated report won't contain any information
from the Gherkin scripts though (i.e. feature , scenario and step names).

#### Usage
- currently works with:
    - `io.cucumber:cucumber-java:4.7.1`
    - `io.cucumber:cucumber-testng:4.7.1`
    - `eu.tsystems.mms.tic.testerra:driver-ui-desktop:1-SNAPSHOT`
    - newer Cucumber versions are incompatible with Testerra due to the newer TestNG version used
- setup:
    - add the `TesterraListener` as Listener to you runner class
    - add the `TesterraReportPlugin` as plugin to your CucumberOptions
    - your runner needs to inherit from `AbstractTestNGCucumberTests`
- example: 
    ```java
    import eu.tsystems.mms.tic.testframework.report.TesterraListener;
    import io.cucumber.testng.AbstractTestNGCucumberTests;
    import io.cucumber.testng.CucumberOptions;
    import org.testng.annotations.Listeners;
    
    @Listeners(TesterraListener.class)
    @CucumberOptions(plugin = {"eu.tsystems.mms.tic.testerra.cucumber.plugin.TesterraReportPlugin"},
                              features = "src/test/resources/features/", glue = "steps")
    public class RunTesterraCucumberTest extends AbstractTestNGCucumberTests {
    }
    ```
  
#### Known Issues
- All features are group as one class in the Testerra report.
- Screenshots of failed tests will be taken after Cucumber @after (method,steps). You need to implement a hook to take 
screenshots before any clean up methods run.