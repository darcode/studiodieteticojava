package studiodietetico;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;

import service.Costanti;
import service.DynNode;

import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Label;

import command.DynamicQueryDAO;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.graphics.Point;

public class DynamicQueryView extends ViewPart{

	private Composite top = null;
	private Tree tree = null;
	private Combo comboSelezioneEntita = null;
	private Label labelSelezioneEntita = null;
	private HashSet<String> nodiVisitati = new HashSet<String>();
	private Button button = null;
	private Table table = null;
	private Shell sShell = null;  //  @jve:decl-index=0:visual-constraint="398,602"
	private Button buttonOk = null;
	private Label etichetta = null;
	private Text text = null;
	private Label nomeAttributo = null;
	private Button buttonCancella = null;
	DynamicQueryDAO dynDao;
	private Shell sShell1 = null;  //  @jve:decl-index=0:visual-constraint="760,595"
	private Label label2 = null;
	private Button ok = null;
	private HashMap<String, DynNode> dynAlbero = new HashMap<String, DynNode>();

	public DynamicQueryView() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void createPartControl(Composite parent) {
        top = new Composite(parent, SWT.NONE);        
        tree = new Tree(top, SWT.CHECK | SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
        tree.setLayout(new FillLayout());
        tree.setHeaderVisible(true);        
        tree.setBounds(new Rectangle(0, 52, 469, 415));
        tree.addMouseListener(new org.eclipse.swt.events.MouseAdapter() {
        	public void mouseDoubleClick(org.eclipse.swt.events.MouseEvent e) {        		
        		//check se � selezionato e se � foglia
        		TreeItem[] arr = tree.getSelection();        		
        		if (arr[0] != null) {
        			TreeItem item = arr[0];
        			if (!item.getText().substring(0, 1).equals(item.getText().substring(0, 1).toUpperCase())) {
//        				si becca l'albero a ritroso
//        				ArrayList<TreeItem> ramo = new ArrayList<TreeItem>();
//        				TreeItem currTreeItem = arr[0];
//        				while (currTreeItem!=null) {
//        					ramo.add(currTreeItem);
//        					currTreeItem = currTreeItem.getParentItem();
//						}      
        				String key = "";
        				if (item.getParentItem()!=null) {
							key = item.getParentItem().getText().concat("_").concat(item.getText()).toUpperCase();
						} else {
							key = "radiceAlbero".concat("_").concat(item.getText()).toUpperCase();
						}        				
        				DynNode currentNode = dynAlbero.get(key);        				
        				
                		createSShell(currentNode);
                		sShell.open();
					}
				}
        	}
        });
        tree.addListener (SWT.Selection, new Listener () {
    		public void handleEvent (Event event) {
    			if (event.detail == SWT.CHECK) {
    				TreeItem[] arr = tree.getSelection();   
    				boolean b = false;
            		if (arr[0] != null) {
            			TreeItem item = arr[0];            			
            			if (item.getText().substring(0, 1).equals(item.getText().substring(0, 1).toUpperCase())) {
    						TreeItem[] figli = item.getItems();
    						for (int i = 0; i < figli.length; i++) {
    							if (!figli[i].getText().substring(0, 1).equals(figli[i].getText().substring(0, 1).toUpperCase())) {
    								b = true;
    								if (item.getChecked()) {
    									figli[i].setChecked(true);
									} else {
										figli[i].setChecked(false);
									}    								
    							}								
							}
						} else {
							b = true;
						}
    				}
            		if(!b){
            			createSShell1();
            			sShell1.open();            			
            			arr[0].setChecked(false);
            		}
    				
				}
    		}
    	});
        createComboSelezioneEntita();
        labelSelezioneEntita = new Label(top, SWT.NONE);
        labelSelezioneEntita.setBounds(new Rectangle(-1, 5, 278, 13));
        labelSelezioneEntita.setText("Selezionare il contesto da cui partire:");
        button = new Button(top, SWT.NONE);
        button.setBounds(new Rectangle(445, 5, 44, 27));
        button.setText("Vai");
        table = new Table(top, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
        table.setHeaderVisible(true);
        table.setLinesVisible(true);
        table.setBounds(new Rectangle(487, 53, 582, 416));
        TableColumn tableColumn = new TableColumn(table, SWT.NONE);
        tableColumn.setWidth(60);
        TableColumn tableColumn1 = new TableColumn(table, SWT.NONE);
        tableColumn1.setWidth(60);
        TableColumn tableColumn2 = new TableColumn(table, SWT.NONE);
        tableColumn2.setWidth(60);
        TableColumn tableColumn3 = new TableColumn(table, SWT.NONE);
        tableColumn3.setWidth(60);
        TreeColumn colFiltro = new TreeColumn(tree, SWT.CENTER);
        colFiltro.setText("Filtro");
        colFiltro.setWidth(200);
        TreeColumn colValore = new TreeColumn(tree, SWT.CENTER);
        colValore.setText("Valore");
        colValore.setWidth(200);
	}

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub
	}
	/**
	 * This method initializes comboSelezioneEntita	
	 *
	 */
	private void createComboSelezioneEntita() {
		comboSelezioneEntita = new Combo(top, SWT.NONE);
		comboSelezioneEntita.setBounds(new Rectangle(295, 4, 132, 21));
		for(int i=0; i<Costanti.entita.length; i++){
			comboSelezioneEntita.add(Costanti.entita[i][0]);			
		}
		comboSelezioneEntita
				.addSelectionListener(new org.eclipse.swt.events.SelectionListener() {
					public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {						
						//trova il nome della classe dato il testo della combolist e lo mette nell'albero
						int index = comboSelezioneEntita.getSelectionIndex();
						String nomeClasse = Costanti.entita[index][0];
						String pathClasse = Costanti.entita[index][1];
						//inserisco il nodo radice (nome della classe/entit� selezionata)
						tree.removeAll();
					    TreeItem radice = new TreeItem(tree, SWT.NONE);
						radice.setText(new String[] {nomeClasse});
						nodiVisitati.clear();	
									
						espandiAlbero(nomeClasse, pathClasse, radice);
						comboSelezioneEntita.setEnabled(false);
						
//						dynDao = new DynamicQueryDAO(pathClasse, nomeClasse);						
//						dynDao.checkSelezionato(ramo, b)
//						dynDao.executeDynQuery(filtroQuery);
					}
					public void widgetDefaultSelected(
							org.eclipse.swt.events.SelectionEvent e) {
					}
				});
	}

	/**
	 * This method initializes sShell	
	 *
	 */
	private void createSShell(final DynNode item) {
		
		
//		
//		if (item.getTreeNode().getText().startsWith("id")) {
//			// do nothing
//		} else if (item.getPathClass().isPrimitive()) {
//			String prim = current.getType().toString();
//			if (prim.equals("char")) {
//										
//			} else if (prim.equals("int")) {
//				
//			} else if (prim.equals("double")) {
//				
//			}										
//		} else if (current.getType().equals(java.lang.Boolean.class)) {
//			
//		} else if (current.getType().equals(java.lang.Double.class)) {
//			
//		} else if (current.getType().isInstance(new String())) {
//			
//		} else if (current.getType().isInstance(new Date())) {
//			
//		} else if (current.getType().equals(java.lang.Integer.class)) {
//			
//		}
		
		
		
		
		
		
		
		
		
		
		
		
		sShell = new Shell();
		sShell.setSize(new Point(290, 167));
		buttonOk = new Button(sShell, SWT.NONE);
		buttonOk.setText("Ok");
		buttonOk.setBounds(new Rectangle(139, 109, 106, 27));
		etichetta = new Label(sShell, SWT.NONE);
		etichetta.setBounds(new Rectangle(87, 9, 117, 34));
		etichetta.setText("Inserisci il valore");
		nomeAttributo = new Label(sShell, SWT.NONE);
		nomeAttributo.setBounds(new Rectangle(17, 59, 121, 22));
		nomeAttributo.setText(item.getTreeNode().getText());
		buttonCancella = new Button(sShell, SWT.NONE);
		buttonCancella.setBounds(new Rectangle(43, 107, 90, 27));
		buttonCancella.setText("Cancella");
		buttonCancella.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
					public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
						item.getTreeNode().setText(new String[] {item.getTreeNode().getText(), ""});						
						sShell.close();
					}
				});
		buttonOk.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				item.getTreeNode().setText(new String[] {item.getTreeNode().getText(),text.getText()});
				sShell.close();
			}
		});
		
		
		//if a cascata
		
		text = new Text(sShell, SWT.BORDER);
		text.setBounds(new Rectangle(152, 56, 113, 25));
		
		
	}

	/**
	 * This method initializes sShell1	
	 *
	 */
	private void createSShell1() {
		sShell1 = new Shell();
		sShell1.setLayout(null);
		sShell1.setText("Attenzione");
		sShell1.setSize(new Point(233, 143));
		label2 = new Label(sShell1, SWT.NONE | SWT.WRAP);
		label2.setBounds(new Rectangle(50, 30, 143, 32));
		label2.setText("Non ci sono elementi per il contesto selezionato");
		ok = new Button(sShell1, SWT.NONE);
		ok.setBounds(new Rectangle(98, 84, 36, 27));
		ok.setText("Ok");
		ok.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				sShell1.close();
			}
		});
	}
	
	public void espandiAlbero(String nomeClasse, String pathClasse, TreeItem radice/*, HashSet<String> nodiVisitati, Tree inizioAlbero, HashMap dynAlbero*/) {
		// istanzia classe dinamicamente
		Class classSelected = null;
		try {
			classSelected = Class.forName(pathClasse);
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if (!nodiVisitati.contains(pathClasse)) {
			nodiVisitati.add(pathClasse);
			// Recupero la lista dei Campi della Classe
			Field campi[] = classSelected.getDeclaredFields();
			ArrayList<Field> fieldClasse = new ArrayList<Field>();
			for (int i = 0; i < campi.length; i++) {
				fieldClasse.add(campi[i]);
			}
			while (fieldClasse.size() > 0) {
				Field currField = fieldClasse.get(0);
				// Passo Base
				if (currField.getName().startsWith("id")) {
					// do nothing
				} else if (currField.getType().isPrimitive()) {
					String prim = currField.getType().toString();
					
					TreeItem sottoRadice = new TreeItem(radice, SWT.NONE);
					sottoRadice.setText(currField.getName());
					DynNode figlio = new DynNode(sottoRadice);
					figlio.setPathClass(currField.getType().getCanonicalName());
					dynAlbero.put(figlio.getIdMap(), figlio);
					
					if (prim.equals("char")) {
												
					} else if (prim.equals("int")) {
						
					} else if (prim.equals("double")) {
						
					}										
				} else if (currField.getType().equals(java.lang.Boolean.class)) {
					TreeItem sottoRadice = new TreeItem(radice, SWT.NONE);
					sottoRadice.setText(currField.getName());
					DynNode figlio = new DynNode(sottoRadice);
					figlio.setPathClass(currField.getType().getCanonicalName());
					dynAlbero.put(figlio.getIdMap(), figlio);
				} else if (currField.getType().equals(java.lang.Double.class)) {
					TreeItem sottoRadice = new TreeItem(radice, SWT.NONE);
					sottoRadice.setText(currField.getName());
					DynNode figlio = new DynNode(sottoRadice);
					figlio.setPathClass(currField.getType().getCanonicalName());
					dynAlbero.put(figlio.getIdMap(), figlio);
				} else if (currField.getType().isInstance(new String())) {
					TreeItem sottoRadice = new TreeItem(radice, SWT.NONE);
					sottoRadice.setText(currField.getName());
					DynNode figlio = new DynNode(sottoRadice);
					figlio.setPathClass(currField.getType().getCanonicalName());
					dynAlbero.put(figlio.getIdMap(), figlio);
				} else if (currField.getType().isInstance(new Date())) {
					TreeItem sottoRadice = new TreeItem(radice, SWT.NONE);
					sottoRadice.setText(currField.getName());
					DynNode figlio = new DynNode(sottoRadice);
					figlio.setPathClass(currField.getType().getCanonicalName());
					dynAlbero.put(figlio.getIdMap(), figlio);
				} else if (currField.getType().equals(java.lang.Integer.class)) {
					TreeItem sottoRadice = new TreeItem(radice, SWT.NONE);
					sottoRadice.setText(currField.getName());
					DynNode figlio = new DynNode(sottoRadice);
					figlio.setPathClass(currField.getType().getCanonicalName());
					dynAlbero.put(figlio.getIdMap(), figlio);
				}
				// Ricorsione
				else if (!currField.getType().isInstance(new HashSet<Object>())) {					
					// nodo del grafo di esplorazione					
					String testo = service.Utils.upperCase(currField.getName());					
					String currentPath = currField.getType().getCanonicalName();
					if (!nodiVisitati.contains(currentPath)) {					
						TreeItem sottoRadice = new TreeItem(radice, SWT.NONE);
						sottoRadice.setText(testo);
						sottoRadice.setFont(new Font(Display.getCurrent(), new FontData("Arial", 10 ,SWT.BOLD)));
						sottoRadice.setForeground(new Color(Display.getCurrent(), 255, 0, 0));
						
						DynNode figlio = new DynNode(sottoRadice);
						figlio.setPathClass(currentPath);
						dynAlbero.put(figlio.getIdMap(), figlio);
						
						espandiAlbero(testo, currentPath, sottoRadice);
					}					
				} else if (!nodiVisitati.contains(currField.getDeclaringClass().toString())) {
					// hashSet					
					String testo = service.Utils.rimuoviS(currField.getName());
					testo = service.Utils.upperCase(testo);
					if (!nodiVisitati.contains("hibernate." + testo)) {
						TreeItem sottoRadice = new TreeItem(radice, SWT.NONE);
						sottoRadice.setText(testo);
						sottoRadice.setFont(new Font(Display.getCurrent(), new FontData("Arial", 10 ,SWT.BOLD)));
						sottoRadice.setForeground(new Color(Display.getCurrent(), 255, 0, 0));
						
						DynNode figlio = new DynNode(sottoRadice);
						figlio.setPathClass("hibernate." + testo);
						dynAlbero.put(figlio.getIdMap(), figlio);
						
						espandiAlbero(testo, "hibernate." + testo, sottoRadice);
					}					
				}
				fieldClasse.remove(currField);
			}
		}
	}

	
}












