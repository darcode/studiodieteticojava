package studiodietetico;

import hibernate.Intervento;
import hibernate.Paziente;

import java.lang.reflect.Field;
import java.util.ArrayList;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import command.AnamnesiDAO;
import command.PazienteDAO;
import common.Utils;
import common.ui.ListComposite;
import forms.HomePazienteForm;


//Provo a gestire gli interventi
public class ProvaTableView extends ViewPart {
	private ProvaTableForm classVis;
	private Paziente pazienteSel;  //  @jve:decl-index=0:
	private ArrayList<Object> interventiPazList;

	public ProvaTableView() {}

	@Override
	public void createPartControl(Composite parent) {
		//Prende il paziente selezionato
		pazienteSel = PazienteDAO.getPazienti().get(4);
		interventiPazList = AnamnesiDAO.getInterventiListByPaziente(pazienteSel);
		
		//Richiama il costruttore della classe Form
		classVis = new ProvaTableForm(parent, SWT.BORDER, interventiPazList);
		classVis.setLayout(new GridLayout(1, true));
		classVis.setBackground(Utils.getStandardWhiteColor());
		//classVis.setIdShellVisualizzaDettagli(AnamnesiView.VIEW_ID);
		//classVis.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		
		//Nasconde le colonne che visualizzano gli id
		classVis.nascondiColonna(1);
		classVis.nascondiColonna(2);
		
		//Aggiunge la colonna che visualizza il nome dell'intervento
		TableColumn colonna = new TableColumn(classVis.getTableVisualizzazione(), SWT.CENTER);
		colonna.setText("Tipo Intervento");
		String nome = "";
		TableItem itemSel = null;
		for (int j = 0; j < interventiPazList.size(); j++) {
			nome = ((Intervento)interventiPazList.get(j)).getTipologiaintervento().getNome();
			itemSel = classVis.getTableVisualizzazione().getItem(j);
			itemSel.setText(classVis.getTableVisualizzazione().getColumnCount()-1, nome);
		} 
		colonna.pack();
		colonna.setResizable(false);
		
		//Aggiorna la combo con l'attributo aggiunto
		classVis.aggiornaCombo();
		
		//Applica l'ordinamento alle colonne visualizzate
		classVis.ordinamentoInteri(classVis.getTableVisualizzazione(), 4);
		classVis.ordinamentoStringhe(classVis.getTableVisualizzazione(), 5);
		classVis.ordinamentoData(classVis.getTableVisualizzazione(), 3);		
	}

	@Override
	public void setFocus() {
		classVis.setFocus();
	}
}