package handler;

import org.eclipse.core.commands.IHandler;

import common.ICommonConstants;

public class MenuGraficiHandler extends MenuStudioDieteticoHandler implements
		IHandler {

	private static final String VIEW = ICommonConstants.graficiViewID;
	private static final String MY_FUNCTION = "Menu Statistiche Automatiche";

	String getMyFunction() {
		return MY_FUNCTION;
	}

	String getMyView() {
		return VIEW;
	}

}