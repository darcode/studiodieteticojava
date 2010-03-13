package handler;

import org.eclipse.core.commands.IHandler;

public class MenuEsameClinicoRicercaHandler extends
		MenuStudioDieteticoHandler implements IHandler {

	private static final String VIEW = "StudioDietetico.EsameClinicoTableView";
	private static final String MY_FUNCTION = "MenuEsameClinicoRicerca";

	String getMyFunction() {
		return MY_FUNCTION;
	}

	String getMyView() {
		return VIEW;
	}

}