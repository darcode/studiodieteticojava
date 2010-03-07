package command;

import hibernate.Alimento;
import hibernate.Composizione;
import hibernate.ComposizioneId;
import hibernate.Costituzione;
import hibernate.CostituzioneId;
import hibernate.Dieta;
import hibernate.Ingrediente;
import hibernate.Intervento;
import hibernate.InterventoId;
import hibernate.Pasto;
import hibernate.Paziente;
import hibernate.Personalizzazionegiornata;
import hibernate.PersonalizzazionegiornataId;
import hibernate.Prescrizione;
import hibernate.Ricetta;
import hibernate.Schemadietetico;
import hibernate.Specifichedieta;
import hibernate.Tipologiaintervento;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.core.commands.Command;
import org.eclipse.swt.widgets.TableItem;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import service.GiornoDieta;
import service.StrutAlimento;
import service.StrutPasto;

public class DietaDAO extends BaseDAO{

	public void insPasto(String nomePasto){
		//close();
		getSession();
		begin();
		Pasto pasto = new Pasto();
		pasto.setNome(nomePasto);
		getSession().saveOrUpdate(pasto);
		commit();
		close();
	}

	public String[] getPasti() {
		begin();
		Query q = getSession().createQuery("FROM Pasto");
		List<Pasto> pasti = (List<Pasto>)q.list();
		List<String> ut = new ArrayList<String>();
		for (Pasto past : pasti) {
			ut.add(past.getNome());
		}
		String[] pastoArray = (String[]) ut.toArray((new String[0]));
		commit();
		close();
		return pastoArray;
	}

	public void insAlimento(String nome, String tipologia, Integer calorie){
		//close();
		getSession();
		begin();
		Alimento alimento = new Alimento();
		alimento.setNome(nome);
		alimento.setTipologia(tipologia);
		alimento.setCalorie(calorie);
		getSession().saveOrUpdate(alimento);
		commit();
		close();
	}

	public String[] getAlimenti() {
		begin();
		Query q = getSession().createQuery("FROM Alimento");
		List<Alimento> alimenti = (List<Alimento>)q.list();
		List<String> ut = new ArrayList<String>();
		for (Alimento ing : alimenti) {
			ut.add(ing.getNome());
		}
		String[] alimentoArray = (String[]) ut.toArray((new String[0]));
		commit();
		close();
		return alimentoArray;

	}


	public ArrayList<StrutAlimento> getAlimentiObj() {
		begin();
		Query q = getSession().createQuery("FROM Alimento");
		List<Alimento> alimenti = (List<Alimento>)q.list();
		ArrayList<StrutAlimento> ut = new ArrayList<StrutAlimento>();
		StrutAlimento item;
		for (Alimento ali : alimenti) {
			item = new StrutAlimento(ali.getNome(), ali.getTipologia(), ali.getCalorie(), ali.getIdAlimento());
			ut.add(item);
		}
		commit();
		close();
		return ut;

	}


	public ArrayList<StrutAlimento> getAlimentiPasto(Pasto pasto, Schemadietetico schema) {

		/*		Criteria criteria = getSession().createCriteria(hibernate.Pasto.class);
		criteria.add( Restrictions.eq("idPasto", pasto.getIdPasto()));
		begin();
		List<Pasto> ris = (List<Pasto>)criteria.list();
		System.out.println(ris.size());
		for (Iterator iter = ris.get(0).getCostituziones().iterator() ; iter.hasNext();) {
			Costituzione pasto2 = (Costituzione) iter.next();
			System.out.println(pasto2.getAlimento().getNome());
		}*/
		begin();
		ArrayList<StrutAlimento> arrRes = new ArrayList<StrutAlimento>();
		Criteria criteria = getSession().createCriteria(hibernate.Costituzione.class);
		criteria.add( Restrictions.eq("pasto", pasto));
		criteria.add( Restrictions.eq("schemadietetico", schema));
		
		List<Costituzione> ris = (List<Costituzione>)criteria.list();
		StrutAlimento stAlimento;
		for (Costituzione item  : ris) {
			stAlimento = new StrutAlimento();
			stAlimento.setCalorie(item.getAlimento().getCalorie());
			stAlimento.setIdAlimento(item.getAlimento().getIdAlimento());
			stAlimento.setNomeAlimento((item.getAlimento().getNome()));
			stAlimento.setQuantita((item.getQuantita()));
			stAlimento.setTipologia(item.getAlimento().getTipologia());

			arrRes.add(stAlimento);
		}

		commit();
		//close();
		//return alimentoArray;
		return arrRes;
	}

