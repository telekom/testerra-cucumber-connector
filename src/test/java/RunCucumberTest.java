import eu.tsystems.mms.tic.testerra.cucumber.plugin.TesterraCucumberListener;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.Listeners;

@Listeners(TesterraCucumberListener.class)
@CucumberOptions(plugin = {"eu.tsystems.mms.tic.testerra.cucumber.plugin.TesterraReportPlugin"}, features = "src/test/resources/features/", glue = "steps")
public class RunCucumberTest extends AbstractTestNGCucumberTests {
}
