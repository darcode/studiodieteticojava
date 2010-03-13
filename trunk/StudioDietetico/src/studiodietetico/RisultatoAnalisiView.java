package studiodietetico;



import hibernate.Esameclinico;
import hibernate.Parametroesame;
import hibernate.Paziente;
import hibernate.Prenotazione;
import hibernate.Referto;
import hibernate.Risultatoanalisi;

import java.util.ArrayList;
import java.util.Date;


import command.EsameClinicoDAO;
import command.MisurazioneDAO;
import command.ParametroAntropometricoDAO;
import command.ParametroClinicoDAO;
import command.PazienteDAO;
import command.RefertoDAO;
import command.RilevamentoDAO;
import command.RisultatoAnalisiDAO;
import command.VisitaDAO;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.swt.widgets.Combo;

import service.Utils;


public class RisultatoAnalisiView extends ViewPart {

	public static final String ID = "studiodietetico.RilevazioneParametroAntroView"; // TODO Needs to be whatever is mentioned in plugin.xml
	private Composite top = null;
	private Table tableParametroClinico = null;
	private Button btnRegistra = null;
	public TableViewer tableViewer = null;
	private ArrayList<Paziente> paz;  //  @jve:decl-index=0:
	ParametroAntropometricoDAO par = new ParametroAntropometricoDAO();  //  @jve:decl-index=0:
	private Button buttonSelezionaData = null;
	private Date dn = null;
	private Combo cmbParametro = null;
	private Label lblParametro = null;
	private Text txtValore = null;
	private Label lblValore = null;
	private Label lblOsservazione = null;
	private Text txtOsservazione = null;
	private Text textAreaPrenotazioniOdierne = null;
	private Label lblData = null;
	private Button btnAggiungi = null;
	private List listEsameClinico = null;
	private Label lblParametro1 = null;
	private Shell ShellCalendario = null;
	private PazienteDAO pz = new PazienteDAO();
	private EsameClinicoDAO es = new EsameClinicoDAO();
	private ParametroClinicoDAO parclinico= new ParametroClinicoDAO();  //  @jve:decl-index=0:
	private Esameclinico esamemem = new Esameclinico();  //  @jve:decl-index=0:
	private Paziente pazientemem = new Paziente();  //  @jve:decl-index=0:
	private Label labelPaziente = null;
	private Text textPaziente = null;
	
