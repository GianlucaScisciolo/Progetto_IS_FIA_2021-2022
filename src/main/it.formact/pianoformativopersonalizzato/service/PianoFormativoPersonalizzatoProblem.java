package pianoformativopersonalizzato.service;

import java.util.ArrayList;
import java.util.List;

import org.uma.jmetal.problem.integerproblem.impl.AbstractIntegerProblem;
import org.uma.jmetal.solution.integersolution.IntegerSolution;

import model.entity.CategoriaEntity;
import model.entity.PercorsoFormativoEntity;

public class PianoFormativoPersonalizzatoProblem extends AbstractIntegerProblem {
	
	private ArrayList<Individuo> individui;
	private ArrayList<Stato> spazioStati;
	private ArrayList<String> giorniLiberi; 
	private ArrayList<String> interessi;
	private ArrayList<CategoriaEntity> categorie;
	
	
	public PianoFormativoPersonalizzatoProblem () {
		
	}
	
	public PianoFormativoPersonalizzatoProblem (ArrayList<Stato> spazioStati, int geniSize) {
		this.spazioStati = spazioStati;
		setName("Piano formativo personalizzato");
		setNumberOfVariables(spazioStati.size()); //
		List<Integer> lowerBounds = new ArrayList<>();
		List<Integer> upperBounds = new ArrayList<>();
		for (int i = 0; i < geniSize; i++) {
			lowerBounds.add(0);
			upperBounds.add(spazioStati.size() - 1);
		}
		setVariableBounds(lowerBounds, upperBounds);
		setNumberOfObjectives(1);
	}
	
	public ArrayList<Individuo> getIndividui() {
		return individui;
	}

	public void setIndividui(ArrayList<Individuo> individui) {
		this.individui = individui;
	}

	public ArrayList<Stato> getSpazioStati() {
		return spazioStati;
	}

	public void setSpazioStati(ArrayList<Stato> spazioStati) {
		this.spazioStati = spazioStati;
	}

	public ArrayList<String> getGiorniLiberi() {
		return giorniLiberi;
	}

	public void setGiorniLiberi(ArrayList<String> giorniLiberi) {
		this.giorniLiberi = giorniLiberi;
	}

	public ArrayList<String> getInteressi() {
		return interessi;
	}

	public void setInteressi(ArrayList<String> interessi) {
		this.interessi = interessi;
	}

	public ArrayList<CategoriaEntity> getCategorie() {
		return categorie;
	}

	public void setCategorie(ArrayList<CategoriaEntity> categorie) {
		this.categorie = categorie;
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
	public void evaluate(IntegerSolution integerSolution) {
		ArrayList<Integer> codifica = (ArrayList<Integer>) integerSolution.getVariables();
		ArrayList<Integer> geniControllati = new ArrayList<>();
		int punteggio = 0;
		int size = 10;
		int i = 0;
		while(i < size) {
			Integer gene = codifica.get(i);
			Boolean duplicato;
			
			if (i == 0) {
				duplicato = false;
			}
			else {
				duplicato = geniControllati.contains(gene);
			}
			
			geniControllati.add(gene);
			
			if (duplicato == false) {
				punteggio += this.calcolaPunteggioGene(gene);
			}
			
			i++;
		}
		integerSolution.getObjectives()[0] = punteggio;
	}
	
	private int calcolaPunteggioGene (Integer gene) {
		
		Stato stato = spazioStati.get(gene);
		String indiceContenuti = stato.getPercorsoFormativo().getIndice_contenuti();
		int categoria = stato.getPercorsoFormativo().getCategoria();
		double costo = stato.getPercorsoFormativo().getCosto();
		
		int valoreInteresse = 5;
		int valoreCategoria = 5;
		int valoreCosto = 5;
		
		String regexInteresse = "";
		String regexCategoria = "/(" + categoria + ")/g";
		
		Boolean risultatoInteresse = false;
		Boolean risultatoCategoria = false;
		
		int punteggio = 0;
		for (int i = 0; i < interessi.size(); i++) {
			regexInteresse = "/" + interessi.get(i) + "/g";
						
			if (risultatoInteresse == false) {
				risultatoInteresse = indiceContenuti.matches(regexInteresse);
				if (risultatoInteresse == true) { 
					valoreInteresse += 5;
				}
			}
			
			if (risultatoCategoria == false) {
				risultatoCategoria = interessi.get(i).matches(regexCategoria);
				if (risultatoCategoria == true) {
					valoreCategoria += 5;
				}
			}
			
			if (risultatoInteresse == true && risultatoCategoria == true) {
				break;
			}
		}
		
		if (costo <= 20.00) {
			valoreCosto += 5;
		}
		
		
		
		punteggio += valoreInteresse + valoreCategoria + valoreCosto;
		
		return punteggio;
		
	}

}
