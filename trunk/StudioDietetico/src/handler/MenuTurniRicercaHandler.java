package handler;

import org.eclipse.core.commands.IHandler;

public class MenuTurniRicercaHandler extends
		MenuStudioDieteticoHandler implements IHandler {

	private static final String VIEW = "StudioDietetico.TurniTableView";
	private static final String MY_FUNCTION = "Gestione Turni";

	String getMyFunction() {
		return MY_FUNCTION;
	}

	String getMyView() {
		return VIEW;
	}

}