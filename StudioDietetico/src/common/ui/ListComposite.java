package common.ui;

import hibernate.Abitudinialimentari;
import hibernate.Alimento;
import hibernate.Attivitafisica;
import hibernate.Dieta;
import hibernate.Esameclinico;
import hibernate.Farmacoassunto;
import hibernate.Fattura;
import hibernate.Funzione;
import hibernate.Ingrediente;
import hibernate.Intolleranzaallergia;
import hibernate.Malattia;
import hibernate.Medico;
import hibernate.Modalitapastofuori;
import hibernate.Parametroantropometrico;
import hibernate.Parametroesame;
import hibernate.Pasto;
import hibernate.Paziente;
import hibernate.Prenotazione;
import hibernate.Prescrizione;
import hibernate.Ricetta;
import hibernate.Rilevamento;
import hibernate.Risultatoanalisi;
import hibernate.Schemadietetico;
import hibernate.Specifichedieta;
import hibernate.Tipologiabevanda;
import hibernate.Tipologiadietaspeciale;
import hibernate.Tipologiaintervento;
import hibernate.Tipologiavisita;
import hibernate.Turno;
import hibernate.Visita;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import common.GenericBean;

public class ListComposite extends Composite {

	public ListComposite(Composite parent, int style) {
		super(parent, style);
	}

