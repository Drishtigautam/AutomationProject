package io.auto.tests;

import io.auto.dataprovider.userDataProvider;
import io.auto.exceptions.LoginFailedExceptions;
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

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;


public class LoginWithDifferentUserTest {

    private WebDriver driver;
    private static final ConfigReader configreader = new ConfigReader();
    private static final Logger logger = LogManager.getLogger(LoginWithDifferentUserTest.class);
    protected LoginPage loginPage;
    protected ProductPage productPage;
    protected CartPage cartPage;

    @BeforeClass(alwaysRun = true)
    @Parameters("browser")
    public void setUp(ITestContext context, @Optional("firefox") String browserName) {
        logger.debug("Initialize the WebDriver for browser: {}", browserName);
        Browser browser = Browser.valueOf(browserName.toUpperCase());

        logger.debug("Initialize the webDriver");
        driver = WebDriverFactory.createDriver(browser);
        context.setAttribute("driver", driver);

        logger.info("Navigating the base url: {}", configreader.getProperty("app.url"));
        driver.get(configreader.getProperty("app.url"));

        loginPage = new LoginPage(driver);
    }

    @Test(dataProvider = "userData")
    public void testLoginWithDifferentUsers(String username, String password, boolean shouldLoginSucceed){

        loginPage.enterCredentials(username, password);
        productPage =  loginPage.clickLogin();

        if(shouldLoginSucceed){
         assertTrue(productPage.isProductsPageDisplayed(),"product Page is not Displayed");
         productPage.logOut();
         assertTrue(loginPage.isLoginPageDisplayed(),"user is not on the login page");
        }else{
         assertTrue(loginPage.isLoginErrorDisplayed(), "Error Message is not Present");
        }
    }


    @DataProvider(name = "userData")
    private Object[][] userData(){
     return userDataProvider.userData();
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() {
        if (driver != null) {
            logger.info("Closing the browser.");
            driver.quit();
        }
    }
}

