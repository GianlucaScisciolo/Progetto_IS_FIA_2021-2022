package pianoformativopersonalizzato.geneticalgorithm.fix;

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
	
	/**
	 * Costruttore della classe MyGeneticAlgorithm
	 * 
	 * @param problem: problema
	 * @param maxEvaluations: valutazione massima
	 * @param populationSize: numero di individui della popolazione
	 * @param crossoverOperator: operatore di crossover
	 * @param mutationOperator: operatore di mutazione
	 * @param selectionOperator: operatore di selezione
	 * @param evaluator: valutatore
	 */
	public MyGeneticAlgorithm(Problem<S> problem, int maxEvaluations, int populationSize,
			CrossoverOperator<S> crossoverOperator, MutationOperator<S> mutationOperator, 
			SelectionOperator<List<S>, S> selectionOperator, SolutionListEvaluator<S> evaluator) {

		super(problem, maxEvaluations, populationSize, crossoverOperator, mutationOperator, selectionOperator, evaluator);
		this.comparator = new ObjectiveComparator<S>(0);
		this.bestIndividual = null;
		this.numberGeneration = 0;
	}
	
	/**
	 * Metodo che ritorna il miglior individuo
	 * @return il miglior individuo
	 */
	public S getBestIndividual() {
		return this.bestIndividual;
	}
	
	/**
	 * Metodo che modifica il tempo massimo di computazione
	 * @param maxComputingTime: nuovo tempo massimo di computazione
	 */
	public void setMaxComputingTime (long maxComputingTime) {
		initComputingTime = System.currentTimeMillis() ;
		thresholdComputingTime = maxComputingTime ;
	}
	
	@Override
	protected boolean isStoppingConditionReached() {
		// se è scaduto il tempo e/oppure abbiamo raggiunto l'ottimo allora ci fermiamo altrimenti non ci fermiamo
		if (this.isStoppingByTime() || this.isStoppingByEvaluation()) {
			return true;
		}
		return false;
	}
	
	/**
	 * Metodo che controlla se abbiamo raggiunto l'ottimo
	 * @return 
	 * 		true: se abbiamo raggiunto l'ottimo; <br>
	 * 		false: se NON abbiamo raggiunto l'ottimo
	 */
	protected boolean isStoppingByEvaluation () {
		return super.isStoppingConditionReached();
	}
	
	/**
	 * Metodo che controlla se è scaduto il tempo.
	 * @return
	 * 		true: se è scaduto il tempo; <br>
	 * 		false: se NON è scaduto il tempo.
	 */
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
		
		// incrementiamo il numero della generazione
		this.numberGeneration++;
		
		int populationSize = population.size();
		int offspringPopulationSize = 0;
		
		if (populationSize >= 10) {
			
			offspringPopulationSize = (populationSize < 20) ? 1 : 2;
			
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









