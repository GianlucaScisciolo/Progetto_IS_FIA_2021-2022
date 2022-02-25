package pianoformativopersonalizzato.service;

import org.uma.jmetal.operator.crossover.CrossoverOperator;
import org.uma.jmetal.solution.binarysolution.BinarySolution;
import org.uma.jmetal.solution.integersolution.IntegerSolution;

import pianoformativopersonalizzato.service.Check;
import pianoformativopersonalizzato.service.JMetalException;
import org.uma.jmetal.util.pseudorandom.JMetalRandom;
import org.uma.jmetal.util.pseudorandom.RandomGenerator;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class IntegerUniformCrossover implements CrossoverOperator<IntegerSolution> {
	private double crossoverProbability;
	private RandomGenerator<Double> crossoverRandomGenerator;

//	/** Constructor */
//	public IntegerUniformCrossover(double crossoverProbability Object object) {
//		this(crossoverProbability, () -> JMetalRandom.getInstance().nextDouble());
//	}

/** Constructor */
  public IntegerUniformCrossover (double crossoverProbability, RandomGenerator<Double> crossoverRandomGenerator) {
    if (crossoverProbability < 0) {
      throw new JMetalException("Crossover probability is negative: " + crossoverProbability);
    }
    this.crossoverProbability = crossoverProbability;
    this.crossoverRandomGenerator = crossoverRandomGenerator;
  }

  /* Getter */
  @Override
  public double getCrossoverProbability() {
    return crossoverProbability;
  }

  /* Setter */
  public void setCrossoverProbability(double crossoverProbability) {
    this.crossoverProbability = crossoverProbability;
  }

  @Override
  public List<IntegerSolution> execute(List<IntegerSolution> solutions) {
    Check.notNull(solutions);
    Check.that(solutions.size() == 2, "There must be two parents instead of " + solutions.size());

    return doCrossover(crossoverProbability, solutions.get(0), solutions.get(1));
  }

  /**
   * Perform the crossover operation.
   *
   * @param probability Crossover setProbability
   * @param parent1 The first parent
   * @param parent2 The second parent
   * @return An array containing the two offspring
   */
  public List<IntegerSolution> doCrossover(double probability, IntegerSolution parent1, IntegerSolution parent2) {
	  List<IntegerSolution> offspring = new ArrayList<>(2);
	  offspring.add((IntegerSolution) parent1.copy());
	  offspring.add((IntegerSolution) parent2.copy());
	  
	  BinarySolution b = null;
	  int i = 0;
	  	  
	  if (crossoverRandomGenerator.getRandomValue() < probability) {
      for (int variableIndex = 0; variableIndex < parent1.getVariables().size(); variableIndex++) {
        for (int integerIndex = 0;
            //bitIndex < parent1.getVariables().get(variableIndex).getBinarySetLength();
        		integerIndex < parent1.getVariables().get(variableIndex);
        		integerIndex++) {
          if (crossoverRandomGenerator.getRandomValue() < 0.5) {
            offspring
                .get(0)
                .getVariables().get(variableIndex)
                .set(bitIndex, parent2.getVariables().get(variableIndex).get(bitIndex));
            offspring
                .get(1)
                .getVariables().get(variableIndex)
                .set(bitIndex, parent1.getVariables().get(variableIndex).get(bitIndex));
          }
        }
      }
    }
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