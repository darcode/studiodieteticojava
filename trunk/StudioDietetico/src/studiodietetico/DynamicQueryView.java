package studiodietetico;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Image;
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
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Restrictions;
import org.hibernate.jdbc.BorrowedConnectionProxy;
import org.hibernate.proxy.HibernateProxy;

import service.Costanti;
import service.DynNode;
import service.Utils;

import common.GenericBean;
import common.HibernateUtils;

public class DynamicQueryView extends ViewPart {
	private static final Font					font					= common.Utils.getFont("Arial", 9, SWT.BOLD);
	private static final ThreadLocal<Session>	session					= new ThreadLocal<Session>();
	private static final SessionFactory			sessionFactory			= new Configuration().configure().buildSessionFactory();
	private Composite							compFiltro;
	private Composite							top						= null;
	private Tree								tree					= null;
	private Combo								comboSelezioneEntita	= null;
	private Label								labelSelezioneEntita	= null;
	private HashSet<String>						nodiVisitati			= new HashSet<String>();
	private Button								button					= null;
	private Tree								visualizzaRisultati		= null;
	private Shell								sShell1					= null;
	private Label								label2					= null;
	private Button								ok						= null;
	private HashMap<TreeItem, DynNode>			dynAlbero				= new HashMap<TreeItem, DynNode>();
	private HashMap<String, String>				selectedEntities		= new HashMap();
	// ShellInserimento
	// private DynNode item = null;
	// private Button buttonOkInserimento = null;
	// private Label etichettaInserimento = null;
	// private Text textInserimento = null;
	// private Button buttonCancellaInserimento = null;
	// private Button buttonMatchingInserimento = null;
	// private CCombo cComboInserimento = null;
	// private CCombo cCombo1Inserimento = null;
	private Composite							cmpFiltri				= null;

