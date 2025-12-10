package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class FormPage extends BasePage {

    private String baseUrl = "https://parabank.parasoft.com/parabank/register.htm";

    public FormPage(WebDriver driver) {
        super(driver);
    }

    // פתיחת דף ההרשמה
    public void open() {
        driver.get(baseUrl);
    }

    // פונקציות למילוי שדות
    public void setFirstName(String val) {
        type(driver.findElement(By.name("customer.firstName")), val);
    }

    public void setLastName(String val) {
        type(driver.findElement(By.name("customer.lastName")), val);
    }

    public void setAddress(String val) {
        type(driver.findElement(By.name("customer.address.street")), val);
    }

    public void setCity(String val) {
        type(driver.findElement(By.name("customer.address.city")), val);
    }

    public void setState(String val) {
        type(driver.findElement(By.name("customer.address.state")), val);
    }

    public void setZipCode(String val) {
        type(driver.findElement(By.name("customer.address.zipCode")), val);
    }

    public void setPhone(String val) {
        type(driver.findElement(By.name("customer.phoneNumber")), val);
    }

    public void setSSN(String val) {
        type(driver.findElement(By.name("customer.ssn")), val);
    }

    public void setUsername(String val) {
        type(driver.findElement(By.name("customer.username")), val);
    }

    public void setPassword(String val) {
        type(driver.findElement(By.name("customer.password")), val);
    }

    public void setConfirmPassword(String val) {
        type(driver.findElement(By.name("repeatedPassword")), val);
    }

    // לחיצה על כפתור Register
    public void clickRegister() {
        click(By.cssSelector("input[value='Register']"));
    }

    // בדיקה אם הודעת הצלחה קיימת
    public boolean isRegistrationSuccessDisplayed() {
        try {
            WebElement el = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//h1[contains(text(),'Welcome')]")
            ));
            return el.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    // בדיקת הודעת שגיאה במקרה של שדה חובה חסר
    public boolean isRequiredFieldErrorDisplayed() {
        try {
            WebElement el = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.cssSelector(".error")
            ));
            return el.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

}
