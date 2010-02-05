package studiodietetico;

import hibernate.Intervento;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import org.eclipse.swt.widgets.TableColumn;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.TableItem;

import common.ui.ListComposite;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.graphics.Rectangle;

import service.Utils;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.widgets.Text;


public class ProvaTableForm extends ListComposite {

	private Composite top;
	private Label labelSelPaz;
	public Table tableVisualizzazione = null;
	public Button buttonInsert;
	public Button buttonElimina;
	private TableItem riga;
	private String idShellVisualizzaDettagli="";  //  @jve:decl-index=0:
	private CCombo cComboColonne = null;
	private Label labelRicerca;
	private Text textRicerca = null;
	private Set<String> setAttributi = new HashSet<String>();  //  @jve:decl-index=0:
	//private final int index=0, direction=0;

	public void setIdShellVisualizzaDettagli(String idShellVisualizzaDettagli) {
		this.idShellVisualizzaDettagli = idShellVisualizzaDettagli;
	}

	public ProvaTableForm(Composite parent, int style, ArrayList<Object> listaElementi) {
		super(parent, style);
		initialize(listaElementi);
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize(ArrayList<Object> listaElementi) {
        
        
        GridLayout gridLayout = new GridLayout(4, true);
        gridLayout.numColumns = 4;
        
        GridData gridDataLabel = new GridData();
        gridDataLabel.horizontalSpan = 4;
        GridData gridDataButton = new GridData();
        gridDataButton.horizontalSpan = 1;
        GridData gridDataTable = new GridData();
        gridDataTable.horizontalSpan = 4;
        GridData gridDataRic = new GridData();
        gridDataRic.horizontalSpan = 1;
        
        this.setSize(new Point(800, 800));
        
        top = new Composite(this, SWT.BORDER);
        top.setLocation(new Point(0, 0));
        top.setLayout(gridLayout);
        top.setSize(new Point(800, 800));
        
        labelSelPaz = new Label(top, SWT.NONE);
		labelSelPaz.setText("Selezionare un elemento e scegliere una opzione");
		labelSelPaz.setLayoutData(gridDataLabel);
		labelSelPaz.setSize(new Point(265, 15));
		
		labelRicerca = new Label(top, SWT.NONE);
		labelRicerca.setText("Ricerca");
		labelRicerca.setLayoutData(gridDataRic);
		
		cComboColonne = new CCombo(top, SWT.READ_ONLY);
		cComboColonne.setLayoutData(gridDataRic);
		
		textRicerca = new Text(top, SWT.BORDER);
		textRicerca.setLayoutData(gridDataRic);
		//textRicerca.setSize(200, 20);
		
		
		tableVisualizzazione = new Table(top, SWT.FILL | SWT.BORDER | SWT.FULL_SELECTION | SWT.V_SCROLL | SWT.MULTI);
		tableVisualizzazione.setHeaderVisible(true);
		tableVisualizzazione.setLinesVisible(true);
		tableVisualizzazione.setLayout(new GridLayout(1, true));
		tableVisualizzazione.setLayoutData(gridDataTable);
		tableVisualizzazione
				.addMouseListener(new org.eclipse.swt.events.MouseAdapter() {
					public void mouseDoubleClick(org.eclipse.swt.events.MouseEvent e) {
						 //for (int i = 0; i < tableVisualizzazione.getSelectionCount(); i++) {
							 riga = tableVisualizzazione.getSelection()[0];
							 //System.out.println("Riga tabella: "+riga.getText());
							 Utils.showView(idShellVisualizzaDettagli);
						//}
					}
				});
		
		
		
		
		
		
		
		
		
		/*Listener sortListener = new Listener() {
			public void handleEvent(Event e) {
	        // determine new sort column and direction
	        TableColumn sortColumn = tableVisualizzazione.getSortColumn();
System.out.println("sortCol: "+sortColumn.getText());
	        TableColumn currentColumn = (TableColumn) e.widget;
	        int dir = tableVisualizzazione.getSortDirection();
	        if (sortColumn == currentColumn) {
	          dir = dir == SWT.UP ? SWT.DOWN : SWT.UP;
	        } else {
	        	tableVisualizzazione.setSortColumn(currentColumn);
	          dir = SWT.UP;
	        }
	        // sort the data based on column and direction
	        for (int i = 0; i < tableVisualizzazione.getColumnCount(); i++) {
	        	index = i;
	        	direction = dir;
	        	
			}
	        
	        
	        Arrays.sort(data, new Comparator() {
	          public int compare(Object arg0, Object arg1) {
	            int[] a = (int[]) arg0;
	            int[] b = (int[]) arg1;
	            if (a[index] == b[index])
	              return 0;
	            if (direction == SWT.UP) {
	              return a[index] < b[index] ? -1 : 1;
	            }
	            return a[index] < b[index] ? 1 : -1;
	          }
	        });
	        // update data displayed in table
	        tableVisualizzazione.setSortDirection(dir);
	        tableVisualizzazione.clearAll();
		  }
	    };*/
	    //column1.addListener(SWT.Selection, sortListener);*/

		
		
		
		
		
		buttonInsert = new Button(top, SWT.NONE);
		buttonInsert.setText("Inserisci nuovo");
		buttonInsert.setLayoutData(gridDataButton);
		
		
		buttonElimina = new Button(top, SWT.NONE);
		buttonElimina.setText("Elimina");
		buttonElimina.setLayoutData(gridDataButton);
		
		
		riempiTabellaEntita(tableVisualizzazione, listaElementi);
		for (TableColumn colonna : tableVisualizzazione.getColumns()) {
			colonna.pack();
			colonna.setResizable(false);
		}
		//nasconde la prima colonna che contiene l'id
		tableVisualizzazione.getColumn(0).setWidth(0);

		/*buttonInsert.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(
					org.eclipse.swt.events.SelectionEvent e) {
				//Utils.showView(RegistraPazienteView.VIEW_ID);
			}
		});
		buttonModifica.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(
					org.eclipse.swt.events.SelectionEvent e) {
				//Utils.showView(RegistraPazienteView.VIEW_ID);
			}
		});*/
		
		ordinamentoItem(tableVisualizzazione);
		

///////////////////////////////////ordinamento///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		/*for(int col = 0; col < tableVisualizzazione.getColumnCount(); col++){
	    	final int currentColumn= col;
	    	TableColumn column = tableVisualizzazione.getColumn(col);
		column.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
					
				TableItem[] items = tableVisualizzazione.getItems();
					Collator collator = Collator.getInstance(Locale.getDefault());
					for (int i = 1; i < items.length; i++) {
						String value1 = items[i].getText(currentColumn);
						for (int j = 0; j < i; j++){
							String value2 = items[j].getText(currentColumn);
							if (collator.compare(value1, value2) < 0) {
								String[] values = {
									items[i].getText(0), 
									items[i].getText(1), 
									items[i].getText(2),
									items[i].getText(3)};
								Object data = items[i].getData();
								items[i].dispose();
								TableItem item = new TableItem(tableVisualizzazione, SWT.NONE, j);
								item.setText(values);
								item.setData(data);
								break;
							}
						}
					}
				}
			});		      
		}*/
		
		
