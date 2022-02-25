package pianoformativopersonalizzato.service;

import java.util.ArrayList;
import java.util.Arrays;

import org.uma.jmetal.algorithm.Algorithm;
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

import org.uma.jmetal.operator.mutation.impl.SimpleRandomMutation;

import model.entity.CategoriaEntity;

public class PianoFormativoPersonalizzatoGA {
	
	public PianoFormativoPersonalizzatoGA () {
		
	}
	
	public Individuo getPianoFormativoPersonalizzato(ArrayList<Stato> spazioStati,
			ArrayList<String> giorniLiberi, ArrayList<String> interessi, ArrayList<CategoriaEntity> categorie) {
		
		double probabilitaCrossover = 0.8;
        double probabilitaMutazione = 0.01;
        int geniSize = 10;
        int valutazioneMassima = 10000; // Da definire meglio
        int popolazioneSize = spazioStati.size() / 3;
		
		// Ci ricaviamo la popolazione
        Problem<IntegerSolution> problem = new PianoFormativoPersonalizzatoProblem(spazioStati, geniSize);
        
        BinaryTournamentSelection<IntegerSolution> selection = new BinaryTournamentSelection<>();
        CrossoverOperator<IntegerSolution> crossover = null;
        MutationOperator<IntegerSolution> mutation = new RandomResettingMutation(probabilitaMutazione);
        
        Algorithm<IntegerSolution> algoritmoGenetico = new GeneticAlgorithmBuilder<IntegerSolution>(problem, crossover, mutation)
        		.setPopulationSize(popolazioneSize)
        		.setMaxEvaluations(popolazioneSize)
        		.setSelectionOperator(selection)
        		.build();
        
        AlgorithmRunner algorithmRunner = new AlgorithmRunner.Executor(algoritmoGenetico).execute();
        IntegerSolution bestIndividual = algoritmoGenetico.getResult();
        
        JMetalLogger.logger.info(String.format("Problem: %s", problem.getName()));
        JMetalLogger.logger.info(String.format("Solution: %s", bestIndividual.getVariables()));
        JMetalLogger.logger.info(String.format("Evaluation: %s", Arrays.toString(bestIndividual.getObjectives())));
        JMetalLogger.logger.info(String.format("Total execution time: %s ms", algorithmRunner.getComputingTime()));
        
        return null;
		
	}
	
	public ArrayList<String> getIndividui () {
		return null;
	}
	
}
