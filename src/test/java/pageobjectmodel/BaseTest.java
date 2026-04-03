package pageobjectmodel;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.concurrent.TimeUnit;

public abstract class BaseTest {
    protected WebDriver driver;
    protected static final String URL = "https://qa-scooter.praktikum-services.ru/";
    protected static final int IMPLICIT_WAIT = 10;

    @Before
    public void setUp() {
        //  WebDriverManager автоматически скачивает и настраивает драйверы
        WebDriverManager.chromedriver().setup();
        WebDriverManager.firefoxdriver().setup();

        boolean useFirefox = false; // false = Chrome, true = Firefox
        driver = getWebDriver(useFirefox);
        driver.get(URL);
        driver.manage().timeouts().implicitlyWait(IMPLICIT_WAIT, TimeUnit.SECONDS);
        driver.manage().window().maximize();
    }

    public WebDriver getWebDriver(boolean useFirefox) {
        if (useFirefox) {
            return new FirefoxDriver();
        } else {
            return new ChromeDriver();
        }
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}

