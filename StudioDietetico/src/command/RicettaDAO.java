package command;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import hibernate.Alimento;
import hibernate.Composizione;
import hibernate.Ingrediente;
import hibernate.Ricetta;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;


public class RicettaDAO extends BaseDAO {

	public Ricetta inserisciNuovaRicetta(String nome, String procedimento){
		
		//close();
		getSession();
		begin();
		Ricetta ric = new Ricetta();
		
		ric.setNome(nome);
		ric.setProcedimento(procedimento);
		getSession().saveOrUpdate(ric);
		commit();
		close();
		return ric;
	}
}