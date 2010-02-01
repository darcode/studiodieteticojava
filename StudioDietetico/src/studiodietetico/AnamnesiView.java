package studiodietetico;

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
import org.eclipse.swt.widgets.TabItem;
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
import org.eclipse.swt.layout.GridLayout;

import service.RegistraIntervento;
import service.Utils;
import studiodietetico.HomePazienteView;

public class AnamnesiView extends ViewPart {

	private Label labelNome = null;
	private Text textNomeInt = null;
	private Label labelDescrInt = null;
	private Text textAreaDescrInt = null;
	private Label labelLocalizzazione = null;
	private Text textAreaLocalizzazione = null;
	private Group groupIns = null;
	private Button buttonConferma = null;
	private Label labelPaziente = null;  //  @jve:decl-index=0:visual-constraint="9,-39"
	private Text textPaziente = null;  //  @jve:decl-index=0:visual-constraint="630,11"
	private Label labelSceltaInt = null;
	private List listInterventi = null;  //  @jve:decl-index=0:visual-constraint="735,0"
	private Button buttonIns = null;
	private List listOk = null;
	private Button buttonOk = null;
	private Label labelNumInt = null;
	private Spinner spinnerNumInt = null;
	private Label labelData = null;
	private Combo cComboGG = null;
	private Combo cComboMese = null;
	private Combo cComboAnno = null;
	private Button buttonInsInt = null;
	private Group groupInt = null;
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
	private Button buttonOkInt = null;
	private Text textGrado = null;
	private CTabFolder cTabFolderAnamnesi = null;
	private Set<String> setInt = new HashSet<String>();  //  @jve:decl-index=0:
	private ArrayList<Tipologiaintervento> listInterventiDB;  //  @jve:decl-index=0:
	private ArrayList<Tipologiaintervento> assNewIntervSel = new ArrayList<Tipologiaintervento>();  //  @jve:decl-index=0:
	Tipologiaintervento interventoSelez;  //  @jve:decl-index=0:
	private Shell sShellNumData = null;  //  @jve:decl-index=0:visual-constraint="7,437"
	private Button buttonOK = null;
	private ArrayList<RegistraIntervento> listaInterventiRegistrati = new ArrayList<RegistraIntervento>();  //  @jve:decl-index=0:
	
	public static final String VIEW_ID = "StudioDietetico.anamnesi"; 
	public AnamnesiView() {}

