package handler;

import org.eclipse.core.commands.IHandler;

import security.IFunzioniConstants;

public class MenuPrenotazioneRicercaHandler extends
		MenuStudioDieteticoHandler implements IHandler {

	private static final String VIEW = "StudioDietetico.PrenotazioneTableView";
	private static final String MY_FUNCTION = IFunzioniConstants.MENU_PRENOTAZIONI;

	String getMyFunction() {
		return MY_FUNCTION;
	}

	String getMyView() {
		return VIEW;
	}

}