	public static void main(String[] args) {
		DietaDAO dieta = new DietaDAO();
		/*ArrayList<GiornoDieta> giorni = dieta.getSchemiDieta(12);
		for (GiornoDieta giorno : giorni) {
			System.out.println(giorno.getGiorno()+" "+giorno.getPasti());
		}*/
		ArrayList<Dieta> giorni = dieta.getDiete();
		for (Dieta giorno : giorni) {
			System.out.println(giorno.getDurataCiclo()+" "+giorno.getIdDieta());
		}
		/*Pasto pasto = new Pasto();
		pasto.setNome("Colazione");
		pasto.setIdPasto(1);
		dieta.getAlimentiPasto(pasto);*/
	}

	public ArrayList<GiornoDieta> getSchemiDieta(int idDieta){
		ArrayList<GiornoDieta> arrGiorni = new ArrayList<GiornoDieta>();
		Criteria criteria = getSession().createCriteria(hibernate.Dieta.class);
		criteria.add( Restrictions.eq("idDieta", idDieta));
		begin();
		Dieta dieta = (Dieta)criteria.list().get(0);
		commit();
		close();
		if (dieta!=null) {
			ArrayList<Schemadietetico> arrSchemi =  new ArrayList<Schemadietetico>(getSchemiDieteticiDieta(dieta));
			ArrayList<Pasto> arrPasti; 
			ArrayList<StrutAlimento> arrAlimenti;
			GiornoDieta giorno = null;
			StrutPasto strutPasto;

			for (int i = 0; i < arrSchemi.size(); i++) {
				if (arrSchemi.size()==7) {
					switch (i) {
					case 0:
						giorno = new GiornoDieta("Lunedì");
						break;
					case 1:
						giorno = new GiornoDieta("Martedì");
						break;
					case 2:
						giorno = new GiornoDieta("Mercoledì");
						break;
					case 3:
						giorno = new GiornoDieta("Giovedì");
						break;
					case 4:
						giorno = new GiornoDieta("Venerdì");
						break;
					case 5:
						giorno = new GiornoDieta("Sabato");
						break;
					case 6:
						giorno = new GiornoDieta("Domenica");
						break;

					}
				}else{
					giorno = new GiornoDieta("Giorno "+i);
				}
				giorno.setDescrizione(arrSchemi.get(i).getDescrizione());
				giorno.setNote(arrSchemi.get(i).getNote());
				arrPasti = new ArrayList<Pasto>(getPastiSchema(arrSchemi.get(i)));

				for (Pasto pasto : arrPasti) {
					arrAlimenti = new ArrayList<StrutAlimento>(getAlimentiPasto(pasto,arrSchemi.get(i)));
					strutPasto = new StrutPasto(pasto.getNome());
					strutPasto.setAlimenti(arrAlimenti);
					giorno.addPasto(strutPasto);
				}
				arrGiorni.add(giorno);
			}
			

			return arrGiorni;
		}else
			return null;
	}
	

	private ArrayList<Pasto> getPastiSchema(Schemadietetico schema) {
		begin();
		ArrayList<Pasto> arrRes = new ArrayList<Pasto>();
		Criteria criteria = getSession().createCriteria(hibernate.Costituzione.class);

		criteria.add( Restrictions.eq("schemadietetico", schema));

		List<Costituzione> risCost = (List<Costituzione>)criteria.list();
		//System.out.println(ris.size());
		commit();
		Set<Integer> elimDup = new HashSet<Integer>();
		for (Costituzione itemCost : risCost) {
			if(!elimDup.contains(itemCost.getPasto().getIdPasto())){
				arrRes.add(itemCost.getPasto());
				elimDup.add(itemCost.getPasto().getIdPasto());
			}
			
		}

		//close();

		return arrRes;



	}


