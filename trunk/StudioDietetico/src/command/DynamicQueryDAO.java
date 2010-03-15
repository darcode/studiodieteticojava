package command;

import org.hibernate.Criteria;
import org.hibernate.Session;

import service.DynNode;

public class DynamicQueryDAO extends BaseDAO{;
	
	public DynamicQueryDAO(String pathClasse, String nomeClasse){
		Class c = null;
		try {
			c = Class.forName(pathClasse);
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}		
		Session session = getSession();
		begin();
	}
	
	public void createCriteria(DynNode current){
		Criteria criterion = getSession().createCriteria(current.getPathClass());
	}
	
	
	
//	public List executeDynQuery () {
//        List results = criteria.list();
//		return results;
//	}
	
}