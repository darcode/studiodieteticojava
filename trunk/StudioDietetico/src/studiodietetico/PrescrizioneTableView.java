package studiodietetico;

import hibernate.Prenotazione;
import hibernate.Prescrizione;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.part.ViewPart;

import command.DietaDAO;
import common.Utils;

public class PrescrizioneTableView extends ViewPart {
	private Composite top = null;
	private TableForm classVis;
	private ArrayList<Object> prescrizioni;
	
	public PrescrizioneTableView() {}

	@Override
	public void createPartControl(Composite parent) {
		top = new Composite(parent, SWT.NONE);
		prescrizioni = DietaDAO.getPrescrizioniObject();
		DietaDAO dd = new DietaDAO();
		//TODO aggiungere parametri
		classVis = new TableForm(top, SWT.BORDER, prescrizioni, "","",PrescrizioneTableView.this, dd,"PrescrizioneTableView");
		classVis.setBounds(new Rectangle(6, 50, 800, 332));
		classVis.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		classVis.setLayout(new GridLayout(1, true));
		classVis.setBackground(Utils.getStandardWhiteColor());
		
		classVis.nascondiColonne(new int[] {0,1,2,5});
		
		aggiungiColonne(classVis, prescrizioni);

		classVis.aggiornaCombo();
		
		classVis.ordinamentoData(classVis.getTableVisualizzazione(), 3);
		classVis.ordinamentoInteri(classVis.getTableVisualizzazione(), 4);
		classVis.ordinamentoStringhe(classVis.getTableVisualizzazione(), 6);
		classVis.ordinamentoStringhe(classVis.getTableVisualizzazione(), 7);

	}

	public static void aggiungiColonne(TableForm classVis, ArrayList<Object> prescrizioni) {
		TableColumn colonna = new TableColumn(classVis.getTableVisualizzazione(), SWT.CENTER);
		colonna.setText("Paziente");
		String nome = "";
		TableItem itemSel = null;
		for (int j = 0; j < prescrizioni.size(); j++) {
			nome = ((Prescrizione)prescrizioni.get(j)).getPaziente().getCognome()+" "+((Prescrizione)prescrizioni.get(j)).getPaziente().getNome();
			itemSel = classVis.getTableVisualizzazione().getItem(j);
			itemSel.setText(classVis.getTableVisualizzazione().getColumnCount()-1, nome);
		} 
		colonna.pack();
		colonna.setResizable(false);
		
		TableColumn colonna2 = new TableColumn(classVis.getTableVisualizzazione(), SWT.CENTER);
		colonna2.setText("Dieta");
		String nome2 = "";
		TableItem itemSel2 = null;
		for (int j = 0; j < prescrizioni.size(); j++) {
			nome2 = ((Prescrizione)prescrizioni.get(j)).getDieta().getNome();
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
