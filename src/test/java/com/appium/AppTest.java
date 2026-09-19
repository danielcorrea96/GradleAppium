package com.appium;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.serenitybdd.screenplay.questions.Text;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.net.URL;
import java.time.Duration;

import static com.appium.tasks.PerformSum.performSum;
import static com.appium.ui.CalculatorPage.RESULT;
import static org.assertj.core.api.Assertions.assertThat;

@Tag("android")
public class AppTest {

    private AppiumDriver driver;
    private Actor actor;

    @BeforeEach
    void setUp() throws Exception {
        UiAutomator2Options options = new UiAutomator2Options()
            .setPlatformName("Android")
            .setDeviceName("Medium_Phone_API_36.0")
            .setAppPackage("com.google.android.calculator")
            .setAppActivity("com.android.calculator2.Calculator")
            .setAutoGrantPermissions(true)
            .setNoReset(false)
            .setNewCommandTimeout(Duration.ofSeconds(300));

        driver = new AndroidDriver(new URL("http://localhost:4723"), options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
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
