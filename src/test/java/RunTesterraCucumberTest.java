import eu.tsystems.mms.tic.testframework.report.TesterraListener;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.Listeners;

@Listeners(TesterraListener.class)
@CucumberOptions(
        plugin = {"eu.tsystems.mms.tic.testerra.cucumber.plugin.TesterraReportPlugin"}
        , features = "src/test/resources/features/"
        , glue = {"steps","eu.tsystems.mms.tic.testerra.cucumber.plugin"}
)
public class RunTesterraCucumberTest extends AbstractTestNGCucumberTests {
}