	@Override
	public void createPartControl(Composite parent) {
		top = new Composite(parent, SWT.NONE);
/*		GridData gdForm = new GridData(SWT.NONE);
		gdForm.grabExcessVerticalSpace = true;
		gdForm.grabExcessHorizontalSpace = true;
		gdForm.horizontalAlignment = SWT.FILL;
		gdForm.grabExcessVerticalSpace = true;
		gdForm.verticalAlignment = SWT.FILL;
		top.setLayoutData(gdForm);
		GridLayout glForm = new GridLayout(1, false);
		top.setLayout(glForm);*/
		labelPaziente = new Label(top, SWT.NONE);
		labelPaziente.setBounds(new Rectangle(36, 45, 49, 13));
		labelPaziente.setText("Paziente");
		textPaziente = new Text(top, SWT.BORDER | SWT.BOLD);
		textPaziente.setBounds(new Rectangle(100, 30, 263, 31));
		textPaziente.setEnabled(false);
		//pazSelHome = PazienteDAO.getPazienti().get(2);
		pazientemem = PazienteTableView.getPazienteSelezionato();
		String dataNascPazSel = pazientemem.getDataNascita().getDay()+"/"+pazientemem.getDataNascita().getMonth()+"/"+pazientemem.getDataNascita().getYear();
		textPaziente.setText(pazientemem.getCognome()+"   "+pazientemem.getNome()+"   "+dataNascPazSel);
		tableParametroClinico = new Table(top, SWT.NONE);
		tableParametroClinico.setHeaderVisible(true);
		tableParametroClinico.setLinesVisible(true);
		tableParametroClinico.setBounds(new Rectangle(14, 303, 421, 111));
		TableColumn tableColumn = new TableColumn(tableParametroClinico, SWT.NONE);
		tableColumn.setWidth(0);
		tableColumn.setText("IdParametro");
		TableColumn tableColumn1 = new TableColumn(tableParametroClinico, SWT.NONE);
		tableColumn1.setWidth(110);
		tableColumn1.setText("Parametro");
		TableColumn tableColumn11 = new TableColumn(tableParametroClinico, SWT.NONE);
		tableColumn11.setWidth(110);
		tableColumn11.setText("Misurazione");
		TableColumn tableColumn12 = new TableColumn(tableParametroClinico, SWT.NONE);
		tableColumn12.setWidth(200);
		tableColumn12.setText("Osservazione");
		tableViewer = new TableViewer(tableParametroClinico);
		btnRegistra = new Button(top, SWT.NONE);
		btnRegistra.setBounds(new Rectangle(319, 419, 117, 23));
		btnRegistra.setText("Registra Rilevazioni");
		buttonSelezionaData = new Button(top, SWT.NONE);
        buttonSelezionaData.setBounds(new Rectangle(13, 210, 189, 27));
        buttonSelezionaData.setText("Seleziona la data dell'esame");
        buttonSelezionaData
        		.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
        			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
        				createShellCalendario();
        				ShellCalendario.open();
        				System.out.println("widgetSelected()"); // TODO Auto-generated Event stub widgetSelected()
        			}
        		});
        lblData = new Label(top, SWT.NONE);
		lblData.setBounds(new Rectangle(216, 219, 172, 17));
		lblData.setText("");
		createCmbParametro();
		lblParametro = new Label(top, SWT.NONE);
		lblParametro.setBounds(new Rectangle(14, 256, 61, 13));
		lblParametro.setText("Parametro");
		txtValore = new Text(top, SWT.BORDER);
		txtValore.setBounds(new Rectangle(140, 275, 76, 19));
		lblValore = new Label(top, SWT.NONE);
		lblValore.setBounds(new Rectangle(140, 257, 43, 13));
		lblValore.setText("Valore");
		lblOsservazione = new Label(top, SWT.NONE);
		lblOsservazione.setBounds(new Rectangle(232, 257, 66, 13));
		lblOsservazione.setText("Osservazioni");
		txtOsservazione = new Text(top, SWT.BORDER);
		txtOsservazione.setBounds(new Rectangle(232, 276, 155, 19));
		btnAggiungi = new Button(top, SWT.NONE);
		btnAggiungi.setBounds(new Rectangle(404, 274, 30, 23));
		btnAggiungi.setText("+");
		listEsameClinico = new List(top, SWT.NONE);
		listEsameClinico.setBounds(new Rectangle(120, 119, 166, 76));
		listEsameClinico
				.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
					public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
						Esameclinico esame =  es.getEsameClinico(listEsameClinico.getItem(listEsameClinico.getSelectionIndex()));
						esamemem = esame;
						statoterzo();
					}
				});
		lblParametro1 = new Label(top, SWT.NONE);
		lblParametro1.setBounds(new Rectangle(14, 122, 103, 13));
		lblParametro1.setText("Seleziona esame:");
		btnAggiungi.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				Parametroesame parametro = parclinico.getParametroClinico(cmbParametro.getText(), esamemem.getIdEsameClinico());	
				TableItem item = new TableItem(tableParametroClinico, SWT.NONE);
			      item.setText(new String[] { Integer.toString(parametro.getIdParametroEsame()),parametro.getNome(),txtValore.getText(),txtOsservazione.getText() });
			      txtValore.setText("");
			      txtOsservazione.setText("");
			}
		});
		btnRegistra.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				for (int i = 0; i <= tableParametroClinico.getItemCount() - 1;++i){
					TableItem item = tableParametroClinico.getItem(i);
					Parametroesame parametro = parclinico.getParametroClinico(Integer.valueOf(item.getText(0)));
					
					RisultatoAnalisiDAO ris = new RisultatoAnalisiDAO();
					RefertoDAO ref = new RefertoDAO();
					Risultatoanalisi r = new Risultatoanalisi();
					Referto re = new Referto();
					re = ref.registraReferto(parametro, pazientemem, item.getText(3));
					Date data = dn;

					ris.registraRisultatoAnalisi(data, re, item.getText(2), item.getText(3));
					//p.registraMisurazione(parametro, paziente, setril, item.getText(3));
					
				}
				statosecondario();
			}
		});
        CaricaListEsami();
        statosecondario();
	}
	
	public void statosecondario(){
		listEsameClinico.setEnabled(true);
		cmbParametro.setVisible(false);
		txtValore.setVisible(false);
		txtOsservazione.setVisible(false);
		btnAggiungi.setVisible(false);
		btnRegistra.setVisible(false);
		tableParametroClinico.clearAll();
		tableParametroClinico.removeAll();
		//tutto il resto a false
	}
	
	public void statoterzo(){
		listEsameClinico.setEnabled(false);
		caricacmbParametri();
		cmbParametro.setVisible(true);
		txtValore.setVisible(true);
		txtOsservazione.setVisible(true);
		btnAggiungi.setVisible(true);
		btnRegistra.setVisible(true);
		//tutto il resto a true
	}
	
	public void CaricaListEsami(){
		listEsameClinico.removeAll();
		ArrayList<Esameclinico> lis = es.getEsamiClinici();
		for (Esameclinico esame : lis) {
			listEsameClinico.add(esame.getNome());
		}
	}
	
	public void caricacmbParametri(){
		ArrayList <Parametroesame> list = es.getParametriEsame(esamemem.getIdEsameClinico());
		for (int i = 0; i<= list.size() -1; ++i){
			cmbParametro.add(list.get(i).getNome());
		}
		cmbParametro.select(0);
	}
	
	@Override
	public void setFocus() {
		// TODO Auto-generated method stub

	}

	/**
	 * This method initializes cmbParametro	
	 *
	 */
	private void createCmbParametro() {
		cmbParametro = new Combo(top, SWT.NONE);
		cmbParametro.setBounds(new Rectangle(15, 275, 103, 21));
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
				//ArrayList<Prenotazione> prenotGiorno = VisitaDAO.getPrenotazioniGiorno(calendar.getYear (), (calendar.getMonth () + 1), calendar.getDay ());
				//textAreaPrenotazioniOdierne.setText("N. di prenotazioni per il "+calendar.getDay ()+"/"+(calendar.getMonth () + 1)+"/"+calendar.getYear ()+" = "+ prenotGiorno.size()+"\n");
				//for (Prenotazione prenotazione : prenotGiorno) {
				//	textAreaPrenotazioniOdierne.append("ore: "+prenotazione.getDataOra().getHours()+":"+ (prenotazione.getDataOra().getMinutes() < 10 ? "0" : "") + prenotazione.getDataOra().getMinutes()+"  -paziente: "+prenotazione.getPaziente().getCognome()+" "+prenotazione.getPaziente().getNome()+"\n");
				//}
				textAreaPrenotazioniOdierne.setTopIndex(0);
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		//final DateTime time = new DateTime (ShellCalendario, SWT.TIME | SWT.SHORT);
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
				//System.out.println ("Calendar date selected (MM/DD/YYYY) = " + (calendar.getMonth () + 1) + "/" + calendar.getDay () + "/" + calendar.getYear ());
				//System.out.println ("Date selected (MM/YYYY) = " + (date.getMonth () + 1) + "/" + date.getYear ());
				//System.out.println ("Time selected (HH:MM) = " + time.getHours () + ":" + (time.getMinutes () < 10 ? "0" : "") + time.getMinutes ());
				//String dateString = calendar.getYear ()+"-"+(calendar.getMonth () + 1)+"-"+calendar.getDay ()+" "+time.getHours () +":"+(time.getMinutes () < 10 ? "0" : "") + time.getMinutes ()+":00";
				String dateString = calendar.getYear ()+"-"+(calendar.getMonth () + 1)+"-"+calendar.getDay ()+" 00:00:00";
				String formato = "yyyy-MM-dd HH:mm:ss";
				dn = Utils.convertStringToDate(dateString, formato);
				lblData.setText("" + dateString);
				ShellCalendario.close ();
			}
		});

		//ShellCalendario.open();
		
		return dn ;
	}

}  //  @jve:decl-index=0:visual-constraint="12,3,535,462"
