package studiodietetico;

import hibernate.Prenotazione;
import hibernate.Prestazione;
import hibernate.PrestazioneId;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.part.ViewPart;

import command.MedicoDAO;
import common.Utils;

public class TurniTableView extends ViewPart {
	private Composite top = null;
	private TableForm classVis;
	private ArrayList<Object> prestazioni;

	public TurniTableView() {}

	@Override
	public void createPartControl(Composite parent) {
		top = new Composite(parent, SWT.NONE);
		prestazioni = MedicoDAO.getPrestazioniObject();
		//TODO aggiungere parametri
		classVis = new TableForm(top, SWT.BORDER, prestazioni, "","","","MedicoDAO","TurniTableView");
		classVis.setBounds(new Rectangle(6, 50, 800, 332));
		classVis.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		classVis.setLayout(new GridLayout(1, true));
		classVis.setBackground(Utils.getStandardWhiteColor());

		classVis.nascondiColonne(new int[] {0,1,2});
		
		aggiungiColonne(classVis, prestazioni);
		
		classVis.aggiornaCombo();
		
		classVis.ordinamentoData(classVis.getTableVisualizzazione(), 3);
		classVis.ordinamentoStringhe(classVis.getTableVisualizzazione(), 6);

	}

	public static void aggiungiColonne(TableForm classVis, ArrayList<Object> prestazioni) {
		TableColumn colonna = new TableColumn(classVis.getTableVisualizzazione(), SWT.CENTER);
		colonna.setText("Data turno");
		String nome = "";
		PrestazioneId prestId = null;
		TableItem itemSel = null;
		for (int j = 0; j < prestazioni.size(); j++) {
			prestId = ((Prestazione)prestazioni.get(j)).getId();
			nome = prestId.getDataTurno().toString();
			itemSel = classVis.getTableVisualizzazione().getItem(j);
			itemSel.setText(classVis.getTableVisualizzazione().getColumnCount()-1, nome);
		} 
		colonna.pack();
		colonna.setResizable(false);
		
		TableColumn colonna2 = new TableColumn(classVis.getTableVisualizzazione(), SWT.CENTER);
		colonna2.setText("Ora inizio turno");
		String nome2 = "";
		TableItem itemSel2 = null;
		for (int j = 0; j < prestazioni.size(); j++) {
			int lunghezzaNome = ((Prestazione)prestazioni.get(j)).getTurno().getOraInizio().toString().length();
			nome2 = ((Prestazione)prestazioni.get(j)).getTurno().getOraInizio().toString();
			itemSel2 = classVis.getTableVisualizzazione().getItem(j);
			itemSel2.setText(classVis.getTableVisualizzazione().getColumnCount()-1, nome2.substring(0, lunghezzaNome-3));
		} 
		colonna2.pack();
		colonna2.setResizable(false);
		
		TableColumn colonna3 = new TableColumn(classVis.getTableVisualizzazione(), SWT.CENTER);
		colonna3.setText("Ora fine turno");
		String nome3 = "";
		TableItem itemSel3 = null;
		for (int j = 0; j < prestazioni.size(); j++) {
			int lunghezzaNome = ((Prestazione)prestazioni.get(j)).getTurno().getOraFine().toString().length();
			nome3 = ((Prestazione)prestazioni.get(j)).getTurno().getOraFine().toString();
			itemSel3 = classVis.getTableVisualizzazione().getItem(j);
			itemSel3.setText(classVis.getTableVisualizzazione().getColumnCount()-1, nome3.substring(0, lunghezzaNome-3));
		} 
		colonna3.pack();
		colonna3.setResizable(false);
		
		TableColumn colonna4 = new TableColumn(classVis.getTableVisualizzazione(), SWT.CENTER);
		colonna4.setText("Medico");
		String nome4 = "";
		TableItem itemSel4 = null;
		for (int j = 0; j < prestazioni.size(); j++) {
			nome4 = ((Prestazione)prestazioni.get(j)).getMedico().getCognome()+" "+((Prestazione)prestazioni.get(j)).getMedico().getNome();
			itemSel4 = classVis.getTableVisualizzazione().getItem(j);
			itemSel4.setText(classVis.getTableVisualizzazione().getColumnCount()-1, nome4);
		} 
		colonna4.pack();
		colonna4.setResizable(false);
	}
	
	@Override
	public void setFocus() {
		classVis.setFocus();
	}

}
