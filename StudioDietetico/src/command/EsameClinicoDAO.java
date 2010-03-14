package command;

import hibernate.Esameclinico;
import hibernate.Parametroesame;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.widgets.TableItem;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;


public class EsameClinicoDAO extends BaseDAO{

	public static ArrayList<Object> getEsameClinicoObject(){
		Criteria criteria = getSession().createCriteria(hibernate.Esameclinico.class);
		begin();
		ArrayList<Object> ris = (ArrayList<Object>)criteria.list();
		commit();
		//close();
		return ris;
	}
	
	public Esameclinico registranewEsame(String nome,String descrizione){
		//close();
		getSession();
		begin();
		Esameclinico par = new Esameclinico();
		
		par.setNome(nome);
		par.setDescrizione(descrizione);
			//getSession().update(par);
			
		getSession().saveOrUpdate(par);
		//getSession().saveOrUpdate(par);
		
		commit();
		close();
		return par;
	}
	
	public Esameclinico registraEsame(Esameclinico esame){
		//close();
		getSession();
		begin();
		getSession().saveOrUpdate(esame);
		//getSession().saveOrUpdate(par);
		
		commit();
		close();
		return esame;
	}
	
	public static ArrayList<Esameclinico> getEsamiClinici() {
		getSession();
		begin();
		Query q = getSession().createQuery(" FROM Esameclinico ORDER BY nome");
		ArrayList<Esameclinico> esami = (ArrayList<Esameclinico>) q.list();
		commit();
		return esami;
	}
	
	public static ArrayList<Parametroesame> getParametriEsame(int idesame) {
		getSession();
		begin();
		Query q = getSession().createQuery(" FROM Parametroesame p where FK_EsameClinico='" + idesame + "' ORDER BY p.nome");
		ArrayList<Parametroesame> parametri = (ArrayList<Parametroesame>)q.list();
		commit();
		return parametri;
	}
	
	public static Esameclinico getEsameClinico(String nomeesame) {
		getSession();
		begin();
		Query q = getSession().createQuery(" FROM Esameclinico p where Nome ='" + nomeesame +  "'");
		commit();
		Esameclinico esame = (Esameclinico)q.uniqueResult();
		return esame;

	}
	
	public static Esameclinico getEsameClinico(Integer idesame) {
		getSession();
		begin();
		Query q = getSession().createQuery(" FROM Esameclinico p where id ='" + idesame +  "'");
		commit();
		Esameclinico esame = (Esameclinico)q.uniqueResult();
		return esame;

	}

	public static  void cancellaEsame(Esameclinico esame) {
		getSession();
		begin();
		getSession().delete(esame);
		commit();
		close();
		
	}
	
	public static void cancellaEsameClinico(TableItem rigaTable) {
		begin();
		Criteria criteria = getSession().createCriteria(hibernate.Esameclinico.class);
		int id = Integer.parseInt(rigaTable.getText(0));
		criteria.add( Restrictions.eq("idEsameClinico", id));
		List<Esameclinico> es = (List<Esameclinico>)criteria.list();
		commit();
		begin();
		getSession().delete(es.get(0));
		commit();
		close();
	}
	
	
	
	
	
	
}
