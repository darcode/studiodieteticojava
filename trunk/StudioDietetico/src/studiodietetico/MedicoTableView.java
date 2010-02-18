package studiodietetico;

import hibernate.Medico;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

import command.MedicoDAO;
import common.Utils;

public class MedicoTableView extends ViewPart {
	private ProvaTableForm classVis;
	private ArrayList<Object> medici;

	public MedicoTableView() {}

	@Override
	public void createPartControl(Composite parent) {
		medici = MedicoDAO.getMediciObject();
		//TODO aggiungere parametri
		classVis = new ProvaTableForm(parent, SWT.BORDER, medici, "","","","MedicoTableView");
		classVis.setLayout(new GridLayout(1, true));
		classVis.setBackground(Utils.getStandardWhiteColor());
		
		classVis.nascondiColonne(new int[] {0,1,4,5,8,9,13});
		
		classVis.aggiornaCombo();
		
		classVis.ordinamentoStringhe(classVis.getTableVisualizzazione(), 2);
		classVis.ordinamentoStringhe(classVis.getTableVisualizzazione(), 3);
		classVis.ordinamentoStringhe(classVis.getTableVisualizzazione(), 6);
		classVis.ordinamentoStringhe(classVis.getTableVisualizzazione(), 7);
		classVis.ordinamentoStringhe(classVis.getTableVisualizzazione(), 10);
		classVis.ordinamentoStringhe(classVis.getTableVisualizzazione(), 11);
		classVis.ordinamentoStringhe(classVis.getTableVisualizzazione(), 12);

	}

	@Override
	public void setFocus() {
		classVis.setFocus();
	}

}
