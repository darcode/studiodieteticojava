package hibernate;

// Generated 14-gen-2010 20.26.42 by Hibernate Tools 3.2.4.GA

/**
 * Composizione generated by hbm2java
 */
public class Composizione implements java.io.Serializable {

	private ComposizioneId id;
	private Ricetta ricetta;
	private Ingrediente ingrediente;
	private String quantita;

	public Composizione() {
	}

	public Composizione(ComposizioneId id, Ricetta ricetta,
			Ingrediente ingrediente, String quantita) {
		this.id = id;
		this.ricetta = ricetta;
		this.ingrediente = ingrediente;
		this.quantita = quantita;
	}

	public ComposizioneId getId() {
		return this.id;
	}

	public void setId(ComposizioneId id) {
		this.id = id;
	}

	public Ricetta getRicetta() {
		return this.ricetta;
	}

	public void setRicetta(Ricetta ricetta) {
		this.ricetta = ricetta;
	}

	public Ingrediente getIngrediente() {
		return this.ingrediente;
	}

	public void setIngrediente(Ingrediente ingrediente) {
		this.ingrediente = ingrediente;
	}

	public String getQuantita() {
		return this.quantita;
	}

	public void setQuantita(String quantita) {
		this.quantita = quantita;
	}

}
