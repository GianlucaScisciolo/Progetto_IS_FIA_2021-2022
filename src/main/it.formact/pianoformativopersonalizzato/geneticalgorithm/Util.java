package pianoformativopersonalizzato.geneticalgorithm;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import model.entity.PercorsoFormativoEntity;

public class Util {
	
	public static ArrayList<Stato> generaCodifica(int numeroGeni) {
		ArrayList<String> giorniSettimana = new ArrayList<String>(
				Arrays.asList("lunedì","martedì","mercoledì","giovedì","venerdì")
		);
		PercorsoFormativoEntity percorso = new PercorsoFormativoEntity();
		Random random = new Random();
		ArrayList<Stato> codifica = new ArrayList<>();
		
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
		
		return codifica;
	}
	
}
