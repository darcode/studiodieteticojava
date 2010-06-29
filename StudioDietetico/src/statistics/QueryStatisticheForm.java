package statistics;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.events.SelectionAdapter;
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
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.swt.widgets.TreeItem;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.impl.CriteriaImpl;
import org.hibernate.impl.CriteriaImpl.Subcriteria;

import service.Costanti;
import service.DynNode;
import service.Utils;

public class QueryStatisticheForm extends Composite {
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
	private HashMap<TreeItem, DynNode>			dynAlbero				= new HashMap<TreeItem, DynNode>();
	private Composite							cmpFiltri				= null;
	private Table								tableRisultati;
	// DynamicQueryDAO
	public Criteria								criteria;
	private Object								filtroQuery;
	private ScrollableResults					result;
	private ProjectionList						projList;
	private ArrayList<String> criteri = new ArrayList<String>();

	public QueryStatisticheForm(Composite parent, int style) {
		super(parent, style);
		top = new Composite(this, SWT.BORDER);
		GridData gdTop = new GridData();
		gdTop.horizontalAlignment = SWT.FILL;
		gdTop.verticalAlignment = SWT.FILL;
		gdTop.grabExcessHorizontalSpace = true;
		gdTop.grabExcessVerticalSpace = true;
		gdTop.minimumHeight = 450;
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
				if (result == null || result.getRowNumber() == 0 || !result.isFirst()) {
					// popup "non ci sono risultati"
					final Shell noResults = new Shell();
					noResults.setSize(new Point(300, 150));
					Button okNoResults = new Button(noResults, SWT.NONE);
					okNoResults.setText("chiudi");
					okNoResults.setBounds(new Rectangle(100, 40, 100, 30));
					Label etichettaNoResults = new Label(noResults, SWT.NONE);
					etichettaNoResults.setBounds(new Rectangle(20, 20, 300, 50));
					etichettaNoResults.setText("L'interrogazione non ha restituito risultati");
					okNoResults.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
						public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
							noResults.close();
						}
					});
					noResults.open();
				} else {
					while (result.next()) {
						if (result.get() instanceof Object[]) {
							TableItem item = new TableItem(tableRisultati, SWT.NONE);
							String[] valori = new String[result.get().length];
							int i = 0;
							for (Object row : result.get()) {
								valori[i] = ("" + row).toString();
								i++;
							}
							item.setText(valori);
						}
					}
					button.setEnabled(false);
				}

				// System.out.println(selectedEntities.keySet());
			}
		});
		Button filtra = new Button(top, SWT.NONE);
		filtra.setText("Pulisci");
		filtra.setBounds(new Rectangle(500, 5, 44, 27));
		filtra.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				tableRisultati.clearAll();
				for (TableColumn col : tableRisultati.getColumns())
					col.dispose();
			}

		});
		treeEntity = new Tree(top, SWT.CHECK | SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		GridData gdTree = new GridData();
		gdTree.horizontalAlignment = SWT.FILL;
		gdTree.verticalAlignment = SWT.FILL;
		gdTree.grabExcessHorizontalSpace = true;
		gdTree.grabExcessVerticalSpace = true;
		gdTree.horizontalSpan = 2;
		gdTree.minimumHeight = 250;
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
		tableRisultati = new Table(top, SWT.BORDER | SWT.SINGLE | SWT.FULL_SELECTION);
		tableRisultati.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		tableRisultati.setLinesVisible(true);
		tableRisultati.setHeaderVisible(true);
		TreeColumn colFiltro = new TreeColumn(treeEntity, SWT.CENTER);
		colFiltro.setText("Filtro");
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
		gdFiltri.minimumHeight = 300;
		cmpFiltri.setLayoutData(gdFiltri);
		GridLayout glFiltri = new GridLayout();
		glFiltri.numColumns = 4;
		cmpFiltri.setLayout(glFiltri);

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
	public void espandiAlbero(String nomeClasse, String pathClasse, TreeItem radice
	/*
	 * , HashSet < String > nodiVisitati , Tree inizioAlbero , HashMap dynAlbero
	 */) {
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
			if (item.getItems().length == 0) {
				if (projList == null) {
					projList = Projections.projectionList();
				}
				if (!"".equals(getAttributePath(item))) {
					ricostruisci(dynAlbero.get(item));
					projList.add(Projections.property(getAttributePath(item)));
					// } else {
					// projList.add(Projections.property(currentNode.getTreeNode().getText()));
					// }

					TableColumn col = new TableColumn(tableRisultati, SWT.NONE);
					col.setWidth(100);
					col.setText(item.getText());
				}

			} else {
				for (TreeItem figlio : item.getItems()) {
					if (!figlio.getText().substring(0, 1).equals(figlio.getText().substring(0, 1).toUpperCase())) {
						String nome = figlio.getText();
						if (item.getChecked()) {
							DynNode currentNode = dynAlbero.get(figlio);
							figlio.setChecked(true);
							if (projList == null) {
								projList = Projections.projectionList();
							}
							if (figlio.getItems().length == 0) {
								if (!"".equals(getAttributePath(figlio))) {
									ricostruisci(dynAlbero.get(figlio));
									projList.add(Projections.property(getAttributePath(figlio)));

									// } else {
									// projList.add(Projections.property(currentNode.getTreeNode().getText()));
									// }
									TableColumn col = new TableColumn(tableRisultati, SWT.NONE);
									col.setWidth(100);
									col.setText(nome);
								}
							}
						}
					}
				}
			}
		}
	}

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
		gdFiltri.minimumHeight = 200;
		compFiltro.setLayoutData(gdFiltri);
		Label titolo = new Label(compFiltro, SWT.NONE);
		GridData gdTitolo = new GridData();
		gdTitolo.horizontalSpan = 2;
		titolo.setLayoutData(gdTitolo);
		titolo.setText("Operazione su " + item.getTreeNode().getText());
		titolo.setFont(font);
		Label lbl = new Label(compFiltro, SWT.NONE);
		lbl.setText("Operazione: ");
		CCombo cboTipoAssociazione = new CCombo(compFiltro, SWT.NONE);
		cboTipoAssociazione.add("NESSUNA", 0);
		cboTipoAssociazione.add("GROUP BY", 1);
		cboTipoAssociazione.select(0);
		Label etichettaOperazione = new Label(compFiltro, SWT.NONE);
		etichettaOperazione.setText("Aggregazione:");
		Combo comboOperazione = new Combo(compFiltro, SWT.NONE);
		comboOperazione.select(0);
		GridLayout glButton = new GridLayout();
		glButton.numColumns = 2;
		SelectionAdapter listener = null;
		if (item.getPathClass().contains("Integer") | item.getPathClass().contains("int")) {
			comboOperazione.add("MEDIA", 0);
			comboOperazione.add("MASSIMO", 1);
			comboOperazione.add("MINIMO", 2);
			comboOperazione.add("SOMMA", 3);
			comboOperazione.add("COUNT", 4);
			comboOperazione.add("COUNT-DISTINCT", 5);
			listener = gestisciFiltroIntero(comboOperazione, cboTipoAssociazione, item);
		} else if (item.getPathClass().contains("Double") | item.getPathClass().contains("double")) {
			comboOperazione.add("MEDIA", 0);
			comboOperazione.add("MASSIMO", 1);
			comboOperazione.add("MINIMO", 2);
			comboOperazione.add("SOMMA", 3);
			comboOperazione.add("COUNT", 4);
			comboOperazione.add("COUNT-DISTINCT", 5);
			listener = gestisciFiltroIntero(comboOperazione, cboTipoAssociazione, item);
		} else if (item.getPathClass().contains("Date")) {
			comboOperazione.add("DATA MEDIA", 0);
			comboOperazione.add("DATA MASSIMO", 1);
			comboOperazione.add("DATA MINIMA", 2);
		} else if (item.getPathClass().contains("Boolean") | item.getPathClass().contains("boolean")) {
		} else if (item.getPathClass().contains("Char") | item.getPathClass().contains("char")) {
			comboOperazione.add("", 0);
			comboOperazione.add("MASSIMO", 1);
			comboOperazione.add("MINIMO", 2);
			comboOperazione.add("MINIMO", 3);
			comboOperazione.add("COUNT", 4);
			comboOperazione.add("COUNT-DISTINCT", 5);
		} else if (item.getPathClass().contains("String")) {
			comboOperazione.add("", 0);
			comboOperazione.add("MASSIMO", 1);
			comboOperazione.add("MINIMO", 2);
			comboOperazione.add("", 3);
			comboOperazione.add("COUNT", 4);
			comboOperazione.add("COUNT-DISTINCT", 5);
			listener = gestisciFiltroPerStringa(comboOperazione, cboTipoAssociazione, item);
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
			buttonOkInserimento.addSelectionListener(elaboraFiltro(comboOperazione, cboTipoAssociazione, item));
		} else
			buttonOkInserimento.addSelectionListener(listener);
		compFiltro.layout();
		cmpFiltri.layout();
	}

	private SelectionAdapter gestisciFiltroIntero(final Combo tipoOperazione, final CCombo tipoAssociazione, DynNode currNode) {
		final DynNode item = currNode;
		return new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent eS) {
				DynNode pathPadre = dynAlbero.get(item.getTreeNode().getParentItem());
				String path = pathPadre.getPathClass().substring(pathPadre.getPathClass().indexOf(".") + 1, pathPadre.getPathClass().length());
				if (pathPadre.getPathClass().equalsIgnoreCase(filtroQuery.getClass().getCanonicalName())) {
					aggiungiRestrizione(tipoOperazione, tipoAssociazione, item, INTEGER);
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
						aggiungiRestrizione(tipoOperazione, tipoAssociazione, item, DECIMAL);
					}

					// criteria.add(Restrictions.eq(item.getTreeNode().getText(),
					// textInserimento.getText()));
				}
				((Control) eS.getSource()).setEnabled(false);
				tipoAssociazione.setEnabled(false);
				tipoOperazione.setEnabled(false);

			}
		};
	}

	private SelectionAdapter gestisciFiltroPerStringa(final Combo tipoOperazione, final CCombo tipoAssociazione, DynNode currNode) {
		final DynNode item = currNode;
		return new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				DynNode pathPadre = dynAlbero.get(item.getTreeNode().getParentItem());
				String path = pathPadre.getPathClass().substring(pathPadre.getPathClass().indexOf(".") + 1, pathPadre.getPathClass().length());
				if (pathPadre.getPathClass().equalsIgnoreCase(filtroQuery.getClass().getCanonicalName())) {
					aggiungiRestrizione(tipoOperazione, tipoAssociazione, item, STRING);
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
						aggiungiRestrizione(tipoOperazione, tipoAssociazione, item, STRING);
					}

					// criteria.add(Restrictions.eq(item.getTreeNode().getText(),
					// textInserimento.getText()));
				}
				((Control) e.getSource()).setEnabled(false);
				tipoAssociazione.setEnabled(false);
				tipoOperazione.setEnabled(false);
			}
		};
	}

	private SelectionAdapter elaboraFiltro(final Combo tipoOperazione, final CCombo tipoAssociazione, DynNode currNode) {
		final DynNode item = currNode;
		return new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				DynNode pathPadre = dynAlbero.get(item.getTreeNode().getParentItem());
				String path = pathPadre.getPathClass().substring(pathPadre.getPathClass().indexOf(".") + 1, pathPadre.getPathClass().length());
				if (pathPadre.getPathClass().equalsIgnoreCase(filtroQuery.getClass().getCanonicalName())) {
					aggiungiRestrizione(tipoOperazione, tipoAssociazione, item, STRING);
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
						aggiungiRestrizione(tipoOperazione, tipoAssociazione, item, STRING);
					}

					// criteria.add(Restrictions.eq(item.getTreeNode().getText(),
					// textInserimento.getText()));
				}
				((Control) e.getSource()).setEnabled(false);
				tipoAssociazione.setEnabled(false);
				tipoOperazione.setEnabled(false);
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
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			e1.printStackTrace();
		}

		Session session = getSession();
		begin();

		criteria = session.createCriteria(filtroQuery.getClass());
	}

