package command;

import hibernate.Misurazione;
import hibernate.MisurazioneId;
import hibernate.Parametroantropometrico;
import hibernate.Paziente;

public class MisurazioneDAO extends BaseDAO{

	public Misurazione registraMisurazione(Parametroantropometrico parametro,Paziente paziente,String osservazioni){
		//close();
		getSession();
		begin();
		Misurazione mis = new Misurazione();
		mis.setOsservazioni(osservazioni);
		mis.setPaziente(paziente);
		mis.setParametroantropometrico(parametro);
		MisurazioneId idmis = new MisurazioneId();
		idmis.setIdParametroAntropometrico(parametro.getIdParametroAntropometrico());
		idmis.setIdPaziente(paziente.getIdPaziente());
		mis.setId(idmis);
		getSession().saveOrUpdate(mis);
		commit();
		close();
		return mis;
	}
	
}
