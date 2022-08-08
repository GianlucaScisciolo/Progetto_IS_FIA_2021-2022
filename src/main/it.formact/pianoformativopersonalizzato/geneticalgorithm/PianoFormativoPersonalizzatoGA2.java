package pianoformativopersonalizzato.geneticalgorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

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
public class PianoFormativoPersonalizzatoGA2 {
	
	/**
	 * Costruttore classe PianoFormativoPersonalizzatoGA
	 */
	public PianoFormativoPersonalizzatoGA2 () {
		
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
		
		// Assegniamo una probabilità al crossover e alla mutazione
		double probabilitaCrossover = 0.8;
        double probabilitaMutazione = 0.2;
        
        int spazioStatiSize = spazioStati.size();
        
        // se spazioStatiSize < 1 allora ritorno un individuo vuoto
        if (spazioStatiSize < 1) {
        	return new Soluzione();
        }
        // se invece 1 <= spazioStatiSize < 11 allora ritorno un individuo con codifica = spazioStati 
        else if (spazioStatiSize >= 1 && spazioStatiSize < 11 ) {
        	return new Soluzione(spazioStati);
        }
        // se invece spazioStatiSize >= 11 allora individuoSize = 10 ed eseguo l'algoritmo genetico.
        int individuoSize = 10;
        
        // definisco la valutazione massima (Da definire meglio)
        int valutazioneMassima = 1000000;
        
        // definisco il numero totale di individui (popolazione).
        int popolazioneSize = (spazioStatiSize / 3);
        // se popolazioneSize è dispari allora popolazioneSize = popolazioneSize + 1
        if (popolazioneSize % 2 != 0) {
        	popolazioneSize = popolazioneSize + 1;
        }
		
		// definisco un problema di tipo PianoFormativoPersonalizzatoProblem
        Problem<IntegerSolution> pianoFormativoPersonalizzatoProblem = 
        		new PianoFormativoPersonalizzatoProblem(spazioStati, individuoSize, giorniLiberi, interessi, categorie);
        
        
        // Selezione: BinaryTournamentSelection (k-way Tournament Selection)
        BinaryTournamentSelection<IntegerSolution> selection = new BinaryTournamentSelection<>();
        // Crossover: IntegerSolutionUniformCrossover (Uniform Crossover)
        CrossoverOperator<IntegerSolution> crossover = new IntegerSolutionUniformCrossover(probabilitaCrossover);
        // Mutazione: IntegerSolutionRandomResettingMutation (Random Resetting Mutation)
        MutationOperator<IntegerSolution> mutation = new IntegerSolutionRandomResettingMutation(probabilitaMutazione);
        
        // valutazione di tipo sequenziale
        SolutionListEvaluator<IntegerSolution> evaluator = new SequentialSolutionListEvaluator<IntegerSolution>();
        
        // definisco un oggetto algoritmoGenetico della classe MyGeneticAlgorithm
        MyGeneticAlgorithm<IntegerSolution> algoritmoGenetico = 
        		new MyGeneticAlgorithm<> (pianoFormativoPersonalizzatoProblem, valutazioneMassima, popolazioneSize, 
        				crossover, mutation, selection, evaluator);
        
        // Gli assegno un tempo massimo di computazione (5 secondi)
        algoritmoGenetico.setMaxComputingTime(10000);
        
        // eseguiamo l'algoritmo genetico (oggetto algoritmoGenetico)
        AlgorithmRunner algorithmRunner = new AlgorithmRunner.Executor(algoritmoGenetico).execute();
        
        // miglior individuo ottenuto dall'esecuzione dell'algoritmo genetico
        IntegerSolution bestIndividual = algoritmoGenetico.getResult();
        
        // Stampo a video delle informazioni inerenti l'algoritmo genetico e la sua esecuzione
        JMetalLogger.logger.info(String.format("Problem: %s", pianoFormativoPersonalizzatoProblem.getName()));
        JMetalLogger.logger.info(String.format("Solution: %s", bestIndividual.variables()));
        JMetalLogger.logger.info(String.format("Evaluation: %s", Arrays.toString(bestIndividual.objectives())));
        JMetalLogger.logger.info(String.format("Total execution time: %s ms", algorithmRunner.getComputingTime()));
        JMetalLogger.logger.info(String.format("Description: %s", algoritmoGenetico.getDescription()));
        
        // Ottengo la codifica dell'individuo migliore
        List<Integer> indici = (ArrayList<Integer>) bestIndividual.variables();
        System.out.println(bestIndividual.toString());
        
        // Ottengo gli stati in base all'individuo migliore
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
	}
	
}