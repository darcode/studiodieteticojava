package handler;

import org.eclipse.core.commands.IHandler;

public class MenuPazienteHandler extends MenuStudioDieteticoHandler implements
		IHandler {

	private static final String VIEW = "StudioDietetico.RegistraPazienteView";
	private static final String MY_FUNCTION = "MenuPaziente";

	String getMyFunction() {
		return MY_FUNCTION;
	}

	String getMyView() {
		return VIEW;
	}

}