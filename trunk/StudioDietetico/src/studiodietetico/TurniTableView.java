package studiodietetico;

import hibernate.Medico;
import hibernate.Prestazione;
import hibernate.PrestazioneId;
import hibernate.Turno;

import java.util.ArrayList;
import java.util.Date;

import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.part.ViewPart;

import command.MedicoDAO;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.graphics.Point;

import service.Utils;

public class TurniTableView extends ViewPart {
	private Composite top = null;
	private TableForm classVis;
	private ArrayList<Object> prestazioni;  //  @jve:decl-index=0:
	private Shell sShellGestioneTurni = null;  //  @jve:decl-index=0:visual-constraint="333,15"

	private Label labelSelezMese = null;
	private Combo comboMesi = null;
	private ArrayList<Medico> medici;  //  @jve:decl-index=0:
	private ArrayList<Turno> turni;  //  @jve:decl-index=0:
	private ArrayList<Prestazione> turniMediciMensili;
	private Table tableTurni = null;
	private Label labelSelezionaMedico = null;
	private Button buttonAggiungiTurnoMedico = null;
	private Table tableTurniOrari = null;
	private Button buttonRimuoviTurnoMedico = null;
	private Label labelOrganizzazioneTurni = null;
	private Button buttonAggiungiTurno = null;
	private Label labelNomeTurno = null;
	private Text textNomeTurno = null;
	private Label labelOraInizioTurno = null;
	private Label labelOraFineTurno = null;
	private Button buttonAggiungiOk = null;
	private Label labelSelezGiornoTurno = null;
	private Spinner spinnerGiornoMese = null;
	private Label labelSelezTurno = null;
	private Combo comboTurni = null;
	private Button buttonConfermaAggiungiTurno = null;
	private Group groupGestisciTurnoMedico = null;
	private Button buttonConfermaRimuoviTurno = null;
	private Button buttonChiudi = null;
	
	
	public TurniTableView() {}

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
		prestazioni = MedicoDAO.getPrestazioniObject();
		MedicoDAO md = new MedicoDAO();
		classVis = new TableForm(top, SWT.BORDER, prestazioni, "createSShellDettagliTurni","createSShellGestioneTurni",TurniTableView.this,md,"TurniTableView");
		classVis.setBounds(new Rectangle(6, 50, 800, 332));
		classVis.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		classVis.setLayout(new GridLayout(1, true));
		classVis.setBackground(Utils.getStandardWhiteColor());

		classVis.nascondiColonne(new int[] {0,1,2});
		
		aggiungiColonne(classVis, prestazioni);
		
		classVis.aggiornaCombo();
		
