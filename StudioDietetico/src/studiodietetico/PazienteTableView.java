package studiodietetico;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

import command.PazienteDAO;
import common.Utils;

public class PazienteTableView extends ViewPart {
	private ProvaTableForm classVis;
	private ArrayList<Object> pazienti;

	public PazienteTableView() {}

	@Override
	public void createPartControl(Composite parent) {
		pazienti = PazienteDAO.getPazientiObject();
		//TODO aggiungere parametri
		classVis = new ProvaTableForm(parent, SWT.BORDER, pazienti, "","","","PazienteTableView");
		classVis.setLayout(new GridLayout(1, true));
		classVis.setBackground(Utils.getStandardWhiteColor());
		
		classVis.nascondiColonne(new int[] {0,1,8,9,10,11,12,13,14,15});
		
		classVis.aggiornaCombo();
		
		classVis.ordinamentoData(classVis.getTableVisualizzazione(), 4);
		classVis.ordinamentoStringhe(classVis.getTableVisualizzazione(), 2);
		classVis.ordinamentoStringhe(classVis.getTableVisualizzazione(), 3);
		classVis.ordinamentoStringhe(classVis.getTableVisualizzazione(), 5);
		classVis.ordinamentoStringhe(classVis.getTableVisualizzazione(), 6);
		classVis.ordinamentoStringhe(classVis.getTableVisualizzazione(), 7);
	
		
	}

	@Override
	public void setFocus() {
		classVis.setFocus();
	}

}
