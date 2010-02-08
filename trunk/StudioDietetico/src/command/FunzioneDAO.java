package command;

import hibernate.Funzione;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.Query;

public class FunzioneDAO extends BaseDAO {

	public static ArrayList<Object> getAllFunzioni() {
		ArrayList<Object> funzioni = null;
		try {
			begin();
			Query q = getSession().createQuery(
					"from Funzione");
			funzioni = (ArrayList<Object>)q.list();
			commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return funzioni;
	}
	public static Set getFunzioniAsSet(int[] id) throws Exception {
		Set funzioni = new HashSet();
		try {
			begin();
			
			for(int item:id){
				Query q = getSession().createQuery("from Funzione where idFunzione = " + item+1);
				funzioni.add(q.uniqueResult());
			}
			commit();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return funzioni;
	}
}
