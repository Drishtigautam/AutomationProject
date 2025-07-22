package io.auto.tests;

import io.auto.pages.CartPage;
import io.auto.pages.LoginPage;
import io.auto.pages.ProductPage;
import io.auto.utilis.Browser;
import io.auto.utilis.ConfigReader;
import io.auto.utilis.WebDriverFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.annotations.*;


public abstract class BaseTest {

    protected WebDriver driver;
    protected LoginPage loginPage;
    protected ProductPage productPage;
    protected CartPage cartPage;
    protected ConfigReader configreader = new ConfigReader();
    private static final Logger logger = LogManager.getLogger(BaseTest.class);

    @BeforeClass(alwaysRun = true)
    @Parameters("browser")
    public void setUp(ITestContext context, @Optional("firefox") String browserName) {
        logger.debug("Initialize the WebDriver for browser: {}", browserName);

        Browser browser = Browser.valueOf(browserName.toUpperCase());
        driver = WebDriverFactory.createDriver(browser);
        context.setAttribute("driver", driver);

        logger.info("Navigating the base url: {}", configreader.getProperty("app.url"));
        driver.get(configreader.getProperty("app.url"));

        loginPage = new LoginPage(driver);
        logger.info("Entering credentials and logging in.");
        loginPage.enterCredentials(
                configreader.getProperty("app.valid.user"),
                configreader.getProperty("app.valid.password")
        );
        productPage = loginPage.clickLogin();
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() {
        if (driver != null) {
            logger.info("Closing the browser.");
            driver.quit();
        }
    }
}
