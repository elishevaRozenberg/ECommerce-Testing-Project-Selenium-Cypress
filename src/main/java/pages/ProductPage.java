package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class ProductPage extends BasePage {

    public ProductPage(WebDriver driver) {
        super(driver);
    }

    public void selectProduct() {
        WebDriverWait localWait = new WebDriverWait(driver, Duration.ofSeconds(20));

        // כל הלינקים למוצרים בגריד הראשי
        By productsLocator = By.cssSelector("#facet-main product-item a[href*='/products/']");

        // קודם מוודאים שיש בכלל מוצרים בדף
        localWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(productsLocator));

        List<WebElement> products = driver.findElements(productsLocator);

        if (products.isEmpty()) {
            throw new RuntimeException("No products found in grid");
        }

        WebElement productToClick = null;
        for (WebElement p : products) {
            if (p.isDisplayed()) {
                productToClick = p;
                break;
            }
        }

        if (productToClick == null) {
            throw new RuntimeException("No visible clickable product found");
        }

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView({block: 'center'});", productToClick);
        js.executeScript("arguments[0].click();", productToClick);
    }

    public void addToCart() {
        try {
            try {
                By popupBy = By.cssSelector(".popup-message.active");
                wait.until(ExpectedConditions.invisibilityOfElementLocated(popupBy));
            } catch (Exception ignore) {}

            By addToCartBy = By.xpath("//*[span[contains(text(),'הוספה לסל')]]");
            WebElement addToCart = wait.until(ExpectedConditions.presenceOfElementLocated(addToCartBy));

            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].scrollIntoView({block: 'center', behavior: 'instant'});", addToCart
            );
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", addToCart);

            System.out.println("Item added to cart successfully");
        } catch (Exception e) {
            System.out.println("Add to Cart failed: " + e.getMessage());
        }
    }

}
