package ru.netology.web;


import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;


import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CardTest {

    private String firstMeetingDate;
    private String secondMeetingDate;

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @BeforeEach
    void setUpDates() {
        firstMeetingDate = Generate.generateDate(4);
        secondMeetingDate = Generate.generateDate(7);
    }


    @Test
    @DisplayName("Should successful plan and replan meeting")
    void shouldSuccessfulPlanAndReplanMeeting() {
        var validUser = Generate.Registration.generateUser("ru");
        $("[data-test-id='city'] input").setValue(validUser.getCity());
        $("[data-test-id='date'] input")
                .press(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE)
                .setValue(firstMeetingDate);
        $("[data-test-id='name'] input").setValue(validUser.getName());
        $("[data-test-id='phone'] input").setValue(validUser.getPhone());
        $("[data-test-id='agreement'] span").click();
        $$("button").find(text("Запланировать")).click();
        $(Selectors.withText("Успешно!")).should(Condition.visible);
        $("[data-test-id='success-notification'] .notification__content").should(Condition.text("Встреча успешно запланирована на " + firstMeetingDate)).should(Condition.visible);
        $("[data-test-id='date'] input")
                .press(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE)
                .setValue(secondMeetingDate);
        $$("button").find(text("Запланировать")).click();
        $("[data-test-id='replan-notification'] button").should(Condition.visible).click();
        $("[data-test-id='success-notification'] .notification__content").should(Condition.text("Встреча успешно запланирована на " + secondMeetingDate)).should(Condition.visible);
    }

    @Test
    @DisplayName("Negative City Test")
    void negativeCityTest() {
        var validUser = Generate.Registration.generateUser("ru");
        $("[data-test-id='date'] input")
                .press(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE)
                .setValue(firstMeetingDate);
        $("[data-test-id='name'] input").setValue(validUser.getName());
        $("[data-test-id='phone'] input").setValue(validUser.getPhone());
        $("[data-test-id='agreement'] span").click();
        $$("button").find(text("Запланировать")).click();
        $("[data-test-id='city'] .input__sub").should(Condition.text("Поле обязательно для заполнения")).should(Condition.visible);


    }

    @Test
    @DisplayName("Negative City Test with undefined city")
    void negativeCityTest2() {
        var validUser = Generate.Registration.generateUser("ru");
        $("[data-test-id='city'] input").setValue(validUser.getCity() + "aaaaa");
        $("[data-test-id='date'] input")
                .press(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE)
                .setValue(firstMeetingDate);
        $("[data-test-id='name'] input").setValue(validUser.getName());
        $("[data-test-id='phone'] input").setValue(validUser.getPhone());
        $("[data-test-id='agreement'] span").click();
        $$("button").find(text("Запланировать")).click();
        $("[data-test-id='city'] .input__sub").should(Condition.text("Доставка в выбранный город недоступна")).should(Condition.visible);
    }

    @Test
    @DisplayName("Negative Date Test")
    void negativeDateTest() {
        var validUser = Generate.Registration.generateUser("ru");
        $("[data-test-id='city'] input").setValue(validUser.getCity());
        $("[data-test-id='date'] input")
                .press(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='name'] input").setValue(validUser.getName());
        $("[data-test-id='phone'] input").setValue(validUser.getPhone());
        $("[data-test-id='agreement'] span").click();
        $$("button").find(text("Запланировать")).click();
        $("[data-test-id='date'] .input__inner .input__sub").should(Condition.text("Неверно введена дата")).should(Condition.visible);
    }

    @Test
    @DisplayName("Negative Name Test")
    void negativeNameTest() {
        var validUser = Generate.Registration.generateUser("ru");
        $("[data-test-id='city'] input").setValue(validUser.getCity());
        $("[data-test-id='date'] input")
                .press(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE)
                .setValue(firstMeetingDate);
        $("[data-test-id='phone'] input").setValue(validUser.getPhone());
        $("[data-test-id='agreement'] span").click();
        $$("button").find(text("Запланировать")).click();
        $("[data-test-id='name'] .input__sub").should(Condition.text("Поле обязательно для заполнения")).should(Condition.visible);
    }


    @Test
    @DisplayName("Negative Name Test for another language")
    void negativeNameTest2() {
        var validUser = Generate.Registration.generateUser("en");
        $("[data-test-id='city'] input").setValue(validUser.getCity());
        $("[data-test-id='date'] input")
                .press(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE)
                .setValue(firstMeetingDate);
        $("[data-test-id='name'] input").setValue(validUser.getName());
        $("[data-test-id='phone'] input").setValue(validUser.getPhone());
        $("[data-test-id='agreement'] span").click();
        $$("button").find(text("Запланировать")).click();
        $("[data-test-id='name'] .input__sub").should(Condition.text("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.")).should(Condition.visible);
    }

    @Test
    @DisplayName("Negative Phone Test")
    void negativePhoneTest() {
        var validUser = Generate.Registration.generateUser("ru");
        $("[data-test-id='city'] input").setValue(validUser.getCity());
        $("[data-test-id='date'] input")
                .press(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE)
                .setValue(firstMeetingDate);
        $("[data-test-id='name'] input").setValue(validUser.getName());
        $("[data-test-id='agreement'] span").click();
        $$("button").find(text("Запланировать")).click();
        $("[data-test-id='phone'] .input__sub").should(Condition.text("Поле обязательно для заполнения")).should(Condition.visible);
    }

    @Test
    @DisplayName("Negative Phone Test, only '+'")
    void negativePhoneTest2() {
        var invalidUser = Generate.Registration.generateUserError("ru");
        $("[data-test-id='city'] input").setValue(invalidUser.getCity());
        $("[data-test-id='date'] input")
                .press(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE)
                .setValue(firstMeetingDate);
        $("[data-test-id='name'] input").setValue(invalidUser.getName());
        $("[data-test-id='phone'] input").setValue(invalidUser.getPhone());
        $("[data-test-id='agreement'] span").click();
        $$("button").find(text("Запланировать")).click();
        $("[data-test-id='phone'] .input__sub").should(Condition.text("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.")).should(Condition.visible);
    }

    @Test
    @DisplayName("Should successful plan and replan meeting")
    void negativeCheckTest() {
        var validUser = Generate.Registration.generateUser("ru");
        $("[data-test-id='city'] input").setValue(validUser.getCity());
        $("[data-test-id='date'] input")
                .press(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE)
                .setValue(firstMeetingDate);
        $("[data-test-id='name'] input").setValue(validUser.getName());
        $("[data-test-id='phone'] input").setValue(validUser.getPhone());
        $$("button").find(text("Запланировать")).click();
        $(".input_invalid").should(Condition.visible);
    }
}