package studiodietetico;

import hibernate.Paziente;
import hibernate.Prenotazione;
import hibernate.Tipologiavisita;

import java.util.ArrayList;
import java.util.Date;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.part.ViewPart;

import command.VisitaDAO;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.graphics.Point;

import service.Utils;

public class PrenotazioneTableView extends ViewPart {
	private Composite top = null;
	private TableForm classVis;
	private ArrayList<Object> prenotazioni;
	private Shell sShellPrenotaVisita = null; // @jve:decl-index=0:visual-constraint="336,12"
	private Label labelTipolVisitPrenot = null;
	private Label labelDataPrenotVisita = null;
	private Label labelNote = null;
	private Text textAreaNote = null;
	private Button buttonPrenotaVisita = null;
	private ArrayList<Tipologiavisita> tv; // @jve:decl-index=0:
	private Button buttonSelezionaData = null;
	private Shell ShellCalendario = null; // @jve:decl-index=0:visual-constraint="80,420"
	private Date dn = null;
	public static final String VIEW_ID = "StudioDietetico.prenotavisita";
	private Text textAreaPrenotazioniOdierne = null;
	private Shell sShellMessElimina;
	private Table tableTipVisita = null;
	private Button buttonCreaTipVisita = null;
	private Shell sShellCreaTipVisita = null; // @jve:decl-index=0:visual-constraint="15,723"
	private Label labelTipologia = null;
	private Text textTipologia = null;
	private Label labelCosto = null;
	private Text textCosto = null;
	private Button buttonOk = null;
	private Button buttonAnnulla = null;
	private Shell sShellDettagliPrenotazione = null; // @jve:decl-index=0:visual-constraint="16,878"
	private Label labelPazientePren = null;
	private Text textPazientePren = null;
	private Label labelTipologVis = null;
	private Text textTipologiaVis = null;
	private Label labelCostoVisita = null;
	private Text textCostoVisita = null;
	private Label labelDataOraPrenot = null;
	private Text textDataOraPrenot = null;
	private Label labelNotePren = null;
	private Text textAreaNotePren = null;
	private Button buttonChiudiDettagliPren = null;

	public PrenotazioneTableView() {
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
		prenotazioni = VisitaDAO.getPrenotazioniObject();
		VisitaDAO vd = new VisitaDAO();
		classVis = new TableForm(top, SWT.BORDER, prenotazioni,
				"createSShellDettagliPrenotazione",
				"createSShellPrenotaVisita", PrenotazioneTableView.this, vd,
				"PrenotazioneTableView");
		classVis.setBounds(new Rectangle(6, 50, 800, 332));
		classVis.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		classVis.setLayout(new GridLayout(1, true));
		classVis.setBackground(Utils.getStandardWhiteColor());

		classVis.nascondiColonne(new int[] { 0, 1, 2, 4 });

		aggiungiColonne(classVis, prenotazioni);

		classVis.aggiornaCombo();

		classVis.ordinamentoData(classVis.getTableVisualizzazione(), 3);
		classVis.ordinamentoStringhe(classVis.getTableVisualizzazione(), 5);
		classVis.ordinamentoStringhe(classVis.getTableVisualizzazione(), 6);
	}

	private void aggiornaTableView() {
		classVis.getTableVisualizzazione().removeAll(); // rimuove le righe
		// rimuove le colonne
		int k = 0;
		while (k < classVis.getTableVisualizzazione().getColumnCount()) {
			classVis.getTableVisualizzazione().getColumn(k).dispose();
		}
		prenotazioni = VisitaDAO.getPrenotazioniObject();
		classVis.riempiTabella(prenotazioni, "PrenotazioneTableView");
		classVis.nascondiColonne(new int[] { 0, 1, 2, 4 });
		aggiungiColonne(classVis, prenotazioni);
		classVis.aggiornaCombo();
		classVis.ordinamentoData(classVis.getTableVisualizzazione(), 3);
		classVis.ordinamentoStringhe(classVis.getTableVisualizzazione(), 5);
		classVis.ordinamentoStringhe(classVis.getTableVisualizzazione(), 6);
	}

	public static void aggiungiColonne(TableForm classVis,
			ArrayList<Object> prenotazioni) {
		TableColumn colonna = new TableColumn(classVis
				.getTableVisualizzazione(), SWT.CENTER);
		colonna.setText("Paziente");
		String nome = "";
		TableItem itemSel = null;
		for (int j = 0; j < prenotazioni.size(); j++) {
			nome = ((Prenotazione) prenotazioni.get(j)).getPaziente()
					.getCognome()
					+ " "
					+ ((Prenotazione) prenotazioni.get(j)).getPaziente()
							.getNome();
			itemSel = classVis.getTableVisualizzazione().getItem(j);
			itemSel.setText(
					classVis.getTableVisualizzazione().getColumnCount() - 1,
					nome);
		}
		colonna.pack();
		colonna.setResizable(false);

		TableColumn colonna2 = new TableColumn(classVis
				.getTableVisualizzazione(), SWT.CENTER);
		colonna2.setText("Tipologia visita");
		String nome2 = "";
		TableItem itemSel2 = null;
		for (int j = 0; j < prenotazioni.size(); j++) {
			nome2 = ((Prenotazione) prenotazioni.get(j)).getTipologiavisita()
					.getTipologia();
			itemSel2 = classVis.getTableVisualizzazione().getItem(j);
			itemSel2.setText(classVis.getTableVisualizzazione()
					.getColumnCount() - 1, nome2);
		}
		colonna2.pack();
		colonna2.setResizable(false);
	}

