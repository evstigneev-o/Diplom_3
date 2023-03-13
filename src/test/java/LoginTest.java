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
import pom.*;

import static config.Config.BASE_URL;

@Epic("Stellar Burgers")
@Feature("Login")
@DisplayName("Вход в личный кабинет")
public class LoginTest extends BaseTest {
    MainPage mainPage;
    LoginPage loginPage;
    AccountPage accountPage;
    RegisterRq testUser;
    String accessToken;
    SignInRq signInRequest;

    @Before
    public void init() {
        testUser = UserGenerator.generateUser();
        SuccessSignRs signUpResponse = UserSteps.createUser(testUser)
                .extract()
                .as(SuccessSignRs.class);
        accessToken = signUpResponse.getAccessToken();
        signInRequest = new SignInRq(testUser.getEmail(), testUser.getPassword());
        driver.get(BASE_URL);
        this.mainPage = new MainPage(driver);
        this.loginPage = new LoginPage(driver);
        this.accountPage = new AccountPage(driver);
    }

    @After
    public void cleanUp() {
        UserSteps.deleteUser(accessToken);
    }

    @Test
    @Severity(SeverityLevel.BLOCKER)
    @DisplayName("Вход по кнопке Войти в аккаунт на главной")
    public void successfullySignInWithValidDataWithSignInButtonTest() {
        mainPage.clickSignInButton();
        loginPage.loginWithCredentials(signInRequest);
        mainPage.clickAccountButton();
        boolean isDisplayed = accountPage.getProfileButton().isDisplayed();
        Assert.assertTrue("Вход в личный кабинет не выполнен", isDisplayed);
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Вход через кнопку Личный кабинет")
    public void successfullySignInWithValidDataWithAccountButtonTest() {
        mainPage.clickAccountButton();
        loginPage.loginWithCredentials(signInRequest);
        mainPage.clickAccountButton();
        boolean isDisplayed = accountPage.getProfileButton().isDisplayed();
        Assert.assertTrue("Вход в личный кабинет не выполнен", isDisplayed);
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Вход через кнопку в форме регистрации")
    public void successfullySignInWithValidDataFromSignUpFormTest() {
        mainPage.clickSignInButton();
        loginPage.clickSignUpButton();
        SignUpPage signUpPage = new SignUpPage(driver);
        signUpPage.clickSignInButton();
        loginPage.loginWithCredentials(signInRequest);
        mainPage.clickAccountButton();
        boolean isDisplayed = accountPage.getProfileButton().isDisplayed();
        Assert.assertTrue("Вход в личный кабинет не выполнен", isDisplayed);
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Вход через кнопку в форме восстановления пароля")
    public void successfullySignInWithValidDataFromPasswordRecoverFormTest() {
        mainPage.clickSignInButton();
        loginPage.clickRecoverPasswordButton();
        ForgotPasswordPage forgotPasswordPage = new ForgotPasswordPage(driver);
        forgotPasswordPage.clickSignInButton();
        loginPage.loginWithCredentials(signInRequest);
        mainPage.clickAccountButton();
        boolean isDisplayed = accountPage.getProfileButton().isDisplayed();
        Assert.assertTrue("Вход в личный кабинет не выполнен", isDisplayed);
    }
}
