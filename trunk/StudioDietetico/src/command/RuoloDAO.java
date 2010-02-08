package command;

import hibernate.Ruolo;

import java.util.List;

import org.hibernate.Query;

public class RuoloDAO extends BaseDAO{

	public static Ruolo get(int ruoloID) throws Exception {
		Ruolo ruolo = null;
		try {
			begin();
			Query q = getSession().createQuery(
					"from Ruolo where idRuolo = '" + ruoloID + "'");
			ruolo = (Ruolo) q.uniqueResult();
			commit();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return ruolo;
	}
	
	public static Ruolo get(String descrRuolo) throws Exception {
		Ruolo ruolo = null;
		try {
			begin();
			Query q = getSession().createQuery(
					"from Ruolo where descrizione = '" + descrRuolo + "'");
			ruolo = (Ruolo) q.uniqueResult();
			commit();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return ruolo;
	}
	public static List<Ruolo> getAllRoules() {
		List<Ruolo> ruoli = null;
		try {
			begin();
			Query q = getSession().createQuery(
					"from Ruolo");
			 ruoli = q.list();
			commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ruoli;
	}
	
	public static boolean insRuolo(String descr, int[] idFunzioni) {
		try {
			begin();
			System.out.println(idFunzioni);
			Ruolo ruoloNew = new Ruolo(descr);
			ruoloNew.setFunziones(FunzioneDAO.getFunzioniAsSet(idFunzioni));
			getSession().save(ruoloNew);
			commit();
			getSession().flush();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
