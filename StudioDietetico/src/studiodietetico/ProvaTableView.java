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
		interventiPazList = AnamnesiDAO.getInterventiPazPerLista(pazienteSel);
		
		classVis = new ProvaTableForm(parent, SWT.BORDER, interventiPazList);
		classVis.setIdShellVisualizzaDettagli(AnamnesiView.VIEW_ID);
		//classVis.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		classVis.setLayout(new GridLayout(1, true));
		classVis.setBackground(Utils.getStandardWhiteColor());
		classVis.buttonInsert.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(
					org.eclipse.swt.events.SelectionEvent e) {
				Utils.showView(AnamnesiView.VIEW_ID);
			}
		});
		
		classVis.tableVisualizzazione.getColumn(1).setWidth(0);
		classVis.tableVisualizzazione.getColumn(2).setWidth(0);	
		TableColumn colonna = new TableColumn(classVis.tableVisualizzazione, SWT.CENTER);
		colonna.setText("Tipo Intervento");
		

		for (int j = 0; j < interventiPazList.size(); j++) {
			String nome = ((Intervento)interventiPazList.get(j)).getTipologiaintervento().getNome();
			TableItem itemSel = classVis.tableVisualizzazione.getItem(j);
			itemSel.setText(classVis.tableVisualizzazione.getColumnCount()-1, nome);
		} 
		colonna.pack();
		colonna.setResizable(false);
		
		classVis.nascondiColonna(1);
		classVis.nascondiColonna(2);		
		classVis.aggiornaCombo();
		
		classVis.ordinamentoInteri(classVis.tableVisualizzazione, 4);
		classVis.ordinamentoStringhe(classVis.tableVisualizzazione, 5);
		classVis.ordinamentoData(classVis.tableVisualizzazione, 3);		
			
		
		
		/*paz = PazienteDAO.getPazientiPerLista();
		riempiTabellaEntita(listElencoPazienti, paz);
		listElencoPazienti.getColumn(0).setWidth(0);*/
		
	}

	@Override
	public void setFocus() {
		classVis.setFocus();
	}
}




/*class ProvaDieta extends ListComposite {

	private Composite top = null;
	private Table tableVisualizzazione = null;
	
	public ProvaDieta(Composite parent, int style) {
		super(parent, style);
		initialize(parent);
	}

	private void initialize(Composite parent) {
		top = new Composite(parent, SWT.NONE);
		
        tableVisualizzazione = new Table(top, SWT.NONE);
        tableVisualizzazione.setHeaderVisible(true);
        tableVisualizzazione.setLinesVisible(true);
        tableVisualizzazione.setBounds(new Rectangle(31, 19, 660, 147));
		
	}
	
}*/








/*public static String getClassName(Object aClass){
    return aClass.getClass().getSimpleName();
  }
  
  // Tramite l'oggetto di cui non conosco la classe mi faccio restituire i suoi attributi in un array di stringhe
  
  public static String[] getStringFields(Object aClass) {
    Field[] campi = aClass.getClass().getDeclaredFields();
    String[] campiStringa = new String[campi.length];
    for (int i = 0; i < campi.length; i++) {
      campiStringa[i] = campi[i].getName();
    }
    return campiStringa;
  }*/
