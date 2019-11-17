package demo.cucumber;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(plugin = {"rerun:target/rerun.txt"},
        features = "src/test/resources/features",
        tags = "@All"
)
public class RunnerIT {
}
