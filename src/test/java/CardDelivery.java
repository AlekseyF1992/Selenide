package ru.netology;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static org.openqa.selenium.remote.tracing.EventAttribute.setValue;

public class CardDelivery {

    public String generateDate(int days, String pattern) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern(pattern));
    }

    @Test
    void shouldSuccessfulDeliveryRequest() {

        String planning = generateDate(3, "dd.MM.yyyy");


        Selenide.open("http://localhost:9999/");

        //SelenideElement form = $$("form").find(Condition.visible);
        $("[data-test-id='city'] input").setValue("Саратов");
        $("[data-test-id='date'] input").clear();
        $("[data-test-id='date'] input").setValue(planning);
        $("[data-test-id='name'] input").setValue("Иванов Степан");
        $("[data-test-id='phone'] input").setValue("+79210001111");
        $("[data-test-id='agreement']").click();
        $$("button").findBy(Condition.exactText("Забронировать")).click();
        $$("[data-test-id='notification'] .notification__content");

        String expectedText = "Встреча успешно забронирована на " + planning;
        $$("[data-test-id='notification'] .notification__content")
                .findBy(Condition.visible)
                .shouldHave(Condition.text(expectedText), Duration.ofSeconds(15));
    }

    @Test
    void shouldCheckingTheDropDownList() {
        LocalDate today = LocalDate.now();
        LocalDate targetDate = today.plusDays(7);

        String planning = targetDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        String day = targetDate.format(DateTimeFormatter.ofPattern("d"));

        long monthsToAdvance = (targetDate.getYear() - today.getYear()) * 12L
                + (targetDate.getMonthValue() - today.getMonthValue());

        Selenide.open("http://localhost:9999/");

        $("[data-test-id='city'] input").setValue("Ка");
        $$(".menu-item__control") .findBy(Condition.exactText("Казань")).click();

        $("[data-test-id='date'] input").click();
        for (long i = 0; i < monthsToAdvance; i++) {
            $$(".calendar__arrow_direction_right:not(.calendar__arrow_double)").first().click();
        }

        $$(".calendar__day:not(.calendar__day_type_off)").findBy(Condition.exactText(day)).click();

        $("[data-test-id='name'] input").setValue("Иванов Степан");
        $("[data-test-id='phone'] input").setValue("+79210001111");
        $("[data-test-id='agreement']").click();
        $$("button").findBy(Condition.exactText("Забронировать")).click();

        String expectedText = "Встреча успешно забронирована на " + planning;
        $$("[data-test-id='notification'] .notification__content")
                .findBy(Condition.visible)
                .shouldHave(Condition.text(expectedText), Duration.ofSeconds(15));
    }
}