package studiodietetico;

import hibernate.Esameclinico;

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
import common.Utils;

public class EsameClinicoTableView extends ViewPart {
	public static final String ID = "studiodietetico.EsameClinicoTableView"; // TODO Needs to be whatever is mentioned in plugin.xml
	private Composite top = null;
	private static TableForm classVis;
	private static ArrayList<Object> esami;

	public EsameClinicoTableView() {
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
		esami = EsameClinicoDAO.getEsameClinicoObject();
		EsameClinicoDAO dd = new EsameClinicoDAO();
		classVis = new TableForm(top, SWT.BORDER, esami,
				"createShellModEsameClinico",
				"createShellInsEsameClinico", EsameClinicoTableView.this, dd,
				"EsameClinicoTableView");
		classVis.setBounds(new Rectangle(6, 50, 800, 332));
		classVis.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		classVis.setLayout(new GridLayout(1, true));
		classVis.setBackground(Utils.getStandardWhiteColor());

		classVis.nascondiColonne(new int[] {0});

		//aggiungiColonne(classVis, esami);

		classVis.aggiornaCombo();

		classVis.ordinamentoStringhe(classVis.getTableVisualizzazione(), 1);
		classVis.ordinamentoStringhe(classVis.getTableVisualizzazione(), 2);
	}

	public void createShellInsEsameClinico() {
		InserisciEsameClinicoShell EsameShell = new InserisciEsameClinicoShell();
		EsameShell.createShellInsEsameClinico();
	}

	public void createShellModEsameClinico(TableItem itemSel) {
		EsameClinicoDAO esame = new EsameClinicoDAO();
		// TableItem itemSel =
		// classVis.getTableVisualizzazione().getSelection()[0];
		int idEsame = Integer.parseInt(itemSel.getText(0));
		InserisciEsameClinicoShell EsameShell = new InserisciEsameClinicoShell(idEsame);
		EsameShell.createShellInsEsameClinico();
	}

	public static void aggiungiColonne(TableForm classVis,
			ArrayList<Object> esami) {
		TableColumn colonna = new TableColumn(classVis
				.getTableVisualizzazione(), SWT.CENTER);
		colonna.setText("id");
		Integer id = -1;
		TableItem itemSel = null;
		for (int j = 0; j < esami.size(); j++) {
			id =  ((Esameclinico) esami.get(j)).getIdEsameClinico();
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
		for (int j = 0; j < esami.size(); j++) {
			nome = "" + ((Esameclinico) esami.get(j)).getNome();
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
		for (int j = 0; j < esami.size(); j++) {
			nome3 = ((Esameclinico) esami.get(j)).getDescrizione();
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
		esami = EsameClinicoDAO.getEsameClinicoObject();
		classVis.riempiTabella(esami, "EsameClinicoTableView");
		classVis.nascondiColonne(new int[] {0});
		aggiungiColonne(classVis, esami);
		classVis.aggiornaCombo();
		classVis.ordinamentoStringhe(classVis.getTableVisualizzazione(), 1);
		classVis.ordinamentoStringhe(classVis.getTableVisualizzazione(), 2);
	}

	@Override
	public void setFocus() {
		classVis.setFocus();
	}

}