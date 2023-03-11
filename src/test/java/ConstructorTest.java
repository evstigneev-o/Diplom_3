import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pom.MainPage;

import static config.Config.BASE_URL;
@Epic("Stellar Burgers")
@Feature("Constructor")
@DisplayName("Конструктор")
public class ConstructorTest extends BaseTest {
    public MainPage mainPage;

    @Before
    public void init() {
        driver.get(BASE_URL);
        mainPage = new MainPage(driver);
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Переход к булкам")
    public void clickOnBunsSectionButtonAutoScrollTest() {
        mainPage.clickOnFillingsSectionButton();
        mainPage.clickOnBunsSectionButton();
        boolean isSelected = mainPage.isSectionButtonSelected(mainPage.getBunsSectionButton());
        Assert.assertTrue("Переход к булкам не осуществлен", isSelected);
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Переход к соусам")
    public void clickOnSousesSectionButtonAutoScrollTest() {
        mainPage.clickOnSousesSectionButton();
        boolean isSelected = mainPage.isSectionButtonSelected(mainPage.getSousesSectionButton());
        Assert.assertTrue("Переход к соусам не осуществлен", isSelected);
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Переход к начинкам")
    public void clickOnFillingsSectionButtonAutoScrollTest() {
        mainPage.clickOnFillingsSectionButton();
        boolean isSelected = mainPage.isSectionButtonSelected(mainPage.getFillingsSectionButton());
        Assert.assertTrue("Переход к начинкам не осуществлен", isSelected);
    }
}