/////////////////////////////ordLor///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
	    
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	    
	    
	    
	    
	    
}
	
	public void ordinamentoItem(final Table tableVis) {
		Listener sortListener = new Listener() {
	        public void handleEvent(Event e) {
	            TableItem[] items = tableVis.getItems();
	            Collator collator = Collator.getInstance(Locale.getDefault());
	            TableColumn column = (TableColumn)e.widget;
	            int index = 4; //provo sulla seconda colonna
	            
	            /*if(column == columnNome){
	            	index = 0;
	            }else if (column == columnTipologia) {
	            	index = 1;
				}else if (column == columnCalorie) {
					index = 2;
				}else if (column == columnIdAlimento) {
					index = 3;
				}*/
	            String value1 = null;
	            String value2 = null;
	            int valueInt1 = 0;
	            int valueInt2 = 0;
	            for (int i = 1; i < items.length; i++) {
	                //if((index==0)||(index==1)) { //per le string
	                	/*value1 = items[i].getText(index);
	                	for (int j = 0; j < i; j++){
		                    value2 = items[j].getText(index);
		                    if (collator.compare(value1, value2) < 0) {
		                        String[] values = {items[i].getText(0), items[i].getText(1), items[i].getText(2)};
		                        items[i].dispose();
		                        TableItem item = new TableItem(tableVisualizzazione, SWT.NONE, j);
		                        item.setText(values);
		                        items = tableVisualizzazione.getItems();
		                        break;
		                    }
		                }*/
	              //  }else{ //per i numerici
	            	System.out.println("item[i] "+items[i].getText(index));
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
			                        String[] values = {items[i].getText(0), items[i].getText(1), items[i].getText(2)
			                        		, items[i].getText(3), items[i].getText(4), items[i].getText(5)};
			                        items[i].dispose();
			                        TableItem item = new TableItem(tableVis, SWT.NONE, j);
			                        item.setText(values);
			                        items = tableVis.getItems();
			                        break;
			                    }
			                }
	                }
	                	
	              
	            //}
	            tableVis.setSortColumn(column);
	        }
	    };
	    tableVis.getColumn(4).addListener(SWT.Selection, sortListener);
	    tableVis.getColumn(3).addListener(SWT.Selection, sortListener);
	    for (int i = 0; i <tableVisualizzazione.getColumnCount(); i++) {
	    	tableVis.setSortColumn(tableVisualizzazione.getColumn(i));
		    if (tableVis.getSortDirection()== SWT.UP) {
		    	tableVis.setSortDirection(SWT.DOWN);
			} else
				tableVis.setSortDirection(SWT.UP);
		}
	}
	
	
	
	public void aggiornaCombo() {
		for (TableColumn colonna : tableVisualizzazione.getColumns()) {
			if (colonna.getWidth()!=0 /*&& !setAttributi.contains(colonna.getText())*/) {
				cComboColonne.add(colonna.getText());
			}
		}
		cComboColonne.setText("Seleziona l'attributo");
		cComboColonne.pack();
	}

	public TableItem getRiga() {
		return riga;
	}

	public void setRiga(TableItem riga) {
		this.riga = riga;
	}
}  //  @jve:decl-index=0:visual-constraint="10,10"
