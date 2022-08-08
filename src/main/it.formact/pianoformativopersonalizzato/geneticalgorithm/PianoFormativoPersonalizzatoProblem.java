package pianoformativopersonalizzato.geneticalgorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.uma.jmetal.problem.integerproblem.impl.AbstractIntegerProblem;
import org.uma.jmetal.solution.integersolution.IntegerSolution;

/**
 * 
 * Classe che definisce il problema del piano formativo personalizzato
 *  
 * @author GianlucaScisciolo
 *
 */
public class PianoFormativoPersonalizzatoProblem extends AbstractIntegerProblem {
	
	private ArrayList<Stato> spazioStati;
	private int sizeIndividui;
	private ArrayList<String> giorniLiberi;
	private ArrayList<String> interessi;
	private Map<Integer, String> categorie;
	
	private ArrayList<Integer> punteggiIndividuoMigliore;
	private IntegerSolution individuoMigliore;
	
	
	/**
	 * Costruttore della classe PianoFormativoPersonalizzatoProblem
	 * 
	 * @param spazioStati: spazio degli stati
	 * @param sizeIndividui: numero di geni di ogni individuo (uguale per tutti)
	 * @param giorniLiberi: giorni liberi dello studente
	 * @param interessi: interessi dello studente
	 * @param categorie: categorie di percorsi formativi
	 */
	public PianoFormativoPersonalizzatoProblem(ArrayList<Stato> spazioStati, int sizeIndividui,
			ArrayList<String> giorniLiberi, ArrayList<String> interessi, Map<Integer, String> categorie) {
		
		this.spazioStati = spazioStati;
		this.sizeIndividui = sizeIndividui;
		this.giorniLiberi = giorniLiberi;
		this.interessi = interessi;
		this.categorie = categorie;
		
		// assegno un nome al problema
		setName("Piano formativo personalizzato");
		
		// setto il numero di variabili
		setNumberOfVariables(spazioStati.size());
		
		// inserisco i limiti inferiori e i limiti superiori e inizializzo i punteggi dell'individuo migliore con tutti 0
		// inizia
		List<Integer> lowerBounds = new ArrayList<>();
		List<Integer> upperBounds = new ArrayList<>();
		for (int i = 0; i < this.sizeIndividui; i++) {
			lowerBounds.add(0);
			upperBounds.add(spazioStati.size() - 1);
			
			punteggiIndividuoMigliore.add(0);
		}
		punteggiIndividuoMigliore.add(0);
		this.individuoMigliore = null;
		setVariableBounds(lowerBounds, upperBounds);
		
		// problema mono-obiettivo
		setNumberOfObjectives(1);
	}
	
	/**
	 * Metodo che ritorna lo spazio degli stati del problema
	 * 
	 * @return lo spazio degli stati del problema
	 */
	public ArrayList<Stato> getSpazioStati() {
		return spazioStati;
	}
	
	/**
	 * Metodo che ritorna il numero di geni di ogni individuo (uguale per tutti)
	 * @return il numero di geni di ogni individuo (uguale per tutti)
	 */
	public int getSizeIndividui() {
		return sizeIndividui;
	}
	
	/**
	 * Metodo che ritorna i giorni liberi dello studente
	 * 
	 * @return i giorni liberi dello studente
	 */
	public ArrayList<String> getGiorniLiberi() {
		return giorniLiberi;
	}
	
	/**
	 * Metodo che ritorna gli ointeressi dello studente
	 * 
	 * @return gli interessi dello studente
	 */
	public ArrayList<String> getInteressi() {
		return interessi;
	}
	
	/**
	 * Metodo che ritorna le categorie di percorsi formativi
	 * 
	 * @return le categorie di percorsi formativi
	 */
	public Map<Integer, String> getCategorie() {
		return categorie;
	}
	
	/**
	 * Metodo che ritorna i punteggi dell'individuo migliore del problema
	 * 
	 * @return i punteggi dell'individuo migliore del problema
	 */
	public ArrayList<Integer> getPunteggiIndividuoMigliore() {
		return punteggiIndividuoMigliore;
	}
	
	/**
	 * Metodo che ritorna l'individuo migliore del problema (non per forza l'ottimo)
	 * @return l'individuo migliore del problema (non per forza l'ottimo)
	 */
	public IntegerSolution getIndividuoMigliore() {
		return individuoMigliore;
	}
	
	
	
	
	
	
	
	
	
	
	@Override
	public IntegerSolution evaluate(IntegerSolution solution) {
		
		return null;
		
	}
	
	/**
	 * Metodo che calcola il punteggio del gene.
	 * @param gene: gene da valutare
	 * @return il punteggio del gene
	 */
	private int calcolaPunteggioGene (Integer gene) {
		return 0;
	}

}









