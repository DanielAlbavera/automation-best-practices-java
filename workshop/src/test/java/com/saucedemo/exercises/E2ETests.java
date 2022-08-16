package com.saucedemo.exercises;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.saucedemo.solution.pages.*;
import org.junit.Ignore;
import org.junit.Test;
import com.saucelabs.saucebindings.DataCenter;
import com.saucelabs.saucebindings.junit4.SauceBaseTest;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.JavascriptExecutor;

public class E2ETests extends SauceBaseTest {

    private String USER_NAME = "standard_user";

    @Override
    public DataCenter getDataCenter() {
        // Select data center to execute against
        return DataCenter.US_WEST; // DataCenter.EU_CENTRAL
    }

    // Here's the first test to get you started. Try to run it
    @Test()
    public void appRenders() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.visit();
        assertTrue(loginPage.isDisplayed());
    }

    @Test()
    //@Ignore("not implemented yet")
    public void loginWorks() {
        LoginPage loginPage = new LoginPage(driver);
        /*
         * Add your code below this
         */
        loginPage.visit();
        loginPage.login(USER_NAME);
        /*
         * ^^^^^^^^ AddYour code above this ^^^^^^^^^
         */
        assertTrue(new ProductsPage(driver).isDisplayed());
    }

    @Test()
    //@Ignore("not implemented yet")
    public void userCanCheckout() {
        /*
         * Add your code below this
         */
        LoginPage loginPage = new LoginPage(driver);
        loginPage.visit();
        loginPage.login(USER_NAME);

        ProductsPage productsPage = new ProductsPage(driver);
        productsPage.addAnyProductToCart();

        CheckoutStepOnePage checkoutStepOnePage = new CheckoutStepOnePage(driver);
        checkoutStepOnePage.visit();
        checkoutStepOnePage.enterPersonalDetails();

        CheckoutOverviewPage checkoutOverviewPage = new CheckoutOverviewPage(driver);
        checkoutOverviewPage.finish();

        /*
         * ^^^^^^^^ AddYour code above this ^^^^^^^^^
         */
        assertTrue(new CheckoutCompletePage(driver).isDisplayed());
    }

    /*
     * Don't do or look at the test below until the atomic tests section
     */
    @Test()
    //@Ignore("Ignoring until atomic tests section")
    public void userCanCheckoutAtomic() {
        /*
         * Add your code below this
         */

        /*
         * 1. First navigate to the LoginPage
         */
        LoginPage loginPage = new LoginPage(driver);
        loginPage.visit();

        /*
         * 2. Removing UI Login We already know that our user can successfully log in with
         * loginWorks() hence, we don't need to waste time, web requests, or add flakiness
         */
        driver.manage().deleteAllCookies();
        ((JavascriptExecutor) driver).executeScript("localStorage.clear();");

        Cookie loginCookie = new Cookie("session-username", USER_NAME);
        //Try document.cookie="session-username=standard_user" in browser console
        driver.manage().addCookie(loginCookie);

        /*
         * 3. Add item to cart without UI interactions
         *
         * We also don't care regardless of whether clicking a button will add an item to a cart We can
         * easily cover this risk with another test Hence, let's simulate adding an item to a cart
         * by updating localStorage
         */
        ShoppingCartPage shoppingCartPage = new ShoppingCartPage(driver);
        shoppingCartPage.visit();
        assertEquals(0, shoppingCartPage.getItemsCount());

        ((JavascriptExecutor) driver).executeScript("localStorage.setItem(\"cart-contents\", \"[4]\");");
        driver.navigate().refresh();
        assertEquals(1, shoppingCartPage.getItemsCount());

        /*
         * 4. Truly test the checkout flow All the preconditions have been met - User is logged in -
         * User has item in a cart Does the checkout process work?
         *
         * Fill in the code, you've done this before
         */

        CheckoutStepOnePage checkoutStepOnePage = new CheckoutStepOnePage(driver);
        checkoutStepOnePage.visit();
        checkoutStepOnePage.enterPersonalDetails();

        CheckoutOverviewPage checkoutOverviewPage = new CheckoutOverviewPage(driver);
        checkoutOverviewPage.finish();

        /*
         * ^^^^^^^^ AddYour code above this ^^^^^^^^^
         */
        assertTrue(new CheckoutCompletePage(driver).isDisplayed());
    }
}
