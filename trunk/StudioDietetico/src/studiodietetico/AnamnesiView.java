package studiodietetico;

import forms.HomePazienteForm;
import hibernate.Attivitafisica;
import hibernate.Intervento;
import hibernate.Paziente;
import hibernate.Tipologiaintervento;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.widgets.Spinner;

import command.AnamnesiDAO;
import command.PazienteDAO;

import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;

import service.RegistraIntervento;
import service.Utils;
import studiodietetico.HomePazienteView;
import org.eclipse.swt.widgets.Table;
import common.ui.ListComposite;

public class AnamnesiView extends ViewPart {
	
	//GENERALE
	private Label labelPaziente = null;  //  @jve:decl-index=0:visual-constraint="9,-39"
	private Text textPaziente = null;  //  @jve:decl-index=0:visual-constraint="630,11"
	private Paziente pazSelHome;
	private CTabFolder cTabFolderAnamnesi = null;
	
	//INTERVENTI
	private Label labelNomeInt = null;
	private Text textNomeInt = null;
	private Label labelDescrInt = null;
	private Text textAreaDescrInt = null;
	private Label labelLocalizzazione = null;
	private Text textAreaLocalizzazione = null;
	//private Group groupInsInt = null;
	private Button buttonInsertNewInt = null;
	private Label labelSceltaInt = null;
	private List listInterventi = null;  
	private Button buttonAddIntSel = null;
	private List listIntSel = null;
	private Button buttonConfermaInt = null;
	private Label labelNumInt = null;
	private Spinner spinnerNumInt = null;
	private Label labelData = null;
	private Combo cComboGG = null;
	private Combo cComboMese = null;
	private Combo cComboAnno = null;
	private Button buttonInsInt = null;
	private Button buttonModificaInt = null;
	private Button buttonEliminaInt = null;
	private Shell sShellNumData = null;  //  @jve:decl-index=0:visual-constraint="304,231"
	private Button buttonOKNumData = null;
	private Set<String> setInt = new HashSet<String>();
	private ArrayList<Tipologiaintervento> listInterventiDB;  //  @jve:decl-index=0:
	private Tipologiaintervento interventoSelez;  
	private ArrayList<RegistraIntervento> listaInterventiRegistrati = new ArrayList<RegistraIntervento>();  //  @jve:decl-index=0:
	private ArrayList<Intervento> listIntervPazDB;
	private Shell sShellInserimentoInterventi = null;  //  @jve:decl-index=0:visual-constraint="-20,0"
	private Group groupInserimentoInt = null;
	//ALLERGIE
	private Group groupAllergie = null;
	private Label labelIntAll = null;
	private Button radioButtonInt = null;
	private Button radioButtonAll = null;
	private Label labelSostanza = null;
	private Text textSost = null;
	private Label labelAlPrinc = null;
	private Text textAlPrinc = null;
	private Label labelDerivati = null;
	private Text textAreaDerivati = null;
	private Label labelGrado = null;
	private Label labelEffColl = null;
	private Text textAreaEffColl = null;
	private Button buttonConfermaAll = null;
	private Text textGrado = null;
	//ATTIVITA' FISICA
	private Group groupAttFisica = null;
	private Label labelAttFisSel = null;
	private List textAreaAttFis = null;
	private Button buttonVaiAttFis = null;
	private List textAreaAttFisSel = null;
	private Label labelNomeAttFis = null;
	private Text textNomeAttFis = null;
	private Label labelDescAttFis = null;
	private Text textAreaDescAttFis = null;
	private Button buttonAggiornaListaAttFis = null;
	private Group groupInsAttFis = null;
	private Button buttonInsNewAttFis = null;
	private Label labelDurataAttFis = null;
	private Text textDurataAttFis = null;
	private Label labelFrequenzaAttFis = null;
	private Spinner spinnerFrequenzaAttFis = null;
	private Button buttonConfermaAttFis = null;
	private Shell sShellInsAttFis = null; 
	//private static ArrayList<Object> sport;
	//private Table tableSportPerPaziente = null;
	private Group groupVisualizzazioneSport = null;
	private AnamnesiViewTableSport tableSport;
	private ArrayList<String> listSport;
	private Shell SShellDurFreq = null;
	private Shell sShellProva = null;  //  @jve:decl-index=0:visual-constraint="-23,476"
	private Button buttonOKFreqDur = null;
	
	public static final String VIEW_ID = "StudioDietetico.anamnesi";
	private Shell sShellMessElimina = null;  //  @jve:decl-index=0:visual-constraint="308,396"
	
	
	
