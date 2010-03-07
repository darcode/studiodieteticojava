package studiodietetico;

import hibernate.Dieta;
import hibernate.Specifichedieta;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

import command.DietaDAO;

import service.GiornoDieta;
import service.StrutAlimento;
import service.StrutPasto;

public class InserisciDietaShell {

	private MessageBox messageBox = null;  //  @jve:decl-index=0:
	private Shell shellInsertPasto = null;  //  @jve:decl-index=0:visual-constraint="1016,17"
	private Button button = null;
	private Text textShellIns = null;
	private Label lShellIns = null;
	private int nGiorni = 0;

	private Shell shellInsSchemaDietetico = null;  //  @jve:decl-index=0:visual-constraint="10,4"
	private Group groupSchemaDieta = null;
	private Label lDescrizione = null;
	private Text textDescrizione = null;
	private List listGiorni = null;
	private Label lGiorni = null;
	private List listPasti = null;
	private Label lPasti = null;
	private Button bAddGiorno = null;
	private Button bDelGiorno = null;
	private Label lAlimenti = null;
	private Button bAddAlimenti = null;
	private Button bDelAlimento = null;
	private Label lAlimentiPasto = null;
	private Text textQuant = null;
	private Label lQuant = null;
	private Text textNoteSchema = null;
	private Label lNoteSchema = null;
	private Button bAddPasto = null;
	private Button bDelPasto = null;
	private Button bAddNewAlim = null;
	private Button bDelNewAlim = null;
	private static Table tableAlimenti = null;
	private Table tableAlimenti1 = null;
	private Button bAddDesc = null;
	private Button bAddNote = null;
	private Button bConfSchema = null;
	private Label lMod = null;
	private Button radioButton = null;
	private Button radioButton1 = null;
	private ArrayList<StrutAlimento> arrAlimenti = null;  //  @jve:decl-index=0:
	private Integer idTipoDieta = null;
	private Label labelNomeDieta = null;
	private Text textNomeDieta = null;
	private Button checkBoxStandard = null;
	private ArrayList<GiornoDieta> giorniDieta = null;  //  @jve:decl-index=0:
	private static DietaDAO dieta = null;
	private Table tableTipoDieta = null;
	private Button bAddNuovaDietaSpec = null;
	private Label lTipoDieta = null;
	private Shell sShellSpecificheDieta = null;  //  @jve:decl-index=0:visual-constraint="1014,332"
	private Group groupSpecificheDieta = null;
	private Label labelKilocal = null;
	private Spinner spinKilocal = null;
	private Label labelContAssente = null;
	private Text textContAssente = null;
	private Label labelContPresente = null;
	private Text textContPresente = null;
	private Text textIndicata = null;
	private Label labelIndicata = null;
	private Button buttonInsNewSpecDieta = null;
	private Shell shellMsg = null;
	private Text textNoteDieta = null;
	private Label labelNoteDieta = null;
	

	public InserisciDietaShell() {
		dieta = new DietaDAO();
		giorniDieta = new ArrayList<GiornoDieta>();
	}

	
	public void createShellInsSchemaDietetico() {
		//Display display = new Display();
		shellInsSchemaDietetico = new Shell(Display.getCurrent(),
				SWT.APPLICATION_MODAL | SWT.SHELL_TRIM);
		shellInsSchemaDietetico.setLayout(null);
		
		createGroupSchemaDieta();
		shellInsSchemaDietetico.setText("Inserisci nuovo Schema Dietetico");
		shellInsSchemaDietetico.setSize(new Point(942, 718));
		shellInsSchemaDietetico.open();
		
	}

