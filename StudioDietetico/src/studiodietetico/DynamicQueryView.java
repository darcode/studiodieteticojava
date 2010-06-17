package studiodietetico;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Map.Entry;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.part.ViewPart;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.PropertyExpression;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.SimpleExpression;
import org.hibernate.proxy.HibernateProxy;

import service.Costanti;
import service.DynNode;
import service.Utils;

import common.GenericBean;
import common.HibernateUtils;

public class DynamicQueryView extends ViewPart {
	private static final String					BOOL					= "BOOL";
	private static final String					INTEGER					= "INTEGER";
	private static final String					STRING					= "STRING";
	private static final String					DATE					= "DATE";
	private static final String					DECIMAL					= "DECIMAL";
	private static final Font					font					= common.Utils.getFont("Arial", 9, SWT.BOLD);
	private static final ThreadLocal<Session>	session					= new ThreadLocal<Session>();
	private static final SessionFactory			sessionFactory			= new Configuration().configure().buildSessionFactory();
	private Composite							compFiltro;
	private Composite							top						= null;
	private Tree								treeEntity				= null;
	private TreeItem							radice					= null;
	private Combo								comboSelezioneEntita	= null;
	private Label								labelSelezioneEntita	= null;
	private HashSet<String>						nodiVisitati			= new HashSet<String>();
	private Button								button					= null;
	private Tree								visualizzaRisultati		= null;
	private HashMap<TreeItem, DynNode>			dynAlbero				= new HashMap<TreeItem, DynNode>();
	private HashMap<String, String>			selectedEntities		= new HashMap<String, String>();
	private Composite							cmpFiltri				= null;

	// DynamicQueryDAO
	public Criteria								criteria;
	private Object								filtroQuery;
	private List<Object>						result;

	public DynamicQueryView() {
	}

