package hibernate;

// Generated 14-gen-2010 20.26.42 by Hibernate Tools 3.2.4.GA

import java.util.Date;

import common.GenericBean;

/**
 * Visita generated by hbm2java
 */
public class Visita extends GenericBean implements java.io.Serializable {

	private Integer idVisita;
	private Prenotazione prenotazione;
	private Fattura fattura;
	private Medico medico;
	private Date dataOraInizio;
	private Date dataOraFine;
	private String motivazioni;
	private String statoPagamento;
	private String note;

	public Visita() {
	}

	public Visita(Prenotazione prenotazione, Fattura fattura, Medico medico,
			Date dataOraInizio, Date dataOraFine, String statoPagamento) {
		this.prenotazione = prenotazione;
		this.fattura = fattura;
		this.medico = medico;
		this.dataOraInizio = dataOraInizio;
		this.dataOraFine = dataOraFine;
		this.statoPagamento = statoPagamento;
	}

	public Visita(Prenotazione prenotazione, Fattura fattura, Medico medico,
			Date dataOraInizio, Date dataOraFine, String motivazioni,
			String statoPagamento, String note) {
		this.prenotazione = prenotazione;
		this.fattura = fattura;
		this.medico = medico;
		this.dataOraInizio = dataOraInizio;
		this.dataOraFine = dataOraFine;
		this.motivazioni = motivazioni;
		this.statoPagamento = statoPagamento;
		this.note = note;
	}

	public Integer getIdVisita() {
		return this.idVisita;
	}

	public void setIdVisita(Integer idVisita) {
		this.idVisita = idVisita;
	}

	public Prenotazione getPrenotazione() {
		return this.prenotazione;
	}

	public void setPrenotazione(Prenotazione prenotazione) {
		this.prenotazione = prenotazione;
	}

	public Fattura getFattura() {
		return this.fattura;
	}

	public void setFattura(Fattura fattura) {
		this.fattura = fattura;
	}

	public Medico getMedico() {
		return this.medico;
	}

	public void setMedico(Medico medico) {
		this.medico = medico;
	}

	public Date getDataOraInizio() {
		return this.dataOraInizio;
	}

	public void setDataOraInizio(Date dataOraInizio) {
		this.dataOraInizio = dataOraInizio;
	}

	public Date getDataOraFine() {
		return this.dataOraFine;
	}

	public void setDataOraFine(Date dataOraFine) {
		this.dataOraFine = dataOraFine;
	}

	public String getMotivazioni() {
		return this.motivazioni;
	}

	public void setMotivazioni(String motivazioni) {
		this.motivazioni = motivazioni;
	}

	public String getStatoPagamento() {
		return this.statoPagamento;
	}

	public void setStatoPagamento(String statoPagamento) {
		this.statoPagamento = statoPagamento;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

}
