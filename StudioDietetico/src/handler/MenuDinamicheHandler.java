package handler;

import org.eclipse.core.commands.IHandler;

import common.ICommonConstants;

public class MenuDinamicheHandler extends MenuStudioDieteticoHandler implements
		IHandler {

	private static final String VIEW = ICommonConstants.queryView;
	private static final String MY_FUNCTION = "Menu Query Dinamiche";

	String getMyFunction() {
		return MY_FUNCTION;
	}

	String getMyView() {
		return VIEW;
	}
}
