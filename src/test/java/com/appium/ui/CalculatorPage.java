package com.appium.ui;

import net.serenitybdd.screenplay.targets.Target;

public class CalculatorPage {

    public static final Target BUTTON_2 = Target.the("Button 2")
        .locatedBy("//*[@resource-id='com.google.android.calculator:id/digit_2']");
    public static final Target BUTTON_PLUS = Target.the("Plus button")
        .locatedBy("//*[@resource-id='com.google.android.calculator:id/op_add']");
    public static final Target BUTTON_8 = Target.the("Button 8")
        .locatedBy("//*[@resource-id='com.google.android.calculator:id/digit_8']");
    public static final Target BUTTON_EQUALS = Target.the("Equals button")
        .locatedBy("//*[@resource-id='com.google.android.calculator:id/eq']");
    public static final Target RESULT = Target.the("Result field")
        .locatedBy("//*[@resource-id='com.google.android.calculator:id/result_final']");
}
