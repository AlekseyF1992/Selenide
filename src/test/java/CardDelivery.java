package ru.netology;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static org.openqa.selenium.remote.tracing.EventAttribute.setValue;

public class CardDelivery {

    public String generateDate(int days, String pattern) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern(pattern));
    }

    @Test
    void shouldSuccessfulDeliveryRequest() {

        String planning = generateDate(7, "dd.MM.yyyy");
        String day = generateDate(7, "d");

        Selenide.open("http://localhost:9999/");

        $("[data-test-id='city'] input").setValue("Ка");
        $$(".menu-item__control") .findBy(Condition.exactText("Казань")).click();

        $("[data-test-id='date'] input").click();
        $$(".calendar__day").findBy(Condition.exactText(day)).click();

        $("[data-test-id='name'] input").setValue("Иванов Степан");
        $("[data-test-id='phone'] input").setValue("+79210001111");
        $("[data-test-id='agreement']").click();
        $$("button").findBy(Condition.exactText("Забронировать")).click();

        $$("[data-test-id='notification'] .notification__content")
                .findBy(Condition.visible)
                .shouldHave(Condition.text("Встреча успешно забронирована на "), Duration.ofSeconds(15))
                .shouldHave(Condition.text(planning), Duration.ofSeconds(15));
    }
}