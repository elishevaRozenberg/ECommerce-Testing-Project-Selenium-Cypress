package tests;

import org.testng.Assert;
import pages.FormPage;
import org.testng.annotations.Test;

import java.util.UUID;

public class FormTest extends BaseTest {

    @Test(priority = 1)
    public void testSuccessfulRegistration() throws InterruptedException {

        FormPage formPage = new FormPage(driver);
        formPage.open();
        Thread.sleep(1500);

        String username = "user" + UUID.randomUUID().toString().substring(0, 5);

        formPage.setFirstName("Elisheva");
        Thread.sleep(500);
        formPage.setLastName("Rosenberg");
        Thread.sleep(500);
        formPage.setAddress("Main street 5");
        Thread.sleep(500);
        formPage.setCity("Tel Aviv");
        Thread.sleep(500);
        formPage.setState("Israel");
        Thread.sleep(500);
        formPage.setZipCode("12345");
        Thread.sleep(500);
        formPage.setPhone("0501234567");
        Thread.sleep(500);
        formPage.setSSN("9999");
        Thread.sleep(500);
        formPage.setUsername(username);
        Thread.sleep(500);
        formPage.setPassword("Test1234");
        Thread.sleep(500);
        formPage.setConfirmPassword("Test1234");
        Thread.sleep(800);

        formPage.clickRegister();
        Thread.sleep(1500);

        Assert.assertTrue(formPage.isRegistrationSuccessDisplayed(),
                "Registration did not complete successfully!");
    }


    @Test(priority = 2)
    public void testMissingRequiredFieldShowsError() throws InterruptedException {

        FormPage page = new FormPage(driver);
        page.open();
        Thread.sleep(1000);

        // כאן לא ממלאים FirstName בכוונה
        page.setLastName("Rosenberg");
        page.setAddress("Street 5");
        page.setCity("TLV");
        page.setState("Israel");
        page.setZipCode("12345");
        page.setPhone("0501234567");
        page.setSSN("1111");
        page.setUsername("testUser");
        page.setPassword("Test1234");
        page.setConfirmPassword("Test1234");

        page.clickRegister();
        Thread.sleep(1500);

        Assert.assertTrue(page.isRequiredFieldErrorDisplayed(),
                "Expected required field error, but none appeared.");
    }


    @Test(priority = 3)
    public void testDuplicateUsernameShowsError() throws InterruptedException {

        FormPage formPage = new FormPage(driver);
        formPage.open();
        Thread.sleep(1500);

        // משתמש סביר שקיים במערכת
        String existingUser = "john";

        formPage.setFirstName("Test");
        formPage.setLastName("User");
        formPage.setAddress("Street 1");
        formPage.setCity("NY");
        formPage.setState("NY");
        formPage.setZipCode("12345");
        formPage.setPhone("0501234567");
        formPage.setSSN("1234");
        formPage.setUsername(existingUser);
        formPage.setPassword("Test1234");
        formPage.setConfirmPassword("Test1234");

        formPage.clickRegister();
        Thread.sleep(2000);

        Assert.assertTrue(driver.getPageSource().contains("This username already exists")
                        || driver.getPageSource().toLowerCase().contains("error"),
                "Expected duplicate username error, but did not see one.");
    }

}
