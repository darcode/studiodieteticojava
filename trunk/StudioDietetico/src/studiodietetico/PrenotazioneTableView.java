package studiodietetico;

import hibernate.Prenotazione;
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

public class PrenotazioneTableView extends ViewPart {
	private ProvaTableForm classVis;
	private ArrayList<Object> prenotazioni;
	
	public PrenotazioneTableView() {}

	@Override
	public void createPartControl(Composite parent) {
	prenotazioni = VisitaDAO.getPrenotazioniObject();
	
	//TODO aggiungere parametri
	classVis = new ProvaTableForm(parent, SWT.BORDER, prenotazioni, "","","","PrenotazioneTableView");
	classVis.setLayout(new GridLayout(1, true));
	classVis.setBackground(Utils.getStandardWhiteColor());
	
	classVis.nascondiColonne(new int[] {0,1,2,4});
	
	aggiungiColonne(classVis, prenotazioni);
	
	classVis.aggiornaCombo();
	
	classVis.ordinamentoData(classVis.getTableVisualizzazione(), 3);
	classVis.ordinamentoStringhe(classVis.getTableVisualizzazione(), 5);
	classVis.ordinamentoStringhe(classVis.getTableVisualizzazione(), 6);
	}

	public static void aggiungiColonne(ProvaTableForm classVis, ArrayList<Object> prenotazioni) {
		TableColumn colonna = new TableColumn(classVis.getTableVisualizzazione(), SWT.CENTER);
		colonna.setText("Paziente");
		String nome = "";
		TableItem itemSel = null;
		for (int j = 0; j < prenotazioni.size(); j++) {
			nome = ((Prenotazione)prenotazioni.get(j)).getPaziente().getCognome()+" "+((Prenotazione)prenotazioni.get(j)).getPaziente().getNome();
			itemSel = classVis.getTableVisualizzazione().getItem(j);
			itemSel.setText(classVis.getTableVisualizzazione().getColumnCount()-1, nome);
		} 
		colonna.pack();
		colonna.setResizable(false);
		
		TableColumn colonna2 = new TableColumn(classVis.getTableVisualizzazione(), SWT.CENTER);
		colonna2.setText("Tipologia visita");
		String nome2 = "";
		TableItem itemSel2 = null;
		for (int j = 0; j < prenotazioni.size(); j++) {
			nome2 = ((Prenotazione)prenotazioni.get(j)).getTipologiavisita().getTipologia();
			itemSel2 = classVis.getTableVisualizzazione().getItem(j);
			itemSel2.setText(classVis.getTableVisualizzazione().getColumnCount()-1, nome2);
		} 
		colonna2.pack();
		colonna2.setResizable(false);
	}
	
	@Override
	public void setFocus() {
		classVis.setFocus();
	}

}
