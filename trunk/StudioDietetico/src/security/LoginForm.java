package security;

import grafici.FattureBarChart;
import grafici.FatturePieChart;
import grafici.FattureTimeSeriesChart;
import grafici.MediciBarChart;
import grafici.MediciPieChart;
import grafici.MediciTimeSeriesChart;
import grafici.PazientiBarChart;
import grafici.PazientiPieChart;
import grafici.PazientiTimeSeriesChart;
import grafici.PrenotazioneTimeSeriesChart;
import grafici.PrenotazioniBarChart;
import grafici.PrenotazioniPieChart;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import common.Utils;
import common.ui.ListComposite;

public class LoginForm extends ListComposite {

	public LoginForm(Composite parent, int style) {
		super(parent, style);

		GridData gdForm = new GridData(SWT.BORDER);
		gdForm.widthHint = 100;
		// gdForm.grabExcessHorizontalSpace = true;
		gdForm.horizontalAlignment = SWT.CENTER;
		gdForm.verticalAlignment = SWT.CENTER;
		this.setLayoutData(gdForm);
		GridLayout glForm = new GridLayout(2, false);
		this.setLayout(glForm);
		Color white = Utils.getStandardWhiteColor();
		this.setBackground(white);
		
		GridData gdLbl = new GridData(SWT.BORDER);
		gdLbl.grabExcessHorizontalSpace = true;
		gdLbl.verticalIndent = 100;
		gdLbl.verticalAlignment = SWT.CENTER;
		gdLbl.horizontalAlignment = SWT.CENTER;
		gdLbl.grabExcessVerticalSpace = true;

		Label lbl2 = new Label(this, SWT.NONE | SWT.BOLD);
		lbl2.setText("UTENTE");
		lbl2.setBackground(white);
		lbl2.setLayoutData(gdLbl);
		lbl2.setFont(Utils.getFont("Arial", 13, SWT.BOLD));
		Text utente = new Text(this, SWT.BORDER);
		GridData layoutData = new GridData(SWT.FILL);
		layoutData.verticalIndent = 100;
		layoutData.verticalAlignment = SWT.CENTER;
		layoutData.widthHint = 200;
		utente.setLayoutData(layoutData);
		Label lbl = new Label(this, SWT.NONE | SWT.BOLD);
		lbl.setText("PASSWORD");
		lbl.setBackground(white);
		lbl.setLayoutData(gdLbl);
		lbl.setFont(Utils.getFont("Arial", 13, SWT.BOLD));
		Text password = new Text(this, SWT.PASSWORD| SWT.BORDER);
		password.setLayoutData(layoutData);
	}
}
