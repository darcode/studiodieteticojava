package security;

import hibernate.Funzione;
import hibernate.Ruolo;
import hibernate.Utente;

import java.util.Set;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

import command.RuoloDAO;
import command.UtenteDAO;
import common.Utils;
import common.ui.ListComposite;

public class RegistrazioneForm extends ListComposite {

	private static final Font font = Utils.getFont("Arial", 8, SWT.BOLD);
	private static final Font fontTitle = Utils.getFont("Arial", 12, SWT.BOLD);
	Color white = Utils.getStandardWhiteColor();
	private Text password;
	private Text utente;
	private Combo profilo;
	private Composite cmp;
	private Table tblUtentiRuoli;
	private Combo cboRuoli;
	private Table tblRuoli;
	private Table tblRuoliEsistenti;
	private Table tblFunzioni;
	private RuoliForm form;

	public RegistrazioneForm(Composite parent, int style) {
		super(parent, style);
		GridData gdForm = new GridData(SWT.NONE);
		gdForm.grabExcessVerticalSpace = true;
		gdForm.grabExcessHorizontalSpace = true;
		gdForm.horizontalAlignment = SWT.FILL;
		gdForm.verticalAlignment = SWT.FILL;
		this.setLayoutData(gdForm);
		GridLayout glForm = new GridLayout(1, false);
		this.setLayout(glForm);

		this.setBackground(white);
		cmp = new Composite(this, SWT.BORDER);
		GridData gdCmp = new GridData(SWT.BORDER);
		gdCmp.grabExcessHorizontalSpace = true;
		gdCmp.horizontalAlignment = SWT.FILL;
		gdCmp.horizontalSpan=2;
		gdCmp.minimumHeight = 250;
		cmp.setLayoutData(gdCmp);
		// cmp.setLayoutData(new GridData());
		cmp.setLayout(new GridLayout(2, false));
		cmp.setBackground(white);
		Label lblTitolo = new Label(cmp, SWT.FILL);
		GridData gdLblTitolo = new GridData();
		gdLblTitolo.horizontalSpan = 2;
		gdLblTitolo.horizontalAlignment = SWT.CENTER;
		lblTitolo.setLayoutData(gdLblTitolo);
		lblTitolo.setText("Registra Utente");
		lblTitolo.setFont(fontTitle);
		lblTitolo.setBackground(white);

		GridData gdLbl = new GridData(SWT.BORDER);
		gdLbl.grabExcessHorizontalSpace = true;
		gdLbl.verticalAlignment = SWT.CENTER;
		gdLbl.horizontalAlignment = SWT.FILL;
		gdLbl.grabExcessVerticalSpace = true;

		Label lbl2 = new Label(cmp, SWT.NONE | SWT.BOLD);
		lbl2.setText("Utente");
		lbl2.setBackground(white);
		lbl2.setLayoutData(gdLbl);
		lbl2.setFont(font);
		utente = new Text(cmp, SWT.BORDER);
		GridData layoutData = new GridData(SWT.FILL);
		layoutData.grabExcessHorizontalSpace = true;
		layoutData.horizontalAlignment = SWT.FILL;

		utente.setFont(font);
		utente.setLayoutData(layoutData);
		Label lbl = new Label(cmp, SWT.NONE | SWT.BOLD);
		lbl.setText("Password");
		lbl.setBackground(white);
		lbl.setLayoutData(gdLbl);
		lbl.setFont(font);
		password = new Text(cmp, SWT.PASSWORD | SWT.BORDER);
		password.setLayoutData(layoutData);
		password.setFont(font);
		Label lblPrfilo = new Label(cmp, SWT.NONE);
		lblPrfilo.setText("Profilo");
		lblPrfilo.setLayoutData(gdLbl);
		lblPrfilo.setBackground(white);
		profilo = new Combo(cmp, SWT.BORDER);
		for (Ruolo item : RuoloDAO.getAllRoules()) {
			profilo.add(item.getDescrizione());
		}
		lblPrfilo.setFont(font);
		profilo.setFont(font);

		Label blbFIll3 = new Label(cmp, SWT.NONE);
		Button registra = new Button(cmp, SWT.NONE);
		registra.setText("Registra");
		registra.setFont(font);
		registra.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				abilita(utente.getText(), password.getText().trim(), profilo
						.getText());
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {

			}
		});
		Button utentiPerRuolo = new Button(this, SWT.NONE);
		utentiPerRuolo.setText("Visualizza Utenti per Ruolo");
		utentiPerRuolo.setFont(font);
		utentiPerRuolo.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				creaUtentiRuoli();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {

			}
		});
		
		Button gestioneRuoli = new Button(this, SWT.NONE);
		gestioneRuoli.setText("Gestione Ruoli");
		gestioneRuoli.setFont(font);
		gestioneRuoli.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				openGestioneRuoli();

			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {

			}
		});
		creaUtentiRuoliFunzioni();
	}

	private void abilita(String utente, String password, String descrRuolo) {
		try {
			Utente newUtente = new Utente(RuoloDAO.get(descrRuolo), utente,
					password);
			UtenteDAO.RegistraUtente(newUtente);
			MessageBox msg = new MessageBox(new Shell());
			msg.setMessage("Operazione eseguita con successo");
			msg.setText("Info");
			msg.open();
		} catch (Exception e) {
			MessageBox msg = new MessageBox(new Shell());
			msg.setMessage(e.getMessage());
			msg.setText("Errore");
			msg.open();

		}
	}

	private void openGestioneRuoli() {
		for(Control ctrl :this.getChildren())
			ctrl.dispose();
		form = new RuoliForm(this, SWT.NONE);
		this.layout();
	}
	
	private void creaUtentiRuoli() {
		GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, true);
		gridData.heightHint = 32;
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 1;
		Shell shell = new Shell(Display.getCurrent(), SWT.APPLICATION_MODAL
				| SWT.SHELL_TRIM);
		shell.setText("Associazioni utenti ruoli");
		shell.setLayout(new GridLayout());
		shell.setSize(400, 400);
		Composite cmp = new Composite(shell, SWT.BORDER);
		GridData gdCmp = new GridData(SWT.BORDER);
		gdCmp.grabExcessHorizontalSpace = true;
		gdCmp.grabExcessVerticalSpace = true;
		gdCmp.horizontalAlignment = SWT.FILL;
		gdCmp.verticalAlignment = SWT.FILL;
		gdCmp.minimumHeight = 350;
		cmp.setLayoutData(gdCmp);
		// cmp.setLayoutData(new GridData());
		cmp.setLayout(new GridLayout(2, true));
		cmp.setBackground(white);
		Label lblTitolo = new Label(cmp, SWT.FILL);
		GridData gdLblTitolo = new GridData();
		gdLblTitolo.horizontalSpan = 2;
		gdLblTitolo.horizontalAlignment = SWT.CENTER;
		lblTitolo.setLayoutData(gdLblTitolo);
		lblTitolo.setText("Utente e Ruoli esistenti");
		lblTitolo.setFont(fontTitle);
		lblTitolo.setBackground(white);
		GridData gdTables = new GridData();
		gdTables.grabExcessHorizontalSpace = true;
		gdTables.horizontalAlignment = SWT.FILL;
		gdTables.grabExcessVerticalSpace = true;
		gdTables.verticalAlignment = SWT.FILL;
		tblRuoli = new Table(cmp, SWT.BORDER | SWT.FULL_SELECTION
				| SWT.V_SCROLL | SWT.MULTI | SWT.H_SCROLL);
		TableColumn tableColumn = new TableColumn(tblRuoli, SWT.LEAD);
		tableColumn.setText("id");
		TableColumn tableColumn2 = new TableColumn(tblRuoli, SWT.LEAD);
		tableColumn2.setText("Ruolo");
		tableColumn2.setWidth(200);
		for(Ruolo ruolo:RuoloDAO.getAllRoules()){
			TableItem tableItem= new TableItem(tblRuoli, SWT.NONE);
			tableItem.setText(new String[]{""+ruolo.getIdRuolo(),ruolo.getDescrizione() });
		}
		tblRuoli.setHeaderVisible(true);
		tblRuoli.setToolTipText("Utenti - Ruoli");
		tblRuoli.setLinesVisible(true);
		tblRuoli.getColumn(0).setWidth(0);
		tblRuoli.setFont(font);
		tblRuoli.setLayoutData(gdTables);

		GridData gdLbl = new GridData(SWT.BORDER);
		gdLbl.grabExcessHorizontalSpace = true;
		gdLbl.verticalAlignment = SWT.CENTER;
		gdLbl.horizontalAlignment = SWT.FILL;
		gdLbl.grabExcessVerticalSpace = true;
		
		tblRuoli.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				ricaricaUtentiRuolo(((TableItem)tblRuoli.getSelection()[0]).getText(0));
				}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				
			}
		});


		tblUtentiRuoli = new Table(cmp, SWT.BORDER | SWT.FULL_SELECTION
				| SWT.V_SCROLL | SWT.MULTI | SWT.H_SCROLL);
		TableColumn tableColumn3 = new TableColumn(tblUtentiRuoli, SWT.LEAD);
		tableColumn3.setText("id Utente");
		TableColumn tableColumn4 = new TableColumn(tblUtentiRuoli, SWT.LEAD);
		tableColumn4.setText("Nome Utente");
		tableColumn4.setWidth(200);
		tblUtentiRuoli.setHeaderVisible(true);
		tblUtentiRuoli.setToolTipText("Utenti - Ruoli");
		tblUtentiRuoli.setLinesVisible(true);
		tblUtentiRuoli.getColumn(0).setWidth(0);
		tblUtentiRuoli.setFont(font);
		tblUtentiRuoli.setLayoutData(gdTables);
		shell.pack();
		shell.open();
	}
	private void creaUtentiRuoliFunzioni() {
		Composite cmp = new Composite(this, SWT.BORDER);
		GridData gdCmp = new GridData(SWT.BORDER);
		gdCmp.grabExcessHorizontalSpace = true;
		gdCmp.grabExcessVerticalSpace = true;
		gdCmp.horizontalAlignment = SWT.FILL;
		gdCmp.horizontalSpan = 2;
		gdCmp.verticalAlignment = SWT.FILL;
		gdCmp.minimumHeight = 350;
		cmp.setLayoutData(gdCmp);
		// cmp.setLayoutData(new GridData());
		cmp.setLayout(new GridLayout(2, false));
		cmp.setBackground(white);
		Label lblTitolo = new Label(cmp, SWT.FILL);
		GridData gdLblTitolo = new GridData();
		gdLblTitolo.horizontalSpan = 2;
		gdLblTitolo.horizontalAlignment = SWT.CENTER;
		lblTitolo.setLayoutData(gdLblTitolo);
		lblTitolo.setText("Ruoli esistenti");
		lblTitolo.setFont(fontTitle);
		lblTitolo.setBackground(white);
		GridData gdTables = new GridData();
		gdTables.grabExcessHorizontalSpace = true;
		gdTables.horizontalAlignment = SWT.FILL;
		gdTables.grabExcessVerticalSpace = true;
		gdTables.verticalAlignment = SWT.FILL;
		tblRuoliEsistenti = new Table(cmp, SWT.BORDER | SWT.FULL_SELECTION
				| SWT.V_SCROLL | SWT.MULTI | SWT.H_SCROLL);
		TableColumn tableColumn = new TableColumn(tblRuoliEsistenti, SWT.LEAD);
		tableColumn.setText("id");
		TableColumn tableColumn2 = new TableColumn(tblRuoliEsistenti, SWT.LEAD);
		tableColumn2.setText("Ruoli");
		tableColumn2.setWidth(200);
		for(Ruolo ruolo:RuoloDAO.getAllRoules()){
			TableItem tableItem= new TableItem(tblRuoliEsistenti, SWT.NONE);
			tableItem.setText(new String[]{""+ruolo.getIdRuolo(),ruolo.getDescrizione() });
		}
		tblRuoliEsistenti.setHeaderVisible(true);
		tblRuoliEsistenti.setToolTipText("Utenti");
		tblRuoliEsistenti.setLinesVisible(true);
		tblRuoliEsistenti.getColumn(0).setWidth(0);
		tblRuoliEsistenti.setFont(font);
		tblRuoliEsistenti.setLayoutData(gdTables);

		GridData gdLbl = new GridData(SWT.BORDER);
		gdLbl.grabExcessHorizontalSpace = true;
		gdLbl.verticalAlignment = SWT.CENTER;
		gdLbl.horizontalAlignment = SWT.FILL;
		gdLbl.grabExcessVerticalSpace = true;
		
		tblRuoliEsistenti.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				ricaricaRuoliFunzioni(Integer.parseInt(((TableItem)tblRuoliEsistenti.getSelection()[0]).getText(0)));
				}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				
			}
		});


		tblFunzioni = new Table(cmp, SWT.BORDER | SWT.FULL_SELECTION
				| SWT.V_SCROLL | SWT.MULTI | SWT.H_SCROLL);
		TableColumn tableColumn3 = new TableColumn(tblFunzioni, SWT.LEAD);
		tableColumn3.setText("id Funzione");
		TableColumn tableColumn4 = new TableColumn(tblFunzioni, SWT.LEAD);
		tableColumn4.setText("Funzioni");
		tableColumn4.setWidth(200);
		tblFunzioni.setHeaderVisible(true);
		tblFunzioni.setToolTipText("Utenti - Ruoli");
		tblFunzioni.setLinesVisible(true);
		tblFunzioni.getColumn(0).setWidth(0);
		tblFunzioni.setFont(font);
		tblFunzioni.setLayoutData(gdTables);
	}
	private void ricaricaUtentiRuolo(String idRuolo) {
		try {
			tblUtentiRuoli.removeAll();
			for (Utente user : UtenteDAO.getUtenti(Integer.parseInt(idRuolo))) {
				TableItem item = new TableItem(tblUtentiRuoli, SWT.CENTER);
				item.setText(new String[] { "" + user.getIdUtente(),
						user.getNomeUtente() });
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void ricaricaRuoliFunzioni(int idRuolo) {
		try {
			tblFunzioni.removeAll();
			Ruolo ruolo = RuoloDAO.get(idRuolo);
			for (Funzione funct: (Set<Funzione>)ruolo.getFunziones()) {
				TableItem item = new TableItem(tblFunzioni, SWT.CENTER);
				item.setText(new String[] { "" + funct.getIdFunzione(),
						funct.getDescrizione() });
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
