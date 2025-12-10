package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;
import org.testng.Assert;
import pages.BasePage;
import pages.ProductPage;
import pages.ShoppingCartPage;
import pages.CategoryPage;

import utils.ExcelUtils;

import java.time.Duration;
import java.util.List;

public class ShoppingCartTest extends BaseTest {
    @Test
    public void testAddProductsToCartAndExportToExcel() {

        CategoryPage categoryPage = new CategoryPage(driver);
        ProductPage productPage = new ProductPage(driver);
        ShoppingCartPage cartPage = new ShoppingCartPage(driver);

        // קטגוריה 1
        categoryPage.selectCategoryByName("מטבח");
        ShoppingCartPage.closePopup(driver);
        productPage.selectProduct();
        ShoppingCartPage.closePopup(driver);
        productPage.addToCart();
        ShoppingCartPage.closePopup(driver);
        productPage.waitForPageStable();
        wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("button.drawer__close-button[data-action='close']")
        )).click();

        // קטגוריה 2
        ShoppingCartPage.closePopup(driver);
        categoryPage.selectCategoryByName("חדר שינה");
        ShoppingCartPage.closePopup(driver);
        productPage.selectProduct();
        ShoppingCartPage.closePopup(driver);
        productPage.addToCart();
        ShoppingCartPage.closePopup(driver);
        productPage.waitForPageStable();
        wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("button.drawer__close-button[data-action='close']")
        )).click();

        // קטגוריה 3
        ShoppingCartPage.closePopup(driver);
        categoryPage.selectCategoryByName("חדר רחצה");
        ShoppingCartPage.closePopup(driver);
        productPage.selectProduct();
        ShoppingCartPage.closePopup(driver);
        productPage.addToCart();
        ShoppingCartPage.closePopup(driver);
        productPage.waitForPageStable();
        wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("button.drawer__close-button[data-action='close']")
        )).click();

        // עגלה + בדיקות...
        cartPage.goToBag();
        cartPage.increaseQuantity();

        int itemCount = cartPage.getItemsCount();
        System.out.println("Number of items in cart: " + itemCount);
        Assert.assertTrue(itemCount > 0, "Cart should have at least one item");

        new WebDriverWait(driver, Duration.ofSeconds(30));
        String totalPriceText = cartPage.getTotalPrice();
        System.out.println("Total price displayed in cart: " + totalPriceText);

        List<WebElement> itemElements = driver.findElements(By.cssSelector("#mini-cart-form > line-item"));
        List<ExcelUtils.CartItem> items = ExcelUtils.extractCartItems(itemElements, driver);

        ExcelUtils.writeCartToExcel(items, "cart_report.xlsx");
    }

}