	protected void riempiTabellaEntita(Table table, ArrayList<Object> lista, String classeChiamante) {
		ArrayList<String> colonne = new ArrayList<String>();

		if (lista.isEmpty()) {
			//TODO generare le colonne in base alla classe chiamante 			
			if (classeChiamante.equalsIgnoreCase("VisitaTableView")) {
				colonne.add("dataOraInizio");
				colonne.add("dataOraFine");
				colonne.add("Medico");
				colonne.add("Paziente");
				colonne.add("Fatturata");
			} else if (classeChiamante.equalsIgnoreCase("PazienteTableView")) {
				colonne.add("cognome");
				colonne.add("nome");
				colonne.add("dataNascita");
				colonne.add("sesso");
				colonne.add("indirizzo");
				colonne.add("citta");
			} else if (classeChiamante.equalsIgnoreCase("MedicoTableView")) {
				colonne.add("cognome");
				colonne.add("nome");
				colonne.add("indirizzo");
				colonne.add("citta");
				colonne.add("specializzazione");
				colonne.add("telefono1");
				colonne.add("telefono2");
			} else if (classeChiamante.equalsIgnoreCase("PrenotazioneTableView")) {
				colonne.add("dataOra");
				colonne.add("Paziente");
				colonne.add("Tipologia visita");			
			} else if (classeChiamante.equalsIgnoreCase("DietaTableView")) {
				colonne.add("nome");
				colonne.add("durataCiclo");
				colonne.add("Kcal");
				colonne.add("Indicata");
				colonne.add("Contenuto presente");
				colonne.add("Contenuto assente");			
			} else if (classeChiamante.equalsIgnoreCase("PrescrizioneTableView")) {
				colonne.add("dataInizio");
				colonne.add("numRipetizCiclo");
				colonne.add("Paziente");
				colonne.add("Dieta");			
			} else if (classeChiamante.equalsIgnoreCase("TurniTableView")) {
				colonne.add("Data turno");
				colonne.add("Ora inizio turno");
				colonne.add("Ora fine turno");
				colonne.add("Medico");			
			} else if (classeChiamante.equalsIgnoreCase("FattureTableView")) {
				colonne.add("importo");
				colonne.add("acconto");
				colonne.add("importoSconto");
				colonne.add("note");
				colonne.add("descrizione");
				colonne.add("data");			
			} else if (classeChiamante.equalsIgnoreCase("InterventiTableView")) {
				colonne.add("IdIntervento");
				colonne.add("IdPaziente");
				colonne.add("IdTipologiaIntervento");
				colonne.add("Data");
				colonne.add("Numero");
			} else if (classeChiamante.equalsIgnoreCase("AllergieTableView")) {
				colonne.add("idIntolleranzaAllergia");
				colonne.add("IdPaziente");
				colonne.add("flagIntAll");
				colonne.add("sostanza");
				colonne.add("alimentoPrincipale");
				colonne.add("derivati");
				colonne.add("grado");
				colonne.add("effettiCollaterali");
			} else if (classeChiamante.equalsIgnoreCase("SportTableView")) {
				colonne.add("idAttivitaFisica");
				colonne.add("idPaziente");
				colonne.add("nome");
				colonne.add("descrizione");
				colonne.add("durata");
				colonne.add("frequenzaSettimanale");
			} else if (classeChiamante.equalsIgnoreCase("FarmaciTableView")) {
				colonne.add("idFarmacoAssunto");
				colonne.add("idPaziente");
				colonne.add("nome");
				colonne.add("descrizione");
				colonne.add("dose");
				colonne.add("frequenza");
				colonne.add("principiAttivi");
			} else if (classeChiamante.equalsIgnoreCase("MalattiaTableView")) {
				colonne.add("idMalattia");
				colonne.add("idTipologiadietaspeciale");
				colonne.add("patologia");
				colonne.add("malattiaEreditaria");
				colonne.add("idPaziente");
			}
			
			
				
			for (String item : colonne) {
				TableColumn colonna = new TableColumn(table, SWT.CENTER);
				colonna.setWidth(item.length() * 15);
				colonna.setText(item);
			}
			table.setHeaderVisible(true);

		} else {	
			colonne = GenericBean.getFieldsNamesPerQuery(lista.get(0));
			for (String item : colonne) {
				TableColumn colonna = new TableColumn(table, SWT.CENTER);
//				non cancellare questa riga altrimneti se il campo è troppo lungo non si vede..e sembra vuota la tabella
				colonna.setWidth(item.length() * 18);
				colonna.setText(item);
			}

			table.setHeaderVisible(true);
			for (Object item : lista) {
				TableItem tblItem = new TableItem(table, SWT.NONE);
				Object[] valuesObj = new Object[GenericBean.getFieldsNumber(item)];
				String[] values = new String[GenericBean.getFieldsNumber(item)];
				int i = 0;

				for (String colonna : colonne) {
					valuesObj[i] = GenericBean.getProperty(colonna, item);
					//if a cascata per sostituire l'id hibernate con l'id numerico 
					if  (valuesObj[i] instanceof Abitudinialimentari) {  
						valuesObj[i] = ((Abitudinialimentari)valuesObj[i]).getIdAbitudiniAlimentari();
					}
					else if  (valuesObj[i] instanceof Alimento) {  
						valuesObj[i] = ((Alimento)valuesObj[i]).getIdAlimento();
					}
					else if  (valuesObj[i] instanceof Attivitafisica) {  
						valuesObj[i] = ((Attivitafisica)valuesObj[i]).getIdAttivitaFisica();
					}
					else if  (valuesObj[i] instanceof Dieta) {  
						valuesObj[i] = ((Dieta)valuesObj[i]).getIdDieta();
					}
					else if  (valuesObj[i] instanceof Esameclinico) {  
						valuesObj[i] = ((Esameclinico)valuesObj[i]).getIdEsameClinico();
					}
					else if  (valuesObj[i] instanceof Farmacoassunto) {  
						valuesObj[i] = ((Farmacoassunto)valuesObj[i]).getIdFarmacoAssunto();
					}
					else if  (valuesObj[i] instanceof Fattura) {  
						valuesObj[i] = ((Fattura)valuesObj[i]).getIdFattura();
					}
					else if  (valuesObj[i] instanceof Ingrediente) {  
						valuesObj[i] = ((Ingrediente)valuesObj[i]).getIdIngrediente();
					}
					else if  (valuesObj[i] instanceof Intolleranzaallergia) {  
						valuesObj[i] = ((Intolleranzaallergia)valuesObj[i]).getIdIntolleranzaAllergia();
					}
					else if  (valuesObj[i] instanceof Malattia) {  
						valuesObj[i] = ((Malattia)valuesObj[i]).getIdMalattia();
					}
					else if  (valuesObj[i] instanceof Medico) {  
						valuesObj[i] = ((Medico)valuesObj[i]).getIdMedico();
					}
					else if  (valuesObj[i] instanceof Modalitapastofuori) {  
						valuesObj[i] = ((Modalitapastofuori)valuesObj[i]).getIdModalitaPastoFuori();
					}
					else if  (valuesObj[i] instanceof Parametroantropometrico) {  
						valuesObj[i] = ((Parametroantropometrico)valuesObj[i]).getIdParametroAntropometrico();
					}
					else if  (valuesObj[i] instanceof Parametroesame) {  
						valuesObj[i] = ((Parametroesame)valuesObj[i]).getIdParametroEsame();
					}
					else if  (valuesObj[i] instanceof Pasto) {  
						valuesObj[i] = ((Pasto)valuesObj[i]).getIdPasto();
					}
					else if ( valuesObj[i] instanceof Paziente) { 
						valuesObj[i] = ((Paziente)valuesObj[i]).getIdPaziente();
					}
					else if  (valuesObj[i] instanceof Prenotazione) {  
						valuesObj[i] = ((Prenotazione)valuesObj[i]).getIdPrenotazione();
					}
					else if  (valuesObj[i] instanceof Prescrizione) {  
						valuesObj[i] = ((Prescrizione)valuesObj[i]).getIdPrescrizione();
					}
					else if  (valuesObj[i] instanceof Ricetta) {  
						valuesObj[i] = ((Ricetta)valuesObj[i]).getIdRicetta();
					}
					else if  (valuesObj[i] instanceof Rilevamento) {  
						valuesObj[i] = ((Rilevamento)valuesObj[i]).getIdRilevamento();
					}
					else if  (valuesObj[i] instanceof Risultatoanalisi) {  
						valuesObj[i] = ((Risultatoanalisi)valuesObj[i]).getIdRisultatoAnalisi();
					}
					else if  (valuesObj[i] instanceof Schemadietetico) {  
						valuesObj[i] = ((Schemadietetico)valuesObj[i]).getIdSchemaDietetico();
					}
					else if  (valuesObj[i] instanceof Specifichedieta) {  
						valuesObj[i] = ((Specifichedieta)valuesObj[i]).getIdSpecificheDieta();
					}
					else if  (valuesObj[i] instanceof Tipologiabevanda) {  
						valuesObj[i] = ((Tipologiabevanda)valuesObj[i]).getIdTipologiaBevanda();
					}
					else if  (valuesObj[i] instanceof Tipologiadietaspeciale) {  
						valuesObj[i] = ((Tipologiadietaspeciale)valuesObj[i]).getIdTipologiaDietaSpeciale();
					}
					else if  (valuesObj[i] instanceof Tipologiaintervento) {  
						valuesObj[i] = ((Tipologiaintervento)valuesObj[i]).getIdTipologiaIntervento();
					} 
					else if  (valuesObj[i] instanceof Tipologiavisita) {  
						valuesObj[i] = ((Tipologiavisita)valuesObj[i]).getIdTipologiaVisita();
					}
					else if  (valuesObj[i] instanceof Turno) {  
						valuesObj[i] = ((Turno)valuesObj[i]).getIdTurno();
					}
					else if  (valuesObj[i] instanceof Visita) {  
						valuesObj[i] = ((Visita)valuesObj[i]).getIdVisita();
					}
					i++;
				}

				for (int j = 0; j < valuesObj.length; j++) {
					if (valuesObj[j]!=null)
						values[j]=valuesObj[j].toString();
				}
				tblItem.setText(values);
			}
		}
	}

	protected void riempiTabellaCampi(Table table, GenericBean bean,
			String entita) {
		try {
			boolean esisteInTabella = false;
			TableItem[] items = table.getItems();
			for (TableItem item : items) {
				if (item.getText(0).equals(entita))
					esisteInTabella = true;
			}
			if (!esisteInTabella) {
				ArrayList<String> campi = GenericBean
						.getFieldsNamesPerQuery(bean);
				for (String item : campi) {
					TableItem tblItem = new TableItem(table, SWT.NONE);
					tblItem.setText(new String[] { entita, item });
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}