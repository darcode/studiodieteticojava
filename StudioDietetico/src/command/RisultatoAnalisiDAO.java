package command;

import hibernate.Misurazione;
import hibernate.Referto;
import hibernate.Rilevamento;
import hibernate.Risultatoanalisi;

import java.util.Date;

public class RisultatoAnalisiDAO extends BaseDAO{

	public void registraRisultatoAnalisi(Date data,Referto referto,String valore,String note){
		//close();
		getSession();
		begin();
		Risultatoanalisi ril = new Risultatoanalisi();
		ril.setData(data);
		ril.setReferto(referto);
		ril.setValore(valore);
		ril.setNote(note);
		getSession().saveOrUpdate(ril);
		commit();
		close();
	}
	
}
