package studiodietetico;

import hibernate.Esameclinico;
import hibernate.Parametroantropometrico;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.part.ViewPart;

import command.DietaDAO;
import command.EsameClinicoDAO;
import command.ParametroAntropometricoDAO;
import common.Utils;

public class ParametroAntropometricoTableView extends ViewPart {
	public static final String ID = "studiodietetico.ParametroAntropometricoTableView"; // TODO Needs to be whatever is mentioned in plugin.xml
	private Composite top = null;
	private static TableForm classVis;
	private static ArrayList<Object> parametri;

	public ParametroAntropometricoTableView() {
	}

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
		parametri = ParametroAntropometricoDAO.getParametroAntropometricoObject();
		ParametroAntropometricoDAO dd = new ParametroAntropometricoDAO();
		classVis = new TableForm(top, SWT.BORDER, parametri,
				"createShellModParametroAntropometrico",
				"createShellInsParametroAntropometrico", ParametroAntropometricoTableView.this, dd,
				"ParametroAntropometricoTableView");
		classVis.setBounds(new Rectangle(6, 50, 800, 332));
		classVis.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		classVis.setLayout(new GridLayout(1, true));
		classVis.setBackground(Utils.getStandardWhiteColor());

		classVis.nascondiColonne(new int[] {0});

		//aggiungiColonne(classVis, parametri);

		classVis.aggiornaCombo();

		classVis.ordinamentoStringhe(classVis.getTableVisualizzazione(), 1);
		classVis.ordinamentoStringhe(classVis.getTableVisualizzazione(), 2);
	}

	public void createShellInsParametroAntropometrico() {
		InserisciParametroAntropometricoShell ParametroAntropometricoShell = new InserisciParametroAntropometricoShell();
		ParametroAntropometricoShell.createShellInsParametroAntropometrico();
	}

	public void createShellModParametroAntropometrico(TableItem itemSel) {
		ParametroAntropometricoDAO esame = new ParametroAntropometricoDAO();
		// TableItem itemSel =
		// classVis.getTableVisualizzazione().getSelection()[0];
		int idParametro = Integer.parseInt(itemSel.getText(0));
		InserisciParametroAntropometricoShell ParametroAntropometricoShell = new InserisciParametroAntropometricoShell(idParametro);
		ParametroAntropometricoShell.createShellInsParametroAntropometrico();
	}

	public static void aggiungiColonne(TableForm classVis,
			ArrayList<Object> esami) {
		TableColumn colonna = new TableColumn(classVis
				.getTableVisualizzazione(), SWT.CENTER);
		colonna.setText("id");
		Integer id = -1;
		TableItem itemSel = null;
		for (int j = 0; j < esami.size(); j++) {
			id =  ((Parametroantropometrico) parametri.get(j)).getIdParametroAntropometrico();
			itemSel = classVis.getTableVisualizzazione().getItem(j);
			itemSel.setText(
					classVis.getTableVisualizzazione().getColumnCount() - 1,
					id.toString());
		}
		colonna.pack();
		colonna.setResizable(false);

		TableColumn colonna2 = new TableColumn(classVis
				.getTableVisualizzazione(), SWT.CENTER);
		colonna2.setText("nome");
		String nome = "";
		TableItem itemSel2 = null;
		for (int j = 0; j < parametri.size(); j++) {
			nome = "" + ((Parametroantropometrico) parametri.get(j)).getNome();
			itemSel2 = classVis.getTableVisualizzazione().getItem(j);
			itemSel2.setText(classVis.getTableVisualizzazione()
					.getColumnCount() - 1, nome);
		}
		colonna2.pack();
		colonna2.setResizable(false);
		
		TableColumn colonna3 = new TableColumn(classVis
				.getTableVisualizzazione(), SWT.CENTER);
		colonna3.setText("descrizione");
		String nome3 = "";
		TableItem itemSel3 = null;
		for (int j = 0; j < parametri.size(); j++) {
			nome3 = ((Parametroantropometrico) parametri.get(j)).getDescrizione();
			itemSel3 = classVis.getTableVisualizzazione().getItem(j);
			itemSel3.setText(classVis.getTableVisualizzazione()
					.getColumnCount() - 1, nome3);
		}
		colonna3.pack();
		colonna3.setResizable(false);

		
	}

	public static void aggiornaTableView() {
		classVis.getTableVisualizzazione().removeAll(); // rimuove le righe
		// rimuove le colonne
		int k = 0;
		while (k < classVis.getTableVisualizzazione().getColumnCount()) {
			classVis.getTableVisualizzazione().getColumn(k).dispose();
		}
		parametri = ParametroAntropometricoDAO.getParametroAntropometricoObject();
		classVis.riempiTabella(parametri, "ParametroAntropometricoTableView");
		classVis.nascondiColonne(new int[] {0});
		//aggiungiColonne(classVis, parametri);
		//classVis.aggiornaCombo();
		classVis.ordinamentoStringhe(classVis.getTableVisualizzazione(), 1);
		classVis.ordinamentoStringhe(classVis.getTableVisualizzazione(), 2);
	}

	@Override
	public void setFocus() {
		classVis.setFocus();
	}

}