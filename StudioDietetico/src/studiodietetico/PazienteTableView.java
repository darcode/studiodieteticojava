package studiodietetico;

import hibernate.Paziente;
import hibernate.Prenotazione;
import hibernate.Tipologiavisita;

import java.util.ArrayList;
import java.util.Date;

import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.part.ViewPart;

import command.PazienteDAO;
import command.UtenteDAO;
import command.VisitaDAO;

import org.eclipse.swt.widgets.Button;

import security.IFunzioniConstants;
import service.Utils;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.DateTime;

public class PazienteTableView extends ViewPart {
	private Composite top = null;
	private TableForm classVis;
	private ArrayList<Object> pazienti;
	private Button buttonPrenotaVisita = null;
	private Shell sShellMessElimina;
	public static Paziente pazienteSel = null;
	private Shell sShellInserisciPaziente = null; // @jve:decl-index=0:visual-constraint="28,438"
	private Label labelCognPaz = null;
	private Text textCognPaz = null;
	private Label labelNomePaz = null;
	private Text textNomePaz = null;
	private Label labelSessoPaz = null;
	private Button radioButtonM = null;
	private Button radioButtonF = null;
	private Label labelDataNascPaz = null;
	private Combo comboGiorno = null;
	private Combo comboMese = null;
	private Combo comboAnno = null;
	private Label labelCodFiscPaz = null;
	private Text textCodFiscPaz = null;
	private Label labelIndirizzoPaz = null;
	private Text textIndirizzoPaz = null;
	private Label labelCittPaz = null;
	private Text textCittPaz = null;
	private Label labelCAP = null;
	private Text textCAP = null;
	private Label labelProvincia = null;
	private Text textProvincia = null;
	private Label labelProfessPaz = null;
	private Text textProfessPaz = null;
	private Label labelTel1Paz = null;
	private Text textTel1Paz = null;
	private Label labelTel2Paz = null;
	private Text textTel2Paz = null;
	private Label labelEmailPaz = null;
	private Text textEmailPaz = null;
	private Label labelNumTessPaz = null;
	private Text textNumTessPaz = null;
	private Label labelNotePaz = null;
	private Text textAreaNotePaz = null;
	private Button buttonRegistraPaz = null;
	private Button buttonAggiornaPaz = null;
	private Button buttonAnnulla = null;
	private Shell sShellPrenotaVisita = null; // @jve:decl-index=0:visual-constraint="843,157"
	private Label labelTipolVisitPrenot = null;
	private Label labelDataPrenotVisita = null;
	private Label labelNote = null;
	private Text textAreaNote = null;
	private Button buttonPrenVisita = null;
	private Button buttonCreaPrescrizione = null;
	private Button buttonSelezionaData = null;
	private Table tableTipVisita = null;
	private Button buttonCreaTipVisita = null;
	private Button buttonAnnullaCreaTip = null;
	private Shell ShellCalendario = null; // @jve:decl-index=0:visual-constraint="487,568"
	private DateTime calendar = null;
	private DateTime time = null;
	private Text textAreaPrenotazioniOdierne = null;
	private Button ok = null;
	private Shell sShellCreaTipVisita = null; // @jve:decl-index=0:visual-constraint="786,569"
	private Label labelTipologia = null;
	private Text textTipologia = null;
	private Label labelCosto = null;
	private Text textCosto = null;
	private Button buttonOk = null;
	private Date dn = null;
	private ArrayList<Tipologiavisita> tv; // @jve:decl-index=0:
	private Button buttonGestioneAnamnesi;
	private Button buttonRilevamentoParametri;
	private Button buttonRilevamentoEsami;

	public PazienteTableView() {
	}

