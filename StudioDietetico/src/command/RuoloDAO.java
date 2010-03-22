package command;

import hibernate.Funzione;
import hibernate.Ruolo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.TreeItem;
import org.hibernate.Query;

public class RuoloDAO extends BaseDAO {

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

	public static Ruolo get(String descrRuolo) {
		Ruolo ruolo = null;
		try {
			begin();
			Query q = getSession().createQuery(
					"from Ruolo where descrizione = '" + descrRuolo + "'");
			ruolo = (Ruolo) q.uniqueResult();
			commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ruolo;
	}

	public static List<Ruolo> getAllRoules() {
		List<Ruolo> ruoli = null;
		try {
			begin();
			Query q = getSession().createQuery("from Ruolo");
			ruoli = q.list();
			commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ruoli;
	}

	public static ArrayList<Object> getAllRoulesForTable() {
		ArrayList<Object> ruoli = null;
		try {
			begin();
			Query q = getSession().createQuery("from Ruolo");
			ruoli = (ArrayList<Object>) q.list();
			commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ruoli;
	}

	public static boolean insRuolo(String descr, TreeItem[] funzioni) {
		try {
			begin();
			ArrayList<String> descrFunzioni = new ArrayList<String>();
			for (TreeItem item : funzioni) {
				if (item.getChecked()) {
					descrFunzioni.add((String) item.getData());
				}
				for (TreeItem item1 : item.getItems()) {
					if (item1.getChecked()) {
						descrFunzioni.add((String) item1.getData());
					}
					
				}
			}
			Ruolo ruoloNew = new Ruolo(descr);
			ruoloNew.setFunziones(FunzioneDAO.getFunzioniAsSet(descrFunzioni));
			getSession().save(ruoloNew);
			commit();
			getSession().flush();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public static boolean updateRuolo(Ruolo ruolo, TreeItem[] funzioni) {
		try {
			begin();
			ArrayList<String> descrFunzioni = new ArrayList<String>();
			for (TreeItem item : funzioni) {
				if (item.getChecked()) {
					descrFunzioni.add((String) item.getData());
				}
				for (TreeItem item1 : item.getItems()) {
					if (item1.getChecked()) {
						descrFunzioni.add((String) item1.getData());
					}
				}
			}
			ruolo.setFunziones(FunzioneDAO.getFunzioniAsSet(descrFunzioni));
			getSession().saveOrUpdate(ruolo);
			commit();
			close();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
