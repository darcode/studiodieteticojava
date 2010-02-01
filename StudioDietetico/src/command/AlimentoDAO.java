package command;

import hibernate.Alimento;



public class AlimentoDAO extends BaseDAO{
	public AlimentoDAO(){}
	
	
	public void insAlimento(String nome, String tipologia){
		//close();
		getSession();
		begin();
		Alimento alimento = new Alimento();
		alimento.setNome(nome);
		alimento.setTipologia(tipologia);
		getSession().saveOrUpdate(alimento);
		commit();
		close();
	}

}
