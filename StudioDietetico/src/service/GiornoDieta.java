package service;

import java.util.ArrayList;
import java.util.Iterator;

public class GiornoDieta {
	private String giorno = null;
	private ArrayList<StrutPasto> pasti = null;
	private String descrizione = "";
	private String note = "";
	
	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public GiornoDieta(String giorno) {
		this.giorno = giorno;
		pasti = new ArrayList<StrutPasto>();
	}

	public GiornoDieta() {
		// TODO Auto-generated constructor stub
	}

	public void addPasto(StrutPasto pasto) {
		pasti.add(pasto);
	}
	public String getGiorno() {
		return giorno;
	}

	public void setGiorno(String giorno) {
		this.giorno = giorno;
	}

	public ArrayList<StrutPasto> getPasti() {
		return pasti;
	}
	
	public StrutPasto getPasto(int index) {
		return pasti.get(index);
	}
	
	public String[] getPastiArr() {
		String[] arrpasti = new String[this.pasti.size()];
		for (int i = 0; i < pasti.size(); i++) {
			arrpasti[i] = pasti.get(i).getNomePasto();
		}
		return arrpasti;
	}

	public void setPasti(ArrayList<StrutPasto> pasti) {
		this.pasti = pasti;
	}

	public void delPasto(String nomePasto) {
		for (int i = 0; i < pasti.size(); i++) {
			if (pasti.get(i).getNomePasto().equalsIgnoreCase(nomePasto)) {
				pasti.remove(i);
				break;
			}
		}
		
	}


	
	
	
	
}
