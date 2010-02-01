package command;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;

import hibernate.Attivitafisica;
import hibernate.Intervento;
import hibernate.InterventoId;
import hibernate.Intolleranzaallergia;
import hibernate.Paziente;
import hibernate.Prenotazione;
import hibernate.Tipologiaintervento;

public class AnamnesiDAO extends BaseDAO{
	public AnamnesiDAO(){}
	
	public void registraTipoIntervento(String nome, String descrizione, String localizzazione){
		getSession();
		begin();
		Tipologiaintervento interv = new Tipologiaintervento();
		interv.setNome(nome);
		interv.setDescrizione(descrizione);
		interv.setLocalizzazione(localizzazione);
		getSession().saveOrUpdate(interv);
		commit();
		close();
	}
	
	public ArrayList<Tipologiaintervento> getInterventi(){
		getSession();
		begin();
		Query q = getSession().createQuery("FROM Tipologiaintervento interv ORDER BY interv.nome");
		
		ArrayList<Tipologiaintervento> interventi = (ArrayList<Tipologiaintervento>)q.list();
		commit();
		return interventi;
	}
	
	public void registraIntervento(Paziente paziente, Tipologiaintervento tipologiaintervento, Date data, int numeroInt){
		getSession();
		begin();
		Intervento interv = new Intervento();
		InterventoId idInt = new InterventoId();
		idInt.setIdPaziente(paziente.getIdPaziente());
		idInt.setIdTipologiaIntervento(tipologiaintervento.getIdTipologiaIntervento());
		interv.setId(idInt);
		interv.setPaziente(paziente);
		interv.setTipologiaintervento(tipologiaintervento);
		interv.setData(data);
		interv.setNumero(numeroInt);
		
		getSession().saveOrUpdate(interv);
		commit();
		close();
	}
	
	public ArrayList<Intervento> getInterventiPaz(){
		getSession();
		begin();
		Query q = getSession().createQuery("FROM Intervento interv");
		
		ArrayList<Intervento> interventiPaz = (ArrayList<Intervento>)q.list();
		commit();
		return interventiPaz;
	}
	
	//update studiodietetico.intervento set numero=5 where idPaziente=4 and idTipologiaIntervento=2;
	public void aggiornaNumero(Paziente paziente, Tipologiaintervento tipointervento, Date data, int numeroInt) {
		getSession();
		begin();
		Intervento interv = new Intervento();
		Query q = getSession().createQuery("FROM Intervento interv WHERE idPaziente="+paziente.getIdPaziente()+" and idTipologiaIntervento="+tipointervento.getIdTipologiaIntervento());
		interv = (Intervento) q.uniqueResult();
		interv.setData(data);
		interv.setNumero(numeroInt);
		getSession().saveOrUpdate(interv);
		/*InterventoId idInt = new InterventoId();
		idInt.setIdPaziente(paziente.getIdPaziente());
		idInt.setIdTipologiaIntervento(tipointervento.getIdTipologiaIntervento());
		interv.setId(idInt);
		interv.setPaziente(paziente);
		interv.setTipologiaintervento(tipointervento);
		interv.setData(data);
		interv.setNumero(numeroInt);*/
		//Query q = getSession().createQuery("UPDATE Intervento SET numero="+numeroInt+", data="+data+" WHERE idPaziente="+paziente.getIdPaziente()+" and idTipologiaIntervento="+tipointervento.getIdTipologiaIntervento());
		//q.executeUpdate();
		//getSession().update(interv);
		commit();
		close();
	}
	
	public void registraAllergie(String flagAll,String sostanza, String alimPrinc, 
			String derivati, String grado, String effCollaterali, Paziente paziente){
		getSession();
		begin();
		Intolleranzaallergia all = new Intolleranzaallergia();
		all.setFlagIntAll(flagAll);
		all.setSostanza(sostanza);
		all.setAlimentoPrincipale(alimPrinc);
		all.setDerivati(derivati);
		all.setGrado(grado);
		all.setEffettiCollaterali(effCollaterali);
		all.setPaziente(paziente);
		getSession().saveOrUpdate(all);
		commit();
		close();
	}
	
	public void registraSport(String nome, String descrizione, String durata, int freqSett, Paziente paziente){
		getSession();
		begin();
		Attivitafisica af = new Attivitafisica();
		af.setNome(nome);
		af.setDescrizione(descrizione);
		af.setDurata(durata);
		af.setFrequenzaSettimanale(freqSett);
		af.setPaziente(paziente);
		getSession().saveOrUpdate(af);
		commit();
		close();
	}
	
	public static ArrayList<Object> getSportPazPerLista(Paziente paz) {
		getSession();
		begin();
		Query q = getSession().createQuery("FROM Attivitafisica af WHERE paziente="+paz/*+" ORDER BY af.nome"*/);
		ArrayList<Object> sport = (ArrayList<Object>)q.list();
		return sport;

	}
	
	public ArrayList<Attivitafisica> getSport(){
		getSession();
		begin();
		Query q = getSession().createQuery("FROM Attivitafisica att ORDER BY att.nome");
		
		ArrayList<Attivitafisica> sport = (ArrayList<Attivitafisica>)q.list();
		commit();
		return sport;
	}
	
}
