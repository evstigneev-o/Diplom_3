package pom;

import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

@Getter
public class ForgotPasswordPage extends BasePage {

    @FindBy(xpath = "//input[@name='name']")
    private WebElement emailField;
    @FindBy(xpath = "//button[text()='Восстановить']")
    private WebElement recoverButton;
    @FindBy(xpath = "//a[@href='/login']")
    private WebElement signInButton;

    public ForgotPasswordPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public void enterEmail(String email) {
        emailField.sendKeys(email);
    }

    public void clickRecoverButton() {
        recoverButton.click();
    }

    public void clickSignInButton() {
        signInButton.click();
    }
}
