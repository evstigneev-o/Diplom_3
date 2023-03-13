import Utils.UserGenerator;
import api.steps.UserSteps;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pojo.RegisterRq;
import pojo.SignInRq;
import pojo.SuccessSignRs;
import pom.AccountPage;
import pom.LoginPage;
import pom.MainPage;

import static config.Config.BASE_URL;

@Epic("Stellar Burgers")
@Feature("Account")
@DisplayName("Личный кабинет")
public class GoToConstructorTest extends BaseTest {
    MainPage mainPage;
    LoginPage loginPage;
    AccountPage accountPage;
    String accessToken;

    @Before
    public void init() {
        driver.get(BASE_URL);
        mainPage = new MainPage(driver);
        loginPage = new LoginPage(driver);
        accountPage = new AccountPage(driver);
    }

    @After
    public void cleanUp() {
        if (accessToken != null) {
            UserSteps.deleteUser(accessToken);
        }
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Успешный переход в конструктор из аккаунта")
    public void successfullyGoToConstructorFromAccountTest() {
        RegisterRq user = UserGenerator.generateUser();
        SuccessSignRs signUpResponse = UserSteps.createUser(user)
                .extract()
                .as(SuccessSignRs.class);
        mainPage.clickAccountButton();
        loginPage.loginWithCredentials(new SignInRq(user.getEmail(), user.getPassword()));
        mainPage.clickAccountButton();
        accountPage.clickGoToConstructorButton();
        boolean isDisplayed = mainPage.getBurgerConstructorHeader().isDisplayed();
        accessToken = signUpResponse.getAccessToken();
        Assert.assertTrue("Конструктор не открыт", isDisplayed);
    }
}
