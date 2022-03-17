package pianoformativopersonalizzato.geneticalgorithm.fix;

import java.util.ArrayList;

import org.uma.jmetal.solution.integersolution.IntegerSolution;
import org.uma.jmetal.util.bounds.Bounds;

public interface MyIntegerSolution extends IntegerSolution {
	ArrayList<Integer> scores = new ArrayList<>();
	
//	default Bounds<Integer> getBounds(int index) {
//	    IntegerSolution solution = this;
//	    Integer lowerBound = solution.getLowerBound(index);
//	    Integer upperBound = solution.getUpperBound(index);
//	    return Bounds.create(lowerBound, upperBound);
//	  }
}
