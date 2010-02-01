package statistics;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

import common.ICommonConstants;
import common.Utils;

/**
 * 
 * @author Anna
 */
public class StatisticheGraficiView extends ViewPart implements
		 ICommonConstants {

	public static final String ID_VIEW = graficiViewID;
	private Color whiteColor = Utils.getStandardWhiteColor();
	private GraficiMainForm form;

	/**
	 * This is a callback that will allow us to create the viewer and initialize
	 * it.
	 */
	public void createPartControl(Composite parent) {
		form = new GraficiMainForm(parent, SWT.FILL);
	}

	
	@Override
	public void setFocus() {

	}

	@Override
	public void dispose() {
		form.dispose();
		super.dispose();
	}

}