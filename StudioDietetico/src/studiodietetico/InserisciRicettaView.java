package studiodietetico;

import hibernate.Composizione;
import hibernate.Ingrediente;
import hibernate.Ricetta;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.Button;

import command.ComposizioneDAO;
import command.IngredienteDAO;
import command.RicettaDAO;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Spinner;

public class InserisciRicettaView extends ViewPart {

	private Composite top = null;
	private Label lNomeRicetta = null;
	private Text textNome = null;
	private Label lIngredienti = null;
	private List listIngredienti = null;
	private Text textAddIngrediente = null;
	private Button bAddIngrediente = null;
	private Button bAddIngRic = null;
	private List listRicetta = null;
	private Button bDelIngRic = null;
	private Label lRicetta = null;
	private Shell shellMsg = null;
	private MessageBox messageBox = null;
	private Text textProcedimento = null;
	private Label lProcedimento = null;
	private Button bRicetta = null;
	private Label lQuant = null;
	private Text textQuant = null;
	private Label lNomeAlimento = null;
	private Label lTipAlim = null;
	private Label lCalorie = null;
	private Text textTipAlim = null;
	private Combo comboPortata = null;
	private Spinner spCalorie = null;

	public InserisciRicettaView() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void createPartControl(Composite parent) {
		
        lNomeRicetta = new Label(top, SWT.NONE);
        lNomeRicetta.setBounds(new Rectangle(15, 105, 74, 20));
        lNomeRicetta.setText("Nome ricetta");
        textNome = new Text(top, SWT.BORDER);
        textNome.setBounds(new Rectangle(15, 127, 306, 19));
        lIngredienti = new Label(top, SWT.NONE);
        lIngredienti.setBounds(new Rectangle(391, 106, 55, 20));
        lIngredienti.setText("Ingredienti");
        listIngredienti = new List(top, SWT.V_SCROLL | SWT.BORDER);
        listIngredienti.setBounds(new Rectangle(391, 127, 317, 198));
        textAddIngrediente = new Text(top, SWT.BORDER);
        textAddIngrediente.setBounds(new Rectangle(391, 328, 195, 19));
     
       
        bAddIngrediente = new Button(top, SWT.NONE);
        bAddIngrediente.setBounds(new Rectangle(594, 328, 115, 20));
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
        bAddIngRic.setBounds(new Rectangle(330, 194, 49, 23));
        bAddIngRic.setText("<<");
        bAddIngRic.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
        	public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {    	
        		shellMsg = new Shell();
        		String[] ingRic =	confrontaArray(listIngredienti.getSelection(), listRicetta.getItems());
        		if (textQuant.getText().equals("")) {
        			messageBox = new MessageBox(shellMsg,
						    SWT.OK | 
						    SWT.ICON_ERROR);
						 messageBox.setMessage("Inserire quantità");
						 messageBox.open();		
				}else{
        		for (int i = 0; i < ingRic.length; i++) {
					listRicetta.add(textQuant.getText() + "     " + ingRic[i]);
				}
        		textQuant.setText("");
				}
        	}

			private String[] confrontaArray(String[] ingredienti, String[] ricetta) {
				Set<String> items = new HashSet<String>();
				ArrayList<String> ingDaInserire = new ArrayList<String>();
				for (int i = 0; i < ricetta.length; i++) {
					items.add(ricetta[i]);
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
        listRicetta = new List(top, SWT.V_SCROLL | SWT.BORDER);
        listRicetta.setBounds(new Rectangle(15, 171, 306, 80));
        bDelIngRic = new Button(top, SWT.NONE);
        bDelIngRic.setBounds(new Rectangle(330, 222, 49, 23));
        bDelIngRic.setText(">>");
        bDelIngRic.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
        	public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
        		System.out.println("widgetSelected()"); // TODO Auto-generated Event stub widgetSelected()
        	
        		listRicetta.remove(listRicetta.getSelectionIndices());
        	}
        });
        lRicetta = new Label(top, SWT.NONE);
        lRicetta.setBounds(new Rectangle(15, 150, 122, 17));
        lRicetta.setText("Ingredienti Ricetta");
        textProcedimento = new Text(top, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL);
        textProcedimento.setBounds(new Rectangle(15, 277, 307, 92));
        lProcedimento = new Label(top, SWT.NONE);
        lProcedimento.setBounds(new Rectangle(15, 259, 89, 13));
        lProcedimento.setText("Procedimento");
        bRicetta = new Button(top, SWT.NONE);
        bRicetta.setBounds(new Rectangle(298, 399, 117, 41));
        bRicetta.setText("Inserisci ricetta");
        bRicetta.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
        	public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
        		RicettaDAO ricD = new RicettaDAO();
        		IngredienteDAO ingredienteD = new IngredienteDAO();
        		Ingrediente ing;
        		Ricetta ric;
        		ComposizioneDAO compD = new ComposizioneDAO();
        		String[] ingRic = listIngredienti.getItems();
        		
        		for (int i = 0; i < ingRic.length; i++) {
        			ric = ricD.inserisciNuovaRicetta(textNome.getText(), textProcedimento.getText());
        			ing = ingredienteD.getIngrediente(ingRic[i].split("     ")[1]);
        			compD.inserisciComposizione(ing, ingRic[i].split("     ")[0], ric);
				}
        		
        		
        		
        	}
        });
        lQuant = new Label(top, SWT.NONE);
        lQuant.setBounds(new Rectangle(328, 150, 48, 16));
        lQuant.setText("Quantità");
        textQuant = new Text(top, SWT.BORDER);
        textQuant.setBounds(new Rectangle(330, 172, 54, 19));
        lNomeAlimento = new Label(top, SWT.NONE);
        lNomeAlimento.setBounds(new Rectangle(391, 56, 90, 13));
        lNomeAlimento.setText("Nome Alimento");
        lTipAlim = new Label(top, SWT.NONE);
        lTipAlim.setBounds(new Rectangle(14, 60, 88, 13));
        lTipAlim.setText("Tipologia alimento");
        lCalorie = new Label(top, SWT.NONE);
        lCalorie.setBounds(new Rectangle(599, 57, 85, 13));
        lCalorie.setText("Calorie");
        textTipAlim = new Text(top, SWT.BORDER);
        textTipAlim.setBounds(new Rectangle(391, 79, 196, 19));
        top = new Composite(parent, SWT.NONE);
        createComboPortata();
        spCalorie = new Spinner(top, SWT.NONE);
        spCalorie.setBounds(new Rectangle(599, 79, 64, 17));     
        
        listIngredienti.setItems(IngredienteDAO.getIngredienti());
	}

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub

	}

	/**
	 * This method initializes comboPortata	
	 *
	 */
	private void createComboPortata() {
		comboPortata = new Combo(top, SWT.NONE);
		comboPortata.setBounds(new Rectangle(14, 79, 306, 19));
		comboPortata.add("Antipasto");
		comboPortata.add("Primo");
		comboPortata.add("Secondo");
		comboPortata.add("Contorno");
		comboPortata.add("Frutta");
		comboPortata.add("Dessert");
		comboPortata.add("Spuntino");
		comboPortata.add("Piatto unico");
		comboPortata.add("Altro");
		comboPortata.select(0);
	}

}  //  @jve:decl-index=0:visual-constraint="10,10,739,485"
