package handler;

import org.eclipse.core.commands.IHandler;

public class MenuDietaRicercaHandler extends
		MenuStudioDieteticoHandler implements IHandler {

	private static final String VIEW = "StudioDietetico.DietaTableView";
	private static final String MY_FUNCTION = "Ricerca Dieta";

	String getMyFunction() {
		return MY_FUNCTION;
	}

	String getMyView() {
		return VIEW;
	}

}