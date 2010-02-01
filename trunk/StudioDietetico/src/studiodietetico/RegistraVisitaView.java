package studiodietetico;

import hibernate.Fattura;
import hibernate.Medico;
import hibernate.Paziente;
import hibernate.Prenotazione;
import hibernate.Tipologiavisita;

import java.util.ArrayList;
import java.util.Date;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.List;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.swt.widgets.Button;

import command.FatturaDAO;
import command.MedicoDAO;
import command.PazienteDAO;
import command.VisitaDAO;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Group;

import service.Utils;

public class RegistraVisitaView extends ViewPart {

	private Composite top = null;
	private Label labelSelezPrenotaz = null;
	private Combo comboPrenotazOdierne = null;
	private ComboViewer comboViewer = null;
	private Text textAreaInfoPrenotazione = null;
	private Label labelInfoPrenotazione = null;
	private Label labelSelezMedico = null;
	private Combo comboMedicoVisita = null;
	private ComboViewer comboViewer1 = null;
	private Label labelDataVisita = null;
	private Combo comboGiornoVisita = null;
	private Combo comboMeseVisita = null;
	private Combo comboAnnoVisita = null;
	private Label labelOraInizioVisita = null;
	private Combo comboOraInizioVisita = null;
	private Combo comboOraMinInizioVisita = null;
	private Label labelOraFineVisita = null;
	private Combo comboOraFineVisita = null;
	private Combo comboOraMinFineVisita = null;
	private Label labelMotivazioni = null;
	private Text textAreaMotivazioni = null;
	private Label labelStatoPagamento = null;
	private Text textStatoPagamento = null;
	private Label labelNote = null;
	private Text textAreaNote = null;
	private Label labelFattura = null;
	private List listFatture = null;
	private ListViewer listViewer = null;
	private Button buttonCreaNuovaFattura = null;
	private Button buttonRegistraVisita = null;
	private ArrayList<Medico> medici;  //  @jve:decl-index=0:
	private ArrayList<Prenotazione> prenotazioni;  //  @jve:decl-index=0:
	private Shell sShellNuovaFattura = null;  //  @jve:decl-index=0:visual-constraint="17,722"
	private Group groupPrenotazione = null;
	private Label labelNumFattura = null;
	private Text textNumeroFattura = null;
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
	private Group groupDataVisita = null;
	private ArrayList<Fattura> fatt;
	private int idFatt;
	private Button buttonAssociaFattura = null;
	private Button buttonAggiornaFattura = null;
	private Fattura fatturaSelezionata = null;
	private Label labelFatturaAssociata = null;
	final ArrayList<Prenotazione> prenotazioniOdierne = new ArrayList<Prenotazione>();  //  @jve:decl-index=0:
	