	@Override
	public void setFocus() {
		classVis.setFocus();
	}

	/**
	 * This method initializes sShellPrenotaVisita
	 * 
	 */
	public void createSShellPrenotaVisita() {
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
							v.prenotaVisita(PazienteTableView.pazienteSel,
									tipovisita, dn, textAreaNote.getText());
							aggiornaTableView();
						} else {
							createMessSelElemCanc();
						}

					}
				});
		sShellPrenotaVisita.open();
	}

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
		buttonAnnulla = new Button(sShellCreaTipVisita, SWT.NONE);
		buttonAnnulla.setBounds(new Rectangle(286, 82, 130, 25));
		buttonAnnulla.setText("Annulla");
		buttonAnnulla
				.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
					public void widgetSelected(
							org.eclipse.swt.events.SelectionEvent e) {
						sShellCreaTipVisita.close();
					}
				});

		sShellCreaTipVisita.open();
	}

	private void createMessSelElemCanc() {
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

	private void createSShellMessElimina() {
		sShellMessElimina = new Shell(SWT.APPLICATION_MODAL | SWT.SHELL_TRIM);
		sShellMessElimina.setLayout(new GridLayout());
		sShellMessElimina.setSize(new Point(377, 72));
	}

	/**
	 * This method initializes sShellDettagliPrenotazione
	 * 
	 */
	public void createSShellDettagliPrenotazione(final TableItem rigaTableClick) {
		int idPrenot = Integer.parseInt(rigaTableClick.getText(0));
		final Prenotazione pren = VisitaDAO.getPrenotazioneByID(idPrenot);
		sShellDettagliPrenotazione = new Shell(SWT.APPLICATION_MODAL
				| SWT.SHELL_TRIM);
		// sShellDettagliPrenotazione.setLayout(new GridLayout());
		sShellDettagliPrenotazione.setText("Dettagli prenotazione");
		sShellDettagliPrenotazione.setSize(new Point(398, 328));
		labelPazientePren = new Label(sShellDettagliPrenotazione, SWT.NONE);
		labelPazientePren.setBounds(new Rectangle(6, 9, 64, 22));
		labelPazientePren.setText("Paziente :");
		textPazientePren = new Text(sShellDettagliPrenotazione, SWT.BORDER);
		textPazientePren.setBounds(new Rectangle(80, 9, 283, 22));
		textPazientePren.setText(pren.getPaziente().getCognome() + " "
				+ pren.getPaziente().getNome());
		textPazientePren.setEditable(false);
		labelTipologVis = new Label(sShellDettagliPrenotazione, SWT.NONE);
		labelTipologVis.setBounds(new Rectangle(6, 36, 98, 22));
		labelTipologVis.setText("Tipologia visita :");
		textTipologiaVis = new Text(sShellDettagliPrenotazione, SWT.BORDER);
		textTipologiaVis.setBounds(new Rectangle(114, 36, 248, 22));
		textTipologiaVis.setText(pren.getTipologiavisita().getTipologia());
		textTipologiaVis.setEditable(false);
		labelCostoVisita = new Label(sShellDettagliPrenotazione, SWT.NONE);
		labelCostoVisita.setBounds(new Rectangle(6, 63, 98, 22));
		labelCostoVisita.setText("Costo visita :");
		textCostoVisita = new Text(sShellDettagliPrenotazione, SWT.BORDER);
		textCostoVisita.setBounds(new Rectangle(115, 63, 200, 22));
		textCostoVisita
				.setText("" + pren.getTipologiavisita().getCostoVisita());
		textCostoVisita.setEditable(false);
		labelDataOraPrenot = new Label(sShellDettagliPrenotazione, SWT.NONE);
		labelDataOraPrenot.setBounds(new Rectangle(6, 89, 135, 22));
		labelDataOraPrenot.setText("Data e ora prenotazione :");
		textDataOraPrenot = new Text(sShellDettagliPrenotazione, SWT.BORDER);
		textDataOraPrenot.setBounds(new Rectangle(145, 89, 217, 22));
		textDataOraPrenot.setText(pren.getDataOra().toLocaleString());
		textDataOraPrenot.setEditable(false);
		labelNotePren = new Label(sShellDettagliPrenotazione, SWT.NONE);
		labelNotePren.setBounds(new Rectangle(6, 116, 50, 22));
		labelNotePren.setText("Note :");
		textAreaNotePren = new Text(sShellDettagliPrenotazione, SWT.MULTI
				| SWT.WRAP | SWT.V_SCROLL);
		textAreaNotePren.setBounds(new Rectangle(62, 117, 299, 130));
		textAreaNotePren.setText(pren.getNote());
		textAreaNotePren.setEditable(false);
		buttonChiudiDettagliPren = new Button(sShellDettagliPrenotazione,
				SWT.NONE);
		buttonChiudiDettagliPren.setBounds(new Rectangle(239, 257, 120, 25));
		buttonChiudiDettagliPren.setText("Chiudi dettagli");
		buttonChiudiDettagliPren
				.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
					public void widgetSelected(
							org.eclipse.swt.events.SelectionEvent e) {
						sShellDettagliPrenotazione.close();
					}
				});

		sShellDettagliPrenotazione.open();
	}

}
