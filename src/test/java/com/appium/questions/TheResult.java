package com.appium.questions;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.questions.Text;

import static com.appium.ui.CalculatorPage.RESULT;

public class TheResult implements Question<String> {

    private final String expected;

    public TheResult(String expected) {
        this.expected = expected;
    }

    public static TheResult equalsTo(String expected) {
        return new TheResult(expected);
    }

    @Override
    public String answeredBy(Actor actor) {
        return Text.of(RESULT).answeredBy(actor);
    }
}
