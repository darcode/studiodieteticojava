package forms;

import hibernate.Attivitafisica;
import hibernate.Intolleranzaallergia;
import hibernate.Paziente;
import hibernate.Tipologiaintervento;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import common.GenericBean;

public class TableUtil {
	
	public static void riempiTabellaEntita(Table table, ArrayList<Object> lista) {
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
				Object[] valuesObj = new Object[GenericBean.getFieldsNumber(item)];
				String[] values = new String[GenericBean.getFieldsNumber(item)];
				
				int i = 0;
				for (String colonna : colonne) {
					valuesObj[i] = GenericBean.getProperty(colonna, item);
					//if a cascata per sostituire l'id hibernate con l'id numerico 
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

}
