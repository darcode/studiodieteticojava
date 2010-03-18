package handler;

import hibernate.Utente;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.handlers.HandlerUtil;

import studiodietetico.Activator;
import studiodietetico.AnamnesiTTableView;

import command.UtenteDAO;
import common.Utils;

public abstract class MenuStudioDieteticoHandler extends AbstractHandler {

	public MenuStudioDieteticoHandler() {
		super();
	}

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
				try {
					HandlerUtil.getActiveWorkbenchWindow(event).getActivePage()
							.showView(getMyView());
				} catch (PartInitException e) {
					e.printStackTrace();
				}
		return event;
	}

	String getMyFunction() {
		return null;
	}

	String getMyView() {
		return null;
	}
	@Override
	public boolean isEnabled() {
			Utente user = Activator.getUser();
				try {
					if (UtenteDAO.canDo(user.getNomeUtente(), getMyFunction())) {
						return true;
					}
				} catch (Exception e) {
					e.printStackTrace();
					return false;
				}
				return false;
	}

}