package com;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import java.util.List;
import static org.testng.Assert.assertTrue;

public interface BasePage {

    @Step("check fields '{fields}' are displayed")
    static void checkFieldsAreDisplayed(List<SelenideElement> fields) {
        for (SelenideElement field : fields) {
            assertTrue(field.isDisplayed());
        }
    }
}
