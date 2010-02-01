package hibernate;

// Generated 14-gen-2010 20.26.42 by Hibernate Tools 3.2.4.GA

import java.util.HashSet;
import java.util.Set;

/**
 * Schemadietetico generated by hbm2java
 */
public class Schemadietetico implements java.io.Serializable {

	private Integer idSchemaDietetico;
	private String descrizione;
	private String note;
	private Set costituziones = new HashSet(0);
	private Set personalizzazionegiornatas = new HashSet(0);

	public Schemadietetico() {
	}

	public Schemadietetico(String descrizione) {
		this.descrizione = descrizione;
	}

	public Schemadietetico(String descrizione, String note, Set costituziones,
			Set personalizzazionegiornatas) {
		this.descrizione = descrizione;
		this.note = note;
		this.costituziones = costituziones;
		this.personalizzazionegiornatas = personalizzazionegiornatas;
	}

	public Integer getIdSchemaDietetico() {
		return this.idSchemaDietetico;
	}

	public void setIdSchemaDietetico(Integer idSchemaDietetico) {
		this.idSchemaDietetico = idSchemaDietetico;
	}

	public String getDescrizione() {
		return this.descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Set getCostituziones() {
		return this.costituziones;
	}

	public void setCostituziones(Set costituziones) {
		this.costituziones = costituziones;
	}

	public Set getPersonalizzazionegiornatas() {
		return this.personalizzazionegiornatas;
	}

	public void setPersonalizzazionegiornatas(Set personalizzazionegiornatas) {
		this.personalizzazionegiornatas = personalizzazionegiornatas;
	}

}
