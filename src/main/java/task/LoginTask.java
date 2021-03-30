package task;

import exceldata.CreateModels;
import models.LoginData;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.screenplay.waits.WaitUntil;

import java.util.List;

import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;
import static ui.LoginUI.*;

public class LoginTask implements Task {
    private LoginData loginData;
    private String data;
    public LoginTask (String data){
        this.data = data;
        int pos=Integer.parseInt(data);
        loginData = CreateModels.setData(pos); }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                WaitUntil.the(EMAIL_INPUT, isVisible()).forNoMoreThan(5).seconds(),
                Enter.theValue(loginData.getEmail()).into(EMAIL_INPUT),
                WaitUntil.the(PASSWORD_INPUT, isVisible()).forNoMoreThan(5).seconds(),
                Enter.theValue(loginData.getPassword()).into(PASSWORD_INPUT),
                WaitUntil.the(START_BTN, isVisible()).forNoMoreThan(5).seconds(),
                Click.on(START_BTN)
        );

    }
    public static LoginTask LoginDemo(List<String> data)  {
        return Tasks.instrumented(LoginTask.class,data.get(1));
    }
}
