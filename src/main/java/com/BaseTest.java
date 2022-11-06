package com;

import com.codeborne.selenide.Selenide;
import io.qameta.allure.Step;

public class BaseTest {

    private String LOGIN_PAGE_URL = "file:///C:/Users/vladi/Downloads/Telegram%20Desktop/qa-test.html";

    LoginPage loginPage = new LoginPage();
    AddUserPage addUserPage = new AddUserPage();
    AddUserPage.ModalWindow modalWindow = new AddUserPage.ModalWindow();

    @Step("open {url}")
    void openByUrl(String url) {
        Selenide.open(url);
    }

    public String getLOGIN_PAGE_URL() {
        return LOGIN_PAGE_URL;
    }

    public void setLOGIN_PAGE_URL(String LOGIN_PAGE_URL) {
        this.LOGIN_PAGE_URL = LOGIN_PAGE_URL;
    }
}
