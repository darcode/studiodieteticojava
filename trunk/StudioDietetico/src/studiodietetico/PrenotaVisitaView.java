package studiodietetico;

import hibernate.Medico;
import hibernate.Paziente;
import hibernate.Tipologiavisita;

import java.util.ArrayList;
import java.util.Date;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.List;
import org.eclipse.jface.viewers.ListViewer;

import command.MedicoDAO;
import command.PazienteDAO;
import command.VisitaDAO;

import org.eclipse.swt.widgets.Combo;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;

import service.Utils;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.graphics.Point;

public class PrenotaVisitaView extends ViewPart {

	private Composite top = null;
	private Label labelPazVis = null;
	private List listPazienti = null;
	private ListViewer listViewer = null;
	private Label labelTipolVisitPrenot = null;
	private Combo comboTipologVisita = null;
	private ComboViewer comboViewer = null;
	private Label labelDataPrenotVisita = null;
	private Combo comboGiornoVisita = null;
	private ComboViewer comboViewer1 = null;
	private Combo comboMeseVisita = null;
	private Combo comboAnnoVisita = null;
	private Label labelOraVisita = null;
	private Combo comboOraVisita = null;
	private Combo comboOraMinVisita = null;
	private Label labelNote = null;
	private Text textAreaNote = null;
	private Button buttonPrenotaVisita = null;
	private ComboViewer comboViewer2 = null;
	private ComboViewer comboViewer3 = null;
	private ComboViewer comboViewer4 = null;
	private ComboViewer comboViewer5 = null;
	private ComboViewer comboViewer6 = null;
	private ArrayList<Paziente> paz;  //  @jve:decl-index=0:
	private ArrayList<Tipologiavisita> tv;  //  @jve:decl-index=0:
	private Group groupDataPrenotazione = null;
	private Button buttonSelezionaData = null;
	private Shell ShellCalendario = null;  //  @jve:decl-index=0:visual-constraint="80,420"
	private Date dn = null;
	public static final String VIEW_ID = "StudioDietetico.prenotavisita";
	public PrenotaVisitaView() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void createPartControl(Composite parent) {
        top = new Composite(parent, SWT.NONE);
		// TODO Auto-generated method stub

        labelPazVis = new Label(top, SWT.NONE);
        labelPazVis.setBounds(new Rectangle(9, 10, 111, 18));
        labelPazVis.setText("Seleziona il paziente: ");
        listPazienti = new List(top, SWT.V_SCROLL);
        listPazienti.setBounds(new Rectangle(132, 11, 240, 92));
        labelTipolVisitPrenot = new Label(top, SWT.NONE);
        labelTipolVisitPrenot.setBounds(new Rectangle(15, 117, 164, 18));
        labelTipolVisitPrenot.setText("Seleziona la tipologia di visita:");
        createComboTipologVisita();
        labelDataPrenotVisita = new Label(top, SWT.WRAP);
        labelDataPrenotVisita.setBounds(new Rectangle(13, 167, 79, 27));
        labelDataPrenotVisita.setText("Data visita :");
        //createComboGiornoVisita();
        //createComboMeseVisita();
        //createComboAnnoVisita();
        /*labelOraVisita = new Label(top, SWT.NONE);
        labelOraVisita.setBounds(new Rectangle(14, 206, 63, 19));
        labelOraVisita.setText("Ora visita:");*/
        //createComboOraVisita();
        //createComboOraMinVisita();
        labelNote = new Label(top, SWT.NONE);
        labelNote.setBounds(new Rectangle(15, 210, 36, 19));
        labelNote.setText("Note:");
        textAreaNote = new Text(top, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL);
        textAreaNote.setBounds(new Rectangle(63, 208, 288, 103));
        buttonPrenotaVisita = new Button(top, SWT.NONE);
        buttonPrenotaVisita.setBounds(new Rectangle(290, 320, 115, 25));
        buttonPrenotaVisita.setText("Prenota visita");
        //createGroupDataPrenotazione();
        buttonSelezionaData = new Button(top, SWT.NONE);
        buttonSelezionaData.setBounds(new Rectangle(101, 166, 161, 27));
        buttonSelezionaData.setText("Seleziona la data della visita");
        buttonSelezionaData
        		.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
        			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
        				createShellCalendario();
        				ShellCalendario.open();
        				System.out.println("widgetSelected()"); // TODO Auto-generated Event stub widgetSelected()
        			}
        		});
        buttonPrenotaVisita
        		.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
        			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
        				//String dateString = comboAnnoVisita.getText()+"-"+comboMeseVisita.getText()+"-"+comboGiornoVisita.getText();
        				//String formato = "yyyy-MM-dd";
        				/*String dateString = comboAnnoVisita.getText()+"-"+comboMeseVisita.getText()+"-"+comboGiornoVisita.getText()+" "+comboOraVisita.getText()+":"+comboOraMinVisita.getText()+":00";
        				String formato = "yyyy-MM-dd HH:mm:ss";
        				Date dn = Utils.convertStringToDate(dateString, formato);*/   
        				Tipologiavisita tipovisita = tv.get(comboTipologVisita.getSelectionIndex());
        				Paziente paziente = paz.get(listPazienti.getSelectionIndex());
        				VisitaDAO v = new VisitaDAO();
        				dn = createShellCalendario();
        				//int pazsel = listPazienti.getFocusIndex();
        				v.prenotaVisita(paziente, tipovisita, dn, textAreaNote.getText());
        				System.out.println("widgetSelected()"); // TODO Auto-generated Event stub widgetSelected()
        			}
        		});
        //PazienteDAO ut = new PazienteDAO();
		paz = PazienteDAO.getPazienti();
		ArrayList<String> p = new ArrayList<String>();
		for (Paziente paziente : paz) {
			p.add(paziente.getCognome()+" "+paziente.getNome()+"  ("+paziente.getDataNascita()+")");
		}
		String[] pazientiArray = (String[]) p.toArray((new String[0]));
        listPazienti.setItems(pazientiArray);
        listViewer = new ListViewer(listPazienti);
	}

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub

	}

	/**
	 * This method initializes comboTipologVisita	
	 *
	 */
	private void createComboTipologVisita() {
		comboTipologVisita = new Combo(top, SWT.READ_ONLY);
		comboTipologVisita.setBounds(new Rectangle(189, 116, 184, 24));
		comboViewer = new ComboViewer(comboTipologVisita);
		//VisitaDAO v = new VisitaDAO();
		tv = VisitaDAO.getTipologVisita();
		ArrayList<String> tipvis = new ArrayList<String>();
		for (Tipologiavisita tipolvisit : tv) {
			tipvis.add(tipolvisit.getTipologia());
		}
		String[] tipolVisArray = (String[]) tipvis.toArray((new String[0]));
		comboTipologVisita.setItems(tipolVisArray);
	}

	/**
	 * This method initializes comboGiornoVisita	
	 *
	 */
