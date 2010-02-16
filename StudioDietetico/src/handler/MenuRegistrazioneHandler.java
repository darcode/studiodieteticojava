package handler;

import org.eclipse.core.commands.IHandler;

import common.ICommonConstants;

public class MenuRegistrazioneHandler extends MenuStudioDieteticoHandler
		implements IHandler {

	private static final String VIEW = ICommonConstants.registrazioneViewID;
	private static final String MY_FUNCTION = "MenuRegistrazione";

	String getMyFunction() {
		return MY_FUNCTION;
	}

	String getMyView() {
		return VIEW;
	}

}