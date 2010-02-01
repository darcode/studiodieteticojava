package hibernate;

// Generated 14-gen-2010 20.26.42 by Hibernate Tools 3.2.4.GA

import java.util.HashSet;
import java.util.Set;

/**
 * Pasto generated by hbm2java
 */
public class Pasto implements java.io.Serializable {

	private Integer idPasto;
	private String nome;
	private Set costituziones = new HashSet(0);

	public Pasto() {
	}

	public Pasto(String nome) {
		this.nome = nome;
	}

	public Pasto(String nome, Set costituziones) {
		this.nome = nome;
		this.costituziones = costituziones;
	}

	public Integer getIdPasto() {
		return this.idPasto;
	}

	public void setIdPasto(Integer idPasto) {
		this.idPasto = idPasto;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Set getCostituziones() {
		return this.costituziones;
	}

	public void setCostituziones(Set costituziones) {
		this.costituziones = costituziones;
	}

}
