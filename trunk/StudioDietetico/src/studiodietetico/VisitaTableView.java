package studiodietetico;

import hibernate.Intervento;
import hibernate.Visita;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.part.ViewPart;

import command.VisitaDAO;
import common.Utils;

public class VisitaTableView extends ViewPart {
	private ProvaTableForm classVis;
	private ArrayList<Object> visite;  //  @jve:decl-index=0:
	
	
	public VisitaTableView() {}

	@Override
	public void createPartControl(Composite parent) {
		visite = VisitaDAO.getVisite();
		//TODO aggiungere parametri
		classVis = new ProvaTableForm(parent, SWT.BORDER, visite, "","","","VisitaTableView");
		classVis.setLayout(new GridLayout(1, true));
		classVis.setBackground(Utils.getStandardWhiteColor());
		
		classVis.nascondiColonne(new int[] {0,1,2,3,6,7,8});

		aggiungiColonne(classVis, visite);
		
		classVis.aggiornaCombo();
		
		classVis.ordinamentoData(classVis.getTableVisualizzazione(), 4);
		classVis.ordinamentoData(classVis.getTableVisualizzazione(), 5);
		classVis.ordinamentoStringhe(classVis.getTableVisualizzazione(), 9);
		classVis.ordinamentoStringhe(classVis.getTableVisualizzazione(), 10);
	
	}
	
	public static void aggiungiColonne(ProvaTableForm classVis, ArrayList<Object> visite) {
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

}
