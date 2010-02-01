package handler;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.internal.WorkbenchWindow;

import common.ICommonConstants;

public class MenuExitHandler extends AbstractHandler implements IHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		
		HandlerUtil.getActiveWorkbenchWindow(event).close();
			/*HandlerUtil.getActiveWorkbenchWindow(event)
		.getActivePage()
		.showView(ICommonConstants.statisticheViewID);
			} catch (PartInitException e) {
				throw new ExecutionException("Error while opening view", e);
			}
		*/
			//ActionFactory.QUIT.create(window);
		return null;
	}

}