package command;

import hibernate.Medico;
import hibernate.Prenotazione;
import hibernate.Prestazione;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.hibernate.Query;

public class MedicoDAO extends BaseDAO {
	public MedicoDAO() {
	}

	public void registraMedico(String cognome, String nome, String sesso,
			Date datanascita, String codicefiscale, String indirizzo,
			String citta, String cap, String provincia,
			String specializzazione, String telefono1, String telefono2,
			String email) {
		// close();
		getSession();
		begin();
		Medico med = new Medico();
		med.setCognome(cognome);
		med.setNome(nome);
		char ses = sesso.charAt(0);
		med.setSesso(ses);
		med.setDataNascita(datanascita);
		med.setCodiceFiscale(codicefiscale);
		med.setIndirizzo(indirizzo);
		med.setCitta(citta);
		med.setCap(cap);
		med.setProvincia(provincia);
		med.setSpecializzazione(specializzazione);
		med.setTelefono1(telefono1);
		med.setTelefono2(telefono2);
		med.setEmail(email);
		getSession().saveOrUpdate(med);
		commit();
		close();
	}

	public static List<Medico> getMedici() {
		getSession();
		begin();
		Query q = getSession().createQuery("FROM Medico m ORDER BY m.cognome");
		List<Medico> medici = (List<Medico>) q.list();
		commit();
		return medici;

	}

	public static ArrayList<HashMap<String, String>> getMediciForStatistics() {
		getSession();
		begin();
		Query q = getSession().createQuery("FROM Medico m ORDER BY m.cognome");
		ArrayList<HashMap<String, String>> ris = new ArrayList<HashMap<String, String>>();
		List<Medico> medici = (List<Medico>) q.list();
		for (Medico item : medici) {
			HashMap<String, String> riga = new HashMap<String, String>();
			riga.put("CAP", item.getCap());
			riga.put("Codice Fiscale", item.getCodiceFiscale());
			riga.put("Cognome", item.getCognome());
			ris.add(riga);
		}
		commit();
		return ris;

	}
	public static List<Object> getPrestazioni(int anno) {
		begin();
		try {
			Query q = getSession().createQuery(
					"FROM Prestazione pr join pr.medico md where YEAR(pr.DataTurno) = " + anno);
			List<Object> pr = (List<Object>) q.list();
			commit();
			System.out.println("fatto:" + pr.size());
			return pr;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}
	

}
