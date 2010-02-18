package common.ui;

import hibernate.Abitudinialimentari;
import hibernate.Alimento;
import hibernate.Attivitafisica;
import hibernate.Dieta;
import hibernate.Esameclinico;
import hibernate.Farmacoassunto;
import hibernate.Fattura;
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

	protected void riempiTabellaEntita(Table table, ArrayList<Object> lista) {
		if (!lista.isEmpty()) {
			ArrayList<String> colonne = GenericBean.getFieldsNamesPerQuery(lista.get(0));
			for (String item : colonne) {
				TableColumn colonna = new TableColumn(table, SWT.CENTER);
				//colonna.setWidth(item.length() * 15);
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
					//TODO if a cascata per sostituire l'id hibernate con l'id numerico 
					if  (valuesObj[i] instanceof Abitudinialimentari) {  
						valuesObj[i] = ((Abitudinialimentari)valuesObj[i]).getIdAbitudiniAlimentari();
					}
					if  (valuesObj[i] instanceof Alimento) {  
						valuesObj[i] = ((Alimento)valuesObj[i]).getIdAlimento();
					}
					if  (valuesObj[i] instanceof Attivitafisica) {  
						valuesObj[i] = ((Attivitafisica)valuesObj[i]).getIdAttivitaFisica();
					}
					if  (valuesObj[i] instanceof Dieta) {  
						valuesObj[i] = ((Dieta)valuesObj[i]).getIdDieta();
					}
					if  (valuesObj[i] instanceof Esameclinico) {  
						valuesObj[i] = ((Esameclinico)valuesObj[i]).getIdEsameClinico();
					}
					if  (valuesObj[i] instanceof Farmacoassunto) {  
						valuesObj[i] = ((Farmacoassunto)valuesObj[i]).getIdFarmacoAssunto();
					}
					if  (valuesObj[i] instanceof Fattura) {  
						valuesObj[i] = ((Fattura)valuesObj[i]).getIdFattura();
					}
					if  (valuesObj[i] instanceof Ingrediente) {  
						valuesObj[i] = ((Ingrediente)valuesObj[i]).getIdIngrediente();
					}
					if  (valuesObj[i] instanceof Intolleranzaallergia) {  
						valuesObj[i] = ((Intolleranzaallergia)valuesObj[i]).getIdIntolleranzaAllergia();
					}
					if  (valuesObj[i] instanceof Malattia) {  
						valuesObj[i] = ((Malattia)valuesObj[i]).getIdMalattia();
					}
					if  (valuesObj[i] instanceof Medico) {  
						valuesObj[i] = ((Medico)valuesObj[i]).getIdMedico();
					}
					if  (valuesObj[i] instanceof Modalitapastofuori) {  
						valuesObj[i] = ((Modalitapastofuori)valuesObj[i]).getIdModalitaPastoFuori();
					}
					if  (valuesObj[i] instanceof Parametroantropometrico) {  
						valuesObj[i] = ((Parametroantropometrico)valuesObj[i]).getIdParametroAntropometrico();
					}
					if  (valuesObj[i] instanceof Parametroesame) {  
						valuesObj[i] = ((Parametroesame)valuesObj[i]).getIdParametroEsame();
					}
					if  (valuesObj[i] instanceof Pasto) {  
						valuesObj[i] = ((Pasto)valuesObj[i]).getIdPasto();
					}
					if ( valuesObj[i] instanceof Paziente) { 
						valuesObj[i] = ((Paziente)valuesObj[i]).getIdPaziente();
					}
					if  (valuesObj[i] instanceof Prenotazione) {  
						valuesObj[i] = ((Prenotazione)valuesObj[i]).getIdPrenotazione();
					}
					if  (valuesObj[i] instanceof Prescrizione) {  
						valuesObj[i] = ((Prescrizione)valuesObj[i]).getIdPrescrizione();
					}
					if  (valuesObj[i] instanceof Ricetta) {  
						valuesObj[i] = ((Ricetta)valuesObj[i]).getIdRicetta();
					}
					if  (valuesObj[i] instanceof Rilevamento) {  
						valuesObj[i] = ((Rilevamento)valuesObj[i]).getIdRilevamento();
					}
					if  (valuesObj[i] instanceof Risultatoanalisi) {  
						valuesObj[i] = ((Risultatoanalisi)valuesObj[i]).getIdRisultatoAnalisi();
					}
					if  (valuesObj[i] instanceof Schemadietetico) {  
						valuesObj[i] = ((Schemadietetico)valuesObj[i]).getIdSchemaDietetico();
					}
					if  (valuesObj[i] instanceof Specifichedieta) {  
						valuesObj[i] = ((Specifichedieta)valuesObj[i]).getIdSpecificheDieta();
					}
					if  (valuesObj[i] instanceof Tipologiabevanda) {  
						valuesObj[i] = ((Tipologiabevanda)valuesObj[i]).getIdTipologiaBevanda();
					}
					if  (valuesObj[i] instanceof Tipologiadietaspeciale) {  
						valuesObj[i] = ((Tipologiadietaspeciale)valuesObj[i]).getIdTipologiaDietaSpeciale();
					}
					if  (valuesObj[i] instanceof Tipologiaintervento) {  
						valuesObj[i] = ((Tipologiaintervento)valuesObj[i]).getIdTipologiaIntervento();
					} 
					if  (valuesObj[i] instanceof Tipologiavisita) {  
						valuesObj[i] = ((Tipologiavisita)valuesObj[i]).getIdTipologiaVisita();
					}
					if  (valuesObj[i] instanceof Turno) {  
						valuesObj[i] = ((Turno)valuesObj[i]).getIdTurno();
					}
					if  (valuesObj[i] instanceof Visita) {  
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