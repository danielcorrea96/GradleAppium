package com.appium;

import com.appium.config.DriverFactory;
import io.appium.java_client.AppiumDriver;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.serenitybdd.screenplay.questions.Text;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static com.appium.tasks.PerformSum.performSum;
import static com.appium.ui.CalculatorPage.RESULT;
import static org.assertj.core.api.Assertions.assertThat;

@Tag("android")
public class AppTest {

    private AppiumDriver driver;
    private Actor actor;

    @BeforeEach
    void setUp() throws Exception {
        driver = DriverFactory.createDriver();
        actor = Actor.named("Tester");
        actor.can(BrowseTheWeb.with(driver));
    }

    @Test
    void shouldCalculateSumCorrectly() {
        actor.attemptsTo(performSum());
        String result = Text.of(RESULT).answeredBy(actor);
        assertThat(result.trim()).contains("10");
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
