import api.steps.UserSteps;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pojo.SuccessSignRs;
import pom.LoginPage;
import pom.MainPage;
import pom.SignUpPage;

import static config.Config.BASE_URL;
@Epic("Stellar Burgers")
@Feature("SignUp")
@DisplayName("Регистрация")
public class SignUpTest extends BaseTest {
    MainPage mainPage;
    LoginPage loginPage;
    SignUpPage signUpPage;
    String accessToken;

    @Before
    public void init() {
        driver.get(BASE_URL);
        mainPage = new MainPage(driver);
    }

    @After
    public void cleanUp() {
        if (accessToken != null) {
            UserSteps.deleteUser(accessToken);
        }
    }

    @Test
    @Severity(SeverityLevel.BLOCKER)
    @DisplayName("Успешная регистрация с валидными данными")
    public void successfullySignUpWithValidDataTest() {
        String name = RandomStringUtils.randomAlphabetic(10);
        String email = RandomStringUtils.randomAlphanumeric(10, 12) + "@yandex.ru";
        String password = RandomStringUtils.randomAlphanumeric(6, 12);
        mainPage.clickSignInButton();
        loginPage = new LoginPage(driver);
        loginPage.clickSignUpButton();
        signUpPage = new SignUpPage(driver);
        signUpPage.signUp(name, email, password);
        boolean displayed = loginPage.getSignInButton().isDisplayed();
        Assert.assertTrue("Регистрация не выполнена", displayed);
        ValidatableResponse response = UserSteps.signIn(email, password);
        accessToken = response
                .extract()
                .as(SuccessSignRs.class).getAccessToken();
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Попытка регистрации с паролем менее 6 символов")
    public void signUpWithInvalidPasswordFailTest() {
        String name = RandomStringUtils.randomAlphabetic(10);
        String email = RandomStringUtils.randomAlphanumeric(10, 12) + "@yandex.ru";
        String password = RandomStringUtils.randomAlphanumeric(5);
        mainPage.clickSignInButton();
        loginPage = new LoginPage(driver);
        loginPage.clickSignUpButton();
        signUpPage = new SignUpPage(driver);
        signUpPage.signUp(name, email, password);
        boolean isDisplayed = signUpPage.getPasswordErrorMessage().isDisplayed();
        if (!isDisplayed) {
            accessToken = UserSteps.signIn(email, password)
                    .extract().as(SuccessSignRs.class)
                    .getAccessToken();
        }
        Assert.assertTrue("Нет ошибки о некорректном пароле", isDisplayed);
    }
}
