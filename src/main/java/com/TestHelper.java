package com;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import com.FieldType.*;
import com.FieldInfo.*;
import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static org.openqa.selenium.By.xpath;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class TestHelper {

    @Step("click '{buttonName}'")
    public static void clickButton(SelenideElement buttonName) {
        buttonName.shouldBe(enabled).scrollTo();
        buttonName.click();
    }

    @Step("Filling '{field}' with '{value}'")
    public static void fillTextField(SelenideElement field, String value) {
        field.shouldBe(enabled).clear();
        field.sendKeys(value);
    }

    @Step("select '{value}'")
    public static void select(SelenideElement dropDown, String value) {
        SelenideElement option = dropDown.findAll("select > option").find(exactText(value));
        if (!option.isDisplayed()) {
            dropDown.click();
        }
        option.click();
    }

    @Step("Set {radioButtonNumber} radiobutton")
    public static void setRadioButtonAndCheckItIsSelected(int radioButtonNumber) {
        SelenideElement radioLabel = $("input#dataSelect2" + radioButtonNumber);
        radioLabel.scrollIntoView("{block: \"center\"}");
        radioLabel.shouldBe(enabled, Duration.ofMillis(5000)).click();
        assertTrue(radioLabel.isSelected());
    }

    @Step("Set {checkboxNumber} checkbox")
    public static void setCheckboxAndCheckItIsSelected(int checkboxNumber) {
        SelenideElement checkboxLabel = $("input#dataCheck1" + checkboxNumber);
        checkboxLabel.scrollIntoView("{block: \"center\"}");
        checkboxLabel.shouldBe(enabled, Duration.ofMillis(5000));
        if (checkboxLabel.is(not(checked))) {
            checkboxLabel.click();
        }
        assertTrue(checkboxLabel.isSelected());
    }

    @Step("Set {checkboxNumber} radiobutton")
    public static void unselectCheckboxAndCheckItIsNotSelected(int checkboxNumber) {
        SelenideElement checkboxLabel = $("input#dataCheck1" + checkboxNumber);
        checkboxLabel.scrollIntoView("{block: \"center\"}");
        checkboxLabel.shouldBe(enabled, Duration.ofMillis(5000));
        if (checkboxLabel.is(checked)) {
            checkboxLabel.click();
        }
        assertFalse(checkboxLabel.isSelected());
    }

    @Step("set '{field.name}' to '{value}'")
    private void setFieldValue(FieldType type, String value, SelenideElement field) {
        switch(type) {
            case TEXT_FIELD:
                fillTextField(field, value);
                break;
        }

    }
}
