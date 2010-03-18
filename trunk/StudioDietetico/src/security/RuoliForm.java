package security;

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
	private Table tblFunzioni;
	private Composite cmpNuovoRuolo;
	private Table tblFunzioniUp;
	private Combo ruoloCombo;
	private Composite cmpModificaRoulo;

	public RuoliForm(Composite parent, int style) {
		super(parent, style);

		GridData gdLbl = new GridData(SWT.BORDER);
		gdLbl.grabExcessHorizontalSpace = true;
		gdLbl.verticalAlignment = SWT.CENTER;
		gdLbl.horizontalAlignment = SWT.FILL;
		gdLbl.grabExcessVerticalSpace = true;

		cmpNuovoRuolo = new Composite(this, SWT.BORDER);
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
		tblFunzioni = new Table(cmpNuovoRuolo, SWT.BORDER | SWT.FULL_SELECTION
				| SWT.V_SCROLL | SWT.MULTI | SWT.H_SCROLL);
		tblFunzioni.setHeaderVisible(true);
		tblFunzioni.setToolTipText("Funzioni - selezione multipla");
		tblFunzioni.setLinesVisible(true);
		riempiTabellaEntita(tblFunzioni, FunzioneDAO.getAllFunzioni(), "");
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
		GridData gdIns = new GridData();
		gdIns.horizontalSpan = 2;
		gdIns.horizontalAlignment = SWT.CENTER;
		inserisci.setLayoutData(gdIns);
		cmpModificaRoulo = new Composite(this, SWT.BORDER);
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
		riempiTabellaEntita(tblFunzioniUp, FunzioneDAO.getAllFunzioni(), "");
		tblFunzioniUp.getColumn(0).setWidth(0);
		tblFunzioniUp.setFont(font);
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

			}
		});
		GridData gdForm = new GridData(SWT.NONE);
		gdForm.grabExcessVerticalSpace = true;
		gdForm.grabExcessHorizontalSpace = true;
		gdForm.horizontalAlignment = SWT.FILL;
		gdForm.verticalAlignment = SWT.FILL;
		this.setLayoutData(gdForm);
		GridLayout glForm = new GridLayout(2, false);
		this.setLayout(glForm);

		this.setBackground(white);
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
			Utils.showMessageError("Selezionare almeno una funzione");
			return;
		}
		Ruolo ruolo = RuoloDAO.get(ruoloCombo.getText());
		System.out.println(ruolo.getIdRuolo());
		if (ruolo == null) {
			Utils.showMessageError("Ruolo non esistente");
		} else {
			if (RuoloDAO
					.updateRuolo(ruolo, tblFunzioniUp.getSelectionIndices())) {
				Utils.showMessageInfo("Operazione eseguita con successo");
			} else {
				Utils.showMessageError("Modifica non riuscita");
			}
		}
	}

	private void insertRuolo() {
		if (tblFunzioni.getSelectionIndices().length == 0) {
			Utils.showMessageError("Selezionare almeno una funzione");
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
