package statistics;

import java.util.ArrayList;
import java.util.HashMap;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

import command.MedicoDAO;
import common.GenericBean;
import common.Utils;
import common.ui.ListComposite;

public class StatisticheMainForm extends ListComposite {

	private Table tableEntitaSel;
	private Table tableEntita;
	private Table tableCampi;
	private Table tableFunct;
	private HashMap<String, Object> classi = null;
	private int numColonnaFiltro = 0;
	private Table tblRisultati;
	private String selectToExecute = "";

	private Listener listenerFiltro = new Listener() {
		private Text textUguale;
		private Text textMggioreDi;
		private Text textMinoreDi;
		private Shell filtroShell;

		public void handleEvent(Event event) {

			filtroShell = new Shell(SWT.BORDER | SWT.DIALOG_TRIM);
			filtroShell.setText("Filtro - Attenzione, non è annullabile!");
			filtroShell.setBounds(100, 100, 200, 200);
			filtroShell.setLayout(new GridLayout(2, false));
			filtroShell.setLayoutData(new GridData(SWT.FILL));
			Label lblUguale = new Label(filtroShell, SWT.NONE);
			lblUguale.setText("Uguale a:");
			textUguale = new Text(filtroShell, SWT.BORDER);
			textUguale.setLayoutData(new GridData(SWT.FILL));

			Label lblMaggioreDi = new Label(filtroShell, SWT.NONE);
			lblMaggioreDi.setText("Maggiore di:");
			textMggioreDi = new Text(filtroShell, SWT.BORDER);
			textMggioreDi.setLayoutData(new GridData(SWT.FILL));

			Label lblMinore = new Label(filtroShell, SWT.NONE);
			lblMinore.setText("Maggiore di:");
			textMinoreDi = new Text(filtroShell, SWT.BORDER);
			textMinoreDi.setLayoutData(new GridData(SWT.FILL));

			Combo compresoTra = new Combo(filtroShell, SWT.CHECK);
			compresoTra.setText("Compreso tra");
			GridData gdCompreso = new GridData();
			gdCompreso.horizontalSpan = 2;
			compresoTra.setLayoutData(gdCompreso);
			Button ok = new Button(filtroShell, SWT.NONE);
			ok.setText("OK");
			GridData gdButton = new GridData(SWT.FILL);
			gdButton.horizontalAlignment = SWT.RIGHT;
			ok.setLayoutData(gdButton);
			ok.addSelectionListener(new SelectionListener() {
				public void widgetSelected(SelectionEvent e) {
					filtraValori(new String[] { textUguale.getText() });
				}

				public void widgetDefaultSelected(SelectionEvent e) {
				}
			});
			Button annulla = new Button(filtroShell, SWT.NONE);
			annulla.setText("Annulla");
			annulla.addSelectionListener(new SelectionListener() {
				public void widgetDefaultSelected(SelectionEvent e) {
				}

				public void widgetSelected(SelectionEvent e) {
					filtroShell.dispose();
				};
			});
			filtroShell.open();
		}
	};

