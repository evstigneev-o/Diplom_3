package pom;

import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import pojo.SignInRq;

@Getter
public class LoginPage extends BasePage {
    @FindBy(xpath = "//input[@name='name']")
    private WebElement emailField;
    @FindBy(xpath = "//input[@type='password']")
    private WebElement passwordField;
    @FindBy(xpath = "//button[text()='Войти']")
    private WebElement signInButton;
    @FindBy(xpath = "//a[@href='/register']")
    private WebElement signUpButton;
    @FindBy(xpath = "//a[@href='/forgot-password']")
    private WebElement recoverPasswordButton;

    public LoginPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public void clickSignUpButton() {
        signUpButton.click();
    }

    public void clickSignInButton() {
        signInButton.click();
    }

    public void enterPassword(String password) {
        passwordField.sendKeys(password);
    }

    public void enterEmail(String email) {
        emailField.sendKeys(email);
    }

    public void clickRecoverPasswordButton() {
        recoverPasswordButton.click();
    }

    public void loginWithCredentials(SignInRq signInRequest) {
        enterEmail(signInRequest.getEmail());
        enterPassword(signInRequest.getPassword());
        clickSignInButton();
    }
}
