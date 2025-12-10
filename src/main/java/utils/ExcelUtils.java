package utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ExcelUtils {

    public static class CartItem {
        public String name;
        public int quantity;
        public String pricePerItem;
        public String totalPrice;
        public String status;

        public CartItem(String name, int quantity, String pricePerItem, String totalPrice, String status) {
            this.name = name;
            this.quantity = quantity;
            this.pricePerItem = pricePerItem;
            this.totalPrice = totalPrice;
            this.status = status;
        }

        @Override
        public String toString() {
            return name + " | Qty: " + quantity + " | Price: " + pricePerItem + " | Total: " + totalPrice + " | Status: " + status;
        }
    }

    public static List<CartItem> extractCartItems(List<WebElement> itemElements, WebDriver driver) {
        List<CartItem> items = new ArrayList<>();

        for (int i = 0; i < itemElements.size(); i++) {
            WebElement item = itemElements.get(i);

            try {
                System.out.println("\n--- Processing item " + (i + 1) + " ---");

                // שם המוצר
                String name = item.findElement(
                        By.cssSelector("div.line-item__info div > a")
                ).getText().trim();
                System.out.println("Name: " + name);

                // כמות
                WebElement quantityInput = item.findElement(
                        By.cssSelector("line-item-quantity input.quantity-selector__input")
                );
                int quantity = Integer.parseInt(quantityInput.getAttribute("value"));
                System.out.println("Quantity: " + quantity);

                // מחיר פריט
                String pricePerItem = item.findElement(
                        By.cssSelector("line-item .price-list--stack .price--highlight")
                ).getText().trim();
                System.out.println("Price per item: " + pricePerItem);

                // חישוב מחיר כולל
                String numericPrice = pricePerItem.replaceAll("[^\\d.]", "");
                double total = Double.parseDouble(numericPrice) * quantity;
                String totalPrice = "₪" + String.format("%,.2f", total);

                // הוספה לרשימה
                items.add(new CartItem(name, quantity, pricePerItem, totalPrice, "PASS"));

            } catch (Exception e) {
                System.out.println("Warning: Could not read item details. Skipping...");
                e.printStackTrace();
            }
        }

        return items;
    }

    // ⭐ פונקציה חדשה – חישוב סכום כולל של כל הפריטים
    public static double calculateCartTotal(List<CartItem> items) {
        double sum = 0;

        for (CartItem item : items) {
            try {
                double price = Double.parseDouble(item.pricePerItem.replaceAll("[^\\d.]", ""));
                sum += price * item.quantity;
            } catch (Exception e) {
                System.out.println("Failed parsing price for: " + item.name);
            }
        }

        return sum;
    }

    // ⭐ כתיבה לאקסל – כולל שורת TOTAL בסוף
    public static void writeCartToExcel(List<CartItem> items, String filePath) {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Shopping Cart");

        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("Item Name");
        header.createCell(1).setCellValue("Quantity");
        header.createCell(2).setCellValue("Price per Item");
        header.createCell(3).setCellValue("Total Price");
        header.createCell(4).setCellValue("Status");

        int rowNum = 1;
        for (CartItem item : items) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(item.name);
            row.createCell(1).setCellValue(item.quantity);
            row.createCell(2).setCellValue(item.pricePerItem);
            row.createCell(3).setCellValue(item.totalPrice);
            row.createCell(4).setCellValue(item.status);
        }

        // ⭐ סכום כולל
        double total = calculateCartTotal(items);
        String formattedTotal = "₪" + String.format("%,.2f", total);

        // ⭐ שורת TOTAL
        Row totalRow = sheet.createRow(rowNum + 1);
        totalRow.createCell(0).setCellValue("FINAL TOTAL:");
        totalRow.createCell(3).setCellValue(formattedTotal);

        for (int i = 0; i < 5; i++) {
            sheet.autoSizeColumn(i);
        }

        try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
            workbook.write(fileOut);
            workbook.close();
            System.out.println("Excel file written successfully to " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
