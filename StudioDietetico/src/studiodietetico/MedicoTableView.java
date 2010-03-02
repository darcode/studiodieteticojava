package studiodietetico;

import hibernate.Medico;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

import command.MedicoDAO;
import common.Utils;

public class MedicoTableView extends ViewPart {
	private Composite top = null;
	private TableForm classVis;
	private ArrayList<Object> medici;

	public MedicoTableView() {}

	@Override
	public void createPartControl(Composite parent) {
		top = new Composite(parent, SWT.NONE);
		medici = MedicoDAO.getMediciObject();
		MedicoDAO md = new MedicoDAO();
		//TODO aggiungere parametri
		classVis = new TableForm(top, SWT.BORDER, medici, "","","", md, "MedicoTableView");
		classVis.setBounds(new Rectangle(6, 50, 800, 332));
		classVis.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
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