	//Data una dieta restituisce tutti gli schemi dietetici 1 per ciascun giorno
	private ArrayList<Schemadietetico> getSchemiDieteticiDieta(Dieta dieta) {
		begin();
		ArrayList<Schemadietetico> arrRes = new ArrayList<Schemadietetico>();
		Criteria criteria = getSession().createCriteria(hibernate.Personalizzazionegiornata.class);
		criteria.add( Restrictions.eq("dieta", dieta));

		List<Personalizzazionegiornata> risGiornata = (List<Personalizzazionegiornata>)criteria.list();
		//System.out.println(ris.size());
		
		for (Personalizzazionegiornata itemGiornata : risGiornata) {
			arrRes.add(itemGiornata.getSchemadietetico());
		}
		commit();
		//close();

		return arrRes;	
	}
	
	
	public void inserisciPrescrizione(Date dataInizio, int numRipCiclo, String note, Paziente paziente, Dieta dieta){
		Prescrizione prescrizione = new Prescrizione();
		prescrizione.setDataInizio(dataInizio);
		prescrizione.setNumRipetizCiclo(numRipCiclo);
		prescrizione.setNote(note);
		prescrizione.setPaziente(paziente);
		prescrizione.setDieta(dieta);
		begin();
		getSession().saveOrUpdate(prescrizione);
		commit();
		close();
		
	}

	public void inserisciDieta(ArrayList<GiornoDieta> gioniDieta, String nomeDieta, String note, 
			boolean dietaStandard, Specifichedieta specifichedieta) {

		

		Schemadietetico schema;
		Personalizzazionegiornata giornata;
		PersonalizzazionegiornataId giornataId;
		Alimento alimentiSchema;
		Pasto pastiSchema;
		Costituzione costituzione;
		CostituzioneId costituzioneId;

		begin();
		getSession().saveOrUpdate(specifichedieta);
		commit();

		Dieta dieta = new Dieta();
		dieta.setDietaStandard(dietaStandard);
		dieta.setDurataCiclo(gioniDieta.size());
		dieta.setNote(note);
		dieta.setNome(nomeDieta);
		//close();
		begin();
		dieta.setSpecifichedieta(specifichedieta);
		getSession().saveOrUpdate(dieta);
		commit();

		//for (GiornoDieta giorno : gioniDieta) {
		for (int i = 0; i < gioniDieta.size(); i++) {
			schema = new Schemadietetico();	
			giornata = new Personalizzazionegiornata();
			giornataId = new PersonalizzazionegiornataId();
			alimentiSchema = new Alimento();

			//TODO completare
			//giornata.setDieta(dieta);
			schema.setDescrizione(gioniDieta.get(i).getDescrizione());
			schema.setNote(gioniDieta.get(i).getNote());
			//TODO Il giorno del ciclo non può essere int nel caso di settimana
			giornataId.setGiornoCiclo(i+1);
			giornataId.setIdDieta(dieta.getIdDieta());
			begin();
			getSession().saveOrUpdate(schema);
			commit();
			giornataId.setIdSchemaDietetico(schema.getIdSchemaDietetico());
			giornata.setId(giornataId);
			giornata.setSchemadietetico(schema);

			begin();
			getSession().saveOrUpdate(giornata);
			commit();
			ArrayList<StrutPasto> arrPastiSchema = gioniDieta.get(i).getPasti();
			for (StrutPasto pasto : arrPastiSchema) {
				pastiSchema = new Pasto();
				Pasto newPasto = esistePasto(pasto.getNomePasto());
				if(newPasto!=null)
				{
					pastiSchema.setNome(newPasto.getNome());
					pastiSchema.setIdPasto(newPasto.getIdPasto());

				}else {
					pastiSchema.setNome(pasto.getNomePasto());
					begin();
					getSession().saveOrUpdate(pastiSchema);
					commit();
				}

				for (StrutAlimento alimento : pasto.getAlimenti()) {
					alimentiSchema = new Alimento();
					costituzione = new Costituzione();
					costituzioneId = new CostituzioneId();

					alimentiSchema.setNome(alimento.getNomeAlimento());
					alimentiSchema.setTipologia(alimento.getTipologia());
					alimentiSchema.setCalorie(alimento.getCalorie());


					costituzioneId.setIdSchemaDietetico(schema.getIdSchemaDietetico());
					costituzioneId.setIdPasto(pastiSchema.getIdPasto());
					costituzioneId.setIdAlimento(alimento.getIdAlimento());

					costituzione.setId(costituzioneId);
					costituzione.setQuantita(alimento.getQuantita());

					begin();
					getSession().saveOrUpdate(costituzione);
					commit();				


				}
			}

		}
		close();

	}

