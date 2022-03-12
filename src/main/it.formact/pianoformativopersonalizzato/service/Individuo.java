package pianoformativopersonalizzato.service;

import java.util.ArrayList;
import java.util.Random;



public class Individuo {
	
	private ArrayList<Stato> codifica;
	
	public Individuo() {
		
	}
	
	public Individuo(ArrayList<Stato> codifica) {
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
	
	public void generaCodifica(ArrayList<Stato> spazioStati) {
		int min = 0;
		int max = spazioStati.size() - 1;

		Random random = new Random();
		
		ArrayList<Stato> codifica = new ArrayList<Stato>();
		
		for (int i = 0; i < 10; i++) {			
			int indiceCasuale = random.nextInt(max + min) + min;
			codifica.add(spazioStati.get(indiceCasuale));
		}
		
		this.codifica = codifica;
	}
	
}
