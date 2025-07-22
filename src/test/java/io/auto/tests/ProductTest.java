package io.auto.tests;

import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

public class ProductTest extends BaseTest{

    @Test(priority = 1)
    public void testProductDetails(){
        assertTrue(productPage.isProductListDisplayed(),"Products are not Displayed");
        assertTrue(productPage.isProductTitleDisplayed("Sauce Labs Backpack"),
                "Product Title is not Displayed");
        assertTrue(productPage.isProductPriceDisplayed("Sauce Labs Backpack"),
                "Product price is not displayed");
    }

    @Test(priority = 2)
    public void testAddToCart(){

        productPage.addProductName("Sauce Labs Backpack");
        assertTrue(productPage.isProductInCart("Sauce Labs Backpack"),
                "Product is not Added to the Cart");
    }
}
