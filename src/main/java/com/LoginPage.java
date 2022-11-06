package com;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import java.util.List;
import static com.TestHelper.clickButton;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class LoginPage implements BasePage {

    private final SelenideElement emailInput = $("input#loginEmail");
    private final SelenideElement passwordInput = $("input#loginPassword");
    private final SelenideElement emailFormatError = $("div#emailFormatError");
    private final SelenideElement emailFormatErrorXButton = $("button#authButton");
    private final SelenideElement enterButton = $("button#authButton");
    private final SelenideElement loginEmailTitle = $(By.xpath("//label[@for='loginEmail' and text()='E-Mail:']"));
    private final SelenideElement loginPasswordTitle = $(By.xpath("//label[@for='loginPassword' and text()='Пароль:']"));

    private final List<SelenideElement> keyFields = List.of(emailInput, passwordInput, enterButton, loginEmailTitle,
            loginPasswordTitle);

    public void setEmailInput(String email) {
        emailInput.clear();
        emailInput.sendKeys(email);
    }

    public void setPasswordInput(String password) {
        passwordInput.clear();
        passwordInput.sendKeys(password);
    }

    public void login(String email, String password) {
        setEmailInput(email);
        setPasswordInput(password);
        clickButton(enterButton);
    }

    public String getEmailFormatErrorText() {
        emailFormatError.shouldBe(visible);
        return emailFormatError.text();
    }

    public void closeErrorMessage() {
        emailFormatError.shouldBe(visible);
        clickButton(emailFormatErrorXButton);
    }

    public List<SelenideElement> getKeyFields() {
        return this.keyFields;
    }
}
