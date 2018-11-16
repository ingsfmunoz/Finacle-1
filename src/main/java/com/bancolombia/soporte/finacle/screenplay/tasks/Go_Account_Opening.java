package com.bancolombia.soporte.finacle.screenplay.tasks;
import java.util.List;
import java.util.Map;

import com.bancolombia.soporte.finacle.screenplay.userinterfaces.Interface_Account_Opening;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;

public class Go_Account_Opening implements Task {

	private String cifId;
	private String schemeCode;
	
	public Go_Account_Opening(List<Map<String, String>> dataAccountCDT, int i) {
		
		this.cifId = dataAccountCDT.get(i).get("cif_id");
		this.schemeCode = dataAccountCDT.get(i).get("scheme_Code");
	}

	@Override
	public <T extends Actor> void performAs(T actor) {
	
		actor.attemptsTo(Enter.theValue(cifId).into(Interface_Account_Opening.CIF_ID));
		actor.attemptsTo(Enter.theValue(schemeCode).into(Interface_Account_Opening.SCHEME_CODE));
		actor.attemptsTo(Click.on(Interface_Account_Opening.IR));
		
	}

	public static Go_Account_Opening solAccountOpening(List<Map<String, String>> dataAccountCDT, int i) {
		
		return new Go_Account_Opening(dataAccountCDT , i);
	}
	

}