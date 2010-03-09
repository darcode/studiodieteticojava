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
		if (Activator.IS_DEBUG)
			try {
				HandlerUtil.getActiveWorkbenchWindow(event).getActivePage()
						.showView(getMyView());
				return null;
			} catch (PartInitException e1) {
				e1.printStackTrace();
			}
		else {
			if (Activator.getUser() != null) {
				Utente user = Activator.getUser();
				try {
					if (UtenteDAO.canDo(user.getNomeUtente(), getMyFunction())) {
						HandlerUtil.getActiveWorkbenchWindow(event)
								.getActivePage().showView(getMyView());
						if (getMyFunction() == "MenuAnamnesi") {
							HandlerUtil.getActiveWorkbenchWindow(event)
							.getActivePage();
						}
					} else {
						Utils
								.showMessageError("Operazione non consentita per il tuo profilo.");
					}
					return null;
				} catch (PartInitException e) {
					throw new ExecutionException("Error while opening view", e);
				} catch (Exception e) {
					e.printStackTrace();
					return null;
				}
			} else {
				Utils.showMessageError("Effettuare la login.");

				return null;
			}
		}
		return event;
	}

	String getMyFunction() {
		return null;
	}

	String getMyView() {
		return null;
	}

}