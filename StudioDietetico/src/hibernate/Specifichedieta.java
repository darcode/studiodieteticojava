package hibernate;

// Generated 5-feb-2010 10.52.14 by Hibernate Tools 3.2.4.GA

import java.util.HashSet;
import java.util.Set;

/**
 * Specifichedieta generated by hbm2java
 */
public class Specifichedieta implements java.io.Serializable {

	private Integer idSpecificheDieta;
	private int kilocalorie;
	private String indicata;
	private String contenutoPresente;
	private String contenutoAssente;
	private Set dietas = new HashSet(0);

	public Specifichedieta() {
	}

	public Specifichedieta(int kilocalorie, String indicata) {
		this.kilocalorie = kilocalorie;
		this.indicata = indicata;
	}

	public Specifichedieta(int kilocalorie, String indicata,
			String contenutoPresente, String contenutoAssente, Set dietas) {
		this.kilocalorie = kilocalorie;
		this.indicata = indicata;
		this.contenutoPresente = contenutoPresente;
		this.contenutoAssente = contenutoAssente;
		this.dietas = dietas;
	}

	public Integer getIdSpecificheDieta() {
		return this.idSpecificheDieta;
	}

	public void setIdSpecificheDieta(Integer idSpecificheDieta) {
		this.idSpecificheDieta = idSpecificheDieta;
	}

	public int getKilocalorie() {
		return this.kilocalorie;
	}

	public void setKilocalorie(int kilocalorie) {
		this.kilocalorie = kilocalorie;
	}

	public String getIndicata() {
		return this.indicata;
	}

	public void setIndicata(String indicata) {
		this.indicata = indicata;
	}

	public String getContenutoPresente() {
		return this.contenutoPresente;
	}

	public void setContenutoPresente(String contenutoPresente) {
		this.contenutoPresente = contenutoPresente;
	}

	public String getContenutoAssente() {
		return this.contenutoAssente;
	}

	public void setContenutoAssente(String contenutoAssente) {
		this.contenutoAssente = contenutoAssente;
	}

	public Set getDietas() {
		return this.dietas;
	}

	public void setDietas(Set dietas) {
		this.dietas = dietas;
	}

}
