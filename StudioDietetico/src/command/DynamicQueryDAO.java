package command;

import hibernate.Medico;
import hibernate.Visita;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;

import service.DynNode;

public class DynamicQueryDAO extends BaseDAO{
	private Criteria criteria;
	private Object filtroQuery;
	
	public static void main(String[] args) {
		Medico m = new Medico();
		
		
		Session session = getSession();
		begin();
		Criteria c  = session.createCriteria(m.getClass()); 
		List result = c.list();		
		Medico primo = (Medico) result.get(0);
		primo.getVisitas();
		System.out.println("ciao");

	}
	
	public DynamicQueryDAO(String pathClasse, String nomeClasse){
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
	
	public void createCriteria(DynNode node, HashMap dynAlbero){			
		
		//Si crea il ramo a ritroso partendo dalla foglia e arrivando alla radice
		ArrayList<DynNode> ramo = new ArrayList<DynNode>(); 
		ramo.add(node);
		while (node.getTreeNode().getParentItem()!=null) {
			String key = "";			
			if (node.getTreeNode().getParentItem()!=null) {
				key = node.getTreeNode().getParentItem().getText().concat("_").concat(node.getTreeNode().getText()).toUpperCase();
			} else {
				key = "radiceAlbero".concat("_").concat(node.getTreeNode().getText()).toUpperCase();
			}		
			node = (DynNode) dynAlbero.get(key);
			ramo.add(node);
		}		
		
		//Partendo dall'ultimo della lista (e quindi dalla radice) e proseguendo a ritroso (andando dunque alle foglie),
		//per ogni nodo del ramo esegue:
		//1- controlla se è istanziata la classe corrispondente ed eventualmente la istanzia
		//2- controlla se ci sono criteri da aggiungere e li aggiunge
		
		//TODO ricordarsi di gestire diversamente una chiave esterna ad una classe hibernate 
		//con una chiave esterna ad un hashset di classe hibernate
		
		while(!ramo.isEmpty()){
			DynNode currentNode = ramo.get(ramo.size()-1);
		}
		
		

	}
	
	
	
	public List executeDynQuery () {
        List results = criteria.list();
		return results;
	}
	
}