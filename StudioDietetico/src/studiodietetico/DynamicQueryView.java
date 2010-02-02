package studiodietetico;

import java.util.HashSet;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;

import service.Costanti;
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
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.graphics.Point;

public class DynamicQueryView extends ViewPart{

	private Composite top = null;
	private Tree tree = null;
	private Combo comboSelezioneEntita = null;
	private Label labelSelezioneEntita = null;
	private HashSet<String> nodiVisitati = new HashSet<String>();
	private Button button = null;
	private Table table = null;
	private Shell sShell = null;  //  @jve:decl-index=0:visual-constraint="398,565"
	private Button button1 = null;
	private Label label = null;
	private Text text = null;
	private Label label1 = null;

	public DynamicQueryView() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void createPartControl(Composite parent) {
        top = new Composite(parent, SWT.NONE);        
        tree = new Tree(top, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
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
        TreeColumn col1 = new TreeColumn(tree, SWT.LEFT);
        col1.setText("Filtro");
        col1.setWidth(200);
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
						DynamicQueryDAO dynDao = new DynamicQueryDAO(pathClasse);
						dynDao.espandiAlbero(nomeClasse, pathClasse, radice, nodiVisitati, tree);
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
//		sShell.setLayout(new GridLayout());
		sShell.setSize(new Point(290, 204));
		button1 = new Button(sShell, SWT.NONE);
		button1.setText("Ok");
		button1.setBounds(new Rectangle(87, 132, 106, 27));
		label = new Label(sShell, SWT.NONE);
		label.setBounds(new Rectangle(87, 12, 117, 34));
		label.setText("Inserisci il valore");
		text = new Text(sShell, SWT.BORDER);
		text.setBounds(new Rectangle(139, 81, 75, 25));
		label1 = new Label(sShell, SWT.NONE);
		label1.setBounds(new Rectangle(51, 87, 34, 15));
		label1.setText(item.getText());
		button1.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				item.setText(item.getText()+" = "+text.getText());
				sShell.close();
			}
		});
	}
}  //  @jve:decl-index=0:visual-constraint="-1,6,1051,526"