package pianoformativopersonalizzato.geneticalgorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.uma.jmetal.problem.integerproblem.impl.AbstractIntegerProblem;
import org.uma.jmetal.solution.integersolution.IntegerSolution;

import model.entity.PercorsoFormativoEntity;

/**
 * 
 * Classe che definisce il problema del piano formativo personalizzato
 * 
 * @author GianlucaScisciolo
 *
 */
public class PianoFormativoPersonalizzatoProblem extends AbstractIntegerProblem {
	private ArrayList<Integer> individui;
	private ArrayList<Stato> spazioStati;
	private ArrayList<String> giorniLiberi; 
	private ArrayList<String> interessi;
	private ArrayList<Integer> punteggiIndividuoMigliore;
	private IntegerSolution individuoMigliore;
	private int individuoSize;
	private Map<Integer, String> categorie;
	
	/**
	 * Costruttore della classe PianoFormativoPersonalizzatoProblem
	 * 
	 * @param spazioStati: spazio degli stati
	 * @param individuoSize: numero di geni di ogni individuo (uguale per tutti)
	 * @param giorniLiberi: giorni liberi dello studente
	 * @param interessi: interessi dello studente
	 * @param categorie: categorie di percorsi formativi
	 */
	public PianoFormativoPersonalizzatoProblem 
			(ArrayList<Stato> spazioStati, int individuoSize, ArrayList<String> giorniLiberi, 
			 ArrayList<String> interessi, Map<Integer, String> categorie) {
		
		this.spazioStati = spazioStati;
		this.individuoSize = individuoSize;
		this.giorniLiberi = giorniLiberi;
		this.interessi = interessi;
		this.categorie = categorie;
		individui = new ArrayList<>();
		
		// assegno un nome al problema
		setName("Piano formativo personalizzato");
		
		// setto il numero di variabili
		setNumberOfVariables(spazioStati.size());
		
		// inserisco i limiti inferiori e i limiti superiori
		List<Integer> lowerBounds = new ArrayList<>();
		List<Integer> upperBounds = new ArrayList<>();
		for (int i = 0; i < this.individuoSize; i++) {
			lowerBounds.add(0);
			upperBounds.add(spazioStati.size() - 1);
		}
		setVariableBounds(lowerBounds, upperBounds);
		
		// problema mono-obiettivo
		setNumberOfObjectives(1);
		
		// inizializzo i punteggi dell'individuo migliore con tutti 0
		this.punteggiIndividuoMigliore = new ArrayList<>();
		for (int i = 0; i < this.individuoSize + 1; i++) {
			punteggiIndividuoMigliore.add(0);
		}
		this.individuoMigliore = null;
	}
	
	/**
	 * Metodo che ritorna gli individui
	 * 
	 * @return gli individui
	 */
	public ArrayList<Integer> getIndividui() {
		return individui;
	}
	
	/**
	 * Metodo che modifica gli individui
	 * 
	 * @param individui: nuovi individui
	 */
	public void setIndividui(ArrayList<Integer> individui) {
		this.individui = individui;
	}
	
	/**
	 * Metodo che ritorna lo spazio degli stati
	 * 
	 * @return lo spazio degli stati
	 */
	public ArrayList<Stato> getSpazioStati() {
		return spazioStati;
	}
	
