package studiodietetico;



import hibernate.Misurazione;
import hibernate.Parametroantropometrico;
import hibernate.Paziente;
import hibernate.Rilevamento;

import javax.swing.JComponent;

import java.util.ArrayList;
import java.util.Date;

import command.MisurazioneDAO;
import command.ParametroAntropometricoDAO;
import command.PazienteDAO;
import command.RilevamentoDAO;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.swt.widgets.Combo;

public class RilevazioneParametroAntroView extends ViewPart {

	public static final String ID = "studiodietetico.RilevazioneParametroAntroView"; // TODO Needs to be whatever is mentioned in plugin.xml
	private Composite top = null;
	private List listPaziente = null;
	private Label labelPazVis = null;
	private Table tableParametroAntropometrico = null;
	private Button btnRegistra = null;
	public TableViewer tableViewer = null;
	private JComponent[] components;
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
		labelPazVis.setText("Selezione il paziente: ");
		tableParametroAntropometrico = new Table(top, SWT.NONE);
		tableParametroAntropometrico.setHeaderVisible(true);
		tableParametroAntropometrico.setLinesVisible(true);
		tableParametroAntropometrico.setBounds(new Rectangle(14, 183, 421, 111));
		TableColumn tableColumn = new TableColumn(tableParametroAntropometrico, SWT.NONE);
		tableColumn.setWidth(80);
		tableColumn.setText("IdParametro");
		TableColumn tableColumn1 = new TableColumn(tableParametroAntropometrico, SWT.NONE);
		tableColumn1.setWidth(80);
		tableColumn1.setText("Parametro");
		TableColumn tableColumn11 = new TableColumn(tableParametroAntropometrico, SWT.NONE);
		tableColumn11.setWidth(80);
		tableColumn11.setText("Misurazione");
		TableColumn tableColumn12 = new TableColumn(tableParametroAntropometrico, SWT.NONE);
		tableColumn12.setWidth(180);
		tableColumn12.setText("Osservazione");
		tableViewer = new TableViewer(tableParametroAntropometrico);

		btnRegistra = new Button(top, SWT.NONE);
		btnRegistra.setBounds(new Rectangle(319, 300, 117, 23));
		btnRegistra.setText("Registra Rilevazioni");
		createCmbParametro();
		lblParametro = new Label(top, SWT.NONE);
		lblParametro.setBounds(new Rectangle(14, 125, 61, 13));
		lblParametro.setText("Parametro");
		txtValore = new Text(top, SWT.BORDER);
		txtValore.setBounds(new Rectangle(140, 144, 76, 19));
		txtValore.setEnabled(false);
		lblValore = new Label(top, SWT.NONE);
		lblValore.setBounds(new Rectangle(140, 126, 43, 13));
		lblValore.setText("Valore");
		lblOsservazione = new Label(top, SWT.NONE);
		lblOsservazione.setBounds(new Rectangle(232, 126, 66, 13));
		lblOsservazione.setText("Osservazioni");
		txtOsservazione = new Text(top, SWT.BORDER);
		txtOsservazione.setBounds(new Rectangle(232, 145, 155, 19));
		txtOsservazione.setEnabled(false);
		btnAggiungi = new Button(top, SWT.NONE);
		btnAggiungi.setBounds(new Rectangle(404, 143, 30, 23));
		btnAggiungi.setText("+");
		btnAggiungi.setEnabled(false);
		btnAggiungi.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				Parametroantropometrico parametro = par.getParametro(cmbParametro.getText());	
				TableItem item = new TableItem(tableParametroAntropometrico, SWT.NONE);
			      item.setText(new String[] { parametro.getIdParametroAntropometrico().toString(),parametro.getNome(),txtValore.getText(),txtOsservazione.getText() });
			      txtValore.setText("");
			      txtOsservazione.setText("");
			}
		});
		btnRegistra.setEnabled(false);
		btnRegistra.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				Paziente paziente = paz.get(listPaziente.getSelectionIndex());
				for (int i = 0; i <= tableParametroAntropometrico.getItemCount() - 1;++i){
					TableItem item = tableParametroAntropometrico.getItem(i);
					Parametroantropometrico parametro = par.getParametro(Integer.valueOf(item.getText(0)));
					RilevamentoDAO ril = new RilevamentoDAO();
					MisurazioneDAO p = new MisurazioneDAO();
					Rilevamento r = new Rilevamento();
					Misurazione misurazione = new Misurazione();
					misurazione = p.registraMisurazione(parametro, paziente, item.getText(3));
					Date data = new Date();

					ril.registraRilevamento(data, misurazione, item.getText(2));
					//p.registraMisurazione(parametro, paziente, setril, item.getText(3));
					
				}
				tableParametroAntropometrico.clearAll();
				tableParametroAntropometrico.removeAll();
				cmbParametro.setEnabled(true);
				txtValore.setEnabled(true);
				txtOsservazione.setEnabled(true);
				btnAggiungi.setEnabled(true);
				btnRegistra.setEnabled(true);
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
        				cmbParametro.setEnabled(true);
        				txtValore.setEnabled(true);
        				txtOsservazione.setEnabled(true);
        				btnAggiungi.setEnabled(true);
        				btnRegistra.setEnabled(true);
        			}
        		});
        listViewer = new ListViewer(listPaziente);
        caricacmbParametri();
	}
	
	public void caricacmbParametri(){
		ArrayList <Parametroantropometrico> list = ParametroAntropometricoDAO.getParametri();
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
		cmbParametro.setBounds(new Rectangle(15, 144, 103, 21));
		cmbParametro.setEnabled(false);
	}

}  //  @jve:decl-index=0:visual-constraint="10,10,440,328"
