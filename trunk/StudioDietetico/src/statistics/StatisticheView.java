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
//	private StatisticheMainForm form;
	private QueryStatisticheForm form;

	/**
	 * This is a callback that will allow us to create the viewer and initialize
	 * it.
	 */
	public void createPartControl(Composite parent) {
//		form = new StatisticheMainForm(parent, SWT.FILL);
		Composite top = new Composite(parent, SWT.BORDER);
		GridData gdTop = new GridData();
		gdTop.horizontalAlignment = SWT.FILL;
		gdTop.verticalAlignment = SWT.FILL;
		gdTop.grabExcessHorizontalSpace = true;
		gdTop.grabExcessVerticalSpace = true;
		top.setLayoutData(gdTop);
		GridLayout glTop = new GridLayout();
		glTop.numColumns = 1;
		top.setLayout(glTop);
		form = new QueryStatisticheForm(top, SWT.BORDER);
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