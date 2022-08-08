package pianoformativopersonalizzato.geneticalgorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.uma.jmetal.algorithm.Algorithm;
import org.uma.jmetal.algorithm.singleobjective.geneticalgorithm.GeneticAlgorithmBuilder;
import org.uma.jmetal.example.AlgorithmRunner;
import org.uma.jmetal.operator.crossover.CrossoverOperator;
import org.uma.jmetal.operator.mutation.MutationOperator;
import org.uma.jmetal.operator.selection.impl.BinaryTournamentSelection;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.solution.integersolution.IntegerSolution;
import org.uma.jmetal.util.JMetalLogger;
import org.uma.jmetal.util.evaluator.SolutionListEvaluator;
import org.uma.jmetal.util.evaluator.impl.SequentialSolutionListEvaluator;

import pianoformativopersonalizzato.geneticalgorithm.fix.IntegerSolutionRandomResettingMutation;
import pianoformativopersonalizzato.geneticalgorithm.fix.IntegerSolutionUniformCrossover;
import pianoformativopersonalizzato.geneticalgorithm.fix.MyGeneticAlgorithm;

/**
 * 
 * Classe che definisce il PianoFormativoPersonalizzatoGA (algoritmo genetico piano formativo personalizzato)
 * 
 * @author GianlucaScisciolo
 * 
 */
public class PianoFormativoPersonalizzatoGA {
	
	/**
	 * Costruttore classe PianoFormativoPersonalizzatoGA
	 */
	public PianoFormativoPersonalizzatoGA () {
		
	}
	
	/**
	 * Metodo che ritorna un piano formativo personalizzato.
	 * 
	 * @param spazioStati: spazio degli stati
	 * @param giorniLiberi: giorni liberi dello studente
	 * @param interessi: interessi dello studente
	 * @param categorie: categorie di percorsi formativi
	 * 
	 * @return un piano formativo personalizzato
	 */
	public Soluzione getPianoFormativoPersonalizzato(ArrayList<Stato> spazioStati,
			ArrayList<String> giorniLiberi, ArrayList<String> interessi, Map<Integer, String> categorie) {
				
		/********************  Inizializzo parametri algoritmo genetico  ********************/
		
		// Assegniamo una probabilità al crossover e alla mutazione
		double probabilitaCrossover = 0.8;
		double probabilitaMutazione = 0.2;
		
		// mi ricavo il numero di stati nel vettore spazio stati
		int sizeSpazioStati = (spazioStati == null) ? 0 : spazioStati.size();
		
		// se N < 1 allora ritorno un individuo vuoto
		// se 1 <= N < 11 allora ritorno un individuo con codifica = spazioStati 
		// se N >= 11 allora sizeIndividui = 10 ed eseguo l'algoritmo genetico.
		int sizeIndividui;
		if (sizeSpazioStati >= 11) { 
			sizeIndividui = 10; 
		}
		else {
			return (sizeSpazioStati < 1) ? new Soluzione() : new Soluzione(spazioStati);
		}
		
		// definisco la valutazione massima (Da definire meglio)
		int valutazioneMassima = 1000000;		
		
		// definisco il numero totale di individui (popolazione): M = |popolazione|.
		int sizePopolazione = ( (sizeSpazioStati/3) % 2 == 0 ) ? sizeSpazioStati/3 : (sizeSpazioStati/3)+1;
		
		Problem<IntegerSolution> pianoFormativoPersonalizzatoProblem = new PianoFormativoPersonalizzatoProblem
				(spazioStati, sizeIndividui, giorniLiberi, interessi, categorie);////
		
		// Selezione: BinaryTournamentSelection (k-way Tournament Selection)
		BinaryTournamentSelection<IntegerSolution> selection = new BinaryTournamentSelection<>();
		// Crossover: IntegerSolutionUniformCrossover (Uniform Crossover)
		CrossoverOperator<IntegerSolution> crossover = new IntegerSolutionUniformCrossover(probabilitaCrossover);
		// Mutazione: IntegerSolutionRandomResettingMutation (Random Resetting Mutation)
		MutationOperator<IntegerSolution> mutation = new IntegerSolutionRandomResettingMutation(probabilitaMutazione);
		
		// valutazione di tipo sequenziale
		SolutionListEvaluator<IntegerSolution> evaluator = new SequentialSolutionListEvaluator<IntegerSolution>();
		
		MyGeneticAlgorithm<IntegerSolution> algoritmoGenetico = 
				new MyGeneticAlgorithm<> (pianoFormativoPersonalizzatoProblem, valutazioneMassima, sizePopolazione, 
						crossover, mutation, selection, evaluator);
		
		// Gli assegno un tempo massimo di computazione (5 secondi)
		algoritmoGenetico.setMaxComputingTime(5000);
        
		/************************************************************************************/
        
        /***************************  Eseguo l'algoritmo genetico  **************************/
        
        AlgorithmRunner algorithmRunner = new AlgorithmRunner.Executor(algoritmoGenetico).execute();
        // miglior individuo ottenuto dall'esecuzione dell'algoritmo genetico (non per forza l'ottimo)
        IntegerSolution bestIndividual = algoritmoGenetico.getResult();
        
        // Stampo a video delle informazioni inerenti l'algoritmo genetico e la sua esecuzione
        JMetalLogger.logger.info(String.format("Problem: %s", pianoFormativoPersonalizzatoProblem.getName()));
        JMetalLogger.logger.info(String.format("Solution: %s", bestIndividual.variables()));
        JMetalLogger.logger.info(String.format("Evaluation: %s", Arrays.toString(bestIndividual.objectives())));
        JMetalLogger.logger.info(String.format("Total execution time: %s ms", algorithmRunner.getComputingTime()));
        JMetalLogger.logger.info(String.format("Description: %s", algoritmoGenetico.getDescription()));
        
        /************************************************************************************/
		
        /*******************  Ottengo e ritorno la soluzione del problema  ******************/
        
        // Ottengo gli indici dell'individuo migliore
        List<Integer> indici = (ArrayList<Integer>) bestIndividual.variables();
        System.out.println(bestIndividual.toString());
        
        // Ottengo gli stati degli indici
        ArrayList<Stato> stati = new ArrayList<>();
        for (int i = 0; i < indici.size(); i++) {
			stati.add(spazioStati.get(indici.get(i)));
		}
        
        // Assegno il punteggio ad ogni stato
        PianoFormativoPersonalizzatoProblem problema = (PianoFormativoPersonalizzatoProblem) pianoFormativoPersonalizzatoProblem;
        for (int i = 0; i < problema.getPunteggiIndividuoMigliore().size() - 1; i++) {
        	stati.get(i).setPunteggio(problema.getPunteggiIndividuoMigliore().get(i));
        }
        
        System.out.println(problema.getPunteggiIndividuoMigliore());
        for (int i = 0; i < stati.size(); i++) {
        	System.out.println(stati.get(i).getPunteggio());
        }
        
        // ritorno l'individuo migliore sotto forma di soluzione
        return new Soluzione(stati);
        
        /************************************************************************************/
	}
	
}









