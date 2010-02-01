package command;

import java.util.ArrayList;

import org.hibernate.Query;

import hibernate.Esameclinico;
import hibernate.Parametroesame;

public class ParametroClinicoDAO extends BaseDAO{
	
	public Parametroesame registraParametroClinico(int idParametroClinico,String nome,String descrizione,String minUomo,String maxUomo,String minDonna,String maxDonna, String minBambino,String maxBambino,Esameclinico esame){
		//close();
		getSession();
		begin();
		//Esameclinico esameclinico = new Esameclinico();
		//if (idesameclinico !=0) {  //esame esistente
		//	esameclinico.setIdEsameClinico(idesameclinico);
		//}
		//esameclinico.setNome(txtnome.getText());
		//esameclinico.setDescrizione(txtDescrizione.getText());
		
		Parametroesame par = new Parametroesame();
		if (idParametroClinico ==0) {
			
			//getSession().save(par);
		}else {
			par.setIdParametroEsame(idParametroClinico);
			
		}
		par.setNome(nome);
		par.setDescrizione(descrizione);
		par.setMinUomo(minUomo);
		par.setMaxUomo(maxUomo);
		par.setMinDonna(minDonna);
		par.setMaxDonna(maxDonna);
		par.setMinBambino(minBambino);
		par.setMaxBambino(maxBambino);	
		par.setEsameclinico(esame);
		getSession().saveOrUpdate(par);
		//getSession().saveOrUpdate(par);
		
		commit();
		close();
		return par;
	}
	
	
	public static  void cancellaParametroClinico(Parametroesame parametro) {
		getSession();
		begin();
		Parametroesame p = new Parametroesame();
		p = parametro;
		//getSession().evict(parametro);
		//getSession().flush();
		getSession().delete(parametro);
		commit();
		close();
		
	}
	
	public static Parametroesame getParametroClinico(int idparametro) {
		getSession();
		begin();
		Query q = getSession().createQuery(" FROM Parametroesame p where idParametroEsame='" + idparametro + "'");
		Parametroesame parametro = (Parametroesame)q.uniqueResult();
		commit();
		return parametro;
		
	}
	
	public static Parametroesame getParametroClinico(String nomeparametro,int idesame) {
		getSession();
		begin();
		Query q = getSession().createQuery(" FROM Parametroesame p where nome='" + nomeparametro + "' and FK_EsameClinico='" + idesame + "'");
		Parametroesame parametro = (Parametroesame)q.uniqueResult();
		commit();
		return parametro;
		
	}

}
