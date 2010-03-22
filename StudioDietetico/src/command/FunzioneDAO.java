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
	public static Set getFunzioniAsSet(ArrayList<String> descr) throws Exception {
		Set funzioni = new HashSet();
		try {
			for(String item:descr){
				Query q = getSession().createQuery("from Funzione where descrizione = '" +item+"'");
				funzioni.add(q.list().get(0));
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return funzioni;
	}
}
