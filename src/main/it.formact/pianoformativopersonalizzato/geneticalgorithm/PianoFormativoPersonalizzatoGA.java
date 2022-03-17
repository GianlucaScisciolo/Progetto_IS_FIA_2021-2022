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

import model.entity.CategoriaEntity;
import pianoformativopersonalizzato.geneticalgorithm.fix.IntegerSolutionRandomResettingMutation;
import pianoformativopersonalizzato.geneticalgorithm.fix.IntegerSolutionUniformCrossover;
import pianoformativopersonalizzato.geneticalgorithm.fix.MyGeneticAlgorithm;

public class PianoFormativoPersonalizzatoGA {
	
	public PianoFormativoPersonalizzatoGA () {
		
	}
	
	public Individuo getPianoFormativoPersonalizzato(ArrayList<Stato> spazioStati,
			ArrayList<String> giorniLiberi, ArrayList<String> interessi, Map<Integer, String> categorie) {
		
		double probabilitaCrossover = 0.8;
        double probabilitaMutazione = 0.2;
        int geniSize = 10;
        int valutazioneMassima = 1000000; // Da definire meglio
        int popolazioneSize = (spazioStati.size() / 2);
        if ( (popolazioneSize % 2) == 1 ) {
        	popolazioneSize += 1;
        }
        
        //int popolazioneSize = (spazioStati.size() / 2);
		
		// Ci ricaviamo la popolazione
        Problem<IntegerSolution> problem = new PianoFormativoPersonalizzatoProblem(spazioStati, geniSize, giorniLiberi, interessi, categorie);
        
        BinaryTournamentSelection<IntegerSolution> selection = new BinaryTournamentSelection<>();
        CrossoverOperator<IntegerSolution> crossover = new IntegerSolutionUniformCrossover(probabilitaCrossover);
        MutationOperator<IntegerSolution> mutation = new IntegerSolutionRandomResettingMutation(probabilitaMutazione);
        SolutionListEvaluator<IntegerSolution> evaluator = new SequentialSolutionListEvaluator<IntegerSolution>();
        MyGeneticAlgorithm<IntegerSolution> algoritmoGenetico = 
        		new MyGeneticAlgorithm<> (problem, valutazioneMassima, popolazioneSize, crossover, mutation, selection, evaluator);
        //algoritmoGenetico.setMaxComputingTime(6500);
        algoritmoGenetico.setMaxComputingTime(3000);
        AlgorithmRunner algorithmRunner = new AlgorithmRunner.Executor(algoritmoGenetico).execute();
        
        IntegerSolution bestIndividual = algoritmoGenetico.getResult();
        
        JMetalLogger.logger.info(String.format("Problem: %s", problem.getName()));
        JMetalLogger.logger.info(String.format("Solution: %s", bestIndividual.variables()));
        JMetalLogger.logger.info(String.format("Evaluation: %s", Arrays.toString(bestIndividual.objectives())));
        JMetalLogger.logger.info(String.format("Total execution time: %s ms", algorithmRunner.getComputingTime()));
        JMetalLogger.logger.info(String.format("Description: %s", algoritmoGenetico.getDescription()));
        
        List<Integer> indici = (ArrayList<Integer>) bestIndividual.variables();
        System.out.println(bestIndividual.toString());
        ArrayList<Stato> codifica = new ArrayList<>();
        
        for (int i = 0; i < indici.size(); i++) {
			codifica.add(spazioStati.get(indici.get(i)));
		}
        
        
        return new Individuo(codifica);
	}
	
	public ArrayList<String> getIndividui () {
		return null;
	}
	
}
