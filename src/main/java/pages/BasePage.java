package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

public class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void click(By locator) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // מחכים שהאלמנט החדש יהיה קליקבל
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));

        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block: 'center'});", element);

        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }



    /** כתיבה בטופס */
    protected void type(WebElement element, String text) {
        wait.until(ExpectedConditions.visibilityOf(element)).clear();
        element.sendKeys(text);
    }

    /** מחכה שהאלמנט יהיה גלוי */
    protected void waitForVisibility(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    /** מחזיר טקסט מהאלמנט */
    protected String getText(WebElement element) {
        waitForVisibility(element);
        return element.getText();
    }

    public void waitForPageStable() {
        try {
            Thread.sleep(1500); // דיליי קצר ומספיק
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }






}
