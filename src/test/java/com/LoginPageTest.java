package com;

import jdk.jfr.Description;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import static org.testng.Assert.assertEquals;

public class LoginPageTest extends BaseTest {

    @BeforeClass
    void init() {
        openByUrl(getLOGIN_PAGE_URL());
    }

    @Test(priority = 1)
    @Description("Check key fields are displayed")
    void checkKeyFieldsAreDisplayed() {
        BasePage.checkFieldsAreDisplayed(loginPage.getKeyFields());
    }

    @Test(priority = 3)
    @Description("Check success login")
    void loginPositiveTest() {
        loginPage.login("test@protei.ru", "test");
        BasePage.checkFieldsAreDisplayed(addUserPage.getAddUserPageKeyFields());
    }

    @Test(priority = 2, dataProvider = "Invalid Login Data Test")
    @Description("Check error message")
    void loginNegativeTest(String login, String password, String errorMessage) {
        loginPage.login(login, password);
        String actualErrorMessage = loginPage.getEmailFormatErrorText();
        assertEquals(actualErrorMessage, errorMessage,
                "Expected error message is " + errorMessage + ". But actual is " + actualErrorMessage);
        loginPage.closeErrorMessage();
    }

    @DataProvider(name = "Invalid Login Data Test")
    public Object[][] userLoginData() {
        String invalidEmailFormatErrorMessage = "Неверный формат E-Mail";
        String invalidEmailAndPasswordErrorMessage = "Неверный E-Mail или пароль";
        return new Object[][] {
                {"test@", "", invalidEmailFormatErrorMessage},
                {"test", "t", invalidEmailFormatErrorMessage},
                {"testprotei.ru", "", invalidEmailFormatErrorMessage},
                {"test@protei.", "t", invalidEmailFormatErrorMessage},
                {"", "test", invalidEmailFormatErrorMessage},
                {"test@proteiru", "test", invalidEmailFormatErrorMessage},
                {"test@protei", "", invalidEmailFormatErrorMessage},
                {"", "", invalidEmailFormatErrorMessage},
                {"test@protei.", "", invalidEmailFormatErrorMessage},
                {"test@proteiru", "", invalidEmailFormatErrorMessage},
                {"test@protei", "test", invalidEmailFormatErrorMessage},
                {"test@", "test", invalidEmailFormatErrorMessage},
                {"test@proteiru", "t", invalidEmailFormatErrorMessage},
                {"testprotei.ru", "t", invalidEmailFormatErrorMessage},
                {"test", "test", invalidEmailFormatErrorMessage},
                {"test", "", invalidEmailFormatErrorMessage},
                {"", "t", invalidEmailFormatErrorMessage},
                {"test@protei", "t", invalidEmailFormatErrorMessage},
                {"test@", "t", invalidEmailFormatErrorMessage},
                {"test@protei.", "test", invalidEmailFormatErrorMessage},
                {"test@protei.ru", "", invalidEmailAndPasswordErrorMessage},
                {"test@protei.ru", "t", invalidEmailAndPasswordErrorMessage}
        };
    }
}