	/**
	 * Metodo che modofica lo spazio degli stati
	 * 
	 * @param spazioStati: nuovo spazio degli stati
	 */
	public void setSpazioStati(ArrayList<Stato> spazioStati) {
		this.spazioStati = spazioStati;
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
	 * Metodo che modifica i giorni liberi dello studente
	 * 
	 * @param giorniLiberi: nuovi giorni liberi dello studente
	 */
	public void setGiorniLiberi(ArrayList<String> giorniLiberi) {
		this.giorniLiberi = giorniLiberi;
	}
	
	/**
	 * Metodo che ritorna gli interessi dello studente
	 * 
	 * @return gli interessi dello studente
	 */
	public ArrayList<String> getInteressi() {
		return interessi;
	}
	
	/**
	 * Metodo che modifica gli interessi dello studente
	 * 
	 * @param interessi: nuovi interessi dello studente
	 */
	public void setInteressi(ArrayList<String> interessi) {
		this.interessi = interessi;
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
	 * Metodo che modifica le categorie di percorsi formativi
	 * 
	 * @param categorie: nuove categorie di percorsi formativi
	 */
	public void setCategorie(Map<Integer, String> categorie) {
		this.categorie = categorie;
	}
	
	/**
	 * Metodo che ritorna i punteggi dell'individuo migliore
	 * 
	 * @return i punteggi dell'individuo migliore
	 */
	public ArrayList<Integer> getPunteggiIndividuoMigliore() {
		return punteggiIndividuoMigliore;
	}
	
	/**
	 * Metodo che modifica i punteggi dell'individuo migliore
	 * 
	 * @param punteggiIndividuoMigliore: nuovi punteggi dell'individuo migliore
	 */
	public void setPunteggiIndividuoMigliore(ArrayList<Integer> punteggiIndividuoMigliore) {
		this.punteggiIndividuoMigliore = punteggiIndividuoMigliore;
	}
	
	/**
	 * Metodo che ritorna l'individuo migliore
	 * 
	 * @return l'individuo migliore
	 */
	public IntegerSolution getIndividuoMigliore() {
		return this.individuoMigliore;
	}
	
	/**
	 * Metodo che modifica l'individuo migliore
	 * 
	 * @param individuoMigliore: nuovo individuo migliore
	 */
	public void setIndividuoMigliore (IntegerSolution individuoMigliore) {
		this.individuoMigliore = individuoMigliore;
	}
	
	/**
	 * Metodo che ritorna il numero di geni degli individui (uguale per tutti)
	 * 
	 * @return il numero di geni degli individui (uguale per tutti)
	 */
	public int getIndividuoSize() {
		return individuoSize;
	}
	
	/**
	 * Metodo che modifica il numero di geni degli individui (uguale per tutti)
	 * 
	 * @param individuoSize: nuovo numero di geni degli individui (uguale per tutti)
	 */
	public void setGeniSize(int individuoSize) {
		this.individuoSize = individuoSize;
	}

	@Override
	public IntegerSolution evaluate(IntegerSolution integerSolution) {
		// Otteniamo la Codifica dell'individuo da valutare
		ArrayList<Integer> codifica = (ArrayList<Integer>) integerSolution.variables();
		
		ArrayList<Integer> punteggiIndividuo = new ArrayList<>();
		ArrayList<String> nomiAccettati = new ArrayList<>();
		int punteggio = 0;
		int size = this.individuoSize;
		int i = 0;
		
		// valutiamo tutti i geni
		while(i < size) {
			int punteggioGene = 0;
			
			// otteniamo il gene i-esimo
			int gene = codifica.get(i);
			// otteniamo il contenuto del gene (uno stato)
			Stato contenutoGene = this.getSpazioStati().get(gene);
			// otteniamo il nome del percorso formativo dello stato
			String nomePercorso = contenutoGene.getPercorsoFormativo().getNome();
			
			Boolean presente = false;
			// se indice gene < 1 allora presente = falso 
			if (i < 1) {
				presente = false;
			}
			// altrimenti
			else {
				// - se il nome dello stato è presente nell'array dei nomi accettati allora presente = vero
				// - se il nome dello stato NON è presente nell'array dei nomi accettati allora presente = falso
				presente = nomiAccettati.contains(nomePercorso);
			}
			
			// se presente = falso:
			if (presente == false) {
				// aggiungo il nome del percorso formativo nell'array dei nomi accettati
				nomiAccettati.add(nomePercorso);
				// valuto il gene
				punteggioGene = this.calcolaPunteggioGene(gene);
				// aggiungo il punteggio nell'array dei punteggi del gene
				punteggiIndividuo.add(punteggioGene);
				// sommo il punteggio al punteggio finale.
				punteggio += punteggioGene;
			}
			
			i++;
		}
		
		// aggiungo il punteggio totale all'array dei punteggi dell'individuo
		punteggiIndividuo.add(punteggio);
		
		// Se punteggio individuo > punteggio individuo migliore allora punteggio individuo migliore = punteggio individuo
		// (Da rivedere)
		if (punteggiIndividuo.get(punteggiIndividuo.size() - 1) 
				> punteggiIndividuoMigliore.get(punteggiIndividuoMigliore.size() - 1)) {
			
			this.setPunteggiIndividuoMigliore(punteggiIndividuo);
			this.setIndividuoMigliore(integerSolution);
		}
		System.out.println(punteggiIndividuoMigliore);
		
		integerSolution.objectives()[0] = -punteggio;
		return integerSolution;
	}
	
	/**
	 * Metodo che calcola il punteggio del gene
	 * @param gene: gene da valutare
	 * @return il punteggio del gene
	 */
	private int calcolaPunteggioGene (Integer gene) {
		// all'inizio punteggio = 10
		int punteggio = 10;
		
		// otteniamo il contenuto del gene (uno stato)
		Stato contenutoGene = this.getSpazioStati().get(gene);
		// otteniamo il percorsoFormativo dello stato
		PercorsoFormativoEntity percorsoFormativo = contenutoGene.getPercorsoFormativo();
		// otteniamo il nome, l'indice dei contenuti, l'id della categoria e il nome della categoria del percorso formativo
		String nome = percorsoFormativo.getNome().toLowerCase();
		String indiceContenuti = percorsoFormativo.getIndice_contenuti().toLowerCase();
		Integer idCategoria = percorsoFormativo.getCategoria();
		String nomeCategoria = categorie.get(idCategoria).toLowerCase();
		
		boolean risultatoInteresse = false;
		boolean risultatoCategoria = false;
		
		// Scorro tutti gli interessi dello studente
		for (int i = 0; i < interessi.size(); i++) {
			// ottengo l'interesse i-esimo dello studente (in minuscolo)
			String interesse = interessi.get(i).toLowerCase();
			
			// se il nome del percorso formativo contiene l'interesse i-esimo dello studente allora: 
			// assegno 10 punti al gene
			risultatoInteresse = nome.contains(interesse); // Da controllare meglio
			if (risultatoInteresse) {
				punteggio += 10;
			}
			else {
				punteggio += 5;
			}
			
			// se l'indice dei contenuti del percorso formativo contiene l'interesse i-esimo del percorso formativo allora: 
			// assegno 5 punti al gene
			risultatoInteresse = indiceContenuti.contains(interesse); // Da controllare meglio
			if (risultatoInteresse) {
				punteggio += 10;
			}
			else {
				punteggio += 5;
			}
			
			
			// se la categoria del percorso formativo contiene l'interesse i-esimo del percorso formativo allora:
			// assegno 5 punti al gene
			risultatoCategoria = nomeCategoria.contains(interesse); // Da controllare meglio
			if (risultatoCategoria) {
				punteggio += 10;
			}
			else {
				punteggio += 5;
			}
		}
		
		// ritorno il punteggio del gene
		return punteggio;
		
	}
	
	/*
	public ArrayList<String> getInteresseAndArea (String interesse) {
		
		ArrayList<String> interesseAndArea = new ArrayList<>();
		String areaInteresse = "";
		
        int posizioneParentesi = interesse.indexOf("(");
        if (posizioneParentesi != -1) {
        	areaInteresse = interesse.substring(posizioneParentesi + 1, interesse.length() - 1);
        	interesse = interesse.substring(0, posizioneParentesi - 1);
        }
        else {
        	areaInteresse = interesse;
        }
        
        interesseAndArea.add(interesse);
        interesseAndArea.add(areaInteresse);
        
        return interesseAndArea;
	}
	*/
	
}