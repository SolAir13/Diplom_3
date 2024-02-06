package core;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.concurrent.TimeUnit;

import static api.config.ConfigApp.*;

abstract public class BaseTest {
    protected WebDriver driver;

    @BeforeEach
    public void setUp() {
//        String browserName = System.getProperty("browserName");
        String browserName = "chrome";

        switch (browserName) {
            case "chrome":
                WebDriverManager.chromedriver().setup();

                driver = new ChromeDriver();

                driver.manage().window().maximize();
                driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
                driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
                BasePage.setDriver(driver);
                driver.get(BASE_URL);
                break;
            case "yandex":
                WebDriverManager.chromedriver().setup();
                ChromeOptions options = new ChromeOptions();
                System.setProperty("webdriver.chrome.driver", "src/main/resources/yandexdriver");
                options.addArguments("--remote-allow-origins=*");
                options.setBinary("/Applications/Yandex.app/Contents/MacOS/Yandex");
                driver = new ChromeDriver(options);
                BasePage.setDriver(driver);
                driver.get(BASE_URL);
                break;
            default:
                throw new RuntimeException("Browser is not detected");
        }
    }

    @AfterEach
    public void tearDown() {
        driver.close();
        driver.quit();
    }
}
