package command;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.TreeItem;
import org.hibernate.Criteria;
import org.hibernate.Session;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

public class DynamicQueryDAO extends BaseDAO{
	private Criteria criteria;
	private Object filtroQuery;	
	
	public DynamicQueryDAO(String pathClasse){
		Class c = null;
		try {
			c = Class.forName(pathClasse);
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
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
	
	public List executeDynQuery () {
        List results = criteria.list();
		return results;
	}
	
	public void espandiAlbero(String nomeClasse, String pathClasse, TreeItem radice, HashSet<String> nodiVisitati) {
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
			// Recupero la lista dei Campi della Classe
			Field campi[] = classSelected.getDeclaredFields();
			ArrayList<Field> fieldClasse = new ArrayList<Field>();
			for (int i = 0; i < campi.length; i++) {
				fieldClasse.add(campi[i]);
			}
			while (fieldClasse.size() > 0) {
				Field currField = fieldClasse.get(0);
				// Passo Base
				if (currField.getName().contains("id")) {
					// do nothing
				} else if (currField.getType().isPrimitive()) {
					String prim = currField.getType().toString();
					if (prim.equals("char")) {
						
					} else if (prim.equals("int")) {
						
					} else if (prim.equals("double")) {
							
					}					
					TreeItem sottoRadice = new TreeItem(radice, SWT.NONE);
					sottoRadice.setText(currField.getName());
				} else if (currField.getType().equals(java.lang.Boolean.class)) {
					TreeItem sottoRadice = new TreeItem(radice, SWT.NONE);
					sottoRadice.setText(currField.getName());
				} else if (currField.getType().equals(java.lang.Double.class)) {
					TreeItem sottoRadice = new TreeItem(radice, SWT.NONE);
					sottoRadice.setText(currField.getName());
				} else if (currField.getType().isInstance(new String())) {
					TreeItem sottoRadice = new TreeItem(radice, SWT.NONE);
					sottoRadice.setText(currField.getName());
				} else if (currField.getType().isInstance(new Date())) {
					TreeItem sottoRadice = new TreeItem(radice, SWT.NONE);
					sottoRadice.setText(currField.getName());
				} else if (currField.getType().equals(java.lang.Integer.class)) {
					TreeItem sottoRadice = new TreeItem(radice, SWT.NONE);
					sottoRadice.setText(currField.getName());
				}
				// Ricorsione
				else if (!currField.getType().isInstance(new HashSet<Object>())) {					
					// nodo del grafo di esplorazione
					String testo = service.Utils.upperCase(currField.getName());					
					String currentPath = currField.getType().getCanonicalName();
					if (!nodiVisitati.contains(currentPath)) {
						TreeItem sottoRadice = new TreeItem(radice, SWT.NONE);
						sottoRadice.setText(testo);
						espandiAlbero(testo, currentPath, sottoRadice, nodiVisitati);
					}					
				} else if (!nodiVisitati.contains(currField.getDeclaringClass().toString())) {
					// hashSet					
					String testo = service.Utils.rimuoviS(currField.getName());
					testo = service.Utils.upperCase(testo);
					if (!nodiVisitati.contains("hibernate." + testo)) {
						TreeItem sottoRadice = new TreeItem(radice, SWT.NONE);
						sottoRadice.setText(testo);
						espandiAlbero(testo, "hibernate." + testo, sottoRadice, nodiVisitati);
					}					
				}
				fieldClasse.remove(currField);
			}
		}
	}
}