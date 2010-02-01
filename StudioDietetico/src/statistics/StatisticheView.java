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
public class StatisticheView extends ViewPart implements
		 ICommonConstants {

	public static final String ID_VIEW = statisticheViewID;
	private Color whiteColor = Utils.getStandardWhiteColor();
	private StatisticheMainForm form;

	/**
	 * This is a callback that will allow us to create the viewer and initialize
	 * it.
	 */
	public void createPartControl(Composite parent) {
		form = new StatisticheMainForm(parent, SWT.FILL);
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