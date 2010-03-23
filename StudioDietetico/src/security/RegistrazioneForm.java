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
import org.eclipse.swt.layout.FillLayout;
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
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.swt.widgets.TreeItem;

import command.RuoloDAO;
import command.UtenteDAO;
import common.Utils;
import common.ui.ListComposite;

public class RegistrazioneForm extends ListComposite {

	private static final Font font = Utils.getFont("Arial", 8, SWT.BOLD);
	private static final Font fontTbl = Utils.getFont("Arial", 8, SWT.BOLD);
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
	private Tree tblFunzioni;
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
		gdCmp.horizontalSpan = 2;
		gdCmp.minimumHeight = 250;
		cmp.setLayoutData(gdCmp);
		// cmp.setLayoutData(new GridData());
		cmp.setLayout(new GridLayout(4, true));
		cmp.setBackground(white);
		Label lblTitolo = new Label(cmp, SWT.FILL);
		GridData gdLblTitolo = new GridData();
		gdLblTitolo.horizontalSpan = 4;
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
		Label blbFIll4 = new Label(cmp, SWT.NONE);
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
		Button utentiPerRuolo = new Button(cmp, SWT.NONE);
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

		Button gestioneRuoli = new Button(cmp, SWT.NONE);
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
		for (Control ctrl : this.getChildren())
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
		for (Ruolo ruolo : RuoloDAO.getAllRoules()) {
			TableItem tableItem = new TableItem(tblRuoli, SWT.NONE);
			tableItem.setText(new String[] { "" + ruolo.getIdRuolo(),
					ruolo.getDescrizione() });
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
				ricaricaUtentiRuolo(((TableItem) tblRuoli.getSelection()[0])
						.getText(0));
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

	private void creaTabellaFunzioniInsert(Composite compositeParent) {
		tblFunzioni = new Tree(compositeParent, SWT.BORDER | SWT.H_SCROLL
				| SWT.V_SCROLL);
		tblFunzioni.setLayout(new FillLayout());
		GridData gdTree = new GridData();
		gdTree.grabExcessVerticalSpace = true;
		gdTree.verticalAlignment = SWT.FILL;
		tblFunzioni.setLayoutData(gdTree);
		tblFunzioni.setHeaderVisible(true);
		TreeColumn colFunzione = new TreeColumn(tblFunzioni, SWT.CENTER);
		colFunzione.setText("Funzioni");
		colFunzione.setWidth(300);
		TreeItem paziente = new TreeItem(tblFunzioni, SWT.NONE);
		paziente.setData(IFunzioniConstants.GESTIONE_PAZIENTI);
		paziente.setText("Menu Pazienti");
		TreeItem treeItem = new TreeItem(paziente, SWT.NONE);
		treeItem.setData(IFunzioniConstants.GESTIONE_PAZIENTI);
		treeItem.setText("CRUD Paziente");
		TreeItem treeItem1 = new TreeItem(paziente, SWT.NONE);
		treeItem1.setData(IFunzioniConstants.RILEVAZ_PARAM_ANTROP);
		treeItem1.setText("Rilevazione Parametri Antropometrici");
		TreeItem treeItem2 = new TreeItem(paziente, SWT.NONE);
		treeItem2.setData(IFunzioniConstants.ANAMNESI);
		treeItem2.setText("Anamnesi");
		TreeItem treeItem3 = new TreeItem(paziente, SWT.NONE);
		treeItem3.setData(IFunzioniConstants.PRENOTAZ_VISITA);
		treeItem3.setText("Prenotazione Visita");
		TreeItem treeItem4 = new TreeItem(paziente, SWT.NONE);
		treeItem4.setData(IFunzioniConstants.ESAME_CLINICO);
		treeItem4.setText("Esame Clinico");

		TreeItem visite = new TreeItem(tblFunzioni, SWT.NONE);
		visite.setData(IFunzioniConstants.GESTIONE_VISITA);
		visite.setText("Menu Visite");
		TreeItem treeItemV1 = new TreeItem(visite, SWT.NONE);
		treeItemV1.setData(IFunzioniConstants.REGISTRA_VISITA);
		treeItemV1.setText("Registra / Elimina Visita");
		TreeItem treeItemV2 = new TreeItem(visite, SWT.NONE);
		treeItemV2.setData(IFunzioniConstants.FATTURA_VISITA);
		treeItemV2.setText("Associa Fattura");
		TreeItem treeItemV3 = new TreeItem(visite, SWT.NONE);
		treeItemV3.setData(IFunzioniConstants.CONTO_VISITA);
		treeItemV3.setText("Associa Conto");

		TreeItem diete = new TreeItem(tblFunzioni, SWT.NONE);
		diete.setData(IFunzioniConstants.GESTIONE_DIETA);
		diete.setText("Menu Diete");
		TreeItem treeItemD1 = new TreeItem(diete, SWT.NONE);
		treeItemD1.setData(IFunzioniConstants.GESTIONE_DIETA);
		treeItemD1.setText("Crea / Elimina Dieta");

		TreeItem esamiClinici = new TreeItem(tblFunzioni, SWT.NONE);
		esamiClinici.setData(IFunzioniConstants.MENU_ESAME_CLINICO);
		esamiClinici.setText("Menu Esami Clinici");
		TreeItem treeItemE1 = new TreeItem(esamiClinici, SWT.NONE);
		treeItemE1.setData(IFunzioniConstants.MENU_ESAME_CLINICO);
		treeItemE1.setText("Crea / Elimina Esame Clinico");

		TreeItem paramAntro = new TreeItem(tblFunzioni, SWT.NONE);
		paramAntro.setData(IFunzioniConstants.PARAMETRI_ANTROPOMETRICI);
		paramAntro.setText("Menu Parametri Antropometrici");
		TreeItem treeItemP1 = new TreeItem(paramAntro, SWT.NONE);
		treeItemP1.setData(IFunzioniConstants.PARAMETRI_ANTROPOMETRICI);
		treeItemP1.setText("Crea / Elimina Parametro Antropometrico");

		TreeItem turni = new TreeItem(tblFunzioni, SWT.NONE);
		turni.setData(IFunzioniConstants.GESTIONE_TURNI);
		turni.setText("Menu Turni");
		TreeItem treeItemT1 = new TreeItem(turni, SWT.NONE);
		treeItemT1.setData(IFunzioniConstants.GESTIONE_TURNI);
		treeItemT1.setText("Crea Turno");

		TreeItem medico = new TreeItem(tblFunzioni, SWT.NONE);
		medico.setData(IFunzioniConstants.MENU_GESTIONE_MEDICI);
		medico.setText("Menu Medici");
		TreeItem treeItemM1 = new TreeItem(medico, SWT.NONE);
		treeItemM1.setData(IFunzioniConstants.MENU_GESTIONE_MEDICI);
		treeItemM1.setText("Crea/Modifica/Elimina Medico");

		TreeItem interrog = new TreeItem(tblFunzioni, SWT.NONE);
		interrog.setData(IFunzioniConstants.MENU_STATISTICHE);
		interrog.setText("Menu Interrogazioni");
		TreeItem treeItemI1 = new TreeItem(interrog, SWT.NONE);
		treeItemI1.setData(IFunzioniConstants.MENU_STATISTICHE);
		treeItemI1.setText("Statistiche");
		TreeItem treeItemI2 = new TreeItem(interrog, SWT.NONE);
		treeItemI2.setData(IFunzioniConstants.MENU_QUERY_DINAMICHE);
		treeItemI2.setText("Dinamiche");
		TreeItem treeItemI3 = new TreeItem(interrog, SWT.NONE);
		treeItemI3.setData(IFunzioniConstants.MENU_STATISTICHE);
		treeItemI3.setText("Automatiche");

		TreeItem utente = new TreeItem(tblFunzioni, SWT.NONE);
		utente.setData(IFunzioniConstants.GESTIONE_UTENTI);
		utente.setText("Menu Utenti");
		TreeItem treeItemU1 = new TreeItem(utente, SWT.NONE);
		treeItemU1.setData(IFunzioniConstants.GESTIONE_UTENTI);
		treeItemU1.setText("Crea Utenti/Ruoli");
		tblFunzioni.setFont(fontTbl);
		tblFunzioni.setEnabled(false);

	}

	private void creaUtentiRuoliFunzioni() {
		Composite cmp = new Composite(this, SWT.BORDER);
		GridData gdCmp = new GridData(SWT.BORDER);
		gdCmp.grabExcessHorizontalSpace = true;
		gdCmp.grabExcessVerticalSpace = true;
		gdCmp.horizontalAlignment = SWT.FILL;
		gdCmp.horizontalSpan = 1;
		gdCmp.verticalAlignment = SWT.FILL;
		cmp.setLayoutData(gdCmp);
		// cmp.setLayoutData(new GridData());
		cmp.setLayout(new GridLayout(2, true));
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
		for (Ruolo ruolo : RuoloDAO.getAllRoules()) {
			TableItem tableItem = new TableItem(tblRuoliEsistenti, SWT.NONE);
			tableItem.setText(new String[] { "" + ruolo.getIdRuolo(),
					ruolo.getDescrizione() });
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
				ricaricaRuoliFunzioni(Integer
						.parseInt(((TableItem) tblRuoliEsistenti.getSelection()[0])
								.getText(0)));
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {

			}
		});

		creaTabellaFunzioniInsert(cmp);
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

			Ruolo ruolo = RuoloDAO.get(idRuolo);
			for (TreeItem item : tblFunzioni.getItems()) {
				item.setChecked(false);
				item.setExpanded(false);
				for (TreeItem item1 : item.getItems()) {
					item1.setChecked(false);
					item1.setExpanded(false);
				}
			}
			for (Object fn : ruolo.getFunziones()) {
				for (TreeItem item : tblFunzioni.getItems()) {
					if (item.getData().equals(((Funzione) fn).getDescrizione())) {
						item.setChecked(true);
						tblFunzioni.showItem(item);
					}
					for (TreeItem item1 : item.getItems()) {
						if (item1.getData().equals(
								((Funzione) fn).getDescrizione())) {
							item1.setChecked(true);
							tblFunzioni.showItem(item1);
						}
					}
				}
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
