package command;


import java.util.ArrayList;
import java.util.List;

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


public class ComposizioneDAO extends BaseDAO {

	public void inserisciComposizione(Ingrediente ingrediente, String quantita, Ricetta ricetta){
		
		//close();
		getSession();
		begin();
		Composizione comp = new Composizione();
		comp.setIngrediente(ingrediente);
		comp.setQuantita(quantita);
		comp.setRicetta(ricetta);
		
		getSession().saveOrUpdate(comp);
		commit();
		close();
	}
}