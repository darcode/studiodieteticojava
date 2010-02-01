package studiodietetico;



import hibernate.Esameclinico;
import hibernate.Parametroesame;
import hibernate.Paziente;
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

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.swt.widgets.Combo;


public class RisultatoAnalisiView extends ViewPart {

	public static final String ID = "studiodietetico.RilevazioneParametroAntroView"; // TODO Needs to be whatever is mentioned in plugin.xml
	private Composite top = null;
	private List listPaziente = null;
	private Label labelPazVis = null;
	private Table tableParametroClinico = null;
	private Button btnRegistra = null;
	public TableViewer tableViewer = null;
	private ArrayList<Paziente> paz;  //  @jve:decl-index=0:
	private ListViewer listViewer = null;
	ParametroAntropometricoDAO par = new ParametroAntropometricoDAO();  //  @jve:decl-index=0:
    
	private Combo cmbParametro = null;
	private Label lblParametro = null;
	private Text txtValore = null;
	private Label lblValore = null;
	private Label lblOsservazione = null;
	private Text txtOsservazione = null;
	private Button btnAggiungi = null;
	private List listEsameClinico = null;
	private Label lblParametro1 = null;
	private PazienteDAO pz = new PazienteDAO();
	private EsameClinicoDAO es = new EsameClinicoDAO();
	private ParametroClinicoDAO parclinico= new ParametroClinicoDAO();  //  @jve:decl-index=0:
	private Esameclinico esamemem = new Esameclinico();  //  @jve:decl-index=0:
	private Paziente pazientemem = new Paziente();
	
	@Override
	public void createPartControl(Composite parent) {
		// TODO Auto-generated method stub
		top = new Composite(parent, SWT.NONE);
		top.setLayout(null);
		listPaziente = new List(top, SWT.NONE);
		listPaziente.setBounds(new Rectangle(119, 15, 167, 77));
		listViewer = new ListViewer(listPaziente);
		labelPazVis = new Label(top, SWT.NONE);
		labelPazVis.setBounds(new Rectangle(11, 15, 103, 13));
		labelPazVis.setText("Seleziona paziente: ");
		tableParametroClinico = new Table(top, SWT.NONE);
		tableParametroClinico.setHeaderVisible(true);
		tableParametroClinico.setLinesVisible(true);
		tableParametroClinico.setBounds(new Rectangle(14, 288, 421, 111));
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
		btnRegistra.setBounds(new Rectangle(319, 405, 117, 23));
		btnRegistra.setText("Registra Rilevazioni");
		createCmbParametro();
		lblParametro = new Label(top, SWT.NONE);
		lblParametro.setBounds(new Rectangle(14, 230, 61, 13));
		lblParametro.setText("Parametro");
		txtValore = new Text(top, SWT.BORDER);
		txtValore.setBounds(new Rectangle(140, 249, 76, 19));
		lblValore = new Label(top, SWT.NONE);
		lblValore.setBounds(new Rectangle(140, 231, 43, 13));
		lblValore.setText("Valore");
		lblOsservazione = new Label(top, SWT.NONE);
		lblOsservazione.setBounds(new Rectangle(232, 231, 66, 13));
		lblOsservazione.setText("Osservazioni");
		txtOsservazione = new Text(top, SWT.BORDER);
		txtOsservazione.setBounds(new Rectangle(232, 250, 155, 19));
		btnAggiungi = new Button(top, SWT.NONE);
		btnAggiungi.setBounds(new Rectangle(404, 248, 30, 23));
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
					Date data = new Date();

					ris.registraRisultatoAnalisi(data, re, item.getText(2), item.getText(3));
					//p.registraMisurazione(parametro, paziente, setril, item.getText(3));
					
				}
				statoiniziale();
			}
		});
		paz = PazienteDAO.getPazienti();
		ArrayList<String> p = new ArrayList<String>();
		for (Paziente paziente : paz) {
			p.add(paziente.getCognome()+" "+paziente.getNome());
		}
		String[] pazientiArray = (String[]) p.toArray((new String[0]));
        listPaziente.setItems(pazientiArray);
        listPaziente
        		.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
        			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
        				Paziente paziente =  paz.get(listPaziente.getSelectionIndex());
						pazientemem = paziente;
        				statosecondario();
        			}
        		});
        listViewer = new ListViewer(listPaziente);
        CaricaListEsami();
        statoiniziale();
	}
	
	public void statoiniziale() {
		listPaziente.setEnabled(true);
		listEsameClinico.setEnabled(false);
		cmbParametro.setVisible(false);
		txtValore.setVisible(false);
		txtOsservazione.setVisible(false);
		btnAggiungi.setVisible(false);
		btnRegistra.setVisible(false);
		tableParametroClinico.clearAll();
		tableParametroClinico.removeAll();
		//tutti gli altri a false
	}
	
	public void statosecondario(){
		listPaziente.setEnabled(false);
		listEsameClinico.setEnabled(true);
		cmbParametro.setVisible(false);
		txtValore.setVisible(false);
		txtOsservazione.setVisible(false);
		btnAggiungi.setVisible(false);
		btnRegistra.setVisible(false);
		//tutto il resto a false
	}
	
	public void statoterzo(){
		listPaziente.setEnabled(false);
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
		cmbParametro.setBounds(new Rectangle(15, 249, 103, 21));
	}

}  //  @jve:decl-index=0:visual-constraint="12,3,535,462"
