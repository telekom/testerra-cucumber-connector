import eu.tsystems.mms.tic.testframework.report.TesterraListener;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.Listeners;

@Listeners(TesterraListener.class)
@CucumberOptions(plugin = {"pretty"}, features = "src/test/resources/features/", glue="steps")
public class RunCucumberTest extends AbstractTestNGCucumberTests {
}
