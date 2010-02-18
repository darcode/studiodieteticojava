package command;

import hibernate.Medico;
import hibernate.Prestazione;
import hibernate.PrestazioneId;
import hibernate.Turno;
import hibernate.Visita;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.swt.widgets.DateTime;
import org.hibernate.Query;

import service.Utils;

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

	public static ArrayList<Medico> getMedici() {
		getSession();
		begin();
		Query q = getSession().createQuery("FROM Medico m ORDER BY m.cognome");
		ArrayList<Medico> medici = (ArrayList<Medico>) q.list();
		commit();
		return medici;

	}

	public static ArrayList<Turno> getTurni() {
		getSession();
		begin();
		Query q = getSession().createQuery("FROM Turno t ORDER BY t.nome");
		ArrayList<Turno> turni = (ArrayList<Turno>) q.list();
		commit();
		return turni;
	}

	public static ArrayList<Prestazione> getTurniMediciByMese(int mese) {
		getSession();
		begin();
		Date now = new Date();
		String dataTurnoInizioString = "" + (now.getYear() + 1900) + "-" + mese
				+ "-01";
		String dataTurnoFineString = "" + (now.getYear() + 1900) + "-" + mese
				+ "-31";
		// String formato = "yyyy-MM-dd";
		// Date dn = Utils.convertStringToDate(dataTurnoString, formato);
		Query q = getSession().createQuery(
				"FROM Prestazione p WHERE p.id.dataTurno BETWEEN'"
						+ dataTurnoInizioString + "' AND '"
						+ dataTurnoFineString + "'");
		ArrayList<Prestazione> turniMediciMese = (ArrayList<Prestazione>) q
				.list();
		commit();
		return turniMediciMese;
	}

	public void RegistraTurno(String nome, Date oraInizio, Date oraFine) {
		getSession();
		begin();
		Turno turno = new Turno();
		turno.setNome(nome);
		turno.setOraInizio(oraInizio);
		turno.setOraFine(oraFine);
		getSession().saveOrUpdate(turno);
		commit();
		close();
	}

	public void RegistraTurnoMedico(Medico medico, Turno turno, Date dataTurno) {
		getSession();
		begin();
		Prestazione pr = new Prestazione();
		pr.setMedico(medico);
		pr.setTurno(turno);
		PrestazioneId prId = new PrestazioneId();
		prId.setIdMedico(medico.getIdMedico());
		prId.setIdTurno(turno.getIdTurno());
		prId.setDataTurno(dataTurno);
		pr.setId(prId);
		getSession().saveOrUpdate(pr);
		commit();
		close();
	}

	public void RimuoviTurnoMedico(Medico medico, Date dataTurno) {
		getSession();
		begin();
		String dataTurnoString = "" + (dataTurno.getYear() + 1900) + "-"
				+ (dataTurno.getMonth() + 1) + "-" + dataTurno.getDate();
		// String formato = "yyyy-MM-dd";
		// Date dn = Utils.convertStringToDate(dataTurnoString, formato);
		Query q = getSession().createQuery(
				"FROM Prestazione p WHERE p.id.dataTurno='" + dataTurnoString
						+ "' AND p.medico.idMedico=" + medico.getIdMedico());
		ArrayList<Prestazione> turniMedico = (ArrayList<Prestazione>) q.list();
		for (int i = 0; i < turniMedico.size(); i++) {
			begin();
			Prestazione prestaz = new Prestazione();
			prestaz = (Prestazione) turniMedico.get(i);
			getSession().delete(prestaz);
			commit();
		}
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

	public static ArrayList<Object> getPrestazioni(int anno) {
		begin();
		try {
			Query q = getSession().createQuery(
					"FROM Prestazione pr join pr.medico md where YEAR(pr.id.dataTurno) = "
							+ anno);
			List pr = q.list();
			commit();
			return (ArrayList<Object>) pr;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	public static double getPrestazioniNmrPerTipologia(Integer tipologiaId) {
		begin();
		int percentuale = 0;
		try {
			Query q = getSession().createQuery("FROM Medico me ");

			int perTipologia = 0;
			for (Medico item : (ArrayList<Medico>) q.list()) {
				Iterator<Set> it = item.getVisitas().iterator();
				while (it.hasNext()) {
					if (((Visita) it.next()).getPrenotazione()
							.getTipologiavisita().getIdTipologiaVisita() == tipologiaId)
						perTipologia++;
				}
			}
			Query q1 = getSession().createQuery("FROM Visita vs where vs.prenotazione.tipologiavisita.idTipologiaVisita =  " + tipologiaId);
			int totale = q1.list().size();
			percentuale = (perTipologia == 0) ? 0 : (totale / perTipologia);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return percentuale;

	}

	public static int getNumPrestazioniMedico(int medico, int mese) {
		begin();
		try {
			Query q = getSession().createQuery(
					"FROM Prestazione pr where pr.medico.idMedico = " + medico
							+ " and MONTH(pr.id.dataTurno) = " + mese);
			List pr = q.list();
			commit();
			return pr.size();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;

	}
	
	public static ArrayList<Object> getMediciObject() {
		getSession();
		begin();
		Query q = getSession().createQuery("FROM Medico m ORDER BY m.cognome");
		ArrayList<Object> medici = (ArrayList<Object>) q.list();
		commit();
		return medici;

	}
	
	public static ArrayList<Object> getPrestazioniObject() {
		getSession();
		begin();
		Query q = getSession().createQuery("FROM Prestazione p ORDER BY p.id.dataTurno");
		ArrayList<Object> prestazioni = (ArrayList<Object>) q.list();
		commit();
		return prestazioni;
	}

}
