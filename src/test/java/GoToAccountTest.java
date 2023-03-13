import Utils.UserGenerator;
import api.steps.UserSteps;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
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
public class GoToAccountTest extends BaseTest {
    MainPage mainPage;
    LoginPage loginPage;
    AccountPage accountPage;
    String accessToken;

    @Before
    public void init() {
        driver.get(BASE_URL);
        mainPage = new MainPage(driver);
        accountPage = new AccountPage(driver);
        loginPage = new LoginPage(driver);
    }

    @After
    public void cleanUp() {
        if (accessToken != null){
            UserSteps.deleteUser(accessToken);
        }
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Успешный переход по клику на Личный кабинет")
    public void successfullyGoToAccountWithAccountButtonTest() {
        RegisterRq user = UserGenerator.generateUser();
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        accessToken = UserSteps.createUser(user)
                .extract()
                .as(SuccessSignRs.class)
                .getAccessToken();
        mainPage.clickAccountButton();
        loginPage.loginWithCredentials(new SignInRq(user.getEmail(), user.getPassword()));
        mainPage.clickAccountButton();
        boolean isDisplayed = accountPage.getProfileButton().isDisplayed();
        Assert.assertTrue("Личный кабинет не открылся", isDisplayed);
    }
}
