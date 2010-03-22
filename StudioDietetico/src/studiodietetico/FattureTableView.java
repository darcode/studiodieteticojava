package studiodietetico;

import hibernate.Fattura;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.part.ViewPart;

import command.FatturaDAO;
import common.Utils;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.graphics.Point;

public class FattureTableView extends ViewPart {
	private Composite top = null;
	private TableForm classVis;
	private ArrayList<Object> fatture;  //  @jve:decl-index=0:
	public static final String VIEW_ID = "StudioDietetico.FattureTableView";
	private Shell sShellDettagliFattura = null;  //  @jve:decl-index=0:visual-constraint="334,13"
	private Label labelDescrizioneFat = null;
	private Text textDescrizioneFatt = null;
	private Label labelImportoFatt = null;
	private Text textImportoFatt = null;
	private Label labelAccontoFatt = null;
	private Text textAccontoFatt = null;
	private Label labelImportoScontoFatt = null;
	private Text textImportoScontoFatt = null;
	private Label labelDataFatt = null;
	private Text textDataFatt = null;
	private Label labelNoteFatt = null;
	private Text textAreaNoteFatt = null;
	private Button buttonOk = null;
	
	public FattureTableView() {}

	@Override
	public void createPartControl(Composite parent) {
		top = new Composite(parent, SWT.NONE);
		GridData gdForm = new GridData(SWT.NONE);
		gdForm.grabExcessVerticalSpace = true;
		gdForm.grabExcessHorizontalSpace = true;
		gdForm.horizontalAlignment = SWT.FILL;
		gdForm.grabExcessVerticalSpace = true;
		gdForm.verticalAlignment = SWT.FILL;
		top.setLayoutData(gdForm);
		GridLayout glForm = new GridLayout(1, false);
		top.setLayout(glForm);
		fatture = FatturaDAO.getFattureObject();
		FatturaDAO fd = new FatturaDAO();
		classVis = new TableForm(top, SWT.BORDER, fatture, "createSShellDettagliFattura","",FattureTableView.this, fd, "FattureTableView");
		classVis.setBounds(new Rectangle(6, 50, 800, 332));
		classVis.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		classVis.setLayout(new GridLayout(1, true));
		classVis.setBackground(Utils.getStandardWhiteColor());

		classVis.nascondiColonne(new int[] {0});
		
		classVis.aggiornaCombo();
		
		modificaColonna(classVis, fatture);
		
		classVis.ordinamentoInteri(classVis.getTableVisualizzazione(), 1);
		classVis.ordinamentoInteri(classVis.getTableVisualizzazione(), 2);
		classVis.ordinamentoInteri(classVis.getTableVisualizzazione(), 3);
		classVis.ordinamentoStringhe(classVis.getTableVisualizzazione(), 4);
		classVis.ordinamentoStringhe(classVis.getTableVisualizzazione(), 5);
		classVis.ordinamentoData(classVis.getTableVisualizzazione(), 6);
	}

	public static void modificaColonna(TableForm classVis, ArrayList<Object> prenotazioni) {
		for (int i = 1; i < 4; i++) {
			for (TableItem item : classVis.getTableVisualizzazione().getItems()) {
				String testoitem = item.getText(i);
				int lunghezzaTestoItem = item.getText(i).length();
				item.setText(i, testoitem.substring(0, lunghezzaTestoItem-2));
			}
		}
		for (TableItem item : classVis.getTableVisualizzazione().getItems()) {
			int i = 6;
			String testoitem = item.getText(i);
			int lunghezzaTestoItem = item.getText(i).length();
			item.setText(i, testoitem.substring(0, lunghezzaTestoItem-5));
		}
	}
	
	@Override
	public void setFocus() {
		classVis.setFocus();
	}