/*	private void createComboGiornoVisita() {
		comboGiornoVisita = new Combo(top, SWT.READ_ONLY);
		comboGiornoVisita.setBounds(new Rectangle(104, 163, 73, 18));
		for (int i = 1; i < 32; i++) {
			comboGiornoVisita.add(""+i);
		}
		//comboGiornoVisita.setItems(new String [] {"01","02","03","04","05","06","07","08","09","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31"});
		comboGiornoVisita.setText(comboGiornoVisita.getItem(0));
		comboViewer2 = new ComboViewer(comboGiornoVisita);
	}*/

	/**
	 * This method initializes comboMeseVisita	
	 *
	 */
/*	private void createComboMeseVisita() {
		comboMeseVisita = new Combo(top, SWT.READ_ONLY);
		comboMeseVisita.setBounds(new Rectangle(184, 163, 65, 23));
		for (int i = 1; i < 13; i++) {
			comboMeseVisita.add(""+i);
		}
		//comboMeseVisita.setItems(new String [] {"01","02","03","04","05","06","07","08","09","10","11","12"});
		comboMeseVisita.setText(comboMeseVisita.getItem(0));
		comboViewer3 = new ComboViewer(comboMeseVisita);
	}*/

	/**
	 * This method initializes comboAnnoVisita	
	 *
	 */
/*	private void createComboAnnoVisita() {
		comboAnnoVisita = new Combo(top, SWT.READ_ONLY);
		comboAnnoVisita.setBounds(new Rectangle(257, 163, 80, 23));
		Date now = new Date();
		for (int i = (now.getYear()+1900); i < (now.getYear()+1902); i++) {
			comboAnnoVisita.add(""+i);
		}
		//comboAnnoVisita.setItems(new String [] {"2009","2010","2011","2012","2013","2014","2015"});
		comboAnnoVisita.setText(comboAnnoVisita.getItem(0));
		comboViewer4 = new ComboViewer(comboAnnoVisita);
	}*/

	/**
	 * This method initializes comboOraVisita	
	 *
	 */
