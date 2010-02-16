package command;

import hibernate.Funzione;
import hibernate.Utente;

import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;

public class UtenteDAO extends BaseDAO {
	public UtenteDAO() {
	}

	public static Utente get(String username) throws Exception {
		Utente user = null;
		try {
			begin();
			Query q = getSession().createQuery(
					"from Utente where nomeUtente = '" + username + "'");

			user = (Utente) q.uniqueResult();
			commit();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

		return user;
	}

	public static Utente get(String username, String password) {
		Utente user = null;
		try {
			begin();
			Query q = getSession().createQuery(
					"from Utente u  where u.nomeUtente = '" + username
							+ "' AND u.password = '" + password + "'");
			user = (Utente) q.uniqueResult();
			commit();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return user;
	}

	public static void RegistraUtente(Utente utente) throws Exception {
		begin();
		Query q = getSession().createQuery(
				"from Utente as u where u.nomeUtente= '"
						+ utente.getNomeUtente() + "' ");
		commit();
		Utente result = (Utente) q.uniqueResult();

		if (result == null) {
			// Iscrive l'utente
			begin();
			getSession().save(utente);
			commit();
			getSession().flush();
		} else {
			String msg = "Nome Utente o Ragione Sociale già esistente";
			throw new Exception(msg);
		}
	}

	/**
	 * Fornisce tutti gli utenti aventi il profilo specificato
	 * 
	 * @param idProfilo
	 *            id del profilo di cui si vuole ottenere gli utenti
	 * @return lista di utenti
	 */
	public static List<Utente> getUtenti(int profiloUtente) throws Exception {
		List<Utente> utenti = null;
		try {
			begin();

			Criteria crit = getSession().createCriteria(Utente.class);
			crit.add(Restrictions.not(Restrictions.eq("ruolo", profiloUtente)));
			utenti = crit.list();
		} catch (Exception e) {
			String mes = ("Errore durante il recupero della lista utenti...");
			throw new Exception(mes);
		}

		return utenti;
	}

	public static void cancellaUtente(String nome_utente) throws Exception {
		begin();
		Utente utente;
		utente = get(nome_utente);
		getSession().delete(utente);

	}
	public static boolean hasRole(String nome_utente, String roleString) throws Exception{
		begin();
		Utente utente;
		utente = get(nome_utente);
		if(roleString.equals(utente.getRuolo().getDescrizione()))
			return true;
		else
			return false;
		
	}
	public static boolean canDo(String nome_utente, String function) throws Exception{
		begin();
		Utente utente;
		utente = get(nome_utente);
		if(utente!= null && utente.getRuolo()!= null && utente.getRuolo().getFunziones()!= null){
			
			Set functions  = utente.getRuolo().getFunziones();
				for(Object item: functions){
					if(function.equals(((Funzione)item).getDescrizione()))
						return true;
				}
				return false;
		}
		else
			return false;
		
	}
}
