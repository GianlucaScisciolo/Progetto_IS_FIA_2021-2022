package pianoformativopersonalizzato.service.fix;

import java.util.Random;

import org.uma.jmetal.operator.mutation.MutationOperator;
import org.uma.jmetal.solution.integersolution.IntegerSolution;
import org.uma.jmetal.util.bounds.Bounds;
import org.uma.jmetal.util.errorchecking.JMetalException;
import org.uma.jmetal.util.pseudorandom.JMetalRandom;
import org.uma.jmetal.util.pseudorandom.RandomGenerator;

public class IntegerSolutionRandomResettingMutation implements MutationOperator<IntegerSolution> {
	  private double mutationProbability;
	  private RandomGenerator<Double> randomGenerator;

	  /** Constructor */
	  public IntegerSolutionRandomResettingMutation(double probability) {
		  this(probability, () -> JMetalRandom.getInstance().nextDouble());
	  }
	  
	  /** Constructor */
	  public IntegerSolutionRandomResettingMutation(double probability, RandomGenerator<Double> randomGenerator) {
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
	  public IntegerSolution execute(IntegerSolution solution) {
		  if (null == solution) {
		      throw new JMetalException("Null parameter");
		    }

		    doMutation(mutationProbability, solution);
		    
//			System.out.print("Mutation:");
//			System.out.print(solution);
//			System.out.println();
		    
		    return solution;
	  }

	private void doMutation(double probability, IntegerSolution solution) {
		//System.out.println("Pre:");
		//System.out.print(solution);
		
		Random randomNumber = new Random();
		//for (int i = 0; i < solution.variables().size(); i++) {
			if (randomGenerator.getRandomValue() <= probability) {
				Bounds<Integer> bounds = solution.getBounds(0);
				Integer lowerBound = bounds.getLowerBound();
		        Integer upperBound = bounds.getUpperBound();
		        for (int i = 0; i < 3; i++) {
		        	Integer randomIndexGene = randomNumber.nextInt(solution.variables().size());
		        	Integer randomIndexStati = randomNumber.nextInt(upperBound);
		        	solution.variables().set(randomIndexGene, randomIndexStati);		        	
		        }
//		        System.out.println(
//		        		  "lowerBound = " + lowerBound + "\n"
//		        		+ "upperBound = " + upperBound + "\n"
//		        		+ "randomIndexGene = " + randomIndexGene + "\n"
//		        		+ "randomIndexStati = " + randomIndexStati
//		        );
		        
			}
		//}
		//System.out.println("Post:");
		//System.out.print(solution);
	}
}
