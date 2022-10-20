package order.test.delivery;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import org.openqa.selenium.Keys;

import java.time.Duration;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;
import static order.test.data.DataGen.*;

public class ReportTest {

    @BeforeAll
    static void setUpALl() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test
    @DisplayName("Should successful plan and replan meeting")
    void shouldSuccessfulPlanAndReplanMeeting() {
        $("[data-test-id=city] input").setValue(generateCity("ru"));
        $("[data-test-id=name] input").setValue(generateName("ru"));
        $("[data-test-id=phone] input").setValue(generatePhone("ru"));
        $("[data-test-id=agreement]").click();
        $("[data-test-id=date] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        String planningDate = generateDate(5);
        $("[data-test-id=date] input").setValue(planningDate);
        $$("button").find(exactText("Запланировать")).click();
        $(withText("Успешно!")).shouldBe(visible);
        $(".notification__content").shouldHave(Condition.text("Встреча успешно запланирована на " + planningDate), Duration.ofSeconds(15)).shouldBe(Condition.visible);
        $("[data-test-id=date] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        String secondDate = generateDate(7);
        $("[data-test-id=date] input").setValue(secondDate);
        $$("button").find(exactText("Запланировать")).click();
        $$("button").find(exactText("Перепланировать")).click();
        $(withText("Успешно!")).shouldBe(visible);
        $(".notification__content").shouldHave(Condition.text("Встреча успешно запланирована на " + secondDate), Duration.ofSeconds(15)).shouldBe(Condition.visible);
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }
}
