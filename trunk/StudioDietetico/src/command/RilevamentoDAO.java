package command;

import hibernate.Misurazione;
import hibernate.Parametroantropometrico;
import hibernate.Paziente;
import hibernate.Rilevamento;

import java.util.Date;
import java.util.Set;

public class RilevamentoDAO extends BaseDAO{

	public void registraRilevamento(Date data,Misurazione misurazione,String valore){
		//close();
		getSession();
		begin();
		Rilevamento ril = new Rilevamento();
		ril.setData(data);
		ril.setMisurazione(misurazione);
		ril.setValore(valore);
		getSession().saveOrUpdate(ril);
		commit();
		close();
	}
	
}
