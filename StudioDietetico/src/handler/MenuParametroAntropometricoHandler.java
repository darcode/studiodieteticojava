package handler;

import org.eclipse.core.commands.IHandler;

public class MenuParametroAntropometricoHandler extends
		MenuStudioDieteticoHandler implements IHandler {

	private static final String VIEW = "StudioDietetico.ParametroAntropometricoView";
	private static final String MY_FUNCTION = "Gestione Parametri Antropometrici";

	String getMyFunction() {
		return MY_FUNCTION;
	}

	String getMyView() {
		return VIEW;
	}

}