package runner.login;

import cucumber.api.CucumberOptions;
import cucumber.api.SnippetType;
import exceldata.DataToFeature;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.junit.AfterClass;
import org.junit.runner.RunWith;
import org.testng.annotations.BeforeSuite;
import runner.PersonalizedRunner;

import java.io.IOException;


@CucumberOptions(

        features = "src/test/resources/feature/login/login.feature",
       // tags = {"@LoginSuccessFul"},
        glue = "stepdefinition",
        snippets =  SnippetType.CAMELCASE
)

@RunWith(PersonalizedRunner.class)
public class LoginRunner {
    private LoginRunner() {

    }
    @BeforeSuite
    public static void test() throws IOException, InvalidFormatException {
        DataToFeature.overrideFeatureFiles(
                "src/test/resources/feature/Login/login.feature");
    }
/*
    @AfterClass
    public static void finalTest() throws IOException {
        DataToFeature.overwriteOriginalFeature(
                "src/test/resources/feature/Login/login.feature");
    }
*/

}
