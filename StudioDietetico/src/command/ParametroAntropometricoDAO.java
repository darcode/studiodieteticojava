package command;

import java.util.ArrayList;
import java.util.List;

import hibernate.Parametroantropometrico;

import org.eclipse.swt.widgets.TableItem;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;

public class ParametroAntropometricoDAO extends BaseDAO {

	
	public static ArrayList<Object> getParametroAntropometricoObject(){
		Criteria criteria = getSession().createCriteria(hibernate.Parametroantropometrico.class);
		begin();
		ArrayList<Object> ris = (ArrayList<Object>)criteria.list();
		commit();
		//close();
		return ris;
	}
	
	public void registraParametro(int idparametro,String nome,String descrizione){
			//close();
			getSession();
			begin();
			Parametroantropometrico par = new Parametroantropometrico();
			if (idparametro ==0) {
				par.setNome(nome);
				par.setDescrizione(descrizione);
				//getSession().save(par);
			}else {
				par.setIdParametroAntropometrico(idparametro);
				par.setNome(nome);
				par.setDescrizione(descrizione);
				//getSession().update(par);
				
			}
			getSession().merge(par);
			//getSession().saveOrUpdate(par);
			
			commit();
			close();
		}
		
		public static ArrayList<Parametroantropometrico> getParametri() {
			getSession();
			begin();
			Query q = getSession().createQuery(" FROM Parametroantropometrico p ORDER BY p.nome");
			ArrayList<Parametroantropometrico> parametri = (ArrayList<Parametroantropometrico>)q.list();
			commit();
			return parametri;

		}
		
		public static Parametroantropometrico getParametro(int idparametro) {
			getSession();
			begin();
			Query q = getSession().createQuery(" FROM Parametroantropometrico p where idParametroAntropometrico ='" + idparametro +  "'");
			commit();
			Parametroantropometrico parametro = (Parametroantropometrico)q.uniqueResult();
			return parametro;

		}
		
		
		public static Parametroantropometrico getParametro(String nomeparametro) {
			getSession();
			begin();
			Query q = getSession().createQuery(" FROM Parametroantropometrico p where Nome ='" + nomeparametro +  "'");
			commit();
			Parametroantropometrico parametro = (Parametroantropometrico)q.uniqueResult();
			return parametro;

		}
	
		public static  void cancellaParametro(Parametroantropometrico parametro) {
			getSession();
			begin();
			getSession().delete(parametro);
			commit();
			close();
			
		}
		
		public static void cancellaParametroAntropometrico(TableItem rigaTable) {
			begin();
			Criteria criteria = getSession().createCriteria(hibernate.Parametroantropometrico.class);
			int id = Integer.parseInt(rigaTable.getText(0));
			criteria.add( Restrictions.eq("idParametroAntropometrico", id));
			List<Parametroantropometrico> par = (List<Parametroantropometrico>)criteria.list();
			commit();
			begin();
			getSession().delete(par.get(0));
			commit();
			close();
		}
}
