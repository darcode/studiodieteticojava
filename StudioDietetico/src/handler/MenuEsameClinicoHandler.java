package handler;

import org.eclipse.core.commands.IHandler;

import security.IFunzioniConstants;

public class MenuEsameClinicoHandler extends MenuStudioDieteticoHandler
		implements IHandler {
	private static final String VIEW = "StudioDietetico.EsameClinicoView";
	private static final String MY_FUNCTION = IFunzioniConstants.MENU_ESAME_CLINICO;

	String getMyFunction() {
		return MY_FUNCTION;
	}

	String getMyView() {
		return VIEW;
	}
}