	private Pasto esistePasto(String nomePasto) {
		begin();
		Criteria criteria = getSession().createCriteria(hibernate.Pasto.class);
		criteria.add( Restrictions.eq("nome", nomePasto));

		List<Pasto> ris = (List<Pasto>)criteria.list();
		commit();
		//close();
		if (ris != null && ris.size()!=0)
			return ris.get(0);
		else	
			return null;
	}

	public Specifichedieta getSpecificheDieta(int idSpecificheDieta){
		Criteria criteria = getSession().createCriteria(hibernate.Specifichedieta.class);
		criteria.add( Restrictions.eq("idSpecificheDieta", idSpecificheDieta));
		begin();
		List<Specifichedieta> ris = (List<Specifichedieta>)criteria.list();
		//System.out.println(ris.size());

		commit();
		//close();

		return ris.get(0);

	}

	public void inserisciSpecificheDieta(String contenutoAssente, String contenutoPresente, String indicata,
			int kilocalorie){
		Specifichedieta specDieta = new Specifichedieta();
		specDieta.setContenutoAssente(contenutoAssente);
		specDieta.setContenutoPresente(contenutoPresente);
		specDieta.setIndicata(indicata);
		specDieta.setKilocalorie(kilocalorie);

		begin();
		getSession().saveOrUpdate(specDieta);
		commit();
		close();

	}
	
	public ArrayList<Dieta> getDiete(){
		Criteria criteria = getSession().createCriteria(hibernate.Dieta.class);
		//criteria.add( Restrictions.eq("idSpecificheDieta", idSpecificheDieta));
		begin();
		ArrayList<Dieta> ris = (ArrayList<Dieta>)criteria.list();
		//System.out.println(ris.size());

		commit();
		//close();

		return ris;
		
	}

	public ArrayList<Specifichedieta> getSpecificheDieta() {
		Criteria criteria = getSession().createCriteria(hibernate.Specifichedieta.class);
		//criteria.add( Restrictions.eq("idSpecificheDieta", idSpecificheDieta));
		begin();
		ArrayList<Specifichedieta> ris = (ArrayList<Specifichedieta>)criteria.list();
		//System.out.println(ris.size());

		commit();
		//close();

		return ris;
	}

	public Alimento getAlimento(int aliId) {
		Criteria criteria = getSession().createCriteria(hibernate.Alimento.class);
		criteria.add( Restrictions.eq("idAlimento", aliId));
		begin();
		ArrayList<Alimento> ris = (ArrayList<Alimento>)criteria.list();
		//System.out.println(ris.size());

		commit();
		close();

		return ris.get(0);
	}
	
	
	public List<Alimento> getAlimentiList() {
		Criteria criteria = getSession().createCriteria(hibernate.Alimento.class);
		
		ProjectionList proList = Projections.projectionList();
	      proList.add(Projections.property("nome"));
	      proList.add(Projections.property("tipologia"));
	      proList.add(Projections.property("calorie"));
	      criteria.setProjection(proList);
		begin();
		List<Alimento> ris = (List<Alimento>)criteria.list();
		//System.out.println(ris.size());

		commit();
		close();

		return ris;
	}

	public Ricetta[] getRicette(Alimento ali) {
		Criteria criteria = getSession().createCriteria(hibernate.Ricetta.class);
		criteria.add( Restrictions.eq("alimento", ali));
		begin();
		ArrayList<Ricetta> ris = (ArrayList<Ricetta>)criteria.list();
		commit();
		close();
		Ricetta[] ricette = new Ricetta[ris.size()];
		//System.out.println(ris.size());
for (int i = 0; i < ris.size(); i++) {
	ricette[i] = ris.get(i);
}
		

		return ricette;
	}

	public Ricetta getRicetta(int idRicetta) {
		Criteria criteria = getSession().createCriteria(hibernate.Ricetta.class);
		criteria.add( Restrictions.eq("idRicetta", idRicetta));
		begin();
		ArrayList<Ricetta> ris = (ArrayList<Ricetta>)criteria.list();
		//System.out.println(ris.size());

		commit();
		

		return ris.get(0);
	}

	public String[] getIngredienti(Ricetta ricetta) {

		Criteria criteria = getSession().createCriteria(hibernate.Composizione.class);
		criteria.add( Restrictions.eq("ricetta", ricetta));
		begin();
		ArrayList<Composizione> ris = (ArrayList<Composizione>)criteria.list();
		commit();
		
		String[] ingredienti = new String[ris.size()];
		for (int i = 0; i < ris.size(); i++) {
			ingredienti[i] = ris.get(i).getQuantita() +"\t"+ ris.get(i).getIngrediente().getNome();
		}



		return ingredienti;
	}
	
