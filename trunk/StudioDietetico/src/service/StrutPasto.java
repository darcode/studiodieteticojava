package service;

import java.util.ArrayList;

public class StrutPasto {
private String nomePasto;
private ArrayList<StrutAlimento> alimenti;



public StrutPasto(String nomePasto/*, int idPasto*/) {
	this.nomePasto = nomePasto;
	//this.idPasto = idPasto;
	alimenti = new ArrayList<StrutAlimento>();
}

public String getNomePasto() {
	return nomePasto;
}

public void setNomePasto(String nomePasto) {
	this.nomePasto = nomePasto;
}

public ArrayList<StrutAlimento> getAlimenti() {
	return alimenti;
}

public String[] getAlimentiArr() {
	String[] arrali = new String[this.alimenti.size()];
	for (int i = 0; i < alimenti.size(); i++) {
		arrali[i] = alimenti.get(i).getNomeAlimento();
	}
	return arrali;
}

public void setAlimenti(ArrayList<StrutAlimento> alimenti) {
	this.alimenti = alimenti;
}

public void removeAlimenti(ArrayList<StrutAlimento> alimentiEli) {
	int i;
	for (StrutAlimento aliEli : alimentiEli) {
		i = 0;
		while (i<alimenti.size()) {
			if (aliEli.getNomeAlimento().equals(alimenti.get(i).getNomeAlimento())) {
				alimenti.remove(alimenti.get(i));
			}else
				i++;
		}
	}
//	for (int i = 0; i < alimentiEli.size(); i++) {
//		for (int j = 0; j < alimenti.size(); j++) {
//			if (alimentiEli.get(i).equals(alimenti.get(j))) {
//				alimentiEli.remove(alimentiEli.get(i));
//			}
//		}
//	}
}

public void addAlimento(StrutAlimento alimento){
	alimenti.add(alimento);
}

public void addAlimenti(ArrayList<StrutAlimento> alimenti) {
	for (int i = 0; i < alimenti.size(); i++) {
		this.alimenti.add(alimenti.get(i));
	}
	
}

}
