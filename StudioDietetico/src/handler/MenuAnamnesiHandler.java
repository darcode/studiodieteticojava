package handler;


import org.eclipse.core.commands.IHandler;

public class MenuAnamnesiHandler extends MenuStudioDieteticoHandler implements IHandler {

	static final String VIEW = "StudioDietetico.ProvaAnamnesiView";
	static final String MY_FUNCTION = "MenuAnamnesi";

	String getMyFunction(){
		return MY_FUNCTION;
	}
	String getMyView(){
		return VIEW;
	}
}