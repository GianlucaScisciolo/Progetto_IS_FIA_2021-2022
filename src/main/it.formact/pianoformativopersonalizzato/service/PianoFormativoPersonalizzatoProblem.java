package pianoformativopersonalizzato.service;

import java.util.ArrayList;
import java.util.List;

import org.uma.jmetal.problem.integerproblem.impl.AbstractIntegerProblem;
import org.uma.jmetal.solution.integersolution.IntegerSolution;

public class PianoFormativoPersonalizzatoProblem extends AbstractIntegerProblem {
	
	private ArrayList<Individuo> individui;
	private ArrayList<Stato> spazioStati;
	
	
	public PianoFormativoPersonalizzatoProblem () {
		
	}
	
	public PianoFormativoPersonalizzatoProblem (ArrayList<Stato> spazioStati, int geniSize) {
		this.spazioStati = spazioStati;
		setName("Piano formativo personalizzato");
		setNumberOfVariables(spazioStati.size());
		List<Integer> lowerBounds = new ArrayList<>();
		List<Integer> upperBounds = new ArrayList<>();
		for (int i = 0; i < geniSize; i++) {
			lowerBounds.add(0);
			upperBounds.add(spazioStati.size() - 1);
		}
		setVariableBounds(lowerBounds, upperBounds);
		setNumberOfObjectives(1);
	}
	
	/*
	public void generaIndividui (ArrayList<Stato> spazioStati, int popolazioneSize) {
		ArrayList<Individuo> individui = new ArrayList<Individuo>();
		for (int i = 0; i < popolazioneSize; i++) {
			Individuo individuo = new Individuo();
			individuo.generaCodifica(spazioStati);
			individui.add(individuo);
		}
		this.individui = individui;
	}
	*/

	@Override
	public void evaluate(IntegerSolution solution) {
		// TODO Auto-generated method stub
		
	}
	
}
