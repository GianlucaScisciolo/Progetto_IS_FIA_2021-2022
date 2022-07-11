package pianoformativopersonalizzato.geneticalgorithm;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import model.entity.PercorsoFormativoEntity;

/**
 * Questa classe contiene una serie di metodi utili per la creazione e gestione dell'algoritmo genetico.
 * 
 * @author GIANLUCA
 *
 */
public class Util {
	
	/**
	 * Il seguente metodo genera una codifica degli individui.
	 * 
	 * @param numeroGeni: il numero di geni della codofica di ogni individuo.
	 * @return
	 */
	public static ArrayList<Stato> generaCodifica(int numeroGeni) {
		// Array contenente i giorni della settimana che vanno dal lunedì al venerdì.
		ArrayList<String> giorniSettimana = new ArrayList<String>(
				Arrays.asList("lunedì","martedì","mercoledì","giovedì","venerdì")
		);
		PercorsoFormativoEntity percorso = new PercorsoFormativoEntity();
		Random random = new Random();
		ArrayList<Stato> codifica = new ArrayList<>();
		
		// Creo dei geni completamente casuali.
		for (int i = 0; i < numeroGeni; i++) {
			String giorno = giorniSettimana.get(random.nextInt(5));
			
			int ora = random.nextInt(19);
			int minuto = 0;
			if (ora < 18) {
				minuto = random.nextInt(31);
			}
			
			String oraString = Integer.toString(ora), minutoString = Integer.toString(minuto);
			if (ora < 10) {
				oraString = "0" + oraString;
			}
			if (minuto < 10) {
				minutoString = "0" + minutoString;
			}
			LocalTime orario = LocalTime.parse(oraString + ":" + minutoString);
			
			codifica.add(new Stato(percorso, giorno, orario));
		}
		
		// codifica: array di stati (individuo). Ogni stato è un gene.
		return codifica;
	}
	
}
