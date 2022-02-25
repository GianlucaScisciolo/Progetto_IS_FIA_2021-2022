package pianoformativopersonalizzato.service;

import org.uma.jmetal.operator.mutation.MutationOperator;
import org.uma.jmetal.solution.binarysolution.BinarySolution;
import org.uma.jmetal.solution.doublesolution.DoubleSolution;
import org.uma.jmetal.solution.integersolution.IntegerSolution;
import org.uma.jmetal.util.JMetalException;
import org.uma.jmetal.util.checking.Check;
import org.uma.jmetal.util.pseudorandom.JMetalRandom;
import org.uma.jmetal.util.pseudorandom.RandomGenerator;

@SuppressWarnings("serial")
public class RandomResettingMutation implements MutationOperator<IntegerSolution> {
	private double mutationProbability;
	private RandomGenerator<Double> randomGenerator;
	
	public RandomResettingMutation(double probability) {
		this(probability, () -> JMetalRandom.getInstance().nextDouble());
	}
	
	/** Constructor */
	public RandomResettingMutation(double probability, RandomGenerator<Double> randomGenerator) {
		if (probability < 0) {
			throw new JMetalException("Mutation probability is negative: " + mutationProbability);
		}
		
		this.mutationProbability = probability;
		this.randomGenerator = randomGenerator;
	}
	
	/* Getters */
	@Override
	public double getMutationProbability() {
		return mutationProbability;
	}
	
	/* Setters */
	public void setMutationProbability(double mutationProbability) {
		this.mutationProbability = mutationProbability;
	}
	
	@Override
	public IntegerSolution execute(IntegerSolution solution) throws JMetalException {
		if (null == solution) {
			throw new JMetalException("Null parameter");
	    }
		
		doMutation(mutationProbability, solution);
		
		return solution;
	}
	

	/** Implements the mutation operation */
	private void doMutation(double probability, IntegerSolution solution) {
		for (int i = 0; i < solution.getNumberOfVariables(); i++) {
			if (randomGenerator.getRandomValue() <= probability) {
				Double value =
						solution.getLowerBound(i)
							+ ((solution.getUpperBound(i) - solution.getLowerBound(i))
									* randomGenerator.getRandomValue());
				Integer valueInt = value.intValue();
				solution.setVariable(i, valueInt);
			}
		}
	}
}