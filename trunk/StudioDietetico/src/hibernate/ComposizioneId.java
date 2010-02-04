package hibernate;

// Generated 4-feb-2010 12.32.12 by Hibernate Tools 3.2.4.GA

/**
 * ComposizioneId generated by hbm2java
 */
public class ComposizioneId implements java.io.Serializable {

	private int idRicetta;
	private int idIngrediente;

	public ComposizioneId() {
	}

	public ComposizioneId(int idRicetta, int idIngrediente) {
		this.idRicetta = idRicetta;
		this.idIngrediente = idIngrediente;
	}

	public int getIdRicetta() {
		return this.idRicetta;
	}

	public void setIdRicetta(int idRicetta) {
		this.idRicetta = idRicetta;
	}

	public int getIdIngrediente() {
		return this.idIngrediente;
	}

	public void setIdIngrediente(int idIngrediente) {
		this.idIngrediente = idIngrediente;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof ComposizioneId))
			return false;
		ComposizioneId castOther = (ComposizioneId) other;

		return (this.getIdRicetta() == castOther.getIdRicetta())
				&& (this.getIdIngrediente() == castOther.getIdIngrediente());
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + this.getIdRicetta();
		result = 37 * result + this.getIdIngrediente();
		return result;
	}

}
