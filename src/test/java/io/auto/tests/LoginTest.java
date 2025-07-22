package io.auto.tests;

import com.aventstack.extentreports.ExtentTest;
import io.auto.exceptions.LoginFailedExceptions;
import io.auto.listener.TestListener;
import org.testng.annotations.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class LoginTest extends BaseTest {

    private static final Logger logger = LogManager.getLogger(LoginTest.class);


    @Test(priority = 1, groups = {"smoke "})
    public void testLoginSuccess(){
        ExtentTest test = TestListener.getTest();

        //Verify That user is at Product Page
        String logoText = loginPage.getLogoText();
        assertEquals(logoText,"Swag Labs");
        assertTrue(productPage.isProductsPageDisplayed(),"Product Page is Not Displayed");

        //Verify the page url
        String currentUrl = productPage.getCurrentUrl();
        assertEquals(currentUrl, getExpectedUrl(),"Message Page Url Incorrect");

        //Verify that the product list is displayed
        assertTrue(productPage.isProductListDisplayed(),"Product List is not Displayed");
    }

    @Test(priority = 2)
    public void testLoginFailure(){
        productPage.logOut();
        loginPage.enterCredentials("wrong_user","wrong_password");
       try{
           productPage = loginPage.clickLogin();
       }catch (LoginFailedExceptions e){
           //logs statements
       }
       assertTrue(loginPage.isLoginErrorDisplayed(),"Is Error message Displayed, Check User Credentials");

    }

    private String getExpectedUrl(){
        return configreader.getProperty("app.url")+"inventory.html";
    }
}
