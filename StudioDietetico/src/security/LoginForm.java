package security;

import hibernate.Utente;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.commands.ICommandService;
import org.eclipse.ui.part.WorkbenchPart;

import studiodietetico.Activator;
import antlr.debug.Event;

import command.UtenteDAO;
import common.Utils;
import common.ui.ListComposite;

public class LoginForm extends ListComposite {

	private static final Font font = Utils.getFont("Arial", 13, SWT.BOLD);
	private Text password;
	private Text utente;
	public LoginForm(Composite parent, int style) {
		super(parent, style);

		GridData gdForm = new GridData(SWT.BORDER);
		gdForm.grabExcessVerticalSpace = true;
		gdForm.grabExcessHorizontalSpace = true;
		gdForm.horizontalAlignment = SWT.CENTER;
		gdForm.verticalAlignment = SWT.CENTER;
		this.setLayoutData(gdForm);
		GridLayout glForm = new GridLayout(2, true);
		this.setLayout(glForm);
		Color white = Utils.getStandardWhiteColor();
		this.setBackground(white);
		Composite cmp = new Composite(this, SWT.NONE);
		GridData gdCmp = new GridData(SWT.BORDER);
		gdCmp.grabExcessHorizontalSpace = true;
		gdCmp.grabExcessVerticalSpace = true;
		gdCmp.horizontalAlignment = SWT.CENTER;
		gdCmp.verticalAlignment = SWT.CENTER;
		cmp.setLayoutData(gdCmp);
		// cmp.setLayoutData(new GridData());
		cmp.setLayout(new GridLayout(2, true));
		cmp.setBackground(white);
		GridData gdLbl = new GridData(SWT.BORDER);
		gdLbl.grabExcessHorizontalSpace = true;
		gdLbl.verticalIndent = 100;
		gdLbl.verticalAlignment = SWT.FILL;
		gdLbl.horizontalAlignment = SWT.FILL;
		gdLbl.grabExcessVerticalSpace = true;

		Label lbl2 = new Label(cmp, SWT.NONE | SWT.BOLD);
		lbl2.setText("Utente");
		lbl2.setBackground(white);
		lbl2.setLayoutData(gdLbl);
		lbl2.setFont(font);
		utente = new Text(cmp, SWT.BORDER);
		utente.setFont(font);
		GridData layoutData = new GridData(SWT.FILL);
		layoutData.verticalIndent = 100;
		layoutData.widthHint = 200;
		layoutData.verticalAlignment = SWT.CENTER;
		utente.setLayoutData(layoutData);
		Label lbl = new Label(cmp, SWT.NONE | SWT.BOLD);
		lbl.setText("Password");
		lbl.setBackground(white);
		lbl.setLayoutData(gdLbl);
		lbl.setFont(font);
		password = new Text(cmp, SWT.PASSWORD| SWT.BORDER);
		password.setLayoutData(layoutData);
		password.setFont(font);
		Button entra = new Button(cmp, SWT.NONE);
		entra.setText("Entra");
		entra.setFont(font);
		entra.setLayoutData(layoutData);
		entra.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
			abilita(utente.getText().trim(), password.getText().trim());	
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				
			}
		});
	}
	private void abilita(String username, String password){
		Utente ute = UtenteDAO.get(username, password);
		if( ute != null){
			Activator.setUser(ute);
			MessageBox msg = new MessageBox(new Shell());
			msg.setMessage("Benvenuto "+ username);
			msg.open();
		}else{
			MessageBox msg = new MessageBox(new Shell());
			msg.setMessage("Nome utente o password non corrette");
			msg.open();
		}
	}
}
