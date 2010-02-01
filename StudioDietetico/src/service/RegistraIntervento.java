package service;

import java.util.Date;

import hibernate.Paziente;
import hibernate.Tipologiaintervento;

public class RegistraIntervento {
	
	private Paziente paziente;
	private Tipologiaintervento tipoIntervento;
	private Date dataIntervento;
	private int numInterventi;
	
	public void RegistraIntervento() {
		paziente = new Paziente();
		tipoIntervento = new Tipologiaintervento();
		dataIntervento = new Date();
		numInterventi = 0;
	}
	public Paziente getPaziente() {
		return paziente;
	}
	public void setPaziente(Paziente paziente) {
		this.paziente = paziente;
	}
	public Tipologiaintervento getTipoIntervento() {
		return tipoIntervento;
	}
	public void setTipoIntervento(Tipologiaintervento tipoIntervento) {
		this.tipoIntervento = tipoIntervento;
	}
	public Date getDataIntervento() {
		return dataIntervento;
	}
	public void setDataIntervento(Date dataIntervento) {
		this.dataIntervento = dataIntervento;
	}
	public int getNumInterventi() {
		return numInterventi;
	}
	public void setNumInterventi(int numInterventi) {
		this.numInterventi = numInterventi;
	}
	

}
