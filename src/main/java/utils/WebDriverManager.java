package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;


public class WebDriverManager {

    public static WebDriver createDriver() {
        // יוצרים ChromeDriver חדש ומחזירים אותו
        return new ChromeDriver();
    }
}
