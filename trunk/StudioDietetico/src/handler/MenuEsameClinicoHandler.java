package handler;

import org.eclipse.core.commands.IHandler;

public class MenuEsameClinicoHandler extends MenuStudioDieteticoHandler
		implements IHandler {
	private static final String VIEW = "StudioDietetico.EsameClinicoView";
	private static final String MY_FUNCTION = "Menu Esame Clinico";

	String getMyFunction() {
		return MY_FUNCTION;
	}

	String getMyView() {
		return VIEW;
	}
}