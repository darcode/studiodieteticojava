package command;

import org.hibernate.Session;

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
	
//	public List executeDynQuery () {
//        List results = criteria.list();
//		return results;
//	}
	
}