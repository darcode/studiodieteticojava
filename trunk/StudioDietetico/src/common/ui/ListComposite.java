package common.ui;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import common.GenericBean;

public class ListComposite extends Composite {

	public ListComposite(Composite parent, int style) {
		super(parent, style);
	}

	protected void riempiTabellaEntità(Table table, ArrayList<Object> lista) {
		if (!lista.isEmpty()) {
			ArrayList<String> colonne = GenericBean.getFieldsNamesPerQuery(lista.get(0));
			for (String item : colonne) {
				TableColumn colonna = new TableColumn(table, SWT.CENTER);
				colonna.setWidth(item.length() * 15);
				colonna.setText(item);
			}
			table.setHeaderVisible(true);
			for (Object item : lista) {
				TableItem tblItem = new TableItem(table, SWT.NONE);
				String[] values = new String[GenericBean.getFieldsNumber(item)];
				int i = 0;
				for (String colonna : colonne) {
					values[i] = "" + GenericBean.getProperty(colonna, item);
					i++;
				}
				tblItem.setText(values);
			}
		}
	}

	protected void riempiTabellaCampi(Table table, GenericBean bean,
			String entita) {
		try {
			boolean esisteInTabella = false;
			TableItem[] items = table.getItems();
			for (TableItem item : items) {
				if (item.getText(0).equals(entita))
					esisteInTabella = true;
			}
			if (!esisteInTabella) {
				ArrayList<String> campi = GenericBean
						.getFieldsNamesPerQuery(bean);
				for (String item : campi) {
					TableItem tblItem = new TableItem(table, SWT.NONE);
					tblItem.setText(new String[] { entita, item });
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}