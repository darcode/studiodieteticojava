package handler;

import org.eclipse.core.commands.IHandler;

public class MenuRilevazioneParametroAntroHandler extends
		MenuStudioDieteticoHandler implements IHandler {

	//private static final String VIEW = "StudioDietetico.RilevazioneParametroAntroView";
	private static final String VIEW = "StudioDietetico.PazienteTableView";
	private static final String MY_FUNCTION = "Rilevazione Parametro Antropometrico";

	String getMyFunction() {
		return MY_FUNCTION;
	}

	String getMyView() {
		return VIEW;
	}
}