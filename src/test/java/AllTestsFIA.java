import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import control.PianoFormativoServletTest;
import geneticalgorithm.GeneticAlgorithmTest;
import geneticalgorithm.PianoFormativoProblemTest;
import geneticalgorithm.SoluzioneTest;
import geneticalgorithm.StatoTest;
import geneticalgorithm.UniformCrossoverTest;
import services.PianoFormativoServiceTest;

@RunWith(Suite.class)
@SuiteClasses({
	UniformCrossoverTest.class, StatoTest.class, SoluzioneTest.class, GeneticAlgorithmTest.class,
	PianoFormativoProblemTest.class, PianoFormativoServiceTest.class, PianoFormativoServletTest.class
})

public class AllTestsFIA {
		
	public AllTestsFIA () {
		super();
	}
	
}









