package com;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import static com.codeborne.selenide.CollectionCondition.size;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Condition.checked;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static org.testng.Assert.assertTrue;

public class AddUserPage implements BasePage {

    private final SelenideElement emailInputTitle = $("[for='dataEmail']");
    private final SelenideElement emailInput = $("input#dataEmail");
    private final SelenideElement nameInputTitle = $("[for='dataName']");
    private final SelenideElement nameInput = $("input#dataName");
    private final SelenideElement genderInputTitle = $("[for='dataGender']");
    private final SelenideElement genderDropDownField = $("select#dataGender");
    private final SelenideElement checkBoxVersion1 = $("input#dataCheck11");
    private final SelenideElement checkBoxVersion2 = $("input#dataCheck12");
    private final SelenideElement radioButtonVersion1 = $("input#dataSelect21");
    private final SelenideElement radioButtonVersion2 = $("input#dataSelect22");
    private final SelenideElement radioButtonVersion3 = $("input#dataSelect23");
    private final SelenideElement addButton = $("button#dataSend");
    private final ElementsCollection columnTitles = $$("#dataTable th");
    private final ElementsCollection newRecords = $$("tbody > tr");
    private final SelenideElement emailFormatError = $("div#emailFormatError");
    private final SelenideElement blankNameError = $("div#blankNameError");
    private final SelenideElement alert = $(".uk-alert-danger");
    private final ElementsCollection firstNewRecordValues = $$("tbody > tr:first-child > td");

    private final List<SelenideElement> keyFields =
            List.of(emailInputTitle, emailInput, nameInputTitle, nameInput, genderInputTitle, genderDropDownField,
                    checkBoxVersion1, checkBoxVersion2, radioButtonVersion1, radioButtonVersion2, radioButtonVersion3,
                    addButton);

    public List<String> newRecordValues() {
        return firstNewRecordValues.texts();
    }

    public boolean isAlertDisplayed() {
        alert.shouldBe(visible);
        return alert.isDisplayed();
    }

    public String getAlertText() {
        alert.shouldBe(visible, Duration.ofMillis(5000));
        return alert.text();
    }

    public String getEmailFormatErrorText() {
        emailFormatError.shouldBe(visible);
        return emailFormatError.text();
    }

    public String getBlankNameErrorText() {
        blankNameError.shouldBe(visible);
        return blankNameError.text();
    }

    public int getNewRecordsCollectionSize() {
        return newRecords.size();
    }

    public void setEmailInput(String email) {
        emailInput.shouldBe(enabled);
        emailInput.clear();
        emailInput.sendKeys(email);
    }

    public void setNameInput(String name) {
        nameInput.shouldBe(enabled);
        nameInput.clear();
        nameInput.sendKeys(name);
    }

    public SelenideElement getAddButton() {
        addButton.shouldBe(enabled);
        return addButton;
    }

    public List<SelenideElement> getAddUserPageKeyFields() {
        return this.keyFields;
    }

    public List<String> getColumnsTitlesTexts() {
        columnTitles.shouldBe(size(5));
        return columnTitles.texts();
    }

    public List<SelenideElement> getKeyFields() {
        return this.keyFields;
    }

    public SelenideElement getGenderDropDownField() {
        genderDropDownField.shouldBe(enabled);
        return genderDropDownField;
    }

    public String getGenderDropDownFieldSelectedText() {
        genderDropDownField.shouldBe(visible);
        return genderDropDownField.text();
    }

    @Step("Set {checkboxNumber} radiobutton")
    public static Object setCheckboxAndCheckItIsSelected(int checkboxNumber) {
        SelenideElement checkboxLabel = $("input#dataCheck1" + checkboxNumber);
        checkboxLabel.scrollIntoView("{block: \"center\"}");
        checkboxLabel.shouldBe(enabled, Duration.ofMillis(5000));
        if (checkboxLabel.is(not(checked))) {
            checkboxLabel.click();
        }
        assertTrue(checkboxLabel.isSelected());
        return checkboxLabel;
    }

    @Step("Set {radioButtonNumber} radiobutton")
    public static Object setRadioButtonAndCheckItIsSelected(int radioButtonNumber) {
        SelenideElement radioLabel = $("input#dataSelect2" + radioButtonNumber);
        radioLabel.scrollIntoView("{block: \"center\"}");
        radioLabel.shouldBe(enabled, Duration.ofMillis(5000)).click();
        assertTrue(radioLabel.isSelected());
        return radioLabel;
    }

    @Step("Get expected new record values")
    public List<String> expectedNewRecordValues() {
        List<String> values = new ArrayList<>();
        values.add(emailInput.getValue());
        values.add(nameInput.getValue());
        values.add(genderDropDownField.text());
        values.add(getCheckBoxValue());
        values.add(getRadioButtonValue());
        return values;
    }

    @Step("Get check box text if check box is ticked")
    public String getCheckBoxValue() {
        String value;
        if (checkBoxVersion1.is(checked) && checkBoxVersion2.is(checked)) {
            value = "1.1, 1.2";
        } else if (checkBoxVersion1.is(checked)) {
            value = "1.1";
        } else if (checkBoxVersion2.is(checked)) {
            value = "1.2";
        } else {
            value = "Нет";
        }
        return value;
    }

    @Step
    public String getRadioButtonValue() {
        String value;
        if (radioButtonVersion1.isSelected()) {
            value = "2.1";
        } else if (radioButtonVersion2.isSelected()) {
            value = "2.2";
        } else if (radioButtonVersion3.isSelected()) {
            value = "2.3";
        } else {
            value = "Нет";
        }
        return value;
    }

    public static class ModalWindow {

        private final SelenideElement modalDialogWindow = $(".uk-modal-dialog");
        private final SelenideElement okButton = $(".uk-modal-close");

        public boolean isModalWindowDisplayed() {
            return modalDialogWindow.isDisplayed();
        }

        public SelenideElement getOkButton() {
            okButton.shouldBe(enabled);
            return okButton;
        }
    }
}
