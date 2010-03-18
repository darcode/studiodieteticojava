package handler;

import org.eclipse.core.commands.IHandler;

public class MenuRisultatoAnalisiHandler extends MenuStudioDieteticoHandler
		implements IHandler {

	//private static final String VIEW = "StudioDietetico.RisultatoAnalisiView";
	private static final String VIEW = "StudioDietetico.PazienteTableView";
	private static final String MY_FUNCTION = "Risultato Analisi";

	String getMyFunction() {
		return MY_FUNCTION;
	}

	String getMyView() {
		return VIEW;
	}

}