	public AnamnesiView() {}

	//-----------------------------------------GENERALE-------------------------------------------------------------
	@Override
	public void setFocus() {}
	
	@Override
	public void createPartControl(Composite parent) {
		
		cTabFolderAnamnesi = new CTabFolder(parent, SWT.TOP);
		cTabFolderAnamnesi.setBounds(new Rectangle(3, 2, 609, 366));
		
		//Visualizzazione del paziente selezionato
		labelPaziente = new Label(cTabFolderAnamnesi, SWT.NONE);
		labelPaziente.setBounds(new Rectangle(14, 30, 82, 24));
		labelPaziente.setText("Paziente");
		textPaziente = new Text(cTabFolderAnamnesi, SWT.BORDER);
		textPaziente.setBounds(new Rectangle(106, 30, 263, 31));
		textPaziente.setEnabled(false);
		pazSelHome = PazienteDAO.getPazienti().get(3);
		//pazSelHome = HomePazienteForm.getPazienteSelezionato();
		String dataNascPazSel = pazSelHome.getDataNascita().getDay()+"/"+pazSelHome.getDataNascita().getMonth()+"/"+pazSelHome.getDataNascita().getYear();
		textPaziente.setText(pazSelHome.getCognome()+"   "+pazSelHome.getNome()+"   "+dataNascPazSel);
		
		//creazione tabItem
		CTabItem itemInt = new CTabItem(cTabFolderAnamnesi, SWT.NONE);
	    itemInt.setText("Interventi");
	    {
	    	Composite comp1 = new Composite(cTabFolderAnamnesi, SWT.TRANSPARENT);
	    	itemInt.setControl(comp1);
	    	createTabInterventi(comp1);
	    } // fine tab interventi
	    
	    
	    CTabItem itemAll = new CTabItem(cTabFolderAnamnesi, SWT.NONE);
	    itemAll.setText("Allergie/Intolleranze");
	    {
	    	Composite comp2 = new Composite(cTabFolderAnamnesi, SWT.TRANSPARENT);
			itemAll.setControl(comp2);
			createGroupAll(comp2);
	    } // fine tab allergie
	    
	    
	    CTabItem itemAttFisica = new CTabItem(cTabFolderAnamnesi, SWT.NONE);
	    itemAttFisica.setText("Attività Fisica");
	    {
	    	Composite comp3 = new Composite(cTabFolderAnamnesi, SWT.TRANSPARENT);
	    	itemAttFisica.setControl(comp3);
			//createGroupAll(comp3);
			
	    } // fine tab attività fisica
	    
	    createSShellProva();
		sShellProva.open();
	}
	
	
	
