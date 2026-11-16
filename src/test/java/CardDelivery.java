package ru.netology;

import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.Test;

public class CardDelivery {

    @Test
    void shouldSuccessfulDeliveryRequest() {
        Selenide.open("http://localhost:9999/");
        
    }

}
