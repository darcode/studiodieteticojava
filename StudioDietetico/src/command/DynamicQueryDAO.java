package command;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

public class DynamicQueryDAO extends BaseDAO{
	private Criteria criteria;
	private Object filtroQuery;
	private Document albero = null;
	private XMLOutputter xmlOutputter;
	
	public DynamicQueryDAO(String pathClasse, String nomeClasse, Element radice){
		SAXBuilder saxBuilder = new SAXBuilder();
		try {
			albero = saxBuilder.build(new File("dynQuery.xml"));
		} catch (JDOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		xmlOutputter = new XMLOutputter();
		xmlOutputter.setFormat(Format.getPrettyFormat());
		
		albero.removeContent();
		albero.setRootElement(radice);		
		
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
	
	public void espandiAlbero(String nomeClasse, String pathClasse, TreeItem radice, HashSet<String> nodiVisitati, Tree inizioAlbero, Element currElement) {
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
				if (currField.getName().startsWith("id")) {
					// do nothing
				} else if (currField.getType().isPrimitive()) {
					String prim = currField.getType().toString();
					if (prim.equals("char")) {
						Element figlio = new Element("foglia");
						figlio.setAttribute("path", currField.getType().getCanonicalName());
						figlio.setAttribute("nome", currField.getName());
						figlio.setAttribute("check", "no");
						currElement.addContent(figlio);
					} else if (prim.equals("int")) {
						Element figlio = new Element("foglia");
						figlio.setAttribute("path", currField.getType().getCanonicalName());
						figlio.setAttribute("nome", currField.getName());
						figlio.setAttribute("check", "no");
						currElement.addContent(figlio);
					} else if (prim.equals("double")) {
						Element figlio = new Element("foglia");
						figlio.setAttribute("path", currField.getType().getCanonicalName());	
						figlio.setAttribute("nome", currField.getName());
						figlio.setAttribute("check", "no");
						currElement.addContent(figlio);
					}					
					TreeItem sottoRadice = new TreeItem(radice, SWT.NONE);
					sottoRadice.setText(currField.getName());
				} else if (currField.getType().equals(java.lang.Boolean.class)) {
					Element figlio = new Element("foglia");
					figlio.setAttribute("path", currField.getType().getCanonicalName());
					currElement.addContent(figlio);
					figlio.setAttribute("nome", currField.getName());
					figlio.setAttribute("check", "no");
					TreeItem sottoRadice = new TreeItem(radice, SWT.NONE);
					sottoRadice.setText(currField.getName());
				} else if (currField.getType().equals(java.lang.Double.class)) {
					Element figlio = new Element("foglia");
					figlio.setAttribute("path", currField.getType().getCanonicalName());
					figlio.setAttribute("nome", currField.getName());
					figlio.setAttribute("check", "no");
					currElement.addContent(figlio);
					TreeItem sottoRadice = new TreeItem(radice, SWT.NONE);
					sottoRadice.setText(currField.getName());
				} else if (currField.getType().isInstance(new String())) {
					Element figlio = new Element("foglia");
					figlio.setAttribute("path", currField.getType().getCanonicalName());
					figlio.setAttribute("nome", currField.getName());
					figlio.setAttribute("check", "no");
					currElement.addContent(figlio);
					TreeItem sottoRadice = new TreeItem(radice, SWT.NONE);
					sottoRadice.setText(currField.getName());
				} else if (currField.getType().isInstance(new Date())) {
					Element figlio = new Element("foglia");
					figlio.setAttribute("path", currField.getType().getCanonicalName());
					figlio.setAttribute("nome", currField.getName());
					figlio.setAttribute("check", "no");
					currElement.addContent(figlio);
					TreeItem sottoRadice = new TreeItem(radice, SWT.NONE);
					sottoRadice.setText(currField.getName());
				} else if (currField.getType().equals(java.lang.Integer.class)) {
					Element figlio = new Element("foglia");
					figlio.setAttribute("path",currField.getType().getCanonicalName());
					figlio.setAttribute("nome", currField.getName());
					figlio.setAttribute("check", "no");
					currElement.addContent(figlio);
					TreeItem sottoRadice = new TreeItem(radice, SWT.NONE);
					sottoRadice.setText(currField.getName());
				}
				// Ricorsione
				else if (!currField.getType().isInstance(new HashSet<Object>())) {					
					// nodo del grafo di esplorazione					
					String testo = service.Utils.upperCase(currField.getName());					
					String currentPath = currField.getType().getCanonicalName();
					if (!nodiVisitati.contains(currentPath)) {
						Element figlio = new Element("ramo");
						figlio.setAttribute("path", currentPath);
						figlio.setAttribute("nome", testo);
						figlio.setAttribute("check", "no");
						currElement.addContent(figlio);						
						TreeItem sottoRadice = new TreeItem(radice, SWT.NONE);
						sottoRadice.setText(testo);
						sottoRadice.setFont(new Font(Display.getCurrent(), new FontData("Arial", 10 ,SWT.BOLD)));
						sottoRadice.setForeground(new Color(Display.getCurrent(), 255, 0, 0));
						espandiAlbero(testo, currentPath, sottoRadice, nodiVisitati, inizioAlbero, figlio);
					}					
				} else if (!nodiVisitati.contains(currField.getDeclaringClass().toString())) {
					// hashSet					
					String testo = service.Utils.rimuoviS(currField.getName());
					testo = service.Utils.upperCase(testo);
					if (!nodiVisitati.contains("hibernate." + testo)) {
						Element figlio = new Element("ramo");
						figlio.setAttribute("path", currField.getDeclaringClass().getCanonicalName());
						figlio.setAttribute("nome", testo);
						figlio.setAttribute("check", "no");
						currElement.addContent(figlio);
						TreeItem sottoRadice = new TreeItem(radice, SWT.NONE);
						sottoRadice.setText(testo);
//						sottoRadice.setBackground(new Color(Display.getCurrent(), 255, 0, 0));
						sottoRadice.setFont(new Font(Display.getCurrent(), new FontData("Arial", 10 ,SWT.BOLD)));
						sottoRadice.setForeground(new Color(Display.getCurrent(), 255, 0, 0));
						espandiAlbero(testo, "hibernate." + testo, sottoRadice, nodiVisitati, inizioAlbero, figlio);
					}					
				}
				aggiornaXml();
				fieldClasse.remove(currField);
			}
		}
	}

//	public void checkSelezionato(ArrayList<TreeItem> ramo, boolean b) {
//		//TODO rivedere il metodo
//		TreeItem last = ramo.get(ramo.size());
//		Element current = albero.getRootElement();
//		
//		if(last.getText().equals(current.getAttribute("nome"))){
//			ramo.remove(last);
//			while (ramo.size()>1) {
//				last = ramo.get(ramo.size());
//				current = current.getChild(last.getText());
//			}
//			last = ramo.get(ramo.size());
//			current = current.getChild(last.getText());
//			if (current.getName().equals("foglia")) {				
//				current.setAttribute("check", "si");
//			} else {
//				
//			}
//			aggiornaXml();
//			current = current.getChild(last.getText());
//		} else {
//			System.out.println("errore nella creazione dell'albero");
//		}
//	}
	
	private void aggiornaXml(){
		FileOutputStream fileOutputStream = null;
		try {
			fileOutputStream = new FileOutputStream("dynQuery.xml");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		try {
			xmlOutputter.output(albero, fileOutputStream);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}