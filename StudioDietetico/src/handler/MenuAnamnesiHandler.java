package handler;


import org.eclipse.core.commands.IHandler;

public class MenuAnamnesiHandler extends MenuStudioDieteticoHandler implements IHandler {

	//static final String VIEW = "StudioDietetico.AnamnesiTableView";
	static final String VIEW = "StudioDietetico.PazienteTableView";
	static final String MY_FUNCTION = "Menu Anamnesi";

	String getMyFunction(){
		return MY_FUNCTION;
	}
	String getMyView(){
		return VIEW;
	}
	
}