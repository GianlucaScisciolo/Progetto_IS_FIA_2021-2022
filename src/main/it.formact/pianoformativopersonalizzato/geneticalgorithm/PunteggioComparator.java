package pianoformativopersonalizzato.geneticalgorithm;

import java.util.Comparator;

/**
 * 
 * @author GIANLUCA
 *
 */
public class PunteggioComparator implements Comparator<Stato> {
	
	private int ordinamento;
	
	public PunteggioComparator () {
		this.ordinamento = 0;
	}
	
	/**
	 * Costruttore oggetto PunteggioComparator.
	 * @param ordinamento: tipo di ordinamento: 
	 * - se ordinamento < 0 allora l'ordinamento dovrà essere decrescente;
	 * - se ordinamento >= 0 allora l'ordinamento dovrà essere crescente;
	 * 
	 * di default ordinamento = 0.
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