	@Override
	public void createPartControl(Composite parent) {
		top = new Composite(parent, SWT.BORDER);
		GridData gdTop = new GridData();
		gdTop.horizontalAlignment = SWT.FILL;
		gdTop.verticalAlignment = SWT.FILL;
		gdTop.grabExcessHorizontalSpace = true;
		gdTop.grabExcessVerticalSpace = true;
		top.setLayoutData(gdTop);
		GridLayout glTop = new GridLayout();
		glTop.numColumns = 4;
		top.setLayout(glTop);
		labelSelezioneEntita = new Label(top, SWT.NONE);
		labelSelezioneEntita.setLayoutData(new GridData());
		labelSelezioneEntita.setText("Selezionare il contesto da cui partire:");
		createComboSelezioneEntita();
		button = new Button(top, SWT.NONE);
		button.setText("Esegui");
		button.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				executeQuery();
				// System.out.println(dynAlbero.keySet());
				if (result != null && !result.isEmpty()) {
					Table table = new Table(top, SWT.SINGLE | SWT.FULL_SELECTION);
					table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
					table.setLinesVisible(true);
					table.setHeaderVisible(true);

					for (int i = 0; i < table.getColumnCount(); i++) {
						table.getColumn(i).pack();
					}
					TreeItem root = new TreeItem(visualizzaRisultati, SWT.NONE);
					root.setText("Risultato");
					for (Object row : result) {
						TreeItem figlio = new TreeItem(root, SWT.NONE);
						figlio.setText(row.getClass().getSimpleName().toUpperCase());
						figlio.setFont(font);
						feelTableResult(figlio, row, true, 0,"HIBERNATE."+row.getClass().getSimpleName().toUpperCase());
					}

				} else {
					// popup "non ci sono risultati"
					final Shell noResults = new Shell();
					noResults.setSize(new Point(200, 150));
					Button okNoResults = new Button(noResults, SWT.NONE);
					okNoResults.setText("chiudi");
					okNoResults.setBounds(new Rectangle(172, 165, 106, 27));
					Label etichettaNoResults = new Label(noResults, SWT.NONE);
					etichettaNoResults.setBounds(new Rectangle(87, 9, 117, 34));
					etichettaNoResults.setText("L'interrogazione non ha restituito risultati");
					okNoResults.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
						public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
							noResults.close();
						}
					});

				}
				treeEntity.setEnabled(false);
			}
		});
		treeEntity = new Tree(top, SWT.CHECK | SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		GridData gdTree = new GridData();
		gdTree.horizontalAlignment = SWT.FILL;
		gdTree.verticalAlignment = SWT.FILL;
		gdTree.grabExcessHorizontalSpace = true;
		gdTree.grabExcessVerticalSpace = true;
		gdTree.horizontalSpan = 2;
		treeEntity.setLayoutData(gdTree);
		treeEntity.setLayout(new GridLayout());
		treeEntity.setHeaderVisible(true);
		treeEntity.addMouseListener(new org.eclipse.swt.events.MouseAdapter() {
			public void mouseDoubleClick(org.eclipse.swt.events.MouseEvent e) {
				// check se è¨ selezionato e se è foglia
				TreeItem[] arr = treeEntity.getSelection();
				if (arr[0] != null) {
					TreeItem item = arr[0];
					if (!item.getText().substring(0, 1).equals(item.getText().substring(0, 1).toUpperCase())) {

						DynNode currentNode = dynAlbero.get(item);
						createCompositeInserimento(currentNode);
					}
				}
			}
		});
		treeEntity.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				if (event.detail == SWT.CHECK) {
					TreeItem item = (TreeItem) event.item;
					performChecking(item);
				}
			}
		});

		GridData gdTree1 = new GridData();
		gdTree1.horizontalAlignment = SWT.FILL;
		gdTree1.verticalAlignment = SWT.FILL;
		gdTree1.grabExcessHorizontalSpace = true;
		gdTree1.grabExcessVerticalSpace = true;
		gdTree1.horizontalSpan = 2;
		visualizzaRisultati = new Tree(top, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		visualizzaRisultati.setHeaderVisible(true);
		visualizzaRisultati.setLinesVisible(true);
		visualizzaRisultati.setLayoutData(gdTree1);
		visualizzaRisultati.setLayout(new GridLayout());
		TreeColumn colFiltro = new TreeColumn(treeEntity, SWT.CENTER);
		colFiltro.setText("Campo");
		colFiltro.setWidth(200);
		TreeColumn colValore = new TreeColumn(treeEntity, SWT.CENTER);
		colValore.setText("Valore");
		colValore.setWidth(200);
		cmpFiltri = new Composite(top, SWT.BORDER);
		GridData gdFiltri = new GridData();
		gdFiltri.horizontalAlignment = SWT.FILL;
		gdFiltri.verticalAlignment = SWT.FILL;
		gdFiltri.grabExcessHorizontalSpace = true;
		gdFiltri.grabExcessVerticalSpace = true;
		gdFiltri.horizontalSpan = 4;
		gdFiltri.minimumHeight = 250;
		cmpFiltri.setLayoutData(gdFiltri);
		GridLayout glFiltri = new GridLayout();
		glFiltri.numColumns = 4;
		cmpFiltri.setLayout(glFiltri);

	}

	protected void feelTableResult(TreeItem node, Object item, boolean isRoot, int depth,String path) {
		if (depth < 20 && !fermaEspansione(node)) {
			try {
				if (item != null && (item instanceof Set) && !isRoot) {
					for (Object itemFiglio : ((Set) item)) {
						depth++;
						if (itemFiglio != null) {
							TreeItem figlio = new TreeItem(node, SWT.NONE);
							figlio.setText(itemFiglio.getClass().getSimpleName().toUpperCase());
							figlio.setFont(font);
							feelTableResult(figlio, itemFiglio, false, depth,"HIBERNATE"+"."+itemFiglio.getClass().getSimpleName().toUpperCase());
						}
					}
				} else {
					if (item != null) {
						for (Class clazz : item.getClass().getInterfaces()) {
							if (clazz.equals(HibernateProxy.class)) {
								item = HibernateUtils.getProxiedObject(item);
								break;
							}
						}
						ArrayList<String> campi = GenericBean.getAllFieldsNames(item);
						for (String campo : campi) {
							if (campo.contains("Id") || campo.contains("id")) {
							} else if (!campo.getClass().isPrimitive()
									&& !(GenericBean.getPropertyPackage(campo, item) == null)
									&& (GenericBean.getPropertyClass(campo, item).equals(Set.class.getSimpleName()) || GenericBean
											.getPropertyPackage(campo, item).getName().equals("hibernate"))) {
								depth++;
								String key  = (path+"."+campo).toUpperCase();
								if(!(key.lastIndexOf("S")== key.length()-1))
									key+="S";
								System.out.println(key);
								if (selectedEntities.containsKey(key)) {
									TreeItem figlio = new TreeItem(node, SWT.NONE);
									figlio.setText(campo.toUpperCase());
									figlio.setFont(font);
									feelTableResult(figlio, GenericBean.getProperty(campo, item), false, depth,path+"."+campo.toUpperCase());
								}
							} else {
								if (selectedEntities.containsKey((path+"."+campo).toUpperCase())) {
									TreeItem treeItem = new TreeItem(node, SWT.NONE);
									String label = "" + campo;
									while (label.length() < 25)
										label = " " + label;
									treeItem.setText(label.toUpperCase() + ":             " + (GenericBean.getProperty(campo, item)));
								}
							}
						}

					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	private boolean fermaEspansione(TreeItem node) {
		TreeItem padre = node.getParentItem();
		while (padre != null && padre != node) {
			if (padre.getText().equals(node.getText())) {
				node.dispose();
				return true;
			}
			padre = padre.getParentItem();
		}
		return false;
	}

	@Override
	public void setFocus() {
	}

	private void createComboSelezioneEntita() {
		comboSelezioneEntita = new Combo(top, SWT.NONE);
		comboSelezioneEntita.setLayout(new GridLayout());
		comboSelezioneEntita.setLayoutData(new GridData());
		for (int i = 0; i < Costanti.entita.length; i++) {
			comboSelezioneEntita.add(Costanti.entita[i][0]);
		}
		comboSelezioneEntita.addSelectionListener(new org.eclipse.swt.events.SelectionListener() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				// trova il nome della classe dato il testo della
				// combolist e lo mette nell'albero
				int index = comboSelezioneEntita.getSelectionIndex();
				String nomeClasse = Costanti.entita[index][0];
				String pathClasse = Costanti.entita[index][1];
				// inserisco il nodo radice (nome della classe/entità
				// selezionata)
				treeEntity.removeAll();
				radice = new TreeItem(treeEntity, SWT.NONE);
				radice.setText(new String[] { nomeClasse });
				nodiVisitati.clear();

				espandiAlbero(nomeClasse, pathClasse, radice);
				comboSelezioneEntita.setEnabled(false);
				initDao(pathClasse, nomeClasse);
				radice.setChecked(true);
				performChecking(radice);
			}

			public void widgetDefaultSelected(org.eclipse.swt.events.SelectionEvent e) {
			}
		});
	}

	@SuppressWarnings("unchecked")
	public void espandiAlbero(String nomeClasse, String pathClasse, TreeItem radice) {
		// istanzia classe dinamicamente
		Class classSelected = null;
		try {
			classSelected = Class.forName(pathClasse);
		} catch (ClassNotFoundException e1) {

			e1.printStackTrace();
		}
		if (!nodiVisitati.contains(pathClasse)) {
			nodiVisitati.add(pathClasse);
			/* Anna ..per evitare null pointer!!! */
			DynNode rad = new DynNode(radice);
			/* Corrado ..hai dimenticato il path :D */
			rad.setPathClass(pathClasse);
			dynAlbero.put(rad.getTreeNode(), rad);
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
					dynAlbero.put(figlio.getTreeNode(), figlio);

					if (prim.equals("char")) {

					} else if (prim.equals("int")) {

					} else if (prim.equals("double")) {

					}
				} else if (currField.getType().equals(java.lang.Boolean.class)) {
					TreeItem sottoRadice = new TreeItem(radice, SWT.NONE);
					sottoRadice.setText(currField.getName());
					DynNode figlio = new DynNode(sottoRadice);
					figlio.setPathClass(currField.getType().getCanonicalName());
					dynAlbero.put(figlio.getTreeNode(), figlio);
				} else if (currField.getType().equals(java.lang.Double.class)) {
					TreeItem sottoRadice = new TreeItem(radice, SWT.NONE);
					sottoRadice.setText(currField.getName());
					DynNode figlio = new DynNode(sottoRadice);
					figlio.setPathClass(currField.getType().getCanonicalName());
					dynAlbero.put(figlio.getTreeNode(), figlio);
				} else if (currField.getType().isInstance(new String())) {
					TreeItem sottoRadice = new TreeItem(radice, SWT.NONE);
					sottoRadice.setText(currField.getName());
					DynNode figlio = new DynNode(sottoRadice);
					figlio.setPathClass(currField.getType().getCanonicalName());
					dynAlbero.put(figlio.getTreeNode(), figlio);
				} else if (currField.getType().isInstance(new Date())) {
					TreeItem sottoRadice = new TreeItem(radice, SWT.NONE);
					sottoRadice.setText(currField.getName());
					DynNode figlio = new DynNode(sottoRadice);
					figlio.setPathClass(currField.getType().getCanonicalName());
					dynAlbero.put(figlio.getTreeNode(), figlio);
				} else if (currField.getType().equals(java.lang.Integer.class)) {
					TreeItem sottoRadice = new TreeItem(radice, SWT.NONE);
					sottoRadice.setText(currField.getName());
					DynNode figlio = new DynNode(sottoRadice);
					figlio.setPathClass(currField.getType().getCanonicalName());
					dynAlbero.put(figlio.getTreeNode(), figlio);
				}
				// Ricorsione
				// classe di hibernate (uno a molti)
				else if (!currField.getType().isInstance(new HashSet<Object>())) {
					// nodo del grafo di esplorazione
					String testo = service.Utils.upperCase(currField.getName());
					String currentPath = currField.getType().getCanonicalName();
					if (!nodiVisitati.contains(currentPath)) {
						TreeItem sottoRadice = new TreeItem(radice, SWT.NONE);
						sottoRadice.setText(testo);
						sottoRadice.setFont(new Font(Display.getCurrent(), new FontData("Arial", 10, SWT.BOLD)));
						sottoRadice.setForeground(new Color(Display.getCurrent(), 255, 0, 0));

						DynNode figlio = new DynNode(sottoRadice);
						figlio.setPathClass(currentPath);
						dynAlbero.put(figlio.getTreeNode(), figlio);

						espandiAlbero(testo, currentPath, sottoRadice);
					}
					// hashSet (molti a molti)
				} else if (!nodiVisitati.contains(currField.getDeclaringClass().toString())) {
					String testo = service.Utils.rimuoviS(currField.getName());
					testo = service.Utils.upperCase(testo);
					if (!nodiVisitati.contains("hibernate." + testo)) {
						TreeItem sottoRadice = new TreeItem(radice, SWT.NONE);
						sottoRadice.setText(testo);
						sottoRadice.setFont(new Font(Display.getCurrent(), new FontData("Arial", 10, SWT.BOLD)));
						sottoRadice.setForeground(new Color(Display.getCurrent(), 255, 0, 0));

						DynNode figlio = new DynNode(sottoRadice);
						figlio.setPathClass("hibernate." + testo);
						dynAlbero.put(figlio.getTreeNode(), figlio);

						espandiAlbero(testo, "hibernate." + testo, sottoRadice);
					}
				}
				fieldClasse.remove(currField);
			}
		}
	}

	public void performChecking(TreeItem item) {
		if (item.getChecked()) {
			selectedEntities.put(getAttributePath(item), getAttributePath(item));
		} else {
			selectedEntities.remove(getAttributePath(item));
		}
		for (TreeItem figlio : item.getItems()) {
			if (!figlio.getText().substring(0, 1).equals(figlio.getText().substring(0, 1).toUpperCase())) {
				if (item.getChecked()) {
					figlio.setChecked(true);
					selectedEntities.put(getAttributePath(figlio),getAttributePath(figlio));
				} else {
					figlio.setChecked(false);
					selectedEntities.remove(getAttributePath(figlio));
				}
			}
		}
	}

	// ShellPopUp

	public void createCompositeInserimento(final DynNode item) {
		GridLayout glFiltro = new GridLayout();
		glFiltro.numColumns = 2;
		compFiltro = new Composite(cmpFiltri, SWT.BORDER);
		compFiltro.setLayout(glFiltro);
		GridData gdFiltri = new GridData();
		gdFiltri.grabExcessHorizontalSpace = true;
		gdFiltri.grabExcessVerticalSpace = true;
		gdFiltri.horizontalAlignment = SWT.FILL;
		gdFiltri.verticalAlignment = SWT.FILL;
		gdFiltri.minimumHeight = 250;
		compFiltro.setLayoutData(gdFiltri);
		Label titolo = new Label(compFiltro, SWT.NONE);
		GridData gdTitolo = new GridData();
		gdTitolo.horizontalSpan = 2;
		titolo.setLayoutData(gdTitolo);
		titolo.setText("Filtro su " + item.getTreeNode().getText());
		titolo.setFont(font);
		Label lbl = new Label(compFiltro, SWT.NONE);
		lbl.setText("Aggiungi in: ");
		CCombo cboTipoAssociazione = new CCombo(compFiltro, SWT.NONE);
		cboTipoAssociazione.add("AND", 0);
		cboTipoAssociazione.add("OR", 1);
		cboTipoAssociazione.add("NOT", 2);
		cboTipoAssociazione.select(0);
		Label etichettaOperazione = new Label(compFiltro, SWT.NONE);
		etichettaOperazione.setText("Operazione:");
		CCombo comboOperazione = new CCombo(compFiltro, SWT.NONE);
		comboOperazione.add(" = (Uguale)", 0);
		comboOperazione.add("<> (Diverso)", 1);
		comboOperazione.add(" < (Minore)", 2);
		comboOperazione.add("=< (Minore o Uguale)", 3);
		comboOperazione.add(" > (Maggiore)", 4);
		comboOperazione.add(">= (Maggiore o Uguale)", 5);
		comboOperazione.select(0);
		Label lblMatchingInserimento = new Label(compFiltro, SWT.NONE);
		lblMatchingInserimento.setText("Confronta con:");
		final CCombo elencoAltriCampi = new CCombo(compFiltro, SWT.NONE);
		riempiComboPerTipo(item.getPathClass(), elencoAltriCampi);
		GridLayout glButton = new GridLayout();
		glButton.numColumns = 2;
		Label etichettaInserimento = new Label(compFiltro, SWT.NONE);
		final Text textInserimento = new Text(compFiltro, SWT.BORDER);
		textInserimento.addModifyListener(new ModifyListener() {

			@Override
			public void modifyText(ModifyEvent e) {
				if (((Text) e.getSource()).getText().equals(""))
					elencoAltriCampi.setEnabled(true);
				else
					elencoAltriCampi.setEnabled(false);
			}
		});
		elencoAltriCampi.addModifyListener(new ModifyListener() {

			@Override
			public void modifyText(ModifyEvent e) {
				if (((CCombo) e.getSource()).getText().equals(""))
					textInserimento.setEnabled(true);
				else
					textInserimento.setEnabled(false);
			}
		});
		SelectionAdapter listener = null;
		if (item.getPathClass().contains("Integer") | item.getPathClass().contains("int")) {
			etichettaInserimento.setText("Inserisci un INTERO");
			listener = gestisciFiltroIntero(textInserimento, comboOperazione, cboTipoAssociazione, elencoAltriCampi, item);
		} else if (item.getPathClass().contains("Double") | item.getPathClass().contains("double")) {
			etichettaInserimento.setText("Inserisci un DECIMALE");
			listener = gestisciFiltroDecimale(textInserimento, comboOperazione, cboTipoAssociazione, elencoAltriCampi, item);
		} else if (item.getPathClass().contains("Date")) {
			etichettaInserimento.setText("Inserisci una DATA");
			listener = gestisciFiltroData(textInserimento, comboOperazione, cboTipoAssociazione, elencoAltriCampi, item);
		} else if (item.getPathClass().contains("Boolean") | item.getPathClass().contains("boolean")) {
			etichettaInserimento.setText("Inserisci VERO/FALSO");
			textInserimento.setVisible(false);
//			Label fill = new Label(compFiltro, SWT.NONE);
			listener = gestisciFiltroPerBoolean(comboOperazione, cboTipoAssociazione, elencoAltriCampi, item);
		} else if (item.getPathClass().contains("Char") | item.getPathClass().contains("char")) {
			etichettaInserimento.setText("Inserisci un CARATTERE");
			textInserimento.setTextLimit(1);
			listener = gestisciFiltroPerChar(textInserimento, comboOperazione, cboTipoAssociazione, elencoAltriCampi, item);
		} else if (item.getPathClass().contains("String")) {
			etichettaInserimento.setText("Inserisci un valore");
			listener = gestisciFiltroPerStringa(textInserimento, comboOperazione, cboTipoAssociazione, elencoAltriCampi, item);
		} else {
			System.out.println(item.getPathClass() + " ---> " + item.getTreeNode().getText());
		}
		Composite cmpButton = new Composite(compFiltro, SWT.NONE);
		cmpButton.setLayout(glButton);
		GridData gdButton = new GridData();
		// gdButton.grabExcessHorizontalSpace = true;
		// gdButton.grabExcessVerticalSpace = true;
		gdButton.horizontalAlignment = SWT.FILL;
		gdButton.verticalAlignment = SWT.BOTTOM;
		gdButton.horizontalSpan = 2;
		cmpButton.setLayoutData(gdButton);
		Button buttonOkInserimento = new Button(cmpButton, SWT.NONE);
		buttonOkInserimento.setText("Ok");
		Button buttonCancellaInserimento = new Button(cmpButton, SWT.NONE);
		buttonCancellaInserimento.setText("Cancella");
		buttonCancellaInserimento.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				item.getTreeNode().setText(new String[] { item.getTreeNode().getText(), "" });
				((Control) e.getSource()).getParent().getParent().dispose();
			}
		});
		if (listener == null) {
			// nel caso sia una stringa
			buttonOkInserimento.addSelectionListener(elaboraFiltro(textInserimento, comboOperazione, cboTipoAssociazione, elencoAltriCampi, item));
		} else
			buttonOkInserimento.addSelectionListener(listener);
		compFiltro.layout();
		cmpFiltri.layout();
	}

	private SelectionAdapter gestisciFiltroDecimale(final Text textInserimento, final CCombo tipoOperazione, final CCombo tipoAssociazione,
			final CCombo elencoAltriCampi, DynNode currNode) {
		final DynNode item = currNode;
		return new org.eclipse.swt.events.SelectionAdapter() {
			@SuppressWarnings("unchecked")
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent eS) {
				DynNode pathPadre = dynAlbero.get(item.getTreeNode().getParentItem());
//				String path = pathPadre.getPathClass().substring(pathPadre.getPathClass().indexOf(".") + 1, pathPadre.getPathClass().length());
				if (pathPadre.getPathClass().equalsIgnoreCase(filtroQuery.getClass().getCanonicalName())) {
					aggiungiRestrizione(textInserimento, tipoOperazione, tipoAssociazione, elencoAltriCampi, item, DECIMAL);
				} else {
					// si costruisce a ritroso il percorso
					ArrayList<String> ramo = new ArrayList<String>();
					DynNode current = dynAlbero.get(item.getTreeNode().getParentItem());
					; // turno
					DynNode currentPadre = dynAlbero.get(current.getTreeNode().getParentItem()); // prestaziones
					while (!current.getPathClass().equalsIgnoreCase(filtroQuery.getClass().getCanonicalName())) {
						String currentNode = "";

						Class currentClass = null;
						try {
							currentClass = Class.forName(currentPadre.getPathClass());
						} catch (ClassNotFoundException e1) {

							e1.printStackTrace();
						}
						Field currentFields[] = currentClass.getDeclaredFields();
						ArrayList<Field> fieldClasse = new ArrayList<Field>();
						for (int i = 0; i < currentFields.length; i++) {
							fieldClasse.add(currentFields[i]);
						}
						while (fieldClasse.size() > 0) {
							Field currField = fieldClasse.get(0);
							// si becca se è un hashset
							String matching = currField.getName().toLowerCase();
							if (matching.contains(current.getTreeNode().getText().toLowerCase())) {
								if (currField.getType().isInstance(new HashSet<Object>())) {
									currentNode = current.getTreeNode().getText().toLowerCase().concat("s");
								} else {
									currentNode = current.getTreeNode().getText().toLowerCase();
								}
								ramo.add(currentNode);
							}
							fieldClasse.remove(currField);
						}
						current = dynAlbero.get(current.getTreeNode().getParentItem());
						currentPadre = dynAlbero.get(currentPadre.getTreeNode().getParentItem());
					}
					ramo = Utils.inversione(ramo);
					for (int i = 0; i < ramo.size(); i++) {
						criteria = criteria.createCriteria(ramo.get(i));
					}
					aggiungiRestrizione(textInserimento, tipoOperazione, tipoAssociazione, elencoAltriCampi, item, DECIMAL);
					// criteria.add(Restrictions.eq(item.getTreeNode().getText(),
					// textInserimento.getText()));
				}
				((Control) eS.getSource()).setEnabled(false);
				textInserimento.setEnabled(false);
				tipoOperazione.setEnabled(false);
				tipoAssociazione.setEnabled(false);
				elencoAltriCampi.setEnabled(false);
			}
		};
	}

	private SelectionAdapter gestisciFiltroIntero(final Text textInserimento, final CCombo tipoOperazione, final CCombo tipoAssociazione,
			final CCombo elencoAltriCampi, DynNode currNode) {
		final DynNode item = currNode;
		return new org.eclipse.swt.events.SelectionAdapter() {
			@SuppressWarnings("unchecked")
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent eS) {
				DynNode pathPadre = dynAlbero.get(item.getTreeNode().getParentItem());
//				String path = pathPadre.getPathClass().substring(pathPadre.getPathClass().indexOf(".") + 1, pathPadre.getPathClass().length());
				if (pathPadre.getPathClass().equalsIgnoreCase(filtroQuery.getClass().getCanonicalName())) {
					aggiungiRestrizione(textInserimento, tipoOperazione, tipoAssociazione, elencoAltriCampi, item, INTEGER);
					// criteria.add(Expression.eq(item.getTreeNode().getText(),
					// textInserimento.getText()));
				} else {
					// si costruisce a ritroso il percorso
					ArrayList<String> ramo = new ArrayList<String>();
					DynNode current = dynAlbero.get(item.getTreeNode().getParentItem());
					; // turno
					DynNode currentPadre = dynAlbero.get(current.getTreeNode().getParentItem()); // prestaziones
					while (!current.getPathClass().equalsIgnoreCase(filtroQuery.getClass().getCanonicalName())) {
						String currentNode = "";

						Class currentClass = null;
						try {
							currentClass = Class.forName(currentPadre.getPathClass());
						} catch (ClassNotFoundException e1) {

							e1.printStackTrace();
						}
						Field currentFields[] = currentClass.getDeclaredFields();
						ArrayList<Field> fieldClasse = new ArrayList<Field>();
						for (int i = 0; i < currentFields.length; i++) {
							fieldClasse.add(currentFields[i]);
						}
						while (fieldClasse.size() > 0) {
							Field currField = fieldClasse.get(0);
							// si becca se è un hashset
							String matching = currField.getName().toLowerCase();
							if (matching.contains(current.getTreeNode().getText().toLowerCase())) {
								if (currField.getType().isInstance(new HashSet<Object>())) {
									currentNode = current.getTreeNode().getText().toLowerCase().concat("s");
								} else {
									currentNode = current.getTreeNode().getText().toLowerCase();
								}
								ramo.add(currentNode);
							}
							fieldClasse.remove(currField);
						}
						current = dynAlbero.get(current.getTreeNode().getParentItem());
						currentPadre = dynAlbero.get(currentPadre.getTreeNode().getParentItem());
					}
					ramo = Utils.inversione(ramo);
					for (int i = 0; i < ramo.size(); i++) {
						criteria = criteria.createCriteria(ramo.get(i));
					}
					aggiungiRestrizione(textInserimento, tipoOperazione, tipoAssociazione, elencoAltriCampi, item, DECIMAL);
					// criteria.add(Restrictions.eq(item.getTreeNode().getText(),
					// textInserimento.getText()));
				}
				((Control) eS.getSource()).setEnabled(false);
				textInserimento.setEnabled(false);
				tipoOperazione.setEnabled(false);
				tipoAssociazione.setEnabled(false);
				elencoAltriCampi.setEnabled(false);
			}
		};
	}

	private SelectionAdapter gestisciFiltroData(final Text textInserimento, final CCombo tipoOperazione, final CCombo tipoAssociazione,
			final CCombo elencoAltriCampi, DynNode currNode) {
		final DynNode item = currNode;
		GridData gridData6 = new GridData();
		gridData6.horizontalAlignment = GridData.CENTER;
		gridData6.verticalAlignment = GridData.CENTER;
		GridData gridData5 = new GridData();
		gridData5.horizontalAlignment = GridData.CENTER;
		gridData5.verticalAlignment = GridData.CENTER;
//		GridData gridData4 = new GridData();
		GridData gridData1 = new GridData();
		gridData1.horizontalSpan = 2;
		GridData gridData = new GridData();
		gridData.horizontalAlignment = GridData.CENTER;
		gridData.horizontalSpan = 2;
		gridData.verticalAlignment = GridData.CENTER;
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		gridLayout.makeColumnsEqualWidth = false;
		// compFiltro = new Composite(parent,SWT.NONE);
		// compFiltro.setLayout(new GridLayout());
		// compFiltro.setLayoutData(new GridData(SWT.FILL));
		textInserimento.setEnabled(false);
		final DateTime calendar = new DateTime(compFiltro, SWT.CALENDAR | SWT.BORDER);
		calendar.setLayoutData(gridData1);

		calendar.addSelectionListener(new org.eclipse.swt.events.SelectionListener() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
			}

			public void widgetDefaultSelected(org.eclipse.swt.events.SelectionEvent e) {
			}
		});
		final DateTime time = new DateTime(compFiltro, SWT.TIME | SWT.SHORT);
		time.setLayoutData(gridData);
