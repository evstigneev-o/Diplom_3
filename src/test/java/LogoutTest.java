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
import static config.Config.LOGIN_PATH;

@Epic("Stellar Burgers")
@Feature("Logout")
@DisplayName("Выход из личного кабинета")
public class LogoutTest extends BaseTest {
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
        driver.get(BASE_URL + LOGIN_PATH);
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
    @DisplayName("Выход по кнопке Выйти в личном кабинете")
    public void successfullyLogoutWithLogoutButtonTest() {
        loginPage.loginWithCredentials(signInRequest);
        mainPage.clickAccountButton();
        accountPage.clickLogoutButton();
        mainPage.clickAccountButton();
        boolean isDisplayed = loginPage.getSignInButton().isDisplayed();
        Assert.assertTrue("Выход из личного кабинета не выполнен", isDisplayed);
    }
}
