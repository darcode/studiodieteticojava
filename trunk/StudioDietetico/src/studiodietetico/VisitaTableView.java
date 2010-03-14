package studiodietetico;

import hibernate.Fattura;
import hibernate.Medico;
import hibernate.Prenotazione;
import hibernate.Visita;

import java.util.ArrayList;
import java.util.Date;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.part.ViewPart;

import command.FatturaDAO;
import command.MedicoDAO;
import command.UtenteDAO;
import command.VisitaDAO;

import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Combo;

import security.IFunzioniConstants;
import service.Utils;

public class VisitaTableView extends ViewPart {
	private Composite top = null;
	private TableForm classVis;
	private ArrayList<Object> visite;  //  @jve:decl-index=0:
	private Shell sShellNuovaFattura = null;  //  @jve:decl-index=0:visual-constraint="10,425"
	private Group groupPrenotazione = null;
	private Label labelImportoFatt = null;
	private Text textImportoFatt = null;
	private Label labelImportoPagato = null;
	private Button radioButtonImportoSi = null;
	private Button radioButtonImportoNo = null;
	private Label labelAcconto = null;
	private Text textImportoAcconto = null;
	private Group groupImportoAcconto = null;
	private Label labelSconto = null;
	private Button radioButtonScontoSi = null;
	private Button radioButtonScontoNo = null;
	private Label labelImportoSconto = null;
	private Text textImportoSconto = null;
	private Group groupScontoFattura = null;
	private Label labelNoteFattura = null;
	private Text textAreaNoteFattura = null;
	private Button buttonRegistraFattura = null;
	private Button buttonAnnullaFattura = null;
	private Composite compositeAcconto = null;
	private Label labelDescrizioneFattura = null;
	private Text textDescrizioneFattura = null;
	private Button buttonAggiornaFattura = null;
	private int idFatt;
	private Shell sShellMessElimina;
	private Shell sShellFatture = null;  //  @jve:decl-index=0:visual-constraint="7,808"
	private Table tableConti = null;
	private ArrayList<Fattura> fatture;  //  @jve:decl-index=0:
	private Composite compositeShell = null;
	private Fattura fatturaSelezionata = null;
	private Visita visitaSel = null;
	private Label labelSelezAttributo = null;
	private Combo comboAttributi = null;
	private Text textRicerca = null;
	private Shell sShellRegistraVisita = null;  //  @jve:decl-index=0:visual-constraint="18,1187"
	
	private Label labelSelezPrenotaz = null;
	private Combo comboPrenotazOdierne = null;
	private Text textAreaInfoPrenotazione = null;
	private Label labelInfoPrenotazione = null;
	private Label labelSelezMedico = null;
	private Combo comboMedicoVisita = null;
	private Combo comboGiornoVisita = null;
	private Label labelOraInizioVisita = null;
	private Label labelOraFineVisita = null;
	private Label labelMotivazioni = null;
	private Text textAreaMotivazioni = null;
	private Label labelNote = null;
	private Text textAreaNote = null;
	private Button buttonRegistraVisita = null;
	private ArrayList<Medico> medici;  //  @jve:decl-index=0:
	private ArrayList<Prenotazione> prenotazioni;  //  @jve:decl-index=0:
	private Group groupDataVisita = null;
	private Label labelFatturaAssociata = null;
	final ArrayList<Prenotazione> prenotazioniOdierne = new ArrayList<Prenotazione>();  //  @jve:decl-index=0:
	private Shell sShellDettagliVisita = null;  //  @jve:decl-index=0:visual-constraint="20,1725"
	private Label labelPazienteVis = null;
	private Text textPazienteVis = null;
	private Label labelTipologVis = null;
	private Text textTipologiaVis = null;
	private Label labelCostoVisita = null;
	private Text textCostoVisita = null;
	private Label labelMedicoVis = null;
	private Text textMedicoVis = null;
	private Label labelDataOraInizioVis = null;
	private Text textDataOraInizioVis = null;
	private Label labelDataOraFineVis = null;
	private Text textDataOraFineVis = null;
	private Label labelStatoPagamento = null;
	private Text textStatoPagamento = null;
	private Label labelMotivazioniVis = null;
	private Text textAreaMotivazioniVis = null;
	private Label labelNoteVis = null;
	private Text textAreaNoteVis = null;
	private Button buttonOk = null;
	private Button buttonAnnulla = null;
	
	public VisitaTableView() {}

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
		visite = VisitaDAO.getVisiteObject();
		VisitaDAO vd = new VisitaDAO();
		classVis = new TableForm(top, SWT.BORDER, visite, "createSShellDettagliVisita","createSShellRegistraVisita",VisitaTableView.this, vd, "VisitaTableView");
		classVis.setBounds(new Rectangle(6, 50, 800, 332));
		classVis.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		classVis.setLayout(new GridLayout(1, true));
		classVis.setBackground(Utils.getStandardWhiteColor());
		classVis.getButtonElimina().setEnabled(UtenteDAO.hasFunction(IFunzioniConstants.FUNZIONE_VISITA));
		classVis.getButtonInsert().setEnabled(UtenteDAO.hasFunction(IFunzioniConstants.FUNZIONE_VISITA));
		classVis.nascondiColonne(new int[] {0,1,2,3,6,7,8});

		aggiungiColonne(classVis, visite);
		
		classVis.aggiornaCombo();
		
		classVis.ordinamentoData(classVis.getTableVisualizzazione(), 4);
		classVis.ordinamentoData(classVis.getTableVisualizzazione(), 5);
		classVis.ordinamentoStringhe(classVis.getTableVisualizzazione(), 9);
		classVis.ordinamentoStringhe(classVis.getTableVisualizzazione(), 10);
		classVis.ordinamentoStringhe(classVis.getTableVisualizzazione(), 11);
		
