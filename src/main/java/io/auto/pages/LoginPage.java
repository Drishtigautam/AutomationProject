package io.auto.pages;


import io.auto.exceptions.LoginFailedExceptions;

import org.apache.logging.log4j.LogManager;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.apache.logging.log4j.Logger;



import java.time.Duration;


public class LoginPage {

    private final WebDriver driver;
    private final WebDriverWait wait;
    private static final Logger logger = LogManager.getLogger(LoginPage.class);





    //Elements
    private final By userNameField = By.id("user-name");
    private final By userPassword = By.id("password");
    private final By loginButton = By.id("login-button");
    private final By appLogotext = By.xpath("//div[@class='app_logo']");
    private final By errorMessage = By.cssSelector(".error-message-container.error");

    public LoginPage(WebDriver driver){

        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));

    }


    //Actions

    public void enterCredentials(String username, String password){
        this.enterUserName(username);
        this.enterPassword(password);
        logger.info("Entering credentials: " + username + " / [PROTECTED]");
        //this.clickLogin();
    }

    public String getLogoText(){
        String text =driver.findElement(appLogotext).getText();
        logger.debug("Fetched logo text: " + text);
        return text;
    }

    private void enterUserName(String name){
        driver.findElement(userNameField).clear();
        driver.findElement(userNameField).sendKeys(name);
        logger.debug("Entering username: " + name);
    }

    private void enterPassword(String password){
        driver.findElement(userPassword).clear();
        driver.findElement(userPassword).sendKeys(password);
        logger.debug("Entering password.");
    }

    public ProductPage clickLogin(){
        logger.info("Clicking login button.");
        wait.until(ExpectedConditions.elementToBeClickable(loginButton)).click();

        if(isLoginErrorDisplayed()){
            logger.error("Login failed. Error message is displayed.");
            throw new LoginFailedExceptions("Login Failed!");

        }
        logger.info("Login successful, navigating to ProductPage.");
        return new ProductPage(driver);
    }

    public boolean isLoginErrorDisplayed(){
        try{
        WebElement element = driver.findElement(errorMessage);
        boolean visible = element.isDisplayed();
            logger.warn("Login error displayed: " + visible);
            return  visible;
        }catch(NoSuchElementException e){
            //if error message is not found, there was no error - login successfull
            logger.debug("No login error message displayed.");
            return false;
        }

    }

    public boolean isLoginPageDisplayed(){
        return driver.findElement(userNameField).isDisplayed();
    }
}
