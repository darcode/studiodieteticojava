package handler;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.handlers.HandlerUtil;

public class MenuParametroAntropometricoHandler extends AbstractHandler implements IHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		
		try {
			HandlerUtil.getActiveWorkbenchWindow(event)
		.getActivePage()
		.showView("StudioDietetico.ParametroAntropometricoView");
			} catch (PartInitException e) {
				throw new ExecutionException("Error while opening view", e);
			}
		return null;
	}

}