	public Ricetta inserisciNuovaRicetta(String nome, String procedimento, Alimento alimento){
		
		begin();
		Ricetta ric = new Ricetta();
		
		ric.setNome(nome);
		ric.setProcedimento(procedimento);
		ric.setAlimento(alimento);
		getSession().saveOrUpdate(ric);
		commit();
		close();
		return ric;
	}
	
	public Ricetta modificaRicetta(int idRicetta, String nome, String procedimento, Alimento alimento){
		
		begin();
		Ricetta ric = getRicetta(idRicetta);
		
		ric.setNome(nome);
		ric.setProcedimento(procedimento);
		ric.setAlimento(alimento);
		getSession().saveOrUpdate(ric);
		commit();
		close();
		return ric;
	}
	
	public void inserisciComposizione(Ingrediente ingrediente, String quantita, Ricetta ricetta){
		
		begin();
		Composizione comp = new Composizione();
		ComposizioneId compId = new ComposizioneId();
		compId.setIdRicetta(ricetta.getIdRicetta());
		compId.setIdIngrediente(ingrediente.getIdIngrediente());
		comp.setId(compId);
		comp.setRicetta(ricetta);
		comp.setIngrediente(ingrediente);
		comp.setQuantita(quantita);
		
		getSession().saveOrUpdate(comp);
		commit();
		close();
	}
	
	public void cancellaComposizione(Ricetta ricetta){
		
		begin();
		Criteria criteria = getSession().createCriteria(hibernate.Composizione.class);
		criteria.add( Restrictions.eq("ricetta", ricetta));
		List<Composizione> comp = (List<Composizione>)criteria.list();
		commit();
		for (Composizione composizione : comp) {
			getSession().delete(composizione);
		}
		commit();
		close();
	}
	
	public void cancellaRicetta(Ricetta ricetta){
		
		begin();
		
		Criteria criteria = getSession().createCriteria(hibernate.Composizione.class);
		criteria.add( Restrictions.eq("ricetta", ricetta));
		List<Composizione> comp = (List<Composizione>)criteria.list();
		commit();
		for (Composizione composizione : comp) {
			getSession().delete(composizione);
		}
		commit();
		begin();
		getSession().delete(ricetta);
		commit();
		
		close();
	}
	
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

	public Ingrediente getIngrediente(String nome) {
		begin();
		Criteria criteria = getSession().createCriteria(hibernate.Ingrediente.class);
		criteria.add( Restrictions.eq("nome", nome));
		List<Ingrediente> ingredienti = (List<Ingrediente>)criteria.list();
		
		commit();
		
		return ingredienti.get(0);
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
		
		return ingredientiArray;

	}
	
	public static ArrayList<Object> getDieteObject(){
		Criteria criteria = getSession().createCriteria(hibernate.Dieta.class);
		begin();
		ArrayList<Object> ris = (ArrayList<Object>)criteria.list();
		commit();
		//close();
		return ris;
	}
	
	public static ArrayList<Object> getPrescrizioniObject(){
		Criteria criteria = getSession().createCriteria(hibernate.Prescrizione.class);
		begin();
		ArrayList<Object> ris = (ArrayList<Object>)criteria.list();
		commit();
		//close();
		return ris;
	}
	
	public static void cancellaDieta(TableItem rigaTable) {
		begin();
		Criteria criteria = getSession().createCriteria(hibernate.Dieta.class);
		int id = Integer.parseInt(rigaTable.getText(0));
		criteria.add( Restrictions.eq("idDieta", id));
		List<Dieta> diete = (List<Dieta>)criteria.list();
		commit();
		begin();
		getSession().delete(diete.get(0));
		commit();
		close();
	}
	
	public static void cancellaPrescrizione(TableItem rigaTable) {
		begin();
		Criteria criteria = getSession().createCriteria(hibernate.Prescrizione.class);
		int id = Integer.parseInt(rigaTable.getText(0));
		criteria.add( Restrictions.eq("idPrescrizione", id));
		List<Prescrizione> pres = (List<Prescrizione>)criteria.list();
		commit();
		begin();
		getSession().delete(pres.get(0));
		commit();
		close();
	}
	
}
