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
import org.uma.jmetal.util.evaluator.impl.SequentialSolutionListEvaluator;

@SuppressWarnings("serial")
public class MyGeneticAlgorithm<S extends Solution<?>> extends GenerationalGeneticAlgorithm<S> {
	  private Comparator<S> comparator;
	  private long initComputingTime;
	  private long thresholdComputingTime;

	public MyGeneticAlgorithm(Problem<S> problem, int maxEvaluations, int populationSize,
			CrossoverOperator<S> crossoverOperator, MutationOperator<S> mutationOperator, SelectionOperator<List<S>, S> selectionOperator,
			SolutionListEvaluator<S> evaluator) {
		
		
		super(problem, maxEvaluations, populationSize, crossoverOperator, mutationOperator, selectionOperator, evaluator);
		
		this.comparator = new ObjectiveComparator<S>(0);
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
		int populationSize = population.size();
		int offspringPopulationSize = 0;
		
		if (populationSize >= 10) {
			offspringPopulationSize = populationSize / 100 * 10;
			Collections.sort(population, this.comparator);
			for (int i = 0; i < offspringPopulationSize; i++) {
				offspringPopulation.add(population.get(i));
			}
			Collections.sort(offspringPopulation, comparator);
			for (int i = 0; i < offspringPopulationSize; i++) {
				offspringPopulation.remove(offspringPopulation.size() - 1);
			}
		}
		
		return offspringPopulation;
	  }

}
