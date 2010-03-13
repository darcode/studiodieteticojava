package common;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.action.IStatusLineManager;
import org.eclipse.jface.action.StatusLineContributionItem;
import org.eclipse.ui.IViewReference;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;

import studiodietetico.Activator;

/**
 * Static methods that send messages to the user via the status line
 * and write messages to the RCP log.
 *
 */

public class Messages {
   

    /**
     * Set status line of RCP with this message, will not replace
     * error status.
     * @param message String to be placed on status line, set to
     *            null to clear status message. 
     */
	public static void setStatusline(final String message) {

		Activator.getDefault().getWorkbench().getDisplay().asyncExec(new Runnable() {

			public void run() {
				IWorkbench workbench = PlatformUI.getWorkbench();
				IViewReference[] views = workbench.getActiveWorkbenchWindow().getActivePage().getViewReferences();
				int card = views.length;
				for(int i=0;i<card;i++){
					IStatusLineManager mgr = views[i].getView(false).getViewSite().getActionBars().getStatusLineManager();
					StatusLineContributionItem statusLine = (StatusLineContributionItem)(mgr.find(ICommonConstants.messageBarId));
					statusLine.setText(message);
					statusLine.setVisible(true);
				}

			}

		});

	}
    
    /**
     * Clear status line of RCP.
     */
	public static void clearStatusline() {

		Activator.getDefault().getWorkbench().getDisplay().asyncExec(new Runnable() {

			public void run() {
				IWorkbench workbench = PlatformUI.getWorkbench();
				IViewReference[] views = workbench.getActiveWorkbenchWindow().getActivePage().getViewReferences();
				int card = views.length;
				for(int i=0;i<card;i++){
					IStatusLineManager mgr = views[i].getView(false).getViewSite().getActionBars().getStatusLineManager();
					StatusLineContributionItem statusLine = (StatusLineContributionItem)(mgr.find(ICommonConstants.messageBarId));
					statusLine.setText(null);
					statusLine.setVisible(false);
				}

			}

		});

	}

 

    /**
     * Set error status line of RCP with this message.
     * @param message String to be placed on status line, set to
     *            null to clear status message. 
     */
	public static void setErrorStatusline(final String message) {

		Activator.getDefault().getWorkbench().getDisplay().asyncExec(new Runnable() {

			public void run() {
				IWorkbench workbench = PlatformUI.getWorkbench();
				IViewReference[] views = workbench.getActiveWorkbenchWindow().getActivePage().getViewReferences();
				int card = views.length;
				for(int i=0;i<card;i++)
					views[i].getView(false).getViewSite().getActionBars().getStatusLineManager().setErrorMessage(message);
			}

		});

	}
	
    /**
     * Write message to log.
     * @param severity One of these values<ul>
     * <li>IStatus.OK</li>
     * <li>IStatus.INFO</li>
     * <li>IStatus.WARNING</li>
     * <li>IStatus.ERROR</li>
     * <li>IStatus.CANCEL</li></ul>
     * @param pluginId Identify plugin or class which wrote the log entry.
     * @param message Message to log.
     */
    public static void logEntry(int severity, String pluginId, String message) {

    	Activator.getDefault().getLog().log((IStatus)new Status(severity, pluginId, 0, message, null) );

      }

 

    /**
     * Write message to log.
     * @param severity One of these values<ul>
     * <li>IStatus.OK</li>
     * <li>IStatus.INFO</li>
     * <li>IStatus.WARNING</li>
     * <li>IStatus.ERROR</li>
     * <li>IStatus.CANCEL</li></ul>
     * @param pluginId Identify plugin or class which wrote the log entry.
     * @param message Message to log.
     * @param exception Thowable if applicable (trace information will be
     *            logged) otherwise null.
     */
    public static void logEntry(int severity, String pluginId, String message, Throwable exception) {

    	Activator.getDefault().getLog().log((IStatus)new Status(severity, pluginId, 0, message, exception) );
      }
    

}
