package com.accenture.GradleAppium.ui;




import net.serenitybdd.core.pages.PageObject;

import net.serenitybdd.screenplay.targets.Target;

public class Suma extends PageObject {
	
	 public static Target BOTON_2 = Target.the("Botón 2").locatedBy("//android.widget.LinearLayout[@content-desc=\"Números y operaciones básicas\"]/android.view.ViewGroup[1]/android.widget.Button[8]");
	 public static Target BOTON_MAS = Target.the("Boton más").locatedBy("//android.widget.Button[@content-desc=\"más\"]");	 
	 public static Target BOTON_8 = Target.the("Boton 8").locatedBy("//android.widget.LinearLayout[@content-desc=\"Números y operaciones básicas\"]/android.view.ViewGroup[1]/android.widget.Button[2]");
	 public static Target BOTON_IGUAL = Target.the("Boton igual").locatedBy("//android.widget.Button[@content-desc=\"igual a\"]");

	
}