	// DynamicQueryDAO
	public Criteria								criteria;
	private Object								filtroQuery;
	private List								result;

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
				System.out.println(dynAlbero.keySet());
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
						feelTableResult(figlio, row, true, 0);
					}

				}
				System.out.println(selectedEntities.keySet());
			}
		});
		Button filtra = new Button(top, SWT.NONE);
		filtra.setText("Filtra");
		filtra.setBounds(new Rectangle(500, 5, 44, 27));
		filtra.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {

				TreeItem root = visualizzaRisultati.getTopItem();
				disposeChild(root);
			}

			private void disposeChild(TreeItem root) {
				for (TreeItem figlio : root.getItems()) {
					if (figlio.getChecked())
						figlio.dispose();
					else
						disposeChild(figlio);
				}
			}
		});
		tree = new Tree(top, SWT.CHECK | SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		GridData gdTree = new GridData();
		gdTree.horizontalAlignment = SWT.FILL;
		gdTree.verticalAlignment = SWT.FILL;
		gdTree.grabExcessHorizontalSpace = true;
		gdTree.grabExcessVerticalSpace = true;
		gdTree.horizontalSpan = 2;
		tree.setLayoutData(gdTree);
		tree.setLayout(new GridLayout());
		tree.setHeaderVisible(true);
		tree.addMouseListener(new org.eclipse.swt.events.MouseAdapter() {
			public void mouseDoubleClick(org.eclipse.swt.events.MouseEvent e) {
				// check se è¨ selezionato e se è foglia
				TreeItem[] arr = tree.getSelection();
				if (arr[0] != null) {
					TreeItem item = arr[0];
					if (!item.getText().substring(0, 1).equals(item.getText().substring(0, 1).toUpperCase())) {

						DynNode currentNode = dynAlbero.get(item);
						createCompositeInserimento(currentNode);
					}
				}
			}
		});
		tree.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
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
										String nome = figli[i].getText();
										TreeItem nodoPadre = figli[i].getParentItem();
										while (nodoPadre != null) {
											nome += "." + nodoPadre.getText();
											nodoPadre = nodoPadre.getParentItem();
										}
										selectedEntities.put(nome, nome);
									} else {
										figli[i].setChecked(false);
									}
								}
							}
						} else {
							b = true;
						}
					}
					if (!b) {
						createSShell1();
						sShell1.open();
						arr[0].setChecked(false);
					}
				}
			}
		});

		GridData gdTree1 = new GridData();
		gdTree1.horizontalAlignment = SWT.FILL;
		gdTree1.verticalAlignment = SWT.FILL;
		gdTree1.grabExcessHorizontalSpace = true;
		gdTree1.grabExcessVerticalSpace = true;
		gdTree1.horizontalSpan = 2;
		visualizzaRisultati = new Tree(top, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.CHECK);
		visualizzaRisultati.setHeaderVisible(true);
		visualizzaRisultati.setLinesVisible(true);
		visualizzaRisultati.setLayoutData(gdTree1);
		visualizzaRisultati.setLayout(new GridLayout());
		TreeColumn colFiltro = new TreeColumn(tree, SWT.CENTER);
		colFiltro.setText("Filtro");
		colFiltro.setWidth(200);
		TreeColumn colValore = new TreeColumn(tree, SWT.CENTER);
		colValore.setText("Valore");
		colValore.setWidth(200);
		cmpFiltri = new Composite(top, SWT.BORDER);
		GridData gdFiltri = new GridData();
		gdFiltri.horizontalAlignment = SWT.FILL;
		gdFiltri.verticalAlignment = SWT.FILL;
		gdFiltri.grabExcessHorizontalSpace = true;
		gdFiltri.grabExcessVerticalSpace = true;
		gdFiltri.horizontalSpan = 4;
		cmpFiltri.setLayoutData(gdFiltri);
		GridLayout glFiltri = new GridLayout();
		glFiltri.numColumns = 4;
		cmpFiltri.setLayout(glFiltri);

	}

	protected void feelTableResult(TreeItem node, Object item, boolean isRoot, int depth) {
		if (depth < 20 && !fermaEspansione(node)) {
			try {
				if (item != null && (item instanceof Set) && !isRoot) {
					for (Object itemFiglio : ((Set) item)) {
						depth++;
						if (itemFiglio != null) {
							TreeItem figlio = new TreeItem(node, SWT.NONE);
							figlio.setText(itemFiglio.getClass().getSimpleName().toUpperCase());
							figlio.setFont(font);
							feelTableResult(figlio, itemFiglio, false, depth);
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
								TreeItem figlio = new TreeItem(node, SWT.NONE);
								figlio.setText(campo.toUpperCase());
								figlio.setFont(font);
								feelTableResult(figlio, GenericBean.getProperty(campo, item), false, depth);
							} else {
								TreeItem treeItem = new TreeItem(node, SWT.NONE);
								String label = "" + campo;
								while (label.length() < 25)
									label = " " + label;
								treeItem.setText(label.toUpperCase() + ":             " + (GenericBean.getProperty(campo, item)));
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

	/**
	 * This method initializes comboSelezioneEntita
	 */
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
				tree.removeAll();
				TreeItem radice = new TreeItem(tree, SWT.NONE);
				radice.setText(new String[] { nomeClasse });
				nodiVisitati.clear();

				espandiAlbero(nomeClasse, pathClasse, radice);
				comboSelezioneEntita.setEnabled(false);
				initDao(pathClasse, nomeClasse);
			}

			public void widgetDefaultSelected(org.eclipse.swt.events.SelectionEvent e) {
			}
		});
	}

	/**
	 * This method initializes sShell1
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

	public void espandiAlbero(String nomeClasse, String pathClasse, TreeItem radice
	/*
	 * , HashSet < String > nodiVisitati , Tree inizioAlbero , HashMap dynAlbero
	 */) {
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

	// ShellPopUp

	public void createCompositeInserimento(final DynNode item) {
		// item = currentItem;
		GridLayout glFiltro = new GridLayout();
		glFiltro.numColumns = 2;
		compFiltro = new Composite(cmpFiltri, SWT.BORDER);
		compFiltro.setLayout(glFiltro);
		// Image imageFromFile =
		// common.Utils.getImageFromFile("icons/filter.jpg");
		// imageFromFile.getImageData().scaledTo(30, 30);
		// Composite cmp = new Composite(compFiltro, SWT.NONE);
		// cmp.setBounds(new Rectangle(10, 10, 30, 30));
		// cmp.setBackgroundImage(imageFromFile);
		// sShellInserimento.setImage(imageFromFile);
		GridData gdFiltri = new GridData();
		gdFiltri.grabExcessHorizontalSpace = true;
		gdFiltri.grabExcessVerticalSpace = true;
		gdFiltri.horizontalAlignment = SWT.FILL;
		gdFiltri.verticalAlignment = SWT.FILL;
		compFiltro.setLayoutData(gdFiltri);
		Label titolo = new Label(compFiltro, SWT.NONE);
		GridData gdTitolo = new GridData();
		gdTitolo.horizontalSpan = 2;
		titolo.setLayoutData(gdTitolo);
		titolo.setText("Filtro su " + item.getTreeNode().getText());
		titolo.setFont(font);
		Label etichettaInserimento = new Label(compFiltro, SWT.NONE);
		Text textInserimento = new Text(compFiltro, SWT.BORDER);
		SelectionAdapter listener = null;
		if (item.getPathClass().contains("Integer") | item.getPathClass().contains("int")) {
			etichettaInserimento.setText("Inserisci un INTERO");
		} else if (item.getPathClass().contains("Double") | item.getPathClass().contains("double")) {
			etichettaInserimento.setText("Inserisci un DECIMALE");
			listener = gestisciFiltroDecimale(textInserimento, item);
		} else if (item.getPathClass().contains("Date")) {
			etichettaInserimento.setText("Inserisci una DATA");
			gestisciFiltroData(item);
		} else if (item.getPathClass().contains("Boolean") | item.getPathClass().contains("boolean")) {
			etichettaInserimento.setText("Inserisci VERO/FALSO");
			textInserimento.setVisible(false);
			Label fill = new Label(compFiltro,SWT.NONE);
			listener = gestisciFiltroPerBoolean(item);
		} else if (item.getPathClass().contains("Char") | item.getPathClass().contains("char")) {
			etichettaInserimento.setText("Inserisci un CARATTERE");
			textInserimento.setTextLimit(1);
			listener = gestisciFiltroPerChar( textInserimento, item);
		} else if (item.getPathClass().contains("String")) {
			etichettaInserimento.setText("Inserisci una STRINGA");
			listener = gestisciFiltroPerStringa(textInserimento, item);
		} else {
			System.out.println(item.getPathClass() + " ---> " + item.getTreeNode().getText());
		}
		Label etichettaOperazione = new Label(compFiltro, SWT.NONE);
		etichettaOperazione.setText("Operazione:");
		CCombo comboOperazione = new CCombo(compFiltro, SWT.NONE);
		comboOperazione.add(" = (Uguale)",0);
		comboOperazione.add("<> (Diverso)",1);
		comboOperazione.add(" < (Minore)",2);
		comboOperazione.add("=< (Minore o Uguale)",3);
		comboOperazione.add(" > (Maggiore)",4);
		comboOperazione.add(">= (Maggiore o Uguale)",5);
		comboOperazione.select(0);
		Button buttonMatchingInserimento = new Button(compFiltro, SWT.NONE);
		buttonMatchingInserimento.setText("Confronta con:");
		final Combo elencoAltriCampi = new Combo(compFiltro, SWT.NONE);
		GridLayout glButton = new GridLayout();
		glButton.numColumns = 2;
		Label lbl = new Label(compFiltro,SWT.NONE);
		lbl.setText("Aggiungi in: ");
		Combo cboTipoOperatore = new Combo(compFiltro,SWT.NONE);
		cboTipoOperatore.add("AND",0);
		cboTipoOperatore.add("OR",1);
		cboTipoOperatore.add("NOT",2);
		cboTipoOperatore.select(0);
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
		buttonMatchingInserimento.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				elencoAltriCampi.getSelectionIndex();
			}
		});
		if(listener == null){
			buttonOkInserimento.addSelectionListener(elaboraFiltro(textInserimento, item));
		}else
			buttonOkInserimento.addSelectionListener(listener);
		
		compFiltro.layout();
		cmpFiltri.layout();
	}

	private SelectionAdapter gestisciFiltroDecimale(final Text textInserimento, DynNode currNode) {
		final DynNode item = currNode;
		CCombo cCombo1Inserimento = new CCombo(compFiltro, SWT.NONE);
		cCombo1Inserimento.add("<");
		cCombo1Inserimento.add("=<");
		cCombo1Inserimento.add(">");
		cCombo1Inserimento.add(">=");
		cCombo1Inserimento.add("=");
		// buttonMatchingInserimento.addSelectionListener(new
		// org.eclipse.swt.events.SelectionAdapter() {
		// public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
		// System.out.println("dare la possibilità di selezionare un altro campo (dello stesso tipo) dall'albero della query");
		// }
		// });
		return new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent eS) {

				item.getTreeNode().setText(new String[] { item.getTreeNode().getText(), textInserimento.getText() });
				DynNode pathPadre = dynAlbero.get(item.getTreeNode().getParentItem());
				String path = pathPadre.getPathClass().substring(pathPadre.getPathClass().indexOf(".") + 1, pathPadre.getPathClass().length());
				if (pathPadre.getPathClass().equalsIgnoreCase(filtroQuery.getClass().getCanonicalName())) {
					criteria.add(Expression.eq(item.getTreeNode().getText(), textInserimento.getText()));
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
							// TODO Auto-generated catch block
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
					criteria.add(Restrictions.eq(item.getTreeNode().getText(), textInserimento.getText()));
				}
				// sShellInserimento.close();
				compFiltro.dispose();

			}
		};
	}

	private SelectionAdapter gestisciFiltroData(DynNode currNode) {
		final DynNode item = currNode;
		GridData gridData6 = new GridData();
		gridData6.horizontalAlignment = GridData.CENTER;
		gridData6.verticalAlignment = GridData.CENTER;
		GridData gridData5 = new GridData();
		gridData5.horizontalAlignment = GridData.CENTER;
		gridData5.verticalAlignment = GridData.CENTER;
		GridData gridData4 = new GridData();
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
		final DateTime calendar = new DateTime(compFiltro, SWT.CALENDAR | SWT.BORDER);
		calendar.setLayoutData(gridData1);
		calendar.addSelectionListener(new org.eclipse.swt.events.SelectionListener() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				System.out.println("widgetSelected()");
			}

			public void widgetDefaultSelected(org.eclipse.swt.events.SelectionEvent e) {
			}
		});
		final DateTime time = new DateTime(compFiltro, SWT.TIME | SWT.SHORT);
		time.setLayoutData(gridData);
		button = new Button(compFiltro, SWT.NONE);
		button.setText("Confronta con un altro campo");
		button.setLayoutData(gridData4);
		Label filler17 = new Label(compFiltro, SWT.NONE);
		return new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				System.out.println("Calendar date selected (MM/DD/YYYY) = " + (calendar.getMonth() + 1) + "/" + calendar.getDay() + "/"
						+ calendar.getYear());
				System.out.println("Time selected (HH:MM) = " + time.getHours() + ":" + (time.getMinutes() < 10 ? "0" : "") + time.getMinutes());
				String dateString = calendar.getYear() + "-" + (calendar.getMonth() + 1) + "-" + calendar.getDay() + " " + time.getHours() + ":"
						+ (time.getMinutes() < 10 ? "0" : "") + time.getMinutes() + ":00";
				String formato = "yyyy-MM-dd HH:mm:ss";
				Date selectedData = Utils.convertStringToDate(dateString, formato);

				item.getTreeNode().setText(new String[] { item.getTreeNode().getText(), selectedData.toString() });
				// TODO inserire il criteria adeguato
			}
		};
	}

	private SelectionAdapter gestisciFiltroPerBoolean(DynNode currNode) {
		final DynNode item = currNode;
		// compFiltro = new Composite(parent,SWT.NONE);
		// compFiltro.setLayout(new GridLayout());
		// compFiltro.setLayoutData(new GridData(SWT.FILL));
		final CCombo cComboInserimento = new CCombo(compFiltro, SWT.NONE);
		cComboInserimento.add("Vero");
		cComboInserimento.add("Falso");
		cComboInserimento.setEditable(false);
		cComboInserimento.select(0);
		return new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				String selezione = cComboInserimento.getText();
				item.getTreeNode().setText(new String[] { item.getTreeNode().getText(), selezione });

				if (selezione.equals("Vero")) {
					// TODO costruire il Criteria adeguato
					System.out.println(selezione);
				} else {
					// TODO costruire il Criteria adeguato
					System.out.println(selezione);
				}
			}
		};
	}

	private SelectionAdapter gestisciFiltroPerChar(final Text textInserimento, DynNode currNode) {
		final DynNode item = currNode;
		// buttonMatchingInserimento.addSelectionListener(new
		// org.eclipse.swt.events.SelectionAdapter() {
		// public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
		// System.out.println("dare la possibilità di selezionare un altro campo (dello stesso tipo) dall'albero della query");
		// }
		// });
		return new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {

				item.getTreeNode().setText(new String[] { item.getTreeNode().getText(), textInserimento.getText() });
				DynNode pathPadre = dynAlbero.get(item.getTreeNode().getParentItem());
				String path = pathPadre.getPathClass().substring(pathPadre.getPathClass().indexOf(".") + 1, pathPadre.getPathClass().length());
				if (pathPadre.getPathClass().equalsIgnoreCase(filtroQuery.getClass().getCanonicalName())) {
					criteria.add(Expression.eq(item.getTreeNode().getText(), textInserimento.getText()));
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
							// TODO Auto-generated catch block
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
					criteria.add(Restrictions.eq(item.getTreeNode().getText(), textInserimento.getText()));
				}

			}
		};
	}

	private SelectionAdapter gestisciFiltroPerStringa(final Text textInserimento, DynNode currNode) {
		final DynNode item = currNode;
		// buttonMatchingInserimento.addSelectionListener(new
		// org.eclipse.swt.events.SelectionAdapter() {
		// public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
		// top.forceFocus();
		//
		// System.out.println("dare la possibilità di selezionare un altro campo (dello stesso tipo) dall'albero della query");
		// }
		// });
		return new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				item.getTreeNode().setText(new String[] { item.getTreeNode().getText(), textInserimento.getText() });
				DynNode pathPadre = dynAlbero.get(item.getTreeNode().getParentItem());
				String path = pathPadre.getPathClass().substring(pathPadre.getPathClass().indexOf(".") + 1, pathPadre.getPathClass().length());
				if (pathPadre.getPathClass().equalsIgnoreCase(filtroQuery.getClass().getCanonicalName())) {
					criteria.add(Expression.eq(item.getTreeNode().getText(), textInserimento.getText()));
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
							// TODO Auto-generated catch block
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
					criteria.add(Restrictions.eq(item.getTreeNode().getText(), textInserimento.getText()));
				}
			}
		};
	}

	private SelectionAdapter elaboraFiltro(final Text textInserimento, DynNode currNode) {
		final DynNode item = currNode;
		return new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				item.getTreeNode().setText(new String[] { item.getTreeNode().getText(), textInserimento.getText() });
				DynNode pathPadre = dynAlbero.get(item.getTreeNode().getParentItem());
				String path = pathPadre.getPathClass().substring(pathPadre.getPathClass().indexOf(".") + 1, pathPadre.getPathClass().length());
				if (pathPadre.getPathClass().equalsIgnoreCase(filtroQuery.getClass().getCanonicalName())) {
					criteria.add(Expression.eq(item.getTreeNode().getText(), textInserimento.getText()));
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
							// TODO Auto-generated catch block
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
					criteria.add(Restrictions.eq(item.getTreeNode().getText(), textInserimento.getText()));
				}
			}
		};
	}

	// DynamicQueryDAO

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
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		Session session = getSession();
		begin();

		criteria = session.createCriteria(filtroQuery.getClass());
	}

	private void executeQuery() {
		result = criteria.list();
	}

	// BaseDAO

	public static Session getSession() {
		Session session = (Session) DynamicQueryView.session.get();
		if (session == null) {
			try {

				session = sessionFactory.openSession();
			} catch (HibernateException ex) {

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
			String msg = "Method = begin(); Calling getSession().beginTransaction(); Thrown: ";
		}
	}

	protected static void commit() {
		Transaction tx = null;
		try {
			tx = getSession().getTransaction();
			tx.commit();
		} catch (HibernateException ex) {
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

}
