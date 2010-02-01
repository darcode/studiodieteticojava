package service;


public class StrutAlimento {
private String nomeAlimento;
private String tipologia;
private Integer calorie;
private String quantita;
private int idAlimento;

public int getIdAlimento() {
	return idAlimento;
}

public void setIdAlimento(int idAlimento) {
	this.idAlimento = idAlimento;
}

public StrutAlimento(String nomeAlimento, String tipologia, Integer calorie, String quantita, int idAlimento) {
	this.nomeAlimento = nomeAlimento;
	this.tipologia = tipologia;
	this.calorie = calorie;
	this.quantita = quantita;
	this.idAlimento = idAlimento;
}

public StrutAlimento(String nomeAlimento, String tipologia, Integer calorie,  Integer idAlimento) {
	this.nomeAlimento = nomeAlimento;
	this.tipologia = tipologia;
	this.calorie = calorie;
	this.idAlimento = idAlimento;
}

public StrutAlimento() {
	// TODO Auto-generated constructor stub
}

public String getQuantita() {
	return quantita;
}

public void setQuantita(String quantita) {
	this.quantita = quantita;
}

public String getNomeAlimento() {
	return nomeAlimento;
}

public void setNomeAlimento(String nomeAlimento) {
	this.nomeAlimento = nomeAlimento;
}

public String getTipologia() {
	return tipologia;
}

public void setTipologia(String tipologia) {
	this.tipologia = tipologia;
}

public Integer getCalorie() {
	return calorie;
}

public void setCalorie(Integer calorie) {
	this.calorie = calorie;
}


}
