package pianoformativopersonalizzato.geneticalgorithm.fix;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.uma.jmetal.operator.crossover.CrossoverOperator;
import org.uma.jmetal.solution.integersolution.IntegerSolution;
import org.uma.jmetal.util.errorchecking.Check;
import org.uma.jmetal.util.errorchecking.JMetalException;
import org.uma.jmetal.util.pseudorandom.JMetalRandom;
import org.uma.jmetal.util.pseudorandom.RandomGenerator;

/** 
 * 
 * Classe che definisce l'operazione di crossover: Random Uniform Crossover
 * 
 * @author GianlucaScisciolo
 *
 */
public class IntegerSolutionUniformCrossover implements CrossoverOperator<IntegerSolution> {

	private double crossoverProbability;
	private RandomGenerator<Double> crossoverRandomGenerator;
	
	/**
	 * Costruttore della classe IntegerSolutionUniformCrossover
	 * 
	 * @param crossoverProbability: probabilità del crossover
	 */
	public IntegerSolutionUniformCrossover(double crossoverProbability) {
		this(crossoverProbability, () -> JMetalRandom.getInstance().nextDouble());
	}
	
	/**
	 * Costruttore della classe IntegerSolutionUniformCrossover
	 * 
	 * @param crossoverProbability: probabilità crossover
	 * @param crossoverRandomGenerator: Generatore casuale crossover
	 */
	public IntegerSolutionUniformCrossover(double crossoverProbability, RandomGenerator<Double> crossoverRandomGenerator) {
	    if (crossoverProbability < 0) {
	    	throw new JMetalException("Crossover probability is negative: " + crossoverProbability);
	    }
	    this.crossoverProbability = crossoverProbability;
	    this.crossoverRandomGenerator = crossoverRandomGenerator;
	}
		
	@Override
	public double getCrossoverProbability() {
		return crossoverProbability;
	}
	
	/**
	 * Metodo che modifica la probabilità del crossover
	 * 
	 * @param crossoverProbability: nuova probabilità crossover
	 */
	public void setCrossoverProbability(double crossoverProbability) {
	    this.crossoverProbability = crossoverProbability;
	}
	
	@Override
	public List<IntegerSolution> execute(List<IntegerSolution> solutions) {
		// se solution = null allora lanciamo un'eccezione del tipo: NullParameterException()
		Check.notNull(solutions);
		
		// se la taglia di solutions != 2 allora lanciamo un'eccezione del tipo: 
		// InvalidConditionException("There must be two parents instead of " + solutions.size())
	    Check.that(solutions.size() == 2, "There must be two parents instead of " + solutions.size());
	    
	    // eseguiamo il crossover tra solutions.get(0) e solutions.get(1)
	    List<IntegerSolution> solutionsCrossover = 
	    		doCrossover(crossoverProbability, solutions.get(0), solutions.get(1));
	    
	    // ritorno gli individui ottenuti dal crossover
	    return solutionsCrossover;
	}
	
	/**
	 * Metodo che esegue il crossover tra parent1 e parent2
	 * 
	 * @param probability: probabilità del crossover
	 * @param parent1: genitore 1
	 * @param parent2: genitore 2
	 * @return i 2 figli ottenuti dal crossover
	 */
	private List<IntegerSolution> doCrossover(double crossoverProbability, IntegerSolution parent1, IntegerSolution parent2) {
		List<IntegerSolution> offspring = new ArrayList<>(2);
	    offspring.add((IntegerSolution) parent1.copy());
	    offspring.add((IntegerSolution) parent2.copy());
		
	    // se il valore casuale ottenuto dal crossoverRandomGenerator <= crossoverProbability 
	    // allora possiamo eseguire l'operazione di crossover
	    if (crossoverRandomGenerator.getRandomValue() <= crossoverProbability) {	    	
	    	Random random = new Random();
	    	for (int i = 0; i < parent1.variables().size(); i++) {
	    		// ottengo il gene i-esimo del genitore1 e del genitore 2
	    		int geneParent1 = parent1.variables().get(i);
	    		int geneParent2 = parent2.variables().get(i);
	    		
	    		// ottengo un numero casuale: 0 oppure 1
	    		int randomNumber = random.nextInt(2);
	    		
	    		// se randomNumber = 0:
	    		// - gene i-esimo parent1 = gene i-esimo parent1 (nessuna modifica effettuata)
	    		// - gene i-esimo parent2 = gene i-esimo parent2 (nessuna modifica effettuata)
	    		if (randomNumber == 0) {
	    			offspring.get(0).variables().set(i, geneParent1);
	    			offspring.get(1).variables().set(i, geneParent2);	
	    		}
	    		// se invece randomNumber = 1:
	    		// - gene i-esimo parent1 = gene i-esimo parent2 (modifica effettuata)
	    		// - gene i-esimo parent2 = gene i-esimo parent1 (modifica effettuata)
	    		else {
	    			offspring.get(0).variables().set(i, geneParent2);
	    			offspring.get(1).variables().set(i, geneParent1);
	    		}
	    	}
	    }
	    
	    // ritorno i 2 figli ottenuti dal crossover
		return offspring;
	}
	
	@Override
	public int getNumberOfRequiredParents() {
		return 2;
	}
	
	@Override
	public int getNumberOfGeneratedChildren() {
		return 2;
	}
	
}
