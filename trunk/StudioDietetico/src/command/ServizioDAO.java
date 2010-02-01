package command;

import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import org.hibernate.Query;


public class ServizioDAO extends BaseDAO {	
	
//	public ServizioDAO(){}
	
//	public void salvaServizio(Servizio servizio, String pathApp) {
//	
//		close();
//		begin();
//	
//		Integer id_servizio = null;
//		if (servizio.getId()==null) {
//			id_servizio = (Integer) getSession().save(servizio);
//		} else {
//			getSession().saveOrUpdate(servizio);
//			id_servizio = servizio.getId();
//		}				
//		servizio.setId(id_servizio);	
//	
//		
//		String path = pathApp + File.separator +  id_servizio,
//				dirPath = null,
//				relativePath = null;
//	
//		boolean success = (new File(path)).mkdir();
//		File file = null;
//	
//		if (success) {
//			for (Iterator<AllegatoServizio> iter = servizio.getAllegatoServizios().iterator() ; iter.hasNext() ; ) {
//				AllegatoServizio allegato = iter.next();
//				allegato.getAllegato().setPath(path + File.separator + allegato.getAllegato().getFile().getFileName());
//				FileSystemUtils.scriviAllegato(allegato.getAllegato().getFile(), path);
//				
//				Allegato current_allegato = new Allegato();
//				current_allegato.setPath(id_servizio + File.separator + allegato.getAllegato().getFile().getFileName());
//				current_allegato.setContenuto(allegato.getAllegato().getFile().getFileName());
//				Integer id_current_allegato = (Integer)getSession().save(current_allegato);				
//				AllegatoServizioId id = new AllegatoServizioId();
//				id.setIdAllegato(id_current_allegato);
//				id.setIdServizio(id_servizio);				
//				allegato.setAllegato(current_allegato);
//				allegato.setServizio(servizio);
//				allegato.setId(id);
//				getSession().save(allegato);				
//			}
//		}
//		
//		Iterator<OutputServizio> outputs = servizio.getOutputServizios().iterator();
//		while(outputs.hasNext()){
//			OutputServizio o = outputs.next();
//			o.setServizio(servizio);
//			getSession().save(o);
//		}	
//		Iterator<InputServizio> inputs = servizio.getInputServizios().iterator();
//		while(inputs.hasNext()){
//			InputServizio o = inputs.next();
//			o.setServizio(servizio);
//			getSession().save(o);
//		}
//		Iterator<ProcessoServizio> processi = servizio.getProcessoServizios().iterator();
//		while(processi.hasNext()){
//			ProcessoServizio p = processi.next();
//			p.setServizio(servizio);
//			getSession().save(p);
//		}		
//		Iterator<AccordoIndustriale> accordi = servizio.getAccordoIndustriales().iterator();
//		while(accordi.hasNext()){
//			AccordoIndustriale a = accordi.next();
//			a.setServizio(servizio);
//			getSession().save(a);
//		}		
//		Iterator<CustomerSatisfactionServizio> customers = servizio.getCustomerSatisfactionServizios().iterator();
//		while(customers.hasNext()) {
//			CustomerSatisfactionServizio current = customers.next();
//			current.setServizio(servizio);
//			getSession().save(current);
//		}
//		Iterator<FiguraCoinvolta> figure = servizio.getFiguraCoinvoltas().iterator();
//		while(figure.hasNext()) {
//			FiguraCoinvolta current = figure.next();
//			current.setServizio(servizio);
//			Integer id_current_FiguraCoinvolta = (Integer)getSession().save(current);
//			dirPath = path + File.separator + "FiguraCoinvolta";
//			relativePath = id_servizio + File.separator + "FiguraCoinvolta";
//			file = new File(dirPath);
//			success = true;
//			if (! file.exists())
//				success = file.mkdir();
//			if (success) {
////				String pathFigura = pathApp + File.separator + "WEB-INF" + File.separator + "Allegati" 
////				+ File.separator + id_servizio + File.separator + "FiguraCoinvolta";
//				for (Iterator<AllegatoFiguraCoinvolta> iter = current.getAllegatoFiguraCoinvoltas().iterator() ; iter.hasNext() ; ) {
//					AllegatoFiguraCoinvolta allegato = iter.next();
//					if (allegato != null) {
//						l.info("Allegato recuperato non nullo : " + allegato);
//						allegato.getAllegato().setPath(dirPath + File.separator + allegato.getAllegato().getFile().getFileName());
//						FileSystemUtils.scriviAllegato(allegato.getAllegato().getFile(), dirPath);
//						
//						Allegato current_allegato = new Allegato();
//						current_allegato.setPath(relativePath + File.separator + allegato.getAllegato().getFile().getFileName());
//						current_allegato.setContenuto(allegato.getAllegato().getFile().getFileName());
//						Integer id_current_allegato = (Integer)getSession().save(current_allegato);	
//						
//						AllegatoFiguraCoinvoltaId id = new AllegatoFiguraCoinvoltaId();
//						id.setIdAllegato(id_current_allegato);
//						id.setIdFiguraCoinvolta(id_current_FiguraCoinvolta);				
//						allegato.setAllegato(current_allegato);
//						allegato.setFiguraCoinvolta(current);
//						allegato.setId(id);
//						getSession().save(allegato);
//					} else
//						l.info("Allegato recuperato nullo...");
//				}
//			}
//		}		
//		Iterator<ApparecchiStrumenti> apparecchi = servizio.getApparecchiStrumentis().iterator();
//		while(apparecchi.hasNext()){
//			ApparecchiStrumenti current = apparecchi.next();
//			current.setServizio(servizio);
//			getSession().save(current);
//		}		
//		Iterator<Certificazione> certificazioni = servizio.getCertificaziones().iterator();
//		while(certificazioni.hasNext()){
//			Certificazione current = certificazioni.next();
//			current.setServizio(servizio);
//			Integer id_current_Certificazione = (Integer)getSession().save(current);
//			dirPath = path + File.separator + "Certificazione";
//			relativePath = id_servizio + File.separator + "Certificazione";
//			file = new File(dirPath);
//			success = true;
//			if (! file.exists())
//				success = file.mkdir();
//			if (success) {
////				String pathCertificazione = pathApp + File.separator + "WEB-INF" + File.separator + "Allegati" 
////				+ File.separator + id_servizio + File.separator + "Certificazione";
//				for (Iterator<AllegatoCertificazione> iter = current.getAllegatoCertificaziones().iterator() ; iter.hasNext() ; ) {
//					AllegatoCertificazione allegato = iter.next();
//					if (allegato != null) {
//						l.info("Allegato recuperato non nullo : " + allegato);
//						allegato.getAllegato().setPath(dirPath + File.separator + allegato.getAllegato().getFile().getFileName());
//						FileSystemUtils.scriviAllegato(allegato.getAllegato().getFile(), dirPath);
//	
//						
//						Allegato current_allegato = new Allegato();
//						current_allegato.setPath(relativePath + File.separator + allegato.getAllegato().getFile().getFileName());
//						current_allegato.setContenuto(allegato.getAllegato().getFile().getFileName());
//						Integer id_current_allegato = (Integer)getSession().save(current_allegato);	
//						
//						AllegatoCertificazioneId id = new AllegatoCertificazioneId();
//						id.setIdAllegato(id_current_allegato);
//						id.setIdCertificazione(id_current_Certificazione);
//						
//						allegato.setAllegato(current_allegato);
//						allegato.setCertificazione(current);
//						allegato.setId(id);
//						getSession().save(allegato);
//					} else
//						l.info("Allegato recuperato nullo...");
//				}
//			}
//		}		
//		Iterator<RiferimentoUtile> riferimenti = servizio.getRiferimentoUtiles().iterator();
//		while(riferimenti.hasNext()){
//			RiferimentoUtile current = riferimenti.next();
//			current.setServizio(servizio);
//			getSession().save(current);
//		}
//		
//		Iterator<SpinOff> spinoffs = servizio.getSpinOffs().iterator();
//		while(spinoffs.hasNext()){
//			SpinOff current = spinoffs.next();
//			current.setServizio(servizio);
//			getSession().save(current);
//		}
//		
////		if (servizio.getTipologiaServizio()!= null) {
////			TipologiaServizio tip = servizio.getTipologiaServizio();
////			tip.setIdServizio(servizio.getId());
////			tip.setServizio(servizio);
////			getSession().save(tip);
////		}
//
//		if (servizio.getMercatoServizio() != null) {
//			MercatoServizio mercato = servizio.getMercatoServizio();
//			mercato.setIdServizio(servizio.getId());
//			mercato.setServizio(servizio);
//			Integer id_mercato = (Integer)getSession().save(mercato);
//			dirPath = path + File.separator + "MercatoServizio";
//			relativePath = id_servizio + File.separator + "MercatoServizio";
//			file = new File(dirPath);
//			if (file.mkdir()) {
////				String pathMercato = pathApp + File.separator + "WEB-INF" + File.separator + "Allegati"	+ File.separator + id_servizio + File.separator + "MercatoServizio";
//				for (Iterator<AllegatoMercatoServizio> iter = mercato.getAllegatoMercatoServizios().iterator() ; iter.hasNext() ; ) {
//					AllegatoMercatoServizio allegato = iter.next();
//					if (allegato != null) {
//	
//						allegato.getAllegato().setPath(dirPath + File.separator + allegato.getAllegato().getFile().getFileName());
//						FileSystemUtils.scriviAllegato(allegato.getAllegato().getFile(), dirPath);
//						
//						
//						Allegato current_allegato = new Allegato();
//						current_allegato.setPath(relativePath + File.separator + allegato.getAllegato().getFile().getFileName());
//						current_allegato.setContenuto(allegato.getAllegato().getFile().getFileName());
//						Integer id_current_allegato = (Integer)getSession().save(current_allegato);	
//						
//						AllegatoMercatoServizioId id = new AllegatoMercatoServizioId();
//						id.setIdAllegato(id_current_allegato);
//						id.setIdMercatoServizio(id_mercato);
//						
//						allegato.setAllegato(current_allegato);
//						allegato.setMercatoServizio(mercato);
//						allegato.setId(id);
//						getSession().save(allegato);
//					} else
//						l.info("Allegato recuperato nullo...");
//				}
//			}
//		}
//		
//		Iterator<PrezziTariffe> prezzi = servizio.getPrezziTariffes().iterator();
//		while(prezzi.hasNext()){
//			PrezziTariffe prezzo = prezzi.next();
//			prezzo.setServizio(servizio);
//			getSession().saveOrUpdate(prezzo);
//		}
//				
//		commit();
//	}
//	
//	public List<Servizio> searchServizio(String nome, String dataAttivazione, String descrizione, String operatore, String materiali_impiegati) throws SearchException{
//		
//		l.info("Ricerco servizi con i seguenti parametri: ");
//		begin();
//		l.info(Utils.getStringFromBoundle(MessagesConstants.Open_Database, null));
//		
//		String query = "";
//		
//		
//		if (materiali_impiegati == null || materiali_impiegati.equals("")) {
//			query="select s from Servizio as s where";
//		} else {
////			select s from Servizio as s  join s.apparecchiStrumentis as a 
////			where a.materialiImpiegati like '%asd%'
//			query="select s from Servizio as s  join s.apparecchiStrumentis as a where";
//		}
//		
//		
//		if ((nome != null && !nome.equals("")) || 
//			(dataAttivazione!= null) || 
//			(descrizione != null && !descrizione.equals("")) ||
//			(materiali_impiegati != null && !materiali_impiegati.equals(""))) {
//			
//			boolean clauseAdded = false;
//			
//			if (nome != null && !nome.equals("")) {
//				l.info("nome per la ricerca: " + nome);
//				nome = nome.replaceAll("'", "");
//				nome = nome.replaceAll("\"", "");
//		    	nome = nome.replaceAll("%", "");
//		    	nome = nome.replaceAll("&", "");
//		    	nome = nome.replaceAll("<", "");
//		    	nome = nome.replaceAll(">", "");
//		    	l.info("nome per la ricerca dopo il clean: " + nome);		    	
//				if(clauseAdded){
//					query = query.concat(" AND");
//				}			
//				query = query.concat(" (");								
//				StringTokenizer st = new StringTokenizer(nome);
//			    while (st.hasMoreTokens()) {
//			    	String currentToken = st.nextToken();			    				    	
//			    	query = query.concat(" s.nome like '%"+currentToken+"%'");
//			    	if (st.hasMoreTokens()) {
//			    		query = query.concat(" OR");
//					}
//			    }			    
//			    query = query.concat(" )");
//				clauseAdded = true;
//			}
//			
//			if (dataAttivazione != null) {
//				if(clauseAdded){
//					query = query.concat(" AND");
//				}
//				query = query.concat(" s.dataAttivazione "+operatore+" '"+dataAttivazione+"'");
//				clauseAdded = true;
//			}
//
//			if (descrizione != null && !descrizione.equals("")) {
//				l.info("descrizione per la ricerca : " + descrizione);
//				descrizione = descrizione.replaceAll("'", "");
//				descrizione = descrizione.replaceAll("\"", "");
//				descrizione = descrizione.replaceAll("%", "");
//		    	descrizione = descrizione.replaceAll("&", "");
//		    	descrizione = descrizione.replaceAll("<", "");
//		    	descrizione = descrizione.replaceAll(">", "");
//		    	l.info("descrizione per la ricerca dopo il clean: " + descrizione);		    	
//				if(clauseAdded){
//					query = query.concat(" AND");
//				}
//				query = query.concat(" (");		
//				StringTokenizer st = new StringTokenizer(descrizione);
//			    while (st.hasMoreTokens()) {
//			    	String currentToken = st.nextToken();			    				    	
//			    	query = query.concat(" s.descrizione like '%"+currentToken+"%'");
//			    	if (st.hasMoreTokens()) {
//			    		query = query.concat(" OR");
//					}
//			    }
//			    query = query.concat(" )");
//				clauseAdded = true;
//			}					
//			
//			if (materiali_impiegati != null && !materiali_impiegati.equals("")) {
//				l.info("materiali impiegati per la ricerca : " + materiali_impiegati);
//				materiali_impiegati = materiali_impiegati.replaceAll("'", "");
//				materiali_impiegati = materiali_impiegati.replaceAll("\"", "");
//				materiali_impiegati = materiali_impiegati.replaceAll("%", "");
//				materiali_impiegati = materiali_impiegati.replaceAll("&", "");
//				materiali_impiegati = materiali_impiegati.replaceAll("<", "");
//				materiali_impiegati = materiali_impiegati.replaceAll(">", "");
//				l.info("materiali impiegati per la ricerca dopo il clean: " + materiali_impiegati);
//				if(clauseAdded){
//					query = query.concat(" AND");
//				}			
//				query = query.concat(" (");
//				StringTokenizer st = new StringTokenizer(materiali_impiegati);
//			    while (st.hasMoreTokens()) {
//			    	String currentToken = st.nextToken();			    				    	
//			    	query = query.concat(" a.materialiImpiegati like '%"+currentToken+"%'");
//			    	if (st.hasMoreTokens()) {
//			    		query = query.concat(" OR");
//					}
//			    }			    
//			    query = query.concat(" )");
//				clauseAdded = true;
//			}
//		} else {
//			throw new SearchException();		
//		}		
//		Query q = getSession().createQuery(query);
//		List<Servizio> servizi = q.list();
//		commit();	
//		return servizi;
//	}
//	
//	public List<Servizio> searchServiziOwner(String nome_utente){
//		l.info("Ricerco servizi dato l'owner: "+nome_utente);
//		begin();
//		l.info(Utils.getStringFromBoundle(MessagesConstants.Open_Database, null));
//		
//		String query = "from Servizio where owner ='"+nome_utente+"'";		
//				
//		Query q = getSession().createQuery(query);
//		List<Servizio> servizi = q.list();
//		commit();	
//		return servizi;
//		
//	}
}
