import eu.tsystems.mms.tic.testframework.report.TesterraListener;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;

@Listeners(TesterraListener.class)
@CucumberOptions(
        plugin = {"eu.tsystems.mms.tic.testerra.plugins.cucumber.TesterraReportPlugin"},
        features = "src/test/resources/features/",
        glue = {
                // This package contains some Testerra Cucumber additionals
                "eu.tsystems.mms.tic.testerra.plugins.cucumber",
                // Add your step definition classes
                "eu.tsystems.mms.tic.testerra.test.cucumber"
        }
)
public class RunTesterraCucumberTest extends AbstractTestNGCucumberTests {

    /**
     * Overwrite the default Test-NG Cucumber dataprovider if you want to run tests parallel.
     *
     * @return
     */
    @Override
    @DataProvider(parallel = true)
    public Object[][] scenarios() {
        return super.scenarios();
    }

}
