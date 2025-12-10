package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CategoryPage extends BasePage {

    public CategoryPage(WebDriver driver) {
        super(driver);
    }

    public void selectCategoryByName(String name) {
        By category = By.xpath("//a[contains(@class,'header__linklist-link') and contains(text(),'" + name + "')]");
        click(category);
        waitForPageStable();
        waitForProductsToLoad();
    }

    public void waitForProductsToLoad() {
        By products = By.cssSelector("#facet-main product-item");
        new WebDriverWait(driver, Duration.ofSeconds(20))
                .until(ExpectedConditions.numberOfElementsToBeMoreThan(products, 0));
    }
}