/*	private void createComboOraVisita() {
		comboOraVisita = new Combo(top, SWT.READ_ONLY);
		comboOraVisita.setBounds(new Rectangle(90, 206, 58, 23));
		comboOraVisita.setItems(new String [] {"01","02","03","04","05","06","07","08","09","10","11","12","13","14","15","16","17","18","19","20","21","22","23"});
		comboOraVisita.setText(comboOraVisita.getItem(0));
		comboViewer5 = new ComboViewer(comboOraVisita);
	}*/

	/**
	 * This method initializes comboOraMinVisita	
	 *
	 */
/*	private void createComboOraMinVisita() {
		comboOraMinVisita = new Combo(top, SWT.READ_ONLY);
		comboOraMinVisita.setBounds(new Rectangle(159, 206, 67, 23));
		for (int i = 0; i < 60; i++) {
			comboOraMinVisita.add(""+i);
		}
		comboOraMinVisita.setText(comboOraMinVisita.getItem(0));
		comboViewer6 = new ComboViewer(comboOraMinVisita);
	}*/

	/**
	 * This method initializes groupDataPrenotazione	
	 *
	 */
/*	private void createGroupDataPrenotazione() {
		groupDataPrenotazione = new Group(top, SWT.NONE);
		groupDataPrenotazione.setLayout(new GridLayout());
		groupDataPrenotazione.setText("Data e ora prenotazione");
		groupDataPrenotazione.setBounds(new Rectangle(8, 145, 342, 92));
	}*/

	/**
	 * This method initializes ShellCalendario	
	 *
	 */
	private Date createShellCalendario() {
		ShellCalendario = new Shell(Display.getCurrent(), SWT.APPLICATION_MODAL | SWT.SHELL_TRIM);
		ShellCalendario.setLayout(new GridLayout());
		ShellCalendario.setSize(new Point(269, 293));
		ShellCalendario.setText("Seleziona la data");
		final DateTime calendar = new DateTime (ShellCalendario, SWT.CALENDAR | SWT.BORDER);
		//final DateTime date = new DateTime (ShellCalendario, SWT.DATE | SWT.SHORT);
		final DateTime time = new DateTime (ShellCalendario, SWT.TIME | SWT.SHORT);
		new Label (ShellCalendario, SWT.NONE);
		//new Label (ShellCalendario, SWT.NONE);
		Button ok = new Button (ShellCalendario, SWT.PUSH);
		ok.setText ("OK");
		ok.setLayoutData(new GridData (SWT.FILL, SWT.CENTER, false, false));
		ok.addSelectionListener (new SelectionAdapter () {
			public void widgetSelected (SelectionEvent e) {
				System.out.println ("Calendar date selected (MM/DD/YYYY) = " + (calendar.getMonth () + 1) + "/" + calendar.getDay () + "/" + calendar.getYear ());
				//System.out.println ("Date selected (MM/YYYY) = " + (date.getMonth () + 1) + "/" + date.getYear ());
				System.out.println ("Time selected (HH:MM) = " + time.getHours () + ":" + (time.getMinutes () < 10 ? "0" : "") + time.getMinutes ());
				
				String dateString = calendar.getYear ()+"-"+(calendar.getMonth () + 1)+"-"+calendar.getDay ()+" "+time.getHours () +":"+(time.getMinutes () < 10 ? "0" : "") + time.getMinutes ()+":00";
				String formato = "yyyy-MM-dd HH:mm:ss";
				dn = Utils.convertStringToDate(dateString, formato);   
				
				ShellCalendario.close ();
			}
		});

		//ShellCalendario.open();
		
		return dn ;
	}

	/*
	 * Temporary main generation 
	 */
	public static void main(String[] args) {
		// before you run this, make sure to set up the following in
		// the launch configuration (Arguments->VM Arguments) for the correct SWT lib. path
		// the following is a windows example,
		// -Djava.library.path="installation_directory\plugins\org.eclipse.swt.win32_3.0.1\os\win32\x86"
		/*org.eclipse.swt.widgets.Display display = org.eclipse.swt.widgets.Display
				.getDefault();
		PrenotaVisitaView test = new PrenotaVisitaView();
		test.createShellCalendario();
		test.ShellCalendario.open();
	
		while (!test.ShellCalendario.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();*/
	}

}  //  @jve:decl-index=0:visual-constraint="10,10,450,372"
