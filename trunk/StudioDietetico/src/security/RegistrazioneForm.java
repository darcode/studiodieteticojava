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
	private Text password;
	private Text utente;
	private Combo profilo;
	private Text nomeRuolo;
	private Table tblFunzioni;
	private Composite cmpNuovoRuolo;

	public RegistrazioneForm(Composite parent, int style) {
		super(parent, style);

		GridData gdForm = new GridData(SWT.NONE);
		gdForm.grabExcessHorizontalSpace = true;
		gdForm.horizontalAlignment = SWT.FILL;
		gdForm.grabExcessVerticalSpace = true;
		gdForm.verticalAlignment = SWT.FILL;
		this.setLayoutData(gdForm);
		GridLayout glForm = new GridLayout(1, false);
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
		gdLbl.verticalAlignment = SWT.CENTER;
		gdLbl.horizontalAlignment = SWT.FILL;
		gdLbl.grabExcessVerticalSpace = true;

		Label lbl2 = new Label(cmp, SWT.NONE | SWT.BOLD);
		lbl2.setText("UTENTE");
		lbl2.setBackground(white);
		lbl2.setLayoutData(gdLbl);
		lbl2.setFont(font);
		utente = new Text(cmp, SWT.BORDER);
		GridData layoutData = new GridData(SWT.FILL);
		layoutData.grabExcessHorizontalSpace = true;
		layoutData.verticalAlignment = SWT.CENTER;
		layoutData.horizontalAlignment = SWT.FILL;
		layoutData.grabExcessVerticalSpace = true;

		utente.setLayoutData(layoutData);
		Label lbl = new Label(cmp, SWT.NONE | SWT.BOLD);
		lbl.setText("PASSWORD");
		lbl.setBackground(white);
		lbl.setLayoutData(gdLbl);
		lbl.setFont(font);
		password = new Text(cmp, SWT.PASSWORD | SWT.BORDER);
		password.setLayoutData(layoutData);

		Label lblPrfilo = new Label(cmp, SWT.NONE);
		lblPrfilo.setText("Profilo");
		lblPrfilo.setLayoutData(gdLbl);
		lblPrfilo.setBackground(white);
		profilo = new Combo(cmp, SWT.BORDER);
		for (Ruolo item : RuoloDAO.getAllRoules()) {
			profilo.add(item.getDescrizione());
		}
		lblPrfilo.setFont(font);

		Button registra = new Button(cmp, SWT.NONE);
		registra.setText("Registra");
		registra.setFont(font);
		registra.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				abilita(utente.getText(), password.getText().trim(),
						profilo.getText());
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {

			}
		});
		Button nuovoRuolo = new Button(cmp, SWT.NONE);
		nuovoRuolo.setText("Nuovo ruolo");
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

		cmpNuovoRuolo = new Composite(this, SWT.BORDER);
		GridData gdCmpNuovoRuolo = new GridData(SWT.BORDER);
		gdCmpNuovoRuolo.grabExcessHorizontalSpace = true;
		gdCmpNuovoRuolo.grabExcessVerticalSpace = true;
		gdCmpNuovoRuolo.horizontalAlignment = SWT.CENTER;
		gdCmpNuovoRuolo.verticalAlignment = SWT.CENTER;
		cmpNuovoRuolo.setLayoutData(gdCmpNuovoRuolo);
		// cmp.setLayoutData(new GridData());
		cmpNuovoRuolo.setLayout(new GridLayout(2, true));
		cmpNuovoRuolo.setBackground(white);
		cmpNuovoRuolo.setVisible(false);
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
		ggTxtNomeRUolo.verticalAlignment = SWT.CENTER;
		nomeRuolo.setLayoutData(ggTxtNomeRUolo);
		Label lblFunzioni = new Label(cmpNuovoRuolo, SWT.NONE | SWT.BOLD);
		lblFunzioni.setText("Seleziona le funzioni");
		lblFunzioni.setBackground(white);
		lblFunzioni.setLayoutData(gdLbl);
		lblFunzioni.setFont(font);
		tblFunzioni = new Table(cmpNuovoRuolo,  SWT.BORDER
				| SWT.FULL_SELECTION | SWT.V_SCROLL | SWT.MULTI);
		tblFunzioni.setHeaderVisible(true);
		tblFunzioni.setToolTipText("Funzioni - selezione multipla");
		tblFunzioni.setLinesVisible(true);
		riempiTabellaEntita(tblFunzioni, FunzioneDAO.getAllFunzioni());
		tblFunzioni.getColumn(0).setWidth(0);
		
		Button inserisci = new Button(cmpNuovoRuolo, SWT.NONE);
		inserisci.setText("Inserisci");
		inserisci.setFont(font);
		inserisci.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				if(RuoloDAO.insRuolo(nomeRuolo.getText(), tblFunzioni
						.getSelectionIndices())){
					MessageBox msg = new MessageBox(new Shell());
					msg.setMessage("Operazione eseguita con successo");
					msg.setText("Info");
					msg.open();
				} else {
					MessageBox msg = new MessageBox(new Shell());
					msg.setMessage("Inserimento non riuscito");
					msg.setText("Errore");
					msg.open();
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {

			}
		});
	}

	private void abilita(String utente, String password, String descrRuolo) {
		try {
			Utente newUtente = new Utente(RuoloDAO.get(descrRuolo), utente, password);
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
}