//		final Button ora = new Button(compFiltro, SWT.NONE | SWT.CHECK);
//		Label filler = new Label(compFiltro, SWT.NONE);
		return new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				System.out.println("Calendar date selected (MM/DD/YYYY) = " + (calendar.getMonth() + 1) + "/" + calendar.getDay() + "/"
						+ calendar.getYear());
				System.out.println("Time selected (HH:MM) = " + time.getHours() + ":" + (time.getMinutes() < 10 ? "0" : "") + time.getMinutes());
				String dateString = calendar.getYear() + "-" + (calendar.getMonth() + 1) + "-" + calendar.getDay();
				String formato = "dd/MM/YYYY";
				// if (ora.getSelectionIndex() == 0) {
				// dateString += " " + time.getHours() + ":" +
				// (time.getMinutes() < 10 ? "0" : "") + time.getMinutes() +
				// ":00";
				// formato += " HH:mm:ss";
				// }

				Date selectedData = Utils.convertStringToDate(dateString, formato);
				item.getTreeNode().setText(new String[] { item.getTreeNode().getText(), selectedData.toString() });
				textInserimento.setText(dateString);
//				SimpleExpression restr = null;
				aggiungiRestrizione(textInserimento, tipoOperazione, tipoAssociazione, elencoAltriCampi, item, DATE);
				((Control) e.getSource()).setEnabled(false);
				textInserimento.setEnabled(false);
				tipoOperazione.setEnabled(false);
				tipoAssociazione.setEnabled(false);
				elencoAltriCampi.setEnabled(false);
			}
		};
	}

	private SelectionAdapter gestisciFiltroPerBoolean(final CCombo tipoOperazione, final CCombo tipoAssociazione, final CCombo elencoAltriCampi,
			DynNode currNode) {
		final DynNode item = currNode;
		final CCombo cComboInserimento = new CCombo(compFiltro, SWT.NONE);
		cComboInserimento.add("Vero");
		cComboInserimento.add("Falso");
		cComboInserimento.setEditable(false);
		cComboInserimento.select(0);
		tipoOperazione.setEnabled(false);
		return new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				String selezione = cComboInserimento.getText();
				item.getTreeNode().setText(new String[] { item.getTreeNode().getText(), selezione });
				SimpleExpression restr = null;
				Object valore = "";
				valore = (cComboInserimento.getSelectionIndex() == 0) ? Boolean.TRUE : Boolean.FALSE;
				String altroCampo = elencoAltriCampi.getText();
				if ("".equals(valore))
					valore = altroCampo;
				item.getTreeNode().setText(new String[] { item.getTreeNode().getText(), " = " + valore });
				restr = Restrictions.eq(item.getTreeNode().getText(), valore);
				switch (tipoAssociazione.getSelectionIndex()) {
				case 0:
					// AND
					item.getTreeNode().setText(new String[] { item.getTreeNode().getText(0), item.getTreeNode().getText(1) + " (AND) " });
					criteria.add(restr);
					break;
				case 1:
					// OR
					item.getTreeNode().setText(new String[] { item.getTreeNode().getText(0), item.getTreeNode().getText(1) + " (OR) " });
					criteria.add(Restrictions.disjunction().add(restr));
				case 2:
					// NOT
					item.getTreeNode().setText(new String[] { item.getTreeNode().getText(0), item.getTreeNode().getText(1) + " (NOT) " });
					criteria.add(Restrictions.not(restr));
				default:
					break;
				}

				if (selezione.equals("Vero")) {
					System.out.println(selezione);
				} else {
					System.out.println(selezione);
				}
				((Control) e.getSource()).setEnabled(false);
				tipoOperazione.setEnabled(false);
				tipoAssociazione.setEnabled(false);
				elencoAltriCampi.setEnabled(false);
			}
		};
	}

	private SelectionAdapter gestisciFiltroPerChar(final Text textInserimento, final CCombo tipoOperazione, final CCombo tipoAssociazione,
			final CCombo elencoAltriCampi, DynNode currNode) {
		final DynNode item = currNode;
		return new org.eclipse.swt.events.SelectionAdapter() {
			@SuppressWarnings("unchecked")
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {

				DynNode pathPadre = dynAlbero.get(item.getTreeNode().getParentItem());
//				String path = pathPadre.getPathClass().substring(pathPadre.getPathClass().indexOf(".") + 1, pathPadre.getPathClass().length());
				if (pathPadre.getPathClass().equalsIgnoreCase(filtroQuery.getClass().getCanonicalName())) {
					aggiungiRestrizione(textInserimento, tipoOperazione, tipoAssociazione, elencoAltriCampi, item, STRING);
					// criteria.add(Expression.eq(item.getTreeNode().getText(),
					// textInserimento.getText()));
				} else {
					// si costruisce a ritroso il percorso
					ArrayList<String> ramo = new ArrayList<String>();
					DynNode current = dynAlbero.get(item.getTreeNode().getParentItem());
					; // turno
					DynNode currentPadre = dynAlbero.get(current.getTreeNode().getParentItem()); // prestaziones
					while (!current.getPathClass().equalsIgnoreCase(filtroQuery.getClass().getCanonicalName())) {
						String currentNode = "";

						Class currentClass = null;
						try {
							currentClass = Class.forName(currentPadre.getPathClass());
						} catch (ClassNotFoundException e1) {
							e1.printStackTrace();
						}
						Field currentFields[] = currentClass.getDeclaredFields();
						ArrayList<Field> fieldClasse = new ArrayList<Field>();
						for (int i = 0; i < currentFields.length; i++) {
							fieldClasse.add(currentFields[i]);
						}
						while (fieldClasse.size() > 0) {
							Field currField = fieldClasse.get(0);
							// si becca se è un hashset
							String matching = currField.getName().toLowerCase();
							if (matching.contains(current.getTreeNode().getText().toLowerCase())) {
								if (currField.getType().isInstance(new HashSet<Object>())) {
									currentNode = current.getTreeNode().getText().toLowerCase().concat("s");
								} else {
									currentNode = current.getTreeNode().getText().toLowerCase();
								}
								ramo.add(currentNode);
							}
							fieldClasse.remove(currField);
						}
						current = dynAlbero.get(current.getTreeNode().getParentItem());
						currentPadre = dynAlbero.get(currentPadre.getTreeNode().getParentItem());
					}
					ramo = Utils.inversione(ramo);
					for (int i = 0; i < ramo.size(); i++) {
						criteria = criteria.createCriteria(ramo.get(i));
					}
					aggiungiRestrizione(textInserimento, tipoOperazione, tipoAssociazione, elencoAltriCampi, item, STRING);
					// criteria.add(Restrictions.eq(item.getTreeNode().getText(),
					// textInserimento.getText()));
				}
				((Control) e.getSource()).setEnabled(false);
				textInserimento.setEnabled(false);
				tipoOperazione.setEnabled(false);
				tipoAssociazione.setEnabled(false);
				elencoAltriCampi.setEnabled(false);
			}
		};
	}

	private SelectionAdapter gestisciFiltroPerStringa(final Text textInserimento, final CCombo tipoOperazione, final CCombo tipoAssociazione,
			final CCombo elencoAltriCampi, DynNode currNode) {
		final DynNode item = currNode;
		tipoOperazione.removeAll();
		tipoOperazione.add("UGUALE", 0);
		tipoOperazione.add("DIVERSO", 1);
		tipoOperazione.add("INIZIA CON", 2);
		tipoOperazione.add("FINISCE CON", 3);
		tipoOperazione.add("CONTIENE", 4);
		tipoOperazione.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
			
				if (tipoOperazione.getSelectionIndex() == 2 || tipoOperazione.getSelectionIndex() == 3 || tipoOperazione.getSelectionIndex() == 4)
					elencoAltriCampi.setEnabled(false);
				else
					elencoAltriCampi.setEnabled(true);
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});
		return new org.eclipse.swt.events.SelectionAdapter() {
			@SuppressWarnings("unchecked")
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				DynNode pathPadre = dynAlbero.get(item.getTreeNode().getParentItem());
//				String path = pathPadre.getPathClass().substring(pathPadre.getPathClass().indexOf(".") + 1, pathPadre.getPathClass().length());
				if (pathPadre.getPathClass().equalsIgnoreCase(filtroQuery.getClass().getCanonicalName())) {
					aggiungiRestrizioneStringa(textInserimento, tipoOperazione, tipoAssociazione, elencoAltriCampi, item);
					// criteria.add(Expression.eq(item.getTreeNode().getText(),
					// textInserimento.getText()));
				} else {
					// si costruisce a ritroso il percorso
					ArrayList<String> ramo = new ArrayList<String>();
					DynNode current = dynAlbero.get(item.getTreeNode().getParentItem());
					; // turno
					DynNode currentPadre = dynAlbero.get(current.getTreeNode().getParentItem()); // prestaziones
					while (!current.getPathClass().equalsIgnoreCase(filtroQuery.getClass().getCanonicalName())) {
						String currentNode = "";

						Class currentClass = null;
						try {
							currentClass = Class.forName(currentPadre.getPathClass());
						} catch (ClassNotFoundException e1) {
							e1.printStackTrace();
						}
						Field currentFields[] = currentClass.getDeclaredFields();
						ArrayList<Field> fieldClasse = new ArrayList<Field>();
						for (int i = 0; i < currentFields.length; i++) {
							fieldClasse.add(currentFields[i]);
						}
						while (fieldClasse.size() > 0) {
							Field currField = fieldClasse.get(0);
							// si becca se è un hashset
							String matching = currField.getName().toLowerCase();
							if (matching.contains(current.getTreeNode().getText().toLowerCase())) {
								if (currField.getType().isInstance(new HashSet<Object>())) {
									currentNode = current.getTreeNode().getText().toLowerCase().concat("s");
								} else {
									currentNode = current.getTreeNode().getText().toLowerCase();
								}
								ramo.add(currentNode);
							}
							fieldClasse.remove(currField);
						}
						current = dynAlbero.get(current.getTreeNode().getParentItem());
						currentPadre = dynAlbero.get(currentPadre.getTreeNode().getParentItem());
					}
					ramo = Utils.inversione(ramo);
					for (int i = 0; i < ramo.size(); i++) {
						criteria = criteria.createCriteria(ramo.get(i));
					}
					aggiungiRestrizioneStringa(textInserimento, tipoOperazione, tipoAssociazione, elencoAltriCampi, item);
					// criteria.add(Restrictions.eq(item.getTreeNode().getText(),
					// textInserimento.getText()));
				}
				((Control) e.getSource()).setEnabled(false);
				textInserimento.setEnabled(false);
				tipoOperazione.setEnabled(false);
				tipoAssociazione.setEnabled(false);
				elencoAltriCampi.setEnabled(false);
			}
		};
	}

	private SelectionAdapter elaboraFiltro(final Text textInserimento, final CCombo tipoOperazione, final CCombo tipoAssociazione,
			final CCombo elencoAltriCampi, DynNode currNode) {
		final DynNode item = currNode;
		return new org.eclipse.swt.events.SelectionAdapter() {
			@SuppressWarnings("unchecked")
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				DynNode pathPadre = dynAlbero.get(item.getTreeNode().getParentItem());
//				String path = pathPadre.getPathClass().substring(pathPadre.getPathClass().indexOf(".") + 1, pathPadre.getPathClass().length());
				if (pathPadre.getPathClass().equalsIgnoreCase(filtroQuery.getClass().getCanonicalName())) {
					aggiungiRestrizione(textInserimento, tipoOperazione, tipoAssociazione, elencoAltriCampi, item, STRING);
				} else {
					// si costruisce a ritroso il percorso
					ArrayList<String> ramo = new ArrayList<String>();
					DynNode current = dynAlbero.get(item.getTreeNode().getParentItem());
					; // turno
					DynNode currentPadre = dynAlbero.get(current.getTreeNode().getParentItem()); // prestaziones
					while (!current.getPathClass().equalsIgnoreCase(filtroQuery.getClass().getCanonicalName())) {
						String currentNode = "";

						Class currentClass = null;
						try {
							currentClass = Class.forName(currentPadre.getPathClass());
						} catch (ClassNotFoundException e1) {
							e1.printStackTrace();
						}
						Field currentFields[] = currentClass.getDeclaredFields();
						ArrayList<Field> fieldClasse = new ArrayList<Field>();
						for (int i = 0; i < currentFields.length; i++) {
							fieldClasse.add(currentFields[i]);
						}
						while (fieldClasse.size() > 0) {
							Field currField = fieldClasse.get(0);
							// si becca se è un hashset
							String matching = currField.getName().toLowerCase();
							if (matching.contains(current.getTreeNode().getText().toLowerCase())) {
								if (currField.getType().isInstance(new HashSet<Object>())) {
									currentNode = current.getTreeNode().getText().toLowerCase().concat("s");
								} else {
									currentNode = current.getTreeNode().getText().toLowerCase();
								}
								ramo.add(currentNode);
							}
							fieldClasse.remove(currField);
						}
						current = dynAlbero.get(current.getTreeNode().getParentItem());
						currentPadre = dynAlbero.get(currentPadre.getTreeNode().getParentItem());
					}
					ramo = Utils.inversione(ramo);
					for (int i = 0; i < ramo.size(); i++) {
						criteria = criteria.createCriteria(ramo.get(i));
					}
					aggiungiRestrizione(textInserimento, tipoOperazione, tipoAssociazione, elencoAltriCampi, item, STRING);
					// criteria.add(Restrictions.eq(item.getTreeNode().getText(),
					// textInserimento.getText()));
				}
				((Control) e.getSource()).setEnabled(false);
				textInserimento.setEnabled(false);
				tipoOperazione.setEnabled(false);
				tipoAssociazione.setEnabled(false);
				elencoAltriCampi.setEnabled(false);
			}

		};
	}

	private String getAttributePath(TreeItem nodo){
		String path = "";
		//questo medoto va bene SOLO se il treeitem in questione è una voglia: di seguito il controllo
		if (nodo.getItems().length==0) {
			DynNode current = dynAlbero.get(nodo);
			path = ("hibernate." + current.getIdMap()).replace("_",".").replace("RADICEALBERO.", "").replace(" ", "").toUpperCase();
		} else {
			DynNode current = dynAlbero.get(nodo);
			path = ("hibernate." + current.getIdMap()+"s").replace("_",".").replace("RADICEALBERO.", "").replace(" ", "").toUpperCase();
			
		}
		return path.trim();
	}
	
	// DynamicQueryDAO

	@SuppressWarnings("unchecked")
	private void initDao(String pathClasse, String nomeClasse) {
		Class c = null;
		try {
			c = Class.forName(pathClasse);
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}

		try {
			filtroQuery = c.newInstance();
		} catch (InstantiationException e1) {
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			e1.printStackTrace();
		}

		Session session = getSession();
		begin();

		criteria = session.createCriteria(filtroQuery.getClass());
	}

	@SuppressWarnings("unchecked")
	private void executeQuery() {
		// aggiungo le proiezioni
		// creaProiezione();
		try {
			result = criteria.list();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// BaseDAO

	public static Session getSession() {
		Session session = (Session) DynamicQueryView.session.get();
		if (session == null) {
			try {

				session = sessionFactory.openSession();
			} catch (HibernateException ex) {

				@SuppressWarnings("unused")
				String msg = "Method = getSession; Calling sessionFactory.openSession(); Thrown: ";

			}
			DynamicQueryView.session.set(session);
		}
		return session;
	}

	protected static void begin() {

		try {
			getSession().beginTransaction();
		} catch (HibernateException ex) {
			@SuppressWarnings("unused")
			String msg = "Method = begin(); Calling getSession().beginTransaction(); Thrown: ";
		}
	}

	protected static void commit() {
		Transaction tx = null;
		try {
			tx = getSession().getTransaction();
			tx.commit();
		} catch (HibernateException ex) {
			@SuppressWarnings("unused")
			String msg = "Method = commit(); Calling getSession().getTransaction().commit(); Thrown: ";
		}
	}

	protected void rollback() {
		Transaction tx = getSession().getTransaction();
		try {
			tx.rollback();
		} catch (HibernateException e) {
			System.out.println("Cannot rollback Hibernate transaction" + e.getMessage());
		}
		try {
			getSession().close();
		} catch (HibernateException e) {
			System.out.println("Cannot close Hibernate session:" + e.getMessage());
		}
		DynamicQueryView.session.set(null);
	}

	public static void close() {
		getSession().close();
		DynamicQueryView.session.set(null);
	}

	@SuppressWarnings("deprecation")
	private void aggiungiRestrizione(final Text textInserimento, final CCombo tipoOperazione, final CCombo tipoAssociazione,
			final CCombo cboAltroCampo, final DynNode item, String tipo) {
		SimpleExpression restr = null;
		PropertyExpression propRestr = null;
		String criterio = textInserimento.getText();
		Object valore = "";
		if (INTEGER.equals(tipo))
			valore = new Integer(criterio);
		else if (DATE.equals(tipo))
			valore = new Date(criterio);
		else if (BOOL.equals(tipo))
			valore = new Boolean(criterio);
		String altroCampo = cboAltroCampo.getText();
		boolean property = true;
		if ("".equals(valore)) {
			valore = textInserimento.getText();
			property = false;
		}
		System.out.println(item.getTreeNode().getText());
		System.out.println(altroCampo);
		switch (tipoOperazione.getSelectionIndex()) {
		case 0:
			item.getTreeNode().setText(new String[] { item.getTreeNode().getText(), " = " + valore });
			if (property)
				propRestr = Restrictions.eqProperty(item.getTreeNode().getText(), altroCampo);
			else
				restr = Restrictions.eq(item.getTreeNode().getText(), valore);
			break;
		case 1:
			item.getTreeNode().setText(new String[] { item.getTreeNode().getText(), " <> " + valore });

			if (property)
				propRestr = Restrictions.neProperty(item.getTreeNode().getText(), altroCampo);
			else
				restr = (SimpleExpression) Restrictions.ne(item.getTreeNode().getText(), valore);
			break;
		case 2:
			item.getTreeNode().setText(new String[] { item.getTreeNode().getText(), " < " + valore });
			if (property)
				propRestr = Restrictions.ltProperty(item.getTreeNode().getText(), altroCampo);
			else
				restr = Restrictions.lt(item.getTreeNode().getText(), valore);
			break;
		case 3:
			item.getTreeNode().setText(new String[] { item.getTreeNode().getText(), " <= " + valore });
			if (property)
				propRestr = Restrictions.leProperty(item.getTreeNode().getText(), altroCampo);
			else
				restr = Restrictions.le(item.getTreeNode().getText(), valore);
			break;
		case 4:
			item.getTreeNode().setText(new String[] { item.getTreeNode().getText(), " > " + valore });
			if (property)
				propRestr = Restrictions.gtProperty(item.getTreeNode().getText(), altroCampo);
			else
				restr = Restrictions.gt(item.getTreeNode().getText(), valore);
			break;
		case 5:
			item.getTreeNode().setText(new String[] { item.getTreeNode().getText(), " >= " + valore });
			if (property)
				propRestr = Restrictions.geProperty(item.getTreeNode().getText(), altroCampo);
			else
				restr = Restrictions.ge(item.getTreeNode().getText(), valore);
			break;
		default:
			item.getTreeNode().setText(new String[] { item.getTreeNode().getText(), " = " + valore });
			restr = Restrictions.eq(item.getTreeNode().getText(), valore);
			break;
		}
		switch (tipoAssociazione.getSelectionIndex()) {
		case 0:
			item.getTreeNode().setText(new String[] { item.getTreeNode().getText(0), item.getTreeNode().getText(1) + " (AND) " });
			if (restr != null)
				criteria.add(restr);
			else
				criteria.add(propRestr);
			break;
		case 1:
			item.getTreeNode().setText(new String[] { item.getTreeNode().getText(0), item.getTreeNode().getText(1) + " (OR) " });
			if (restr != null)
				criteria.add(Restrictions.disjunction().add(restr));
			else
				criteria.add(Restrictions.disjunction().add(propRestr));
		case 2:
			item.getTreeNode().setText(new String[] { item.getTreeNode().getText(0), item.getTreeNode().getText(1) + " (NOT) " });
			if (restr != null)
				criteria.add(Restrictions.not(restr));
			else
				criteria.add(Restrictions.not(propRestr));
		default:
			break;
		}
	}

	private void aggiungiRestrizioneStringa(final Text textInserimento, final CCombo tipoOperazione, final CCombo tipoAssociazione,
			final CCombo cboAltroCampo, final DynNode item) {
		SimpleExpression restr = null;
		PropertyExpression propRestr = null;
//		String criterio = textInserimento.getText();
		Object valore = "";
		String altroCampo = cboAltroCampo.getText();
		boolean property = true;
		if ("".equals(valore)) {
			valore = textInserimento.getText();
			property = false;
		}
		// tipoOperazione.add("UGUALE",0);
		// tipoOperazione.add("DIVERSO",0);
		// tipoOperazione.add("INIZIA CON",0);
		// tipoOperazione.add("FINISCE CON",0);
		// tipoOperazione.add("CONTIENE",0);
		switch (tipoOperazione.getSelectionIndex()) {
		case 0:
			item.getTreeNode().setText(new String[] { item.getTreeNode().getText(), " = " + valore });
			if (property)
				propRestr = Restrictions.eqProperty(item.getTreeNode().getText(), altroCampo);
			else
				restr = Restrictions.eq(item.getTreeNode().getText(), valore);
			break;
		case 1:
			item.getTreeNode().setText(new String[] { item.getTreeNode().getText(), " <> " + valore });
			restr = (SimpleExpression) Restrictions.ne(item.getTreeNode().getText(), valore);
			break;
		case 2:
			item.getTreeNode().setText(new String[] { item.getTreeNode().getText(), " INIZIA CON " + valore });
			restr = Restrictions.like(item.getTreeNode().getText(), valore + "%");
			break;
		case 3:
			item.getTreeNode().setText(new String[] { item.getTreeNode().getText(), " FINISCE CON " + valore });
			restr = Restrictions.like(item.getTreeNode().getText(), "%" + valore);
			break;
		case 4:
			item.getTreeNode().setText(new String[] { item.getTreeNode().getText(), " CONTIENE " + valore });
			restr = Restrictions.like(item.getTreeNode().getText(), "%" + valore + "%");
			break;
		case 5:
			item.getTreeNode().setText(new String[] { item.getTreeNode().getText(), " >= " + valore });
			restr = Restrictions.ge(item.getTreeNode().getText(), valore);
			break;
		default:
			item.getTreeNode().setText(new String[] { item.getTreeNode().getText(), " = " + valore });
			restr = Restrictions.eq(item.getTreeNode().getText(), valore);
			break;
		}
		switch (tipoAssociazione.getSelectionIndex()) {
		case 0:
			item.getTreeNode().setText(new String[] { item.getTreeNode().getText(0), item.getTreeNode().getText(1) + " (AND) " });
			if (restr != null)
				criteria.add(restr);
			else
				criteria.add(propRestr);
			break;
		case 1:
			item.getTreeNode().setText(new String[] { item.getTreeNode().getText(0), item.getTreeNode().getText(1) + " (OR) " });
			if (restr != null)
				criteria.add(Restrictions.disjunction().add(restr));
			else
				criteria.add(Restrictions.disjunction().add(propRestr));
		case 2:
			item.getTreeNode().setText(new String[] { item.getTreeNode().getText(0), item.getTreeNode().getText(1) + " (NOT) " });
			if (restr != null)
				criteria.add(Restrictions.not(restr));
			else
				criteria.add(Restrictions.not(propRestr));
		default:
			break;
		}
	}

	private void riempiComboPerTipo(String tipo, CCombo cboAltroCampo) {
		for (Entry<TreeItem, DynNode> item : dynAlbero.entrySet()) {
			if (((DynNode) item.getValue()).getPathClass().equals(tipo)) {
				System.out.println(((DynNode) item.getValue()).getIdMap());
				System.out.println(((DynNode) item.getValue()).getPathClass());
				System.out.println(((DynNode) item.getValue()).getTreeNode().getText());
				// System.out.println(((DynNode) item.getValue()).getIdMap());
				String padreCurrent = "";
				padreCurrent = ((DynNode) item.getValue()).getTreeNode().getParentItem().getText();
				if (padreCurrent.equals("")) {
					cboAltroCampo.add(((DynNode) item.getValue()).getTreeNode().getText());
				} else {
					cboAltroCampo.add(padreCurrent +"."+ ((DynNode) item.getValue()).getTreeNode().getText());
				}
				

			}
		}
	}
}
