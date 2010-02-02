package studiodietetico;

import forms.HomePazienteForm;
import hibernate.Dieta;
import hibernate.Specifichedieta;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.List;

import antlr.Utils;
import command.DietaDAO;
import command.IngredienteDAO;
import command.PazienteDAO;
import service.GiornoDieta;
import service.StrutAlimento;
import service.StrutPasto;

import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.GridData;

public class InserisciDietaView extends ViewPart {
	
	private Label labelPaziente = null;  //  @jve:decl-index=0:visual-constraint="9,-39"
	private Text textPaziente = null;

	private Composite top = null;
	private Group groupDieta = null;
	private Text textConsigli = null;
	private Label lDataInizio = null;
	private Label lConsigli = null;
	private Label lNote = null;
	private Text textNote = null;
	private Label lTipoDieta = null;
	private Button bAddNuovaDietaSpec = null;
	private ArrayList<GiornoDieta> giorniDieta = null;  //  @jve:decl-index=0:
	private Shell shellIns = null;
	private MessageBox messageBox = null;  //  @jve:decl-index=0:
	private Shell shellInsertPasto = null;  //  @jve:decl-index=0:visual-constraint="1016,17"
	private Button button = null;
	private Text textShellIns = null;
	private Label lShellIns = null;
	private int nGiorni = 0;
	private Spinner spinCicli = null;
	private Label lNCicli = null;
	private final Font fontList = new Font(Display.getCurrent(),"Arial",10,SWT.BOLD);
	private DietaDAO dieta = new DietaDAO();  //  @jve:decl-index=0:
	//private ArrayList<StrutAlimento> alimentiDB = null;
	private Shell shellMsg = null;
	private Text textShellIns2;
	private Shell shellInsertAlimento = null;  //  @jve:decl-index=0:visual-constraint="1018,119"
	private Button bInsNewAlimento = null;
	private Text textShellInsAlimento = null;
	private Label lShellInsAlimento = null;
	private Label lShellInsAlimento1 = null;
	private Text textShellInsAlimento1 = null;
	private Label lShellInsAlimento3 = null;
	private Spinner spinCalorie = null;
	private Shell shellInsSchemaDietetico = null;  //  @jve:decl-index=0:visual-constraint="6,795"
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
	private Table tableAlimenti = null;
	private Table tableAlimenti1 = null;
	private Button bCreaSchema = null;
	private Table tableSchemiDiete = null;
	private Label lSelezSchema = null;
	private StyledText textVisSchema = null;
	private Button bAddDesc = null;
	private Button bAddNote = null;
	private Button bConfSchema = null;
	private ArrayList<StrutAlimento> arrAlimenti = null;  //  @jve:decl-index=0:
	private DateTime calendar = null;
	private Label lMod = null;
	private Button radioButton = null;
	private Button radioButton1 = null;
	private Table tableTipoDieta = null;
	private Button buttonInserisci = null;
	private Shell sShellSpecificheDieta = null;  //  @jve:decl-index=0:visual-constraint="6,1263"
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
	private Integer idTipoDieta = null;
	
	 public static final String VIEW_ID = "StudioDietetico.InserisciDietaView";
	
	public InserisciDietaView() {
		giorniDieta = new ArrayList<GiornoDieta>();
		//alimentiDB = dieta.getAlimentiObj();
	}

