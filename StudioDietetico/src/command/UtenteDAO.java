package command;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.PropertyAccessException;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;

public class UtenteDAO extends BaseDAO {	
//	Logger l= Logger.getLogger(UtenteDAO.class);
//	
//	public UtenteDAO(){}
//
//	public User get(String username) throws UtenteNotFoundExceptionDAO {
//		l.info(Utils.getStringFromBoundle(MessagesConstants.Load_Utente_Username, null) + " " + username); 
//		
//		User user = null;
//		try {
//			begin();
//			
//			l.info(Utils.getStringFromBoundle(MessagesConstants.Open_Database, null));
//			
//			Query q = getSession().createQuery("from User where nome_utente = '" + username + "'");
//			
//			user = (User)q.uniqueResult();
//			commit();
//			
//			l.info(Utils.getStringFromBoundle(MessagesConstants.Close_Database, null));
//			
//			if (user == null){
//				
//			}
//		} catch(Exception e) {
//			l.error(e);
//			throw new UtenteNotFoundExceptionDAO();
//		}
//		
//		return user;
//	} 
//	
//	public User get(String username, String password) throws UtenteNotFoundExceptionDAO {
//		l.info(Utils.getStringFromBoundle(MessagesConstants.Load_Utente_Username, null) + " " + username); 
//		
//		User user = null;
//		try {
//			begin();
//			
//			l.info(Utils.getStringFromBoundle(MessagesConstants.Open_Database, null));
//			
//			Query q = getSession().createQuery("from User where nome_utente = '" + username + "' AND password = '" + password + "'");
//			
//			user = (User)q.uniqueResult();
//			commit();
//			
//			l.info(Utils.getStringFromBoundle(MessagesConstants.Close_Database, null));
//				
//			
//			
//			
//			GregorianCalendar now = new GregorianCalendar();
//			now.setTime(new Date());
//			GregorianCalendar lastLogin = new GregorianCalendar();
//			lastLogin.setTime(user.getUltimoAccesso());
//			
////			long nowMilliseconds = now.getTimeInMillis();
////			long lastLoginMilliseconds = lastLogin.getTimeInMillis();
////			long diff = nowMilliseconds - lastLoginMilliseconds;
////			long diffDays = diff / (24 * 60 * 60 * 1000);
////			long diffSeconds = diff / 1000;
//			
//			
//			double diff = Utils.giorniFraDueDate(lastLogin, now);
//			
//			if (diff>ApplicationConstants.Intervallo_Scadenza_Password) {
//				user.setStato(ApplicationConstants.User_Stato_Da_perfezionare);
//				l.info("password scaduta, utente: " + user.getNomeUtente());
//				aggiornaUtente(user);
//			}
//		} catch(Exception e) {
//			l.error(e);
//			throw new UtenteNotFoundExceptionDAO();
//		}
//		
//		return user;
//	} 
//	
//	public void PerfezionaUtente(User utente) {
//		l.info("Aggiorno la password di " + utente.getNomeUtente());
//		begin();
//		getSession().update(utente); //TODO decidere se cambiare con saveOrUpdate
//		commit();
//	}
//	
//	public void RegistraUtente(User utente) throws UtenteEsistenteException {
//		l.info("Sto registrando l'utente: "+utente.getNomeUtente());
//		begin();
//
//		Query q = getSession().createQuery("from User as u where u.nomeUtente= '" + utente.getNomeUtente() + "' OR u.nominativo = '" + utente.getNominativo() +
//				"' OR  u.identificativo = '"+ utente.getIdentificativo() +"'");
//		commit();
//		User result = (User) q.uniqueResult();
//			
//		if (result == null){
//			// Iscrive l'utente			
//			Date iscrizione = new Date();
//			utente.setDataCreazione(iscrizione);
//			utente.setDataModifica(iscrizione);
//			utente.setUltimoAccesso(iscrizione);
//			begin();
//			getSession().save(utente);
//			commit();
//		} else {
//			String msg = "Nome Utente o Ragione Sociale già esistente";
//			throw new UtenteEsistenteException(msg, "info");
//		}
//	}
//	
//	/**
//	 * Fornisce tutti gli utenti aventi il profilo specificato
//	 * @param idProfilo id del profilo di cui si vuole ottenere gli utenti
//	 * @return lista di utenti
//	 * @throws PropertyAccessException
//	 */
//	public List<User> getUtenti(int statoUtente) throws ListaUtentiExceptionDAO {
//		l.info("Richiesta lista utenti...");
//		List<User> utenti = null;
//		try {
//			begin();
//	
//			Criteria crit = getSession().createCriteria(User.class);
//			if (statoUtente != ApplicationConstants.User_Level_Administrator &&
//					statoUtente != ApplicationConstants.User_Level_Developer)
//				crit.add(Restrictions.eq("ruolo", ApplicationConstants.User_Level_User));
//			else
//				crit.add(Restrictions.not(Restrictions.eq("ruolo", ApplicationConstants.User_Level_Developer)));
//			utenti = crit.list();
//		} catch(Exception e) {
//			l.error("Errore durante il recupero della lista utenti...");
//			throw new ListaUtentiExceptionDAO(ApplicationConstants.Log_Level_Info);
//		}
//
//		l.info("Lista utenti recuperata...");
//		
//		return utenti;
//	}
//	
//	public void aggiornaUtente(User utente){
//		l.info("Salvo a DB le nuove info dell'utente: " + utente.getNomeUtente());
//		begin();
//		getSession().update(utente);
//		commit();		
//	}
//
//	public void cancellaUtente(String nome_utente) throws UtenteNotFoundExceptionDAO {
//		begin();
//		User utente;
//		utente = get(nome_utente);
//		getSession().delete(utente);
//		
//	}
}
