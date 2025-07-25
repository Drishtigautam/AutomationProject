package io.auto.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.locators.RelativeLocator;

public class ProductPage {

    private WebDriver driver;

    private By addToCartButton = By.xpath("//button[@id='add-to-cart-sauce-labs-backpack']");
    private By productTitle = By.className("inventory_item_price");
    private By productPrice = By.className("inventory_item_price");
    private By cartLink = By.className("shopping_cart_link");
    private By hamBurger = By.id("react-burger-menu-btn");
    private By logOutButton = By.id("logout_sidebar_link");

    public ProductPage(WebDriver driver){
        this.driver = driver;
    }

    public boolean isProductsPageDisplayed(){
       return driver.findElement(addToCartButton).isDisplayed();
    }

    public boolean isProductListDisplayed(){
        return !driver.findElements(productTitle).isEmpty();
    }

    public String getCurrentUrl(){
        return driver.getCurrentUrl();
    }

    public void addProductToCart(String productName){

        driver.findElement(addToCartButton).click();
    }

    public void addProductName(String productName){
        driver.findElement(By
                .xpath("//div[text()='"+productName+"']/parent::a/parent::div/following-sibling::div/button")).click();
        //NSE -handle
    }

    public boolean isProductTitleDisplayed(String productName){
      return  driver.findElement(By.xpath("//div[text()='"+productName+"']")).isDisplayed();
    }

    public boolean isProductPriceDisplayed(String productName){
        WebElement productNameElement = driver.findElement(By.xpath("//div[text()='"+productName+"']"));
        return driver.findElement(RelativeLocator.with(By.tagName("div")).toLeftOf(addToCartButton)
                .near(productNameElement,2000)).isDisplayed();
    }

    public String getProductPrice(){
        return driver.findElement(RelativeLocator.with(By.tagName("div")).toLeftOf(addToCartButton)).getText();
    }

    public boolean isProductInCart(String productName){

        driver.findElement(cartLink).click();
        return driver.getPageSource().contains(productName);
    }

    public CartPage goToCart(){
        driver.findElement(cartLink).click();
        return new CartPage(driver);
    }

    public void logOut(){
        driver.findElement(hamBurger).click();
        driver.findElement(logOutButton).click();
    }

}
