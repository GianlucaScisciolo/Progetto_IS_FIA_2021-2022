package pianoformativopersonalizzato.geneticalgorithm;

import java.util.Comparator;

import pianoformativopersonalizzato.geneticalgorithm.Stato;

/**
 * 
 * Classe che si occupa della comparazione dei punteggi di 2 stati
 * 
 * @author GianlucaScisciolo
 *
 */
public class PunteggioComparator implements Comparator<Stato> {
	/**
	 * Tipo di ordinamento.
	 * - se ordinamento < 0 allora l'ordinamento dovrà essere decrescente;
	 * - se ordinamento >= 0 allora l'ordinamento dovrà essere crescente;
	 */
	private int ordinamento;
	
	/**
	 * 
	 * Costruttore della classe PunteggioComparator
	 * 
	 */
	public PunteggioComparator () {
		this.ordinamento = 0;
	}
	
	/**
	 * Costruttore della classe PunteggioComparator
	 * 
	 * @param ordinamento: tipo di ordinamento: 
	 * - se ordinamento < 0 allora l'ordinamento dovrà essere decrescente;
	 * - se ordinamento >= 0 allora l'ordinamento dovrà essere crescente;
	 */
	public PunteggioComparator (int ordinamento) {
		this.ordinamento = ordinamento;
	}
	
	@Override
	public int compare(Stato stato1, Stato stato2) {
		
		int risultato = stato1.getPunteggio() - stato2.getPunteggio();
		
		if (ordinamento < 0) {
			return -risultato;
		}
		
		return risultato;
		
	}
}









