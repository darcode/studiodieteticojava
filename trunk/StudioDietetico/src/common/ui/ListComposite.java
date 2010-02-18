package common.ui;

import hibernate.Attivitafisica;
import hibernate.Dieta;
import hibernate.Intolleranzaallergia;
import hibernate.Paziente;
import hibernate.Specifichedieta;
import hibernate.Tipologiaintervento;

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

	protected void riempiTabellaEntita(Table table, ArrayList<Object> lista) {
		if (!lista.isEmpty()) {
			ArrayList<String> colonne = GenericBean.getFieldsNamesPerQuery(lista.get(0));
			for (String item : colonne) {
				TableColumn colonna = new TableColumn(table, SWT.CENTER);
				//colonna.setWidth(item.length() * 15);
				colonna.setText(item);
			}
			table.setHeaderVisible(true);
			for (Object item : lista) {
				TableItem tblItem = new TableItem(table, SWT.NONE);
				Object[] valuesObj = new Object[GenericBean.getFieldsNumber(item)];
				String[] values = new String[GenericBean.getFieldsNumber(item)];
				int i = 0;
				
				for (String colonna : colonne) {
					valuesObj[i] = GenericBean.getProperty(colonna, item);
					//TODO if a cascata per sostituire l'id hibernate con l'id numerico 
					if ( valuesObj[i] instanceof Paziente) { 
						valuesObj[i] = ((Paziente)valuesObj[i]).getIdPaziente();
					} 
					if  (valuesObj[i] instanceof Tipologiaintervento) {  
						valuesObj[i] = ((Tipologiaintervento)valuesObj[i]).getIdTipologiaIntervento();
					} 
					if  (valuesObj[i] instanceof Intolleranzaallergia) {  
						valuesObj[i] = ((Intolleranzaallergia)valuesObj[i]).getIdIntolleranzaAllergia();
					}
					if  (valuesObj[i] instanceof Attivitafisica) {  
						valuesObj[i] = ((Attivitafisica)valuesObj[i]).getIdAttivitaFisica();
					}
					if  (valuesObj[i] instanceof Dieta) {  
						valuesObj[i] = ((Dieta)valuesObj[i]).getIdDieta();
					}
					if  (valuesObj[i] instanceof Specifichedieta) {  
						valuesObj[i] = ((Specifichedieta)valuesObj[i]).getIdSpecificheDieta();
					}
					
					i++;
				}
				
				for (int j = 0; j < valuesObj.length; j++) {
					if (valuesObj[j]!=null)
						values[j]=valuesObj[j].toString();
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