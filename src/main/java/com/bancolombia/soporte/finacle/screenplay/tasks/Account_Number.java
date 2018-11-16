package com.bancolombia.soporte.finacle.screenplay.tasks;

import com.bancolombia.soporte.finacle.screenplay.userinterfaces.Interface_SaveNumberAccount;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;

public class Account_Number implements Task {

	private String number;
	private int i;

	public Account_Number(int i) {
		
		this.i = i;
	}

	@Override
	public <T extends Actor> void performAs(T actor) {
		
		number = Interface_SaveNumberAccount.NUMBER.resolveFor(actor).getText()
				.substring(7,20);
		
		System.out.println(number);
		
		actor.attemptsTo(Click.on(Interface_SaveNumberAccount.BTN_OK));
		
		
		
	}

	public static Account_Number saveAccount(int i) {

		return new Account_Number(i);

	}
 


	
	
}