package handler;

import org.eclipse.core.commands.IHandler;

public class MenuMedicoHandler extends MenuStudioDieteticoHandler implements
		IHandler {

	private static final String VIEW = "StudioDietetico.RegistraMedicoView";
	private static final String MY_FUNCTION = "MenuMedico";

	String getMyFunction() {
		return MY_FUNCTION;
	}

	String getMyView() {
		return VIEW;
	}

}