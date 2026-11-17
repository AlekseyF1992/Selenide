package ru.netology;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.Test;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;


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

}