	/**
	 * This method initializes sShellDettagliFattura	
	 *
	 */
	public void createSShellDettagliFattura(final TableItem rigaTableClick) {
			int idFatt = Integer.parseInt(rigaTableClick.getText(0));
			final Fattura fat = FatturaDAO.getFatturaByID(idFatt);
			sShellDettagliFattura = new Shell(SWT.APPLICATION_MODAL | SWT.SHELL_TRIM);
			//sShellDettagliFattura.setLayout(new GridLayout());
			sShellDettagliFattura.setSize(new Point(407, 373));
			sShellDettagliFattura.setText("Dettagli conto");
			labelDescrizioneFat = new Label(sShellDettagliFattura, SWT.NONE);
			labelDescrizioneFat.setBounds(new Rectangle(6, 9, 73, 22));
			labelDescrizioneFat.setText("Descrizione :");
			textDescrizioneFatt = new Text(sShellDettagliFattura, SWT.BORDER);
			textDescrizioneFatt.setBounds(new Rectangle(87, 9, 299, 22));
			textDescrizioneFatt.setEditable(false);
			textDescrizioneFatt.setText(fat.getDescrizione());
			labelImportoFatt = new Label(sShellDettagliFattura, SWT.NONE);
			labelImportoFatt.setBounds(new Rectangle(6, 36, 72, 22));
			labelImportoFatt.setText("Importo :");
			textImportoFatt = new Text(sShellDettagliFattura, SWT.BORDER);
			textImportoFatt.setBounds(new Rectangle(87, 36, 228, 22));
			textImportoFatt.setEditable(false);
			textImportoFatt.setText(""+fat.getImporto());
			labelAccontoFatt = new Label(sShellDettagliFattura, SWT.NONE);
			labelAccontoFatt.setBounds(new Rectangle(6, 63, 71, 22));
			labelAccontoFatt.setText("Acconto :");
			textAccontoFatt = new Text(sShellDettagliFattura, SWT.BORDER);
			textAccontoFatt.setBounds(new Rectangle(87, 63, 227, 22));
			textAccontoFatt.setEditable(false);
			textAccontoFatt.setText(""+fat.getAcconto());
			labelImportoScontoFatt = new Label(sShellDettagliFattura, SWT.NONE);
			labelImportoScontoFatt.setBounds(new Rectangle(6, 89, 98, 22));
			labelImportoScontoFatt.setText("Importo sconto :");
			textImportoScontoFatt = new Text(sShellDettagliFattura, SWT.BORDER);
			textImportoScontoFatt.setBounds(new Rectangle(115, 89, 200, 22));
			textImportoScontoFatt.setEditable(false);
			textImportoScontoFatt.setText(""+fat.getImportoSconto());
			labelDataFatt = new Label(sShellDettagliFattura, SWT.NONE);
			labelDataFatt.setBounds(new Rectangle(6, 116, 121, 22));
			labelDataFatt.setText("Data ultima modifica :");
			textDataFatt = new Text(sShellDettagliFattura, SWT.BORDER);
			textDataFatt.setBounds(new Rectangle(130, 116, 185, 22));
			textDataFatt.setEditable(false);
			textDataFatt.setText(fat.getData().toLocaleString());
			labelNoteFatt = new Label(sShellDettagliFattura, SWT.NONE);
			labelNoteFatt.setBounds(new Rectangle(6, 146, 50, 22));
			labelNoteFatt.setText("Note :");
			textAreaNoteFatt = new Text(sShellDettagliFattura, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL);
			textAreaNoteFatt.setBounds(new Rectangle(62, 146, 287, 142));
			textAreaNoteFatt.setEditable(false);
			textAreaNoteFatt.setText(fat.getNote());
			buttonOk = new Button(sShellDettagliFattura, SWT.NONE);
			buttonOk.setBounds(new Rectangle(228, 301, 120, 25));
			buttonOk.setText("Chiudi dettagli");
			buttonOk.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
				public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
					sShellDettagliFattura.close();
				}
			});

		
		sShellDettagliFattura.open();
	}

	private void aggiornaTableView() {
		classVis.getTableVisualizzazione().removeAll(); // rimuove le righe
		// rimuove le colonne
		int k = 0;
		while (k < classVis.getTableVisualizzazione().getColumnCount()) {
			classVis.getTableVisualizzazione().getColumn(k).dispose();
		}
		fatture = FatturaDAO.getFattureObject();
		classVis.riempiTabella(fatture, "FattureTableView");
		classVis.nascondiColonne(new int[] {0});
		modificaColonna(classVis, fatture);
		//classVis.aggiornaCombo();
		classVis.ordinamentoInteri(classVis.getTableVisualizzazione(), 1);
		classVis.ordinamentoInteri(classVis.getTableVisualizzazione(), 2);
		classVis.ordinamentoInteri(classVis.getTableVisualizzazione(), 3);
		classVis.ordinamentoStringhe(classVis.getTableVisualizzazione(), 4);
		classVis.ordinamentoStringhe(classVis.getTableVisualizzazione(), 5);
		classVis.ordinamentoData(classVis.getTableVisualizzazione(), 6);
	}
}
