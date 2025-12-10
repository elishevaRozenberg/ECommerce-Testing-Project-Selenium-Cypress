package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class ShoppingCartPage extends BasePage {

    public ShoppingCartPage(WebDriver driver) {
        super(driver);
    }

    // כניסה לעגלת הקניות
    public void goToBag() {
        By cartButton = By.cssSelector(
                "#shopify-section-sections--17619525533846__header store-header div div div div a:nth-child(4)"
        );

        click(cartButton); // עכשיו click עובד עם locator ולא עם WebElement מת
    }

   // ספירת פריטים בעגלה לפי ערך input של כמות
   public int getItemsCount() {
       List<WebElement> quantityInputs = driver.findElements(
               By.cssSelector("line-item-quantity input.quantity-selector__input")
       );

       int totalCount = 0;

       for (WebElement input : quantityInputs) {
           try {
               String value = input.getAttribute("value");
               if (value == null || value.isBlank()) {
                   totalCount += 1;
               } else {
                   totalCount += Integer.parseInt(value.trim());
               }
           } catch (Exception e) {
               System.out.println("Warning: could not read quantity, counting as 1");
               totalCount += 1;
           }
       }

       return totalCount;
   }


    // שליפת מחיר כולל
    // שליפת מחיר כולל מהכפתור של הקופה
    public String getTotalPrice() {
        new WebDriverWait(driver, Duration.ofSeconds(30));

        WebElement checkoutButton = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.cssSelector("button.checkout-button.button--primary.button--full")
                )
        );

        String fullText = checkoutButton.getText().trim();
        String priceText = fullText.replaceAll("[^0-9.,]", "");
        System.out.println("Price: " + priceText);
        return priceText;
    }



  public void increaseQuantity() {
      WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(
              By.cssSelector("line-item-quantity input.quantity-selector__input")
      ));

      ((JavascriptExecutor) driver).executeScript("arguments[0].value = 2;", input);
      ((JavascriptExecutor) driver).executeScript(
              "arguments[0].dispatchEvent(new Event('change', { bubbles: true }));",
              input
      );
  }

    public static void closePopup(WebDriver driver) {
        WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(5));
        JavascriptExecutor js = (JavascriptExecutor) driver;

        // 1) פופ־אפ של Flashy - כפתור X עם תמונה alt="Close Popup"
        try {
            WebElement flashyClose = shortWait.until(
                    ExpectedConditions.elementToBeClickable(
                            By.cssSelector("img[alt='Close Popup']")
                    )
            );
            js.executeScript("arguments[0].click();", flashyClose);
            System.out.println("Flashy popup closed");
        } catch (Exception e) {
            System.out.println("Flashy popup not present");
        }

        // 2) פופ־אפ של ZAP - כפתור עם id = ZA_CAMP_CLOSE_BUTTON
        try {
            WebElement zapClose = shortWait.until(
                    ExpectedConditions.elementToBeClickable(
                            By.id("ZA_CAMP_CLOSE_BUTTON")
                    )
            );
            js.executeScript("arguments[0].click();", zapClose);
            System.out.println("ZAP popup closed");
        } catch (Exception e) {
            System.out.println("ZAP popup not present");
        }

        // 3) ניקוי overlays שנשארו מעל המסך
        try {
            java.util.List<WebElement> overlays = driver.findElements(
                    By.cssSelector(".fancybox-overlay, .modal-backdrop, .fls-overlay, .overlay, [class*='overlay']")
            );
            for (WebElement ov : overlays) {
                js.executeScript("arguments[0].remove();", ov);
            }
            if (!overlays.isEmpty()) {
                System.out.println("Overlays removed: " + overlays.size());
            }
        } catch (Exception ignored) {
        }
    }


}
