package pianoformativopersonalizzato.geneticalgorithm;

import java.util.ArrayList;
import java.util.Collections;

import pianoformativopersonalizzato.geneticalgorithm.Stato;

/**
 * 
 * Classe che rappresenta una soluzione
 *  
 * @author GianlucaScisciolo
 *
 */
public class Soluzione {
	private ArrayList<Stato> stati;
	
	/**
	 * Costruttore della classe Soluzione
	 */
	public Soluzione () {
		this.stati = new ArrayList<>();
	}
	
	/**
	 * Costruttore della classe Soluzione
	 * 
	 * @param stati: stati della soluzione
	 */
	public Soluzione (ArrayList<Stato> stati) {
		this.stati = stati;
	}
	
	/**
	 * Metodo che ritorna gli stati della soluzione
	 * 
	 * @return stati della soluzione
	 */
	public ArrayList<Stato> getStati() {
		return stati;
	}
	
	/**
	 * Metodo che modifica gli stati della soluzione
	 * 
	 * @param stati: nuovi stati della soluzione
	 */
	public void setStati(ArrayList<Stato> stati) {
		this.stati = stati;
	}
	
	/**
	 * Metodo che restituisce uno stato della soluzione
	 * 
	 * @param i: indice stato
	 * @return lo stato i-esimo della soluzione
	 */
	public Stato getStato(int i) {
		return this.getStati().get(i);
	}
	
	/**
	 * Metodo che modifica lo stato i-esimo della soluzione
	 * 
	 * @param i: indice stato
	 * @param gene: nuovo stato
	 */
	public void setStato(int i, Stato stato) {
		this.getStati().set(i, stato);
	}
	
	/**
	 * Metodo che ritorna il numero di stati della soluzione
	 * 
	 * @return il numero di stati della soluzione
	 */
	public int getSize() {
		return this.getStati().size();
	}
	
	/**
	 * Metodo che elimina i percorsi duplicati tramite il nome
	 */
	public void deletePercorsiDuplicatiByNome () {
		// ottengo gli stati della soluzione
		ArrayList<Stato> stati = this.getStati();
		
		// inizializzo l'array di nomi incontrati con un array vuoto
		ArrayList<String> nomiIncontrati = new ArrayList<>();
		
		int i = 0;
		while (i < stati.size()) {
			String nomePercorsoFormativo = stati.get(i).getPercorsoFormativo().getNome().toUpperCase();
			
			if(nomiIncontrati.contains(nomePercorsoFormativo)) {
				stati.remove(i);
			}
			else {
				nomiIncontrati.add(nomePercorsoFormativo);
				i++;
			}
		}
		
		this.setStati(stati);
	}
	
	/**
	 * Questo metodo ordina i primi numeroStatiDaOrdinare stati da ordinare
	 * 
	 * @param numeroStatiDaOrdinare: numero di stati da ordinare
	 */
	public void sortByGiornoAndOrario (int numeroStatiDaOrdinare) {
		// inizializzo l'array di stati con un array vuoto
		ArrayList<Stato> stati = new ArrayList<>();
		
		// Aggiungo i primi numeroStatiDaOrdinare stati della soluzione nell'array stati.
		for (int i = 0; i < numeroStatiDaOrdinare; i++) {
			stati.add(this.getStato(i));
		}
		
		// ordino l'array stati
		Collections.sort(stati);
		
		// Sostituisco i primi numeroStatiDaOrdinare stati della soluzione con gli stessi ordinati
		for (int i = 0; i < numeroStatiDaOrdinare; i++) {
			this.setStato(i, stati.get(i));
		}
	}
}