	//-----------------------------------------INTERVENTI-------------------------------------------------------------
	/**
	 * Crea gli oggetti contenuti nel tab degli interventi
	 * @param comp1 composite nella quale inserire gii oggetti
	 */
	public void createTabInterventi(Composite comp1) {
		interventoSelez = new Tipologiaintervento();
		labelSceltaInt = new Label(comp1, SWT.NONE);
		labelSceltaInt.setBounds(new Rectangle(13, 63, 397, 21));
		labelSceltaInt.setText("Selezionare un intervento");
		listInterventi = new List(comp1, SWT.READ_ONLY | SWT.V_SCROLL | SWT.WRAP);
		listInterventi.setBounds(new Rectangle(13, 91, 330, 115));
		listInterventi
		.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				buttonAddIntSel.setEnabled(true);
			}
		});
		buttonAddIntSel = new Button(comp1, SWT.NONE);
		buttonAddIntSel.setBounds(new Rectangle(350, 113, 70, 30));//350, 90, 70, 30
		buttonAddIntSel.setText(">>");
		buttonAddIntSel.setEnabled(false);
		buttonAddIntSel.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				interventoSelez = listInterventiDB.get(listInterventi.getSelectionIndex());
				//paziente
				//PazienteDAO paz = new PazienteDAO();
				//Paziente paziente = new Paziente();
				//HomePazienteView homeP = new HomePazienteView();
				//paziente = homeP.getPazienteSelezionato();
				//paziente = PazienteDAO.getPazienti().get(3);
				
				createSShellNumData(pazSelHome,interventoSelez);
				
				//listInterventi.deselectAll();
			}
		});
		
		listIntSel = new List(comp1, SWT.READ_ONLY | SWT.V_SCROLL | SWT.WRAP);
		listIntSel.setBounds(new Rectangle(430, 91, 330, 115));
		listIntSel
		.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				//buttonModificaInt.setEnabled(true);
				buttonEliminaInt.setEnabled(true);
			}
		});
		//listIntSel.addListener(SWT., listener)
		
		buttonInsInt = new Button(comp1, SWT.NONE);
		buttonInsInt.setBounds(new Rectangle(13, 208, 98, 21));
		buttonInsInt.setText("Inserisci nuovo");
		buttonInsInt
			.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				createSShellInserimentoInterventi();
			}
		});
		
		/*buttonModificaInt = new Button(comp1, SWT.NONE);
		buttonModificaInt.setBounds(new Rectangle(430, 208, 70, 21));
		buttonModificaInt.setText("Modifica");
		buttonModificaInt.setEnabled(false);
		buttonModificaInt
			.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				openSShellNumData(pazSelHome);
			}
		});*/
		buttonEliminaInt = new Button(comp1, SWT.NONE);
		buttonEliminaInt.setBounds(new Rectangle(430, 208, 70, 21));
		buttonEliminaInt.setText("Elimina");
		buttonEliminaInt.setEnabled(false);
		buttonEliminaInt
			.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				//Inserire messBox
				createSShellMessElimina();
				MessageBox messageBox = new MessageBox(sShellMessElimina, SWT.OK|SWT.CANCEL | SWT.ICON_ERROR);
				messageBox.setMessage("Sei sicuro di voler eliminare questo elemento?");
				messageBox.setText("Conferma eliminazione");
				if (messageBox.open() == SWT.OK) {
					listaInterventiRegistrati.remove(listIntSel.getSelectionIndex());	
					listIntSel.remove(listIntSel.getSelectionIndex());
					if (listIntSel.getItemCount()==0) {
						buttonConfermaInt.setEnabled(false);
						buttonEliminaInt.setEnabled(false);
					}
				}
			}
		});
		
		
		
		buttonConfermaInt = new Button(comp1, SWT.NONE);
		buttonConfermaInt.setBounds(new Rectangle(636, 230, 94, 31)); //636, 266, 94, 31
		buttonConfermaInt.setText("Conferma");
		buttonConfermaInt.setEnabled(false);
		buttonConfermaInt
			.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				AnamnesiDAO interv = new AnamnesiDAO();				
				int numOld = 0, numNew = 0;
				boolean presente = false;
				System.out.println("Size listaReg: "+listaInterventiRegistrati.size());
				listIntervPazDB = new ArrayList<Intervento>();
				listIntervPazDB = interv.getInterventiPaz();
				//prima verifica se è presente nel db una coppia paz-tipoint uguale
				for (int j = 0; j < listaInterventiRegistrati.size(); j++) {
					numOld = 0;
					presente = false;
					if(listIntervPazDB.size() > 0) {
						for (int k = 0; k < listIntervPazDB.size(); k++) {
							if (listaInterventiRegistrati.get(j).getPaziente().getIdPaziente().equals(listIntervPazDB.get(k).getPaziente().getIdPaziente())	&& 
								listaInterventiRegistrati.get(j).getTipoIntervento().getIdTipologiaIntervento().equals(listIntervPazDB.get(k).getTipologiaintervento().getIdTipologiaIntervento())) {
								presente = true;
								numOld = listIntervPazDB.get(k).getNumero();
							}
						}
					}
					if (presente) { //se è presente aggiorna quello esistente
						numNew = numOld + listaInterventiRegistrati.get(j).getNumInterventi();
						interv.aggiornaNumero(listaInterventiRegistrati.get(j).getPaziente(),listaInterventiRegistrati.get(j).getTipoIntervento(),
								listaInterventiRegistrati.get(j).getDataIntervento(),numNew);
					} else {  // altrimenti inserisce uno nuovo
						interv.registraIntervento(listaInterventiRegistrati.get(j).getPaziente(),listaInterventiRegistrati.get(j).getTipoIntervento(),
								listaInterventiRegistrati.get(j).getDataIntervento(), listaInterventiRegistrati.get(j).getNumInterventi());
					}
				}
				
				listaInterventiRegistrati.clear();
				listIntervPazDB.clear();
				
			}
		});
		
		aggiornaListInterventi();
	}
	
	/**
	 * Aggliorna l'oggetto list con gli interventi presenti nel db
	 */
	public void aggiornaListInterventi() {
		listInterventiDB = new ArrayList<Tipologiaintervento>(); 
		AnamnesiDAO interv = new AnamnesiDAO();
		listInterventiDB = interv.getInterventi(); //prende tutti gli interventi dal db
		
		ArrayList<String> listI = new ArrayList<String>();
		//prende i dati da visualizzare in listInterventi
		for (Tipologiaintervento tipoint : listInterventiDB) {
			listI.add(tipoint.getNome()+"  "+tipoint.getDescrizione()+"  "+tipoint.getLocalizzazione());
		} 
		String[] intArray = (String[]) listI.toArray((new String[0]));
		listInterventi.setItems(intArray);
	}

	/**
	 * This method initializes sShellInserimentoInterventi	
	 *
	 */
	private void createSShellInserimentoInterventi() {
		sShellInserimentoInterventi = new Shell(SWT.APPLICATION_MODAL | SWT.SHELL_TRIM);
		//sShellInserimentoInterventi.setLayout(new GridLayout());
		sShellInserimentoInterventi.setText("Inserimento Nuovo Intervento");
		sShellInserimentoInterventi.setSize(new Point(796, 226));
		createGroupInserimentoInt();
		sShellInserimentoInterventi.open();
	}
	
	/**
	 * Crea il gruppo degli oggetti per inserire nuovi interventi, prima di collegarli al paziente
	 */
	private void createGroupInserimentoInt() {
		groupInserimentoInt = new Group(sShellInserimentoInterventi, SWT.NONE);
		//groupInserimentoInt.setLayout(new GridLayout());
		groupInserimentoInt.setText("Inserimento nuovo interevento");
		groupInserimentoInt.setBounds(new Rectangle(5, 3, 772, 180));
		
		labelNomeInt = new Label(groupInserimentoInt, SWT.NONE);
		labelNomeInt.setBounds(new Rectangle(10, 23, 170, 20));
		labelNomeInt.setText("*Indicare il nome dell'intervento");
		textNomeInt = new Text(groupInserimentoInt, SWT.NONE);
		textNomeInt.setBounds(new Rectangle(209, 23, 360, 18));
		labelDescrInt = new Label(groupInserimentoInt, SWT.NONE);
		labelDescrInt.setBounds(new Rectangle(10, 50, 170, 20));
		labelDescrInt.setText("Inserire una breve descrizione");
		textAreaDescrInt = new Text(groupInserimentoInt, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL);
		textAreaDescrInt.setBounds(new Rectangle(209, 50, 545, 40));
		labelLocalizzazione = new Label(groupInserimentoInt, SWT.NONE);
		labelLocalizzazione.setBounds(new Rectangle(10, 100, 170, 20));
		labelLocalizzazione.setText("*Zona interessata");
		textAreaLocalizzazione = new Text(groupInserimentoInt, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL);
		textAreaLocalizzazione.setBounds(new Rectangle(209, 100, 544, 40));
		buttonInsertNewInt = new Button(groupInserimentoInt, SWT.NONE);
		buttonInsertNewInt.setBounds(new Rectangle(10, 150, 748, 28));
		buttonInsertNewInt.setText("Aggiorna lista interventi");
		buttonInsertNewInt.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				AnamnesiDAO interv = new AnamnesiDAO();
				interv.registraTipoIntervento(textNomeInt.getText(), textAreaDescrInt.getText(), textAreaLocalizzazione.getText());
				textNomeInt.setText("");
				textAreaDescrInt.setText("");
				textAreaLocalizzazione.setText("");
				aggiornaListInterventi();
				//listInterventi.add(textNomeInt.getText()+"  "+textAreaDescrInt.getText()+"  "+textAreaLocalizzazione.getText());
			}
		});
	}
	
	/**
	 * Permette di inserire il numero e la data degli interventi
	 */
	private void createSShellNumData(final Paziente paziente, final Tipologiaintervento tipoIntervento) {
		sShellNumData = new Shell(SWT.APPLICATION_MODAL | SWT.SHELL_TRIM);
		sShellNumData.setLayout(null);
		sShellNumData.setText("Informazioni intervento");
		sShellNumData.setSize(new Point(614, 150));
		//numero
		labelNumInt = new Label(sShellNumData, SWT.NONE);
		labelNumInt.setBounds(new Rectangle(20, 10, 380, 19));
		labelNumInt.setText("* Inserire il numero di volte in cui si è sottoposto allo stesso intervento");
		spinnerNumInt = new Spinner(sShellNumData, SWT.READ_ONLY);
		spinnerNumInt.setBounds(new Rectangle(430, 10, 50, 19));
		spinnerNumInt.setMinimum(1);
		//data
		labelData = new Label(sShellNumData, SWT.NONE);
		labelData.setBounds(new Rectangle(20, 40, 150, 21));
		labelData.setText("Data dell'ultimo intervento");
		// crea combo giorno
		cComboGG = new Combo(sShellNumData, SWT.READ_ONLY);
		cComboGG.setBounds(new Rectangle(190, 40, 48, 23));
		cComboGG.setItems(new String [] {"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31"});
		cComboGG.setText(cComboGG.getItem(0));
		//crea combo mese
		cComboMese = new Combo(sShellNumData, SWT.READ_ONLY);
		cComboMese.setBounds(new Rectangle(250, 40, 48, 25));
		cComboMese.setItems(new String [] {"1","2","3","4","5","6","7","8","9","10","11","12"});
		cComboMese.setText(cComboMese.getItem(0));
		// crea combo anno
		cComboAnno = new Combo(sShellNumData, SWT.READ_ONLY);
		cComboAnno.setBounds(new Rectangle(310, 40, 83, 23));
		Date now = new Date();
		for (int i = 1910; i < (now.getYear()+1901); i++) {
			cComboAnno.add(""+i);
		}
		cComboAnno.setText(cComboAnno.getItem(0));
		buttonOKNumData = new Button(sShellNumData, SWT.NONE);
		buttonOKNumData.setBounds(new Rectangle(410, 76, 94, 28));
		buttonOKNumData.setText("Conferma");
		buttonOKNumData.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				int numInt = 0;
				Date dataInt;
				RegistraIntervento intReg = new RegistraIntervento();
				//numero
				numInt = spinnerNumInt.getSelection();
				//data
				String dateString = cComboAnno.getText()+"-"+cComboMese.getText()+"-"+cComboGG.getText();
				String formato = "yyyy-MM-dd";
				dataInt = Utils.convertStringToDate(dateString, formato);
			
				intReg.setPaziente(paziente);
				intReg.setTipoIntervento(tipoIntervento);
				intReg.setDataIntervento(dataInt);
				intReg.setNumInterventi(numInt);
				listaInterventiRegistrati.add(intReg);
				
				//Visualizzazione degli interventi selezionati
				for (String iSel : listInterventi.getSelection()) {
					if(setInt.add(iSel))
						listIntSel.add(iSel + "    Num: "+numInt+"    Data: "+cComboGG.getText()+"/"+cComboMese.getText()+"/"+cComboAnno.getText());
				}
				listInterventi.deselectAll();
				buttonConfermaInt.setEnabled(true);
				sShellNumData.close();
			}
		});
		sShellNumData.open();
	}
	
	
	//-----------------------------------------ALLERGIE/INTOLLERANZE-------------------------------------------------------------
	/**
	 * Crea il gruppo degli oggetti per inserire allergie/intolleranze collegate al paziente
	 */
	private void createGroupAll(Composite comp) {
		groupAllergie = new Group(comp, SWT.NONE);
		groupAllergie.setText("Inserimento eventuali intolleranze o allergie");
		groupAllergie.setBounds(new Rectangle(20, 63, 1000, 400));
		labelIntAll = new Label(groupAllergie, SWT.NONE);
		labelIntAll.setText("Indicare se si tratta di intolleranza o allergia");
		labelIntAll.setBounds(new Rectangle(20, 40, 250, 20));
		radioButtonInt = new Button(groupAllergie, SWT.RADIO);
		radioButtonInt.setBounds(new Rectangle(280, 40, 85, 20));
		radioButtonInt.setText("Intolleranza");
		radioButtonAll = new Button(groupAllergie, SWT.RADIO);
		radioButtonAll.setBounds(new Rectangle(390, 40, 71, 20));
		radioButtonAll.setText("Allergia");
		
		labelGrado = new Label(groupAllergie, SWT.NONE);
		labelGrado.setBounds(new Rectangle(20, 80, 210, 20));
		labelGrado.setText("Indicare il grado di intolleranza/allergia");
		textGrado = new Text(groupAllergie, SWT.BORDER);
		textGrado.setBounds(new Rectangle(250, 80, 300, 20));
		
		labelSostanza = new Label(groupAllergie, SWT.NONE);
		labelSostanza.setBounds(new Rectangle(20, 110, 61, 20));
		labelSostanza.setText("*Sostanza");
		textSost = new Text(groupAllergie, SWT.BORDER);
		textSost.setBounds(new Rectangle(80, 110, 400, 40));
		
		labelAlPrinc = new Label(groupAllergie, SWT.NONE);
		labelAlPrinc.setBounds(new Rectangle(20, 160, 120, 20));
		labelAlPrinc.setText("*Alimento principale");
		textAlPrinc = new Text(groupAllergie, SWT.BORDER);
		textAlPrinc.setBounds(new Rectangle(140, 160, 400, 40));
		
		labelDerivati = new Label(groupAllergie, SWT.NONE);
		labelDerivati.setBounds(new Rectangle(20, 210, 50, 20));
		labelDerivati.setText("Derivati");
		textAreaDerivati = new Text(groupAllergie, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL );
		textAreaDerivati.setBounds(new Rectangle(80, 210, 400, 60));
		
		labelEffColl = new Label(groupAllergie, SWT.NONE);
		labelEffColl.setBounds(new Rectangle(20, 290, 120, 20));
		labelEffColl.setText("Effetti collaterali");
		textAreaEffColl = new Text(groupAllergie, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL);
		textAreaEffColl.setBounds(new Rectangle(150, 290, 400, 60));
		
		buttonConfermaAll = new Button(groupAllergie, SWT.NONE);
		buttonConfermaAll.setBounds(new Rectangle(600, 370, 80, 25));
		buttonConfermaAll.setText("Conferma");
		
		buttonConfermaAll.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				AnamnesiDAO an = new AnamnesiDAO();
				String flag = "";
				if (radioButtonInt.getSelection())
					flag = "int";
				else flag = "all";
				Paziente nomePaziente = new Paziente();
				
				//Sostituire con il paziente selezionato in HomePazienteView(togliere i commenti)
				nomePaziente = PazienteDAO.getPazienti().get(3);
				
				//HomePazienteView homeP = new HomePazienteView();
				//an.registraAllergie(flag, textSost.getText(), textAlPrinc.getText(), textAreaDerivati.getText(), textGrado.getText(), textAreaEffColl.getText(), homeP.getPazienteSelezionato());
				an.registraAllergie(flag, textSost.getText(), textAlPrinc.getText(), textAreaDerivati.getText(), textGrado.getText(), textAreaEffColl.getText(), nomePaziente);
		}
	});
	}

	

	
