package handler;

import org.eclipse.core.commands.IHandler;

public class MenuFattureRicercaHandler extends
		MenuStudioDieteticoHandler implements IHandler {

	private static final String VIEW = "StudioDietetico.FattureTableView";
	private static final String MY_FUNCTION = "";

	String getMyFunction() {
		return MY_FUNCTION;
	}

	String getMyView() {
		return VIEW;
	}

}