package handler;

import org.eclipse.core.commands.IHandler;

import common.ICommonConstants;

public class MenuStatisticheHandler extends MenuStudioDieteticoHandler
		implements IHandler {

	private static final String VIEW = ICommonConstants.statisticheViewID;
	private static final String MY_FUNCTION = "MenuStatisticheHandler";

	String getMyFunction() {
		return MY_FUNCTION;
	}

	String getMyView() {
		return VIEW;
	}
}