package handler;

import org.eclipse.core.commands.IHandler;

public class MenuMedicoRicercaHandler extends
		MenuStudioDieteticoHandler implements IHandler {

	private static final String VIEW = "StudioDietetico.MedicoTableView";
	private static final String MY_FUNCTION = "Ricerca Medico";

	String getMyFunction() {
		return MY_FUNCTION;
	}

	String getMyView() {
		return VIEW;
	}

}