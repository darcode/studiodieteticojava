package security;

import java.util.ArrayList;

import hibernate.Funzione;
import hibernate.Ruolo;
import hibernate.Utente;

import mondrian.tui.CmdRunner;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
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
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.swt.widgets.TreeItem;

import command.FunzioneDAO;
import command.RuoloDAO;
import command.UtenteDAO;
import common.Utils;
import common.ui.ListComposite;

public class RuoliForm extends ListComposite {

	private static final Font font = Utils.getFont("Arial", 8, SWT.BOLD);
	private static final Font fontTitle = Utils.getFont("Arial", 12, SWT.BOLD);
	Color white = Utils.getStandardWhiteColor();
	private Text nomeRuolo;
	private Composite cmpNuovoRuolo;
	private Combo ruoloCombo;
	private Composite cmpModificaRoulo;
	private Tree treeFunzioniInserisci = null;
	private Tree treeFunzioniModifica = null;
	ArrayList<String> funzioni = new ArrayList<String>();

	public RuoliForm(Composite parent, int style) {
		super(parent, style);
		this.setLayout(new GridLayout(1, true));

		GridData gdForm = new GridData();
		gdForm.grabExcessHorizontalSpace = true;
		gdForm.grabExcessVerticalSpace = true;
		gdForm.horizontalAlignment = SWT.FILL;
		gdForm.verticalAlignment = SWT.FILL;
		this.setLayoutData(gdForm);

		Composite cmp = new Composite(this, SWT.FILL);
		cmp.setLayoutData(gdForm);
		cmp.setLayout(new GridLayout(2, true));

		GridData gdLbl = new GridData(SWT.BORDER);
		gdLbl.grabExcessHorizontalSpace = true;
		gdLbl.verticalAlignment = SWT.CENTER;
		gdLbl.horizontalAlignment = SWT.FILL;
		gdLbl.grabExcessVerticalSpace = true;

		cmpNuovoRuolo = new Composite(cmp, SWT.BORDER);
		GridData gdCmpNuovoRuolo = new GridData(SWT.BORDER);
		gdCmpNuovoRuolo.grabExcessHorizontalSpace = true;
		gdCmpNuovoRuolo.grabExcessVerticalSpace = true;
		gdCmpNuovoRuolo.horizontalAlignment = SWT.FILL;
		gdCmpNuovoRuolo.verticalAlignment = SWT.FILL;
		cmpNuovoRuolo.setLayoutData(gdCmpNuovoRuolo);
		// cmp.setLayoutData(new GridData());
		cmpNuovoRuolo.setLayout(new GridLayout(2, false));
		cmpNuovoRuolo.setBackground(white);

		Label lblTitolo1 = new Label(cmpNuovoRuolo, SWT.FILL);
		GridData gdLblTitolo1 = new GridData();
		gdLblTitolo1.horizontalSpan = 2;
		gdLblTitolo1.horizontalAlignment = SWT.CENTER;
		lblTitolo1.setLayoutData(gdLblTitolo1);
		lblTitolo1.setText("Crea Ruolo");
		lblTitolo1.setFont(fontTitle);
		lblTitolo1.setBackground(white);
		GridData gdNomeRuolo = new GridData(SWT.BORDER);
		gdNomeRuolo.grabExcessHorizontalSpace = true;
		gdNomeRuolo.verticalAlignment = SWT.CENTER;
		gdNomeRuolo.horizontalAlignment = SWT.CENTER;
		gdNomeRuolo.grabExcessVerticalSpace = true;

		Label lblNomeRuolo = new Label(cmpNuovoRuolo, SWT.NONE | SWT.BOLD);
		lblNomeRuolo.setText("Nome Ruolo");
		lblNomeRuolo.setBackground(white);
		lblNomeRuolo.setLayoutData(gdLbl);
		lblNomeRuolo.setFont(font);
		nomeRuolo = new Text(cmpNuovoRuolo, SWT.BORDER);
		GridData ggTxtNomeRUolo = new GridData(SWT.FILL);
		ggTxtNomeRUolo.widthHint = 200;
		ggTxtNomeRUolo.verticalAlignment = SWT.CENTER;
		nomeRuolo.setLayoutData(ggTxtNomeRUolo);
		nomeRuolo.setFont(font);
		Label lblFunzioni = new Label(cmpNuovoRuolo, SWT.NONE | SWT.BOLD);
		lblFunzioni.setText("Seleziona le funzioni");
		lblFunzioni.setBackground(white);
		lblFunzioni.setLayoutData(gdLbl);
		lblFunzioni.setFont(font);
		GridData gdTables = new GridData();
		gdTables.grabExcessHorizontalSpace = true;
		gdTables.horizontalAlignment = SWT.FILL;
		gdTables.grabExcessVerticalSpace = true;
		gdTables.verticalAlignment = SWT.FILL;
		creaTabellaFunzioniInsert(cmpNuovoRuolo);
		Button inserisci = new Button(cmpNuovoRuolo, SWT.NONE);
		inserisci.setText("Inserisci");
		inserisci.setFont(font);
		inserisci.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				insertRuolo();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {

			}
		});
		GridData gdIns = new GridData();
		gdIns.horizontalSpan = 2;
		gdIns.horizontalAlignment = SWT.CENTER;
		inserisci.setLayoutData(gdIns);
		cmpModificaRoulo = new Composite(cmp, SWT.BORDER);
		GridData gdCmp1 = new GridData(SWT.BORDER);
		gdCmp1.grabExcessHorizontalSpace = true;
		gdCmp1.grabExcessVerticalSpace = true;
		gdCmp1.horizontalAlignment = SWT.FILL;
		gdCmp1.verticalAlignment = SWT.FILL;
		cmpModificaRoulo.setLayoutData(gdCmp1);
		// cmp.setLayoutData(new GridData());
		cmpModificaRoulo.setLayout(new GridLayout(2, false));
		cmpModificaRoulo.setBackground(white);

		Label lblTitolo2 = new Label(cmpModificaRoulo, SWT.FILL);
		GridData gdLblTitolo2 = new GridData();
		gdLblTitolo2.horizontalSpan = 2;
		gdLblTitolo2.horizontalAlignment = SWT.CENTER;
		lblTitolo2.setLayoutData(gdLblTitolo2);
		lblTitolo2.setText("Modifica Ruolo");
		lblTitolo2.setFont(fontTitle);
		lblTitolo2.setBackground(white);
		Label lblRuolo = new Label(cmpModificaRoulo, SWT.NONE | SWT.BOLD);
		lblRuolo.setText("Ruolo");
		lblRuolo.setBackground(white);
		lblRuolo.setLayoutData(gdLbl);
		lblRuolo.setFont(font);
		ruoloCombo = new Combo(cmpModificaRoulo, SWT.BORDER);
		GridData gdRuolo = new GridData(SWT.FILL);
		gdRuolo.verticalAlignment = SWT.CENTER;
		ruoloCombo.setLayoutData(gdRuolo);
		ruoloCombo.setFont(font);
		for (Ruolo ruolo : RuoloDAO.getAllRoules())
			ruoloCombo.add(ruolo.getDescrizione());
		ruoloCombo.addModifyListener(new ModifyListener() {

			@Override
			public void modifyText(ModifyEvent e) {
				Ruolo ruolo = RuoloDAO.get(ruoloCombo.getText());
				System.out.println(ruolo.getIdRuolo());
				caricaFunzioniRuolo(ruolo);
			}
		});
		Label lblFunz = new Label(cmpModificaRoulo, SWT.NONE | SWT.BOLD);
		lblFunz.setText("Seleziona le funzioni");
		lblFunz.setBackground(white);
		lblFunz.setLayoutData(gdLbl);
		lblFunz.setFont(font);

		creaTabellaFunzioni(cmpModificaRoulo);
		Button modifica = new Button(cmpModificaRoulo, SWT.NONE);
		modifica.setText("Modifica");
		modifica.setFont(font);
		GridData gdMod = new GridData();
		gdMod.horizontalSpan = 2;
		gdMod.horizontalAlignment = SWT.CENTER;
		modifica.setLayoutData(gdMod);
		modifica.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				modificaRuolo();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				modificaRuolo();
			}
		});

		this.setBackground(white);
		Button gestioneUtenti = new Button(this, SWT.NONE);
		gestioneUtenti.setText("Gestione Utenti");
		gestioneUtenti.setFont(font);
		gestioneUtenti.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				chiudiGestioneRuoli();

			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {

			}
		});

	}

	private void creaTabellaFunzioni(Composite compositeParent) {
		treeFunzioniModifica = new Tree(compositeParent, SWT.CHECK | SWT.BORDER
				| SWT.H_SCROLL | SWT.V_SCROLL);
		treeFunzioniModifica.setLayout(new FillLayout());
		GridData gdTree = new GridData();
		gdTree.grabExcessVerticalSpace = true;
		gdTree.verticalAlignment = SWT.FILL;
		treeFunzioniModifica.setLayoutData(gdTree);
		treeFunzioniModifica.setHeaderVisible(true);
		TreeColumn colFunzione = new TreeColumn(treeFunzioniModifica,
				SWT.CENTER);
		colFunzione.setText("Funzioni");
		colFunzione.setWidth(300);
		TreeItem paziente = new TreeItem(treeFunzioniModifica, SWT.NONE);
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

		TreeItem visite = new TreeItem(treeFunzioniModifica, SWT.NONE);
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

		TreeItem diete = new TreeItem(treeFunzioniModifica, SWT.NONE);
		diete.setData(IFunzioniConstants.GESTIONE_DIETA);
		diete.setText("Menu Diete");
		TreeItem treeItemD1 = new TreeItem(diete, SWT.NONE);
		treeItemD1.setData(IFunzioniConstants.GESTIONE_DIETA);
		treeItemD1.setText("Crea / Elimina Dieta");

		TreeItem esamiClinici = new TreeItem(treeFunzioniModifica, SWT.NONE);
		esamiClinici.setData(IFunzioniConstants.MENU_ESAME_CLINICO);
		esamiClinici.setText("Menu Esami Clinici");
		TreeItem treeItemE1 = new TreeItem(esamiClinici, SWT.NONE);
		treeItemE1.setData(IFunzioniConstants.MENU_ESAME_CLINICO);
		treeItemE1.setText("Crea / Elimina Esame Clinico");

		TreeItem paramAntro = new TreeItem(treeFunzioniModifica, SWT.NONE);
		paramAntro.setData(IFunzioniConstants.PARAMETRI_ANTROPOMETRICI);
		paramAntro.setText("Menu Parametri Antropometrici");
		TreeItem treeItemP1 = new TreeItem(paramAntro, SWT.NONE);
		treeItemP1.setData(IFunzioniConstants.PARAMETRI_ANTROPOMETRICI);
		treeItemP1.setText("Crea / Elimina Parametro Antropometrico");

		TreeItem turni = new TreeItem(treeFunzioniModifica, SWT.NONE);
		turni.setData(IFunzioniConstants.GESTIONE_TURNI);
		turni.setText("Menu Turni");
		TreeItem treeItemT1 = new TreeItem(turni, SWT.NONE);
		treeItemT1.setData(IFunzioniConstants.GESTIONE_TURNI);
		treeItemT1.setText("Crea Turno");

		TreeItem medico = new TreeItem(treeFunzioniModifica, SWT.NONE);
		medico.setData(IFunzioniConstants.MENU_GESTIONE_MEDICI);
		medico.setText("Menu Medici");
		TreeItem treeItemM1 = new TreeItem(medico, SWT.NONE);
		treeItemM1.setData(IFunzioniConstants.MENU_GESTIONE_MEDICI);
		treeItemM1.setText("Crea/Modifica/Elimina Medico");

		TreeItem interrog = new TreeItem(treeFunzioniModifica, SWT.NONE);
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

		TreeItem utente = new TreeItem(treeFunzioniModifica, SWT.NONE);
		utente.setData(IFunzioniConstants.GESTIONE_UTENTI);
		utente.setText("Menu Utenti");
		TreeItem treeItemU1 = new TreeItem(utente, SWT.NONE);
		treeItemU1.setData(IFunzioniConstants.GESTIONE_UTENTI);
		treeItemU1.setText("Crea Utenti/Ruoli");

	}

	private void creaTabellaFunzioniInsert(Composite compositeParent) {
		treeFunzioniInserisci = new Tree(compositeParent, SWT.CHECK
				| SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		treeFunzioniInserisci.setLayout(new FillLayout());
		GridData gdTree = new GridData();
		gdTree.grabExcessVerticalSpace = true;
		gdTree.verticalAlignment = SWT.FILL;
		treeFunzioniInserisci.setLayoutData(gdTree);
		treeFunzioniInserisci.setHeaderVisible(true);
		TreeColumn colFunzione = new TreeColumn(treeFunzioniInserisci,
				SWT.CENTER);
		colFunzione.setText("Funzioni");
		colFunzione.setWidth(300);
		TreeItem paziente = new TreeItem(treeFunzioniInserisci, SWT.NONE);
		paziente.setGrayed(true);
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

		TreeItem visite = new TreeItem(treeFunzioniInserisci, SWT.NONE);
		visite.setData(IFunzioniConstants.GESTIONE_VISITA);
		visite.setText("Menu Visite");
		visite.setGrayed(true);
		TreeItem treeItemV1 = new TreeItem(visite, SWT.NONE);
		treeItemV1.setData(IFunzioniConstants.REGISTRA_VISITA);
		treeItemV1.setText("Registra / Elimina Visita");
		TreeItem treeItemV2 = new TreeItem(visite, SWT.NONE);
		treeItemV2.setData(IFunzioniConstants.FATTURA_VISITA);
		treeItemV2.setText("Associa Fattura");
		TreeItem treeItemV3 = new TreeItem(visite, SWT.NONE);
		treeItemV3.setData(IFunzioniConstants.CONTO_VISITA);
		treeItemV3.setText("Associa Conto");

		TreeItem diete = new TreeItem(treeFunzioniInserisci, SWT.NONE);
		diete.setData(IFunzioniConstants.GESTIONE_DIETA);
		diete.setText("Menu Diete");
		diete.setGrayed(true);
		TreeItem treeItemD1 = new TreeItem(diete, SWT.NONE);
		treeItemD1.setData(IFunzioniConstants.GESTIONE_DIETA);
		treeItemD1.setText("Crea / Elimina Dieta");

		TreeItem esamiClinici = new TreeItem(treeFunzioniInserisci, SWT.NONE);
		esamiClinici.setData(IFunzioniConstants.MENU_ESAME_CLINICO);
		esamiClinici.setText("Menu Esami Clinici");
		esamiClinici.setGrayed(true);
		TreeItem treeItemE1 = new TreeItem(esamiClinici, SWT.NONE);
		treeItemE1.setData(IFunzioniConstants.MENU_ESAME_CLINICO);
		treeItemE1.setText("Crea / Elimina Esame Clinico");

		TreeItem paramAntro = new TreeItem(treeFunzioniInserisci, SWT.NONE);
		paramAntro.setGrayed(true);
		paramAntro.setData(IFunzioniConstants.PARAMETRI_ANTROPOMETRICI);
		paramAntro.setText("Menu Parametri Antropometrici");
		TreeItem treeItemP1 = new TreeItem(paramAntro, SWT.NONE);
		treeItemP1.setData(IFunzioniConstants.PARAMETRI_ANTROPOMETRICI);
		treeItemP1.setText("Crea / Elimina Parametro Antropometrico");

		TreeItem turni = new TreeItem(treeFunzioniInserisci, SWT.NONE);
		turni.setGrayed(true);
		turni.setData(IFunzioniConstants.GESTIONE_TURNI);
		turni.setText("Menu Turni");
		TreeItem treeItemT1 = new TreeItem(turni, SWT.NONE);
		treeItemT1.setData(IFunzioniConstants.GESTIONE_TURNI);
		treeItemT1.setText("Crea Turno");

		TreeItem medico = new TreeItem(treeFunzioniInserisci, SWT.NONE);
		medico.setGrayed(true);
		medico.setData(IFunzioniConstants.MENU_GESTIONE_MEDICI);
		medico.setText("Menu Medici");
		TreeItem treeItemM1 = new TreeItem(medico, SWT.NONE);
		treeItemM1.setData(IFunzioniConstants.MENU_GESTIONE_MEDICI);
		treeItemM1.setText("Crea/Modifica/Elimina Medico");

		TreeItem interrog = new TreeItem(treeFunzioniInserisci, SWT.NONE);
		interrog.setGrayed(true);
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

		TreeItem utente = new TreeItem(treeFunzioniInserisci, SWT.NONE);
		utente.setGrayed(true);
		utente.setData(IFunzioniConstants.GESTIONE_UTENTI);
		utente.setText("Menu Utenti");
		TreeItem treeItemU1 = new TreeItem(utente, SWT.NONE);
		treeItemU1.setData(IFunzioniConstants.GESTIONE_UTENTI);
		treeItemU1.setText("Crea Utenti/Ruoli");

	}

	private void modificaRuolo() {
		if (treeFunzioniModifica.getSelectionCount() == 0) {
			Utils.showMessageError("Selezionare almeno una funzione");
			return;
		}
		Ruolo ruolo = RuoloDAO.get(ruoloCombo.getText());
		if (RuoloDAO.updateRuolo(ruolo, treeFunzioniModifica.getItems())) {
			Utils.showMessageInfo("Operazione eseguita con successo");
		} else {
			Utils.showMessageError("Modifica non riuscita");
		}
	}

	private void chiudiGestioneRuoli() {
		for (Control ctrl : this.getChildren())
			ctrl.dispose();
		RegistrazioneForm form = new RegistrazioneForm(this, SWT.NONE);
		this.layout();
	}

	private void insertRuolo() {
		if (treeFunzioniInserisci.getSelectionCount() == 0) {
			Utils.showMessageError("Selezionare almeno una funzione");
			return;
		}
		if (RuoloDAO.get(nomeRuolo.getText()) != null) {
			MessageBox msg = new MessageBox(new Shell());
			msg.setMessage("Ruolo già esistente");
			msg.setText("Errore");
			msg.open();
		} else {
			if (RuoloDAO.insRuolo(nomeRuolo.getText(), treeFunzioniInserisci
					.getSelection())) {
				MessageBox msg = new MessageBox(new Shell());
				msg.setMessage("Operazione eseguita con successo");
				ruoloCombo.add(nomeRuolo.getText());
				msg.setText("Info");
				msg.open();
			} else {
				MessageBox msg = new MessageBox(new Shell());
				msg.setMessage("Inserimento non riuscito");
				msg.setText("Errore");
				msg.open();
			}
		}
	}

	private void caricaFunzioniRuolo(Ruolo ruolo) {
		for (TreeItem item : treeFunzioniModifica.getItems()) {
			item.setChecked(false);
			for (TreeItem item1 : item.getItems()) {
				item1.setChecked(false);
			}
		}
		for (Object fn : ruolo.getFunziones()) {
			for (TreeItem item : treeFunzioniModifica.getItems()) {
				if (item.getData().equals(((Funzione) fn).getDescrizione())) {
					item.setChecked(true);
				}
				for (TreeItem item1 : item.getItems()) {
					if (item1.getData()
							.equals(((Funzione) fn).getDescrizione())) {
						item1.setChecked(true);
					}
				}
			}
		}
		treeFunzioniModifica.showSelection();
	}
}
