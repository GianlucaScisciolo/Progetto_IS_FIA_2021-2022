package pianoformativopersonalizzato.service.fix;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.uma.jmetal.operator.crossover.CrossoverOperator;
import org.uma.jmetal.solution.integersolution.IntegerSolution;
import org.uma.jmetal.util.errorchecking.Check;
import org.uma.jmetal.util.errorchecking.JMetalException;
import org.uma.jmetal.util.pseudorandom.JMetalRandom;
import org.uma.jmetal.util.pseudorandom.RandomGenerator;

public class IntegerSolutionUniformCrossover implements CrossoverOperator<IntegerSolution> {
	
	  private double crossoverProbability;
	  private RandomGenerator<Double> crossoverRandomGenerator;

	  /** Constructor */
	  public IntegerSolutionUniformCrossover(double crossoverProbability) {
	    this(crossoverProbability, () -> JMetalRandom.getInstance().nextDouble());
	  }
	  
	  /** Constructor */
	  public IntegerSolutionUniformCrossover(double crossoverProbability, RandomGenerator<Double> crossoverRandomGenerator) {
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
//	    System.out.println("Crossover:");
//	    System.out.print(solutions.get(0));
//	    System.out.print(solutions.get(1));
	    
	    List<IntegerSolution> solutionsCrossover = 
	    		doCrossover(crossoverProbability, solutions.get(0), solutions.get(1));
//	    System.out.println("--------------------------");
//	    System.out.print(solutionsCrossover.get(0));
//	    System.out.print(solutionsCrossover.get(1));
//	    System.out.println("--------------------------");
//	    System.out.println();
//	    System.out.println(solutions.size());
	    
//	    System.out.println("Pre:");
//	    for (int i = 0; i < solutions.size(); i++) {
//	    	System.out.print(solutions.get(i));
//	    }
//	    System.out.println("Post:");
//	    for (int i = 0; i < solutionsCrossover.size(); i++) {
//	    	System.out.print(solutionsCrossover.get(i));
//	    }
	    
	    return solutionsCrossover;
	  }
	
	
	
	private List<IntegerSolution> doCrossover(double probability, IntegerSolution parent1, IntegerSolution parent2) {
		
		List<IntegerSolution> offspring = new ArrayList<>(2);
	    offspring.add((IntegerSolution) parent1.copy());
	    offspring.add((IntegerSolution) parent2.copy());
		
	    if (crossoverRandomGenerator.getRandomValue() <= probability) {	    	
	    	Random random = new Random();
	    	for (int i = 0; i < parent1.variables().size(); i++) {
	    		int geneParent1 = parent1.variables().get(i);
	    		int geneParent2 = parent2.variables().get(i);
	    			    		
	    		// numero casuale: 0 oppure 1
	    		int randomNumber = random.nextInt(2);
//	    		System.out.println(randomNumber);
	    		
	    		if (randomNumber == 0) {
	    			offspring.get(0).variables().set(i, geneParent1);
	    			offspring.get(1).variables().set(i, geneParent2);	    			
	    		}
	    		else {
	    			offspring.get(0).variables().set(i, geneParent2);
	    			offspring.get(1).variables().set(i, geneParent1);
	    		}
	    	}
	    }
	    
//	    System.out.print(offspring.get(0));
//	    System.out.print(offspring.get(1));
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