	@Override
	public void createPartControl(Composite parent) {
		top = new Composite(parent, SWT.NONE);
		GridData gdForm = new GridData(SWT.NONE);
		gdForm.grabExcessVerticalSpace = true;
		gdForm.grabExcessHorizontalSpace = true;
		gdForm.horizontalAlignment = SWT.FILL;
		gdForm.grabExcessVerticalSpace = true;
		gdForm.verticalAlignment = SWT.FILL;
		top.setLayoutData(gdForm);
		GridLayout glForm = new GridLayout(1, false);
		top.setLayout(glForm);
		pazienti = PazienteDAO.getPazientiObject();
		PazienteDAO pd = new PazienteDAO();
		classVis = new TableForm(top, SWT.BORDER, pazienti,
				"createShellDettagliPaziente", "createSShellInserisciPaziente",
				PazienteTableView.this, pd, "PazienteTableView");
		classVis.setBounds(new Rectangle(6, 50, 800, 400));
		classVis.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		classVis.setLayout(new GridLayout(1, true));
		classVis.setBackground(Utils.getStandardWhiteColor());
		buttonPrenVisita = new Button(classVis.top, SWT.NONE);
//		GESTIONE SICUREZZA - RUOLO
		buttonPrenVisita.setEnabled(UtenteDAO.hasFunction(IFunzioniConstants.FUNZIONE_REGISTRA_VISITA));
		buttonPrenVisita.setBounds(new Rectangle(250, 352, 105, 25));
		buttonPrenVisita.setText("Prenotazione visita");
		buttonPrenVisita
				.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
					public void widgetSelected(
							org.eclipse.swt.events.SelectionEvent e) {
						if (classVis.getTableVisualizzazione()
								.getSelectionCount() > 0) {
							TableItem itemSel = classVis
									.getTableVisualizzazione().getItem(
											classVis.getTableVisualizzazione()
													.getSelectionIndex());
							int idPazienteSel = Integer.parseInt(itemSel
									.getText(0));
							pazienteSel = PazienteDAO
									.getPazienteByID(idPazienteSel);
							createSShellPrenotaVisita();
						} else {
							createMessSelElemCanc();
						}
					}
				});

		buttonCreaPrescrizione = new Button(classVis.top, SWT.NONE);
		buttonCreaPrescrizione.setBounds(new Rectangle(355, 352, 100, 25));
		buttonCreaPrescrizione.setText("Prescrizione dieta");
		buttonCreaPrescrizione.setEnabled(UtenteDAO.hasFunction(IFunzioniConstants.FUNZIONE_DIETA));
		buttonCreaPrescrizione
				.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
					public void widgetSelected(
							org.eclipse.swt.events.SelectionEvent e) {
						if (classVis.getTableVisualizzazione()
								.getSelectionCount() > 0) {
							TableItem itemSel = classVis
									.getTableVisualizzazione().getItem(
											classVis.getTableVisualizzazione()
													.getSelectionIndex());
							int idPazienteSel = Integer.parseInt(itemSel
									.getText(0));
							pazienteSel = PazienteDAO
									.getPazienteByID(idPazienteSel);
							InserisciPrescrizioneShell preShell = new InserisciPrescrizioneShell();
							preShell.createShellInserisciPrescrizione();
						} else {
							createMessSelElemCanc();
						}
					}
				});

		buttonGestioneAnamnesi = new Button(classVis.top, SWT.NONE);
		buttonGestioneAnamnesi.setBounds(new Rectangle(455, 352, 65, 25));
		buttonGestioneAnamnesi.setText("Anamnesi");
		buttonGestioneAnamnesi.setEnabled(UtenteDAO.hasFunction(IFunzioniConstants.FUNZIONE_ANAMNESI));
		buttonGestioneAnamnesi
				.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
					public void widgetSelected(
							org.eclipse.swt.events.SelectionEvent e) {
						if (classVis.getTableVisualizzazione()
								.getSelectionCount() > 0) {
							TableItem itemSel = classVis
									.getTableVisualizzazione().getItem(
											classVis.getTableVisualizzazione()
													.getSelectionIndex());
							int idPazienteSel = Integer.parseInt(itemSel
									.getText(0));
							pazienteSel = PazienteDAO
									.getPazienteByID(idPazienteSel);
							Utils
									.showViewNotUnique("StudioDietetico.AnamnesiTableView");
							// Utils.showView("StudioDietetico.AnamnesiTableView");
							AnamnesiTTableView.selectTab(0);
						} else {
							createMessSelElemCanc();
						}
					}
				});

		buttonRilevamentoParametri = new Button(classVis.top, SWT.NONE);
		buttonRilevamentoParametri.setBounds(new Rectangle(520, 352, 140, 25));
		buttonRilevamentoParametri.setText("Parametri antropometrici");
		buttonRilevamentoParametri.setEnabled(UtenteDAO.hasFunction(IFunzioniConstants.FUNZIONE_ANAMNESI));
		
		buttonRilevamentoParametri
				.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
					public void widgetSelected(
							org.eclipse.swt.events.SelectionEvent e) {
						if (classVis.getTableVisualizzazione()
								.getSelectionCount() > 0) {
							TableItem itemSel = classVis
									.getTableVisualizzazione().getItem(
											classVis.getTableVisualizzazione()
													.getSelectionIndex());
							int idPazienteSel = Integer.parseInt(itemSel
									.getText(0));
							pazienteSel = PazienteDAO
									.getPazienteByID(idPazienteSel);
							Utils
									.showViewNotUnique("StudioDietetico.RilevazioneParametroAntroView");
							// Utils.showView("StudioDietetico.RilevazioneParametroAntroView");
						} else {
							createMessSelElemCanc();
						}
					}
				});

		buttonRilevamentoEsami = new Button(classVis.top, SWT.NONE);
		buttonRilevamentoEsami.setBounds(new Rectangle(660, 352, 90, 25));
		buttonRilevamentoEsami.setText("Esami clinici");
		buttonRilevamentoEsami.setEnabled(UtenteDAO.hasFunction(IFunzioniConstants.FUNZIONE_ANAMNESI));
		
		buttonRilevamentoEsami
				.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
					public void widgetSelected(
							org.eclipse.swt.events.SelectionEvent e) {
						if (classVis.getTableVisualizzazione()
								.getSelectionCount() > 0) {
							TableItem itemSel = classVis
									.getTableVisualizzazione().getItem(
											classVis.getTableVisualizzazione()
													.getSelectionIndex());
							int idPazienteSel = Integer.parseInt(itemSel
									.getText(0));
							pazienteSel = PazienteDAO
									.getPazienteByID(idPazienteSel);
							Utils
									.showViewNotUnique("StudioDietetico.RisultatoAnalisiView");
							// Utils.showView("StudioDietetico.RisultatoAnalisiView");
						} else {
							createMessSelElemCanc();
						}
					}
				});

		classVis
				.nascondiColonne(new int[] { 0, 1, 8, 9, 10, 11, 12, 13, 14, 15 });

		classVis.aggiornaCombo();

		classVis.ordinamentoData(classVis.getTableVisualizzazione(), 4);
		classVis.ordinamentoStringhe(classVis.getTableVisualizzazione(), 2);
		classVis.ordinamentoStringhe(classVis.getTableVisualizzazione(), 3);
		classVis.ordinamentoStringhe(classVis.getTableVisualizzazione(), 5);
		classVis.ordinamentoStringhe(classVis.getTableVisualizzazione(), 6);
		classVis.ordinamentoStringhe(classVis.getTableVisualizzazione(), 7);

	}

	@Override
	public void setFocus() {
		classVis.setFocus();
	}

	private void createMessSelElemCanc() {
		createSShellMessElimina();
		MessageBox messageBox = new MessageBox(sShellMessElimina, SWT.OK
				| SWT.ICON_ERROR);
		messageBox.setMessage("Selezionare un elemento dalla tabella");
		messageBox.setText("Errore: elemento non selezionato");
		if (messageBox.open() == SWT.OK) {
			sShellMessElimina.close();
		}
	}

	private void createSShellMessElimina() {
		sShellMessElimina = new Shell(SWT.APPLICATION_MODAL | SWT.SHELL_TRIM);
		sShellMessElimina.setLayout(new GridLayout());
		sShellMessElimina.setSize(new Point(377, 72));
	}

	public void createSShellInserisciPaziente() {
		sShellInserisciPaziente = new Shell(SWT.APPLICATION_MODAL
				| SWT.SHELL_TRIM);
		// sShellInserisciPaziente.setLayout(new GridLayout());
		sShellInserisciPaziente.setSize(new Point(443, 430));
		sShellInserisciPaziente.setText("Registra un nuovo paziente");

		labelCognPaz = new Label(sShellInserisciPaziente, SWT.NONE);
		labelCognPaz.setBounds(new Rectangle(6, 7, 67, 20));
		labelCognPaz.setText("* Cognome");
		textCognPaz = new Text(sShellInserisciPaziente, SWT.BORDER);
		textCognPaz.setBounds(new Rectangle(75, 6, 158, 21));
		labelNomePaz = new Label(sShellInserisciPaziente, SWT.NONE);
		labelNomePaz.setBounds(new Rectangle(7, 32, 59, 16));
		labelNomePaz.setText("* Nome");
		textNomePaz = new Text(sShellInserisciPaziente, SWT.BORDER);
		textNomePaz.setBounds(new Rectangle(76, 30, 156, 21));
		labelSessoPaz = new Label(sShellInserisciPaziente, SWT.NONE);
		labelSessoPaz.setBounds(new Rectangle(7, 56, 60, 18));
		labelSessoPaz.setText("* Sesso");
		radioButtonM = new Button(sShellInserisciPaziente, SWT.RADIO);
		radioButtonM.setBounds(new Rectangle(76, 55, 32, 16));
		radioButtonM.setText("M");
		radioButtonF = new Button(sShellInserisciPaziente, SWT.RADIO);
		radioButtonF.setBounds(new Rectangle(113, 55, 33, 16));
		radioButtonF.setText("F");
		labelDataNascPaz = new Label(sShellInserisciPaziente, SWT.NONE);
		labelDataNascPaz.setBounds(new Rectangle(9, 79, 94, 21));
		labelDataNascPaz.setText("* Data di nascita");
		createComboGiorno();
		createComboMese();
		createComboAnno();
		labelCodFiscPaz = new Label(sShellInserisciPaziente, SWT.NONE);
		labelCodFiscPaz.setBounds(new Rectangle(10, 105, 92, 21));
		labelCodFiscPaz.setText("* Codice Fiscale");
		textCodFiscPaz = new Text(sShellInserisciPaziente, SWT.BORDER);
		textCodFiscPaz.setBounds(new Rectangle(112, 106, 147, 21));
		textCodFiscPaz.setTextLimit(16);
		labelIndirizzoPaz = new Label(sShellInserisciPaziente, SWT.NONE);
		labelIndirizzoPaz.setBounds(new Rectangle(11, 133, 55, 20));
		labelIndirizzoPaz.setText("Indirizzo");
		textIndirizzoPaz = new Text(sShellInserisciPaziente, SWT.BORDER);
		textIndirizzoPaz.setBounds(new Rectangle(75, 133, 168, 21));
		labelCittPaz = new Label(sShellInserisciPaziente, SWT.NONE);
		labelCittPaz.setBounds(new Rectangle(12, 158, 50, 17));
		labelCittPaz.setText("Città");
		textCittPaz = new Text(sShellInserisciPaziente, SWT.BORDER);
		textCittPaz.setBounds(new Rectangle(74, 157, 170, 20));
		labelCAP = new Label(sShellInserisciPaziente, SWT.NONE);
		labelCAP.setBounds(new Rectangle(265, 157, 42, 19));
		labelCAP.setText("CAP");
		textCAP = new Text(sShellInserisciPaziente, SWT.BORDER);
		textCAP.setBounds(new Rectangle(312, 156, 76, 21));
		textCAP.setTextLimit(5);
		labelProvincia = new Label(sShellInserisciPaziente, SWT.NONE);
		labelProvincia.setBounds(new Rectangle(10, 179, 51, 21));
		labelProvincia.setText("Provincia");
		textProvincia = new Text(sShellInserisciPaziente, SWT.BORDER);
		textProvincia.setBounds(new Rectangle(73, 180, 72, 18));
		textProvincia.setTextLimit(2);
		labelProfessPaz = new Label(sShellInserisciPaziente, SWT.NONE);
		labelProfessPaz.setBounds(new Rectangle(9, 204, 67, 21));
		labelProfessPaz.setText("Professione");
		textProfessPaz = new Text(sShellInserisciPaziente, SWT.BORDER);
		textProfessPaz.setBounds(new Rectangle(81, 205, 97, 16));
		labelTel1Paz = new Label(sShellInserisciPaziente, SWT.NONE);
		labelTel1Paz.setBounds(new Rectangle(9, 228, 68, 21));
		labelTel1Paz.setText("Telefono 1");
		textTel1Paz = new Text(sShellInserisciPaziente, SWT.BORDER);
		textTel1Paz.setBounds(new Rectangle(82, 228, 104, 18));
		labelTel2Paz = new Label(sShellInserisciPaziente, SWT.NONE);
		labelTel2Paz.setBounds(new Rectangle(220, 227, 57, 18));
		labelTel2Paz.setText("Telefono 2");
		textTel2Paz = new Text(sShellInserisciPaziente, SWT.BORDER);
		textTel2Paz.setBounds(new Rectangle(290, 228, 98, 17));
		labelEmailPaz = new Label(sShellInserisciPaziente, SWT.NONE);
		labelEmailPaz.setBounds(new Rectangle(9, 255, 52, 15));
		labelEmailPaz.setText("E-mail");
		textEmailPaz = new Text(sShellInserisciPaziente, SWT.BORDER);
		textEmailPaz.setBounds(new Rectangle(74, 254, 109, 22));
		labelNumTessPaz = new Label(sShellInserisciPaziente, SWT.NONE);
		labelNumTessPaz.setBounds(new Rectangle(10, 280, 123, 17));
		labelNumTessPaz.setText("Num Tessera sanitaria");
		textNumTessPaz = new Text(sShellInserisciPaziente, SWT.BORDER);
		textNumTessPaz.setBounds(new Rectangle(143, 278, 121, 20));
		labelNotePaz = new Label(sShellInserisciPaziente, SWT.NONE);
		labelNotePaz.setBounds(new Rectangle(8, 303, 38, 15));
		labelNotePaz.setText("Note");
		textAreaNotePaz = new Text(sShellInserisciPaziente, SWT.MULTI
				| SWT.WRAP | SWT.V_SCROLL);
		textAreaNotePaz.setBounds(new Rectangle(61, 301, 255, 61));
		buttonRegistraPaz = new Button(sShellInserisciPaziente, SWT.NONE);
		buttonRegistraPaz.setBounds(new Rectangle(321, 364, 102, 25));
		buttonRegistraPaz.setText("Registra paziente");
		buttonAnnulla = new Button(sShellInserisciPaziente, SWT.NONE);
		buttonAnnulla.setBounds(new Rectangle(212, 364, 102, 25));
		buttonAnnulla.setText("Chiudi");
		buttonAnnulla
				.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
					public void widgetSelected(
							org.eclipse.swt.events.SelectionEvent e) {
						sShellInserisciPaziente.close();
					}
				});
		buttonRegistraPaz
				.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
					public void widgetSelected(
							org.eclipse.swt.events.SelectionEvent e) {
						String dateString = comboAnno.getText() + "-"
								+ comboMese.getText() + "-"
								+ comboGiorno.getText();
						String formato = "yyyy-MM-dd";
						Date dn = Utils
								.convertStringToDate(dateString, formato);
						PazienteDAO p = new PazienteDAO();
						if (radioButtonM.getSelection()) {
							p.registraPaziente(textCognPaz.getText(),
									textNomePaz.getText(), radioButtonM
											.getText(), dn, textCodFiscPaz
											.getText(), textIndirizzoPaz
											.getText(), textCittPaz.getText(),
									textCAP.getText(), textProvincia.getText(),
									textProfessPaz.getText(), textTel1Paz
											.getText(), textTel2Paz.getText(),
									textEmailPaz.getText(), textNumTessPaz
											.getText(), textAreaNotePaz
											.getText());
						} else if (radioButtonF.getSelection()) {
							p.registraPaziente(textCognPaz.getText(),
									textNomePaz.getText(), radioButtonF
											.getText(), dn, textCodFiscPaz
											.getText(), textIndirizzoPaz
											.getText(), textCittPaz.getText(),
									textCAP.getText(), textProvincia.getText(),
									textProfessPaz.getText(), textTel1Paz
											.getText(), textTel2Paz.getText(),
									textEmailPaz.getText(), textNumTessPaz
											.getText(), textAreaNotePaz
											.getText());

						}

						classVis.getTableVisualizzazione().removeAll(); // rimuove
																		// le
																		// righe
						// rimuove le colonne
						int k = 0;
						while (k < classVis.getTableVisualizzazione()
								.getColumnCount()) {
							classVis.getTableVisualizzazione().getColumn(k)
									.dispose();
						}
						pazienti = PazienteDAO.getPazientiObject();
						classVis.riempiTabella(pazienti, "PazienteTableView");
						classVis.nascondiColonne(new int[] { 0, 1, 8, 9, 10,
								11, 12, 13, 14, 15 });
						classVis.aggiornaCombo();
						classVis.ordinamentoData(classVis
								.getTableVisualizzazione(), 4);
						classVis.ordinamentoStringhe(classVis
								.getTableVisualizzazione(), 2);
						classVis.ordinamentoStringhe(classVis
								.getTableVisualizzazione(), 3);
						classVis.ordinamentoStringhe(classVis
								.getTableVisualizzazione(), 5);
						classVis.ordinamentoStringhe(classVis
								.getTableVisualizzazione(), 6);
						classVis.ordinamentoStringhe(classVis
								.getTableVisualizzazione(), 7);

						sShellInserisciPaziente.close();
					}
				});

		sShellInserisciPaziente.open();
	}

	/**
	 * This method initializes comboGiorno
	 * 
	 */
	private void createComboGiorno() {
		comboGiorno = new Combo(sShellInserisciPaziente, SWT.READ_ONLY);
		comboGiorno.setBounds(new Rectangle(109, 78, 60, 23));
		for (int i = 1; i < 32; i++) {
			comboGiorno.add("" + i);
		}
		comboGiorno.setText(comboGiorno.getItem(0));

	}

	/**
	 * This method initializes comboMese
	 * 
	 */
	private void createComboMese() {
		comboMese = new Combo(sShellInserisciPaziente, SWT.READ_ONLY);
		comboMese.setBounds(new Rectangle(180, 77, 59, 23));
		for (int i = 1; i < 13; i++) {
			comboMese.add("" + i);
		}
		comboMese.setText(comboMese.getItem(0));
	}

	/**
	 * This method initializes comboAnno
	 * 
	 */
	private void createComboAnno() {
		comboAnno = new Combo(sShellInserisciPaziente, SWT.READ_ONLY);
		comboAnno.setBounds(new Rectangle(251, 77, 61, 23));
		Date now = new Date();
		// System.out.println(now.getYear()+1900);
		for (int i = 1900; i < (now.getYear() + 1901); i++) {
			comboAnno.add("" + i);
		}
		comboAnno.setText(comboAnno.getItem(0));
	}

	public void createShellDettagliPaziente(final TableItem rigaTableClick) {
		createSShellInserisciPaziente();
		int idPaz = Integer.parseInt(rigaTableClick.getText(0));
		final Paziente paz = PazienteDAO.getPazienteByID(idPaz);
		sShellInserisciPaziente.setText("Dettagli paziente");
		buttonRegistraPaz.setEnabled(false);
		buttonRegistraPaz.setVisible(false);
		buttonAggiornaPaz = new Button(sShellInserisciPaziente, SWT.NONE);
		buttonAggiornaPaz.setBounds(new Rectangle(321, 364, 102, 25));
		buttonAggiornaPaz.setText("Aggiorna dati");
		buttonAggiornaPaz
				.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
					public void widgetSelected(
							org.eclipse.swt.events.SelectionEvent e) {
						String dateString = comboAnno.getText() + "-"
								+ comboMese.getText() + "-"
								+ comboGiorno.getText();
						String formato = "yyyy-MM-dd";
						Date dn = Utils
								.convertStringToDate(dateString, formato);
						PazienteDAO p = new PazienteDAO();
						if (radioButtonM.getSelection()) {
							p.aggiornaPaziente(paz, textCognPaz.getText(),
									textNomePaz.getText(), radioButtonM
											.getText(), dn, textCodFiscPaz
											.getText(), textIndirizzoPaz
											.getText(), textCittPaz.getText(),
									textCAP.getText(), textProvincia.getText(),
									textProfessPaz.getText(), textTel1Paz
											.getText(), textTel2Paz.getText(),
									textEmailPaz.getText(), textNumTessPaz
											.getText(), textAreaNotePaz
											.getText());
						} else if (radioButtonF.getSelection()) {
							p.aggiornaPaziente(paz, textCognPaz.getText(),
									textNomePaz.getText(), radioButtonF
											.getText(), dn, textCodFiscPaz
											.getText(), textIndirizzoPaz
											.getText(), textCittPaz.getText(),
									textCAP.getText(), textProvincia.getText(),
									textProfessPaz.getText(), textTel1Paz
											.getText(), textTel2Paz.getText(),
									textEmailPaz.getText(), textNumTessPaz
											.getText(), textAreaNotePaz
											.getText());

						}

						classVis.getTableVisualizzazione().removeAll(); // rimuove
																		// le
																		// righe
						// rimuove le colonne
						int k = 0;
						while (k < classVis.getTableVisualizzazione()
								.getColumnCount()) {
							classVis.getTableVisualizzazione().getColumn(k)
									.dispose();
						}
						pazienti = PazienteDAO.getPazientiObject();
						classVis.riempiTabella(pazienti, "PazienteTableView");
						classVis.nascondiColonne(new int[] { 0, 1, 8, 9, 10,
								11, 12, 13, 14, 15 });
						classVis.aggiornaCombo();
						classVis.ordinamentoData(classVis
								.getTableVisualizzazione(), 4);
						classVis.ordinamentoStringhe(classVis
								.getTableVisualizzazione(), 2);
						classVis.ordinamentoStringhe(classVis
								.getTableVisualizzazione(), 3);
						classVis.ordinamentoStringhe(classVis
								.getTableVisualizzazione(), 5);
						classVis.ordinamentoStringhe(classVis
								.getTableVisualizzazione(), 6);
						classVis.ordinamentoStringhe(classVis
								.getTableVisualizzazione(), 7);

						sShellInserisciPaziente.close();
					}

				});
		textCognPaz.setText(paz.getCognome());
		textNomePaz.setText(paz.getNome());
		if (paz.getSesso() == 'M') {
			radioButtonM.setSelection(true);
		} else {
			radioButtonF.setSelection(true);
		}
		comboGiorno.select(paz.getDataNascita().getDate() - 1);
		comboMese.select(paz.getDataNascita().getMonth());
		comboAnno.select(paz.getDataNascita().getYear());
		textCodFiscPaz.setText(paz.getCodiceFiscale());
		textIndirizzoPaz.setText(paz.getIndirizzo());
		textCittPaz.setText(paz.getCitta());
		textCAP.setText(paz.getCap());
		textProvincia.setText(paz.getProvincia());
		textProfessPaz.setText(paz.getProfessione());
		textTel1Paz.setText(paz.getTelefono1());
		textTel2Paz.setText(paz.getTelefono2());
		textEmailPaz.setText(paz.getEmail());
		textNumTessPaz.setText(paz.getNumTesseraSanitaria());
		textAreaNotePaz.setText(paz.getNote());
	}

	/**
	 * This method initializes sShellPrenotaVisita
	 * 
	 */
	private void createSShellPrenotaVisita() {
		sShellPrenotaVisita = new Shell(SWT.APPLICATION_MODAL | SWT.SHELL_TRIM);
		// sShellPrenotaVisita.setLayout(new GridLayout());
		sShellPrenotaVisita.setSize(new Point(462, 389));
		sShellPrenotaVisita.setText("Prenotazione di una nuova visita");
		labelTipolVisitPrenot = new Label(sShellPrenotaVisita, SWT.WRAP);
		labelTipolVisitPrenot.setBounds(new Rectangle(15, 12, 206, 31));
		labelTipolVisitPrenot
				.setText("Seleziona una tipologia di visita (se necessario, creane una nuova) :");

		labelDataPrenotVisita = new Label(sShellPrenotaVisita, SWT.WRAP);
		labelDataPrenotVisita.setBounds(new Rectangle(15, 167, 79, 27));
		labelDataPrenotVisita.setText("Data visita :");
		labelNote = new Label(sShellPrenotaVisita, SWT.NONE);
		labelNote.setBounds(new Rectangle(15, 210, 36, 19));
		labelNote.setText("Note:");
		textAreaNote = new Text(sShellPrenotaVisita, SWT.MULTI | SWT.WRAP
				| SWT.V_SCROLL);
		textAreaNote.setBounds(new Rectangle(63, 208, 340, 104));
		buttonPrenotaVisita = new Button(sShellPrenotaVisita, SWT.NONE);
		buttonPrenotaVisita.setBounds(new Rectangle(290, 320, 115, 25));
		buttonPrenotaVisita.setText("Prenota visita");
		buttonPrenotaVisita.setEnabled(false);
		buttonSelezionaData = new Button(sShellPrenotaVisita, SWT.NONE);
		buttonSelezionaData.setBounds(new Rectangle(101, 166, 189, 27));
		buttonSelezionaData.setText("Seleziona la data e l'ora della visita");
		tableTipVisita = new Table(sShellPrenotaVisita, SWT.FILL | SWT.BORDER
				| SWT.FULL_SELECTION | SWT.V_SCROLL | SWT.MULTI | SWT.H_SCROLL);
		tableTipVisita.setHeaderVisible(true);
		tableTipVisita.setLinesVisible(true);
		tableTipVisita.setBounds(new Rectangle(15, 49, 415, 109));
		buttonCreaTipVisita = new Button(sShellPrenotaVisita, SWT.NONE);
		buttonCreaTipVisita.setBounds(new Rectangle(238, 15, 188, 28));
		buttonCreaTipVisita.setText("Crea una nuova tipologia di visita");
		buttonCreaTipVisita
				.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
					public void widgetSelected(
							org.eclipse.swt.events.SelectionEvent e) {
						createSShellCreaTipVisita();
					}
				});
		generaTabella();

		buttonSelezionaData
				.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
					public void widgetSelected(
							org.eclipse.swt.events.SelectionEvent e) {
						createShellCalendario();
						ShellCalendario.open();
					}
				});
		buttonPrenotaVisita
				.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
					public void widgetSelected(
							org.eclipse.swt.events.SelectionEvent e) {
						if (tableTipVisita.getSelectionCount() > 0) {
							TableItem[] itemSelez = tableTipVisita
									.getSelection();
							TableItem item = itemSelez[0];
							int idTipSelez = Integer.parseInt(item.getText(0));
							Tipologiavisita tipovisita = VisitaDAO
									.getTipVisitaByID(idTipSelez);
							// Paziente paziente =
							// paz.get(listPazienti.getSelectionIndex());
							VisitaDAO v = new VisitaDAO();
							dn = createShellCalendario();
							// TODO controllare pazienteSel
							v.prenotaVisita(pazienteSel, tipovisita, dn,
									textAreaNote.getText());
							sShellPrenotaVisita.close();
						} else {
							createMessSel();
						}

					}
				});
		sShellPrenotaVisita.open();
	}

	/**
	 * This method initializes ShellCalendario
	 * 
	 */
	private Date createShellCalendario() {
		GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, true);
		gridData.heightHint = 32;
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 1;
		ShellCalendario = new Shell(Display.getCurrent(), SWT.APPLICATION_MODAL
				| SWT.SHELL_TRIM);
		ShellCalendario.setText("Seleziona data e ora");
		ShellCalendario.setLayout(new GridLayout());
		ShellCalendario.setSize(new Point(270, 300));
		final DateTime calendar = new DateTime(ShellCalendario, SWT.CALENDAR
				| SWT.BORDER);
		// final DateTime date = new DateTime (ShellCalendario, SWT.DATE |
		// SWT.SHORT);
		calendar
				.addSelectionListener(new org.eclipse.swt.events.SelectionListener() {
					public void widgetSelected(
							org.eclipse.swt.events.SelectionEvent e) {
						ArrayList<Prenotazione> prenotGiorno = VisitaDAO
								.getPrenotazioniGiorno(calendar.getYear(),
										(calendar.getMonth() + 1), calendar
												.getDay());
						textAreaPrenotazioniOdierne
								.setText("N. di prenotazioni per il "
										+ calendar.getDay() + "/"
										+ (calendar.getMonth() + 1) + "/"
										+ calendar.getYear() + " = "
										+ prenotGiorno.size() + "\n");
						for (Prenotazione prenotazione : prenotGiorno) {
							textAreaPrenotazioniOdierne
									.append("ore: "
											+ prenotazione.getDataOra()
													.getHours()
											+ ":"
											+ (prenotazione.getDataOra()
													.getMinutes() < 10 ? "0"
													: "")
											+ prenotazione.getDataOra()
													.getMinutes()
											+ "  -paziente: "
											+ prenotazione.getPaziente()
													.getCognome()
											+ " "
											+ prenotazione.getPaziente()
													.getNome() + "\n");
						}
						textAreaPrenotazioniOdierne.setTopIndex(0);
						System.out.println("widgetSelected()");
					}

					public void widgetDefaultSelected(
							org.eclipse.swt.events.SelectionEvent e) {
					}
				});
		final DateTime time = new DateTime(ShellCalendario, SWT.TIME
				| SWT.SHORT);
		textAreaPrenotazioniOdierne = new Text(ShellCalendario, SWT.MULTI
				| SWT.V_SCROLL | SWT.BORDER);
		textAreaPrenotazioniOdierne.setEditable(false);
		textAreaPrenotazioniOdierne.setLayoutData(gridData);
		// new Label (ShellCalendario, SWT.NONE);
		// new Label (ShellCalendario, SWT.NONE);
		Button ok = new Button(ShellCalendario, SWT.PUSH);
		ok.setText("OK");
		ok.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false));
		ok.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				System.out.println("Calendar date selected (MM/DD/YYYY) = "
						+ (calendar.getMonth() + 1) + "/" + calendar.getDay()
						+ "/" + calendar.getYear());
				// System.out.println ("Date selected (MM/YYYY) = " +
				// (date.getMonth () + 1) + "/" + date.getYear ());
				System.out.println("Time selected (HH:MM) = " + time.getHours()
						+ ":" + (time.getMinutes() < 10 ? "0" : "")
						+ time.getMinutes());
				String dateString = calendar.getYear() + "-"
						+ (calendar.getMonth() + 1) + "-" + calendar.getDay()
						+ " " + time.getHours() + ":"
						+ (time.getMinutes() < 10 ? "0" : "")
						+ time.getMinutes() + ":00";
				String formato = "yyyy-MM-dd HH:mm:ss";
				dn = Utils.convertStringToDate(dateString, formato);
				ShellCalendario.close();
				buttonPrenotaVisita.setEnabled(true);
			}
		});
		return dn;
	}

	/**
	 * This method initializes calendar
	 * 
	 */
	private void createCalendar() {
		calendar = new DateTime(ShellCalendario, SWT.CALENDAR | SWT.BORDER);
	}

	/**
	 * This method initializes time
	 * 
	 */
	private void createTime() {
		time = new DateTime(ShellCalendario, SWT.TIME | SWT.SHORT);
	}

	/**
	 * This method initializes sShellCreaTipVisita
	 * 
	 */
	private void createSShellCreaTipVisita() {
		sShellCreaTipVisita = new Shell(SWT.APPLICATION_MODAL | SWT.SHELL_TRIM);
		// sShellCreaTipVisita.setLayout(new GridLayout());
		sShellCreaTipVisita.setSize(new Point(443, 150));
		sShellCreaTipVisita.setText("Crea una nuova tipologia di visita");
		labelTipologia = new Label(sShellCreaTipVisita, SWT.NONE);
		labelTipologia.setBounds(new Rectangle(10, 7, 125, 25));
		labelTipologia.setText("Nome tipologia visita: ");
		textTipologia = new Text(sShellCreaTipVisita, SWT.BORDER);
		textTipologia.setBounds(new Rectangle(150, 7, 235, 25));
		labelCosto = new Label(sShellCreaTipVisita, SWT.NONE);
		labelCosto.setBounds(new Rectangle(10, 40, 125, 25));
		labelCosto.setText("Costo tipologia visita:");
		textCosto = new Text(sShellCreaTipVisita, SWT.BORDER);
		textCosto.setBounds(new Rectangle(150, 40, 160, 25));
		buttonOk = new Button(sShellCreaTipVisita, SWT.NONE);
		buttonOk.setBounds(new Rectangle(150, 82, 130, 25));
		buttonOk.setText("Ok");
		buttonOk
				.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
					public void widgetSelected(
							org.eclipse.swt.events.SelectionEvent e) {
						Double costo = Double.parseDouble(textCosto.getText());
						VisitaDAO.registraTipologiaVisita(textTipologia
								.getText(), costo);
						tableTipVisita.removeAll(); // rimuove le righe
						// rimuove le colonne
						int k = 0;
						while (k < tableTipVisita.getColumnCount()) {
							tableTipVisita.getColumn(k).dispose();
						}
						// rigenera la tabella
						generaTabella();
						sShellCreaTipVisita.close();
					}
				});
		buttonAnnullaCreaTip = new Button(sShellCreaTipVisita, SWT.NONE);
		buttonAnnullaCreaTip.setBounds(new Rectangle(286, 82, 130, 25));
		buttonAnnullaCreaTip.setText("Annulla");
		buttonAnnullaCreaTip
				.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
					public void widgetSelected(
							org.eclipse.swt.events.SelectionEvent e) {
						sShellCreaTipVisita.close();
					}
				});

		sShellCreaTipVisita.open();
	}

	private void createMessSel() {
		createSShellMessElimina();
		MessageBox messageBox = new MessageBox(sShellMessElimina, SWT.OK
				| SWT.ICON_ERROR);
		messageBox
				.setMessage("Selezionare la tipologia di visita dalla tabella");
		messageBox.setText("Errore: elemento non selezionato");
		if (messageBox.open() == SWT.OK) {
			sShellMessElimina.close();
		}
	}

	private void generaTabella() {
		TableColumn colonnaId = new TableColumn(tableTipVisita, SWT.CENTER);
		colonnaId.setText("id");
		colonnaId.setWidth(0);
		colonnaId.setResizable(false);
		TableColumn colonnaData = new TableColumn(tableTipVisita, SWT.CENTER);
		colonnaData.setText("Tipologia");
		TableColumn colonnaDescrizione = new TableColumn(tableTipVisita,
				SWT.CENTER);
		colonnaDescrizione.setText("Costo (euro)");
		tv = VisitaDAO.getTipologVisita();
		for (int i = 0; i < tv.size(); i++) {
			TableItem item = new TableItem(tableTipVisita, SWT.NONE);
			item.setText(0, "" + tv.get(i).getIdTipologiaVisita());
			item.setText(1, tv.get(i).getTipologia());
			item.setText(2, "" + tv.get(i).getCostoVisita());
		}
		// nasconde la cifra decimale del prezzo della visita
		for (TableItem item : tableTipVisita.getItems()) {
			int i = 2;
			String testoitem = item.getText(i);
			int lunghezzaTestoItem = item.getText(i).length();
			item.setText(i, testoitem.substring(0, lunghezzaTestoItem - 2));
		}
		for (int i = 1; i < tableTipVisita.getColumnCount(); i++) {
			tableTipVisita.getColumn(i).pack();
			tableTipVisita.getColumn(i).setResizable(false);
		}
		TableForm.ordinamentoStringhe(tableTipVisita, 1);
		TableForm.ordinamentoInteri(tableTipVisita, 2);

	}

	public static Paziente getPazienteSelezionato() {
		return pazienteSel;
	}

} // @jve:decl-index=0:visual-constraint="10,10,857,512"
