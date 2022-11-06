package com;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import jdk.jfr.Description;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.*;
import java.util.List;
import static com.Gender.FEMALE;
import static com.Gender.MALE;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.sleep;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class AddUserPageTest extends BaseTest {

//    LoginPage loginPage = new LoginPage();
//    AddUserPage addUserPage = new AddUserPage();
//    AddUserPage.ModalWindow modalWindow = new AddUserPage.ModalWindow();

    @BeforeClass
    @Description("Open page, login and moving to AddUserPage")
    void init() {
        openByUrl(getLOGIN_PAGE_URL());
    }

    @BeforeMethod
    @Description("Login and moving to AddUserPage")
    void login() {
        //LoginPage loginPage = new LoginPage();
        loginPage.login("test@protei.ru", "test");
    }

    @Test(dataProvider = "Valid Date")
    @Description("Checking if it is possible to add a new record filling full form with valid values")
    void isNewRecordAddedFillingFullForm(String email, String name, String gender, Integer checkBoxNumber, Integer radioButtonNumber) {
        addUserPage.setEmailInput(email);
        addUserPage.setNameInput(name);
        TestHelper.select(addUserPage.getGenderDropDownField(), gender);
        if (checkBoxNumber != null) {
            AddUserPage.setCheckboxAndCheckItIsSelected(checkBoxNumber);
        }
        if (radioButtonNumber != null) {
            AddUserPage.setRadioButtonAndCheckItIsSelected(radioButtonNumber);
        }
        List<String> expectedNewRecordValue = addUserPage.expectedNewRecordValues();
        TestHelper.clickButton(addUserPage.getAddButton());
        modalWindow.getOkButton();
        List<String> actualNewRecordValue = addUserPage.newRecordValues();
        assertEquals(actualNewRecordValue, expectedNewRecordValue,
                "Expected record value is " + expectedNewRecordValue + ". But actual is " + actualNewRecordValue);
//        System.out.println(actualNewRecordValue);
//        System.out.println(expectedNewRecordValue);
//        System.out.println(" ");
    }

    @Test
    @Description("Checking that it is possible to add a new record in case you provide valid data")
    void isNewRecordAdded() {
        //int actualSize = 0;
        for (int i = 0; i < 3; i++) {
            //AddUserPage addUserPage = new AddUserPage();
            addUserPage.setEmailInput(RandomStringUtils.randomAlphabetic(10) + "@v.r");
            addUserPage.setNameInput(RandomStringUtils.randomAscii(10));
            TestHelper.clickButton(addUserPage.getAddButton());
            //AddUserPage.ModalWindow modalWindow = new AddUserPage.ModalWindow();
            assertTrue(modalWindow.isModalWindowDisplayed());
            TestHelper.clickButton(modalWindow.getOkButton());
        }
        int actualSize = addUserPage.getNewRecordsCollectionSize();
        assertEquals(actualSize, 3);
    }

    @Test
    @Description("Check if inputs fields visible in case there are 400 characters in the name and e-mail address")
    void checkVeryLongEmailAndName() {
        addUserPage.setEmailInput(RandomStringUtils.randomAlphabetic(400) + "@r2.d2");
        addUserPage.setNameInput(RandomStringUtils.randomAscii(400));
        TestHelper.clickButton(addUserPage.getAddButton());
        TestHelper.clickButton(modalWindow.getOkButton());
        addUserPage.getGenderDropDownField().scrollTo();
        addUserPage.getGenderDropDownField().shouldBe(visible);
    }

    @Test(dataProvider = "Invalid Emails Or Names")
    @Description("Checking texts of alert messages")
    void checkAlertMessages(String email, String name, String errorMessage) throws Exception {
        //AddUserPage addUserPage = new AddUserPage();
        addUserPage.setEmailInput(email);
        addUserPage.setNameInput(name);
        TestHelper.clickButton(addUserPage.getAddButton());

        AddUserPage.ModalWindow modalWindow = new AddUserPage.ModalWindow();
        if (modalWindow.isModalWindowDisplayed()) {
            TestHelper.clickButton(modalWindow.getOkButton());
            throw new Exception("New record added, but error message was expected. Check email or name format");
        } else {
            String actualAlertMessage = addUserPage.getAlertText();
            assertEquals(actualAlertMessage, errorMessage, "Invalid alert error message text");
            System.out.print(actualAlertMessage + " ");
            System.out.println(errorMessage);
        }
    }

    @Test(priority = 1)
    @Description("Check key fields are displayed")
    void checkKeyFieldsAreDisplayed() {
        //AddUserPage addUserPage = new AddUserPage();
        BasePage.checkFieldsAreDisplayed(addUserPage.getKeyFields());
    }

    @Test
    void checkSelectAndUnselectCheckbox() {
        for (int i = 1; i < 3; i++) {
            TestHelper.setCheckboxAndCheckItIsSelected(i);
        }
        for (int i = 1; i < 3; i++) {
            TestHelper.unselectCheckboxAndCheckItIsNotSelected(i);
        }
    }

    @Test
    @Description("Checking that it is possible to select each radio button")
    void checkSelectRadioButton() {
        for (int i = 1; i < 4; i++) {
            TestHelper.setRadioButtonAndCheckItIsSelected(i);
        }
    }

    @Test
    @Description("Checking if it is possible to choose gender from drop down menu")
    void checkSelectGender() {
        //AddUserPage addUserPage = new AddUserPage();
        TestHelper.select(addUserPage.getGenderDropDownField(), FEMALE.gender);
        String shouldBeFemaleGenderSelected = addUserPage.getGenderDropDownFieldSelectedText();
        assertEquals(shouldBeFemaleGenderSelected, FEMALE.gender);
        TestHelper.select(addUserPage.getGenderDropDownField(), MALE.gender);
        String shouldBeMaleGenderSelected = addUserPage.getGenderDropDownFieldSelectedText();
        assertEquals(shouldBeMaleGenderSelected, MALE.gender);
    }

    @Test
    @Description("Check columns titles")
    void checkColumnsTitlesTest() {
        //AddUserPage addUserPage = new AddUserPage();
        List<String> actualColumnsTitlesTexts = addUserPage.getColumnsTitlesTexts();
        List<String> expectedColumnsTitlesTexts = List.of("E-Mail", "Имя", "Пол", "Выбор 1", "Выбор 2");
        assertEquals(actualColumnsTitlesTexts, expectedColumnsTitlesTexts, "Incorrect columns titles");
    }

    @DataProvider(name = "Invalid Emails Or Names")
    public Object[][] newRecordInvalidData() {
        String invalidEmailFormatErrorMessage = "Неверный формат E-Mail";
        String emptyNameFieldErrorMessage = "Поле имя не может быть пустым";
        return new Object[][] {
                {"a", "", invalidEmailFormatErrorMessage},
                {"b@t.", "h", invalidEmailFormatErrorMessage},
                {"c@t.", "", invalidEmailFormatErrorMessage},
                {"@t.c", "", invalidEmailFormatErrorMessage},
                {"@t.c", "i", invalidEmailFormatErrorMessage},
                {"d@t.c", "", emptyNameFieldErrorMessage},
                {"@t.c", "", invalidEmailFormatErrorMessage},
                {"e", "j", invalidEmailFormatErrorMessage},
                {"f@t", "k", invalidEmailFormatErrorMessage},
                {"g@t", "", invalidEmailFormatErrorMessage},
                {"", "l", invalidEmailFormatErrorMessage},
                {"", "", invalidEmailFormatErrorMessage},
                {"@t.c", "m", invalidEmailFormatErrorMessage},
                {" @t.c", "n", invalidEmailFormatErrorMessage},
        };
    }

    @DataProvider(name = "Valid Date")
    public Object[][] fillForm() {
        return new Object[][] {
                {"a@b.c", "c", FEMALE.gender, 1, 2},
                {"a@b.c", "c-c c", MALE.gender, 2, null},
                {"a@b.c", "c", MALE.gender, 1, 1},
                {"a@b.c", "c-c c", FEMALE.gender, 1, null},
                {"a@b.c", "c", FEMALE.gender, null, 3},
                {"a@b.c", "c", MALE.gender, 2, 3},
                {"a@b.c", "c", FEMALE.gender, 1, 2},
                {"a@b.c", "c-c c", MALE.gender, null, null},
                {"a@b.c", "c-c c", FEMALE.gender, 2, 1},
                {"a@b.c", "c-c c", MALE.gender, null, 2},
                {"a@b.c", "c-c c", MALE.gender, 1, 3},
                {"a@b.c", "c", FEMALE.gender, null, 1},
                {"a@b.c", "c-c c", MALE.gender, 2, null},
                {"a@b.c", "c", FEMALE.gender, 2, 2}
        };
    }

    @AfterMethod
    void refreshPage() {
        Selenide.refresh();
    }
}
