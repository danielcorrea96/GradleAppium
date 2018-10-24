package com.accenture.GradleAppium.task;

import static net.serenitybdd.screenplay.Tasks.instrumented;

import com.accenture.GradleAppium.ui.Suma;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;


public class Calcular implements Task {
	
	
	@Override
	public <T extends Actor> void performAs(T actor) {
		// TODO Auto-generated method stub
		actor.attemptsTo(Click.on(Suma.BOTON_2));
		actor.attemptsTo(Click.on(Suma.BOTON_MAS));
		actor.attemptsTo(Click.on(Suma.BOTON_8));
		actor.attemptsTo(Click.on(Suma.BOTON_IGUAL));			
	}	
	
	public static Calcular theApp() {		
		return instrumented(Calcular.class);
	}
}