	@Override
	public void createPartControl(Composite parent) {
		top = new Composite(parent, SWT.NONE);
		
		//Visualizzazione del paziente selezionato
		labelPaziente = new Label(top, SWT.NONE);
		labelPaziente.setBounds(new Rectangle(14, 10, 82, 24));
		labelPaziente.setText("Paziente");
		textPaziente = new Text(top, SWT.BORDER);
		textPaziente.setBounds(new Rectangle(106, 10, 263, 31));
		textPaziente.setEnabled(false);
		textPaziente.setText(HomePazienteForm.getPazienteSelezionato().getCognome()+"   "+
				HomePazienteForm.getPazienteSelezionato().getNome()+"   "+
				HomePazienteForm.getPazienteSelezionato().getDataNascita());

		createGroupDieta();
	}

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub

	}

	/**
	 * This method initializes groupDieta	
	 *
	 */
	private void createGroupDieta() {
		
		groupDieta = new Group(top, SWT.NONE);
		groupDieta.setLayout(null);
		groupDieta.setText("Dieta");
		groupDieta.setBounds(new Rectangle(11, 50, 816, 517));
		textConsigli = new Text(groupDieta, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL);
		textConsigli.setBounds(new Rectangle(138, 416, 293, 57));
		lDataInizio = new Label(groupDieta, SWT.NONE);
		lDataInizio.setBounds(new Rectangle(16, 34, 73, 13));
		lDataInizio.setText("*Data inizio");
		calendar = new DateTime (groupDieta, SWT.NONE | SWT.CALENDAR | SWT.BORDER);
		calendar.setBounds(new Rectangle(18, 54, 165, 144));
		lConsigli = new Label(groupDieta, SWT.NONE);
		lConsigli.setBounds(new Rectangle(135, 396, 139, 13));
		lConsigli.setText("Consigli per il paziente");
		lNote = new Label(groupDieta, SWT.NONE);
		lNote.setBounds(new Rectangle(440, 401, 60, 13));
		lNote.setText("Note dieta");
		textNote = new Text(groupDieta, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL);
		textNote.setBounds(new Rectangle(442, 417, 347, 57));
		lTipoDieta = new Label(groupDieta, SWT.NONE);
		lTipoDieta.setBounds(new Rectangle(224, 40, 130, 13));
		lTipoDieta.setText("*Seleziona tipo dieta");
		bAddNuovaDietaSpec = new Button(groupDieta, SWT.NONE);
		bAddNuovaDietaSpec.setBounds(new Rectangle(673, 52, 71, 23));
		bAddNuovaDietaSpec.setText("Crea nuovo");
		bAddNuovaDietaSpec
				.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
					public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
						createSShellSpecificheDieta();
					}
				});
		spinCicli = new Spinner(groupDieta, SWT.NONE);
		spinCicli.setMaximum(50);
		spinCicli.setMinimum(1);
		Font fontSpin = new Font(Display.getCurrent(),"Arial",14,SWT.BOLD);
		spinCicli.setFont(fontSpin);
		spinCicli.setBounds(new Rectangle(20, 419, 51, 24));
		lNCicli = new Label(groupDieta, SWT.NONE);
		lNCicli.setBounds(new Rectangle(17, 395, 108, 13));
		lNCicli.setText("*Numero cicli schema");
		bCreaSchema = new Button(groupDieta, SWT.NONE);
		bCreaSchema.setBounds(new Rectangle(670, 234, 125, 23));
		bCreaSchema.setText("Crea nuovo / Conferma");
		tableSchemiDiete = new Table(groupDieta, SWT.NONE | SWT.FULL_SELECTION | SWT.V_SCROLL);
		tableSchemiDiete.setHeaderVisible(true);
		tableSchemiDiete.setLinesVisible(true);
		tableSchemiDiete.setBounds(new Rectangle(20, 230, 207, 149));
	
		final TableColumn columnId = new TableColumn(tableSchemiDiete, SWT.NONE);
		columnId.setText("Id Dieta");
		final TableColumn columnNumGiorni = new TableColumn(tableSchemiDiete, SWT.NONE);
		columnNumGiorni.setText("Numero giorni");
		final TableColumn columnNomePaz = new TableColumn(tableSchemiDiete, SWT.NONE);
		columnNomePaz.setText("Nome paziente");
		
		
		final TableColumn [] columns = tableSchemiDiete.getColumns ();
		aggiornaDiete();
		for (int i=0; i<columns.length; i++) columns[i].pack();

		
		
		tableSchemiDiete
				.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
					public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
						TableItem itemSel = tableSchemiDiete.getSelection()[0];
						int id = Integer.parseInt(itemSel.getText(0));
						ArrayList<GiornoDieta> arrGior = dieta.getSchemiDieta(id);
						String schema = "";
						for (GiornoDieta giorno : arrGior) {
							schema = schema.concat(giorno.getGiorno()+"\n");
							for (StrutPasto pasto : giorno.getPasti()) {
								schema = schema.concat(pasto.getNomePasto()+"\n");
								for (StrutAlimento alimento : pasto.getAlimenti()) {
									schema = schema.concat(alimento.getQuantita()+"\t"+alimento.getNomeAlimento()+"\t"+alimento.getTipologia()+"\n");
								}
							}
							schema = schema.concat("\n");
						}
						
						textVisSchema.setText(schema);
					}
				});
		
		
		
		
		lSelezSchema = new Label(groupDieta, SWT.NONE);
		lSelezSchema.setBounds(new Rectangle(17, 208, 155, 13));
		lSelezSchema.setText("*Seleziona schema dietetico");
		textVisSchema = new StyledText(groupDieta, SWT.NONE | SWT.V_SCROLL | SWT.READ_ONLY);
		textVisSchema.setBounds(new Rectangle(230, 231, 438, 149));
		textVisSchema.setEditable(false);
		tableTipoDieta = new Table(groupDieta, SWT.NONE | SWT.FULL_SELECTION | SWT.V_SCROLL);
		tableTipoDieta.setHeaderVisible(true);
		tableTipoDieta.setLinesVisible(true);
		tableTipoDieta.setBounds(new Rectangle(229, 54, 439, 137));
		tableTipoDieta
				.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
					

					public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
						TableItem itemSel = tableTipoDieta.getSelection()[0];
						idTipoDieta = Integer.parseInt(itemSel.getText(0));
					}
				});
		buttonInserisci = new Button(groupDieta, SWT.NONE);
		buttonInserisci.setBounds(new Rectangle(694, 483, 116, 32));
		buttonInserisci.setText("Inserisci");
		buttonInserisci
		.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				shellMsg = new Shell();
				boolean inserisci = true;
				if (Integer.parseInt(spinCicli.getText())==0 | tableTipoDieta.getSelectionCount()==0 | tableSchemiDiete.getSelectionCount()==0) {
					inserisci = false;
				}

				if (inserisci) {
					String data = calendar.getYear()+"-"+(calendar.getMonth()+1)+"-"+calendar.getDay();
					String formato = "yyyy-MM-dd";
					Date inizioDieta = new Date();
					inizioDieta = service.Utils.convertStringToDate(data, formato);
					Specifichedieta specifichedieta = dieta.getSpecificheDieta(Integer.parseInt(tableTipoDieta.getSelection()[0].getText(0)));
					ArrayList<GiornoDieta> arrGiorni = dieta.getSchemiDieta(Integer.parseInt(tableSchemiDiete.getSelection()[0].getText(0)));
					dieta.inserisciDieta(arrGiorni, HomePazienteForm.getPazienteSelezionato(), inizioDieta, textNote.getText(), arrGiorni.size(), Integer.parseInt(spinCicli.getText()),
							specifichedieta, textConsigli.getText());
				}else{
					messageBox = new MessageBox(shellMsg,
							SWT.OK |
							SWT.ICON_ERROR);
					messageBox.setMessage("Attenzione completare tutti i campi obbligatori (*)");	
					messageBox.open();		

				}


			}

		});
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
		
		
		final TableColumn [] columns3 = tableTipoDieta.getColumns ();
		aggiornaTipoDiete();
		for (int i=0; i<columns3.length; i++) columns3[i].pack();

		
		
		
		
		bCreaSchema.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				shellMsg = new Shell();
				boolean inserisci = true;
				if (Integer.parseInt(spinCicli.getText())==0 | tableTipoDieta.getSelectionCount()==0) {
					inserisci = false;
				}

				if (inserisci) {
					createShellInsSchemaDietetico();
				}else{
					messageBox = new MessageBox(shellMsg,
							SWT.OK |
							SWT.ICON_ERROR);
					messageBox.setMessage("Attenzione completare tutti i campi obbligatori (*)");	
					messageBox.open();		

				}
					
			}
		});
	
	}

	private void aggiornaTipoDiete() {
		TableItem itemSchema = null;
		//TODO
		ArrayList<Specifichedieta> tipoDiete = new ArrayList<Specifichedieta>();
		tipoDiete = dieta.getSpecificheDieta();
		for (Specifichedieta tipodieta : tipoDiete) {
			itemSchema = new TableItem(tableTipoDieta, SWT.NULL);
			itemSchema.setText(0, ""+tipodieta.getIdSpecificheDieta());
			itemSchema.setText(1, ""+tipodieta.getKilocalorie());
			itemSchema.setText(2, tipodieta.getContenutoPresente());
			itemSchema.setText(3, tipodieta.getContenutoAssente());

		}
		
	}

	private void aggiornaDiete() {
		TableItem itemSchema = null;
		//TODO
		ArrayList<Dieta> diete = new ArrayList<Dieta>();
		diete = dieta.getDiete();
		for (Dieta dieta : diete) {
			itemSchema = new TableItem(tableSchemiDiete, SWT.NULL);
			itemSchema.setText(0, ""+dieta.getIdDieta());
			itemSchema.setText(1, ""+dieta.getDurataCiclo());
			itemSchema.setText(2, ""+dieta.getNumRipetizCiclo());
			itemSchema.setText(3, dieta.getPaziente().getCognome()+" "+dieta.getPaziente().getNome());

		}
	}

	/**
	 * This method initializes groupSchemaDieta	
	 *
	 */

	
	private void aggiornaAlimenti() {
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
	 * This method initializes shellInsertAlimento	
	 *
	 */
	private void createShellInsertAlimento() {
		shellInsertAlimento = new Shell(Display.getCurrent(), SWT.APPLICATION_MODAL
				| SWT.SHELL_TRIM);
		shellInsertAlimento.setLayout(null);
		shellInsertAlimento.setText("Inserisci nuovo Alimento");
		shellInsertAlimento.setSize(new Point(330, 204));
		bInsNewAlimento = new Button(shellInsertAlimento, SWT.NONE);
		bInsNewAlimento.setBounds(new Rectangle(250, 143, 66, 23));
		bInsNewAlimento.setText("Inserisci");
		bInsNewAlimento
		.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				if(!textShellInsAlimento.getText().equals("")) {
					//StrutAlimento ali = new StrutAlimento(textShellInsAlimento.getText(), textShellInsAlimento1.getText(), spinCalorie.getDigits());
					dieta.insAlimento(textShellInsAlimento.getText(), textShellInsAlimento1.getText(), Integer.parseInt(spinCalorie.getText()));
					textShellInsAlimento.setText("");
					textShellInsAlimento1.setText("");
					spinCalorie.setDigits(0);
					//listAlimenti.setItems(dieta.getAlimenti());
					aggiornaAlimenti();
					//alimentiDB.add(ali);
					//shellInsertAlimento.close();
				}
			}
		});
		textShellInsAlimento = new Text(shellInsertAlimento, SWT.BORDER);
		textShellInsAlimento.setBounds(new Rectangle(17, 26, 218, 19));
		lShellInsAlimento = new Label(shellInsertAlimento, SWT.NONE);
		lShellInsAlimento.setBounds(new Rectangle(16, 9, 165, 13));
		lShellInsAlimento.setText("Inserisci nome alimento");
		lShellInsAlimento1 = new Label(shellInsertAlimento, SWT.NONE);
		lShellInsAlimento1.setBounds(new Rectangle(15, 58, 125, 13));
		lShellInsAlimento1.setText("Inserisci tipologia alimento");
		textShellInsAlimento1 = new Text(shellInsertAlimento, SWT.BORDER);
		textShellInsAlimento1.setBounds(new Rectangle(16, 73, 219, 19));
		lShellInsAlimento3 = new Label(shellInsertAlimento, SWT.NONE);
		lShellInsAlimento3.setBounds(new Rectangle(14, 103, 98, 13));
		lShellInsAlimento3.setText("Inserisci calorie");
		spinCalorie = new Spinner(shellInsertAlimento, SWT.NONE);
		spinCalorie.setBounds(new Rectangle(15, 122, 65, 18));
		shellInsertAlimento.open();
	}

	/**
	 * This method initializes shellInsSchemaDietetico	
	 *
	 */
	private void createShellInsSchemaDietetico() {
		shellInsSchemaDietetico = new Shell(Display.getCurrent(),
				SWT.APPLICATION_MODAL | SWT.SHELL_TRIM);
		shellInsSchemaDietetico.setLayout(null);
		
		createGroupSchemaDieta();
		shellInsSchemaDietetico.setText("Inserisci nuovo Schema Dietetico");
		shellInsSchemaDietetico.setSize(new Point(942, 462));
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
		groupSchemaDieta.setBounds(new Rectangle(-2, 11, 936, 412));
		tableAlimenti = new Table(groupSchemaDieta, SWT.CHECK | SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);
		tableAlimenti.setHeaderVisible(true);
		tableAlimenti.setLinesVisible(true);
		tableAlimenti.setBounds(new Rectangle(659, 45, 270, 180));
		tableAlimenti1 = new Table(groupSchemaDieta, SWT.CHECK | SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);
		tableAlimenti1.setHeaderVisible(true);
		tableAlimenti1.setLinesVisible(true);
		tableAlimenti1.setBounds(new Rectangle(318, 45, 270, 180));
		
		
		
		
		
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
		lDescrizione.setBounds(new Rectangle(11, 279, 88, 13));
		lDescrizione.setText("Descrizione");
		textDescrizione = new Text(groupSchemaDieta, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL);
		textDescrizione.setBounds(new Rectangle(12, 300, 377, 47));
		listGiorni = new List(groupSchemaDieta, SWT.NONE | SWT.V_SCROLL);
		listGiorni.setBounds(new Rectangle(13, 45, 135, 179));
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
		lGiorni.setBounds(new Rectangle(12, 27, 90, 13));
		lGiorni.setText("Seleziona giorno");
		listPasti = new List(groupSchemaDieta, SWT.NONE | SWT.MULTI | SWT.V_SCROLL);
		listPasti.setBounds(new Rectangle(160, 45, 135, 179));
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
		lPasti.setBounds(new Rectangle(159, 27, 93, 13));
		lPasti.setText("Seleziona Pasto");
		bAddGiorno = new Button(groupSchemaDieta, SWT.NONE);
		bAddGiorno.setBounds(new Rectangle(12, 228, 23, 23));
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
		bDelGiorno.setBounds(new Rectangle(36, 228, 23, 23));
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
		lAlimenti.setBounds(new Rectangle(659, 27, 75, 13));
		lAlimenti.setText("Lista alimenti");
		bAddAlimenti = new Button(groupSchemaDieta, SWT.NONE);
		bAddAlimenti.setBounds(new Rectangle(596, 67, 55, 23));
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
		bDelAlimento.setBounds(new Rectangle(319, 227, 23, 23));
		bDelAlimento.setText("-");
		bDelAlimento.setEnabled(false);
		lAlimentiPasto = new Label(groupSchemaDieta, SWT.NONE);
		lAlimentiPasto.setBounds(new Rectangle(317, 27, 74, 13));
		lAlimentiPasto.setText("Alimenti pasto");
		textQuant = new Text(groupSchemaDieta, SWT.BORDER);
		textQuant.setBounds(new Rectangle(596, 45, 54, 19));
		textQuant.setEnabled(false);
		lQuant = new Label(groupSchemaDieta, SWT.NONE);
		lQuant.setBounds(new Rectangle(597, 27, 49, 13));
		lQuant.setText("Quantità");
		textNoteSchema = new Text(groupSchemaDieta, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL);
		textNoteSchema.setBounds(new Rectangle(398, 301, 369, 47));
		lNoteSchema = new Label(groupSchemaDieta, SWT.NONE);
		lNoteSchema.setBounds(new Rectangle(397, 282, 123, 13));
		lNoteSchema.setText("Note schema dietetico");
		bAddPasto = new Button(groupSchemaDieta, SWT.NONE);
		bAddPasto.setBounds(new Rectangle(160, 228, 23, 23));
		bAddPasto.setText("+");
		bAddPasto.setEnabled(false);
		bAddPasto
		.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				createShellInsertPasto();
			}
		});
		bDelPasto = new Button(groupSchemaDieta, SWT.NONE);
		bDelPasto.setBounds(new Rectangle(185, 228, 23, 23));
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
		bAddNewAlim.setBounds(new Rectangle(660, 228, 23, 23));
		bAddNewAlim.setText("+");
		bAddNewAlim.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				createShellInsertAlimento();
			}
		});
		bDelNewAlim = new Button(groupSchemaDieta, SWT.NONE);
		bDelNewAlim.setBounds(new Rectangle(685, 228, 23, 23));
		bDelNewAlim.setText("-");
		bAddDesc = new Button(groupSchemaDieta, SWT.NONE);
		bAddDesc.setBounds(new Rectangle(14, 346, 112, 23));
		bAddDesc.setText("Aggiungi descrizione");
		bAddNote = new Button(groupSchemaDieta, SWT.NONE);
		bAddNote.setBounds(new Rectangle(398, 350, 109, 23));
		bAddNote.setText("Aggiungi nota");
		bAddNote.setEnabled(false);
		bConfSchema = new Button(groupSchemaDieta, SWT.NONE);
		bConfSchema.setBounds(new Rectangle(782, 375, 132, 29));
		bConfSchema.setText("Conferma");
		lMod = new Label(groupSchemaDieta, SWT.NONE);
		lMod.setBounds(new Rectangle(231, 243, 40, 13));
		lMod.setText("Modalità");
		radioButton = new Button(groupSchemaDieta, SWT.RADIO);
		radioButton.setBounds(new Rectangle(64, 247, 77, 16));
		radioButton.setText("Settimanale");
		radioButton.setSelection(true);
		radioButton1 = new Button(groupSchemaDieta, SWT.RADIO);
		radioButton1.setBounds(new Rectangle(64, 232, 90, 16));
		radioButton1.setText("Personalizzata");
		bConfSchema.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				//TODO INSERIRE CONTROLLI VARI PER DIETA
				//TODO passare al metodo inserisciDieta anche tutte le altre informazioni: n cicli ecc ecc
				String data = calendar.getYear()+"-"+(calendar.getMonth()+1)+"-"+calendar.getDay();
				String formato = "yyyy-MM-dd";
				Date inizioDieta = new Date();
				inizioDieta = service.Utils.convertStringToDate(data, formato);
				Specifichedieta specifichedieta = dieta.getSpecificheDieta(Integer.parseInt(tableTipoDieta.getSelection()[0].getText(0)));
				dieta.inserisciDieta(giorniDieta, HomePazienteForm.getPazienteSelezionato(), inizioDieta, textNote.getText(), giorniDieta.size(), Integer.parseInt(spinCicli.getText()),
						specifichedieta, textConsigli.getText());
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
	 * This method initializes sShellSpecificheDieta	
	 *
	 */
	private void createSShellSpecificheDieta() {
		sShellSpecificheDieta = new Shell(Display.getCurrent(),
				SWT.APPLICATION_MODAL | SWT.SHELL_TRIM);
		sShellSpecificheDieta.setLayout(null);
		createGroupSpecificheDieta();
		sShellSpecificheDieta.setSize(new Point(369, 395));
		sShellSpecificheDieta.setText("Inserisci nuova specifica dieta");
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
		labelKilocal.setText("Kilocalorie");
		labelKilocal.setBounds(new Rectangle(8, 280, 70, 13));
		spinKilocal = new Spinner(groupSpecificheDieta, SWT.NONE);
		spinKilocal.setFont(new Font(Display.getCurrent(), "Arial", 14, SWT.BOLD));
		spinKilocal.setMaximum(10000);
		spinKilocal.setMinimum(1);
		spinKilocal.setBounds(new Rectangle(9, 299, 49, 22));
		labelContAssente = new Label(groupSpecificheDieta, SWT.NONE);
		labelContAssente.setBounds(new Rectangle(8, 12, 144, 13));
		labelContAssente.setText("Contenuto assente");
		textContAssente = new Text(groupSpecificheDieta, SWT.BORDER | SWT.MULTI);
		textContAssente.setBounds(new Rectangle(8, 30, 319, 63));
		labelContPresente = new Label(groupSpecificheDieta, SWT.NONE);
		labelContPresente.setBounds(new Rectangle(8, 104, 148, 16));
		labelContPresente.setText("Contenuto presente");
		textContPresente = new Text(groupSpecificheDieta, SWT.BORDER | SWT.MULTI);
		textContPresente.setBounds(new Rectangle(9, 122, 319, 63));
		textIndicata = new Text(groupSpecificheDieta, SWT.BORDER | SWT.MULTI);
		textIndicata.setBounds(new Rectangle(9, 207, 319, 63));
		labelIndicata = new Label(groupSpecificheDieta, SWT.NONE);
		labelIndicata.setBounds(new Rectangle(9, 193, 97, 13));
		labelIndicata.setText("Indicazioni");
		buttonInsNewSpecDieta = new Button(groupSpecificheDieta, SWT.NONE);
		buttonInsNewSpecDieta.setBounds(new Rectangle(225, 295, 102, 23));
		buttonInsNewSpecDieta.setText("Inserisci");
		buttonInsNewSpecDieta
				.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
					public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
						dieta.inserisciSpecificheDieta(textContAssente.getText(), textContPresente.getText(), textIndicata.getText(), Integer.parseInt(spinKilocal.getText()));
						aggiornaTipoDiete();
					}
				});
	}




}  //  @jve:decl-index=0:visual-constraint="2,10,853,606"
