package question;

import exceldata.CreateModels;
import models.LoginData;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.waits.WaitUntil;

import java.util.List;

import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.containsText;
import static ui.LoginUI.TEXTHOME;

public class LoginQuestion implements Question<Boolean> {
    private LoginData loginData;
    private String data;
    public LoginQuestion (String data){
        this.data = data;
        int pos=Integer.parseInt(data);
        loginData = CreateModels.setData(pos); }
    @Override
    public Boolean answeredBy(Actor actor) {
       // actor.attemptsTo(WaitUntil.the(TEXTHOME, containsText()).forNoMoreThan(5).seconds());
        return TEXTHOME.resolveFor(actor).isPresent()
                && (TEXTHOME.resolveFor(actor).getText()).equals(loginData.getTexthome());
    }
    public static LoginQuestion TextHome(List<String> data){
        return new LoginQuestion(data.get(1));
    }
}
