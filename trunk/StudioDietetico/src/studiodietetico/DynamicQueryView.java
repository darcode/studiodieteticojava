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

public class DynamicQueryView extends ViewPart{

	private Composite top = null;
	private Tree tree = null;
	private Combo comboSelezioneEntita = null;
	private Label labelSelezioneEntita = null;
	private HashSet<String> nodiVisitati = new HashSet<String>();

	public DynamicQueryView() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void createPartControl(Composite parent) {
        top = new Composite(parent, SWT.NONE);        
        tree = new Tree(top, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
        tree.setLayout(new FillLayout());
        tree.setHeaderVisible(true);        
        tree.setBounds(new Rectangle(0, 52, 357, 258));
        createComboSelezioneEntita();
        labelSelezioneEntita = new Label(top, SWT.NONE);
        labelSelezioneEntita.setBounds(new Rectangle(-1, 5, 228, 13));
        labelSelezioneEntita.setText("Selezionare l'entità che si vuole ricercare:");
        TreeColumn col1 = new TreeColumn(tree, SWT.LEFT);
        col1.setText("Filtro");
        col1.setWidth(200);
        TreeColumn col2 = new TreeColumn(tree, SWT.CENTER);
        col2.setText("Valore");
        col2.setWidth(200);
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
		comboSelezioneEntita.setBounds(new Rectangle(227, 2, 132, 21));
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
						//inserisco il nodo radice (nome della classe/entità selezionata)
						tree.removeAll();
					    TreeItem radice = new TreeItem(tree, SWT.NONE);
						radice.setText(new String[] {nomeClasse});
						nodiVisitati.clear();	
						DynamicQueryDAO dynDao = new DynamicQueryDAO(pathClasse);
//						dynDao.espandiAlbero(nomeClasse, pathClasse, radice, nodiVisitati);
//						dynDao.executeDynQuery(filtroQuery);
					}
					public void widgetDefaultSelected(
							org.eclipse.swt.events.SelectionEvent e) {
					}
				});
	}	
}