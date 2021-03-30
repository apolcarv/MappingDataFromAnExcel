package stepdefinition.login;

import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.actions.Open;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.actors.OnlineCast;
import question.LoginQuestion;
import task.LoginTask;

import java.util.List;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static net.serenitybdd.screenplay.actors.OnStage.*;

public class LoginStep {
    @Before
    public void setUp(){
        OnStage.setTheStage(new OnlineCast());
    }

    @Given("^usuario abre el sitio web$")
    public void usuarioAbreElSitioWeb() {
        theActorCalled("usuario").attemptsTo(Open.url("https://demo.applitools.com/"));

    }

    @When("^Inserta credenciales$")
    public void insertaCredenciales(List<String> data) {
        theActorInTheSpotlight().attemptsTo(LoginTask.LoginDemo(data));

    }

    @Then("^visualiza el home o texto de bienvenidad$")
    public void visualizaElHomeOTextoDeBienvenidad(List<String> data) {
        theActorInTheSpotlight().should(seeThat(LoginQuestion.TextHome(data)));
    System.out.println("SuccessFul");
    }

}
