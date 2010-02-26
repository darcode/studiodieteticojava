package command;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;

import hibernate.Attivitafisica;
import hibernate.Intervento;
import hibernate.InterventoId;
import hibernate.Intolleranzaallergia;
import hibernate.Paziente;
import hibernate.Tipologiaintervento;

public class AnamnesiDAO extends BaseDAO{
	public AnamnesiDAO(){}
	
	//TIPOLOGIA INTERVENTO
	public void registraTipoIntervento(String nome, String descrizione, String localizzazione){
		getSession();
		begin();
		Tipologiaintervento interv = new Tipologiaintervento();
		interv.setNome(nome);
		interv.setDescrizione(descrizione);
		interv.setLocalizzazione(localizzazione);
		getSession().saveOrUpdate(interv);
		commit();
		//interv.getIdTipologiaIntervento();
		//System.out.println("IDTIPOINT: "+interv.getIdTipologiaIntervento());
		close();
	}
	
	public ArrayList<Object> getListTipoInterventi(){
		getSession();
		begin();
		Query q = getSession().createQuery("FROM Tipologiaintervento interv ORDER BY interv.nome");
		
		ArrayList<Object> interventi = (ArrayList<Object>)q.list();
		commit();
		return interventi;
	}
	
	public Tipologiaintervento getTipoInterventiById(String id){
		getSession();
		begin();
		Query q = getSession().createQuery("FROM Tipologiaintervento interv WHERE idTipologiaIntervento="+id);
		Tipologiaintervento intervento = (Tipologiaintervento)q.uniqueResult();
		commit();
		return intervento;
	}
	
	public void modificaTipoIntervento(Tipologiaintervento tipointervento, String nome, String descrizione, String localizzazione) {
		getSession();
		begin();
		Tipologiaintervento tipoInterv = new Tipologiaintervento();
		Query q = getSession().createQuery("FROM Tipologiaintervento interv WHERE idTipologiaIntervento="+tipointervento.getIdTipologiaIntervento());
		tipoInterv = (Tipologiaintervento) q.uniqueResult();
		tipoInterv.setNome(nome);
		tipoInterv.setDescrizione(descrizione);
		tipoInterv.setLocalizzazione(localizzazione);
		getSession().saveOrUpdate(tipoInterv);
		commit();
		close();
	}
	
	public void cancellaTipoIntervento(Integer idtipoInt) {
		begin();
		Criteria criteria = getSession().createCriteria(hibernate.Tipologiaintervento.class);
		criteria.add( Restrictions.eq("idTipologiaIntervento", idtipoInt));
		List<Tipologiaintervento> interv = (List<Tipologiaintervento>)criteria.list();
		commit();
		begin();
		getSession().delete(interv.get(0));
		commit();
		close();
	}
	
	
	//INTERVENTO
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
	
	public ArrayList<Object> getListInterventi(){
		getSession();
		begin();
		Query q = getSession().createQuery("FROM Intervento interv");
		ArrayList<Object> interventiPaz = (ArrayList<Object>)q.list();
		commit();
		return interventiPaz;
	}
	
	public static ArrayList<Object> getInterventiListByPaziente(Paziente paz) {
		getSession();
		begin();
		Query q = getSession().createQuery("FROM Intervento i WHERE paziente="+paz.getIdPaziente()/*+" ORDER BY af.nome"*/);
		ArrayList<Object> interventi = (ArrayList<Object>)q.list();
		commit();
		return interventi;
	}
	
	public ArrayList<Object> getInterventiListByIdTipoInt(int idTipoInt) {
		getSession();
		begin();
		Query q = getSession().createQuery("FROM Intervento i WHERE tipologiaintervento="+idTipoInt);
		ArrayList<Object> interventi = (ArrayList<Object>)q.list();
		commit();
		return interventi;
	}
	
	public Object getInterventiListByPazIdTipoInt(int idTipoInt, int idPaz) {
		getSession();
		begin();
		Query q = getSession().createQuery("FROM Intervento i WHERE tipologiaintervento="+idTipoInt+" AND paziente="+idPaz);
		//ArrayList<Object> interventi = (ArrayList<Object>)q.list();
		Object interventi = q.uniqueResult();
		commit();
		return interventi;
	}
	
