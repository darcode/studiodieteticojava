package command;

import hibernate.Farmacoassunto;
import hibernate.Medico;
import hibernate.Paziente;
import hibernate.Prenotazione;
import hibernate.Tipologiavisita;
import hibernate.Visita;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.eclipse.swt.widgets.TableItem;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

public class VisitaDAO extends BaseDAO {
	public VisitaDAO() {
	}

	public static ArrayList<Tipologiavisita> getTipologVisita() {
		getSession();
		begin();
		Query q = getSession().createQuery(
				"FROM Tipologiavisita tv ORDER BY tv.tipologia");
		ArrayList<Tipologiavisita> tv = (ArrayList<Tipologiavisita>) q.list();
		/*
		 * List<String> tipvis = new ArrayList<String>(); for (Tipologiavisita
		 * tipolvisit : tv) { tipvis.add(tipolvisit.getTipologia()); } String[]
		 * tipolVisArray = (String[]) tipvis.toArray((new String[0]));
		 */
		commit();
		return tv;

	}

	public void prenotaVisita(Paziente paziente, Tipologiavisita tipolvisita,
			Date datavisita, String note) {
		// close();
		Session sessione = getSession();
		begin();
		Prenotazione pr = new Prenotazione();
		// Paziente p = new Paziente();
		// p = PazienteDAO.getPazienteByCogNom(paziente);
		pr.setPaziente(paziente);
		// Tipologiavisita v = new Tipologiavisita();
		// v = getTipolVisitByTip(tipolvisita);
		pr.setTipologiavisita(tipolvisita);
		pr.setDataOra(datavisita);
		pr.setNote(note);
		/*
		 * PrenotazioneId idPren = new PrenotazioneId();
		 * idPren.setIdPaziente(paziente.getIdPaziente());
		 * idPren.setIdTipologiaVisita(tipolvisita.getIdTipologiaVisita());
		 * pr.setId(idPren);
		 */
		sessione.save(pr);
		commit();
		close();
	}

	public Tipologiavisita getTipolVisitByTip(String tipologia) {
		getSession();
		begin();
		Tipologiavisita tv = new Tipologiavisita();
		Query q = getSession().createQuery(
				"from Tipologiavisita tv WHERE tv.tipologia like '" + tipologia
						+ "'");
		List<Tipologiavisita> LTip = q.list();
		tv = LTip.get(0);
		commit();
		return tv;
	}

	public static ArrayList<Prenotazione> getPrenotazioni() {
		begin();
		Query q = getSession().createQuery(
				"FROM Prenotazione pr ORDER BY pr.paziente.cognome");
		ArrayList<Prenotazione> pr = (ArrayList<Prenotazione>) q.list();
		commit();
		return pr;

	}

	public void registraVisita(Date datainizio, Date datafine,
			String motivazioni, String note,
			Medico medico, Prenotazione prenotazione) {
		getSession();
		begin();
		Visita vis = new Visita();
		vis.setDataOraInizio(datainizio);
		vis.setDataOraFine(datafine);
		vis.setMotivazioni(motivazioni);
		//vis.setStatoPagamento(statoPagamento);
		vis.setNote(note);
		//vis.setFattura(fattura);
		vis.setMedico(medico);
		vis.setPrenotazione(prenotazione);
		getSession().saveOrUpdate(vis);
		commit();
		close();
	}

	public static Prenotazione getPrenotazioneByID(int id) {
		begin();
		Query q = getSession().createQuery(
				"FROM Prenotazione p WHERE p.idPrenotazione=" + id);
		Prenotazione prenotazione = new Prenotazione();
		prenotazione = (Prenotazione) q.uniqueResult();
		return prenotazione;
	}

