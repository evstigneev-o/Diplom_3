import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;

public class BaseTest {
    public WebDriver driver;
    public ChromeOptions options;

    @Before
    public void prepareDriver() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
//        System.setProperty("webdriver.chrome.driver", "src/test/resources/yandexdriver");
//        options = new ChromeOptions();
//        options.setBinary("/Applications/Yandex.app/Contents/MacOS/Yandex");
//        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
    }
    @After
    public void tearDown() {
        driver.quit();
    }


}
