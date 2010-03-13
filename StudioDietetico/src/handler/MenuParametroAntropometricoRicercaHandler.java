package handler;

import org.eclipse.core.commands.IHandler;

public class MenuParametroAntropometricoRicercaHandler extends MenuStudioDieteticoHandler
		implements IHandler {
	private static final String VIEW = "StudioDietetico.ParametroAntropometricoTableView";
	private static final String MY_FUNCTION = "MenuParametroAntropometricoRicerca";

	String getMyFunction() {
		return MY_FUNCTION;
	}

	String getMyView() {
		return VIEW;
	}
}