	public static double getPrenotazioniNumberPerTipologia(Integer tipologiaId) {
		begin();
		int percentuale = 0;
		try {
			Query q = getSession().createQuery(
					"FROM Prenotazione pr where pr.tipologiavisita = '"
							+ tipologiaId + "'");
			int perTipologia = q.list().size();
			Query q1 = getSession().createQuery("FROM Prenotazione pr ");
			int totale = q1.list().size();
			percentuale = (perTipologia == 0) ? 0 : (totale / perTipologia);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return percentuale;

	}

	public static double getPrenotazioniNumberPerTipologiaNelMese(
			Integer tipologiaId, int mese) {
		begin();
		int perTipologia = 0;
		try {
			Query q = getSession().createQuery(
					"FROM Prenotazione pr where tipologiavisita = '"
							+ tipologiaId + "' and MONTH(pr.dataOra) = '"
							+ mese + "'");
			System.out.println(q.getQueryString());
			perTipologia = q.list().size();
			// Query q1 = getSession()
			// .createQuery(
			// "FROM Visita vs join Prenotazione pr on vs.fk_prenotazione = pr.idPrenotazione ");
			// int totale = q1.list().size();
			// percentuale = (perTipologia == 0) ? 0 : (totale / perTipologia);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return perTipologia;

	}

	public static double getVisiteNumberPerTipologia(Integer tipologiaId) {
		begin();
		int percentuale = 0;
		try {
			Query q = getSession()
					.createQuery(
							"FROM Visita vs join Prenotazione pr on vs.fk_prenotazione = pr.idPrenotazione where pr.tipologiavisita = '"
									+ tipologiaId + "'");
			int perTipologia = q.list().size();
			Query q1 = getSession()
					.createQuery(
							"FROM Visita vs join Prenotazione pr on vs.fk_prenotazione = pr.idPrenotazione ");
			int totale = q1.list().size();
			percentuale = (perTipologia == 0) ? 0 : (totale / perTipologia);
			System.out.println("percentuale:"  + percentuale);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return percentuale;

	}

	public static List<Prenotazione> getPrenotazioni(int anno) {
		begin();
		try {
			Query q = getSession().createQuery(
					"FROM Prenotazione pr where YEAR(pr.dataOra) = " + anno);
			List<Prenotazione> pr = (List<Prenotazione>) q.list();
			commit();
			System.out.println("fatto:" + pr.size());
			return pr;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}
	
	public static ArrayList<Prenotazione> getPrenotazioniGiorno(int anno, int mese, int giorno) {
		begin();
		try {
			Query q = getSession().createQuery(
					"FROM Prenotazione pr where YEAR(pr.dataOra) = " + anno +" AND MONTH(pr.dataOra)= " + mese + " AND DAY(pr.dataOra)= "+ giorno);
			ArrayList<Prenotazione> pr = (ArrayList<Prenotazione>) q.list();
			commit();
			//System.out.println("numero prenotazioni per il giorno selezionato:" + pr.size());
			return pr;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}
	
	public static ArrayList<Object> getVisite() {
		begin();
		Query q = getSession().createQuery(
				"FROM Visita v ORDER BY v.dataOraInizio");
		ArrayList<Object> vis = (ArrayList<Object>) q.list();
		commit();
		return vis;

	}
	
	public static ArrayList<Object> getPrenotazioniObject() {
		begin();
		Query q = getSession().createQuery(
				"FROM Prenotazione pr ORDER BY pr.paziente.cognome");
		ArrayList<Object> pr = (ArrayList<Object>) q.list();
		commit();
		return pr;

	}
	
	public static Visita getVisitaByID(int id) {
		begin();
		Query q = getSession().createQuery(
				"FROM Visita v WHERE v.idVisita=" + id);
		Visita visita = new Visita();
		visita = (Visita) q.uniqueResult();
		return visita;
	}
	
	public static void registraTipologiaVisita(String tipologia, double costo) {
		getSession();
		begin();
		Tipologiavisita tv = new Tipologiavisita();
		tv.setTipologia(tipologia);
		tv.setCostoVisita(costo);
		getSession().saveOrUpdate(tv);
		commit();
		close();
	}
	
	public static Tipologiavisita getTipVisitaByID(int id) {
		begin();
		Query q = getSession().createQuery(
				"FROM Tipologiavisita tv WHERE tv.idTipologiaVisita=" + id);
		Tipologiavisita tv = new Tipologiavisita();
		tv = (Tipologiavisita) q.uniqueResult();
		return tv;
	}
	
	public static void cancellaVisita(TableItem rigaTable) {
		begin();
		Criteria criteria = getSession().createCriteria(hibernate.Visita.class);
		int id = Integer.parseInt(rigaTable.getText(0));
		criteria.add( Restrictions.eq("idVisita", id));
		List<Visita> visit = (List<Visita>)criteria.list();
		commit();
		begin();
		getSession().delete(visit.get(0));
		commit();
		close();
	}
	
	
	public void cancellaPrenotazione(TableItem rigaTable) {
		begin();
		Criteria criteria = getSession().createCriteria(hibernate.Prenotazione.class);
		int id = Integer.parseInt(rigaTable.getText(0));
		criteria.add( Restrictions.eq("idPrenotazione", id));
		List<Prenotazione> prenot = (List<Prenotazione>)criteria.list();
		commit();
		begin();
		getSession().delete(prenot.get(0));
		commit();
		close();
	}
	
	public static void setPagamentoVisita(Visita vis) {
		getSession();
		begin();
		vis.setStatoPagamento("completato");
		getSession().saveOrUpdate(vis);
		commit();
		close();
	}
}
