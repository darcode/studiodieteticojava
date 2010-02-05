package hibernate;

// Generated 5-feb-2010 10.52.14 by Hibernate Tools 3.2.4.GA

import java.util.HashSet;
import java.util.Set;

/**
 * Misurazione generated by hbm2java
 */
public class Misurazione implements java.io.Serializable {

	private MisurazioneId id;
	private Parametroantropometrico parametroantropometrico;
	private Paziente paziente;
	private String osservazioni;
	private Set rilevamentos = new HashSet(0);

	public Misurazione() {
	}

	public Misurazione(MisurazioneId id,
			Parametroantropometrico parametroantropometrico, Paziente paziente) {
		this.id = id;
		this.parametroantropometrico = parametroantropometrico;
		this.paziente = paziente;
	}

	public Misurazione(MisurazioneId id,
			Parametroantropometrico parametroantropometrico, Paziente paziente,
			String osservazioni, Set rilevamentos) {
		this.id = id;
		this.parametroantropometrico = parametroantropometrico;
		this.paziente = paziente;
		this.osservazioni = osservazioni;
		this.rilevamentos = rilevamentos;
	}

	public MisurazioneId getId() {
		return this.id;
	}

	public void setId(MisurazioneId id) {
		this.id = id;
	}

	public Parametroantropometrico getParametroantropometrico() {
		return this.parametroantropometrico;
	}

	public void setParametroantropometrico(
			Parametroantropometrico parametroantropometrico) {
		this.parametroantropometrico = parametroantropometrico;
	}

	public Paziente getPaziente() {
		return this.paziente;
	}

	public void setPaziente(Paziente paziente) {
		this.paziente = paziente;
	}

	public String getOsservazioni() {
		return this.osservazioni;
	}

	public void setOsservazioni(String osservazioni) {
		this.osservazioni = osservazioni;
	}

	public Set getRilevamentos() {
		return this.rilevamentos;
	}

	public void setRilevamentos(Set rilevamentos) {
		this.rilevamentos = rilevamentos;
	}

}
