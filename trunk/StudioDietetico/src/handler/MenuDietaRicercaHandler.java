package handler;

import org.eclipse.core.commands.IHandler;

import security.IFunzioniConstants;

public class MenuDietaRicercaHandler extends
		MenuStudioDieteticoHandler implements IHandler {

	private static final String VIEW = "StudioDietetico.DietaTableView";
	private static final String MY_FUNCTION = IFunzioniConstants.GESTIONE_DIETA;

	String getMyFunction() {
		return MY_FUNCTION;
	}

	String getMyView() {
		return VIEW;
	}

}