	public void modificaIntervento(Paziente paziente, Tipologiaintervento tipointervento, Date data, int numeroInt) {
		getSession();
		begin();
		Intervento interv = new Intervento();
		Query q = getSession().createQuery("FROM Intervento interv WHERE idPaziente="+paziente.getIdPaziente()+" and idTipologiaIntervento="+tipointervento.getIdTipologiaIntervento());
		interv = (Intervento) q.uniqueResult();
		interv.setData(data);
		interv.setNumero(numeroInt);
		getSession().saveOrUpdate(interv);
		commit();
		close();
	}
	
	
	//ALLERGIE
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
	
	public static ArrayList<Object> getAllergieListByPaziente(Paziente paz) {
		getSession();
		begin();
		Query q = getSession().createQuery("FROM Intolleranzaallergia WHERE paziente="+paz.getIdPaziente());
		ArrayList<Object> sport = (ArrayList<Object>)q.list();
		commit();
		return sport;
	}
	
	public static Intolleranzaallergia getAllergiaById(String id){
		getSession();
		begin();
		Query q = getSession().createQuery("FROM Intolleranzaallergia WHERE idIntolleranzaAllergia="+id);
		Intolleranzaallergia allergia = (Intolleranzaallergia)q.uniqueResult();
		commit();
		return allergia;
	}
	
	public void modificaAllergie(Intolleranzaallergia allergia, String flagAll,
			String sostanza, String alimPrinc, String derivati, String grado, String effCollaterali) {
		getSession();
		begin();
		Intolleranzaallergia allerg = new Intolleranzaallergia();
		Query q = getSession().createQuery("FROM Intolleranzaallergia aller WHERE idIntolleranzaAllergia="+allergia.getIdIntolleranzaAllergia());
		allerg = (Intolleranzaallergia) q.uniqueResult();
		allerg.setFlagIntAll(flagAll);
		allerg.setSostanza(sostanza);
		allerg.setAlimentoPrincipale(alimPrinc);
		allerg.setDerivati(derivati);
		allerg.setGrado(grado);
		allerg.setEffettiCollaterali(effCollaterali);
		//allerg.setPaziente(paziente);
		getSession().saveOrUpdate(allerg);
		commit();
		close();
	}
	
	//ATTIVITA' FISICA
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
	
	public static ArrayList<Object> getSportListByPaziente(Paziente paz) {
		getSession();
		begin();
		Query q = getSession().createQuery("FROM Attivitafisica af WHERE paziente="+paz.getIdPaziente()/*+" ORDER BY af.nome"*/);
		ArrayList<Object> sport = (ArrayList<Object>)q.list();
		commit();
		return sport;
	}
	
	public static Attivitafisica getSportById(String id){
		getSession();
		begin();
		Query q = getSession().createQuery("FROM Attivitafisica WHERE idAttivitaFisica="+id);
		Attivitafisica sport = (Attivitafisica)q.uniqueResult();
		commit();
		return sport;
	}

	public void modificaSport(Attivitafisica sport, String nome, String descrizione, 
			String durata, int frequenzaSett) {
		getSession();
		begin();
		Attivitafisica sportMod = new Attivitafisica();
		Query q = getSession().createQuery("FROM Attivitafisica WHERE idAttivitaFisica="+sport.getIdAttivitaFisica());
		sportMod = (Attivitafisica) q.uniqueResult();
		sportMod.setNome(nome);
		sportMod.setDescrizione(descrizione);
		sportMod.setDurata(durata);
		sportMod.setFrequenzaSettimanale(frequenzaSett);
		
		getSession().saveOrUpdate(sportMod);
		commit();
		close();
	}
	
	/*public ArrayList<Attivitafisica> getSport(){
		getSession();
		begin();
		Query q = getSession().createQuery("FROM Attivitafisica att ORDER BY att.nome");
		
		ArrayList<Attivitafisica> sport = (ArrayList<Attivitafisica>)q.list();
		commit();
		return sport;
	}*/
	
}
