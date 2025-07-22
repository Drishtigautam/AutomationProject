package io.auto.tests;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;


import static org.testng.Assert.assertTrue;

public class LogoutTest extends BaseTest{

    private static final Logger logger = LogManager.getLogger(LogoutTest.class);

    @Test(groups = {"smoke "})
    public void testLogout(){
        logger.info("Verifying product title is displayed.");
        assertTrue(productPage.isProductTitleDisplayed("Sauce Labs Backpack"),"Product Title is not Displayed");

        logger.info("Performing logout.");
        productPage.logOut();

        logger.info("Verifying Redirection to login page after logout");
        assertTrue(loginPage.isLoginPageDisplayed(), "User is not directed to to the login page");

        logger.info("Logout Test Completed successfully");
    }

}
