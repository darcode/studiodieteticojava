package studiodietetico;

import hibernate.Paziente;
import hibernate.Prenotazione;
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

import command.PazienteDAO;
import command.VisitaDAO;

import org.eclipse.swt.widgets.Combo;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;

import service.Utils;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Table;

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
	private Combo comboMeseVisita = null;
	private Combo comboAnnoVisita = null;
	private Label labelOraVisita = null;
	private Combo comboOraVisita = null;
	private Combo comboOraMinVisita = null;
	private Label labelNote = null;
	private Text textAreaNote = null;
	private Button buttonPrenotaVisita = null;
	private ArrayList<Paziente> paz;  //  @jve:decl-index=0:
	private ArrayList<Tipologiavisita> tv;  //  @jve:decl-index=0:
	private Group groupDataPrenotazione = null;
	private Button buttonSelezionaData = null;
	private Shell ShellCalendario = null;  //  @jve:decl-index=0:visual-constraint="80,420"
	private Date dn = null;
	public static final String VIEW_ID = "StudioDietetico.prenotavisita";
	private Text textAreaPrenotazioniOdierne = null;
	private Shell sShellMessElimina;
	private Table tableTipVisita = null;
	private Button buttonCreaTipVisita = null;
	private Shell sShellCreaTipVisita = null;  //  @jve:decl-index=0:visual-constraint="15,723"
	private Label labelTipologia = null;
	private Text textTipologia = null;
	private Label labelCosto = null;
	private Text textCosto = null;
	private Button buttonOk = null;
	private Button buttonAnnulla = null;
	
	public PrenotaVisitaView() {
	}

	@Override
	public void createPartControl(Composite parent) {
        top = new Composite(parent, SWT.NONE);

/*        labelPazVis = new Label(top, SWT.NONE);
        labelPazVis.setBounds(new Rectangle(9, 10, 111, 18));
        labelPazVis.setText("Seleziona il paziente: ");
        listPazienti = new List(top, SWT.V_SCROLL);
        listPazienti.setBounds(new Rectangle(132, 11, 240, 92));*/
        labelTipolVisitPrenot = new Label(top, SWT.WRAP);
        labelTipolVisitPrenot.setBounds(new Rectangle(15, 12, 206, 31));
        labelTipolVisitPrenot.setText("Seleziona una tipologia di visita (se necessario, creane una nuova) :");
        //createComboTipologVisita();
        labelDataPrenotVisita = new Label(top, SWT.WRAP);
        labelDataPrenotVisita.setBounds(new Rectangle(15, 167, 79, 27));
        labelDataPrenotVisita.setText("Data visita :");
        labelNote = new Label(top, SWT.NONE);
        labelNote.setBounds(new Rectangle(15, 210, 36, 19));
        labelNote.setText("Note:");
        textAreaNote = new Text(top, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL);
        textAreaNote.setBounds(new Rectangle(63, 208, 340, 104));
        buttonPrenotaVisita = new Button(top, SWT.NONE);
        buttonPrenotaVisita.setBounds(new Rectangle(290, 320, 115, 25));
        buttonPrenotaVisita.setText("Prenota visita");
        buttonPrenotaVisita.setEnabled(false);
        buttonSelezionaData = new Button(top, SWT.NONE);
        buttonSelezionaData.setBounds(new Rectangle(101, 166, 189, 27));
        buttonSelezionaData.setText("Seleziona la data e l'ora della visita");
        tableTipVisita = new Table(top, SWT.FILL | SWT.BORDER | SWT.FULL_SELECTION | SWT.V_SCROLL | SWT.MULTI | SWT.H_SCROLL);
        tableTipVisita.setHeaderVisible(true);
        tableTipVisita.setLinesVisible(true);
        tableTipVisita.setBounds(new Rectangle(15, 49, 415, 109));
        buttonCreaTipVisita = new Button(top, SWT.NONE);
        buttonCreaTipVisita.setBounds(new Rectangle(238, 15, 188, 28));
        buttonCreaTipVisita.setText("Crea una nuova tipologia di visita");
        buttonCreaTipVisita
        		.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
        			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
        				createSShellCreaTipVisita();
        			}
        		});
        generaTabella();
        
        buttonSelezionaData
        		.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
        			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
        				createShellCalendario();
        			}
        		});
        buttonPrenotaVisita
        		.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
        			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) { 
        				if (tableTipVisita.getSelectionCount()>0) {
        					TableItem[] itemSelez = tableTipVisita.getSelection();
        	        		TableItem item = itemSelez[0];
        	        		int idTipSelez = Integer.parseInt(item.getText(0));
        	        		Tipologiavisita tipovisita = VisitaDAO.getTipVisitaByID(idTipSelez);
            				//Paziente paziente = paz.get(listPazienti.getSelectionIndex());
            				VisitaDAO v = new VisitaDAO();
            				dn = createShellCalendario();
            				// TODO controllare pazienteSel
            				v.prenotaVisita(PazienteTableView.pazienteSel, tipovisita, dn, textAreaNote.getText());
						} else {
							createMessSelElemCanc();
						}
        				        					
        			}
        		});
		paz = PazienteDAO.getPazienti();
		ArrayList<String> p = new ArrayList<String>();
		for (Paziente paziente : paz) {
			p.add(paziente.getCognome()+" "+paziente.getNome()+"  ("+paziente.getDataNascita()+")");
		}
		String[] pazientiArray = (String[]) p.toArray((new String[0]));
        //listPazienti.setItems(pazientiArray);
	}

	@Override
	public void setFocus() {
	}

	private Date createShellCalendario() {
		GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, true);
		gridData.heightHint = 32;
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 1;
		ShellCalendario = new Shell(Display.getCurrent(), SWT.APPLICATION_MODAL | SWT.SHELL_TRIM);
		ShellCalendario.setText("Seleziona data e ora");
		ShellCalendario.setLayout(new GridLayout());
		ShellCalendario.setSize(new Point(270, 300));
		final DateTime calendar = new DateTime (ShellCalendario, SWT.CALENDAR | SWT.BORDER);
		//final DateTime date = new DateTime (ShellCalendario, SWT.DATE | SWT.SHORT);
		calendar.addSelectionListener(new org.eclipse.swt.events.SelectionListener() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				ArrayList<Prenotazione> prenotGiorno = VisitaDAO.getPrenotazioniGiorno(calendar.getYear (), (calendar.getMonth () + 1), calendar.getDay ());
				textAreaPrenotazioniOdierne.setText("N. di prenotazioni per il "+calendar.getDay ()+"/"+(calendar.getMonth () + 1)+"/"+calendar.getYear ()+" = "+ prenotGiorno.size()+"\n");
				for (Prenotazione prenotazione : prenotGiorno) {
					textAreaPrenotazioniOdierne.append("ore: "+prenotazione.getDataOra().getHours()+":"+ (prenotazione.getDataOra().getMinutes() < 10 ? "0" : "") + prenotazione.getDataOra().getMinutes()+"  -paziente: "+prenotazione.getPaziente().getCognome()+" "+prenotazione.getPaziente().getNome()+"\n");
				}
				textAreaPrenotazioniOdierne.setTopIndex(0);
				System.out.println("widgetSelected()"); 
			}
			public void widgetDefaultSelected(org.eclipse.swt.events.SelectionEvent e) {
			}
		});
		final DateTime time = new DateTime (ShellCalendario, SWT.TIME | SWT.SHORT);
		textAreaPrenotazioniOdierne = new Text(ShellCalendario, SWT.MULTI | SWT.V_SCROLL | SWT.BORDER);
		textAreaPrenotazioniOdierne.setEditable(false);
		textAreaPrenotazioniOdierne.setLayoutData(gridData);
		//new Label (ShellCalendario, SWT.NONE);
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
				buttonPrenotaVisita.setEnabled(true);
			}
		});

		ShellCalendario.open();
		return dn ;
	}
	
	private void generaTabella() {
		TableColumn colonnaId = new TableColumn(tableTipVisita, SWT.CENTER);	
		colonnaId.setText("id");
		colonnaId.setWidth(0);
		colonnaId.setResizable(false);
		TableColumn colonnaData = new TableColumn(tableTipVisita, SWT.CENTER);	
		colonnaData.setText("Tipologia");
		TableColumn colonnaDescrizione = new TableColumn(tableTipVisita, SWT.CENTER);	
		colonnaDescrizione.setText("Costo (euro)");
		tv = VisitaDAO.getTipologVisita();
		for (int i = 0; i < tv.size(); i++) {
			TableItem item = new TableItem(tableTipVisita, SWT.NONE);
			item.setText(0, ""+tv.get(i).getIdTipologiaVisita());
			item.setText(1, tv.get(i).getTipologia());
			item.setText(2, ""+tv.get(i).getCostoVisita());
		}
		//nasconde la cifra decimale del prezzo della visita
		for (TableItem item : tableTipVisita.getItems()) {
			int i = 2;
			String testoitem = item.getText(i);
			int lunghezzaTestoItem = item.getText(i).length();
			item.setText(i, testoitem.substring(0, lunghezzaTestoItem-2));
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
		//sShellCreaTipVisita.setLayout(new GridLayout());
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
		buttonOk.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				Double costo = Double.parseDouble(textCosto.getText());
				VisitaDAO.registraTipologiaVisita(textTipologia.getText(), costo);
				tableTipVisita.removeAll(); //rimuove le righe
				//rimuove le colonne
				int k = 0;
				while (k<tableTipVisita.getColumnCount()) {
					tableTipVisita.getColumn(k).dispose();
				}
				//rigenera la tabella
				generaTabella();
				sShellCreaTipVisita.close();
			}
		});
		buttonAnnulla = new Button(sShellCreaTipVisita, SWT.NONE);
		buttonAnnulla.setBounds(new Rectangle(286, 82, 130, 25));
		buttonAnnulla.setText("Annulla");
		buttonAnnulla
				.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
					public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
						sShellCreaTipVisita.close();
					}
				});
		
		
		sShellCreaTipVisita.open();
	}
	
	private void createMessSelElemCanc() {
		createSShellMessElimina();
		MessageBox messageBox = new MessageBox(sShellMessElimina, SWT.OK | SWT.ICON_ERROR);
		messageBox.setMessage("Selezionare la tipologia di visita dalla tabella");
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

}  //  @jve:decl-index=0:visual-constraint="10,10,450,372"
