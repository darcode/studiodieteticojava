package studiodietetico;

import java.awt.Frame;

import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;

/**
 * This class controls all aspects of the application's execution
 */
public class Application implements IApplication {

	/* (non-Javadoc)
	 * @see org.eclipse.equinox.app.IApplication#start(org.eclipse.equinox.app.IApplicationContext)
	 */
	public Object start(IApplicationContext context) {
		
		//Login maschera = new Login();
		//LoginMaschera maschera = new LoginMaschera();
		LoginDialog maschera = new LoginDialog(new Frame());
		maschera.setLocation(400, 400);
		maschera.setVisible(true);
		
		//maschera.setAlwaysOnTop(true);
		//maschera.setLocationByPlatform(true);
		//maschera.show();
		//maschera.
		if(maschera.risultato == 1) {
			maschera.dispose();
			Display display = PlatformUI.createDisplay();
			try {
				int returnCode = PlatformUI.createAndRunWorkbench(display, new ApplicationWorkbenchAdvisor());
				if (returnCode == PlatformUI.RETURN_RESTART) {
					return IApplication.EXIT_RESTART;
				}
				return IApplication.EXIT_OK;
			} finally {
				display.dispose();
			}
		}
		else{
			return IApplication.EXIT_RESTART;
		}
		
	}

	/* (non-Javadoc)
	 * @see org.eclipse.equinox.app.IApplication#stop()
	 */
	public void stop() {
		final IWorkbench workbench = PlatformUI.getWorkbench();
		if (workbench == null)
			return;
		final Display display = workbench.getDisplay();
		display.syncExec(new Runnable() {
			public void run() {
				if (!display.isDisposed())
					workbench.close();
			}
		});
	}
}

