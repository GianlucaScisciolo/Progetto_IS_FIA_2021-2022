package pianoformativopersonalizzato.service;

import java.util.ArrayList;

import model.entity.PercorsoFormativoEntity;

public class PianoFormativoPersonalizzato {
	
	private ArrayList<Stato> codifica;
	
	public PianoFormativoPersonalizzato() {
		
	}
	
	public PianoFormativoPersonalizzato(ArrayList<Stato> codifica) {
		this.codifica = codifica;
	}
	
	
	
	
	
	
	public ArrayList<Stato> getCodifica() {
		return this.codifica;
	}

	public void setCodifica(ArrayList<Stato> codifica) {
		this.codifica = codifica;
	}
	
	public Stato getGene(int i) {
		return codifica.get(i);
	}
	
	public void setGene(int i, Stato gene) {
		this.codifica.set(i, gene);
	}
	
	
	public int size() {
		return this.getCodifica().size();
	}
	
	
	public PianoFormativoPersonalizzato getPianoFormativoPersonalizzato(ArrayList<Stato> spazioStati,
			ArrayList<String> giorniLiberi, ArrayList<String> interessi) {
			
		return null;
		
	}

	
}
