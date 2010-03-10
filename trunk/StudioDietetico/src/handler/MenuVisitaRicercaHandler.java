package handler;

import org.eclipse.core.commands.IHandler;

public class MenuVisitaRicercaHandler extends
		MenuStudioDieteticoHandler implements IHandler {

	private static final String VIEW = "StudioDietetico.VisitaTableView";
	private static final String MY_FUNCTION = "MenuVisitaRicerca";

	String getMyFunction() {
		return MY_FUNCTION;
	}

	String getMyView() {
		return VIEW;
	}

}