package hibernate;

// Generated 5-feb-2010 10.52.14 by Hibernate Tools 3.2.4.GA

import java.util.HashSet;
import java.util.Set;

/**
 * Dieta generated by hbm2java
 */
public class Dieta implements java.io.Serializable {

	private Integer idDieta;
	private Specifichedieta specifichedieta;
	private String nome;
	private int durataCiclo;
	private String note;
	private Boolean dietaStandard;
	private Set personalizzazionegiornatas = new HashSet(0);
	private Set prescriziones = new HashSet(0);

	public Dieta() {
	}

	public Dieta(Specifichedieta specifichedieta, String nome, int durataCiclo) {
		this.specifichedieta = specifichedieta;
		this.nome = nome;
		this.durataCiclo = durataCiclo;
	}

	public Dieta(Specifichedieta specifichedieta, String nome, int durataCiclo,
			String note, Boolean dietaStandard, Set personalizzazionegiornatas,
			Set prescriziones) {
		this.specifichedieta = specifichedieta;
		this.nome = nome;
		this.durataCiclo = durataCiclo;
		this.note = note;
		this.dietaStandard = dietaStandard;
		this.personalizzazionegiornatas = personalizzazionegiornatas;
		this.prescriziones = prescriziones;
	}

	public Integer getIdDieta() {
		return this.idDieta;
	}

	public void setIdDieta(Integer idDieta) {
		this.idDieta = idDieta;
	}

	public Specifichedieta getSpecifichedieta() {
		return this.specifichedieta;
	}

	public void setSpecifichedieta(Specifichedieta specifichedieta) {
		this.specifichedieta = specifichedieta;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getDurataCiclo() {
		return this.durataCiclo;
	}

	public void setDurataCiclo(int durataCiclo) {
		this.durataCiclo = durataCiclo;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Boolean getDietaStandard() {
		return this.dietaStandard;
	}

	public void setDietaStandard(Boolean dietaStandard) {
		this.dietaStandard = dietaStandard;
	}

	public Set getPersonalizzazionegiornatas() {
		return this.personalizzazionegiornatas;
	}

	public void setPersonalizzazionegiornatas(Set personalizzazionegiornatas) {
		this.personalizzazionegiornatas = personalizzazionegiornatas;
	}

	public Set getPrescriziones() {
		return this.prescriziones;
	}

	public void setPrescriziones(Set prescriziones) {
		this.prescriziones = prescriziones;
	}

}