		Button buttonCreaAssociaFattura = new Button(classVis.top, SWT.NONE);
		buttonCreaAssociaFattura.setBounds(260, 284, 150, 25);
		buttonCreaAssociaFattura.setText("Crea e associa una fattura");
		buttonCreaAssociaFattura.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				if(classVis.getTableVisualizzazione().getSelectionCount()>0) {
					createSShellNuovaFattura(); 
				} else {
					createMessSelElemCanc();
				}
			}
		});
		buttonCreaAssociaFattura.setEnabled(UtenteDAO.hasFunction(IFunzioniConstants.FUNZIONE_FATTURA_VISITA));
		Button buttonAssociaFattura = new Button(classVis.top, SWT.NONE);
		buttonAssociaFattura.setBounds(420, 284, 150, 25);
		buttonAssociaFattura.setText("Associa ad un conto");
		buttonAssociaFattura.setEnabled(UtenteDAO.hasFunction(IFunzioniConstants.FUNZIONE_FATTURA_VISITA));
		buttonAssociaFattura.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				if(classVis.getTableVisualizzazione().getSelectionCount()>0) {
					createSShellFatture();
				} else {
					createMessSelElemCanc();
				}
			}
		});

	}
	
	public static void aggiungiColonne(TableForm classVis, ArrayList<Object> visite) {
		TableColumn colonna = new TableColumn(classVis.getTableVisualizzazione(), SWT.CENTER);
		colonna.setText("Medico");
		String nome = "";
		TableItem itemSel = null;
		for (int j = 0; j < visite.size(); j++) {
			nome = ((Visita)visite.get(j)).getMedico().getCognome()+" "+((Visita)visite.get(j)).getMedico().getNome();
			itemSel = classVis.getTableVisualizzazione().getItem(j);
			itemSel.setText(classVis.getTableVisualizzazione().getColumnCount()-1, nome);
		} 
		colonna.pack();
		colonna.setResizable(false);
		
		TableColumn colonna2 = new TableColumn(classVis.getTableVisualizzazione(), SWT.CENTER);
		colonna2.setText("Paziente");
		String nome2 = "";
		TableItem itemSel2 = null;
		for (int j = 0; j < visite.size(); j++) {
			nome2 = ((Visita)visite.get(j)).getPrenotazione().getPaziente().getCognome()+" "+((Visita)visite.get(j)).getPrenotazione().getPaziente().getNome();
			itemSel2 = classVis.getTableVisualizzazione().getItem(j);
			itemSel2.setText(classVis.getTableVisualizzazione().getColumnCount()-1, nome2);
		} 
		colonna2.pack();
		colonna2.setResizable(false);
		
		TableColumn colonna3 = new TableColumn(classVis.getTableVisualizzazione(), SWT.CENTER);
		colonna3.setText("Fatturata");
		String nome3 = "";
		TableItem itemSel3 = null;
		for (int j = 0; j < visite.size(); j++) {
			if (((Visita)visite.get(j)).getFattura()!=null) {
				nome3="si";
				itemSel3 = classVis.getTableVisualizzazione().getItem(j);
				itemSel3.setText(classVis.getTableVisualizzazione().getColumnCount()-1, nome3);
			} else {
				nome3="no";
				itemSel3 = classVis.getTableVisualizzazione().getItem(j);
				itemSel3.setText(classVis.getTableVisualizzazione().getColumnCount()-1, nome3);
			}
		} 
		colonna3.pack();
		colonna3.setResizable(false);
	}

	@Override
	public void setFocus() {
		classVis.setFocus();
	}
	
	private void createSShellNuovaFattura() {
		sShellNuovaFattura = new Shell(Display.getCurrent(), SWT.APPLICATION_MODAL | SWT.SHELL_TRIM);
		//sShellNuovaFattura.setLayout(new GridLayout());
		sShellNuovaFattura.setSize(new Point(527, 366));
		sShellNuovaFattura.setText("Crea una nuova fattura");
		labelImportoFatt = new Label(sShellNuovaFattura, SWT.WRAP);
		labelImportoFatt.setBounds(new Rectangle(301, 15, 98, 33));
		labelImportoFatt.setText("Importo fattura (euro):");
		textImportoFatt = new Text(sShellNuovaFattura, SWT.BORDER);
		textImportoFatt.setBounds(new Rectangle(407, 15, 92, 27));		
		
		labelSconto = new Label(sShellNuovaFattura, SWT.WRAP);
		labelSconto.setBounds(new Rectangle(265, 73, 136, 21));
		labelSconto.setText("E' applicato uno sconto?");
		radioButtonScontoSi = new Button(sShellNuovaFattura, SWT.RADIO);
		radioButtonScontoSi.setBounds(new Rectangle(412, 75, 32, 16));
		radioButtonScontoSi.setText("Si");
		radioButtonScontoSi.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				labelImportoSconto.setVisible(true);
				textImportoSconto.setVisible(true);
			System.out.println("widgetSelected()"); 
		}
	});
		radioButtonScontoNo = new Button(sShellNuovaFattura, SWT.RADIO);
		radioButtonScontoNo.setBounds(new Rectangle(447, 75, 39, 16));
		radioButtonScontoNo.setText("No");
		radioButtonScontoNo.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				labelImportoSconto.setVisible(false);
				textImportoSconto.setVisible(false);
			System.out.println("widgetSelected()"); 
		}
	});
		labelImportoSconto = new Label(sShellNuovaFattura, SWT.NONE);
		labelImportoSconto.setBounds(new Rectangle(265, 113, 134, 21));
		labelImportoSconto.setText("Importo sconto (euro):");
		labelImportoSconto.setVisible(false);
		textImportoSconto = new Text(sShellNuovaFattura, SWT.BORDER);
		textImportoSconto.setBounds(new Rectangle(406, 113, 83, 21));
		textImportoSconto.setVisible(false);
		createGroupScontoFattura();
		labelNoteFattura = new Label(sShellNuovaFattura, SWT.NONE);
		labelNoteFattura.setBounds(new Rectangle(15, 172, 97, 18));
		labelNoteFattura.setText("Note (eventuali):");
		textAreaNoteFattura = new Text(sShellNuovaFattura, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL);
		textAreaNoteFattura.setBounds(new Rectangle(124, 172, 276, 84));
		buttonRegistraFattura = new Button(sShellNuovaFattura, SWT.NONE);
		//		GESTIONE SICUREZZA - RUOLO
		buttonRegistraFattura.setEnabled(UtenteDAO.hasFunction(IFunzioniConstants.FUNZIONE_FATTURA_VISITA));

		buttonRegistraFattura.setBounds(new Rectangle(135, 270, 142, 29));
		buttonRegistraFattura.setText("Registra/Associa fattura");
		buttonRegistraFattura
				.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
					public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
						FatturaDAO fattura = new FatturaDAO();
						double importoFattura = Double.parseDouble(textImportoFatt.getText());						
						if (radioButtonImportoSi.getSelection() && radioButtonScontoSi.getSelection()) {
							double importoSconto = Double.parseDouble(textImportoSconto.getText());
							idFatt = fattura.registraFattura(textDescrizioneFattura.getText(), importoFattura, 0, importoSconto, textAreaNoteFattura.getText());
						} else if (radioButtonImportoSi.getSelection() && radioButtonScontoNo.getSelection()){
							idFatt = fattura.registraFattura(textDescrizioneFattura.getText(), importoFattura, 0, 0, textAreaNoteFattura.getText());
						} else if (radioButtonImportoNo.getSelection() && radioButtonScontoSi.getSelection()){
							double importoAcconto = Double.parseDouble(textImportoAcconto.getText());
							double importoSconto = Double.parseDouble(textImportoSconto.getText());
							idFatt = fattura.registraFattura(textDescrizioneFattura.getText(), importoFattura, importoAcconto, importoSconto, textAreaNoteFattura.getText());
						} else if (radioButtonImportoNo.getSelection() && radioButtonScontoNo.getSelection()){
							double importoAcconto = Double.parseDouble(textImportoAcconto.getText());
							idFatt = fattura.registraFattura(textDescrizioneFattura.getText(), importoFattura, importoAcconto, 0, textAreaNoteFattura.getText());
						}
						
						TableItem itemSel = classVis.getTableVisualizzazione().getItem(classVis.getTableVisualizzazione().getSelectionIndex());
						int idVisitaSel = Integer.parseInt(itemSel.getText(0));
						visitaSel = VisitaDAO.getVisitaByID(idVisitaSel);
						VisitaDAO.setPagamentoVisita(visitaSel);
						Fattura fatturaCreata = FatturaDAO.getFatturaByID(idFatt);
						FatturaDAO.associaFattura(fatturaCreata, visitaSel);
						itemSel.setText(11, "si"); 
						System.out.println("visita "+visitaSel.getIdVisita()+" fattura "+fatturaCreata.getIdFattura());
						sShellNuovaFattura.close();
						System.out.println("fattura registrata"); 
					}
				});
		buttonAnnullaFattura = new Button(sShellNuovaFattura, SWT.NONE);
		buttonAnnullaFattura.setBounds(new Rectangle(286, 270, 107, 29));
		buttonAnnullaFattura.setText("Annulla");
		buttonAnnullaFattura.setEnabled(UtenteDAO.hasFunction(IFunzioniConstants.FUNZIONE_FATTURA_VISITA));
		createCompositeAcconto();
		labelDescrizioneFattura = new Label(sShellNuovaFattura, SWT.WRAP);
		labelDescrizioneFattura.setBounds(new Rectangle(8, 6, 62, 35));
		labelDescrizioneFattura.setText("Descrizione fattura:");
		textDescrizioneFattura = new Text(sShellNuovaFattura, SWT.BORDER | SWT.WRAP | SWT.V_SCROLL);
		textDescrizioneFattura.setBounds(new Rectangle(76, 6, 189, 37));
		buttonAggiornaFattura = new Button(sShellNuovaFattura, SWT.NONE);
		buttonAggiornaFattura.setBounds(new Rectangle(135, 270, 142, 29));
		buttonAggiornaFattura.setText("Aggiorna/Associa fattura");
		buttonAggiornaFattura.setVisible(false);
		buttonAggiornaFattura.setEnabled(UtenteDAO.hasFunction(IFunzioniConstants.FUNZIONE_FATTURA_VISITA));
		buttonAggiornaFattura
				.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
					public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
						FatturaDAO fattura = new FatturaDAO(); 
						double importoFattura = Double.parseDouble(textImportoFatt.getText());						
						if (radioButtonImportoSi.getSelection() && radioButtonScontoSi.getSelection()) {
							double importoSconto = Double.parseDouble(textImportoSconto.getText());
							fattura.aggiornaFattura(fatturaSelezionata,textDescrizioneFattura.getText(), importoFattura, 0, importoSconto, textAreaNoteFattura.getText());
						} else if (radioButtonImportoSi.getSelection() && radioButtonScontoNo.getSelection()){
							fattura.aggiornaFattura(fatturaSelezionata,textDescrizioneFattura.getText(), importoFattura, 0, 0, textAreaNoteFattura.getText());
						} else if (radioButtonImportoNo.getSelection() && radioButtonScontoSi.getSelection()){
							double importoAcconto = Double.parseDouble(textImportoAcconto.getText());
							double importoSconto = Double.parseDouble(textImportoSconto.getText());
							fattura.aggiornaFattura(fatturaSelezionata,textDescrizioneFattura.getText(), importoFattura, importoAcconto, importoSconto, textAreaNoteFattura.getText());
						} else if (radioButtonImportoNo.getSelection() && radioButtonScontoNo.getSelection()){
							double importoAcconto = Double.parseDouble(textImportoAcconto.getText());
							fattura.aggiornaFattura(fatturaSelezionata,textDescrizioneFattura.getText(), importoFattura, importoAcconto, 0, textAreaNoteFattura.getText());
						}
						TableItem itemSel = classVis.getTableVisualizzazione().getItem(classVis.getTableVisualizzazione().getSelectionIndex());
						int idVisitaSel = Integer.parseInt(itemSel.getText(0));
						visitaSel = VisitaDAO.getVisitaByID(idVisitaSel);
						VisitaDAO.setPagamentoVisita(visitaSel);
						FatturaDAO.associaFattura(fatturaSelezionata, visitaSel);
						itemSel.setText(11, "si"); 
						System.out.println("visita "+visitaSel.getIdVisita()+" fattura "+fatturaSelezionata.getIdFattura());
						sShellNuovaFattura.close();
						sShellFatture.close();
						System.out.println("conto aggiornato e associato"); 
					}
				});
		buttonAnnullaFattura
				.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
					public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
						sShellNuovaFattura.close();
						System.out.println("widgetSelected()"); 
					}
				});
		
		sShellNuovaFattura.open();
	}

	private void createCompositeAcconto() {
		compositeAcconto = new Composite(sShellNuovaFattura, SWT.NONE);
		//compositeAcconto.setLayout(new GridLayout());
		compositeAcconto.setBounds(new Rectangle(7, 49, 246, 117));
		labelImportoPagato = new Label(compositeAcconto, SWT.WRAP);
		labelImportoPagato.setBounds(new Rectangle(10, 15, 115, 45));
		labelImportoPagato.setText("L'importo della fattura è stato pagato totalmente?");
		radioButtonImportoSi = new Button(compositeAcconto, SWT.RADIO);
		radioButtonImportoSi.setBounds(new Rectangle(140, 20, 38, 20));
		radioButtonImportoSi.setText("Si");
		radioButtonImportoSi.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
					labelAcconto.setVisible(false);
					textImportoAcconto.setVisible(false);
				System.out.println("widgetSelected()"); 
			}
		});
		radioButtonImportoNo = new Button(compositeAcconto, SWT.RADIO);
		radioButtonImportoNo.setBounds(new Rectangle(185, 20, 39, 20));
		radioButtonImportoNo.setText("No");
		radioButtonImportoNo.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
					labelAcconto.setVisible(true);
					textImportoAcconto.setVisible(true);
				System.out.println("widgetSelected()");
			}
		});
		labelAcconto = new Label(compositeAcconto, SWT.WRAP);
		labelAcconto.setBounds(new Rectangle(10, 70, 118, 32));
		labelAcconto.setText("Importo versato (euro):");
		labelAcconto.setVisible(false);
		textImportoAcconto = new Text(compositeAcconto, SWT.BORDER);
		textImportoAcconto.setBounds(new Rectangle(130, 72, 83, 21));
		textImportoAcconto.setVisible(false);
		createGroupImportoAcconto();
	}

	private void createGroupImportoAcconto() {
		groupImportoAcconto = new Group(compositeAcconto, SWT.NONE);
		//groupImportoAcconto.setLayout(new GridLayout());
		groupImportoAcconto.setText("Stato pagamento fattura");
		groupImportoAcconto.setBounds(new Rectangle(3, 0, 240, 110));
	}
	
	private void createGroupScontoFattura() {
		groupScontoFattura = new Group(sShellNuovaFattura, SWT.NONE);
		groupScontoFattura.setLayout(new GridLayout());
		groupScontoFattura.setText("Sconto sulla fattura");
		groupScontoFattura.setBounds(new Rectangle(255, 52, 247, 105));
	}
	
	private void createMessSelElemCanc() {
		createSShellMessElimina();
		MessageBox messageBox = new MessageBox(sShellMessElimina, SWT.OK | SWT.ICON_ERROR);
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

	/**
	 * This method initializes sShellFatture	
	 *
	 */
	private void createSShellFatture() {
		sShellFatture = new Shell();
		compositeShell = new Composite(sShellFatture, SWT.APPLICATION_MODAL | SWT.SHELL_TRIM);
		compositeShell.setBounds(new Rectangle(3, 2, 590, 331));
		sShellFatture.setText("Selezionare un conto dalla tabella");		
		sShellFatture.setSize(new Point(611, 371));
		fatture = FatturaDAO.getFatture();
		tableConti = new Table(compositeShell, SWT.FILL | SWT.BORDER | SWT.FULL_SELECTION | SWT.V_SCROLL | SWT.MULTI | SWT.H_SCROLL);
		tableConti.setHeaderVisible(true);
		tableConti.setLinesVisible(true);
		tableConti.setLocation(new Point(0, 39));
		tableConti.setSize(new Point(589, 289));
		labelSelezAttributo = new Label(compositeShell, SWT.NONE);
		labelSelezAttributo.setBounds(new Rectangle(2, 6, 123, 22));
		labelSelezAttributo.setText("Ricerca sull'attributo: ");
		createComboAttributi();
		textRicerca = new Text(compositeShell, SWT.BORDER);
		textRicerca.setBounds(new Rectangle(283, 6, 196, 24));
		textRicerca.setEditable(false);
		textRicerca.addModifyListener(new org.eclipse.swt.events.ModifyListener() {
			public void modifyText(org.eclipse.swt.events.ModifyEvent e) {
				tableConti.removeAll(); //rimuove le righe
				//rimuove le colonne
				int k = 0;
				while (k<tableConti.getColumnCount()) {
					tableConti.getColumn(k).dispose();
				}
				//rigenera la tabella
				generaTabella();
				//ricerca incrementale nella colonna selezionata
				int indiceColonnaSel = comboAttributi.getSelectionIndex()+1;
				int i = 0;
				while (i<tableConti.getItems().length) {
					String item = tableConti.getItem(i).getText(indiceColonnaSel).toLowerCase();
					if (!(item.startsWith(textRicerca.getText().toLowerCase()))) {
						tableConti.remove(i);
					}
					else {
						i++;
					}
				}
			}
		});
		
		tableConti.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				createSShellNuovaFattura();
        		sShellNuovaFattura.setText("Aggiorna e associa un conto");
        		labelDescrizioneFattura.setText("Descrizione conto:");
        		labelImportoFatt.setText("Importo conto (euro):");
        		labelImportoPagato.setText("L'importo è stato pagato totalmente?");
        		groupImportoAcconto.setText("Stato pagamento");
        		groupScontoFattura.setText("Sconto");
        		buttonAggiornaFattura.setText("Aggiorna/Associa conto");
        		TableItem[] itemSelez = tableConti.getSelection();
        		TableItem item = itemSelez[0];
        		int idFattSelez = Integer.parseInt(item.getText(0));
        		fatturaSelezionata = FatturaDAO.getFatturaByID(idFattSelez);
        		textDescrizioneFattura.setText(fatturaSelezionata.getDescrizione());
        		textImportoFatt.setText(""+fatturaSelezionata.getImporto());
        		textImportoAcconto.setText(""+fatturaSelezionata.getAcconto());
        		textImportoSconto.setText(""+fatturaSelezionata.getImportoSconto());
        		textAreaNoteFattura.setText(fatturaSelezionata.getNote());
        		buttonRegistraFattura.setVisible(false);
        		buttonAggiornaFattura.setVisible(true);
			}
		});
		generaTabella();
		
		sShellFatture.open();
	}

	private void generaTabella() {
		TableColumn colonnaId = new TableColumn(tableConti, SWT.CENTER);	
		colonnaId.setText("id");
		colonnaId.setWidth(0);
		colonnaId.setResizable(false);
		TableColumn colonnaData = new TableColumn(tableConti, SWT.CENTER);	
		colonnaData.setText("Data ultima modifica");
		TableColumn colonnaDescrizione = new TableColumn(tableConti, SWT.CENTER);	
		colonnaDescrizione.setText("Descrizione");
		TableColumn colonnaNote = new TableColumn(tableConti, SWT.CENTER);	
		colonnaNote.setText("Note");
		for (int i = 0; i < fatture.size(); i++) {
			TableItem item = new TableItem(tableConti, SWT.NONE);
			item.setText(0, ""+fatture.get(i).getIdFattura());
			item.setText(1, ""+fatture.get(i).getData());
			item.setText(2, fatture.get(i).getDescrizione());
			item.setText(3, fatture.get(i).getNote());
		}
		//elimina i secondi e i millisecondi dagli items
		for (TableItem item : tableConti.getItems()) {
			int i = 1;
			String testoitem = item.getText(i);
			int lunghezzaTestoItem = item.getText(i).length();
			item.setText(i, testoitem.substring(0, lunghezzaTestoItem-5));
		}
		for (int i = 1; i < tableConti.getColumnCount(); i++) {
			tableConti.getColumn(i).pack();
			tableConti.getColumn(i).setResizable(false);
		}
		
		classVis.ordinamentoData(tableConti, 1);
		classVis.ordinamentoStringhe(tableConti, 2);
		classVis.ordinamentoStringhe(tableConti, 3);
		
	}

	private void createComboAttributi() {
		comboAttributi = new Combo(compositeShell, SWT.READ_ONLY);
		comboAttributi.setBounds(new Rectangle(131, 6, 136, 22));
		comboAttributi.setItems(new String[] {"Data","Descrizione","Note"});
		comboAttributi
				.addSelectionListener(new org.eclipse.swt.events.SelectionListener() {
					public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
						textRicerca.setEditable(true);
					}
					public void widgetDefaultSelected(
							org.eclipse.swt.events.SelectionEvent e) {
					}
				});
	}

	/**
	 * This method initializes sShellRegistraVisita	
	 *
	 */
	public void createSShellRegistraVisita() {
		sShellRegistraVisita = new Shell(SWT.APPLICATION_MODAL | SWT.SHELL_TRIM);
		//sShellRegistraVisita.setLayout(new GridLayout());
		sShellRegistraVisita.setSize(new Point(543, 532));
		sShellRegistraVisita.setText("Registra visita");
		
        labelSelezPrenotaz = new Label(sShellRegistraVisita, SWT.WRAP);
        labelSelezPrenotaz.setBounds(new Rectangle(10, 15, 141, 32));
        labelSelezPrenotaz.setText("* Seleziona la prenotazione per la data odierna:");
        createComboPrenotazOdierne();
        textAreaInfoPrenotazione = new Text(sShellRegistraVisita, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL);
        textAreaInfoPrenotazione.setBounds(new Rectangle(161, 50, 308, 82));
        textAreaInfoPrenotazione.setEditable(false);
        labelInfoPrenotazione = new Label(sShellRegistraVisita, SWT.WRAP);
        labelInfoPrenotazione.setBounds(new Rectangle(10, 50, 143, 39));
        labelInfoPrenotazione.setText("Info sulla prenotazione selezionata:");
        labelSelezMedico = new Label(sShellRegistraVisita, SWT.WRAP);
        labelSelezMedico.setBounds(new Rectangle(10, 142, 144, 32));
        labelSelezMedico.setText("* Seleziona il medico che ha effettuato la visita:");
        createComboMedicoVisita();
        labelOraInizioVisita = new Label(sShellRegistraVisita, SWT.NONE);
        labelOraInizioVisita.setBounds(new Rectangle(10, 200, 117, 20));
        labelOraInizioVisita.setText("* Ora inizio visita:");
        final DateTime timeInizioVisita = new DateTime(sShellRegistraVisita, SWT.TIME | SWT.SHORT);
        timeInizioVisita.setBounds(130, 200, 70, 20);
        labelOraFineVisita = new Label(sShellRegistraVisita, SWT.NONE);
        labelOraFineVisita.setBounds(new Rectangle(10, 230, 117, 20));
        labelOraFineVisita.setText("* Ora fine visita:");
        final DateTime timeFineVisita = new DateTime(sShellRegistraVisita, SWT.TIME | SWT.SHORT);
        timeFineVisita.setBounds(130, 230, 70, 20);
        labelMotivazioni = new Label(sShellRegistraVisita, SWT.NONE);
        labelMotivazioni.setBounds(new Rectangle(10, 275, 129, 22));
        labelMotivazioni.setText("Motivazioni della visita:");
        textAreaMotivazioni = new Text(sShellRegistraVisita, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL);
        textAreaMotivazioni.setBounds(new Rectangle(157, 274, 344, 55));
        labelNote = new Label(sShellRegistraVisita, SWT.NONE);
        labelNote.setBounds(new Rectangle(12, 341, 41, 17));
        labelNote.setText("Note:");
        textAreaNote = new Text(sShellRegistraVisita, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL);
        textAreaNote.setBounds(new Rectangle(60, 342, 398, 115));
        buttonRegistraVisita = new Button(sShellRegistraVisita, SWT.NONE);
        buttonRegistraVisita.setBounds(new Rectangle(354, 467, 147, 25));
        buttonRegistraVisita.setText("Registra visita");
        createGroupPrenotazione();
        createGroupDataVisita();
        labelFatturaAssociata = new Label(sShellRegistraVisita, SWT.NONE);
        labelFatturaAssociata.setBounds(new Rectangle(11, 467, 166, 24));
        labelFatturaAssociata.setText("Visita correttamente registrata");
        labelFatturaAssociata.setVisible(false);
        buttonAnnulla = new Button(sShellRegistraVisita, SWT.NONE);
        buttonAnnulla.setBounds(new Rectangle(205, 467, 147, 25));
        buttonAnnulla.setText("Annulla");
        buttonAnnulla
        		.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
        			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
        				sShellRegistraVisita.close();
        			}
        		});
        buttonRegistraVisita
        		.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
        			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
        				String formato = "yyyy-MM-dd HH:mm:ss";
        				Date now = new Date();
        				String dataInizioString =  Integer.toString(now.getYear()+1900)+"-"+((now.getMonth()+1) < 10 ? "0" : "") + (now.getMonth()+1)+"-"+Integer.toString(now.getDate())+" "+timeInizioVisita.getHours()+":"+(timeInizioVisita.getMinutes () < 10 ? "0" : "") + timeInizioVisita.getMinutes ()+":00";
        				String dataFineString =  Integer.toString(now.getYear()+1900)+"-"+((now.getMonth()+1) < 10 ? "0" : "") + (now.getMonth()+1)+"-"+Integer.toString(now.getDate())+" "+timeFineVisita.getHours()+":"+(timeFineVisita.getMinutes () < 10 ? "0" : "") + timeFineVisita.getMinutes ()+":00";
        				System.out.println(dataInizioString+"____"+dataFineString);
        				Date dataInizioVisita = Utils.convertStringToDate(dataInizioString, formato);   
        				Date dataFineVisita = Utils.convertStringToDate(dataFineString, formato);   
        				Prenotazione pren = prenotazioniOdierne.get(comboPrenotazOdierne.getSelectionIndex());
        				int idPren = pren.getIdPrenotazione();
        				Prenotazione prenotazione = VisitaDAO.getPrenotazioneByID(idPren);
        				Medico medico = medici.get(comboMedicoVisita.getSelectionIndex());
        				VisitaDAO visita = new VisitaDAO();
        				visita.registraVisita(dataInizioVisita, dataFineVisita, textAreaMotivazioni.getText(), textAreaNote.getText(), medico, prenotazione);
        				labelFatturaAssociata.setVisible(true);
        				System.out.println("visita registrata");
        				aggiornaTableView();
        				sShellRegistraVisita.close();
        			}
        		});
		sShellRegistraVisita.open();
	}
		

	private void createComboPrenotazOdierne() {
		comboPrenotazOdierne = new Combo(sShellRegistraVisita, SWT.READ_ONLY);
		comboPrenotazOdierne.setBounds(new Rectangle(161, 15, 260, 25));
		Date now = new Date();
		prenotazioni = (ArrayList<Prenotazione>) VisitaDAO.getPrenotazioni();
		ArrayList<String> pren = new ArrayList<String>();
		//final ArrayList<Prenotazione> prenotazioniOdierne = new ArrayList<Prenotazione>();
		int index = 0;
		for (Prenotazione prenotazione : prenotazioni) {
			if (prenotazione.getDataOra().getDate()== now.getDate() && 
					prenotazione.getDataOra().getMonth()== now.getMonth() &&
					prenotazione.getDataOra().getYear()== now.getYear()) { 
				prenotazioniOdierne.add(index, prenotazione);
				index++;
				pren.add(prenotazione.getPaziente().getCognome()+" "+prenotazione.getPaziente().getNome()+"  - "+prenotazione.getTipologiavisita().getTipologia());
			}
		}
		String[] prenotazioniArray = (String[]) pren.toArray((new String[0]));
		comboPrenotazOdierne.setItems(prenotazioniArray);
		comboPrenotazOdierne
				.addSelectionListener(new org.eclipse.swt.events.SelectionListener() {
					public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
						int selez = comboPrenotazOdierne.getSelectionIndex();
						textAreaInfoPrenotazione.setText("Paziente: "+prenotazioniOdierne.get(selez).getPaziente().getCognome()+" "+prenotazioniOdierne.get(selez).getPaziente().getNome()+"\n"+
															"Data di nascita paziente: "+prenotazioniOdierne.get(selez).getPaziente().getDataNascita()+"\n"+
															"Codice fiscale paziente: "+prenotazioniOdierne.get(selez).getPaziente().getCodiceFiscale()+"\n"+
															"Indirizzo paziente: "+prenotazioniOdierne.get(selez).getPaziente().getCitta()+"  "+prenotazioniOdierne.get(selez).getPaziente().getIndirizzo()+"\n"+
															"Tessera sanitaria paziente: "+prenotazioniOdierne.get(selez).getPaziente().getNumTesseraSanitaria()+"\n\n"+
															"Tipologia visita prenotata: "+prenotazioniOdierne.get(selez).getTipologiavisita().getTipologia()+"\n"+
															"Costo visita prenotata: "+prenotazioniOdierne.get(selez).getTipologiavisita().getCostoVisita()+"\n"+
															"Data e ora prenotazione: "+prenotazioniOdierne.get(selez).getDataOra()+"\n"+
															"Eventuali note sulla prenotazione: "+prenotazioniOdierne.get(selez).getNote());	
					}
					public void widgetDefaultSelected(
							org.eclipse.swt.events.SelectionEvent e) {
					}
				});
		
	}


	private void createComboMedicoVisita() {
		comboMedicoVisita = new Combo(sShellRegistraVisita, SWT.READ_ONLY);
		comboMedicoVisita.setBounds(new Rectangle(165, 147, 209, 22));
		medici = (ArrayList<Medico>) MedicoDAO.getMedici();
		ArrayList<String> med = new ArrayList<String>();
		for (Medico medico : medici) {
			med.add(medico.getCognome()+"_"+medico.getNome());
		}
		String[] mediciArray = (String[]) med.toArray((new String[0]));
		comboMedicoVisita.setItems(mediciArray);
	}

	private void createComboGiornoVisita() {
		comboGiornoVisita = new Combo(sShellRegistraVisita, SWT.READ_ONLY);
		comboGiornoVisita.setBounds(new Rectangle(165, 192, 91, 23));
		for (int i = 1; i < 32; i++) {
			comboGiornoVisita.add(""+i);
		}
		//comboGiornoVisita.setItems(new String [] {"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31"});
		comboGiornoVisita.setText(comboGiornoVisita.getItem(0));
	}

	
	private void createGroupPrenotazione() {
		groupPrenotazione = new Group(sShellRegistraVisita, SWT.NONE);
		groupPrenotazione.setLayout(new GridLayout());
		groupPrenotazione.setBounds(new Rectangle(5, 1, 474, 136));
		groupPrenotazione.setText("Prenotazione");
	}
	
	private void createGroupDataVisita() {
		groupDataVisita = new Group(sShellRegistraVisita, SWT.NONE);
		groupDataVisita.setLayout(new GridLayout());
		groupDataVisita.setText("Data della visita");
		groupDataVisita.setBounds(new Rectangle(7, 176, 300, 85));
	}

	/**
	 * This method initializes sShellDettagliVisita	
	 *
	 */
	public void createSShellDettagliVisita(final TableItem rigaTableClick) {
		int idVis = Integer.parseInt(rigaTableClick.getText(0));
		final Visita vis = VisitaDAO.getVisitaByID(idVis);
		sShellDettagliVisita = new Shell(SWT.APPLICATION_MODAL | SWT.SHELL_TRIM);
		//sShellDettagliVisita.setLayout(new GridLayout());
		sShellDettagliVisita.setSize(new Point(382, 430));
		sShellDettagliVisita.setText("Dettagli visita");
		labelPazienteVis = new Label(sShellDettagliVisita, SWT.NONE);
		labelPazienteVis.setBounds(new Rectangle(6, 9, 64, 22));
		labelPazienteVis.setText("Paziente :");
		textPazienteVis = new Text(sShellDettagliVisita, SWT.BORDER);
		textPazienteVis.setBounds(new Rectangle(80, 9, 283, 22));
		textPazienteVis.setEditable(false);
		textPazienteVis.setText(vis.getPrenotazione().getPaziente().getCognome()+" "+vis.getPrenotazione().getPaziente().getNome());

		labelTipologVis = new Label(sShellDettagliVisita, SWT.NONE);
		labelTipologVis.setBounds(new Rectangle(6, 36, 98, 22));
		labelTipologVis.setText("Tipologia visita :");
		textTipologiaVis = new Text(sShellDettagliVisita, SWT.BORDER);
		textTipologiaVis.setBounds(new Rectangle(114, 36, 248, 22));
		textTipologiaVis.setEditable(false);
		textTipologiaVis.setText(vis.getPrenotazione().getTipologiavisita().getTipologia());
		labelCostoVisita = new Label(sShellDettagliVisita, SWT.NONE);
		labelCostoVisita.setBounds(new Rectangle(6, 63, 98, 22));
		labelCostoVisita.setText("Costo visita :");
		textCostoVisita = new Text(sShellDettagliVisita, SWT.BORDER);
		textCostoVisita.setBounds(new Rectangle(115, 63, 200, 22));
		textCostoVisita.setEditable(false);
		textCostoVisita.setText(""+vis.getPrenotazione().getTipologiavisita().getCostoVisita());
		labelMedicoVis = new Label(sShellDettagliVisita, SWT.NONE);
		labelMedicoVis.setBounds(new Rectangle(6, 89, 98, 22));
		labelMedicoVis.setText("Medico :");
		textMedicoVis = new Text(sShellDettagliVisita, SWT.BORDER);
		textMedicoVis.setBounds(new Rectangle(115, 89, 200, 22));
		textMedicoVis.setEditable(false);
		textMedicoVis.setText(vis.getMedico().getCognome()+" "+vis.getMedico().getNome());
		labelDataOraInizioVis = new Label(sShellDettagliVisita, SWT.NONE);
		labelDataOraInizioVis.setBounds(new Rectangle(6, 116, 100, 22));
		labelDataOraInizioVis.setText("Data e ora inizio :");
		textDataOraInizioVis = new Text(sShellDettagliVisita, SWT.BORDER);
		textDataOraInizioVis.setBounds(new Rectangle(115, 116, 200, 22));
		textDataOraInizioVis.setEditable(false);
		textDataOraInizioVis.setText(vis.getDataOraInizio().toLocaleString());
		labelDataOraFineVis = new Label(sShellDettagliVisita, SWT.NONE);
		labelDataOraFineVis.setBounds(new Rectangle(6, 144, 100, 22));
		labelDataOraFineVis.setText("Data e ora fine :");
		textDataOraFineVis = new Text(sShellDettagliVisita, SWT.BORDER);
		textDataOraFineVis.setBounds(new Rectangle(115, 144, 200, 22));
		textDataOraFineVis.setEditable(false);
		textDataOraFineVis.setText(vis.getDataOraFine().toLocaleString());
		labelStatoPagamento = new Label(sShellDettagliVisita, SWT.NONE);
		labelStatoPagamento.setBounds(new Rectangle(6, 172, 105, 22));
		labelStatoPagamento.setText("Stato pagamento :");
		textStatoPagamento = new Text(sShellDettagliVisita, SWT.BORDER);
		textStatoPagamento.setBounds(new Rectangle(114, 172, 200, 22));
		textStatoPagamento.setEditable(false);
		textStatoPagamento.setText(vis.getStatoPagamento());
		labelMotivazioniVis = new Label(sShellDettagliVisita, SWT.NONE);
		labelMotivazioniVis.setBounds(new Rectangle(6, 200, 83, 22));
		labelMotivazioniVis.setText("Motivazioni :");
		textAreaMotivazioniVis = new Text(sShellDettagliVisita, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL);
		textAreaMotivazioniVis.setBounds(new Rectangle(96, 200, 255, 72));
		textAreaMotivazioniVis.setEditable(false);
		textAreaMotivazioniVis.setText(vis.getMotivazioni());
		labelNoteVis = new Label(sShellDettagliVisita, SWT.NONE);
		labelNoteVis.setBounds(new Rectangle(6, 281, 50, 22));
		labelNoteVis.setText("Note :");
		textAreaNoteVis = new Text(sShellDettagliVisita, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL);
		textAreaNoteVis.setBounds(new Rectangle(66, 281, 284, 72));
		textAreaNoteVis.setEditable(false);
		textAreaNoteVis.setText(vis.getNote());
		buttonOk = new Button(sShellDettagliVisita, SWT.NONE);
		buttonOk.setBounds(new Rectangle(229, 364, 120, 25));
		buttonOk.setText("Chiudi dettagli");
		buttonOk.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				sShellDettagliVisita.close();
			}
		});
		
		
		sShellDettagliVisita.open();
	}
	
	private void aggiornaTableView(){
		classVis.getTableVisualizzazione().removeAll(); //rimuove le righe
		//rimuove le colonne
		int k = 0;
		while (k<classVis.getTableVisualizzazione().getColumnCount()) {
			classVis.getTableVisualizzazione().getColumn(k).dispose();
		}
		visite = VisitaDAO.getVisiteObject();
		classVis.riempiTabella(visite, "VisitaTableView");
		classVis.nascondiColonne(new int[] {0,1,2,3,6,7,8});
		aggiungiColonne(classVis, visite);    				
		//classVis.aggiornaCombo();
		classVis.ordinamentoData(classVis.getTableVisualizzazione(), 4);
		classVis.ordinamentoData(classVis.getTableVisualizzazione(), 5);
		classVis.ordinamentoStringhe(classVis.getTableVisualizzazione(), 9);
		classVis.ordinamentoStringhe(classVis.getTableVisualizzazione(), 10);
		classVis.ordinamentoStringhe(classVis.getTableVisualizzazione(), 11);
	}

	
}  //  @jve:decl-index=0:visual-constraint="10,10,310,351"
