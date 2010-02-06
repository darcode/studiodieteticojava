package studiodietetico;

import java.util.HashSet;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;

import service.Costanti;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Label;

import command.DynamicQueryDAO;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.graphics.Point;
import org.jdom.Element;

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
	private Label label = null;
	private Text text = null;
	private Label label1 = null;
	private Button buttonCancella = null;
	DynamicQueryDAO dynDao;

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
        		//check se è selezionato e se è foglia
        		TreeItem[] arr = tree.getSelection();        		
        		if (arr[0] != null) {
        			TreeItem item = arr[0];
        			if (!item.getText().substring(0, 1).equals(item.getText().substring(0, 1).toUpperCase())) {
                		createSShell(item);
                		sShell.open();
					}
				}
        	}
        });
        tree.addListener (SWT.Selection, new Listener () {
    		public void handleEvent (Event event) {
    			if (event.detail == SWT.CHECK) {
        			System.out.println ("TODO: Raggiungere il nodo checkato controllando (se è una relazione checka tutte le foglie del nodo)");
				}
    		}
    	});
        createComboSelezioneEntita();
        labelSelezioneEntita = new Label(top, SWT.NONE);
        labelSelezioneEntita.setBounds(new Rectangle(-1, 5, 278, 13));
        labelSelezioneEntita.setText("Selezionare l' entita' che si vuole ricercare:");
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
						
						Element radiceXml = new Element("radice");
						radiceXml.setAttribute("path", pathClasse);
						radiceXml.setAttribute("nome", nomeClasse);
						;
						
						dynDao = new DynamicQueryDAO(pathClasse, nomeClasse, radiceXml);
						dynDao.espandiAlbero(nomeClasse, pathClasse, radice, nodiVisitati, tree, radiceXml);
						comboSelezioneEntita.setEnabled(false);
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
	private void createSShell(final TreeItem item) {
		sShell = new Shell();
		sShell.setSize(new Point(290, 167));
		buttonOk = new Button(sShell, SWT.NONE);
		buttonOk.setText("Ok");
		buttonOk.setBounds(new Rectangle(139, 109, 106, 27));
		label = new Label(sShell, SWT.NONE);
		label.setBounds(new Rectangle(87, 9, 117, 34));
		label.setText("Inserisci il valore");
		text = new Text(sShell, SWT.BORDER);
		text.setBounds(new Rectangle(152, 56, 113, 25));
		label1 = new Label(sShell, SWT.NONE);
		label1.setBounds(new Rectangle(17, 59, 121, 22));
		label1.setText(item.getText());
		buttonCancella = new Button(sShell, SWT.NONE);
		buttonCancella.setBounds(new Rectangle(43, 107, 90, 27));
		buttonCancella.setText("Cancella");
		buttonCancella.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
					public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
						item.setText(new String[] {item.getText(), ""});						
						sShell.close();
					}
				});
		buttonOk.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				item.setText(new String[] {item.getText(),text.getText()});
				sShell.close();
			}
		});
	}
}  //  @jve:decl-index=0:visual-constraint="-1,6,1051,526"