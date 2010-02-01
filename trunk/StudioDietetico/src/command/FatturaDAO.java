package command;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;

import hibernate.Fattura;
import hibernate.Prenotazione;

public class FatturaDAO extends BaseDAO {
	public FatturaDAO(){}
	
	public int registraFattura(String descrizione, double importo, double acconto, double importoSconto, String note){
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
		//System.out.println(idFattura);
		return idFattura;
	}
	
	public static ArrayList<Fattura> getFatture() {
		begin();
		Query q = getSession().createQuery("FROM Fattura fat ORDER BY fat.data");
		ArrayList<Fattura> fatture = (ArrayList<Fattura>)q.list();
		commit();
		return fatture;

	}
	
	public Fattura getFatturaByID(int id) {
		begin();
		Query q = getSession().createQuery("FROM Fattura f WHERE f.idFattura="+id);
		Fattura fattura = new Fattura();
		fattura = (Fattura) q.uniqueResult();
		commit();
		return fattura;
	}
	
	public static void aggiornaFattura(Fattura fat, String descrizione, double importo, double acconto, double importoSconto, String note){
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
}
