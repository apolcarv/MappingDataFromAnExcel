package ui;

import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

public class LoginUI {

    public static final Target EMAIL_INPUT = Target.the("user o email")
            .located(By.id("username"));
    public static final Target PASSWORD_INPUT = Target.the("Password")
            .located(By.id("password"));
    public static final Target START_BTN = Target.the("Click Start")
            .located(By.id("log-in"));

    public static final Target TEXTHOME = Target.the("Click Start")
            .located(By.cssSelector("div[class=\"logged-user-name\"]"));
}

