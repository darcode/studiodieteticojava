package command;

import java.util.ArrayList;
import hibernate.Parametroantropometrico;

import org.hibernate.Query;

public class ParametroAntropometricoDAO extends BaseDAO {

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
}