	/**
	 * This method initializes groupSchemaDieta	
	 *
	 */
	private void createGroupSchemaDieta() {
	groupSchemaDieta = new Group(shellInsSchemaDietetico, SWT.NONE);
	groupSchemaDieta.setLayout(null);
	groupSchemaDieta.setText("Schema Dietetico");
	groupSchemaDieta.setBounds(new Rectangle(1, 11, 936, 632));
	tableAlimenti = new Table(groupSchemaDieta, SWT.CHECK | SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);
	tableAlimenti.setHeaderVisible(true);
	tableAlimenti.setLinesVisible(true);
	tableAlimenti.setBounds(new Rectangle(659, 98, 270, 180));
	tableAlimenti1 = new Table(groupSchemaDieta, SWT.CHECK | SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);
	tableAlimenti1.setHeaderVisible(true);
	tableAlimenti1.setLinesVisible(true);
	tableAlimenti1.setBounds(new Rectangle(318, 98, 270, 180));
	
	
	
	
	
	final TableColumn column1Nome = new TableColumn(tableAlimenti1, SWT.NONE);
	column1Nome.setText("Nome alimento");
	final TableColumn column1Tipologia = new TableColumn(tableAlimenti1, SWT.NONE);
	column1Tipologia.setText("Tipologia");
	final TableColumn column1Calorie = new TableColumn(tableAlimenti1, SWT.NONE);
	column1Calorie.setText("Calorie");
	final TableColumn column1Quantita = new TableColumn(tableAlimenti1, SWT.NONE);
	column1Quantita.setText("Quantità");
	final TableColumn columnNome = new TableColumn(tableAlimenti, SWT.NONE);
	columnNome.setText("Nome alimento");
	final TableColumn columnTipologia = new TableColumn(tableAlimenti, SWT.NONE);
	columnTipologia.setText("Tipologia");
	final TableColumn columnCalorie = new TableColumn(tableAlimenti, SWT.NONE);
	columnCalorie.setText("Calorie");
	final TableColumn columnIdAlimento = new TableColumn(tableAlimenti, SWT.NONE);
	columnIdAlimento.setText("Id Alimento");
	
	
	final TableColumn [] columns = tableAlimenti.getColumns ();
	aggiornaAlimenti();
	for (int i=0; i<columns.length; i++) columns[i].pack();


	final TableColumn [] columns1 = tableAlimenti1.getColumns ();
	for (int i=0; i<columns1.length; i++) columns1[i].pack();

	
	  Listener sortListener = new Listener() {
	        public void handleEvent(Event e) {
	            TableItem[] items = tableAlimenti.getItems();
	            Collator collator = Collator.getInstance(Locale.getDefault());
	            TableColumn column = (TableColumn)e.widget;
	            int index = 0;
	            if(column == columnNome){
	            	index = 0;
	            }else if (column == columnTipologia) {
	            	index = 1;
				}else if (column == columnCalorie) {
					index = 2;
				}else if (column == columnIdAlimento) {
					index = 3;
				}
	            String value1 = null;
	            String value2 = null;
	            int valueInt1 = 0;
	            int valueInt2 = 0;
	            for (int i = 1; i < items.length; i++) {
	                if((index==0)||(index==1)) {
	                	value1 = items[i].getText(index);
	                	for (int j = 0; j < i; j++){
		                    value2 = items[j].getText(index);
		                    if (collator.compare(value1, value2) < 0) {
		                        String[] values = {items[i].getText(0), items[i].getText(1), items[i].getText(2), items[i].getText(3)};
		                        items[i].dispose();
		                        TableItem item = new TableItem(tableAlimenti, SWT.NONE, j);
		                        item.setText(values);
		                        items = tableAlimenti.getItems();
		                        break;
		                    }
		                }
	                }else{
	                	if(items[i].getText(index).equals(""))
	                		valueInt1=Integer.MAX_VALUE;
	                	else
	                		valueInt1 = Integer.parseInt(items[i].getText(index));
	                	  for (int j = 0; j < i; j++){
	                		  if(items[j].getText(index).equals(""))
	                			  valueInt2=Integer.MAX_VALUE;
	                		  else
	                			  valueInt2 = Integer.parseInt(items[j].getText(index));
			                    if (valueInt1 < valueInt2) {
			                        String[] values = {items[i].getText(0), items[i].getText(1), items[i].getText(2), items[i].getText(3)};
			                        items[i].dispose();
			                        TableItem item = new TableItem(tableAlimenti, SWT.NONE, j);
			                        item.setText(values);
			                        items = tableAlimenti.getItems();
			                        break;
			                    }
			                }
	                }
	                	
	              
	            }
	            tableAlimenti.setSortColumn(column);
	        }
	    };
	    columnNome.addListener(SWT.Selection, sortListener);
	    columnTipologia.addListener(SWT.Selection, sortListener);
	    columnCalorie.addListener(SWT.Selection, sortListener);
	    columnIdAlimento.addListener(SWT.Selection, sortListener);
	    tableAlimenti.setSortColumn(columnNome);
	    tableAlimenti.setSortDirection(SWT.UP);
	lDescrizione = new Label(groupSchemaDieta, SWT.NONE);
	lDescrizione.setBounds(new Rectangle(11, 332, 88, 13));
	lDescrizione.setText("Descrizione");
	textDescrizione = new Text(groupSchemaDieta, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL);
	textDescrizione.setBounds(new Rectangle(12, 353, 377, 47));
	listGiorni = new List(groupSchemaDieta, SWT.NONE | SWT.V_SCROLL);
	listGiorni.setBounds(new Rectangle(13, 98, 135, 179));
	listGiorni.setFont(new Font(Display.getCurrent(), "Arial", 10, SWT.BOLD));
	listGiorni.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
		public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
			if(listGiorni.getSelectionIndex()<giorniDieta.size()){
				textDescrizione.setEnabled(true);
				textNoteSchema.setEnabled(true);
				textDescrizione.setText(giorniDieta.get(listGiorni.getSelectionIndex()).getDescrizione());
				textNoteSchema.setText(giorniDieta.get(listGiorni.getSelectionIndex()).getNote());
				bAddNote.setEnabled(true);
				bAddDesc.setEnabled(true);
				
				if (((giorniDieta.get(listGiorni.getSelectionIndex()).getPastiArr()).length==0)) {
					listPasti.removeAll();
					listPasti.add("Colazione");
					listPasti.add("Spuntino mattutino");
					listPasti.add("Pranzo");
					listPasti.add("Spuntino pomeridiano");
					listPasti.add("Cena");
					giorniDieta.get(listGiorni.getSelectionIndex()).addPasto(new StrutPasto("Colazione"));
					giorniDieta.get(listGiorni.getSelectionIndex()).addPasto(new StrutPasto("Spuntino mattutino"));
					giorniDieta.get(listGiorni.getSelectionIndex()).addPasto(new StrutPasto("Pranzo"));
					giorniDieta.get(listGiorni.getSelectionIndex()).addPasto(new StrutPasto("Spuntino pomeridiano"));
					giorniDieta.get(listGiorni.getSelectionIndex()).addPasto(new StrutPasto("Cena"));

				}else {
					listPasti.setItems(giorniDieta.get(listGiorni.getSelectionIndex()).getPastiArr());
				}

			}
			listPasti.setEnabled(true);
			bAddPasto.setEnabled(true);
			bDelPasto.setEnabled(true);

		}
	});
	lGiorni = new Label(groupSchemaDieta, SWT.NONE);
	lGiorni.setBounds(new Rectangle(12, 80, 90, 13));
	lGiorni.setText("Seleziona giorno");
	listPasti = new List(groupSchemaDieta, SWT.NONE | SWT.V_SCROLL);
	listPasti.setBounds(new Rectangle(160, 98, 135, 179));
	listPasti.setFont(new Font(Display.getCurrent(), "Arial", 10, SWT.BOLD));
	listPasti.setEnabled(false);
	listPasti.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
		public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
			if (listPasti.getSelectionIndex()<=listPasti.getItems().length) {
				tableAlimenti.setEnabled(true);

				bAddAlimenti.setEnabled(true);
				bDelAlimento.setEnabled(true);
				tableAlimenti1.setEnabled(true);
				textQuant.setEnabled(true);
				tableAlimenti1.removeAll();
				ArrayList<StrutAlimento> alimenti = giorniDieta.get(listGiorni.getSelectionIndex()).getPasto(listPasti.getSelectionIndex()).getAlimenti();
				for (int i = 0; i < alimenti.size(); i++) {
					TableItem item = new TableItem (tableAlimenti1, SWT.NONE);
					item.setText (0, alimenti.get(i).getNomeAlimento());
					item.setText (1, alimenti.get(i).getTipologia());
					if (alimenti.get(i).getCalorie() != 0) 
						item.setText (2, ""+alimenti.get(i).getCalorie());
					item.setText (3, alimenti.get(i).getQuantita());
				}
				for (int i=0; i<columns1.length; i++) columns1 [i].pack ();

				//listAlimenti1.setItems(giornoDieta.get(listGiorni.getSelectionIndex()).getPasto(listPasti.getSelectionIndex()).getAlimentiArr());

			}

		}
	});
	lPasti = new Label(groupSchemaDieta, SWT.NONE);
	lPasti.setBounds(new Rectangle(159, 80, 93, 13));
	lPasti.setText("Seleziona Pasto");
	bAddGiorno = new Button(groupSchemaDieta, SWT.NONE);
	bAddGiorno.setBounds(new Rectangle(12, 281, 23, 23));
	bAddGiorno.setText("+");
	bAddGiorno.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
		public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {

			if(radioButton.getSelection()){
				if(radioButton.isEnabled()||radioButton1.isEnabled()) {
					radioButton.setEnabled(false);
					radioButton1.setEnabled(false);
				}
				nGiorni+=7;
				giorniDieta.add(new GiornoDieta("Lunedì"));
				listGiorni.add("Lunedì");
				giorniDieta.add(new GiornoDieta("Martedì"));
				listGiorni.add("Martedì");
				giorniDieta.add(new GiornoDieta("Mercoledì"));
				listGiorni.add("Mercoledì");
				giorniDieta.add(new GiornoDieta("Giovedì"));
				listGiorni.add("Giovedì");
				giorniDieta.add(new GiornoDieta("Venerdì"));
				listGiorni.add("Venerdì");
				giorniDieta.add(new GiornoDieta("Sabato"));
				listGiorni.add("Sabato");
				giorniDieta.add(new GiornoDieta("Domenica"));
				listGiorni.add("Domenica");
				bAddGiorno.setEnabled(false);
				bDelGiorno.setEnabled(true);

			}else{
				if(radioButton.isEnabled()||radioButton1.isEnabled()) {
					radioButton.setEnabled(false);
					radioButton1.setEnabled(false);
				}
				nGiorni++;
				giorniDieta.add(new GiornoDieta("Giorno "+nGiorni));
				listGiorni.add("Giorno "+nGiorni);
				if(!bDelGiorno.isEnabled())
					bDelGiorno.setEnabled(true);
			}
		}
	});
	bDelGiorno = new Button(groupSchemaDieta, SWT.NONE);
	bDelGiorno.setBounds(new Rectangle(36, 281, 23, 23));
	bDelGiorno.setText("-");
	bDelGiorno.setEnabled(false);
	bDelGiorno.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
		public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {

			if(radioButton.getSelection()){
				nGiorni=0;
				giorniDieta.clear();
				listGiorni.removeAll();
				bAddGiorno.setEnabled(true);
				radioButton.setEnabled(true);
				radioButton1.setEnabled(true);
				bDelGiorno.setEnabled(false);
				bAddPasto.setEnabled(false);
				bDelPasto.setEnabled(false);

			}else {
				if (nGiorni == 1) {
					radioButton.setEnabled(true);
					radioButton1.setEnabled(true);
					bDelGiorno.setEnabled(false);
					bAddPasto.setEnabled(false);
					bDelPasto.setEnabled(false);
				}

				nGiorni--;
				giorniDieta.remove((listGiorni.getItemCount())-1);
				listGiorni.remove((listGiorni.getItemCount())-1);

			}
		}
	});
	lAlimenti = new Label(groupSchemaDieta, SWT.NONE);
	lAlimenti.setBounds(new Rectangle(659, 80, 75, 13));
	lAlimenti.setText("Lista alimenti");
	bAddAlimenti = new Button(groupSchemaDieta, SWT.NONE);
	bAddAlimenti.setBounds(new Rectangle(596, 120, 55, 23));
	bAddAlimenti.setText("<<");
	bAddAlimenti.setEnabled(false);
	bAddAlimenti
	.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
		public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
			TableItem[] tabItem = tableAlimenti.getItems();
			ArrayList<StrutAlimento> alimentiSel = new ArrayList<StrutAlimento>();
			ArrayList<StrutPasto> pasti = giorniDieta.get(listGiorni.getSelectionIndex()).getPasti();
			
			for (int i = 0; i < tabItem.length; i++) {
				if (tabItem[i].getChecked()) {
					if (tabItem[i].getText(2).equals("")) 
						alimentiSel.add(new StrutAlimento(tabItem[i].getText(0), tabItem[i].getText(1), 0, textQuant.getText(), 
								Integer.parseInt(tabItem[i].getText(3))));
					else
						alimentiSel.add(new StrutAlimento(tabItem[i].getText(0), tabItem[i].getText(1), Integer.parseInt(tabItem[i].getText(2)), textQuant.getText(), 
								Integer.parseInt(tabItem[i].getText(3))));
				}
				
			}
			for (int j = 0; j < tableAlimenti.getItems().length; j++) {
				tableAlimenti.getItems()[j].setChecked(false);
			}
			
			pasti.get(listPasti.getSelectionIndex()).addAlimenti(alimentiSel);

			for (int i = 0; i < alimentiSel.size(); i++) {
				TableItem item = new TableItem (tableAlimenti1, SWT.NONE);
				item.setText (0, alimentiSel.get(i).getNomeAlimento());
				item.setText (1, alimentiSel.get(i).getTipologia());
				if ((alimentiSel.get(i).getCalorie() != null) && (alimentiSel.get(i).getCalorie() != 0)) {
					item.setText (2, ""+alimentiSel.get(i).getCalorie());
				}
				item.setText (3, alimentiSel.get(i).getQuantita());
				
			}

			for (int i=0; i<columns1.length; i++) columns1 [i].pack ();
			
			textQuant.setText("");
		}

	
	});
	
	
	
	
	bDelAlimento = new Button(groupSchemaDieta, SWT.NONE);
	bDelAlimento.setBounds(new Rectangle(319, 280, 23, 23));
	bDelAlimento.setText("-");
	bDelAlimento.setEnabled(false);
	lAlimentiPasto = new Label(groupSchemaDieta, SWT.NONE);
	lAlimentiPasto.setBounds(new Rectangle(317, 80, 74, 13));
	lAlimentiPasto.setText("Alimenti pasto");
	textQuant = new Text(groupSchemaDieta, SWT.BORDER);
	textQuant.setBounds(new Rectangle(596, 98, 54, 19));
	textQuant.setEnabled(false);
	lQuant = new Label(groupSchemaDieta, SWT.NONE);
	lQuant.setBounds(new Rectangle(597, 80, 49, 13));
	lQuant.setText("Quantità");
	textNoteSchema = new Text(groupSchemaDieta, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL);
	textNoteSchema.setBounds(new Rectangle(398, 354, 369, 47));
	lNoteSchema = new Label(groupSchemaDieta, SWT.NONE);
	lNoteSchema.setBounds(new Rectangle(397, 335, 123, 13));
	lNoteSchema.setText("Note giorno");
	bAddPasto = new Button(groupSchemaDieta, SWT.NONE);
	bAddPasto.setBounds(new Rectangle(160, 281, 23, 23));
	bAddPasto.setText("+");
	bAddPasto.setEnabled(false);
	bAddPasto
	.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
		public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
			createShellInsertPasto();
		}
	});
	bDelPasto = new Button(groupSchemaDieta, SWT.NONE);
	bDelPasto.setBounds(new Rectangle(185, 281, 23, 23));
	bDelPasto.setText("-");
	bDelPasto.setEnabled(false);
	bDelPasto.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
		public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
			if (listPasti.getSelectionCount()>0) {

				giorniDieta.get(listGiorni.getSelectionIndex()).delPasto(listPasti.getSelection()[0]);
				listPasti.remove(listPasti.getSelectionIndex());
			}

		}
	});
	bAddNewAlim = new Button(groupSchemaDieta, SWT.NONE);
	bAddNewAlim.setBounds(new Rectangle(660, 281, 23, 23));
	bAddNewAlim.setText("+");
	bAddNewAlim.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
		public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
			InserisciAlimentoShell insAlimento = new InserisciAlimentoShell();
			insAlimento.createShellInsertAlimento("dieta");
			aggiornaAlimenti();
		}
	});
	bDelNewAlim = new Button(groupSchemaDieta, SWT.NONE);
	bDelNewAlim.setBounds(new Rectangle(685, 281, 23, 23));
	bDelNewAlim.setText("-");
	bAddDesc = new Button(groupSchemaDieta, SWT.NONE);
	bAddDesc.setBounds(new Rectangle(14, 399, 112, 23));
	bAddDesc.setText("Aggiungi descrizione");
	bAddNote = new Button(groupSchemaDieta, SWT.NONE);
	bAddNote.setBounds(new Rectangle(398, 403, 109, 23));
	bAddNote.setText("Aggiungi nota");
	bAddNote.setEnabled(false);
	bConfSchema = new Button(groupSchemaDieta, SWT.NONE);
	bConfSchema.setBounds(new Rectangle(785, 565, 132, 29));
	bConfSchema.setText("Conferma");
	lMod = new Label(groupSchemaDieta, SWT.NONE);
	lMod.setBounds(new Rectangle(231, 243, 40, 13));
	lMod.setText("Modalità");
	radioButton = new Button(groupSchemaDieta, SWT.RADIO);
	radioButton.setBounds(new Rectangle(64, 300, 77, 16));
	radioButton.setText("Settimanale");
	radioButton.setSelection(true);
	radioButton1 = new Button(groupSchemaDieta, SWT.RADIO);
	radioButton1.setBounds(new Rectangle(64, 285, 90, 16));
	radioButton1.setText("Personalizzata");
	labelNomeDieta = new Label(groupSchemaDieta, SWT.NONE);
	labelNomeDieta.setBounds(new Rectangle(14, 25, 144, 13));
	labelNomeDieta.setText("*Nome dieta");
	textNomeDieta = new Text(groupSchemaDieta, SWT.BORDER);
	textNomeDieta.setBounds(new Rectangle(16, 41, 279, 19));
	checkBoxStandard = new Button(groupSchemaDieta, SWT.CHECK);
	checkBoxStandard.setBounds(new Rectangle(318, 42, 98, 16));
	checkBoxStandard.setText("Dieta standard");
	tableTipoDieta = new Table(groupSchemaDieta, SWT.NONE | SWT.FULL_SELECTION | SWT.V_SCROLL);
	tableTipoDieta.setHeaderVisible(true);
	tableTipoDieta.setLinesVisible(true);
	tableTipoDieta.setBounds(new Rectangle(6, 468, 439, 137));
	tableTipoDieta
			.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
				

				public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
					TableItem itemSel = tableTipoDieta.getSelection()[0];
					idTipoDieta = Integer.parseInt(itemSel.getText(0));
				}
			});
	bAddNuovaDietaSpec = new Button(groupSchemaDieta, SWT.NONE);
	bAddNuovaDietaSpec.setBounds(new Rectangle(457, 478, 71, 23));
	bAddNuovaDietaSpec.setText("Crea nuovo");
	bAddNuovaDietaSpec
			.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
				public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
					createSShellSpecificheDieta();
				}
			});
	
	
	lTipoDieta = new Label(groupSchemaDieta, SWT.NONE);
	lTipoDieta.setBounds(new Rectangle(9, 452, 99, 13));
	lTipoDieta.setText("*Seleziona tipo dieta");
	textNoteDieta = new Text(groupSchemaDieta, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL);
	textNoteDieta.setBounds(new Rectangle(420, 39, 507, 30));
	labelNoteDieta = new Label(groupSchemaDieta, SWT.NONE);
	labelNoteDieta.setBounds(new Rectangle(418, 23, 54, 13));
	labelNoteDieta.setText("Note dieta");
	final TableColumn columnIdTipoDieta = new TableColumn(tableTipoDieta, SWT.NONE);
	columnIdTipoDieta.setText("Id");
	final TableColumn columnKilocal = new TableColumn(tableTipoDieta, SWT.NONE);
	columnKilocal.setText("Kilocalorie");
	final TableColumn columnIndicata = new TableColumn(tableTipoDieta, SWT.NONE);
	columnIndicata.setText("Indicazioni");
	final TableColumn columnContPres = new TableColumn(tableTipoDieta, SWT.NONE);
	columnContPres.setText("Contenuto presente");
	final TableColumn columnContAss = new TableColumn(tableTipoDieta, SWT.NONE);
	columnContAss.setText("Contenuto assente");
	final TableColumn[] columns2 = tableTipoDieta.getColumns();
	aggiornaTipoDiete();
	for (int i=0; i<columns2.length; i++) columns2[i].pack();
	
	
	bConfSchema.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
		public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
			shellMsg = new Shell();
			boolean inserisci = true;
			if (textNomeDieta.getText().equalsIgnoreCase("") | tableTipoDieta.getSelectionCount()==0) {
				inserisci = false;
			}

			if (inserisci) {
						
				Specifichedieta specifichedieta = dieta.getSpecificheDieta(Integer.parseInt(tableTipoDieta.getSelection()[0].getText(0)));
				dieta.inserisciDieta(giorniDieta, textNomeDieta.getText(), textNoteDieta.getText(), checkBoxStandard.getSelection(), specifichedieta);
			}else{
				messageBox = new MessageBox(shellMsg,
						SWT.OK |
						SWT.ICON_ERROR);
				messageBox.setMessage("Attenzione completare tutti i campi obbligatori (*)");	
				messageBox.open();		

			}


		
			
		}
	});
	bAddNote.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
		public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
			if (listGiorni.getSelectionCount()==1) {
				giorniDieta.get(listGiorni.getSelectionIndex()).setNote(textNoteSchema.getText());
				textNoteSchema.setText("");
			}
			
			
		}
	});
	bAddDesc.setEnabled(false);
	bAddDesc.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
		public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
			if (listGiorni.getSelectionCount()==1) {
			giorniDieta.get(listGiorni.getSelectionIndex()).setDescrizione(textDescrizione.getText());
			textDescrizione.setText("");
			}
			
		}
	});
}
	
	
	
	
	/**
	 * This method initializes shellInsert	
	 *
	 */
	private void createShellInsertPasto() {
		//	org.eclipse.swt.widgets.Display display = org.eclipse.swt.widgets.Display
		//.getDefault();
		// Shell must be created with style SWT.NO_TRIM
		shellInsertPasto = new Shell(Display.getCurrent(), SWT.APPLICATION_MODAL | SWT.SHELL_TRIM);
		shellInsertPasto.setLayout(null);
		shellInsertPasto.setSize(new Point(331, 92));
		shellInsertPasto.setText("Inserisci nuovo pasto");
		button = new Button(shellInsertPasto, SWT.NONE);
		button.setBounds(new Rectangle(237, 23, 66, 23));
		button.setText("Inserisci");
		button.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				listPasti.add(textShellIns.getText());
				giorniDieta.get(listGiorni.getSelectionIndex()).addPasto(new StrutPasto(textShellIns.getText()));
				shellInsertPasto.close();

			}
		});
		textShellIns = new Text(shellInsertPasto, SWT.BORDER);
		textShellIns.setBounds(new Rectangle(17, 26, 218, 19));
		lShellIns = new Label(shellInsertPasto, SWT.NONE);
		lShellIns.setBounds(new Rectangle(16, 9, 165, 13));
		lShellIns.setText("Inserisci nome pasto");
		shellInsertPasto.open();




	}



	/**
	 * This method initializes sShellSpecificheDieta	
	 *
	 */
	private void createSShellSpecificheDieta() {
		sShellSpecificheDieta = new Shell(Display.getCurrent(),
				SWT.APPLICATION_MODAL | SWT.SHELL_TRIM);
		sShellSpecificheDieta.setLayout(null);
		sShellSpecificheDieta.setText("Inserisci nuovo tipo dieta");
		createGroupSpecificheDieta();
		sShellSpecificheDieta.open();
	}

	/**
	 * This method initializes groupSpecificheDieta	
	 *
	 */
	private void createGroupSpecificheDieta() {
		groupSpecificheDieta = new Group(sShellSpecificheDieta, SWT.NONE);
		groupSpecificheDieta.setLayout(null);
		groupSpecificheDieta.setBounds(new Rectangle(5, 5, 351, 341));
		labelKilocal = new Label(groupSpecificheDieta, SWT.NONE);
		labelKilocal.setBounds(new Rectangle(8, 280, 70, 13));
		labelKilocal.setText("*Kilocalorie");
		spinKilocal = new Spinner(groupSpecificheDieta, SWT.NONE);
		spinKilocal.setFont(new Font(Display.getCurrent(), "Arial", 14, SWT.BOLD));
		spinKilocal.setMaximum(10000);
		spinKilocal.setMinimum(0);
		spinKilocal.setBounds(new Rectangle(9, 299, 115, 22));
		labelContAssente = new Label(groupSpecificheDieta, SWT.NONE);
		labelContAssente.setBounds(new Rectangle(10, 100, 144, 13));
		labelContAssente.setText("Contenuto assente");
		textContAssente = new Text(groupSpecificheDieta, SWT.BORDER | SWT.MULTI);
		textContAssente.setBounds(new Rectangle(10, 118, 319, 63));
		labelContPresente = new Label(groupSpecificheDieta, SWT.NONE);
		labelContPresente.setBounds(new Rectangle(10, 192, 148, 16));
		labelContPresente.setText("Contenuto presente");
		textContPresente = new Text(groupSpecificheDieta, SWT.BORDER | SWT.MULTI);
		textContPresente.setBounds(new Rectangle(11, 210, 319, 63));
		textIndicata = new Text(groupSpecificheDieta, SWT.BORDER | SWT.MULTI);
		textIndicata.setBounds(new Rectangle(12, 29, 319, 63));
		labelIndicata = new Label(groupSpecificheDieta, SWT.NONE);
		labelIndicata.setBounds(new Rectangle(12, 15, 97, 13));
		labelIndicata.setText("*Indicazioni");
		buttonInsNewSpecDieta = new Button(groupSpecificheDieta, SWT.NONE);
		buttonInsNewSpecDieta.setBounds(new Rectangle(225, 295, 102, 23));
		buttonInsNewSpecDieta.setText("Inserisci");
		buttonInsNewSpecDieta
		.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				int kilocal = Integer.parseInt(spinKilocal.getText());
			
				shellMsg = new Shell();
				boolean inserisci = true;
				if (kilocal==0 | textIndicata.getText().equals("")) {
					inserisci = false;
				}

				if (inserisci) {
					dieta.inserisciSpecificheDieta(textContAssente.getText(), textContPresente.getText(), textIndicata.getText(), kilocal);
					textContAssente.setText("");
					textContPresente.setText("");
					textIndicata.setText("");
					sShellSpecificheDieta.close();
					
				}else{
					messageBox = new MessageBox(shellMsg,
							SWT.OK |
							SWT.ICON_ERROR);
					messageBox.setMessage("Attenzione completare tutti i campi obbligatori (*)");	
					messageBox.open();	
				
			}
				aggiornaTipoDiete();
			}
		});
}

	

