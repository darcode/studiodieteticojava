package command;

import hibernate.Misurazione;
import hibernate.MisurazioneId;
import hibernate.Parametroantropometrico;
import hibernate.Parametroesame;
import hibernate.Paziente;
import hibernate.Referto;
import hibernate.RefertoId;

public class RefertoDAO extends BaseDAO{

	public Referto registraReferto(Parametroesame parametroesame,Paziente paziente,String osservazioni){
		//close();
		getSession();
		begin();
		Referto ref = new Referto();
		ref.setOsservazioni(osservazioni);
		ref.setParametroesame(parametroesame);
		ref.setPaziente(paziente);
		RefertoId idmis = new RefertoId();
		idmis.setIdParametroEsame(parametroesame.getIdParametroEsame());
		idmis.setIdPaziente(paziente.getIdPaziente());
		ref.setId(idmis);
		getSession().saveOrUpdate(ref);
		commit();
		close();
		return ref;
	}
	
}