	public StatisticheMainForm(Composite parent, int style) {
		super(parent, style);

		// riempio l'hash map delle entità che voglio permettere di usare
		classi = StatisticheUtils.getEntitaSelezionabili();

		// this.setBackground(Utils.getStandardBackgroundColor());
		GridData gdForm = new GridData(SWT.FILL);
		gdForm.grabExcessHorizontalSpace = true;
		gdForm.horizontalAlignment = SWT.FILL;
		gdForm.verticalAlignment = SWT.FILL;
		gdForm.grabExcessVerticalSpace = true;
		this.setLayoutData(gdForm);
		GridLayout glForm = new GridLayout(2, false);
		this.setLayout(glForm);
		this.setBackground(Utils.getStandardWhiteColor());
		Label lbl = new Label(this, SWT.NONE | SWT.BOLD);
		lbl.setText("SELEZIONE CRITERI");
		lbl.setBackground(Utils.getStandardWhiteColor());
		// AREE FUNZIONALI - ENTITA'
		Composite compTop = new Composite(this, SWT.TOP | SWT.BORDER
				| SWT.SCROLL_PAGE);
		compTop.setBackground(Utils.getStandardWhiteColor());
		GridLayout glTop = new GridLayout(2, true);
		glTop.horizontalSpacing = 0;
		glTop.marginLeft = 0;
		glTop.marginRight = 0;
		compTop.setLayout(glTop);
		GridData gdTop = new GridData();
		gdTop.grabExcessHorizontalSpace = true;
		gdTop.horizontalSpan = 2;
		gdTop.horizontalAlignment = SWT.FILL;
		gdTop.heightHint = 250;
		compTop.setLayoutData(gdTop);
		// CAMPI
		Composite compLeft = new Composite(compTop, SWT.NONE);
		// comp.setBackground(Utils.getStandardWhiteColor());
		GridData gdComp = new GridData(SWT.FILL);
		gdComp.grabExcessHorizontalSpace = true;
		gdComp.grabExcessVerticalSpace = true;
		gdComp.horizontalAlignment = SWT.FILL;
		gdComp.verticalAlignment = SWT.FILL;
		compLeft.setLayoutData(gdComp);
		GridLayout glComp = new GridLayout(3, false);
		glComp.horizontalSpacing = 0;
		glComp.marginLeft = 0;
		glComp.marginRight = 0;
		compLeft.setLayout(glComp);
		// FUNZIONI STATISTICHE
		Composite compRight = new Composite(compTop, SWT.RIGHT);
		GridLayout glRight = new GridLayout(2, true);
		compRight.setLayout(glRight);
		compRight.setLayoutData(gdComp);
		initializeTopLeft(compLeft);
		initializeTopRight(compRight);
		Button esegui = new Button(this, SWT.NONE);
		esegui.setText("ESEGUI INTERROGAZIONE");
		esegui.setLayoutData(new GridData());
		esegui.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				eseguiInterrogazione();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {

			}
		});
		Button visualizzaQuery = new Button(this, SWT.NONE);
		visualizzaQuery.setLayoutData(new GridData());
		visualizzaQuery.setText("VISUALIZZA INTERROGAZIONE");
		visualizzaQuery.addSelectionListener(new SelectionListener() {

			private StyledText textQuery;
			private Shell queryShell;

			@Override
			public void widgetSelected(SelectionEvent e) {
				queryShell = new Shell(SWT.BORDER | SWT.DIALOG_TRIM);
				queryShell.setText("Interrogazione");
				queryShell.setBounds(100, 100, 400, 200);
				queryShell.setLayout(new GridLayout(1, false));
				queryShell.setLayoutData(new GridData(SWT.FILL));
				textQuery = new StyledText(queryShell, SWT.BORDER | SWT.MULTI
						| SWT.V_SCROLL | SWT.H_SCROLL | SWT.WRAP);
				textQuery.setLayoutData(new GridData(SWT.FILL));
				textQuery.setText(selectToExecute);
				GridData gdText = new GridData();
				gdText.horizontalAlignment = SWT.FILL;
				gdText.grabExcessHorizontalSpace = true;
				gdText.verticalAlignment = SWT.FILL;
				gdText.grabExcessVerticalSpace = true;
				textQuery.setLayoutData(gdText);

				Button ok = new Button(queryShell, SWT.NONE);
				ok.setText("OK");
				GridData gdButton = new GridData(SWT.FILL);
				gdButton.horizontalAlignment = SWT.RIGHT;
				ok.setLayoutData(gdButton);
				ok.addSelectionListener(new SelectionListener() {
					public void widgetSelected(SelectionEvent e) {
						selectToExecute = textQuery.getText();
						System.out.println("select switched to:" + selectToExecute);
						queryShell.dispose();
					}

					public void widgetDefaultSelected(SelectionEvent e) {
					}
				});
				queryShell.open();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {

			}
		});
		initializeRisultati(this);

	}

	private void initializeTopRight(Composite parent) {
		creaTabellaCampi(parent);
		creaTabellaFunct(parent);

	}

	private void initializeRisultati(Composite parent) {
		Label lbl = new Label(this, SWT.NONE | SWT.BOLD);
		lbl
				.setText("RISULTATI - Seleziona la colonna per filtrare su quel dato");
		lbl.setBackground(Utils.getStandardWhiteColor());
		GridData gdLbl = new GridData();
		gdLbl.horizontalSpan = 2;
		lbl.setLayoutData(gdLbl);
		Composite compRisultati = new Composite(this, SWT.TOP | SWT.BORDER
				| SWT.SCROLL_PAGE);
		compRisultati.setBackground(Utils.getStandardWhiteColor());
		GridLayout glRisultati = new GridLayout(1, true);
		glRisultati.horizontalSpacing = 0;
		glRisultati.marginLeft = 0;
		glRisultati.marginRight = 0;
		compRisultati.setLayout(glRisultati);
		GridData gdRisultati = new GridData();
		gdRisultati.grabExcessHorizontalSpace = true;
		gdRisultati.grabExcessVerticalSpace = true;
		gdRisultati.horizontalAlignment = SWT.FILL;
		gdRisultati.verticalAlignment = SWT.FILL;
		gdRisultati.horizontalSpan = 2;
		compRisultati.setLayoutData(gdRisultati);
		tblRisultati = new Table(compRisultati, SWT.FILL | SWT.BORDER
				| SWT.FULL_SELECTION | SWT.V_SCROLL | SWT.MULTI);
		tblRisultati.setHeaderVisible(true);
		tblRisultati.setToolTipText("Risultati interrogazione");
		tblRisultati.setLinesVisible(true);
		GridData gdTblRis = new GridData();
		gdTblRis.grabExcessHorizontalSpace = true;
		gdTblRis.grabExcessVerticalSpace = true;
		gdTblRis.horizontalAlignment = SWT.FILL;
		gdTblRis.verticalAlignment = SWT.FILL;
		tblRisultati.setLayout(new GridLayout());
		tblRisultati.setLayoutData(gdTblRis);
	}

	private void initializeTopLeft(Composite comp) {
		creaTabellaEntita(comp);
		Composite compButtonEntita = new Composite(comp, SWT.NONE);
		GridData gdButtons = new GridData();
		compButtonEntita.setLayoutData(gdButtons);
		GridLayout gdL = new GridLayout(1, false);
		gdL.horizontalSpacing = 0;
		gdL.marginLeft = 0;
		gdL.marginRight = 0;
		compButtonEntita.setLayout(gdL);
		GridData gdButton = new GridData(50, 50);
		Button toRight = new Button(compButtonEntita, SWT.NONE);
		toRight.setText(">");
		toRight.setLayoutData(gdButton);
		toRight.addSelectionListener(new SelectionListener() {

			public void widgetSelected(SelectionEvent e) {
				gestisciEntita(0);
			}

			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});
		Button toLeft = new Button(compButtonEntita, SWT.NONE);
		toLeft.setText("<");
		toLeft.setLayoutData(gdButton);
		toLeft.addSelectionListener(new SelectionListener() {

			public void widgetSelected(SelectionEvent e) {
				gestisciEntita(1);
			}

			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});
		creaTabellaEntitaSel(comp);

	}

	private void creaTabellaEntitaSel(Composite comp) {
		GridData gdTableSel = new GridData();
		gdTableSel.horizontalAlignment = SWT.RIGHT;
		gdTableSel.grabExcessHorizontalSpace = true;
		gdTableSel.grabExcessVerticalSpace = true;
		// gdTableSel.horizontalAlignment = SWT.FILL;
		gdTableSel.verticalAlignment = SWT.FILL;
		GridLayout glTableSel = new GridLayout(1, false);
		tableEntitaSel = new Table(comp, SWT.FILL | SWT.BORDER
				| SWT.FULL_SELECTION | SWT.V_SCROLL | SWT.MULTI);
		// tableEntitaSel.setForeground(Utils.getStandardBlackColor());
		// tableEntitaSel.setFont(Utils.getFont("arial", 10, SWT.BOLD));
		tableEntitaSel.setHeaderVisible(true);
		tableEntitaSel.setLinesVisible(true);
		tableEntitaSel.setLayoutData(gdTableSel);
		tableEntitaSel.setLayout(glTableSel);
		TableColumn colonnaEncodeSel = new TableColumn(tableEntitaSel, SWT.LEFT);
		colonnaEncodeSel.setWidth(0);
		colonnaEncodeSel.setText("CODE");
		TableColumn colonnaEntitàSel = new TableColumn(tableEntitaSel, SWT.LEFT);
		colonnaEntitàSel.setWidth(200);
		colonnaEntitàSel.setText("Entità selezionate");
		tableEntitaSel.setHeaderVisible(true);
		tableEntitaSel.setToolTipText("Seleziona una entità da eliminare");
		tableEntitaSel.addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent e) {

			}

			public void widgetSelected(SelectionEvent e) {
				for (TableItem item : tableEntitaSel.getSelection()) {
					String entita = item.getText();
					riempiTabellaCampi(tableCampi, (GenericBean) classi
							.get(entita), entita);
				}
			}
		});
	}

	private void gestisciEntita(int buttonType) {
		TableItem[] itemNew = null;
		if (buttonType == 0) {
			// >
			itemNew = tableEntita.getSelection();
			for (TableItem item : itemNew) {
				TableItem it = new TableItem(tableEntitaSel, SWT.NONE);
				it.setText(new String[] { item.getText().toUpperCase(),
						item.getText() });
			}
			tableEntita.remove(tableEntita.getSelectionIndices());
			// <
		} else if (buttonType == 1) {
			itemNew = tableEntitaSel.getSelection();
			int[] indexs = tableEntitaSel.getSelectionIndices();
			for (TableItem item : itemNew) {
				String entita = (item).getText(0);
				for (TableItem campo : tableCampi.getItems()) {
					if (campo.getText(0).equals(entita))
						tableCampi.remove(tableCampi.indexOf(campo));
				}
				for (TableItem operaz : tableFunct.getItems()) {
					if (operaz.getText(0).equals(entita))
						tableFunct.remove(tableFunct.indexOf(operaz));
				}
				TableItem it = new TableItem(tableEntita, SWT.NONE);
				it.setText(new String[] { item.getText(), item.getText() });
			}
			tableEntitaSel.remove(indexs);

			// >>
		}
	}

	private void creaTabellaEntita(Composite parent) {
		GridData gdTable = new GridData();
		gdTable.horizontalAlignment = SWT.LEFT;
		gdTable.grabExcessHorizontalSpace = true;
		// gdTable.horizontalAlignment = SWT.FILL;
		gdTable.verticalAlignment = SWT.FILL;
		GridLayout glTable = new GridLayout(1, false);
		glTable.marginRight = 0;
		glTable.marginLeft = 0;
		tableEntita = new Table(parent, SWT.FILL | SWT.BORDER
				| SWT.FULL_SELECTION | SWT.V_SCROLL | SWT.MULTI);
		// tableEntita.setForeground(Utils.getStandardBlackColor());
		// tableEntita.setFont(Utils.getFont("arial", 10, SWT.BOLD));
		tableEntita.setHeaderVisible(true);
		tableEntita.setLinesVisible(true);
		tableEntita.setLayoutData(gdTable);
		tableEntita.setLayout(glTable);
		TableColumn colonnaEncode = new TableColumn(tableEntita, SWT.LEFT);
		colonnaEncode.setWidth(0);
		colonnaEncode.setText("ID");
		TableColumn colonnaEntità = new TableColumn(tableEntita, SWT.LEFT);
		colonnaEntità.setWidth(200);
		colonnaEntità.setText("Entità utilizzabili");
		tableEntita.setHeaderVisible(true);
		tableEntita.setToolTipText("Seleziona una entità da aggiungere");
		for (String item : classi.keySet()) {
			TableItem tblItem = new TableItem(tableEntita, SWT.NONE);
			tblItem.setText(new String[] { item, item });
		}

	}

	private void creaTabellaCampi(Composite parent) {
		GridData gdTableCampi = new GridData();
		gdTableCampi.horizontalAlignment = SWT.FILL;
		gdTableCampi.verticalAlignment = SWT.FILL;
		gdTableCampi.grabExcessHorizontalSpace = true;
		gdTableCampi.grabExcessVerticalSpace = true;

		tableCampi = new Table(parent, SWT.FILL | SWT.BORDER
				| SWT.FULL_SELECTION | SWT.V_SCROLL | SWT.MULTI);
		tableCampi.setLayout(new GridLayout(1, true));
		tableCampi.setLayoutData(gdTableCampi);
		TableColumn colonnaEntita = new TableColumn(tableCampi, SWT.LEFT);
		colonnaEntita.setWidth(90);
		colonnaEntita.setText("Entità");
		// TableColumn colonnaID = new TableColumn(tableCampi, SWT.CENTER);
		// colonnaID.setWidth(0);
		// colonnaID.setText("ID");
		TableColumn colonnaCampo = new TableColumn(tableCampi, SWT.LEFT);
		colonnaCampo.setWidth(170);
		colonnaCampo.setText("Campo");
		tableCampi.setLinesVisible(true);
		tableCampi.setHeaderVisible(true);
		tableCampi.setToolTipText("Campi utlizzabili nelle statistiche");
		tableCampi.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				for (TableItem item : tableCampi.getSelection()) {
					gestisciFuncCampo(item.getText(1), item.getText(0));
				}
			}

			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});
	}

	private void creaTabellaFunct(Composite parent) {
		GridData gdTableFunct = new GridData();
		gdTableFunct.horizontalAlignment = SWT.FILL;
		gdTableFunct.verticalAlignment = SWT.FILL;
		gdTableFunct.grabExcessHorizontalSpace = true;
		gdTableFunct.grabExcessVerticalSpace = true;
		tableFunct = new Table(parent, SWT.FILL | SWT.BORDER
				| SWT.FULL_SELECTION | SWT.V_SCROLL | SWT.MULTI);
		tableFunct.setLayout(new GridLayout(1, true));
		tableFunct.setLayoutData(gdTableFunct);
		TableColumn colonnaEnt = new TableColumn(tableFunct, SWT.LEFT);
		colonnaEnt.setWidth(0);
		colonnaEnt.setText("ID");
		TableColumn colonnaIDFunc = new TableColumn(tableFunct, SWT.LEFT);
		colonnaIDFunc.setWidth(0);
		colonnaIDFunc.setText("ID");
		TableColumn colonnaFunc = new TableColumn(tableFunct, SWT.LEFT);
		colonnaFunc.setWidth(200);
		colonnaFunc.setText("Operazione");
		tableFunct.setLinesVisible(true);
		tableFunct.setHeaderVisible(true);
	}

	private void gestisciFuncCampo(String campo, String entita) {
		String tipoCampo = GenericBean.getPropertyClass(campo,
				(GenericBean) classi.get(entita)).toString();
		if (tipoCampo.equals("String")) {
			createOperazioneCampo("LOWER", campo, entita);
			createOperazioneCampo("UPPER", campo, entita);
			createOperazioneCampo("TRIM", campo, entita);
		} else if (tipoCampo.equals("int") || tipoCampo.equals("Integer")
				|| tipoCampo.equals("double") || tipoCampo.equals("Double")
				|| tipoCampo.equals("BigDecimal")) {
			createOperazioneCampo("MIN", campo, entita);
			createOperazioneCampo("MAX", campo, entita);
			createOperazioneCampo("AVG", campo, entita);
		} else if (tipoCampo.equals("Date")) {
			createOperazioneCampo("DAY", campo, entita);
			createOperazioneCampo("MONTH", campo, entita);
			createOperazioneCampo("YEAR", campo, entita);
		}
	}

	private void createOperazioneCampo(String operaz, String campo,
			String entità) {
		TableItem tblItem = new TableItem(tableFunct, SWT.NONE);
		tblItem.setText(new String[] { entità, operaz,
				operaz + "(" + campo + ")" });
	}

	private void filtraValori(String[] value) {
		for (TableItem item : tableEntita.getItems()) {
			if (!item.getText(numColonnaFiltro).equals(value[0]))
				tableEntita.remove(tableEntita.indexOf(item));
		}

	}

	private void eseguiInterrogazione() {
		ArrayList<HashMap<String, String>> listaMedici = new MedicoDAO()
				.getMediciForStatistics();
		for (String colonna : listaMedici.get(0).keySet()) {
			TableColumn col = new TableColumn(tblRisultati, SWT.CENTER);
			col.addListener(SWT.Selection, listenerFiltro);
			col.setText(colonna);
			col.setWidth(colonna.length() * 20);
		}
		for (HashMap<String, String> item : listaMedici) {
			TableItem tableItem = new TableItem(tblRisultati, SWT.FILL);
			tableItem.setForeground(Utils.getStandardBlackColor());
			// tableItem.setFont(Utils.getFont("arial", 8, SWT.BOLD));
			String values[] = null;
			int i = 0;
			for (String itemValue : item.keySet()) {
				values = new String[itemValue.length()];
				values[i] = "" + item.get(itemValue);
				tableItem.setText(values);
				i++;
			}

		}

	}
}
