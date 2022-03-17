package pianoformativopersonalizzato.geneticalgorithm.test;

import org.uma.jmetal.operator.crossover.CrossoverOperator;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.solution.integersolution.IntegerSolution;

//import cilabo.gbml.operator.crossover.UniformCrossover;
import model.dao.CategoriaDao;
import model.dao.InteresseDao;
import model.entity.CategoriaEntity;
import model.entity.PercorsoFormativoEntity;
import modelfia.dao.PianoFormativoPersonalizzatoDao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pianoformativopersonalizzato.geneticalgorithm.PianoFormativoPersonalizzatoProblem;
import pianoformativopersonalizzato.geneticalgorithm.Stato;
import pianoformativopersonalizzato.geneticalgorithm.fix.IntegerSolutionUniformCrossover;

public class TestIntegerSolutionUniformCrossover {
	public static void main (String [] args) throws SQLException {
		PianoFormativoPersonalizzatoDao pfpDao = new PianoFormativoPersonalizzatoDao();
		InteresseDao iDao = new InteresseDao();
		CategoriaDao cDao = new CategoriaDao();
		
		ArrayList<String> giorniLiberi = new ArrayList<>();
		giorniLiberi.add("lunedì");
		int geniSize = 10;
		//InteresseEntity interesse1 = (InteresseEntity) iDao.doRetrieveByKey(1);
		//InteresseEntity interesse2 = (InteresseEntity) iDao.doRetrieveByKey(2);
		ArrayList<String> interessi = new ArrayList<String>();
		interessi.add("Java");
		interessi.add("Studiare");
		
		Map<Integer, String> categorie = new HashMap<Integer, String>();
		categorie.put(4, "Matematica");
		categorie.put(2, "Linguaggi di programmazione");
		
		PercorsoFormativoEntity pfe = new PercorsoFormativoEntity();
		
		pfe.setId(1);
		Stato stato1 = new Stato();
		stato1.setPercorsoFormativo(pfe);
		pfe.setId(2);
		Stato stato2 = new Stato();
		pfe.setId(3);
		Stato stato3 = new Stato();
		pfe.setId(4);
		Stato stato4 = new Stato();
		pfe.setId(5);
		Stato stato5 = new Stato();
		pfe.setId(6);
		Stato stato6 = new Stato();
		pfe.setId(7);
		Stato stato7 = new Stato();
		pfe.setId(8);
		Stato stato8 = new Stato();
		pfe.setId(9);
		Stato stato9 = new Stato();
		pfe.setId(10);
		Stato stato10 = new Stato();
		pfe.setId(11);
		Stato stato11 = new Stato();
		pfe.setId(12);
		Stato stato12 = new Stato();
		pfe.setId(13);
		Stato stato13 = new Stato();
		pfe.setId(14);
		Stato stato14 = new Stato();
		pfe.setId(15);
		Stato stato15 = new Stato();
		pfe.setId(16);
		Stato stato16 = new Stato();
		pfe.setId(17);
		Stato stato17 = new Stato();
		pfe.setId(18);
		Stato stato18 = new Stato();
		pfe.setId(19);
		Stato stato19 = new Stato();
		pfe.setId(20);
		Stato stato20 = new Stato();
		ArrayList<Stato> spazioStati = new ArrayList<>();
		spazioStati.add(stato1);
		spazioStati.add(stato2);
		spazioStati.add(stato3);
		spazioStati.add(stato4);
		spazioStati.add(stato5);
		spazioStati.add(stato6);
		spazioStati.add(stato7);
		spazioStati.add(stato8);
		spazioStati.add(stato9);
		spazioStati.add(stato10);
		spazioStati.add(stato11);
		spazioStati.add(stato12);
		spazioStati.add(stato13);
		spazioStati.add(stato14);
		spazioStati.add(stato15);
		spazioStati.add(stato16);
		spazioStati.add(stato17);
		spazioStati.add(stato18);
		spazioStati.add(stato19);
		spazioStati.add(stato20);
		
		Problem<IntegerSolution> problem = 
			new PianoFormativoPersonalizzatoProblem(spazioStati, geniSize, giorniLiberi, interessi, categorie);
		
		IntegerSolution parent1 = problem.createSolution();
		IntegerSolution parent2 = problem.createSolution();
		
		
		List<IntegerSolution> solutions = new ArrayList<>();
		solutions.add(parent1);
		solutions.add(parent2);
		
		double probability = 1.0;
		double probabilitaCrossover = 0.8;
		CrossoverOperator<IntegerSolution> crossover = new IntegerSolutionUniformCrossover(probabilitaCrossover);
		//CrossoverOperator<IntegerSolution> crossover = new IntegerSBXCrossover(probabilitaCrossover, 5);
		List<IntegerSolution> offspring = crossover.execute(solutions);
		System.out.print("Parent 1: " + parent1.toString());
		System.out.print("Parent 2 : " + parent2.toString());
		System.out.print("Child 1: " + offspring.get(0));
		System.out.print("Child 2: " +offspring.get(1));
	}
	
	
}







