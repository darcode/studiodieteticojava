package statistics;

import hibernate.Dieta;
import hibernate.Fattura;
import hibernate.Intervento;
import hibernate.Medico;
import hibernate.Paziente;
import hibernate.Turno;
import hibernate.Visita;

import java.util.HashMap;
/**
 * classe di utilità per la sezione delle statistiche
 * @author Illiceto_A
 *
 */
public class StatisticheUtils {

	public StatisticheUtils() {
	}

	public static HashMap<String, Object> getEntitaSelezionabili(){
		HashMap<String, Object> classi = new HashMap<String, Object>();
		classi.put("MEDICO", new Medico());
		classi.put("PAZIENTE", new Paziente());
		classi.put("FATTURA", new Fattura());
		classi.put("DIETA", new Dieta());
		classi.put("INTERVENTO", new Intervento());
		classi.put("TURNO", new Turno());
		classi.put("VISITA", new Visita());
		return classi;
	}
	
	
}