	@Override
	public void createPartControl(Composite parent) {
        //createCTabFolderAnamnesi();

		cTabFolderAnamnesi = new CTabFolder(parent, SWT.TOP);
		cTabFolderAnamnesi.setBounds(new Rectangle(3, 2, 609, 366));
		
		CTabItem itemInt = new CTabItem(cTabFolderAnamnesi, SWT.NONE);
	    itemInt.setText("Interventi");
	    {
	    	Composite comp1 = new Composite(cTabFolderAnamnesi, SWT.TRANSPARENT);
	    	itemInt.setControl(comp1);
	    	createTabInterventi(comp1);
	    } // fine tab interventi
	    
	    CTabItem itemAll = new CTabItem(cTabFolderAnamnesi, SWT.NONE);
	    itemAll.setText("Allergie/intolleranze");
	    {
	    	Composite comp2 = new Composite(cTabFolderAnamnesi, SWT.TRANSPARENT);
			itemAll.setControl(comp2);
			createGroupAll(comp2);
			
	    } // fine tab allergie
	    
	    
        
		//Visualizzazione del paziente
		labelPaziente = new Label(cTabFolderAnamnesi, SWT.NONE);
		labelPaziente.setBounds(new Rectangle(14, 30, 82, 24));
		labelPaziente.setText("Paziente");
		textPaziente = new Text(cTabFolderAnamnesi, SWT.BORDER);
		textPaziente.setBounds(new Rectangle(106, 30, 263, 31));
		textPaziente.setEnabled(false);
		
	}
			
			
	public void createTabInterventi(Composite comp1) {
		interventoSelez = new Tipologiaintervento();
		labelSceltaInt = new Label(comp1, SWT.NONE);
		labelSceltaInt.setBounds(new Rectangle(13, 63, 397, 21));
		labelSceltaInt.setText("Selezionare un intervento dalla lista o inserire uno nuovo se non presente");
		listInterventi = new List(comp1, SWT.READ_ONLY | SWT.V_SCROLL | SWT.WRAP);
		listInterventi.setBounds(new Rectangle(13, 91, 231, 71));
		buttonIns = new Button(comp1, SWT.NONE);
		buttonIns.setBounds(new Rectangle(274, 113, 78, 26));
		buttonIns.setText(">>");
		buttonIns.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				
				interventoSelez = listInterventiDB.get(listInterventi.getSelectionIndex());
				assNewIntervSel.add(interventoSelez);

				for (String iSel : listInterventi.getSelection()) {
					if(setInt.add(iSel))
						listOk.add(iSel);
				}
				
				//paziente
				PazienteDAO paz = new PazienteDAO();
				Paziente paziente = new Paziente();
				HomePazienteView homeP = new HomePazienteView();
				//paziente = homeP.getPazienteSelezionato();
				paziente = PazienteDAO.getPazienti().get(3);
				
				createSShellNumData(paziente,interventoSelez);
				
				listInterventi.deselectAll();
			}
		});
		listOk = new List(comp1, SWT.READ_ONLY | SWT.MULTI | SWT.V_SCROLL | SWT.WRAP);
		listOk.setBounds(new Rectangle(367, 91, 231, 71));
		
		
		
		buttonInsInt = new Button(comp1, SWT.NONE);
		buttonInsInt.setBounds(new Rectangle(429, 60, 171, 22));
		buttonInsInt.setText("Inserimento nuovo intervento");
		buttonInsInt
			.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				groupIns.setVisible(true);
			}
		});
		
		buttonOk = new Button(comp1, SWT.NONE);
		buttonOk.setBounds(new Rectangle(424, 183, 120, 28));
		buttonOk.setText("Conferma");
		buttonOk
			.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				AnamnesiDAO interv = new AnamnesiDAO();				
				System.out.println("Size listaReg: "+listaInterventiRegistrati.size());
				for (int j = 0; j < listaInterventiRegistrati.size(); j++) {
					interv.registraIntervento(listaInterventiRegistrati.get(j).getPaziente(),listaInterventiRegistrati.get(j).getTipoIntervento(),
							listaInterventiRegistrati.get(j).getDataIntervento(), listaInterventiRegistrati.get(j).getNumInterventi());
				//System.out.println("listaReg.paz: " + listaInterventiRegistrati.get(j).getPaziente().getCodiceFiscale());
				}
				
			}
		});
		
		createGroupInsInt(comp1);
		aggiornaListInterventi();
	}
	
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

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub
	}

	/**
	 * This method initializes groupIns	
	 *
	 */
	private void createGroupInsInt(Composite comp) {
		groupIns = new Group(comp, SWT.NONE);
		groupIns.setText("Inserimento nuovo intervento");
		groupIns.setBounds(new Rectangle(19, 231, 581, 199));
		
		labelNome = new Label(groupIns, SWT.NONE);
		labelNome.setBounds(new Rectangle(10, 23, 170, 20));
		labelNome.setText("*Indicare il nome dell'intervento");
		textNomeInt = new Text(groupIns, SWT.NONE);
		textNomeInt.setBounds(new Rectangle(209, 23, 200, 18));
		labelDescrInt = new Label(groupIns, SWT.NONE);
		labelDescrInt.setBounds(new Rectangle(10, 50, 170, 20));
		labelDescrInt.setText("Inserire una breve descrizione");
		textAreaDescrInt = new Text(groupIns, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL);
		textAreaDescrInt.setBounds(new Rectangle(209, 50, 360, 40));
		labelLocalizzazione = new Label(groupIns, SWT.NONE);
		labelLocalizzazione.setBounds(new Rectangle(10, 100, 170, 20));
		labelLocalizzazione.setText("*Zona interessata");
		textAreaLocalizzazione = new Text(groupIns, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL);
		textAreaLocalizzazione.setBounds(new Rectangle(209, 100, 360, 40));
		buttonConferma = new Button(groupIns, SWT.NONE);
		buttonConferma.setBounds(new Rectangle(32, 150, 503, 28));
		buttonConferma.setText("Aggiorna lista interventi");
		buttonConferma.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
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
		
		groupIns.setVisible(false);
	}

	/**
	 * This method initializes groupInt	
	 *
	 */
	private void createGroupAll(Composite comp) {
		groupInt = new Group(comp, SWT.NONE);
		groupInt.setText("Indicare eventuali intolleranze o allergie");
		groupInt.setBounds(new Rectangle(20, 63, 490, 162));
		radioButtonInt = new Button(groupInt, SWT.RADIO);
		radioButtonInt.setBounds(new Rectangle(10, 21, 85, 20));
		radioButtonInt.setText("Intolleranza");
		radioButtonAll = new Button(groupInt, SWT.RADIO);
		radioButtonAll.setBounds(new Rectangle(117, 21, 71, 20));
		radioButtonAll.setText("Allergia");
		labelSostanza = new Label(groupInt, SWT.NONE);
		labelSostanza.setBounds(new Rectangle(10, 50, 61, 20));
		labelSostanza.setText("Sostanza");
		textSost = new Text(groupInt, SWT.BORDER);
		textSost.setBounds(new Rectangle(75, 50, 120, 20));
		labelAlPrinc = new Label(groupInt, SWT.NONE);
		labelAlPrinc.setBounds(new Rectangle(205, 50, 110, 20));
		labelAlPrinc.setText("Alimento principale");
		textAlPrinc = new Text(groupInt, SWT.BORDER);
		textAlPrinc.setBounds(new Rectangle(325, 50, 160, 20));
		labelDerivati = new Label(groupInt, SWT.NONE);
		labelDerivati.setBounds(new Rectangle(10, 77, 50, 20));
		labelDerivati.setText("Derivati");
		textAreaDerivati = new Text(groupInt, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL );
		textAreaDerivati.setBounds(new Rectangle(70, 78, 160, 44));
		labelGrado = new Label(groupInt, SWT.NONE);
		labelGrado.setBounds(new Rectangle(206, 21, 114, 20));
		labelGrado.setText("Grado di intolleranza");
		labelEffColl = new Label(groupInt, SWT.NONE);
		labelEffColl.setBounds(new Rectangle(239, 78, 87, 20));
		labelEffColl.setText("Effetti collaterali");
		textAreaEffColl = new Text(groupInt, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL);
		textAreaEffColl.setBounds(new Rectangle(334, 81, 150, 49));
		buttonOkInt = new Button(groupInt, SWT.NONE);
		buttonOkInt.setBounds(new Rectangle(405, 135, 79, 22));
		buttonOkInt.setText("Conferma");
		textGrado = new Text(groupInt, SWT.BORDER);
		textGrado.setBounds(new Rectangle(332, 21, 152, 20));
		buttonOkInt.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
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

	/**
	 * This method initializes sShellNumData	
	 *
	 */
	private void createSShellNumData(final Paziente paziente, final Tipologiaintervento tipoIntervento) {
		sShellNumData = new Shell();
		sShellNumData.setLayout(null);
		sShellNumData.setText("Informazioni intervento");
		sShellNumData.setSize(new Point(614, 150));
		//numero
		labelNumInt = new Label(sShellNumData, SWT.NONE);
		labelNumInt.setBounds(new Rectangle(20, 10, 380, 19));
		labelNumInt.setText("Inserire il numero di volte in cui si è sottoposto allo stesso intervento");
		spinnerNumInt = new Spinner(sShellNumData, SWT.NONE);
		spinnerNumInt.setBounds(new Rectangle(430, 10, 50, 19));
		//data
		labelData = new Label(sShellNumData, SWT.NONE);
		labelData.setBounds(new Rectangle(20, 40, 91, 21));
		labelData.setText("Data dell'ultimo intervento");
		// crea combo giorno
		cComboGG = new Combo(sShellNumData, SWT.READ_ONLY);
		cComboGG.setBounds(new Rectangle(123, 40, 48, 23));
		cComboGG.setItems(new String [] {"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31"});
		cComboGG.setText(cComboGG.getItem(0));
		//crea combo mese
		cComboMese = new Combo(sShellNumData, SWT.READ_ONLY);
		cComboMese.setBounds(new Rectangle(189, 40, 48, 25));
		cComboMese.setItems(new String [] {"1","2","3","4","5","6","7","8","9","10","11","12"});
		cComboMese.setText(cComboMese.getItem(0));
		// crea combo anno
		cComboAnno = new Combo(sShellNumData, SWT.READ_ONLY);
		cComboAnno.setBounds(new Rectangle(254, 40, 83, 23));
		Date now = new Date();
		for (int i = 1910; i < (now.getYear()+1901); i++) {
			cComboAnno.add(""+i);
		}
		cComboAnno.setText(cComboAnno.getItem(0));
		buttonOK = new Button(sShellNumData, SWT.NONE);
		buttonOK.setBounds(new Rectangle(410, 76, 94, 28));
		buttonOK.setText("Conferma");
		buttonOK.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				int numInt = 0, num = 0;
				Date dataInt;
				numInt = spinnerNumInt.getSelection();
				RegistraIntervento intReg = new RegistraIntervento();
				//data
				String dateString = cComboAnno.getText()+"-"+cComboMese.getText()+"-"+cComboGG.getText();
				String formato = "yyyy-MM-dd";
				dataInt = Utils.convertStringToDate(dateString, formato);
				
				//for (RegistraIntervento iReg : listaInterventiRegistrati) {
				//	System.out.println("Paz: "+iReg.getPaziente().equals(paziente));
				//	System.out.println("TipoInt: "+iReg.getTipoIntervento().equals(tipoIntervento));
				//	if(iReg.getPaziente().equals(paziente)) {
				//		if (iReg.getTipoIntervento().equals(tipoIntervento)) {
							//numInt = numInt + iReg.getNumInterventi();
							//iReg.setNumInterventi(numInt);
							//listaInterventiRegistrati.add(iReg);
					//	}
				//	} else {
						//System.out.println("Entrato nell'else");
						
						intReg.setPaziente(paziente);
						intReg.setTipoIntervento(tipoIntervento);
						intReg.setDataIntervento(dataInt);
						intReg.setNumInterventi(numInt);
						listaInterventiRegistrati.add(intReg);
					//}
				//}
				
				sShellNumData.close();
			}
		});
		sShellNumData.open();
	}

}