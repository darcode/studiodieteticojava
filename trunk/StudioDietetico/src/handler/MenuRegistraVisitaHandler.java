package handler;

import org.eclipse.core.commands.IHandler;

public class MenuRegistraVisitaHandler extends MenuStudioDieteticoHandler
		implements IHandler {

	private static final String VIEW = "StudioDietetico.RegistraVisitaView";
	private static final String MY_FUNCTION = "Registrazione Visita";

	String getMyFunction() {
		return MY_FUNCTION;
	}

	String getMyView() {
		return VIEW;
	}

}