;	private void executeQuery() {
		// aggiungo le proiezioni
		// creaProiezione();
		try {
			criteria.setProjection(projList);
			result = criteria.scroll();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// BaseDAO

	public static Session getSession() {
		Session session = (Session) QueryStatisticheForm.session.get();
		if (session == null) {
			try {

				session = sessionFactory.openSession();
			} catch (HibernateException ex) {

				String msg = "Method = getSession; Calling sessionFactory.openSession(); Thrown: ";

			}
			QueryStatisticheForm.session.set(session);
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
		}
		try {
			getSession().close();
		} catch (HibernateException e) {
		}
		QueryStatisticheForm.session.set(null);
	}

	public static void close() {
		getSession().close();
		QueryStatisticheForm.session.set(null);
	}

	private void aggiungiRestrizione(final Combo tipoOperazione, final CCombo tipoAssociazione, final DynNode item, String tipo) {
		if (projList == null) {
			projList = Projections.projectionList();
		}
		TableColumn col = new TableColumn(tableRisultati, SWT.NONE);
		col.setWidth(100);
		switch (tipoOperazione.getSelectionIndex()) {
		// comboOperazione.add("MEDIA", 0);
		// comboOperazione.add("MASSIMO", 1);
		// comboOperazione.add("MINIMO", 2);
		// comboOperazione.add("SOMMA", 3);
		// comboOperazione.add("COUNT", 4);
		// comboOperazione.add("COUNT-DISTINCT", 5);
		case 0:
			item.getTreeNode().setText(new String[] { item.getTreeNode().getText(0), item.getTreeNode().getText(1) + " (MEDIA) " });
			projList.add(Projections.avg(getAttributePath(item.getTreeNode())));
			col.setText("MEDIA " + item.getTreeNode().getText(0));
			break;
		case 1:
			item.getTreeNode().setText(new String[] { item.getTreeNode().getText(0), item.getTreeNode().getText(1) + " (MAX) " });
			projList.add(Projections.max(getAttributePath(item.getTreeNode())));
			col.setText("MAX " + item.getTreeNode().getText(0));
			break;
		case 2:
			item.getTreeNode().setText(new String[] { item.getTreeNode().getText(0), item.getTreeNode().getText(1) + " (MIN) " });
			projList.add(Projections.min(getAttributePath(item.getTreeNode())));
			col.setText("MIN " + item.getTreeNode().getText(0));
			break;
		case 3:
			item.getTreeNode().setText(new String[] { item.getTreeNode().getText(0), item.getTreeNode().getText(1) + " (SOMMA) " });
			projList.add(Projections.sum(getAttributePath(item.getTreeNode())));
			col.setText("SUM " + item.getTreeNode().getText(0));
			break;
		case 4:
			item.getTreeNode().setText(new String[] { item.getTreeNode().getText(0), item.getTreeNode().getText(1) + " (COUNT) " });
			projList.add(Projections.count(getAttributePath(item.getTreeNode())));
			col.setText("COUNT " + item.getTreeNode().getText(0));
			break;
		case 5:
			item.getTreeNode().setText(new String[] { item.getTreeNode().getText(0), item.getTreeNode().getText(1) + " (COUNT-DISTINCT) " });
			projList.add(Projections.countDistinct(getAttributePath(item.getTreeNode())));
			col.setText("COUNT-DISTINCT " + item.getTreeNode().getText(0));
			break;
		default:
			break;
		}
		switch (tipoAssociazione.getSelectionIndex()) {
		case 0:
			// nessuna
			item.getTreeNode().setText(new String[] { item.getTreeNode().getText(), "" });
			break;

		case 1:
			// group by
			item.getTreeNode().setText(new String[] { item.getTreeNode().getText(), " GROUP BY " });
			projList.add(Projections.property(getAttributePath(item.getTreeNode())));
			projList.add(Projections.groupProperty(getAttributePath(item.getTreeNode())));
			break;
		}

	}

	private void riempiComboPerTipo(String tipo, CCombo cboAltroCampo) {
		for (Entry<TreeItem, DynNode> item : dynAlbero.entrySet()) {
			if (((DynNode) item.getValue()).getPathClass().equals(tipo)) {
				cboAltroCampo.add(((DynNode) item.getValue()).getTreeNode().getText());
			}
		}
	}

	public void ricostruisci(DynNode item) {

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
			String alias = ramo.get(i).replaceFirst(ramo.get(i).substring(0, 1), ramo.get(i).substring(0, 1).toLowerCase());
			System.out.println(alias);
			if(!criteri.contains(alias)){
				criteria.createAlias(alias, alias);
//				criteria.createCriteria(alias);
				criteri.add(alias);
			}
		}
	}

	private String getAttributePath(TreeItem nodo) {
		String path = "";
		DynNode current = dynAlbero.get(nodo);
		if (nodo.getItems().length == 0) {
			if (current.getTreeNode().getParentItem().getText().replace(" ", "").equalsIgnoreCase(filtroQuery.getClass().getSimpleName().toString()))
				path = ((current.getIdMap()).substring(current.getIdMap().lastIndexOf("_") + 1)).replace("RADICEALBERO.", "").replace(" ", "");
			else {
				path = (current.getIdMap()).replace("_", "s_").replace(current.getIdMap().substring(0, 1), current.getIdMap().substring(0, 1).toLowerCase()).replace("_", ".").replace("RADICEALBERO.", "").replace(" ", "");
			}
		}
		// else {
		// if(current.getTreeNode() == filtroQuery)
		// path =
		// (current.getIdMap()).substring(current.getIdMap().lastIndexOf("_")+1).replace("RADICEALBERO.",
		// "").replace(" ", "");
		// else
		// path = (current.getIdMap() + "s").replace("_",
		// ".").replace("RADICEALBERO.", "").replace(" ", "");
		//
		// }
		path = path.replace(("hibernate." + filtroQuery.getClass().getCanonicalName() + "."), "");
		path = path.replace((filtroQuery.getClass().getSimpleName() + "."), "");
		return path.trim();
	}
}
