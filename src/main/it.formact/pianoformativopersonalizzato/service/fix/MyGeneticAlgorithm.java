package pianoformativopersonalizzato.service.fix;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.uma.jmetal.algorithm.singleobjective.geneticalgorithm.GenerationalGeneticAlgorithm;
import org.uma.jmetal.operator.crossover.CrossoverOperator;
import org.uma.jmetal.operator.mutation.MutationOperator;
import org.uma.jmetal.operator.selection.SelectionOperator;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.solution.Solution;
import org.uma.jmetal.util.comparator.ObjectiveComparator;
import org.uma.jmetal.util.evaluator.SolutionListEvaluator;

@SuppressWarnings("serial")
public class MyGeneticAlgorithm<S extends Solution<?>> extends GenerationalGeneticAlgorithm<S> {
	  private Comparator<S> comparator;
	  private long initComputingTime;
	  private long thresholdComputingTime;
	  private S bestIndividual;
	  private int numberGeneration;

	public MyGeneticAlgorithm(Problem<S> problem, int maxEvaluations, int populationSize,
			CrossoverOperator<S> crossoverOperator, MutationOperator<S> mutationOperator, SelectionOperator<List<S>, S> selectionOperator,
			SolutionListEvaluator<S> evaluator) {
		
		
		super(problem, maxEvaluations, populationSize, crossoverOperator, mutationOperator, selectionOperator, evaluator);
		
		this.comparator = new ObjectiveComparator<S>(0);
		this.bestIndividual = null;
		this.numberGeneration = 1;
	}
	
	public S getBestIndividual() {
		return this.bestIndividual;
	}
	
	public void setMaxComputingTime (long maxComputingTime) {
	    initComputingTime = System.currentTimeMillis() ;
	    thresholdComputingTime = maxComputingTime ;
	}
	
	@Override
	protected boolean isStoppingConditionReached() {
		if (this.isStoppingByTime() || this.isStoppingByEvaluation()) {
			return true;
		}
		return false;
	}
	
	protected boolean isStoppingByEvaluation () {
		return super.isStoppingConditionReached();
	}
	
	protected boolean isStoppingByTime () {
		long currentComputingTime = System.currentTimeMillis() - initComputingTime ;
	    return currentComputingTime > thresholdComputingTime ;
	}
	
	@Override 
	protected List<S> replacement(List<S> population, List<S> offspringPopulation) {
		System.out.println("Popolazione numero " + this.numberGeneration + ":");
		for (int i = 0; i < population.size(); i++) {
			System.out.print(population.get(i));
		}
		System.out.println();
		this.numberGeneration++;
		
		int populationSize = population.size();
		int offspringPopulationSize = 0;
		
		if (populationSize >= 10) {
			offspringPopulationSize = populationSize / 100 * 10;
			Collections.sort(population, this.comparator);
			this.bestIndividual = population.get(0);
			for (int i = 0; i < offspringPopulationSize; i++) {
				offspringPopulation.add(population.get(i));
			}
			Collections.sort(offspringPopulation, comparator);
			for (int i = 0; i < offspringPopulationSize; i++) {
				offspringPopulation.remove(offspringPopulation.size() - 1);
			}
		}
		else if (populationSize < 10 && populationSize > 0) {
			Collections.sort(population, this.comparator);
			this.bestIndividual = population.get(0);
		}
		return offspringPopulation;
	}

}
