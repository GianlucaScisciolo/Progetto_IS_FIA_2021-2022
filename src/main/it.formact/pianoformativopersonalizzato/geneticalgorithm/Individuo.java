package pianoformativopersonalizzato.geneticalgorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;


/**
 * 
 * @author GIANLUCA
 * 
 * Classe che definisce un individuo
 *
 */
public class Individuo {
	
	private ArrayList<Stato> codifica;
	
	/**
	 * Costruttore vuoto
	 */
	public Individuo() {
		
	}
	
	/**
	 * Costruttore che prende come parametro una codifica
	 * 
	 * @param codifica: codifica (DNA) dell'individuo
	 */
	public Individuo(ArrayList<Stato> codifica) {
		this.codifica = codifica;
	}
	
	/**
	 * Metodo che restituisce la codifica dell'individuo
	 * @return la codifica dell'individuo
	 */
	public ArrayList<Stato> getCodifica() {
		return this.codifica;
	}
	
	/**
	 * Metodo che modifica la modifica la codifica dell'individuo
	 * @param codifica: nuova codifica
	 */
	public void setCodifica(ArrayList<Stato> codifica) {
		this.codifica = codifica;
	}
	
	/**
	 * Metodo che ritorna il gene i-esimo dell'individuo
	 * @param i: posizione del gene 
	 * @return il gene i-esimo
	 */
	public Stato getGene(int i) {
		return codifica.get(i);
	}
	
	/**
	 * Metodo che modifica il gene i-esimo
	 * @param i: posizione del gene da modificare
	 * @param gene: nuovo gene
	 */
	public void setGene(int i, Stato gene) {
		this.codifica.set(i, gene);
	}
	
	
	/**
	 * Metodo che ritorna la lunghezza della codifica (il numero totale di geni dell'individuo)
	 * @return lunghezza della codifica
	 */
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
	
	/**
	 * Metodo che elimina i percorsi duplicati tramite il nome
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
	
	/**
	 * Questo metodo ordina i primi numeroGeniDaOrdinare geni da ordinare
	 * @param numeroGeniDaOrdinare: numero di geni da ordinare
	 */
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