		classVis.ordinamentoData(classVis.getTableVisualizzazione(), 3);
		classVis.ordinamentoStringhe(classVis.getTableVisualizzazione(), 6);

	}
	
	private void aggiornaTableView(){
		classVis.getTableVisualizzazione().removeAll(); //rimuove le righe
		//rimuove le colonne
		int k = 0;
		while (k<classVis.getTableVisualizzazione().getColumnCount()) {
			classVis.getTableVisualizzazione().getColumn(k).dispose();
		}
		prestazioni = MedicoDAO.getPrestazioniObject();
		classVis.riempiTabella(prestazioni, "TurniTableView");
		classVis.nascondiColonne(new int[] {0,1,2});
		aggiungiColonne(classVis, prestazioni);
		classVis.aggiornaCombo();
		classVis.ordinamentoData(classVis.getTableVisualizzazione(), 3);
		classVis.ordinamentoStringhe(classVis.getTableVisualizzazione(), 6);
	}

	public static void aggiungiColonne(TableForm classVis, ArrayList<Object> prestazioni) {
		TableColumn colonna = new TableColumn(classVis.getTableVisualizzazione(), SWT.CENTER);
		colonna.setText("Data turno");
		String nome = "";
		PrestazioneId prestId = null;
		TableItem itemSel = null;
		for (int j = 0; j < prestazioni.size(); j++) {
			prestId = ((Prestazione)prestazioni.get(j)).getId();
			nome = prestId.getDataTurno().toString();
			itemSel = classVis.getTableVisualizzazione().getItem(j);
			itemSel.setText(classVis.getTableVisualizzazione().getColumnCount()-1, nome);
		} 
		colonna.pack();
		colonna.setResizable(false);
		
		TableColumn colonna2 = new TableColumn(classVis.getTableVisualizzazione(), SWT.CENTER);
		colonna2.setText("Ora inizio turno");
		String nome2 = "";
		TableItem itemSel2 = null;
		for (int j = 0; j < prestazioni.size(); j++) {
			int lunghezzaNome = ((Prestazione)prestazioni.get(j)).getTurno().getOraInizio().toString().length();
			nome2 = ((Prestazione)prestazioni.get(j)).getTurno().getOraInizio().toString();
			itemSel2 = classVis.getTableVisualizzazione().getItem(j);
			itemSel2.setText(classVis.getTableVisualizzazione().getColumnCount()-1, nome2.substring(0, lunghezzaNome-3));
		} 
		colonna2.pack();
		colonna2.setResizable(false);
		
		TableColumn colonna3 = new TableColumn(classVis.getTableVisualizzazione(), SWT.CENTER);
		colonna3.setText("Ora fine turno");
		String nome3 = "";
		TableItem itemSel3 = null;
		for (int j = 0; j < prestazioni.size(); j++) {
			int lunghezzaNome = ((Prestazione)prestazioni.get(j)).getTurno().getOraFine().toString().length();
			nome3 = ((Prestazione)prestazioni.get(j)).getTurno().getOraFine().toString();
			itemSel3 = classVis.getTableVisualizzazione().getItem(j);
			itemSel3.setText(classVis.getTableVisualizzazione().getColumnCount()-1, nome3.substring(0, lunghezzaNome-3));
		} 
		colonna3.pack();
		colonna3.setResizable(false);
		
		TableColumn colonna4 = new TableColumn(classVis.getTableVisualizzazione(), SWT.CENTER);
		colonna4.setText("Medico");
		String nome4 = "";
		TableItem itemSel4 = null;
		for (int j = 0; j < prestazioni.size(); j++) {
			nome4 = ((Prestazione)prestazioni.get(j)).getMedico().getCognome()+" "+((Prestazione)prestazioni.get(j)).getMedico().getNome();
			itemSel4 = classVis.getTableVisualizzazione().getItem(j);
			itemSel4.setText(classVis.getTableVisualizzazione().getColumnCount()-1, nome4);
		} 
		colonna4.pack();
		colonna4.setResizable(false);
	}
	
	@Override
	public void setFocus() {
		classVis.setFocus();
	}

	/**
	 * This method initializes sShellGestioneTurni	
	 *
	 */
	public void createSShellGestioneTurni() {
		sShellGestioneTurni = new Shell(SWT.APPLICATION_MODAL | SWT.SHELL_TRIM);
		//sShellGestioneTurni.setLayout(new GridLayout());
		sShellGestioneTurni.setSize(new Point(627, 554));
		sShellGestioneTurni.setText("Gestione turni");
		
		final DateTime timeInizioTurno = new DateTime(sShellGestioneTurni, SWT.TIME | SWT.SHORT);
        final DateTime timeFineTurno = new DateTime(sShellGestioneTurni, SWT.TIME | SWT.SHORT);
        labelSelezMese = new Label(sShellGestioneTurni, SWT.NONE);
        labelSelezMese.setBounds(new Rectangle(10, 8, 107, 22));
        labelSelezMese.setText("Seleziona il mese:");
        createComboMesi();
        tableTurni = new Table(sShellGestioneTurni, SWT.MULTI | SWT.BORDER | SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.SELECTED | SWT.Selection | SWT.VIRTUAL | SWT.FULL_SELECTION);
        tableTurni.setHeaderVisible(true);
        tableTurni.setLinesVisible(true);
        tableTurni.setBounds(new Rectangle(10, 65, 590, 183));
        tableTurni.setEnabled(false);
        labelSelezionaMedico = new Label(sShellGestioneTurni, SWT.NONE);
        labelSelezionaMedico.setBounds(new Rectangle(10, 37, 507, 22));
        labelSelezionaMedico.setText("Selezionare il medico di cui si vogliono modificare i turni");
        buttonAggiungiTurnoMedico = new Button(sShellGestioneTurni, SWT.NONE);
        buttonAggiungiTurnoMedico.setBounds(new Rectangle(10, 250, 155, 30));
        buttonAggiungiTurnoMedico.setText("Aggiungi turno del medico");
        buttonAggiungiTurnoMedico.setEnabled(false);
        tableTurniOrari = new Table(sShellGestioneTurni, SWT.MULTI | SWT.BORDER | SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.VIRTUAL | SWT.READ_ONLY);
        tableTurniOrari.setHeaderVisible(true);
        tableTurniOrari.setLinesVisible(true);
        tableTurniOrari.setBounds(new Rectangle(10, 350, 308, 139));
        buttonRimuoviTurnoMedico = new Button(sShellGestioneTurni, SWT.NONE);
        buttonRimuoviTurnoMedico.setBounds(new Rectangle(10, 280, 155, 30));
        buttonRimuoviTurnoMedico.setText("Rimuovi turno del medico");
        buttonRimuoviTurnoMedico.setEnabled(false);
        buttonRimuoviTurnoMedico
        		.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
        			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
        				labelSelezGiornoTurno.setVisible(true);
        				spinnerGiornoMese.setVisible(true);
        				buttonConfermaRimuoviTurno.setVisible(true);
        				labelSelezTurno.setVisible(false);
        				comboTurni.setVisible(false);
        				buttonConfermaAggiungiTurno.setVisible(false);
        				groupGestisciTurnoMedico.setVisible(true);
        			}
        		});
        labelOrganizzazioneTurni = new Label(sShellGestioneTurni, SWT.NONE);
        labelOrganizzazioneTurni.setBounds(new Rectangle(12, 330, 281, 18));
        labelOrganizzazioneTurni.setText("Organizzazione dei turni");
        buttonAggiungiTurno = new Button(sShellGestioneTurni, SWT.NONE);
        buttonAggiungiTurno.setBounds(new Rectangle(330, 334, 180, 25));
        buttonAggiungiTurno.setText("Aggiungi un nuovo tipo di turno");
        buttonAggiungiTurno
        		.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
        			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
        				labelNomeTurno.setVisible(true);
        				textNomeTurno.setVisible(true);
        				labelOraInizioTurno.setVisible(true);
        				labelOraFineTurno.setVisible(true);
        				timeInizioTurno.setVisible(true);
        				timeFineTurno.setVisible(true);
        				buttonAggiungiOk.setVisible(true);
        			}
        		});
        labelNomeTurno = new Label(sShellGestioneTurni, SWT.NONE);
        labelNomeTurno.setBounds(new Rectangle(330, 365, 95, 22));
        labelNomeTurno.setText("Sigla del turno: ");
        labelNomeTurno.setVisible(false);
        textNomeTurno = new Text(sShellGestioneTurni, SWT.BORDER);
        textNomeTurno.setBounds(new Rectangle(434, 365, 151, 22));
        textNomeTurno.setVisible(false);
        textNomeTurno.setTextLimit(50);
        labelOraInizioTurno = new Label(sShellGestioneTurni, SWT.NONE);
        labelOraInizioTurno.setBounds(new Rectangle(330, 395, 95, 22));
        labelOraInizioTurno.setText("Ora inizio turno:");
        labelOraInizioTurno.setVisible(false);
        //final DateTime timeInizioTurno = new DateTime(sShellGestioneTurni, SWT.TIME | SWT.SHORT);
        timeInizioTurno.setBounds(434, 395, 70, 20);
        timeInizioTurno.setVisible(false);
        labelOraFineTurno = new Label(sShellGestioneTurni, SWT.NONE);
        labelOraFineTurno.setBounds(new Rectangle(330, 425, 95, 22));
        labelOraFineTurno.setText("Ora fine turno:");
        labelOraFineTurno.setVisible(false);
        //final DateTime timeFineTurno = new DateTime(sShellGestioneTurni, SWT.TIME | SWT.SHORT);
        buttonAggiungiOk = new Button(sShellGestioneTurni, SWT.NONE);
        buttonAggiungiOk.setBounds(new Rectangle(330, 454, 95, 22));
        buttonAggiungiOk.setText("Ok");
        buttonAggiungiOk.setVisible(false);
        labelSelezGiornoTurno = new Label(sShellGestioneTurni, SWT.WRAP);
        labelSelezGiornoTurno.setBounds(new Rectangle(174, 252, 113, 32));
        labelSelezGiornoTurno.setText("Selezionare il giorno del mese: ");
        labelSelezGiornoTurno.setVisible(false);
        spinnerGiornoMese = new Spinner(sShellGestioneTurni, SWT.READ_ONLY);
        spinnerGiornoMese.setBounds(new Rectangle(295, 256, 35, 21));
        spinnerGiornoMese.setMinimum(1);
        spinnerGiornoMese.setMaximum(31);
        spinnerGiornoMese.setVisible(false);
        labelSelezTurno = new Label(sShellGestioneTurni, SWT.WRAP);
        labelSelezTurno.setBounds(new Rectangle(174, 285, 113, 32));
        labelSelezTurno.setText("Selezionare il tipo di turno:");
        labelSelezTurno.setVisible(false);
        createComboTurni();
        buttonConfermaAggiungiTurno = new Button(sShellGestioneTurni, SWT.NONE);
        buttonConfermaAggiungiTurno.setBounds(new Rectangle(477, 288, 108, 25));
        buttonConfermaAggiungiTurno.setText("Aggiungi turno");
        buttonConfermaAggiungiTurno.setVisible(false);
        buttonConfermaRimuoviTurno = new Button(sShellGestioneTurni, SWT.NONE);
        buttonConfermaRimuoviTurno.setBounds(new Rectangle(350, 256, 108, 25));
        buttonConfermaRimuoviTurno.setText("Rimuovi turno");
        buttonConfermaRimuoviTurno.setVisible(false);
        buttonConfermaRimuoviTurno
        		.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
        			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
        				MedicoDAO med = new MedicoDAO();
        				Medico medicoSel = (Medico) medici.get(tableTurni.getSelectionIndex());
        				Date dataTurnoSel = new Date();
        				dataTurnoSel.setMonth(comboMesi.getSelectionIndex());
        				dataTurnoSel.setDate(Integer.parseInt(spinnerGiornoMese.getText()));
        				System.out.println(dataTurnoSel);
        				med.RimuoviTurnoMedico(medicoSel, dataTurnoSel);
        				TableItem itemSelezionato = tableTurni.getItem(tableTurni.getSelectionIndex());
        				itemSelezionato.setText(Integer.parseInt(spinnerGiornoMese.getText()), "");
        				tableTurni.getColumn(Integer.parseInt(spinnerGiornoMese.getText())).pack();
        				labelSelezGiornoTurno.setVisible(false);
        				spinnerGiornoMese.setVisible(false);
        				//labelSelezTurno.setVisible(false);
        				//comboTurni.setVisible(false);
        				buttonConfermaRimuoviTurno.setVisible(false);
        				groupGestisciTurnoMedico.setVisible(false);
        				System.out.println("Turno del medico rimosso"); 
        				aggiornaTableView();
        			}
        		});
        buttonConfermaAggiungiTurno
        		.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
        			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
        				MedicoDAO med = new MedicoDAO();
        				Medico medicoSel = (Medico) medici.get(tableTurni.getSelectionIndex());
        				Turno turnoSel = (Turno) turni.get(comboTurni.getSelectionIndex());
        				Date dataTurnoSel = new Date();
        				dataTurnoSel.setMonth(comboMesi.getSelectionIndex());
        				dataTurnoSel.setDate(Integer.parseInt(spinnerGiornoMese.getText()));
        				System.out.println(dataTurnoSel);
        				med.RegistraTurnoMedico(medicoSel, turnoSel, dataTurnoSel);
        				TableItem itemSelezionato = tableTurni.getItem(tableTurni.getSelectionIndex());
        				itemSelezionato.setText(Integer.parseInt(spinnerGiornoMese.getText()), itemSelezionato.getText(Integer.parseInt(spinnerGiornoMese.getText()))+"-"+comboTurni.getText()+" ");
        				tableTurni.getColumn(Integer.parseInt(spinnerGiornoMese.getText())).pack();
        				labelSelezGiornoTurno.setVisible(false);
        				spinnerGiornoMese.setVisible(false);
        				labelSelezTurno.setVisible(false);
        				comboTurni.setVisible(false);
        				buttonConfermaAggiungiTurno.setVisible(false);
        				groupGestisciTurnoMedico.setVisible(false);
        				System.out.println("Turno del medico registrato"); 
        				aggiornaTableView();
        			}
        		});
        createGroupGestisciTurnoMedico();
        buttonChiudi = new Button(sShellGestioneTurni, SWT.NONE);
        buttonChiudi.setBounds(new Rectangle(450, 489, 156, 22));
        buttonChiudi.setText("Chiudi gestione turni");
        buttonChiudi
        		.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
        			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
        				sShellGestioneTurni.close();
        			}
        		});
        
        
        buttonAggiungiOk
        		.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
        			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
        				MedicoDAO med = new MedicoDAO();
        				String formato = "HH:mm:ss";
        				String oraInizioString =  timeInizioTurno.getHours()+":"+(timeInizioTurno.getMinutes () < 10 ? "0" : "") + timeInizioTurno.getMinutes ()+":00";
        				String oraFineString =  timeFineTurno.getHours()+":"+(timeFineTurno.getMinutes () < 10 ? "0" : "") + timeFineTurno.getMinutes ()+":00";
        				System.out.println(oraInizioString+"____"+oraFineString);
        				Date oraInizioTurno = Utils.convertStringToDate(oraInizioString, formato); 
        				Date oraFineTurno = Utils.convertStringToDate(oraFineString, formato);
        				med.RegistraTurno(textNomeTurno.getText(), oraInizioTurno, oraFineTurno);
        				labelNomeTurno.setVisible(false);
        				textNomeTurno.setVisible(false);
        				labelOraInizioTurno.setVisible(false);
        				labelOraFineTurno.setVisible(false);
        				timeInizioTurno.setVisible(false);
        				timeFineTurno.setVisible(false);
        				buttonAggiungiOk.setVisible(false);
        				
        				turni = (ArrayList<Turno>) MedicoDAO.getTurni();
        				TableItem item = new TableItem (tableTurniOrari, SWT.NONE);
        				item.setText (0, textNomeTurno.getText());
        				String oraInizioStringRidotta = timeInizioTurno.getHours()+":"+(timeInizioTurno.getMinutes () < 10 ? "0" : "") + timeInizioTurno.getMinutes ();
        				String oraFineStringRidotta = timeFineTurno.getHours()+":"+(timeFineTurno.getMinutes () < 10 ? "0" : "") + timeFineTurno.getMinutes ();
        				item.setText(1, oraInizioStringRidotta);
        				item.setText(2, oraFineStringRidotta);
        				textNomeTurno.setText("");
        				
        				System.out.println("turno inserito"); 
        			}
        		});
        timeFineTurno.setBounds(434, 425, 70, 20);
        timeFineTurno.setVisible(false);
        buttonAggiungiTurnoMedico
        		.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
        			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
        				labelSelezGiornoTurno.setVisible(true);
        				spinnerGiornoMese.setVisible(true);
        				labelSelezTurno.setVisible(true);
        				comboTurni.setVisible(true);
        				buttonConfermaAggiungiTurno.setVisible(true);
        				buttonConfermaRimuoviTurno.setVisible(false);
        				groupGestisciTurnoMedico.setVisible(true);
        			}
        		});
        GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
    	data.heightHint = 200;
    	tableTurni.setLayoutData(data);
    	tableTurni.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
    		public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
    			buttonAggiungiTurnoMedico.setEnabled(true);
    			buttonRimuoviTurnoMedico.setEnabled(true); 
    		}
    	});
    	String[] giorniMese = {"Medici \\ Giorni", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"};
    	for (int i=0; i<giorniMese.length; i++) {
    		TableColumn column = new TableColumn (tableTurni, SWT.NONE);
    		column.setText (giorniMese [i]);
    		column.pack();
    	}
    	
    	medici = (ArrayList<Medico>) MedicoDAO.getMedici();
		ArrayList<String> med = new ArrayList<String>();
		for (Medico medico : medici) {
			med.add(medico.getCognome()+" "+medico.getNome());
		}
		String[] mediciArray = (String[]) med.toArray((new String[0]));
		for (int i=0; i<mediciArray.length; i++) {
			TableItem item = new TableItem (tableTurni, SWT.NONE);
			item.setText (0, mediciArray[i]);
			for (int l=1; l<giorniMese.length; l++){
				item.setText(l, " ");
			}	
    	}

		for (int i=0; i<giorniMese.length; i++) {
    		tableTurni.getColumn (i).pack ();
    	}
		
		
    	tableTurniOrari.setLayoutData(data);
    	String[] attributiTurni = {"Sigla turno", "Ora inizio turno", "Ora fine turno"};
    	for (int i=0; i<attributiTurni.length; i++) {
    		TableColumn column = new TableColumn (tableTurniOrari, SWT.NONE);
    		column.setText (attributiTurni [i]);
    		column.pack();
    	}
    	
    	turni = (ArrayList<Turno>) MedicoDAO.getTurni();
		/*ArrayList<String> tur = new ArrayList<String>();
		for (Turno turno : turni) {
			tur.add(turno.getNome());
		}
		String[] turniArray = (String[]) tur.toArray((new String[0]));
		*/for (int i=0; i<turni.size(); i++) {
			TableItem item = new TableItem (tableTurniOrari, SWT.NONE);
			item.setText (0, turni.get(i).getNome());
			item.setText(1, turni.get(i).getOraInizio().getHours()+":"+(turni.get(i).getOraInizio().getMinutes()< 10 ? "0" : "")+turni.get(i).getOraInizio().getMinutes());
			item.setText(2, turni.get(i).getOraFine().getHours()+":"+(turni.get(i).getOraFine().getMinutes()< 10 ? "0" : "")+turni.get(i).getOraFine().getMinutes());
    	}
    	
		for (int i=0; i<attributiTurni.length; i++) {
    		tableTurniOrari.getColumn (i).pack ();
    	}
    			
		sShellGestioneTurni.open();
	}


	private void createComboMesi() {
		comboMesi = new Combo(sShellGestioneTurni, SWT.READ_ONLY);
		comboMesi.setBounds(new Rectangle(128, 7, 210, 22));
		Date now = new Date();
		comboMesi.setItems(new String [] {"Gennaio "+(now.getYear()+1900),"Febbraio "+(now.getYear()+1900),"Marzo "+(now.getYear()+1900),"Aprile "+(now.getYear()+1900),"Maggio "+(now.getYear()+1900),"Giugno "+(now.getYear()+1900),"Luglio "+(now.getYear()+1900),"Agosto "+(now.getYear()+1900),"Settembre "+(now.getYear()+1900),"Ottobre "+(now.getYear()+1900),"Novembre "+(now.getYear()+1900),"Dicembre "+(now.getYear()+1900)});
		comboMesi.addSelectionListener(new org.eclipse.swt.events.SelectionListener() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				tableTurni.setEnabled(true);
				medici = MedicoDAO.getMedici();
				for (int rig = 0; rig < medici.size(); rig++) {
					for (int col = 1; col < tableTurni.getColumnCount(); col++) {
						TableItem item = tableTurni.getItem(rig);
						item.setText(col, "");
						tableTurni.getColumn(col).pack();
					}
				}
				
				int mese = (comboMesi.getSelectionIndex())+1;
				if (mese==4 |  mese==6 | mese==9 | mese==11 ) {
					tableTurni.getColumn(31).setText("");
					spinnerGiornoMese.setMaximum(30);
				} else if (mese==2) {
					tableTurni.getColumn(31).setText("");
					tableTurni.getColumn(30).setText("");
					tableTurni.getColumn(29).setText("");
					spinnerGiornoMese.setMaximum(28);
				} else {
					tableTurni.getColumn(31).setText("31");
					tableTurni.getColumn(30).setText("30");
					tableTurni.getColumn(29).setText("29");
					spinnerGiornoMese.setMaximum(31);
				}
				turniMediciMensili = MedicoDAO.getTurniMediciByMese(mese);
				/*per ogni giorno del mese 
					per ogni elemento di tipo Prestazione 
						se il giorno della prestazione è lo stesso del giorno in considerazione
						aggiunge il nome del turno all'item del medico corrispondente*/	
				for (int i = 1; i < 32; i++) {
					System.out.println("ciclo sul giorno "+i);
					for (Prestazione prest : turniMediciMensili) {
						if (prest.getId().getDataTurno().getDate()==i) {
							System.out.println("trovata prestazione turno al giorno "+i);
							//medici = MedicoDAO.getMedici();
							for (int m=0; m < medici.size(); m++) {
								if (medici.get(m)==prest.getMedico()) {
									System.out.println("trovato medico "+m);
									TableItem itemSelezionato = tableTurni.getItem(m);
									itemSelezionato.setText(i, itemSelezionato.getText(i)+"-"+prest.getTurno().getNome()+" ");
			        				tableTurni.getColumn(i).pack();
								}
							}
						}
					}
				}
				System.out.println("numero di turni per questo mese: "+turniMediciMensili.size());
				System.out.println("mese selezionato: "+mese); 
			}
			public void widgetDefaultSelected(org.eclipse.swt.events.SelectionEvent e) {
			}
		});

	}


	private void createComboTurni() {
		comboTurni = new Combo(sShellGestioneTurni, SWT.READ_ONLY);
		comboTurni.setBounds(new Rectangle(295, 289, 169, 24));
		comboTurni.setVisible(false);
		turni = (ArrayList<Turno>) MedicoDAO.getTurni();
		ArrayList<String> tur = new ArrayList<String>();
		for (Turno turno : turni) {
			tur.add(turno.getNome());
		}
		String[] turniArray = (String[]) tur.toArray((new String[0]));
		comboTurni.setItems(turniArray);
	}


	private void createGroupGestisciTurnoMedico() {
		groupGestisciTurnoMedico = new Group(sShellGestioneTurni, SWT.NONE);
		groupGestisciTurnoMedico.setLayout(new GridLayout());
		groupGestisciTurnoMedico.setBounds(new Rectangle(170, 250, 420, 72));
		groupGestisciTurnoMedico.setVisible(false);
	}
	
	public void createSShellDettagliTurni(final TableItem rigaTableClick) {
		createSShellGestioneTurni();
	}
}
