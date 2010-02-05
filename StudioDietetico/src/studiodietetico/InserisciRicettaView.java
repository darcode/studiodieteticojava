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

import command.ComposizioneDAO;
import command.DietaDAO;
import command.IngredienteDAO;
import command.RicettaDAO;
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
	private Button bRicetta = null;
	private Label lQuant = null;
	private Text textQuant = null;
	private Label lSelAlim = null;
	private DietaDAO dieta = new DietaDAO();  //  @jve:decl-index=0:
	private Table tableAlimenti = null;
	private Table tableIngRic = null;

	public InserisciRicettaView() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void createPartControl(Composite parent) {
		top = new Composite(parent, SWT.NONE);
        lNomeRicetta = new Label(top, SWT.NONE);
        lNomeRicetta.setBounds(new Rectangle(13, 157, 74, 20));
        lNomeRicetta.setText("Nome ricetta");
        textNome = new Text(top, SWT.BORDER);
        textNome.setBounds(new Rectangle(13, 179, 306, 19));
        lIngredienti = new Label(top, SWT.NONE);
        lIngredienti.setBounds(new Rectangle(389, 158, 55, 20));
        lIngredienti.setText("Ingredienti");
        listIngredienti = new List(top, SWT.V_SCROLL | SWT.BORDER);
        listIngredienti.setBounds(new Rectangle(389, 179, 317, 198));
        textAddIngrediente = new Text(top, SWT.BORDER);
        textAddIngrediente.setBounds(new Rectangle(389, 380, 195, 19));
     
       
        bAddIngrediente = new Button(top, SWT.NONE);
        bAddIngrediente.setBounds(new Rectangle(592, 380, 115, 20));
        bAddIngrediente.setText("Aggiungi ingrediente");
        bAddIngrediente
        		.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
        			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
        				shellMsg = new Shell();
        				IngredienteDAO ingDAO = new IngredienteDAO();
        				boolean inserisci = true;
        				for (int i = 0; i < ingDAO.getIngredienti().length; i++) {
							if (textAddIngrediente.getText().equalsIgnoreCase(ingDAO.getIngredienti()[i])) {
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
									 ingDAO.inserisciIngrediente(textAddIngrediente.getText());
			            			listIngredienti.setItems(IngredienteDAO.getIngredienti());
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
        bAddIngRic.setBounds(new Rectangle(328, 246, 49, 23));
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
        bDelIngRic.setBounds(new Rectangle(328, 274, 49, 23));
        bDelIngRic.setText(">>");
        bDelIngRic.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
        	public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
        		System.out.println("widgetSelected()"); // TODO Auto-generated Event stub widgetSelected()
        		tableIngRic.remove(tableIngRic.getSelectionIndex());
        	}
        });
        lRicetta = new Label(top, SWT.NONE);
        lRicetta.setBounds(new Rectangle(13, 202, 122, 17));
        lRicetta.setText("Ingredienti Ricetta");
        textProcedimento = new Text(top, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL);
        textProcedimento.setBounds(new Rectangle(12, 371, 307, 92));
        lProcedimento = new Label(top, SWT.NONE);
        lProcedimento.setBounds(new Rectangle(12, 353, 89, 13));
        lProcedimento.setText("Procedimento");
        bRicetta = new Button(top, SWT.NONE);
        bRicetta.setBounds(new Rectangle(593, 432, 117, 41));
        bRicetta.setText("Inserisci ricetta");
        bRicetta.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
        	public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
        		RicettaDAO ricD = new RicettaDAO();
        		IngredienteDAO ingredienteD = new IngredienteDAO();
        		Ingrediente ing;
        		Ricetta ric;
        		ComposizioneDAO compD = new ComposizioneDAO();
        		String[] ingRic = listIngredienti.getItems();
        		Alimento ali = new Alimento();
        		ali = dieta.getAlimento(Integer.parseInt(tableAlimenti.getSelection()[0].getText(3)));
        		for (int i = 0; i < ingRic.length; i++) {
        			ric = ricD.inserisciNuovaRicetta(textNome.getText(), textProcedimento.getText(), ali);
        			ing = ingredienteD.getIngrediente(ingRic[i].split("     ")[1]);
        			compD.inserisciComposizione(ing, ingRic[i].split("     ")[0], ric);
				}
        		
        	}
        });
        lQuant = new Label(top, SWT.NONE);
        lQuant.setBounds(new Rectangle(326, 202, 48, 16));
        lQuant.setText("Quantità");
        textQuant = new Text(top, SWT.BORDER);
        textQuant.setBounds(new Rectangle(328, 224, 54, 19));
        lSelAlim = new Label(top, SWT.NONE);
        lSelAlim.setBounds(new Rectangle(14, 8, 88, 13));
        lSelAlim.setText("Seleziona Alimento");
        tableAlimenti = new Table(top, SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL | SWT.FULL_SELECTION);
        tableAlimenti.setHeaderVisible(true);
        tableAlimenti.setLinesVisible(true);
        tableAlimenti.setBounds(new Rectangle(16, 30, 688, 114));
        tableIngRic = new Table(top, SWT.NONE);
        tableIngRic.setHeaderVisible(true);
        tableIngRic.setLinesVisible(true);
        tableIngRic.setBounds(new Rectangle(13, 222, 303, 122));
        
        
        final TableColumn columnQuant = new TableColumn(tableIngRic, SWT.NONE);
        columnQuant.setText("Quantità");
		//columnNome.setWidth(300);
		final TableColumn columnNomeIng = new TableColumn(tableIngRic, SWT.NONE);
		columnNomeIng.setText("Ingrediente");
		final TableColumn [] columns = tableIngRic.getColumns ();
	
  
		final TableColumn columnNome = new TableColumn(tableAlimenti, SWT.NONE);
		columnNome.setText("Nome alimento");
		columnNome.setWidth(300);
		final TableColumn columnTipologia = new TableColumn(tableAlimenti, SWT.NONE);
		columnTipologia.setText("Tipologia");
		columnTipologia.setWidth(200);
		final TableColumn columnCalorie = new TableColumn(tableAlimenti, SWT.NONE);
		columnCalorie.setText("Calorie");
		final TableColumn columnIdAlimento = new TableColumn(tableAlimenti, SWT.NONE);
		columnIdAlimento.setText("Id Alimento");
		
		
		final TableColumn [] columns2 = tableAlimenti.getColumns ();
		aggiornaAlimenti();
		for (int i=2; i<columns2.length; i++) columns2[i].pack();
       
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
		
        
        listIngredienti.setItems(IngredienteDAO.getIngredienti());
	}
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
	@Override
	public void setFocus() {
		// TODO Auto-generated method stub

	}

}  //  @jve:decl-index=0:visual-constraint="10,10,739,485"
