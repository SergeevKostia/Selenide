import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class CardDeliveryTest {

    public String generateDate(long addDays, String pattern) {
        return LocalDate.now().plusDays(addDays).format(DateTimeFormatter.ofPattern(pattern));
    }

    String planningDate = generateDate(4, "dd.MM.yyyy");

    @Test
    void shouldMakeOrderDeliveryOfCard() {
        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue("Волгоград");
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME,Keys.DELETE));
        $("[data-test-id=date] input").setValue(planningDate);
        $("[data-test-id=name] input").setValue("Петров-Водкин Федор");
        $("[data-test-id=phone] input").setValue("+79270000000");
        $("[data-test-id=agreement]").click();
        $$("button").find(exactText("Забронировать")).click();
        $(byText("Успешно!")).shouldBe(visible, Duration.ofMillis(15000));
        $(".notification__content").shouldHave(text("Встреча успешно забронирована на "
                        + planningDate),Duration.ofSeconds(15))
                .shouldBe(visible);

    }
}