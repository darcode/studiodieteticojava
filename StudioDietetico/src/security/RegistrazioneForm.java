package security;

import java.sql.DriverManager;
import java.util.ArrayList;

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
import hibernate.Funzione;
import hibernate.Ruolo;
import hibernate.Utente;

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
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;

import com.mysql.jdbc.Statement;

import command.FunzioneDAO;
import command.RuoloDAO;
import command.UtenteDAO;
import common.Utils;
import common.ui.ListComposite;

public class RegistrazioneForm extends ListComposite {

	private static final Font font = Utils.getFont("Arial", 13, SWT.BOLD);
	private static final Font fontTitle = Utils.getFont("Arial", 16, SWT.BOLD);
	private Text password;
	private Text utente;
	private Combo profilo;
	private Text nomeRuolo;
	private Table tblFunzioni;
	private Composite cmpNuovoRuolo;
	private Table tblFunzioniUp;
	private Combo ruoloCombo;
	private Composite cmpModificaRoulo;

	public RegistrazioneForm(Composite parent, int style) {
		super(parent, style);

		GridData gdForm = new GridData(SWT.NONE);
		gdForm.grabExcessVerticalSpace = true;
		gdForm.grabExcessHorizontalSpace = true;
		gdForm.horizontalAlignment = SWT.FILL;
		gdForm.grabExcessVerticalSpace = true;
		gdForm.verticalAlignment = SWT.FILL;
		this.setLayoutData(gdForm);
		GridLayout glForm = new GridLayout(2, false);
		this.setLayout(glForm);
		Color white = Utils.getStandardWhiteColor();
		this.setBackground(white);
		Composite cmp = new Composite(this, SWT.BORDER);
		GridData gdCmp = new GridData(SWT.BORDER);
		gdCmp.grabExcessHorizontalSpace = true;
		gdCmp.grabExcessVerticalSpace = true;
		gdCmp.horizontalAlignment = SWT.FILL;
		gdCmp.verticalAlignment = SWT.FILL;
		gdCmp.horizontalSpan = 2;
		gdCmp.heightHint = 300;
		cmp.setLayoutData(gdCmp);
		// cmp.setLayoutData(new GridData());
		cmp.setLayout(new GridLayout(2, true));
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
		layoutData.verticalAlignment = SWT.FILL;
		layoutData.horizontalAlignment = SWT.FILL;
		layoutData.grabExcessVerticalSpace = true;
		layoutData.heightHint = 40;

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
		Button nuovoRuolo = new Button(cmp, SWT.NONE);
		nuovoRuolo.setText("Nuovo Ruolo");
		nuovoRuolo.setFont(font);
		nuovoRuolo.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				cmpNuovoRuolo.setVisible(true);
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {

			}
		});

		Button modificaRuolo = new Button(cmp, SWT.NONE);
		modificaRuolo.setText("Modifica Ruolo");
		modificaRuolo.setFont(font);
		modificaRuolo.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				cmpModificaRoulo.setVisible(true);
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {

			}
		});
		cmpNuovoRuolo = new Composite(this, SWT.BORDER);
		GridData gdCmpNuovoRuolo = new GridData(SWT.BORDER);
		gdCmpNuovoRuolo.grabExcessHorizontalSpace = true;
		gdCmpNuovoRuolo.grabExcessVerticalSpace = true;
		gdCmpNuovoRuolo.horizontalAlignment = SWT.FILL;
		gdCmpNuovoRuolo.verticalAlignment = SWT.FILL;
		cmpNuovoRuolo.setLayoutData(gdCmpNuovoRuolo);
		// cmp.setLayoutData(new GridData());
		cmpNuovoRuolo.setLayout(new GridLayout(2, true));
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
		tblFunzioni = new Table(cmpNuovoRuolo, SWT.BORDER | SWT.FULL_SELECTION
				| SWT.V_SCROLL | SWT.MULTI | SWT.H_SCROLL);
		tblFunzioni.setHeaderVisible(true);
		tblFunzioni.setToolTipText("Funzioni - selezione multipla");
		tblFunzioni.setLinesVisible(true);
		riempiTabellaEntita(tblFunzioni, FunzioneDAO.getAllFunzioni(),"");
		tblFunzioni.getColumn(0).setWidth(0);
		tblFunzioni.setFont(font);
		tblFunzioni.setLayoutData(gdTables);
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
		cmpModificaRoulo = new Composite(this, SWT.BORDER);
		GridData gdCmp1 = new GridData(SWT.BORDER);
		gdCmp1.grabExcessHorizontalSpace = true;
		gdCmp1.grabExcessVerticalSpace = true;
		gdCmp1.horizontalAlignment = SWT.FILL;
		gdCmp1.verticalAlignment = SWT.FILL;
		cmpModificaRoulo.setLayoutData(gdCmp1);
		// cmp.setLayoutData(new GridData());
		cmpModificaRoulo.setLayout(new GridLayout(2, true));
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
		Label lblFunz = new Label(cmpModificaRoulo, SWT.NONE | SWT.BOLD);
		lblFunz.setText("Seleziona le funzioni");
		lblFunz.setBackground(white);
		lblFunz.setLayoutData(gdLbl);
		lblFunz.setFont(font);
		tblFunzioniUp = new Table(cmpModificaRoulo, SWT.BORDER
				| SWT.FULL_SELECTION | SWT.V_SCROLL | SWT.MULTI | SWT.H_SCROLL);
		tblFunzioniUp.setHeaderVisible(true);
		tblFunzioniUp.setToolTipText("Funzioni - selezione multipla");
		tblFunzioniUp.setLinesVisible(true);
		tblFunzioniUp.setLayoutData(gdTables);
		riempiTabellaEntita(tblFunzioniUp, FunzioneDAO.getAllFunzioni(),"");
		tblFunzioniUp.getColumn(0).setWidth(0);
		tblFunzioniUp.setFont(font);
		Button modifica = new Button(cmpModificaRoulo, SWT.NONE);
		modifica.setText("Modifica");
		modifica.setFont(font);
		modifica.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				modificaRuolo();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {

			}
		});
		cmpNuovoRuolo.setVisible(false);
		cmpModificaRoulo.setVisible(false);
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

	private void modificaRuolo() {
		if (tblFunzioniUp.getSelectionIndices().length == 0) {
			Utils.showMessageError("Selezioanre almeno una funzione");
			return;
		}
		Ruolo ruolo = RuoloDAO.get(ruoloCombo.getText());
		if (ruolo == null) {
			Utils.showMessageError("Ruolo non esistente");
		} else {
			if (RuoloDAO
					.updateRuolo(ruolo, tblFunzioniUp.getSelectionIndices())) {
				Utils.showMessageInfo("Operazione eseguita con successo");
				profilo.add(nomeRuolo.getText());
			} else {
				Utils.showMessageError("Modifica non riuscita");
			}
		}
	}

	private void insertRuolo() {
		if (tblFunzioni.getSelectionIndices().length == 0) {
			Utils.showMessageError("Selezioanre almeno una funzione");
			return;
		}
		if (RuoloDAO.get(nomeRuolo.getText()) != null) {
			MessageBox msg = new MessageBox(new Shell());
			msg.setMessage("Ruolo già esistente");
			msg.setText("Errore");
			msg.open();
		} else {
			if (RuoloDAO.insRuolo(nomeRuolo.getText(), tblFunzioni
					.getSelectionIndices())) {
				MessageBox msg = new MessageBox(new Shell());
				msg.setMessage("Operazione eseguita con successo");
				profilo.add(nomeRuolo.getText());
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
}
