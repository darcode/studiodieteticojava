package command;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;

import hibernate.Paziente;
import hibernate.Visita;

public class PazienteDAO extends BaseDAO{
	public PazienteDAO(){}
	
	public void registraPaziente(String cognome,String nome, String sesso, Date datanascita, String codicefiscale, String indirizzo, String citta, String cap, String provincia, String professione,
		String telefono1, String telefono2, String email, String numTessSanit, String note){
		//close();
		getSession();
		begin();
		Paziente paz = new Paziente();
		paz.setCognome(cognome);
		paz.setNome(nome);
		char ses = sesso.charAt(0);
		paz.setSesso(ses);
		paz.setDataNascita(datanascita);
		paz.setCodiceFiscale(codicefiscale);
		paz.setIndirizzo(indirizzo);
		paz.setCitta(citta);
		paz.setCap(cap);
		paz.setProvincia(provincia);
		paz.setProfessione(professione);
		paz.setTelefono1(telefono1);
		paz.setTelefono2(telefono2);
		paz.setEmail(email);
		paz.setNumTesseraSanitaria(numTessSanit);
		paz.setNote(note);
		getSession().saveOrUpdate(paz);
		commit();
		close();
	}
	
	public static ArrayList<Paziente> getPazienti() {
		getSession();
		begin();
		Query q = getSession().createQuery("FROM Paziente p ORDER BY p.cognome");
		ArrayList<Paziente> pazienti = (ArrayList<Paziente>)q.list();
/*		List<String> ut = new ArrayList<String>();
		for (Paziente paz : pazienti) {
			ut.add(paz.getCognome()+"_"+paz.getNome());
		}
		String[] pazientiArray = (String[]) ut.toArray((new String[0]));*/
		commit();
		return pazienti;

	}
	
	public static ArrayList<Object> getPazientiPerLista() {
		getSession();
		begin();
		Query q = getSession().createQuery("FROM Paziente p ORDER BY p.cognome");
		ArrayList<Object> pazienti = (ArrayList<Object>)q.list();
/*		List<String> ut = new ArrayList<String>();
		for (Paziente paz : pazienti) {
			ut.add(paz.getCognome()+"_"+paz.getNome());
		}
		String[] pazientiArray = (String[]) ut.toArray((new String[0]));*/
		commit();
		return pazienti;

	}
	
	public static Paziente getPazienteByCogNom (String paziente){
		getSession();
		begin();
		int separ = paziente.indexOf("_");
		String cognome = paziente.substring(0, separ);
		String nome = paziente.substring(separ+1, paziente.length());	
		
		Query qq = getSession().createQuery("from Paziente p WHERE p.cognome like '" + cognome + "' and p.nome like'" + nome + "'");
		Paziente p = new Paziente();
		p = (Paziente) qq.uniqueResult();
		commit();	
		close();
		return p;
				
	} 
	
	public static ArrayList<Object> getPazientiObject() {
		getSession();
		begin();
		Query q = getSession().createQuery("FROM Paziente p ORDER BY p.cognome");
		ArrayList<Object> pazienti = (ArrayList<Object>)q.list();
		commit();
		return pazienti;

	}
	
	public static Paziente getPazienteByID(int id) {
		begin();
		Query q = getSession().createQuery(
				"FROM Paziente p WHERE p.idPaziente=" + id);
		Paziente paziente = new Paziente();
		paziente = (Paziente) q.uniqueResult();
		return paziente;
	}
	
}




