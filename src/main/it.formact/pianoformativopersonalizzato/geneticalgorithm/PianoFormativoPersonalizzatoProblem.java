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
	private ArrayList<Integer> punteggiIndividuoMigliore;
	private int geniSize;
	private Map<Integer, String> categorie;
	
	public PianoFormativoPersonalizzatoProblem (ArrayList<Stato> spazioStati, int geniSize, ArrayList<String> giorniLiberi, ArrayList<String> interessi, Map<Integer, String> categorie) {
		this.spazioStati = spazioStati;
		this.giorniLiberi = giorniLiberi;
		this.interessi = interessi;
		this.categorie = categorie;
		this.geniSize = geniSize;
				
		setName("Piano formativo personalizzato");
		setNumberOfVariables(spazioStati.size()); //
		List<Integer> lowerBounds = new ArrayList<>();
		List<Integer> upperBounds = new ArrayList<>();
		for (int i = 0; i < this.geniSize; i++) {
			lowerBounds.add(0);
			upperBounds.add(spazioStati.size() - 1);
		}
		setVariableBounds(lowerBounds, upperBounds);
		setNumberOfObjectives(1);
		
		this.punteggiIndividuoMigliore = new ArrayList<>();
		for (int i = 0; i < this.geniSize + 1; i++) {
			punteggiIndividuoMigliore.add(0);/*Da controllare*/
		}
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
	
	public ArrayList<Integer> getPunteggiIndividuoMigliore() {
		return punteggiIndividuoMigliore;
	}

	public void setPunteggiIndividuoMigliore(ArrayList<Integer> punteggiIndividuoMigliore) {
		this.punteggiIndividuoMigliore = punteggiIndividuoMigliore;
	}

	public int getGeniSize() {
		return geniSize;
	}

	public void setGeniSize(int geniSize) {
		this.geniSize = geniSize;
	}

	@Override
	public IntegerSolution evaluate(IntegerSolution integerSolution) {
		ArrayList<Integer> codifica = (ArrayList<Integer>) integerSolution.variables();
//		ArrayList<Integer> idPercorsiContenutoGeniControllati = new ArrayList<>();
		ArrayList<Integer> punteggiIndividuo = new ArrayList<>();
		ArrayList<String> nomiAccettati = new ArrayList<>();
		int punteggio = 0;
		int size = this.geniSize;
		int i = 0;
//		double costoTotale
		while(i < size) {
			int punteggioGene = 0;
			int gene = codifica.get(i);
			Stato contenutoGene = this.getSpazioStati().get(gene);
//			int idPercorsoContenutoGene = contenutoGene.getPercorsoFormativo().getId();
			String nomePercorso = contenutoGene.getPercorsoFormativo().getNome();
			
			Boolean risultato = false;
			
			if (i < 1) {
				risultato = false;
			}
			else {
				risultato = nomiAccettati.contains(nomePercorso);
//				String parola = "";
//				for (int j = 0; j < nomePercorso.length(); j++) {
//					String carattere = Character.toString(nomePercorso.charAt(j));
//					System.out.println("<" + carattere + ">");
//					if (carattere.equalsIgnoreCase(" ") == true) {
//						System.out.println("|" + parola + "|");
//						risultato = nomiAccettati.contains(parola);
//						if (risultato) {
//							break;
//						}
//						parola = "";
//					}
//					else {
//						parola = parola + carattere;
//					}
//				}
//				if (risultato == false) {
//					risultato = nomiAccettati.contains(parola);
//				}
				
			}
			
//			idPercorsiContenutoGeniControllati.add(idPercorsoContenutoGene);
			
			if (risultato == false) {
				nomiAccettati.add(nomePercorso);
				punteggioGene = this.calcolaPunteggioGene(gene, i);
				punteggiIndividuo.add(punteggioGene);
				punteggio += punteggioGene;
			}
//			else {
//				punteggio -= 10;
//			}
			
			i++;
		}
		punteggiIndividuo.add(punteggio);
		if (punteggiIndividuo.get(punteggiIndividuo.size() - 1) > punteggiIndividuoMigliore.get(punteggiIndividuoMigliore.size() - 1)) {
			punteggiIndividuoMigliore = punteggiIndividuo;
		}
		System.out.println(punteggiIndividuoMigliore);
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
			//String areaInteresse = "";
			
			//ArrayList<String> interesseAndArea = this.getInteresseAndArea(interesse);
			//interesse = interesseAndArea.get(0);
			//areaInteresse = interesseAndArea.get(1);
			
			//System.out.println(interesse + "\n" + areaInteresse);
			
			// mi calcolo il punteggio dato al nome del percorso formativo 
			// tramite gli interessi:
			risultatoInteresse = nome.contains(interesse);
			if (risultatoInteresse) {
				punteggio += 10;
			}
			
			// mi calcolo il punteggio dato all'indice dei contenuti del percorso formativo 
			// tramite gli interessi:
			risultatoInteresse = indiceContenuti.contains(interesse);
			if (risultatoInteresse) {
				punteggio += 5;
			}
			
			// mi calcolo il punteggio dato alla categoria del percorso formativo
			// tramite gli interessi:
			risultatoCategoria = nomeCategoria.contains(interesse);
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













