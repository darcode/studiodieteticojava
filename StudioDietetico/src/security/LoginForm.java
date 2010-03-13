package security;

import java.io.IOException;
import java.io.InputStream;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import studiodietetico.Activator;

import common.Utils;
import common.ui.ListComposite;

public class LoginForm extends ListComposite {

	private static final Font font = Utils.getFont("Arial", 13, SWT.BOLD);
	private static final Font fontTitle = Utils.getFont("Arial", 14, SWT.BOLD);
	private Text password;
	private Text utente;

	public LoginForm(Composite parent, int style) {
		super(parent, style);

		GridData gdForm = new GridData(SWT.BORDER);
		gdForm.horizontalAlignment = SWT.CENTER;
		gdForm.verticalAlignment = SWT.CENTER;
		this.setLayoutData(gdForm);
		GridLayout glForm = new GridLayout(1, true);
		this.setLayout(glForm);
		Color white = Utils.getStandardWhiteColor();
		this.setBackground(white);
		Label label = new Label(this, SWT.NONE);
		label.setBackground(Utils.getStandardWhiteColor());
		GridData gdLabel = new GridData();
		gdLabel.horizontalAlignment = SWT.CENTER;
		gdLabel.grabExcessHorizontalSpace = true;
		gdLabel.heightHint = 100;
		label.setLayoutData(gdLabel);
		label.setFont(font);
		label.setText("Benvenuto " + Activator.getUser().getNomeUtente());
		Composite cmp = new Composite(this, SWT.BORDER);
		GridData gdCmp = new GridData(SWT.BORDER);
		gdCmp.heightHint = 400;
		gdCmp.widthHint = 400;
		gdCmp.horizontalAlignment = SWT.CENTER;
		gdCmp.verticalAlignment = SWT.CENTER;
		cmp.setLayoutData(gdCmp);
		// cmp.setLayoutData(new GridData());
		cmp.setLayout(new GridLayout(1, false));
		cmp.setBackground(white);
		Image imageFromFile = Utils.getImageFromFile("icons/logo.jpg");
		imageFromFile.getImageData().scaledTo(400, 400);
		cmp.setBackgroundImage(imageFromFile);

	}
}
