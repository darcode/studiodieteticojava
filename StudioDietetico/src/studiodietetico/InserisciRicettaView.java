package studiodietetico;

import hibernate.Alimento;
import hibernate.Composizione;
import hibernate.Ingrediente;
import hibernate.Ricetta;

import java.text.Collator;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.Button;
import command.DietaDAO;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Table;

import service.StrutAlimento;

public class InserisciRicettaView extends ViewPart {

	private Composite top = null;
	private Label lNomeRicetta = null;
	private Text textNome = null;
	private Label lIngredienti = null;
	private List listIngredienti = null;
	private Text textAddIngrediente = null;
	private Button bAddIngrediente = null;
	private Button bAddIngRic = null;
	private Button bDelIngRic = null;
	private Label lRicetta = null;
	private Shell shellMsg = null;
	private MessageBox messageBox = null;
	private Text textProcedimento = null;
	private Label lProcedimento = null;
	private Button bConferma = null;
	private Label lQuant = null;
	private Text textQuant = null;
	private Label lSelAlim = null;
	private static DietaDAO dieta = new DietaDAO();  //  @jve:decl-index=0:
	private static Table tableAlimenti = null;
	private Table tableIngRic = null;
	private Button bAddAlimento = null;
	private Table tableRicette = null;
	private Button buttonCreaNuovaRic = null;
	private Button buttonAnnulla = null;
	private Button buttonModificaRic = null;
	private Button buttonCancRic = null;
	private boolean modifica = false;
	public InserisciRicettaView() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void createPartControl(Composite parent) {
		top = new Composite(parent, SWT.NONE);
		
		lNomeRicetta = new Label(top, SWT.NONE);
        lNomeRicetta.setBounds(new Rectangle(19, 213, 74, 20));
        lNomeRicetta.setText("*Nome ricetta");
        textNome = new Text(top, SWT.BORDER);
        textNome.setBounds(new Rectangle(19, 235, 306, 19));
        lIngredienti = new Label(top, SWT.NONE);
        lIngredienti.setBounds(new Rectangle(395, 214, 138, 20));
        lIngredienti.setText("Lista ingredienti disponibili");
        listIngredienti = new List(top, SWT.V_SCROLL | SWT.BORDER);
        listIngredienti.setBounds(new Rectangle(395, 235, 317, 198));
        textAddIngrediente = new Text(top, SWT.BORDER);
        textAddIngrediente.setBounds(new Rectangle(395, 436, 195, 19));
     
       
        bAddIngrediente = new Button(top, SWT.NONE);
        bAddIngrediente.setBounds(new Rectangle(598, 436, 115, 20));
        bAddIngrediente.setText("Aggiungi ingrediente");
        bAddIngrediente
        		.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
        			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
        				shellMsg = new Shell();
        				boolean inserisci = true;
        				for (int i = 0; i < dieta.getIngredienti().length; i++) {
							if (textAddIngrediente.getText().equalsIgnoreCase(dieta.getIngredienti()[i])) {
								inserisci = false;
								break;
							}
						}
        				if (inserisci) {
        					messageBox = new MessageBox(shellMsg,
								    SWT.OK| SWT.CANCEL |
								    SWT.ICON_WARNING);
								 messageBox.setMessage("Inserire l'ingrediente: "+ textAddIngrediente.getText() + " ?" );	
								 if (messageBox.open() == SWT.OK) {
									 dieta.inserisciIngrediente(textAddIngrediente.getText());
			            			listIngredienti.setItems(dieta.getIngredienti());
								}
        					
						}else{
							
							 messageBox = new MessageBox(shellMsg,
								    SWT.OK| 
								    SWT.ICON_ERROR);
								 messageBox.setMessage("L'ingrediente è gia presente nella lista");
								 messageBox.open();		
							
						}
							
        				textAddIngrediente.setText("");
        			}
        		});
        bAddIngRic = new Button(top, SWT.NONE);
        bAddIngRic.setBounds(new Rectangle(334, 302, 49, 23));
        bAddIngRic.setText("<<");
        bAddIngRic.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
        	public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {    	
        		shellMsg = new Shell();
        		String[] ingRic =	confrontaArray(listIngredienti.getSelection(), tableIngRic.getItems());
        		if (textQuant.getText().equals("")) {
        			messageBox = new MessageBox(shellMsg,
						    SWT.OK | 
						    SWT.ICON_ERROR);
						 messageBox.setMessage("Inserire quantità");
						 messageBox.open();		
				}else{
        		for (int i = 0; i < ingRic.length; i++) {
        			TableItem itemIng = new TableItem(tableIngRic, SWT.NONE);
        			itemIng.setText(0, textQuant.getText());
        			itemIng.setText(1, ingRic[i]);
				}
        		textQuant.setText("");
				}
        	}

			private String[] confrontaArray(String[] ingredienti, TableItem[] ricetta) {
				Set<String> items = new HashSet<String>();
				ArrayList<String> ingDaInserire = new ArrayList<String>();
				for (int i = 0; i < ricetta.length; i++) {
					items.add(ricetta[i].getText(1));
					}
				for (int j = 0; j < ingredienti.length; j++) {
					if (!items.contains(ingredienti[j])) {
						ingDaInserire.add(ingredienti[j]);
					}
				}
				String[] ingRic = new String[ingDaInserire.size()];
				ingRic = ingDaInserire.toArray(ingRic);
				return ingRic;
			}
        });
        bDelIngRic = new Button(top, SWT.NONE);
        bDelIngRic.setBounds(new Rectangle(334, 330, 49, 23));
        bDelIngRic.setText(">>");
        bDelIngRic.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
        	public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
        		tableIngRic.remove(tableIngRic.getSelectionIndex());
        	}
        });
        lRicetta = new Label(top, SWT.NONE);
        lRicetta.setBounds(new Rectangle(19, 258, 122, 17));
        lRicetta.setText("*Ingredienti Ricetta");
        textProcedimento = new Text(top, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL);
        textProcedimento.setBounds(new Rectangle(18, 427, 307, 92));
        lProcedimento = new Label(top, SWT.NONE);
        lProcedimento.setBounds(new Rectangle(18, 409, 89, 13));
        lProcedimento.setText("*Procedimento");
        bConferma = new Button(top, SWT.NONE);
        bConferma.setBounds(new Rectangle(599, 488, 117, 41));
        bConferma.setText("Conferma");
        bConferma.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
        	public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {

        		
        		Ingrediente ing;
        		Ricetta ric = null;
        	
        		TableItem[] ingRic = tableIngRic.getItems();
        		Alimento ali = new Alimento();
        		if (tableAlimenti.getSelectionCount()==0 | textNome.getText().equals("") | 
        				textProcedimento.getText().equals("") | tableIngRic.getItemCount()==0) {
        			shellMsg = new Shell();
        			messageBox = new MessageBox(shellMsg,
        					SWT.OK | 
        					SWT.ICON_ERROR);
        			messageBox.setMessage("Completare tutti i campi obbligatori");
        			messageBox.open();		
        		}else{
        			
        				ali = dieta.getAlimento(Integer.parseInt(tableAlimenti.getSelection()[0].getText(3)));
        				if(!modifica){
        					
        					ric = dieta.inserisciNuovaRicetta(textNome.getText(), textProcedimento.getText(), ali);
        				}else{
        					int idRic = Integer.parseInt(tableRicette.getSelection()[0].getText(1));
        					dieta.cancellaComposizione(dieta.getRicetta(idRic));
        					ric = dieta.modificaRicetta(idRic, textNome.getText(), textProcedimento.getText(), ali);
            			}
        				for (int i = 0; i < ingRic.length; i++) {
        					ing = dieta.getIngrediente(ingRic[i].getText(1));
        					dieta.inserisciComposizione(ing, ingRic[i].getText(0), ric);
        				}
        				int idAlimento = Integer.parseInt(tableAlimenti.getSelection()[0].getText(3));
        				aggiornaRicette(idAlimento);
        			
        		}
        		modifica = false;
        	}
        });
        lQuant = new Label(top, SWT.NONE);
        lQuant.setBounds(new Rectangle(332, 258, 48, 16));
        lQuant.setText("Quantità");
        textQuant = new Text(top, SWT.BORDER);
        textQuant.setBounds(new Rectangle(334, 280, 54, 19));
        lSelAlim = new Label(top, SWT.NONE);
        lSelAlim.setBounds(new Rectangle(14, 8, 103, 13));
        lSelAlim.setText("*Seleziona Alimento");
        tableAlimenti = new Table(top, SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL | SWT.FULL_SELECTION);
        tableAlimenti.setHeaderVisible(true);
        tableAlimenti.setLinesVisible(true);
        tableAlimenti.setBounds(new Rectangle(16, 30, 417, 132));
        tableAlimenti
        		.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
        			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
        				int idAlimento = Integer.parseInt(tableAlimenti.getSelection()[0].getText(3));
        				aggiornaRicette(idAlimento);
        			}

				
        		});
        tableIngRic = new Table(top, SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL | SWT.FULL_SELECTION);
        tableIngRic.setHeaderVisible(true);
        tableIngRic.setLinesVisible(true);
        tableIngRic.setBounds(new Rectangle(19, 278, 303, 122));
        bAddAlimento = new Button(top, SWT.NONE);
        bAddAlimento.setBounds(new Rectangle(17, 164, 110, 23));
        bAddAlimento.setText("Aggiungi alimento");
        tableRicette = new Table(top,  SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL | SWT.FULL_SELECTION);
        tableRicette.setHeaderVisible(true);
        tableRicette.setLinesVisible(true);
        tableRicette.setBounds(new Rectangle(441, 30, 292, 131));
        tableRicette
        		.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
        			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
        				if (tableRicette.getSelectionCount()>0) {
							int idRicetta = Integer.parseInt(tableRicette.getSelection()[0].getText(1));
        					Ricetta ricetta = dieta.getRicetta(idRicetta);
							textNome.setText(ricetta.getNome());
							textProcedimento.setText(ricetta.getProcedimento());
							String[] ing = dieta.getIngredienti(ricetta);
							tableIngRic.removeAll();
							for (int j = 0; j < ing.length; j++) {
								TableItem item = new TableItem(tableIngRic, SWT.NONE);
								item.setText(0, ing[j].split("\t")[0]);
								item.setText(1, ing[j].split("\t")[1]);
							}
						}
        			}
        		});
        buttonCreaNuovaRic = new Button(top, SWT.NONE);
        buttonCreaNuovaRic.setBounds(new Rectangle(463, 162, 89, 23));
        buttonCreaNuovaRic.setText("Nuova Ricetta");
        buttonAnnulla = new Button(top, SWT.NONE);
        buttonAnnulla.setBounds(new Rectangle(491, 490, 106, 36));
        buttonAnnulla.setText("Annulla");
        buttonModificaRic = new Button(top, SWT.NONE);
        buttonModificaRic.setBounds(new Rectangle(553, 163, 89, 23));
        buttonModificaRic.setText("Modifica");
        buttonModificaRic
        		.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
        			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
        				modifica=true;
        				textAddIngrediente.setEnabled(true);
        				textAddIngrediente.setEnabled(true);
        				textProcedimento.setEnabled(true);
        				textQuant.setEnabled(true);
        				tableIngRic.setEnabled(true);
        				bAddIngrediente.setEnabled(true);
        				bConferma.setEnabled(true);
        				listIngredienti.setEnabled(true);
        				bAddIngRic.setEnabled(true);
        				bDelIngRic.setEnabled(true);
        				tableAlimenti.setEnabled(false);
        				tableRicette.setEnabled(false);
        				buttonAnnulla.setEnabled(false);
        			}
        		});
        buttonCancRic = new Button(top, SWT.NONE);
        buttonCancRic.setBounds(new Rectangle(644, 163, 89, 23));
        buttonCancRic.setText("Cancella");
        buttonAnnulla
        		.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
        			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
        				textAddIngrediente.setEnabled(false);
        				textAddIngrediente.setEnabled(false);
        				textProcedimento.setEnabled(false);
        				textQuant.setEnabled(false);
        				tableIngRic.setEnabled(false);
        				bAddIngrediente.setEnabled(false);
        				bConferma.setEnabled(false);
        				listIngredienti.setEnabled(false);
        				bAddIngRic.setEnabled(false);
        				bDelIngRic.setEnabled(false);
        				buttonCreaNuovaRic.setEnabled(false);
        				bAddAlimento.setEnabled(false);
        				tableAlimenti.setEnabled(true);
        				tableRicette.setEnabled(true);
        				buttonAnnulla.setEnabled(true);
        				buttonCreaNuovaRic.setEnabled(true);
        				bAddAlimento.setEnabled(true);
        			}
        		});
        buttonCreaNuovaRic
        		.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
        			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
        				textAddIngrediente.setEnabled(true);
        				textAddIngrediente.setEnabled(true);
        				textProcedimento.setEnabled(true);
        				textQuant.setEnabled(true);
        				tableIngRic.setEnabled(true);
        				bAddIngrediente.setEnabled(true);
        				bConferma.setEnabled(true);
        				listIngredienti.setEnabled(true);
        				bAddIngRic.setEnabled(true);
        				bDelIngRic.setEnabled(true);
        				tableAlimenti.setEnabled(false);
        				tableRicette.setEnabled(false);
        				buttonAnnulla.setEnabled(false);
        				}
        		});
        final TableColumn columnNomeRicetta = new TableColumn(tableRicette, SWT.NONE);
        columnNomeRicetta.setText("Nome ricetta");
        final TableColumn columnIdRicetta = new TableColumn(tableRicette, SWT.NONE);
        columnIdRicetta.setResizable(false);
        columnIdRicetta.setWidth(0);
		final TableColumn [] columns3 = tableRicette.getColumns ();
		for (int i=0; i<columns3.length-1; i++) columns3[i].pack();
        
        
        bAddAlimento
        		.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
        			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
        				InserisciAlimentoShell sInsAli = new InserisciAlimentoShell();
        				sInsAli.createShellInsertAlimento("ricetta");
        			}
        		});
        
        
        final TableColumn columnQuant = new TableColumn(tableIngRic, SWT.NONE);
        columnQuant.setText("Quantità");
		//columnNome.setWidth(300);
		final TableColumn columnNomeIng = new TableColumn(tableIngRic, SWT.NONE);
		columnNomeIng.setText("Ingrediente");
		columnNomeIng.setWidth(200);
		final TableColumn [] columns = tableIngRic.getColumns ();
		for (int i=0; i<columns.length-1; i++) columns[i].pack();
	       
  
		final TableColumn columnNome = new TableColumn(tableAlimenti, SWT.NONE);
		columnNome.setText("Nome alimento");
		columnNome.setWidth(300);
		final TableColumn columnTipologia = new TableColumn(tableAlimenti, SWT.NONE);
		columnTipologia.setText("Tipologia");
		columnTipologia.setWidth(200);
		final TableColumn columnCalorie = new TableColumn(tableAlimenti, SWT.NONE);
		columnCalorie.setText("Calorie");
		final TableColumn columnIdAlimento = new TableColumn(tableAlimenti, SWT.NONE);
		columnIdAlimento.setResizable(false);
		columnIdAlimento.setWidth(0);
		
		
		final TableColumn [] columns2 = tableAlimenti.getColumns ();
		aggiornaAlimenti();
		for (int i=2; i<columns2.length-1; i++) columns2[i].pack();
       
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
		
        
        listIngredienti.setItems(dieta.getIngredienti());
        textAddIngrediente.setEnabled(false);
		textAddIngrediente.setEnabled(false);
		textProcedimento.setEnabled(false);
		textQuant.setEnabled(false);
		tableIngRic.setEnabled(false);
		bAddIngrediente.setEnabled(false);
		bConferma.setEnabled(false);
		listIngredienti.setEnabled(false);
		bAddIngRic.setEnabled(false);
		bDelIngRic.setEnabled(false);
		buttonAnnulla.setEnabled(false);
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
	
	private void aggiornaRicette(int idAlimento) {
		tableRicette.removeAll();
		Alimento ali = new Alimento();
		ali = dieta.getAlimento(idAlimento);
		Ricetta[] ricette = dieta.getRicette(ali);
		if (ricette.length!=0 || ricette!=null) {
			for (int i = 0; i < ricette.length; i++) {
				TableItem itemRicetta = new TableItem(tableRicette, SWT.NONE);
				itemRicetta.setText(0, ricette[i].getNome());
				itemRicetta.setText(1, ""+ricette[i].getIdRicetta());
			}
			for (int i=0; i<tableRicette.getColumns().length; i++) tableRicette.getColumns()[i].pack();
		
				
		}
					
	}
	@Override
	public void setFocus() {
		// TODO Auto-generated method stub

	}

}  //  @jve:decl-index=0:visual-constraint="10,10,745,535"
