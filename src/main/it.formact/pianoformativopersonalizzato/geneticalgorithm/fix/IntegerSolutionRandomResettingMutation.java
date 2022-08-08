package pianoformativopersonalizzato.geneticalgorithm.fix;

import java.util.Random;

import org.uma.jmetal.operator.mutation.MutationOperator;
import org.uma.jmetal.solution.integersolution.IntegerSolution;
import org.uma.jmetal.util.bounds.Bounds;
import org.uma.jmetal.util.errorchecking.JMetalException;
import org.uma.jmetal.util.pseudorandom.JMetalRandom;
import org.uma.jmetal.util.pseudorandom.RandomGenerator;
 
/**
 * 
 * Classe che definisce l'operazione di mutazione: Random Resetting Mutation
 * 
 * @author GianlucaScisciolo
 *
 */
public class IntegerSolutionRandomResettingMutation implements MutationOperator<IntegerSolution> {
	private double mutationProbability;
	private RandomGenerator<Double> randomGenerator;
	
	/**
	 * Costruttore della classe IntegerSolutionRandomResettingMutation
	 * 
	 * @param mutationProbability probabilità mutazione
	 */
	public IntegerSolutionRandomResettingMutation(double mutationProbability) {
		this(mutationProbability, () -> JMetalRandom.getInstance().nextDouble());
	}
	
	/**
	 * Costruttore della classe IntegerSolutionRandomResettingMutation
	 * 
	 * @param mutationProbability: probabilità mutazione
	 * @param randomGenerator: generatore numeri casuali
	 */
	public IntegerSolutionRandomResettingMutation(double mutationProbability, RandomGenerator<Double> randomGenerator) {
		if (mutationProbability < 0) {
			throw new JMetalException("Mutation probability is negative: " + mutationProbability);
		}
		
		this.mutationProbability = mutationProbability;
		this.randomGenerator = randomGenerator;
	}
	
	@Override
	public double getMutationProbability() {
		return mutationProbability;
	}
	
	/**
	 * Metodo che modifica la probabilità della mutazione
	 * 
	 * @param mutationProbability: nuova probabilità della mutazione
	 */
	public void setMutationProbability(double mutationProbability) {
		this.mutationProbability = mutationProbability;
	}
	
	@Override
	public IntegerSolution execute(IntegerSolution solution) {
		// se soluzione = null allora lanciamo un'eccezione per segnalare che non deve essere nullo
		if (null == solution) {
			throw new JMetalException("Null parameter");
		}
		
		// eseguiamo la mutazione
		doMutation(mutationProbability, solution);
		
		// ritorniamo l'individuo mutato
		return solution;
	}
	
	/**
	 * Metodo che esegue la mutazione di solution
	 * 
	 * @param mutationProbability: probabilità della mutazione
	 * @param solution: individuo da mutare
	 */
	private void doMutation(double mutationProbability, IntegerSolution solution) {
		Random randomNumber = new Random();
		
		// se il valore casuale ottenuto dal randomGenerator <= mutationProbability 
		// allora possiamo eseguire la mutazione:
		
		
		if (randomGenerator.getRandomValue() <= mutationProbability) {
			// otteniamo il limite superiore di solutions
			Bounds<Integer> bounds = solution.getBounds(0);
			Integer upperBound = bounds.getUpperBound();
			
			// muto 4 geni casuali dell'individuo
			for (int i = 0; i < 4; i++) {
				// indice gene casuale = numero casuale compreso tra 0 |solutions| - 1
				Integer randomIndexGene = JMetalRandom.getInstance().nextInt(0, solution.variables().size() - 1);
				// indice stato casuale = numero casuale compreso tra 0 e il limite superiore - 1
				Integer randomIndexStato = JMetalRandom.getInstance().nextInt(0, upperBound - 1);
				// modifico il gene nella posizione casuale randomIndexGene con l'indice casuale di uno stato
				solution.variables().set(randomIndexGene, randomIndexStato);		        	
			}
		}
	}
}
