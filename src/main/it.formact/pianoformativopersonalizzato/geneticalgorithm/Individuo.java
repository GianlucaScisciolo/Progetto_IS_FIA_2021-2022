package pianoformativopersonalizzato.geneticalgorithm;

import java.util.ArrayList;
import java.util.Collections;
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
	
	/*
	public void generaCodifica(ArrayList<Stato> spazioStati, int numeroGeni) {
		int min = 0;
		int max = spazioStati.size() - 1;

		Random random = new Random();
		
		ArrayList<Stato> codifica = new ArrayList<Stato>();
		
		for (int i = 0; i < numeroGeni; i++) {			
			int indiceCasuale = random.nextInt(max + min) + min;
			codifica.add(spazioStati.get(indiceCasuale));
		}
		
		this.codifica = codifica;
	}
	*/
	
	public void deletePercorsiDuplicatiByNome () {
		
		ArrayList<Stato> codifica = this.getCodifica();
		ArrayList<String> nomiIncontrati = new ArrayList<>();
		
		String nomePercorsoFormativo = codifica.get(0).getPercorsoFormativo().getNome().toUpperCase();
		nomiIncontrati.add(nomePercorsoFormativo);
		int i = 1;
		while (i < codifica.size()) {
			
			nomePercorsoFormativo = codifica.get(i).getPercorsoFormativo().getNome().toUpperCase();
			
			if (nomiIncontrati.contains(nomePercorsoFormativo)) {
				codifica.remove(i);
			}
			else {
				nomiIncontrati.add(nomePercorsoFormativo);
				i++;
			}
			
		}
		
		this.setCodifica(codifica);
		//return individuo;
		
	}
	
	public void sortByGiornoAndOrario (int numeroGeniDaOrdinare) {
		
		
		ArrayList<Stato> stati = new ArrayList<>();
		for (int i = 0; i < numeroGeniDaOrdinare; i++) {
			stati.add(this.getGene(i));
			System.out.println(stati.get(i).getGiorno() + " " + stati.get(i).getOrario());
		}
		
		Collections.sort(stati);
		
		System.out.println();
		
		for (int i = 0; i < numeroGeniDaOrdinare; i++) {
			this.setGene(i, stati.get(i));
			System.out.println(stati.get(i).getGiorno() + " " + stati.get(i).getOrario());
		}
		
	}
	
}
