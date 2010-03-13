package studiodietetico;

import java.util.ArrayList;

import forms.TableUtil;
import hibernate.Intervento;
import hibernate.Paziente;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;

import service.Utils;

import command.AnamnesiDAO;
import command.PazienteDAO;


public class AnamnesiTTableView extends ViewPart {
	//Generale
	private Composite top = null;
	private static TabFolder tabFolder = null;
	private Label labelPaziente;
	private Text textPaziente;
	private static Paziente pazSelHome;
	//Interventi
	private Composite compositeInterventi = null;
	private ArrayList<Object> interventiPazList;
	private TableForm classVis;
	//Allergie
	private Composite compositeAllergie = null;
	private ArrayList<Object> allergiePazList;
	//Attivit‡Fisica
	private Composite compositeAttivitaFisica = null;
	private ArrayList<Object> sportPazList;
	//Farmaci
	private Composite compositeFarmaci = null;
	private ArrayList<Object> farmaciPazList;
	//Malattie
	private Composite compositeMalattie;
	private ArrayList<Object> malattiePazList;
	//Abitudini alimentari
	private Composite compositeAbitudini;
	private Object abitudinePazList;
	private Label labelNumPasti;
	private Composite topAbitudini;
	private AbitudiniForm abitudini;
	
	
	//Costruttore
	public AnamnesiTTableView() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void createPartControl(Composite parent) {
        top = new Composite(parent, SWT.NONE);
        GridData gdForm = new GridData(SWT.NONE);
		gdForm.grabExcessVerticalSpace = true;
		gdForm.grabExcessHorizontalSpace = true;
		gdForm.horizontalAlignment = SWT.FILL;
		gdForm.grabExcessVerticalSpace = true;
		gdForm.verticalAlignment = SWT.FILL;
		top.setLayoutData(gdForm);
		GridLayout glForm = new GridLayout(2, false);
		top.setLayout(glForm);
		//Visualizzazione del paziente selezionato
		labelPaziente = new Label(top, SWT.NONE);
		labelPaziente.setText("Paziente");
		textPaziente = new Text(top, SWT.BORDER | SWT.BOLD);
		textPaziente.setEnabled(false);
		//pazSelHome = PazienteDAO.getPazienti().get(2);
		pazSelHome = PazienteTableView.getPazienteSelezionato();
		String dataNascPazSel = pazSelHome.getDataNascita().getDay()+"/"+pazSelHome.getDataNascita().getMonth()+"/"+pazSelHome.getDataNascita().getYear();
		textPaziente.setText(pazSelHome.getCognome()+"   "+pazSelHome.getNome()+"   "+dataNascPazSel);
		
        createTabFolder();
	}

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub

	}
	public static Paziente getPazienteSel() {
		return pazSelHome;
	}

	/**
	 * This method initializes tabFolder	
	 *
	 */
	private void createTabFolder() {
		tabFolder = new TabFolder(top, SWT.TOP);
		GridData gdForm = new GridData(SWT.NONE);
		gdForm.grabExcessVerticalSpace = true;
		gdForm.grabExcessHorizontalSpace = true;
		gdForm.horizontalAlignment = SWT.FILL;
		gdForm.horizontalSpan = 2;
		gdForm.grabExcessVerticalSpace = true;
		gdForm.verticalAlignment = SWT.FILL;
		tabFolder.setLayoutData(gdForm);
		
		createCompositeInterventi(tabFolder);
		
		createCompositeAllergie(tabFolder);
		
		createCompositeAttivitaFisica(tabFolder);
		
		createCompositeFarmaci(tabFolder);
		
		createCompositeMalattie(tabFolder);
		
		createCompositeAbitudini(tabFolder);
		
	}
	
	public static void selectTab(int indexTab) {
		tabFolder.setSelection(indexTab);
	}

	
	//INTERVENTI
	public void createCompositeInterventi(TabFolder tabFolder) {
		compositeInterventi = new Composite(tabFolder, SWT.NONE);
		GridData gdForm = new GridData(SWT.NONE);
		gdForm.grabExcessVerticalSpace = true;
		gdForm.grabExcessHorizontalSpace = true;
		gdForm.horizontalAlignment = SWT.FILL;
		gdForm.grabExcessVerticalSpace = true;
		gdForm.verticalAlignment = SWT.FILL;
		compositeInterventi.setLayoutData(gdForm);
		GridLayout glForm = new GridLayout(1, false);
		compositeInterventi.setLayout(glForm);
		
		TabItem tabItemInterventi = new TabItem(tabFolder, SWT.NONE);
		tabItemInterventi.setText("Interventi");
		tabItemInterventi.setControl(compositeInterventi);
		AnamnesiDAO ad = new AnamnesiDAO();
		interventiPazList = ad.getInterventiListByPaziente(pazSelHome);
				
		//Richiama il costruttore della classe Form per gli interventi
		AnamnesiShell aw = new AnamnesiShell();
		classVis = new TableForm(compositeInterventi, SWT.NONE, interventiPazList,"createSShellDettagliInterventi", "createSShellInserimentoInterventi", aw, ad, "InterventiTableView");
		classVis.setBounds(new Rectangle(6, 50, 800, 600));
		classVis.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		classVis.setLayout(new GridLayout(1, true));
		classVis.setBackground(Utils.getStandardWhiteColor());
		
		//Nasconde le colonne che visualizzano gli id
		classVis.nascondiColonne(new int[]{0,1,2});
		
		//Aggiunge la colonna che visualizza il nome dell'intervento
		aggiungiColonnaIntervento(/*classVis, interventiPazList*/);
		
		//Aggiorna la combo con l'attributo aggiunto
		classVis.aggiornaCombo();
		
		//Applica l'ordinamento alle colonne visualizzate
		classVis.ordinamentoInteri(classVis.getTableVisualizzazione(), 4);
		classVis.ordinamentoStringhe(classVis.getTableVisualizzazione(), 5);
		classVis.ordinamentoData(classVis.getTableVisualizzazione(), 3);
	}
	
	public static void aggiungiColonnaIntervento(TableForm classVis, ArrayList<Object> interventiPazList) {
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
	}
	
	public void aggiungiColonnaIntervento() {
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
	}
		
	//ALLERGIE
	private void createCompositeAllergie(TabFolder tabFolder) {
		compositeAllergie = new Composite(tabFolder, SWT.NONE);
		GridData gdForm = new GridData(SWT.NONE);
		gdForm.grabExcessVerticalSpace = true;
		gdForm.grabExcessHorizontalSpace = true;
		gdForm.horizontalAlignment = SWT.FILL;
		gdForm.grabExcessVerticalSpace = true;
		gdForm.verticalAlignment = SWT.FILL;
		compositeAllergie.setLayoutData(gdForm);
		GridLayout glForm = new GridLayout(1, false);
		compositeAllergie.setLayout(glForm);
		
		TabItem tabItemAllergie = new TabItem(tabFolder, SWT.NONE);
		tabItemAllergie.setText("Allergie/Intolleranze");
		tabItemAllergie.setControl(compositeAllergie);
		AnamnesiDAO ad = new AnamnesiDAO();
		allergiePazList = ad.getAllergieListByPaziente(pazSelHome);
		
		//Richiama il costruttore della classe Form per le allergie
		AnamnesiShell aw = new AnamnesiShell();
		classVis = new TableForm(compositeAllergie, SWT.NONE, allergiePazList,"createSShellDettagliAllergie", "createSShellInserimentoAllergie", aw, ad, "AllergieTableView");
		classVis.setBounds(new Rectangle(6, 50, 800, 600));
		classVis.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		classVis.setLayout(new GridLayout(1, true));
		classVis.setBackground(Utils.getStandardWhiteColor());
		
		//Nasconde le colonne che visualizzano gli id
		classVis.nascondiColonne(new int[]{0,1,5,7});
		
		//Aggiorna la combo con l'attributo aggiunto
		classVis.aggiornaCombo();
		
		//Applica l'ordinamento alle colonne visualizzate
		classVis.ordinamentoStringhe(classVis.getTableVisualizzazione(), 2);
		classVis.ordinamentoStringhe(classVis.getTableVisualizzazione(), 3);
		classVis.ordinamentoStringhe(classVis.getTableVisualizzazione(), 4);
		classVis.ordinamentoStringhe(classVis.getTableVisualizzazione(), 6);
	}
	
	//ATTIVITA' FISICA
	private void createCompositeAttivitaFisica(TabFolder tabFolder) {
		compositeAttivitaFisica = new Composite(tabFolder, SWT.NONE);
		GridData gdForm = new GridData(SWT.NONE);
		gdForm.grabExcessVerticalSpace = true;
		gdForm.grabExcessHorizontalSpace = true;
		gdForm.horizontalAlignment = SWT.FILL;
		gdForm.grabExcessVerticalSpace = true;
		gdForm.verticalAlignment = SWT.FILL;
		compositeAttivitaFisica.setLayoutData(gdForm);
		GridLayout glForm = new GridLayout(1, false);
		compositeAttivitaFisica.setLayout(glForm);
		
		TabItem tabItemAttivitaFisica = new TabItem(tabFolder, SWT.NONE);
		tabItemAttivitaFisica.setText("Attivit‡ Fisica");
		tabItemAttivitaFisica.setControl(compositeAttivitaFisica);
		AnamnesiDAO ad = new AnamnesiDAO();
		sportPazList = ad.getSportListByPaziente(pazSelHome);
		
		//Richiama il costruttore della classe Form per gli sport
		AnamnesiShell aw = new AnamnesiShell();
		classVis = new TableForm(compositeAttivitaFisica, SWT.NONE, sportPazList,"createSShellDettagliSport", "createSShellInserimentoSport", aw, ad, "SportTableView");
		classVis.setBounds(new Rectangle(6, 50, 800, 600));
		classVis.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		classVis.setLayout(new GridLayout(1, true));
		classVis.setBackground(Utils.getStandardWhiteColor());
		
		//Nasconde le colonne che visualizzano gli id
		classVis.nascondiColonne(new int[]{0,1});
		
		//Aggiorna la combo con l'attributo aggiunto
		classVis.aggiornaCombo();
		
		//Applica l'ordinamento alle colonne visualizzate
		classVis.ordinamentoStringhe(classVis.getTableVisualizzazione(), 2);
		classVis.ordinamentoStringhe(classVis.getTableVisualizzazione(), 3);
		classVis.ordinamentoStringhe(classVis.getTableVisualizzazione(), 4);
		classVis.ordinamentoInteri(classVis.getTableVisualizzazione(), 5);
		
	}
	
	//FARMACI
	private void createCompositeFarmaci(TabFolder tabFolder) {
		compositeFarmaci = new Composite(tabFolder, SWT.NONE);
		GridData gdForm = new GridData(SWT.NONE);
		gdForm.grabExcessVerticalSpace = true;
		gdForm.grabExcessHorizontalSpace = true;
		gdForm.horizontalAlignment = SWT.FILL;
		gdForm.grabExcessVerticalSpace = true;
		gdForm.verticalAlignment = SWT.FILL;
		compositeFarmaci.setLayoutData(gdForm);
		GridLayout glForm = new GridLayout(1, false);
		compositeFarmaci.setLayout(glForm);
		
		TabItem tabItemFarmaci = new TabItem(tabFolder, SWT.NONE);
		tabItemFarmaci.setText("Farmaci");
		tabItemFarmaci.setControl(compositeFarmaci);
		AnamnesiDAO ad = new AnamnesiDAO();
		farmaciPazList = ad.getFarmaciListByPaziente(pazSelHome);
		
		//Richiama il costruttore della classe Form per gli sport
		AnamnesiShell aw = new AnamnesiShell();
		classVis = new TableForm(compositeFarmaci, SWT.NONE, farmaciPazList,"createSShellDettagliFarmaco", "createSShellInserimentoFarmaco", aw, ad, "FarmaciTableView");
		classVis.setBounds(new Rectangle(6, 50, 800, 600));
		classVis.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		classVis.setLayout(new GridLayout(1, true));
		classVis.setBackground(Utils.getStandardWhiteColor());
		
		//Nasconde le colonne che visualizzano gli id
		classVis.nascondiColonne(new int[]{0,1,3,6});
		
		//Aggiorna la combo con l'attributo aggiunto
		classVis.aggiornaCombo();
		
		//Applica l'ordinamento alle colonne visualizzate
		classVis.ordinamentoStringhe(classVis.getTableVisualizzazione(), 2);
		classVis.ordinamentoStringhe(classVis.getTableVisualizzazione(), 4);
		classVis.ordinamentoStringhe(classVis.getTableVisualizzazione(), 5);
		
	}
	
	//MALATTIE
	private void createCompositeMalattie(TabFolder tabFolder) {
		compositeMalattie = new Composite(tabFolder, SWT.NONE);
		GridData gdForm = new GridData(SWT.NONE);
		gdForm.grabExcessVerticalSpace = true;
		gdForm.grabExcessHorizontalSpace = true;
		gdForm.horizontalAlignment = SWT.FILL;
		gdForm.grabExcessVerticalSpace = true;
		gdForm.verticalAlignment = SWT.FILL;
		compositeMalattie.setLayoutData(gdForm);
		GridLayout glForm = new GridLayout(1, false);
		compositeMalattie.setLayout(glForm);
		TabItem tabItemMalattie = new TabItem(tabFolder, SWT.NONE);
		tabItemMalattie.setText("Malattie");
		tabItemMalattie.setControl(compositeMalattie);
		AnamnesiDAO ad = new AnamnesiDAO();
		malattiePazList = ad.getMalattieListByPaziente(pazSelHome);
		
		//Richiama il costruttore della classe Form per gli sport
		AnamnesiShell aw = new AnamnesiShell();
		classVis = new TableForm(compositeMalattie, SWT.NONE, malattiePazList,"createSShellDettagliMalattia", "createSShellInserimentoMalattia", aw, ad, "MalattiaTableView");
		classVis.setBounds(new Rectangle(6, 50, 800, 600));
		classVis.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		classVis.setLayout(new GridLayout(1, true));
		classVis.setBackground(Utils.getStandardWhiteColor());
		
		//Nasconde le colonne che visualizzano gli id
		classVis.nascondiColonne(new int[]{0,1});
		
		//Aggiorna la combo con l'attributo aggiunto
		classVis.aggiornaCombo();
		
		//Applica l'ordinamento alle colonne visualizzate
		classVis.ordinamentoStringhe(classVis.getTableVisualizzazione(), 2);
		classVis.ordinamentoStringhe(classVis.getTableVisualizzazione(), 3);
	}
	
	//ABITUDINI ALIMENTARI
	private void createCompositeAbitudini(TabFolder tabFolder) {
		compositeAbitudini = new Composite(tabFolder, SWT.NONE);
		GridData gdForm = new GridData(SWT.NONE);
		gdForm.grabExcessVerticalSpace = true;
		gdForm.grabExcessHorizontalSpace = true;
		gdForm.horizontalAlignment = SWT.FILL;
		gdForm.grabExcessVerticalSpace = true;
		gdForm.verticalAlignment = SWT.FILL;
		compositeAbitudini.setLayoutData(gdForm);
		GridLayout glForm = new GridLayout(1, false);
		compositeAbitudini.setLayout(glForm);
		
		TabItem tabItemAbitudine = new TabItem(tabFolder, SWT.NONE);
		tabItemAbitudine.setText("Abitudini alimentari");
		tabItemAbitudine.setControl(compositeAbitudini);
		AnamnesiDAO ad = new AnamnesiDAO();
		abitudinePazList = ad.getAbitudineByPaziente(pazSelHome);
				
		abitudini = new AbitudiniForm(compositeAbitudini, SWT.NONE);
		abitudini.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		abitudini.setLayout(new GridLayout(1, true));
		abitudini.setBackground(compositeAbitudini.getBackground());
		
		
	}
	
}


