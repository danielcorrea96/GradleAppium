package com.appium.tasks;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;

import static com.appium.ui.CalculatorPage.BUTTON_2;
import static com.appium.ui.CalculatorPage.BUTTON_PLUS;
import static com.appium.ui.CalculatorPage.BUTTON_8;
import static com.appium.ui.CalculatorPage.BUTTON_EQUALS;

public class PerformSum implements Task {

    public static PerformSum performSum() {
        return new PerformSum();
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
            Click.on(BUTTON_2),
            Click.on(BUTTON_PLUS),
            Click.on(BUTTON_8),
            Click.on(BUTTON_EQUALS)
        );
    }
}
