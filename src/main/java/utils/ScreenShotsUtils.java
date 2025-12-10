package utils;

import org.openqa.selenium.*;
import org.openqa.selenium.io.FileHandler;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ScreenShotsUtils {

    private static final String BASE_FOLDER = "screenshots/";

    // יוצרת תיקייה אם לא קיימת
    private static void ensureDirectoryExists() {
        File folder = new File(BASE_FOLDER);
        if (!folder.exists()) {
            folder.mkdirs();
        }
    }

    // פורמט זמן סטנדרטי לכל הסקרינשוטים
    private static String timestamp() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));
    }

    // צילום מסך רגיל
    public static void takeScreenshot(WebDriver driver, String fileName) {
        ensureDirectoryExists();

        File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String finalName = BASE_FOLDER + fileName + "_" + timestamp() + ".png";

        try {
            FileHandler.copy(src, new File(finalName));
        } catch (IOException e) {
            throw new RuntimeException("Cannot save screenshot: " + e.getMessage());
        }
    }

}
