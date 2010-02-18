package studiodietetico;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.part.ViewPart;

import command.FatturaDAO;
import common.Utils;

public class FattureTableView extends ViewPart {
	private ProvaTableForm classVis;
	private ArrayList<Object> fatture;
	
	
	public FattureTableView() {}

	@Override
	public void createPartControl(Composite parent) {
		fatture = FatturaDAO.getFattureObject();
		//TODO aggiungere parametri
		classVis = new ProvaTableForm(parent, SWT.BORDER, fatture, "","","","FattureTableView");
		classVis.setLayout(new GridLayout(1, true));
		classVis.setBackground(Utils.getStandardWhiteColor());

		classVis.nascondiColonne(new int[] {0});
		
		classVis.aggiornaCombo();
		
		modificaColonna(classVis, fatture);
		
		classVis.ordinamentoInteri(classVis.getTableVisualizzazione(), 1);
		classVis.ordinamentoInteri(classVis.getTableVisualizzazione(), 2);
		classVis.ordinamentoInteri(classVis.getTableVisualizzazione(), 3);
		classVis.ordinamentoStringhe(classVis.getTableVisualizzazione(), 4);
		classVis.ordinamentoStringhe(classVis.getTableVisualizzazione(), 5);
		classVis.ordinamentoData(classVis.getTableVisualizzazione(), 6);
	}

	public static void modificaColonna(ProvaTableForm classVis, ArrayList<Object> prenotazioni) {
		for (int i = 1; i < 4; i++) {
			for (TableItem item : classVis.getTableVisualizzazione().getItems()) {
				String testoitem = item.getText(i);
				int lunghezzaTestoItem = item.getText(i).length();
				item.setText(i, testoitem.substring(0, lunghezzaTestoItem-2));
			}
		}
		for (TableItem item : classVis.getTableVisualizzazione().getItems()) {
			int i = 6;
			String testoitem = item.getText(i);
			int lunghezzaTestoItem = item.getText(i).length();
			item.setText(i, testoitem.substring(0, lunghezzaTestoItem-5));
		}
	}
	
	@Override
	public void setFocus() {
		classVis.setFocus();
	}

}