private void aggiornaTipoDiete() {
	tableTipoDieta.removeAll();
	//TODO
	ArrayList<Specifichedieta> tipoDiete = dieta.getSpecificheDieta();
	for (Specifichedieta tipodieta : tipoDiete) {
		TableItem itemSchema = new TableItem(tableTipoDieta, SWT.NULL);
		itemSchema.setText(0, ""+tipodieta.getIdSpecificheDieta());
		itemSchema.setText(1, ""+tipodieta.getKilocalorie());
		itemSchema.setText(2, tipodieta.getContenutoPresente());
		itemSchema.setText(3, tipodieta.getContenutoAssente());

	}
	
}

public static void aggiornaAlimenti() {
	tableAlimenti.removeAll();
	StrutAlimento stAli;
	ArrayList<StrutAlimento> arrStrutAli = dieta.getAlimentiObj();
	for (int i=0; i<arrStrutAli.size(); i++) {
		stAli = new StrutAlimento();
		stAli = arrStrutAli.get(i);
		TableItem item = new TableItem (tableAlimenti, SWT.NONE);
		item.setText (0, stAli.getNomeAlimento());
		item.setText (1, stAli.getTipologia());
		if (stAli.getCalorie() != 0) 
			item.setText (2, ""+stAli.getCalorie());
		item.setText (3, ""+stAli.getIdAlimento());
	}	

}
}
