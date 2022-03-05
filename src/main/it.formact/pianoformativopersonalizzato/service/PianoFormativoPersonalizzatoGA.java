package pianoformativopersonalizzato.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.uma.jmetal.algorithm.Algorithm;
import org.uma.jmetal.algorithm.singleobjective.geneticalgorithm.GenerationalGeneticAlgorithm;
import org.uma.jmetal.algorithm.singleobjective.geneticalgorithm.GeneticAlgorithmBuilder;
import org.uma.jmetal.example.AlgorithmRunner;
import org.uma.jmetal.operator.crossover.CrossoverOperator;
import org.uma.jmetal.operator.mutation.MutationOperator;
import org.uma.jmetal.operator.mutation.impl.IntegerPolynomialMutation;
import org.uma.jmetal.operator.selection.impl.BinaryTournamentSelection;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.solution.doublesolution.DoubleSolution;
import org.uma.jmetal.solution.integersolution.IntegerSolution;
import org.uma.jmetal.util.JMetalLogger;
import org.uma.jmetal.util.evaluator.SolutionListEvaluator;
import org.uma.jmetal.util.evaluator.impl.SequentialSolutionListEvaluator;
import org.uma.jmetal.operator.mutation.impl.SimpleRandomMutation;

import model.entity.CategoriaEntity;
import pianoformativopersonalizzato.service.fix.IntegerSolutionRandomResettingMutation;
import pianoformativopersonalizzato.service.fix.IntegerSolutionUniformCrossover;
import pianoformativopersonalizzato.service.fix.MyGeneticAlgorithm;

public class PianoFormativoPersonalizzatoGA {
	
	public PianoFormativoPersonalizzatoGA () {
		
	}
	
	public Individuo getPianoFormativoPersonalizzato(ArrayList<Stato> spazioStati,
			ArrayList<String> giorniLiberi, ArrayList<String> interessi, ArrayList<CategoriaEntity> categorie) {
		
		double probabilitaCrossover = 0.8;
        double probabilitaMutazione = 0.01;
        int geniSize = 10;
        int valutazioneMassima = 1000000; // Da definire meglio
        int popolazioneSize = spazioStati.size() / 3;
		
		// Ci ricaviamo la popolazione
        Problem<IntegerSolution> problem = new PianoFormativoPersonalizzatoProblem(spazioStati, geniSize, giorniLiberi, interessi, categorie);
        
        BinaryTournamentSelection<IntegerSolution> selection = new BinaryTournamentSelection<>();
        CrossoverOperator<IntegerSolution> crossover = new IntegerSolutionUniformCrossover(probabilitaCrossover);
        MutationOperator<IntegerSolution> mutation = new IntegerSolutionRandomResettingMutation(probabilitaMutazione);
        /*
        Algorithm<IntegerSolution> algoritmoGenetico = new GeneticAlgorithmBuilder<IntegerSolution>(problem, crossover, mutation)
        		.setPopulationSize(popolazioneSize)
        		.setMaxEvaluations(valutazioneMassima)
        		.setSelectionOperator(selection)
        		.build();
        */
        SolutionListEvaluator<IntegerSolution> evaluator = new SequentialSolutionListEvaluator<IntegerSolution>();
        MyGeneticAlgorithm<IntegerSolution> algoritmoGenetico = 
        		new MyGeneticAlgorithm<> (problem, valutazioneMassima, popolazioneSize, crossover, mutation, selection, evaluator);
        		/*(MyGeneticAlgorithm<IntegerSolution>) new GeneticAlgorithmBuilder<IntegerSolution>(problem, crossover, mutation)
				.setPopulationSize(popolazioneSize)
				.setMaxEvaluations(valutazioneMassima)
				.setSelectionOperator(selection)
				.build();*/
        System.out.println("Prova");
        algoritmoGenetico.setMaxComputingTime(6500);
        
        AlgorithmRunner algorithmRunner = new AlgorithmRunner.Executor(algoritmoGenetico).execute();
        IntegerSolution bestIndividual = algoritmoGenetico.getResult();
        
//        NSGA2 n;
        
        JMetalLogger.logger.info(String.format("Problem: %s", problem.getName()));
        JMetalLogger.logger.info(String.format("Solution: %s", bestIndividual.variables()));
        JMetalLogger.logger.info(String.format("Evaluation: %s", Arrays.toString(bestIndividual.objectives())));
        JMetalLogger.logger.info(String.format("Total execution time: %s ms", algorithmRunner.getComputingTime()));
        
        System.out.println(algoritmoGenetico.getDescription());
//        System.out.println(algoritmoGenetico.toString().);
        
        List<Integer> indici = (ArrayList<Integer>) bestIndividual.variables();
        ArrayList<Stato> codifica = new ArrayList<>();
        
        for (int i = 0; i < indici.size(); i++) {
			codifica.add(spazioStati.get(indici.get(i)));
			//System.out.println(indici.get(i));
		}
        
        return new Individuo(codifica);
	}
	
	public ArrayList<String> getIndividui () {
		return null;
	}
	
}
