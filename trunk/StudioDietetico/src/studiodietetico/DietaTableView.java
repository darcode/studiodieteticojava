package studiodietetico;

import hibernate.Dieta;
import hibernate.Prenotazione;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.part.ViewPart;

import service.GiornoDieta;

import command.DietaDAO;
import command.MedicoDAO;
import common.Utils;

public class DietaTableView extends ViewPart {
	private Composite top = null;
	private static TableForm classVis;
	private static ArrayList<Object> diete;

	public DietaTableView() {
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
		diete = DietaDAO.getDieteObject();
		DietaDAO dd = new DietaDAO();
		classVis = new TableForm(top, SWT.BORDER, diete,
				"createShellModSchemaDietetico",
				"createShellInsSchemaDietetico", DietaTableView.this, dd,
				"DietaTableView");
		classVis.setBounds(new Rectangle(6, 50, 800, 332));
		classVis.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		classVis.setLayout(new GridLayout(1, true));
		classVis.setBackground(Utils.getStandardWhiteColor());

		classVis.nascondiColonne(new int[] { 0, 1, 4, 5 });

		aggiungiColonne(classVis, diete);

		classVis.aggiornaCombo();

		classVis.ordinamentoStringhe(classVis.getTableVisualizzazione(), 2);
		classVis.ordinamentoInteri(classVis.getTableVisualizzazione(), 3);
		classVis.ordinamentoInteri(classVis.getTableVisualizzazione(), 6);
		classVis.ordinamentoStringhe(classVis.getTableVisualizzazione(), 7);
		classVis.ordinamentoStringhe(classVis.getTableVisualizzazione(), 8);
		classVis.ordinamentoStringhe(classVis.getTableVisualizzazione(), 9);
		classVis.ordinamentoStringhe(classVis.getTableVisualizzazione(), 10);

	}

	public void createShellInsSchemaDietetico() {
		InserisciDietaShell dietaShell = new InserisciDietaShell(false);
		dietaShell.createShellInsSchemaDietetico();
	}

	public void createShellModSchemaDietetico(TableItem itemSel) {
		DietaDAO dieta = new DietaDAO();
		// TableItem itemSel =
		// classVis.getTableVisualizzazione().getSelection()[0];
		int idDieta = Integer.parseInt(itemSel.getText(0));
		ArrayList<GiornoDieta> arrGior = dieta.getSchemiDieta(idDieta);
		boolean stand = false;
		if (itemSel.getText(10).equals("si")) {
			stand = true;
		}
		InserisciDietaShell dietaShell = new InserisciDietaShell(arrGior,
				itemSel.getText(2), itemSel.getText(4), stand, Integer
						.parseInt(itemSel.getText(0)), false);

		dietaShell.createShellInsSchemaDietetico();
	}

	public static void aggiungiColonne(TableForm classVis,
			ArrayList<Object> diete) {
		TableColumn colonna = new TableColumn(classVis
				.getTableVisualizzazione(), SWT.CENTER);
		colonna.setText("Kcal");
		String nome = "";
		TableItem itemSel = null;
		for (int j = 0; j < diete.size(); j++) {
			nome = ""
					+ ((Dieta) diete.get(j)).getSpecifichedieta()
							.getKilocalorie();
			itemSel = classVis.getTableVisualizzazione().getItem(j);
			itemSel.setText(
					classVis.getTableVisualizzazione().getColumnCount() - 1,
					nome);
		}
		colonna.pack();
		colonna.setResizable(false);

		TableColumn colonna2 = new TableColumn(classVis
				.getTableVisualizzazione(), SWT.CENTER);
		colonna2.setText("Indicata");
		String nome2 = "";
		TableItem itemSel2 = null;
		for (int j = 0; j < diete.size(); j++) {
			nome2 = ((Dieta) diete.get(j)).getSpecifichedieta().getIndicata();
			itemSel2 = classVis.getTableVisualizzazione().getItem(j);
			itemSel2.setText(classVis.getTableVisualizzazione()
					.getColumnCount() - 1, nome2);
		}
		colonna2.pack();
		colonna2.setResizable(false);

		TableColumn colonna3 = new TableColumn(classVis
				.getTableVisualizzazione(), SWT.CENTER);
		colonna3.setText("Contenuto presente");
		String nome3 = "";
		TableItem itemSel3 = null;
		for (int j = 0; j < diete.size(); j++) {
			nome3 = ((Dieta) diete.get(j)).getSpecifichedieta()
					.getContenutoPresente();
			itemSel3 = classVis.getTableVisualizzazione().getItem(j);
			itemSel3.setText(classVis.getTableVisualizzazione()
					.getColumnCount() - 1, nome3);
		}
		colonna3.pack();
		colonna3.setResizable(false);

		TableColumn colonna4 = new TableColumn(classVis
				.getTableVisualizzazione(), SWT.CENTER);
		colonna4.setText("Contenuto assente");
		String nome4 = "";
		TableItem itemSel4 = null;
		for (int j = 0; j < diete.size(); j++) {
			nome4 = ((Dieta) diete.get(j)).getSpecifichedieta()
					.getContenutoAssente();
			itemSel4 = classVis.getTableVisualizzazione().getItem(j);
			itemSel4.setText(classVis.getTableVisualizzazione()
					.getColumnCount() - 1, nome4);
		}
		colonna4.pack();
		colonna4.setResizable(false);

		TableColumn colonna5 = new TableColumn(classVis
				.getTableVisualizzazione(), SWT.CENTER);
		colonna5.setText("Standard");
		TableItem itemSel5 = null;
		if (diete != null) {
			for (int j = 0; j < diete.size(); j++) {
				itemSel5 = classVis.getTableVisualizzazione().getItem(j);
				if (((Dieta) diete.get(j)).getDietaStandard() != null && ((Dieta) diete.get(j)).getDietaStandard() )
					itemSel5.setText(classVis.getTableVisualizzazione()
							.getColumnCount() - 1, "si");
				else
					itemSel5.setText(classVis.getTableVisualizzazione()
							.getColumnCount() - 1, "no");
			}
			colonna5.pack();
			colonna5.setResizable(false);
		}
	}

	public static void aggiornaTableView() {
		classVis.getTableVisualizzazione().removeAll(); // rimuove le righe
		// rimuove le colonne
		int k = 0;
		while (k < classVis.getTableVisualizzazione().getColumnCount()) {
			classVis.getTableVisualizzazione().getColumn(k).dispose();
		}
		diete = DietaDAO.getDieteObject();
		classVis.riempiTabella(diete, "DietaTableView");
		classVis.nascondiColonne(new int[] { 0, 1, 4, 5 });
		aggiungiColonne(classVis, diete);
		classVis.aggiornaCombo();
		classVis.ordinamentoStringhe(classVis.getTableVisualizzazione(), 2);
		classVis.ordinamentoInteri(classVis.getTableVisualizzazione(), 3);
		classVis.ordinamentoInteri(classVis.getTableVisualizzazione(), 6);
		classVis.ordinamentoStringhe(classVis.getTableVisualizzazione(), 7);
		classVis.ordinamentoStringhe(classVis.getTableVisualizzazione(), 8);
		classVis.ordinamentoStringhe(classVis.getTableVisualizzazione(), 9);
		classVis.ordinamentoStringhe(classVis.getTableVisualizzazione(), 10);
	}

	@Override
	public void setFocus() {
		classVis.setFocus();
	}

}