//-----------------------------------------ATTIVITA' FISICA-------------------------------------------------------------	
	/**
	 * sShellProva da inserire nel tab	
	 *
	 */
	private void createSShellProva() {
		sShellProva = new Shell();
		//sShellProva.setLayout(new GridLayout());
		sShellProva.setText("Shell prova");
		createGroupAttFisica();
		createGroupVisualizzazioneSport();
		sShellProva.setSize(new Point(810, 592));

		
	}

	/**
	 * Crea tutto il contenuto del tab in groupAttFisica
	 */
	private void createGroupAttFisica() {
		groupAttFisica = new Group(sShellProva, SWT.NONE);
		//groupAttFisica.setLayout(new GridLayout());
		groupAttFisica.setText("Attività Fisica");
		groupAttFisica.setBounds(new Rectangle(14, 2, 772, 309));
		labelAttFisSel = new Label(groupAttFisica, SWT.NONE);
		labelAttFisSel.setBounds(new Rectangle(15, 31, 200, 27));
		labelAttFisSel.setText("Selezionare lo sport seguito");
		textAreaAttFis = new List(groupAttFisica, SWT.WRAP | SWT.V_SCROLL);
		textAreaAttFis.setBounds(new Rectangle(15, 60, 330, 115));
		textAreaAttFis
		.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				buttonVaiAttFis.setEnabled(true);
			}
		});
		buttonVaiAttFis = new Button(groupAttFisica, SWT.NONE);
		buttonVaiAttFis.setBounds(new Rectangle(350, 90, 70, 30));
		buttonVaiAttFis.setEnabled(false);
		buttonVaiAttFis.setText(">>");
		buttonVaiAttFis
				.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
					public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
						for (String sportSel : textAreaAttFis.getSelection()) {
							textAreaAttFisSel.add(sportSel);
						}
						textAreaAttFis.deselectAll();
						createSShellDurFreq();
					}
				});
		textAreaAttFisSel = new List(groupAttFisica, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL);
		textAreaAttFisSel.setBounds(new Rectangle(430, 60, 330, 115));
		textAreaAttFisSel
				.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
					public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
						System.out.println("widgetSelected()"); // TODO Auto-generated Event stub widgetSelected()
					}
				});
		
				
		buttonInsNewAttFis = new Button(groupAttFisica, SWT.NONE);
		buttonInsNewAttFis.setBounds(new Rectangle(15, 179, 98, 21));
		buttonInsNewAttFis.setText("Inserisci nuovo");
		buttonInsNewAttFis
				.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
					public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
						createSShellInsAttFis();
					}
				});
		
		buttonConfermaAttFis = new Button(groupAttFisica, SWT.NONE);
		buttonConfermaAttFis.setBounds(new Rectangle(636, 266, 94, 31));
		buttonConfermaAttFis.setText("Conferma");
		buttonConfermaAttFis
				.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
					public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
						
						
					}
				});
		
		
		//aggiorna la lista con gli sport presenti nel db
		AnamnesiDAO am = new AnamnesiDAO();
		ArrayList<Attivitafisica> elencoSport = new ArrayList<Attivitafisica>();
		ArrayList<String> listAF = new ArrayList<String>();
		elencoSport = am.getSport();
		
		for (Attivitafisica sport : elencoSport) {
			listAF.add(sport.getNome()+"    "+sport.getDescrizione());
		}
		String[] listAFString = (String[]) listAF.toArray((new String[0]));
		textAreaAttFis.setItems(listAFString);
	}

	/**
	 * Crea il contenuto per l'inserimento di un nuovo sport in groupInsAttFis	
	 *
	 */
	private void createGroupInsAttFis() {
		groupInsAttFis = new Group(sShellInsAttFis, SWT.NONE);
		groupInsAttFis.setBounds(new Rectangle(4, 8, 774, 167));
		groupInsAttFis.setText("Inserimento nuovo sport");
		labelNomeAttFis = new Label(groupInsAttFis, SWT.NONE);
		labelNomeAttFis.setBounds(new Rectangle(10, 30, 45, 25));
		labelNomeAttFis.setText("* Nome");
		textNomeAttFis = new Text(groupInsAttFis, SWT.BORDER);
		textNomeAttFis.setBounds(new Rectangle(10, 60, 220, 25));
		labelDescAttFis = new Label(groupInsAttFis, SWT.NONE);
		labelDescAttFis.setBounds(new Rectangle(255, 30, 81, 24));
		labelDescAttFis.setText("Descrizione");
		textAreaDescAttFis = new Text(groupInsAttFis, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL);
		textAreaDescAttFis.setBounds(new Rectangle(256, 60, 483, 60));
		buttonAggiornaListaAttFis = new Button(groupInsAttFis, SWT.NONE);
		buttonAggiornaListaAttFis.setBounds(new Rectangle(4, 135, 764, 27));
		buttonAggiornaListaAttFis.setText("Aggiorna elenco attività fisiche");
		buttonAggiornaListaAttFis.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				textAreaAttFis.add(textNomeAttFis.getText()+"    "+textAreaDescAttFis.getText());
				sShellInsAttFis.close();
			}
		});
	}

	/**
	 * Crea sShellInsAttFis per l'inserimento di un nuovo sport
	 */
	private void createSShellInsAttFis() {
		sShellInsAttFis = new Shell(SWT.APPLICATION_MODAL | SWT.SHELL_TRIM);
		//sShellInsAttFis.setLayout(new GridLayout());
		sShellInsAttFis.setText("Inserimento Nuovo Sport");
		sShellInsAttFis.setSize(new Point(800, 215));
		createGroupInsAttFis();
		sShellInsAttFis.open();
	}

	/**
	 * This method initializes groupVisualizzazioneSport	
	 */
	private void createGroupVisualizzazioneSport() {
		groupVisualizzazioneSport = new Group(sShellProva, SWT.NONE);
		//groupVisualizzazioneSport.setLayout(new GridLayout());
		groupVisualizzazioneSport.setText("Attività fisica del paziente");
		groupVisualizzazioneSport.setBounds(new Rectangle(15, 324, 770, 226));
		
		
		tableSport = new AnamnesiViewTableSport(groupVisualizzazioneSport, SWT.BORDER, pazSelHome);
		tableSport.setLayout(new GridLayout(1, true));
		tableSport.setBackground(Utils.getStandardWhiteColor());
		
		/*form = new HomePazienteForm(parent, SWT.BORDER);
		form.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		form.setLayout(new GridLayout(1, true));
		form.setBackground(Utils.getStandardWhiteColor());*/
	}	
	
	/**
	 * Permette di inserire la durata e la frequenza settimanale degli sport
	 */
	private void createSShellDurFreq() {
		SShellDurFreq = new Shell(SWT.APPLICATION_MODAL | SWT.SHELL_TRIM);
		SShellDurFreq.setLayout(null);
		SShellDurFreq.setText("Informazioni sport");
		SShellDurFreq.setSize(new Point(614, 150));
		//durata
		labelDurataAttFis = new Label(SShellDurFreq, SWT.NONE);
		labelDurataAttFis.setBounds(new Rectangle(10, 9, 100, 20));
		labelDurataAttFis.setText("Inserire la durata");
		textDurataAttFis = new Text(SShellDurFreq, SWT.BORDER);
		textDurataAttFis.setBounds(new Rectangle(120, 9, 324, 20));
		//frequenza
		labelFrequenzaAttFis = new Label(SShellDurFreq, SWT.NONE);
		labelFrequenzaAttFis.setBounds(new Rectangle(10, 50, 200, 20));
		labelFrequenzaAttFis.setText("* Selezionare la frequenza settimanale ");
		spinnerFrequenzaAttFis = new Spinner(SShellDurFreq, SWT.READ_ONLY);
		spinnerFrequenzaAttFis.setBounds(new Rectangle(230, 50, 60, 20));
		spinnerFrequenzaAttFis.setMinimum(1);
		
		buttonOKFreqDur = new Button(SShellDurFreq, SWT.NONE);
		buttonOKFreqDur.setBounds(new Rectangle(400, 76, 94, 28));
		buttonOKFreqDur.setText("Conferma");
		/*labelProvaAttFis = new Label(SShellDurFreq, SWT.NONE);
		labelProvaAttFis.setBounds(new Rectangle(7, 9, 211, 19));
		labelProvaAttFis.setText("Label");*/
		buttonOKFreqDur.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				int freq = 0;
				//Date dataInt;
				//RegistraIntervento intReg = new RegistraIntervento();
				
				//frequenza
				freq = spinnerFrequenzaAttFis.getSelection();
				//durata
				String durata = textDurataAttFis.getText();
				textAreaAttFisSel.add(textAreaAttFis.getSelection()+"   Freq: "+freq+"   Durata: "+durata);
				SShellDurFreq.close();
				
				/*intReg.setPaziente(paziente);
				//intReg.setTipoIntervento(tipoIntervento);
				//intReg.setDataIntervento(dataInt);
				//intReg.setNumInterventi(numInt);
				//listaInterventiRegistrati.add(intReg);
				
				//Visualizzazione degli interventi selezionati
				for (String iSel : listInterventi.getSelection()) {
					if(setInt.add(iSel))
						listIntSel.add(iSel + "    Num: "+numInt+"    Data: "+cComboGG.getText()+"/"+cComboMese.getText()+"/"+cComboAnno.getText());
				}
				listInterventi.deselectAll();
				buttonConfermaInt.setEnabled(true);
				sShellNumData.close();*/
			}
		});
		SShellDurFreq.open();
	}

	/**
	 * This method initializes sShellMessElimina	
	 *
	 */
	private void createSShellMessElimina() {
		sShellMessElimina = new Shell();
		sShellMessElimina.setLayout(new GridLayout());
		sShellMessElimina.setText("Conferma eliminazione");
		sShellMessElimina.setSize(new Point(377, 72));
	}

}

class AnamnesiViewTableSport extends ListComposite {

	public AnamnesiViewTableSport(Composite parent, int style, Paziente pazSelHome) {
		super(parent, style);
		//initialize(parent, pazSelHome);
	}
	
	private Table tableSportPerPaziente = null;
	private static ArrayList<Object> sport;
	
	public void initialize(Composite comp, Paziente pazSelHome) {
		tableSportPerPaziente = new Table(comp, SWT.FILL | SWT.BORDER
				| SWT.FULL_SELECTION | SWT.V_SCROLL | SWT.MULTI);
		tableSportPerPaziente.setHeaderVisible(true);
		tableSportPerPaziente.setLinesVisible(true);
		//tableSportPerPaziente.setBounds(new Rectangle(36, 596, 628, 226));
		tableSportPerPaziente.setLayout(new GridLayout(1, true));
		sport = AnamnesiDAO.getSportPazPerLista(pazSelHome);
		System.out.println("N sport: "+sport.size());
		riempiTabellaEntita(tableSportPerPaziente, sport);
		System.out.println("N colonne: "+tableSportPerPaziente.getColumnCount());
		tableSportPerPaziente.getColumn(0).setWidth(0);
	}
}