	public RegistraVisitaView() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void createPartControl(Composite parent) {
        top = new Composite(parent, SWT.NONE);
		// TODO Auto-generated method stub

        labelSelezPrenotaz = new Label(top, SWT.WRAP);
        labelSelezPrenotaz.setBounds(new Rectangle(10, 15, 141, 32));
        labelSelezPrenotaz.setText("* Seleziona la prenotazione per la data odierna:");
        createComboPrenotazOdierne();
        textAreaInfoPrenotazione = new Text(top, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL);
        textAreaInfoPrenotazione.setBounds(new Rectangle(161, 50, 294, 82));
        textAreaInfoPrenotazione.setEditable(false);
        labelInfoPrenotazione = new Label(top, SWT.WRAP);
        labelInfoPrenotazione.setBounds(new Rectangle(10, 50, 143, 39));
        labelInfoPrenotazione.setText("Info sulla prenotazione selezionata:");
        labelSelezMedico = new Label(top, SWT.WRAP);
        labelSelezMedico.setBounds(new Rectangle(10, 142, 144, 32));
        labelSelezMedico.setText("* Seleziona il medico che ha effettuato la visita:");
        createComboMedicoVisita();
        labelDataVisita = new Label(top, SWT.NONE);
        labelDataVisita.setBounds(new Rectangle(10, 195, 123, 18));
        labelDataVisita.setText("* Data della visita:");
        createComboGiornoVisita();
        createComboMeseVisita();
        createComboAnnoVisita();
        labelOraInizioVisita = new Label(top, SWT.NONE);
        labelOraInizioVisita.setBounds(new Rectangle(10, 225, 117, 20));
        labelOraInizioVisita.setText("* Ora inizio visita:");
        createComboOraInizioVisita();
        createComboOraMinInizioVisita();
        labelOraFineVisita = new Label(top, SWT.NONE);
        labelOraFineVisita.setBounds(new Rectangle(10, 262, 115, 18));
        labelOraFineVisita.setText("* Ora fine visita:");
        createComboOraFineVisita();
        createComboOraMinFineVisita();
        labelMotivazioni = new Label(top, SWT.NONE);
        labelMotivazioni.setBounds(new Rectangle(10, 295, 129, 22));
        labelMotivazioni.setText("Motivazioni della visita:");
        textAreaMotivazioni = new Text(top, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL);
        textAreaMotivazioni.setBounds(new Rectangle(165, 294, 330, 55));
        //textAreaMotivazioni.setTextLimit(300);
        labelStatoPagamento = new Label(top, SWT.NONE);
        labelStatoPagamento.setBounds(new Rectangle(10, 360, 108, 21));
        labelStatoPagamento.setText("* Stato pagamento:");
        textStatoPagamento = new Text(top, SWT.BORDER);
        textStatoPagamento.setBounds(new Rectangle(134, 360, 307, 21));
        textStatoPagamento.setTextLimit(45);
        labelNote = new Label(top, SWT.NONE);
        labelNote.setBounds(new Rectangle(12, 394, 41, 17));
        labelNote.setText("Note:");
        textAreaNote = new Text(top, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL);
        textAreaNote.setBounds(new Rectangle(76, 392, 336, 62));
        //textAreaNote.setTextLimit(300);
        labelFattura = new Label(top, SWT.WRAP);
        labelFattura.setBounds(new Rectangle(12, 464, 191, 37));
        labelFattura.setText("Si desidera creare una nuova fattura o associare una fattura esistente?");
        listFatture = new List(top, SWT.V_SCROLL | SWT.H_SCROLL);
        listFatture.setBounds(new Rectangle(15, 504, 501, 133));
        listFatture.setEnabled(false);
        buttonCreaNuovaFattura = new Button(top, SWT.MULTI);
        buttonCreaNuovaFattura.setBounds(new Rectangle(210, 467, 176, 25));
        buttonCreaNuovaFattura.setText("Crea e associa una nuova fattura");
        buttonCreaNuovaFattura
        		.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
        			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
        				//test.sShellNuovaFattura.open();
        				listFatture.setEnabled(false);
        				createSShellNuovaFattura();  				
        				System.out.println("widgetSelected()"); // TODO Auto-generated Event stub widgetSelected()
        			}
        		});
        buttonRegistraVisita = new Button(top, SWT.NONE);
        buttonRegistraVisita.setBounds(new Rectangle(369, 640, 147, 25));
        buttonRegistraVisita.setText("Registra visita");
        createGroupPrenotazione();
        createGroupDataVisita();
        buttonAssociaFattura = new Button(top, SWT.NONE);
        buttonAssociaFattura.setBounds(new Rectangle(389, 467, 158, 25));
        buttonAssociaFattura.setText("Associa una fattura esistente");
        labelFatturaAssociata = new Label(top, SWT.NONE);
        labelFatturaAssociata.setBounds(new Rectangle(15, 644, 332, 18));
        labelFatturaAssociata.setText("Fattura correttamente associata alla visita. Registrare la visita");
        labelFatturaAssociata.setVisible(false);
        buttonAssociaFattura
        		.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
        			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
        				listFatture.setEnabled(true);
        				System.out.println("widgetSelected()"); // TODO Auto-generated Event stub widgetSelected()
        			}
        		});

        buttonRegistraVisita
        		.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
        			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
        				String dateInizioString = comboAnnoVisita.getText()+"-"+comboMeseVisita.getText()+"-"+comboGiornoVisita.getText()+" "+comboOraInizioVisita.getText()+":"+comboOraMinInizioVisita.getText()+":00";
        				String formato = "yyyy-MM-dd HH:mm:ss";
        				Date dataInizioVisita = Utils.convertStringToDate(dateInizioString, formato);   
        				String dateFineString = comboAnnoVisita.getText()+"-"+comboMeseVisita.getText()+"-"+comboGiornoVisita.getText()+" "+comboOraFineVisita.getText()+":"+comboOraMinFineVisita.getText()+":00";
        				Date dataFineVisita = Utils.convertStringToDate(dateFineString, formato);   
        				Prenotazione pren = prenotazioniOdierne.get(comboPrenotazOdierne.getSelectionIndex());
        				int idPren = pren.getIdPrenotazione();
        				Prenotazione prenotazione = VisitaDAO.getPrenotazioneByID(idPren);
        				Medico medico = medici.get(comboMedicoVisita.getSelectionIndex());
        				VisitaDAO visita = new VisitaDAO();
        				if (fatturaSelezionata==null) {
        					FatturaDAO f = new FatturaDAO();
        					Fattura fattura = f.getFatturaByID(idFatt);
        					visita.registraVisita(dataInizioVisita, dataFineVisita, textAreaMotivazioni.getText(), textStatoPagamento.getText(), textAreaNote.getText(), fattura, medico, prenotazione);
						} else {
							visita.registraVisita(dataInizioVisita, dataFineVisita, textAreaMotivazioni.getText(), textStatoPagamento.getText(), textAreaNote.getText(), fatturaSelezionata, medico, prenotazione);
						}
        				
        				System.out.println("visita registrata"); // TODO Auto-generated Event stub widgetSelected()
        			}
        		});
        fatt = FatturaDAO.getFatture();
		ArrayList<String> f = new ArrayList<String>();
		for (Fattura fattura : fatt) {
			f.add("Data fattura: "+fattura.getData().getDate()+"/"+(fattura.getData().getMonth()+1)+"/"+(fattura.getData().getYear()+1900)+"  -- Descrizione: "+fattura.getDescrizione()+"  -- Note: "+fattura.getNote());
		}
		String[] FattureArray = (String[]) f.toArray((new String[0]));
        listFatture.setItems(FattureArray);
        listFatture.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
        	public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
        		createSShellNuovaFattura();
        		sShellNuovaFattura.setText("Associa una fattura");
        		FatturaDAO fatturaSel = new FatturaDAO();
        		int fatturaselez = listFatture.getSelectionIndex();
        		Fattura fatturasel = fatt.get(fatturaselez);
        		int idFatturasel = fatturasel.getIdFattura();
        		fatturaSelezionata = fatturaSel.getFatturaByID(idFatturasel);
        		textDescrizioneFattura.setText(fatturaSelezionata.getDescrizione());
        		textImportoFatt.setText(""+fatturaSelezionata.getImporto());
        		textImportoAcconto.setText(""+fatturaSelezionata.getAcconto());
        		textImportoSconto.setText(""+fatturaSelezionata.getImportoSconto());
        		textAreaNoteFattura.setText(fatturaSelezionata.getNote());
        		buttonRegistraFattura.setVisible(false);
        		buttonAggiornaFattura.setVisible(true);
        		
        		System.out.println("widgetSelected()"); // TODO Auto-generated Event stub widgetSelected()
        	}
        });
        listViewer = new ListViewer(listFatture);
	}

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub

	}

	/**
	 * This method initializes comboPrenotazOdierne	
	 *
	 */
	private void createComboPrenotazOdierne() {
		comboPrenotazOdierne = new Combo(top, SWT.READ_ONLY);
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
			//pren.add(prenotazione.getPaziente().getCognome()+" "+prenotazione.getPaziente().getNome()+"   "+prenotazione.getTipologiavisita().getTipologia()+"   "+prenotazione.getDataOra());
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
															"Tessera sanitaria paziente: "+prenotazioniOdierne.get(selez).getPaziente().getNumTesseraSanitaria()+"\n"+
															"Tipologia visita prenotata: "+prenotazioniOdierne.get(selez).getTipologiavisita().getTipologia()+"\n"+
															"Costo visita prenotata: "+prenotazioniOdierne.get(selez).getTipologiavisita().getCostoVisita()+"\n"+
															"Data e ora prenotazione: "+prenotazioniOdierne.get(selez).getDataOra()+"\n"+
															"Eventuali note sulla prenotazione: "+prenotazioniOdierne.get(selez).getNote());	
						System.out.println("widgetSelected()"); // TODO Auto-generated Event stub widgetSelected()
					}
					public void widgetDefaultSelected(
							org.eclipse.swt.events.SelectionEvent e) {
					}
				});
		comboViewer = new ComboViewer(comboPrenotazOdierne);
		
	}

	/**
	 * This method initializes comboMedicoVisita	
	 *
	 */
	private void createComboMedicoVisita() {
		comboMedicoVisita = new Combo(top, SWT.READ_ONLY);
		comboMedicoVisita.setBounds(new Rectangle(165, 146, 172, 26));
		comboViewer1 = new ComboViewer(comboMedicoVisita);
		medici = (ArrayList<Medico>) MedicoDAO.getMedici();
		ArrayList<String> med = new ArrayList<String>();
		for (Medico medico : medici) {
			med.add(medico.getCognome()+"_"+medico.getNome());
		}
		String[] mediciArray = (String[]) med.toArray((new String[0]));
		comboMedicoVisita.setItems(mediciArray);
	}

	/**
	 * This method initializes comboGiornoVisita	
	 *
	 */
	private void createComboGiornoVisita() {
		comboGiornoVisita = new Combo(top, SWT.READ_ONLY);
		comboGiornoVisita.setBounds(new Rectangle(165, 192, 91, 23));
		for (int i = 1; i < 32; i++) {
			comboGiornoVisita.add(""+i);
		}
		//comboGiornoVisita.setItems(new String [] {"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31"});
		comboGiornoVisita.setText(comboGiornoVisita.getItem(0));
	}

	/**
	 * This method initializes comboMeseVisita	
	 *
	 */
	private void createComboMeseVisita() {
		comboMeseVisita = new Combo(top, SWT.READ_ONLY);
		comboMeseVisita.setBounds(new Rectangle(273, 192, 91, 23));
		for (int i = 1; i < 13; i++) {
			comboMeseVisita.add(""+i);
		}
		//comboMeseVisita.setItems(new String [] {"1","2","3","4","5","6","7","8","9","10","11","12"});
		comboMeseVisita.setText(comboMeseVisita.getItem(0));
	}

	/**
	 * This method initializes comboAnnoVisita	
	 *
	 */
	private void createComboAnnoVisita() {
		comboAnnoVisita = new Combo(top, SWT.READ_ONLY);
		comboAnnoVisita.setBounds(new Rectangle(380, 192, 91, 23));
		Date now = new Date();
		for (int i = (now.getYear()+1899); i < (now.getYear()+1901); i++) {
			comboAnnoVisita.add(""+i);
		}
		//comboAnnoVisita.setItems(new String [] {"2009","2010","2011","2012","2013","2014","2015"});
		comboAnnoVisita.setText(comboAnnoVisita.getItem(0));
	}

	/**
	 * This method initializes comboOraInizioVisita	
	 *
	 */
	private void createComboOraInizioVisita() {
		comboOraInizioVisita = new Combo(top, SWT.READ_ONLY);
		comboOraInizioVisita.setBounds(new Rectangle(165, 223, 72, 24));
		comboOraInizioVisita.setItems(new String [] {"01","02","03","04","05","06","07","08","09","10","11","12","13","14","15","16","17","18","19","20","21","22","23"});
		comboOraInizioVisita.setText(comboOraInizioVisita.getItem(0));
	}

	/**
	 * This method initializes comboOraMinInizioVisita	
	 *
	 */
	private void createComboOraMinInizioVisita() {
		comboOraMinInizioVisita = new Combo(top, SWT.READ_ONLY);
		comboOraMinInizioVisita.setBounds(new Rectangle(254, 223, 72, 23));
		comboOraMinInizioVisita.setItems(new String [] {"00","01","02","03","04","05","06","07","08","09",
				"10","11","12","13","14","15","16","17","18","19",
				"20","21","22","23","24","25","26","27","28","29",
				"30","31","32","33","34","35","36","37","38","39",
				"40","41","42","43","44","45","46","47","48","49",
				"50","51","52","53","54","55","56","57","58","59",});
		comboOraMinInizioVisita.setText(comboOraMinInizioVisita.getItem(0));
	}

	/**
	 * This method initializes comboOraFineVisita	
	 *
	 */
	private void createComboOraFineVisita() {
		comboOraFineVisita = new Combo(top, SWT.READ_ONLY);
		comboOraFineVisita.setBounds(new Rectangle(165, 259, 72, 22));
		comboOraFineVisita.setItems(new String [] {"01","02","03","04","05","06","07","08","09","10","11","12","13","14","15","16","17","18","19","20","21","22","23"});
		comboOraFineVisita.setText(comboOraFineVisita.getItem(0));
	}

	/**
	 * This method initializes comboOraMinFineVisita	
	 *
	 */
	private void createComboOraMinFineVisita() {
		comboOraMinFineVisita = new Combo(top, SWT.READ_ONLY);
		comboOraMinFineVisita.setBounds(new Rectangle(254, 260, 72, 22));
		comboOraMinFineVisita.setItems(new String [] {"00","01","02","03","04","05","06","07","08","09",
				"10","11","12","13","14","15","16","17","18","19",
				"20","21","22","23","24","25","26","27","28","29",
				"30","31","32","33","34","35","36","37","38","39",
				"40","41","42","43","44","45","46","47","48","49",
				"50","51","52","53","54","55","56","57","58","59",});
		comboOraMinFineVisita.setText(comboOraMinFineVisita.getItem(0));
	}

	/**
	 * This method initializes sShellNuovaFattura	
	 *
	 */
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
			System.out.println("widgetSelected()"); // TODO Auto-generated Event stub widgetSelected()
		}
	});
		radioButtonScontoNo = new Button(sShellNuovaFattura, SWT.RADIO);
		radioButtonScontoNo.setBounds(new Rectangle(447, 75, 39, 16));
		radioButtonScontoNo.setText("No");
		radioButtonScontoNo.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				labelImportoSconto.setVisible(false);
				textImportoSconto.setVisible(false);
			System.out.println("widgetSelected()"); // TODO Auto-generated Event stub widgetSelected()
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
						labelFatturaAssociata.setVisible(true);
						listFatture.setEnabled(false);
						buttonCreaNuovaFattura.setEnabled(false);
						buttonAssociaFattura.setEnabled(false);
						sShellNuovaFattura.close();
						System.out.println("fattura registrata"); // TODO Auto-generated Event stub widgetSelected()
					}
				});
		buttonAnnullaFattura = new Button(sShellNuovaFattura, SWT.NONE);
		buttonAnnullaFattura.setBounds(new Rectangle(286, 270, 107, 29));
		buttonAnnullaFattura.setText("Chiudi");
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
		buttonAggiornaFattura
				.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
					public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
						FatturaDAO fattura = new FatturaDAO(); 
						//fattura.aggiornaFattura(fatturaSelezionata, textDescrizioneFattura.getText(), textImportoFatt.getText(), textImportoAcconto.getText(), textImportoSconto.getText(), textAreaNoteFattura.getText());
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
						labelFatturaAssociata.setVisible(true);
						listFatture.setEnabled(false);
						buttonCreaNuovaFattura.setEnabled(false);
						buttonAssociaFattura.setEnabled(false);
						sShellNuovaFattura.close();
						System.out.println("fattura aggiornata/associata"); // TODO Auto-generated Event stub widgetSelected()
					}
				});
		buttonAnnullaFattura
				.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
					public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
						sShellNuovaFattura.close();
						System.out.println("widgetSelected()"); // TODO Auto-generated Event stub widgetSelected()
					}
				});
		
		sShellNuovaFattura.open();
	}

	/**
	 * This method initializes groupPrenotazione	
	 *
	 */
	private void createGroupPrenotazione() {
		groupPrenotazione = new Group(top, SWT.NONE);
		groupPrenotazione.setLayout(new GridLayout());
		groupPrenotazione.setBounds(new Rectangle(5, 1, 474, 136));
		groupPrenotazione.setText("Prenotazione");
	}

	/**
	 * This method initializes groupImportoAcconto	
	 *
	 */
	private void createGroupImportoAcconto() {
		groupImportoAcconto = new Group(compositeAcconto, SWT.NONE);
		//groupImportoAcconto.setLayout(new GridLayout());
		groupImportoAcconto.setText("Stato pagamento");
		groupImportoAcconto.setBounds(new Rectangle(3, 0, 240, 110));
	}

	/**
	 * This method initializes groupScontoFattura	
	 *
	 */
	private void createGroupScontoFattura() {
		groupScontoFattura = new Group(sShellNuovaFattura, SWT.NONE);
		groupScontoFattura.setLayout(new GridLayout());
		groupScontoFattura.setText("Sconto sulla fattura");
		groupScontoFattura.setBounds(new Rectangle(255, 52, 247, 105));
	}

	/**
	 * This method initializes compositeAcconto	
	 *
	 */
	private void createCompositeAcconto() {
		compositeAcconto = new Composite(sShellNuovaFattura, SWT.NONE);
		//compositeAcconto.setLayout(new GridLayout());
		compositeAcconto.setBounds(new Rectangle(7, 49, 246, 117));
		labelImportoPagato = new Label(compositeAcconto, SWT.WRAP);
		labelImportoPagato.setBounds(new Rectangle(10, 15, 112, 32));
		labelImportoPagato.setText("L'importo è stato pagato totalmente?");
		radioButtonImportoSi = new Button(compositeAcconto, SWT.RADIO);
		radioButtonImportoSi.setBounds(new Rectangle(140, 20, 38, 20));
		radioButtonImportoSi.setText("Si");
		radioButtonImportoSi.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
					labelAcconto.setVisible(false);
					textImportoAcconto.setVisible(false);
				System.out.println("widgetSelected()"); // TODO Auto-generated Event stub widgetSelected()
			}
		});
		radioButtonImportoNo = new Button(compositeAcconto, SWT.RADIO);
		radioButtonImportoNo.setBounds(new Rectangle(185, 20, 39, 20));
		radioButtonImportoNo.setText("No");
		radioButtonImportoNo.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
					labelAcconto.setVisible(true);
					textImportoAcconto.setVisible(true);
				System.out.println("widgetSelected()"); // TODO Auto-generated Event stub widgetSelected()
			}
		});
		labelAcconto = new Label(compositeAcconto, SWT.WRAP);
		labelAcconto.setBounds(new Rectangle(10, 60, 102, 34));
		labelAcconto.setText("Importo acconto versato (euro):");
		labelAcconto.setVisible(false);
		textImportoAcconto = new Text(compositeAcconto, SWT.BORDER);
		textImportoAcconto.setBounds(new Rectangle(130, 63, 90, 30));
		textImportoAcconto.setVisible(false);
		createGroupImportoAcconto();
	}

	/**
	 * This method initializes groupDataVisita	
	 *
	 */
	private void createGroupDataVisita() {
		groupDataVisita = new Group(top, SWT.NONE);
		groupDataVisita.setLayout(new GridLayout());
		groupDataVisita.setText("Data della visita");
		groupDataVisita.setBounds(new Rectangle(7, 176, 490, 113));
	}

	/*
	 * Temporary main generation 
	 */
	public static void main(String[] args) {
		// before you run this, make sure to set up the following in
		// the launch configuration (Arguments->VM Arguments) for the correct SWT lib. path
		// the following is a windows example,
		// -Djava.library.path="installation_directory\plugins\org.eclipse.swt.win32_3.0.1\os\win32\x86"
/*		org.eclipse.swt.widgets.Display display = org.eclipse.swt.widgets.Display
				.getDefault();
		RegistraVisitaView test = new RegistraVisitaView();
		test.createSShellNuovaFattura();
		test.sShellNuovaFattura.open();
	
		while (!test.sShellNuovaFattura.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();*/
	}

}  //  @jve:decl-index=0:visual-constraint="10,10,611,668"
