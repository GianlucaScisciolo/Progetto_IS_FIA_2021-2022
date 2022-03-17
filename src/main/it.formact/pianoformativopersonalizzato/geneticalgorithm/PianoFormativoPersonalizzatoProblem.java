package pianoformativopersonalizzato.geneticalgorithm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.uma.jmetal.problem.integerproblem.impl.AbstractIntegerProblem;
import org.uma.jmetal.solution.integersolution.IntegerSolution;

import model.entity.CategoriaEntity;
import model.entity.PercorsoFormativoEntity;


public class PianoFormativoPersonalizzatoProblem extends AbstractIntegerProblem {
	
	private ArrayList<Individuo> individui;
	private ArrayList<Stato> spazioStati;
	private ArrayList<String> giorniLiberi; 
	private ArrayList<String> interessi;
	private Map<Integer, String> categorie;
	
	public PianoFormativoPersonalizzatoProblem (ArrayList<Stato> spazioStati, int geniSize, ArrayList<String> giorniLiberi, ArrayList<String> interessi, Map<Integer, String> categorie) {
		this.spazioStati = spazioStati;
		this.giorniLiberi = giorniLiberi;
		this.interessi = interessi;
		this.categorie = categorie;
				
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

	public Map<Integer, String> getCategorie() {
		return categorie;
	}

	public void setCategorie(Map<Integer, String> categorie) {
		this.categorie = categorie;
	}
	
	@Override
	public IntegerSolution evaluate(IntegerSolution integerSolution) {
		ArrayList<Integer> codifica = (ArrayList<Integer>) integerSolution.variables();
		ArrayList<Integer> idPercorsiContenutoGeniControllati = new ArrayList<>();
		int punteggio = 0;
		int size = 10;
		int i = 0;
		while(i < size) {
			int gene = codifica.get(i);
			Stato contenutoGene = this.getSpazioStati().get(gene);
			int idPercorsoContenutoGene = contenutoGene.getPercorsoFormativo().getId();
			Boolean duplicato;
			
			if (i < 1) {
				duplicato = false;
			}
			else {
				duplicato = idPercorsiContenutoGeniControllati.contains(idPercorsoContenutoGene);
			}
			
			idPercorsiContenutoGeniControllati.add(idPercorsoContenutoGene);
			
			if (duplicato == false) {
				punteggio += this.calcolaPunteggioGene(gene, i);
			}
//			else {
//				punteggio -= 10;
//			}
			
			i++;
		}
		integerSolution.objectives()[0] = -punteggio;
		return integerSolution;
	}
	
	private int calcolaPunteggioGene (Integer gene, int indice) {
		
		int punteggio = 15;
		
		Stato stato = spazioStati.get(gene);
		PercorsoFormativoEntity percorsoFormativo = stato.getPercorsoFormativo();
		
		String nome = percorsoFormativo.getNome().toLowerCase();
		String indiceContenuti = percorsoFormativo.getIndice_contenuti().toLowerCase();
		Integer idCategoria = stato.getPercorsoFormativo().getCategoria();
		String nomeCategoria = categorie.get(idCategoria).toLowerCase();
		
		boolean risultatoInteresse = false;
		boolean risultatoCategoria = false;
		
		for (int i = 0; i < interessi.size(); i++) {
			String interesse = interessi.get(i).toLowerCase();
			String areaInteresse = "";
			
			ArrayList<String> interesseAndArea = this.getInteresseAndArea(interesse);
			interesse = interesseAndArea.get(0);
			areaInteresse = interesseAndArea.get(1);
			
			System.out.println(interesse + "\n" + areaInteresse);
			
			// mi calcolo il punteggio dato al nome del percorso formativo 
			// tramite gli interessi:
			risultatoInteresse = nome.contains(interesse);
			if (risultatoInteresse) {
				punteggio += 15;
			}
			
			// mi calcolo il punteggio dato all'indice dei contenuti del percorso formativo 
			// tramite gli interessi:
			risultatoInteresse = indiceContenuti.contains(interesse);
			if (risultatoInteresse) {
				punteggio += 10;
			}
			
			// mi calcolo il punteggio dato alla categoria del percorso formativo
			// tramite gli interessi:
			risultatoCategoria = nomeCategoria.contains(areaInteresse);
			if (risultatoCategoria) {
				punteggio += 5;
			}
		}
		
		return punteggio;
		
	}
	
//	private void orderSolution (IntegerSolution solution) {
//		if (solution == null || solution.variables().size() <= 0) {
//			new IllegalStateException("Errore, codifica vuota");
//		}
//		
//		ArrayList<Integer> geni = (ArrayList<Integer>) solution.variables();
//		ArrayList<Stato> contenutoGeni = new ArrayList<>();
//		
//		for (int i = 0; i < geni.size(); i++) {
//			int gene = geni.get(i);
//			contenutoGeni.add(this.getSpazioStati().get(gene));
//		}
//		String str;
//		contenutoGeni.sort(null);
//	}
	
	public ArrayList<String> getInteresseAndArea (String interesse) {
		
		ArrayList<String> interesseAndArea = new ArrayList<>();
		String areaInteresse = "";
		
        int posizioneParentesi = interesse.indexOf("(");
        if (posizioneParentesi != -1) {
        	areaInteresse = interesse.substring(posizioneParentesi + 1, interesse.length() - 1);
        	interesse = interesse.substring(0, posizioneParentesi - 1);
        }
        else {
        	areaInteresse = interesse;
        }
        
        interesseAndArea.add(interesse);
        interesseAndArea.add(areaInteresse);
        
        return interesseAndArea;
	}
	
}













