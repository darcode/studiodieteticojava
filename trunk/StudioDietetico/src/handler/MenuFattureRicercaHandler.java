package handler;

import org.eclipse.core.commands.IHandler;

import security.IFunzioniConstants;

public class MenuFattureRicercaHandler extends
		MenuStudioDieteticoHandler implements IHandler {

	private static final String VIEW = "StudioDietetico.FattureTableView";
	private static final String MY_FUNCTION = IFunzioniConstants.MENU_FATTURE;

	String getMyFunction() {
		return MY_FUNCTION;
	}

	String getMyView() {
		return VIEW;
	}

}