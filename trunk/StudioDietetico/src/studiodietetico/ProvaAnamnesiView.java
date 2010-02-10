package studiodietetico;

import java.util.ArrayList;

import forms.HomePazienteForm;
import hibernate.Intervento;
import hibernate.Paziente;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;

import service.Utils;

import command.AnamnesiDAO;
import command.PazienteDAO;

public class ProvaAnamnesiView extends ViewPart {

	private Composite top = null;
	private TabFolder tabFolder = null;
	private Composite compositeInterventi = null;
	private Label labelPaziente;
	private Text textPaziente;
	private Paziente pazSelHome;
	private ArrayList<Object> interventiPazList;
	private ProvaTableForm classVis;
	public ProvaAnamnesiView() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void createPartControl(Composite parent) {
        top = new Composite(parent, SWT.NONE);
        createTabFolder();
	}

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub

	}

	/**
	 * This method initializes tabFolder	
	 *
	 */
	private void createTabFolder() {
		tabFolder = new TabFolder(top, SWT.TOP);
		tabFolder.setBounds(new Rectangle(0, 0, 854, 416));
		
		//Visualizzazione del paziente selezionato
		labelPaziente = new Label(tabFolder, SWT.NONE);
		labelPaziente.setBounds(new Rectangle(14, 30, 82, 24));
		labelPaziente.setText("Paziente");
		textPaziente = new Text(tabFolder, SWT.BORDER | SWT.BOLD);
		textPaziente.setBounds(new Rectangle(106, 30, 263, 31));
		textPaziente.setEnabled(false);
		pazSelHome = PazienteDAO.getPazienti().get(3);
		//pazSelHome = HomePazienteForm.getPazienteSelezionato();
		String dataNascPazSel = pazSelHome.getDataNascita().getDay()+"/"+pazSelHome.getDataNascita().getMonth()+"/"+pazSelHome.getDataNascita().getYear();
		textPaziente.setText(pazSelHome.getCognome()+"   "+pazSelHome.getNome()+"   "+dataNascPazSel);
		
		createCompositeInterventi(tabFolder);
		
	}

	/**
	 * This method initializes compositeInterventi	
	 *
	 */
	public void createCompositeInterventi(TabFolder tabFolder) {
		compositeInterventi = new Composite(tabFolder, SWT.TRANSPARENT);
		TabItem tabItemInterventi = new TabItem(tabFolder, SWT.NONE);
		tabItemInterventi.setText("Interventi");
		tabItemInterventi.setControl(compositeInterventi);
		interventiPazList = AnamnesiDAO.getInterventiPazPerLista(pazSelHome);
				
		//Richiama il costruttore della classe Form
		classVis = new ProvaTableForm(compositeInterventi, SWT.BORDER, interventiPazList);
		classVis.setBounds(new Rectangle(6, 50, 800, 332));
		classVis.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		classVis.setLayout(new GridLayout(1, true));
		classVis.setBackground(Utils.getStandardWhiteColor());
		
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
	

}
