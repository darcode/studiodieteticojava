package handler;

import org.eclipse.core.commands.IHandler;

public class MenuPazienteRicercaHandler extends
		MenuStudioDieteticoHandler implements IHandler {

	private static final String VIEW = "StudioDietetico.PazienteTableView";
	private static final String MY_FUNCTION = "MenuPazienteRicerca";

	String getMyFunction() {
		return MY_FUNCTION;
	}

	String getMyView() {
		return VIEW;
	}

}