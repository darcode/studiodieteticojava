package hibernate;

// Generated 5-feb-2010 10.52.14 by Hibernate Tools 3.2.4.GA

import java.util.HashSet;
import java.util.Set;

/**
 * Referto generated by hbm2java
 */
public class Referto implements java.io.Serializable {

	private RefertoId id;
	private Parametroesame parametroesame;
	private Paziente paziente;
	private String osservazioni;
	private Set risultatoanalisis = new HashSet(0);

	public Referto() {
	}

	public Referto(RefertoId id, Parametroesame parametroesame,
			Paziente paziente) {
		this.id = id;
		this.parametroesame = parametroesame;
		this.paziente = paziente;
	}

	public Referto(RefertoId id, Parametroesame parametroesame,
			Paziente paziente, String osservazioni, Set risultatoanalisis) {
		this.id = id;
		this.parametroesame = parametroesame;
		this.paziente = paziente;
		this.osservazioni = osservazioni;
		this.risultatoanalisis = risultatoanalisis;
	}

	public RefertoId getId() {
		return this.id;
	}

	public void setId(RefertoId id) {
		this.id = id;
	}

	public Parametroesame getParametroesame() {
		return this.parametroesame;
	}

	public void setParametroesame(Parametroesame parametroesame) {
		this.parametroesame = parametroesame;
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

	public Set getRisultatoanalisis() {
		return this.risultatoanalisis;
	}

	public void setRisultatoanalisis(Set risultatoanalisis) {
		this.risultatoanalisis = risultatoanalisis;
	}

}
