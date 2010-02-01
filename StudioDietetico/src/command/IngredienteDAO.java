package command;

import hibernate.Ingrediente;
import hibernate.Paziente;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.PropertyAccessException;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;

public class IngredienteDAO extends BaseDAO {	
	
	public void inserisciIngrediente(String nome){
		
			//close();
			getSession();
			begin();
			Ingrediente ing = new Ingrediente();
			ing.setNome(nome);
			getSession().saveOrUpdate(ing);
			commit();
			close();
		}
		
		public static String[] getIngredienti() {
			begin();
			Query q = getSession().createQuery("FROM Ingrediente i ORDER BY i.nome");
			List<Ingrediente> ingredienti = (List<Ingrediente>)q.list();
			List<String> ut = new ArrayList<String>();
			for (Ingrediente ing : ingredienti) {
				ut.add(ing.getNome());
			}
			String[] ingredientiArray = (String[]) ut.toArray((new String[0]));
			commit();
			close();
			return ingredientiArray;

		}

		public Ingrediente getIngrediente(String nome) {
			Ingrediente ing;
			begin();
			Query q = getSession().createQuery("FROM Ingrediente WHERE  nome like '"+ nome +"'");
			List<Ingrediente> ingredienti = (List<Ingrediente>)q.list();
			
			commit();
			close();
			return ingredienti.get(0);
		}
		 
}
