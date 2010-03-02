package command;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;

import hibernate.Fattura;
import hibernate.Visita;

public class FatturaDAO extends BaseDAO {
	public FatturaDAO() {
	}

	public int registraFattura(String descrizione, double importo,
			double acconto, double importoSconto, String note) {
		getSession();
		begin();
		Fattura fat = new Fattura();
		fat.setDescrizione(descrizione);
		fat.setImporto(importo);
		fat.setAcconto(acconto);
		fat.setImportoSconto(importoSconto);
		fat.setNote(note);
		Date now = new Date();
		fat.setData(now);
		getSession().saveOrUpdate(fat);
		commit();
		close();
		int idFattura = fat.getIdFattura();
		// System.out.println(idFattura);
		return idFattura;
	}

	public static ArrayList<Fattura> getFatture() {
		begin();
		Query q = getSession()
				.createQuery("FROM Fattura fat ORDER BY fat.data");
		ArrayList<Fattura> fatture = (ArrayList<Fattura>) q.list();
		commit();
		return fatture;

	}

	public static Fattura getFatturaByID(int id) {
		begin();
		Query q = getSession().createQuery(
				"FROM Fattura f WHERE f.idFattura=" + id);
		Fattura fattura = new Fattura();
		fattura = (Fattura) q.uniqueResult();
		commit();
		return fattura;
	}

	public static void aggiornaFattura(Fattura fat, String descrizione,
			double importo, double acconto, double importoSconto, String note) {
		getSession();
		begin();
		fat.setDescrizione(descrizione);
		fat.setImporto(importo);
		fat.setAcconto(acconto);
		fat.setImportoSconto(importoSconto);
		fat.setNote(note);
		Date now = new Date();
		fat.setData(now);
		getSession().update(fat);
		commit();
		close();
	}

	public static int getNumFattureMese(int mese) {
		begin();
		try {
			Query q = getSession().createQuery(
					"FROM Fattura  fatt where MONTH(fatt.data) = " + mese);
			List pr = q.list();
			commit();
			return pr.size();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;

	}

	public static double getFatturatoMese(int mese) {
		begin();
		try {
			double ris = 0;
			Query q = getSession().createQuery(
					" FROM Fattura  fatt where MONTH(fatt.data) = " + mese);
			for (Fattura item : (ArrayList<Fattura>) q.list()) {
				ris += item.getImporto();
			}
			commit();
			return ris;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;

	}
	
	public static ArrayList<Object> getFattureObject() {
		begin();
		Query q = getSession()
				.createQuery("FROM Fattura fat ORDER BY fat.data");
		ArrayList<Object> fatture = (ArrayList<Object>) q.list();
		commit();
		return fatture;

	}
	
	public static void associaFattura(Fattura fat, Visita vis) {
		getSession();
		begin();
		vis.setFattura(fat);
		getSession().update(vis);
		commit();
		close();
	}
	
	public static void cancellaFatture(int idFatt) {
		begin();
		Criteria criteria = getSession().createCriteria(hibernate.Fattura.class);
		criteria.add( Restrictions.eq("idFattura", idFatt));
		List<Fattura> fatture = (List<Fattura>)criteria.list();
		commit();
		begin();
		getSession().delete(fatture.get(0));
		commit();
		close();
	}
}
