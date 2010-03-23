package handler;

import org.eclipse.core.commands.IHandler;

import security.IFunzioniConstants;

public class MenuMedicoHandler extends MenuStudioDieteticoHandler implements
		IHandler {

	private static final String VIEW = "StudioDietetico.RegistraMedicoView";
	private static final String MY_FUNCTION = IFunzioniConstants.MENU_GESTIONE_MEDICI;

	String getMyFunction() {
		return MY_FUNCTION;
	}

	String getMyView() {
		return VIEW;
	}

}