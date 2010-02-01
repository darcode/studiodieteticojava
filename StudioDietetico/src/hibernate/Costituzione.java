package hibernate;

// Generated 14-gen-2010 20.26.42 by Hibernate Tools 3.2.4.GA

/**
 * Costituzione generated by hbm2java
 */
public class Costituzione implements java.io.Serializable {

	private CostituzioneId id;
	private Pasto pasto;
	private Alimento alimento;
	private Schemadietetico schemadietetico;
	private String quantita;

	public Costituzione() {
	}

	public Costituzione(CostituzioneId id, Pasto pasto, Alimento alimento,
			Schemadietetico schemadietetico, String quantita) {
		this.id = id;
		this.pasto = pasto;
		this.alimento = alimento;
		this.schemadietetico = schemadietetico;
		this.quantita = quantita;
	}

	public CostituzioneId getId() {
		return this.id;
	}

	public void setId(CostituzioneId id) {
		this.id = id;
	}

	public Pasto getPasto() {
		return this.pasto;
	}

	public void setPasto(Pasto pasto) {
		this.pasto = pasto;
	}

	public Alimento getAlimento() {
		return this.alimento;
	}

	public void setAlimento(Alimento alimento) {
		this.alimento = alimento;
	}

	public Schemadietetico getSchemadietetico() {
		return this.schemadietetico;
	}

	public void setSchemadietetico(Schemadietetico schemadietetico) {
		this.schemadietetico = schemadietetico;
	}

	public String getQuantita() {
		return this.quantita;
	}

	public void setQuantita(String quantita) {
		this.quantita = quantita;
	}

}
