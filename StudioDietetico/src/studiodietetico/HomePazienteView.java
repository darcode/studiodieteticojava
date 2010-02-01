package studiodietetico;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

import common.Utils;

import forms.HomePazienteForm;

public class HomePazienteView extends ViewPart {

	private HomePazienteForm form;

	public HomePazienteView() {
	}

	@Override
	public void createPartControl(Composite parent) {
		form = new HomePazienteForm(parent, SWT.BORDER);
		form.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		form.setLayout(new GridLayout(1, true));
		form.setBackground(Utils.getStandardWhiteColor());
	}

	@Override
	public void setFocus() {
		form.setFocus();
	}

} // @jve:decl-index=0:visual-constraint="10,10,518,282"
