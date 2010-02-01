package studiodietetico;

import java.awt.Dimension;
import java.awt.Toolkit;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;
import org.eclipse.ui.application.IWorkbenchWindowConfigurer;
import org.eclipse.ui.application.WorkbenchWindowAdvisor;

public class ApplicationWorkbenchWindowAdvisor extends WorkbenchWindowAdvisor {

	public ApplicationWorkbenchWindowAdvisor(
			IWorkbenchWindowConfigurer configurer) {
		super(configurer);
	}

	public ActionBarAdvisor createActionBarAdvisor(
			IActionBarConfigurer configurer) {
		return new ApplicationActionBarAdvisor(configurer);
	}

	public void preWindowOpen() {
		/*
		 * IWorkbenchWindowConfigurer configurer = getWindowConfigurer();
		 * configurer.setShellStyle(SWT.BORDER);
		 * configurer.setShowStatusLine(true); //
		 * configurer.setShellStyle(SWT.NONE);
		 * 
		 * configurer.setShowMenuBar(true);
		 * configurer.setTitle("Studio Dietetico");
		 */
		IWorkbenchWindowConfigurer configurer = getWindowConfigurer();
		configurer.setInitialSize(new Point(1200,800));
		configurer.setShowCoolBar(true);
		configurer.setShowStatusLine(false);
		configurer.setShowMenuBar(true);
		configurer.setTitle("Studio Dietetico");
	}

}
