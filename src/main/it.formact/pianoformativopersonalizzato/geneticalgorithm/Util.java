package pianoformativopersonalizzato.geneticalgorithm;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import model.entity.PercorsoFormativoEntity;
import pianoformativopersonalizzato.geneticalgorithm.Stato;



/**
 * 
 * Questa classe contiene una serie di metodi utili per la creazione e gestione dell'algoritmo genetico.
 * 
 * @author GianlucaScisciolo
 *
 */
public class Util {
//	
//	/**
//	 * Il seguente metodo genera una codifica pseudo-casuale degli individui.
//	 * 
//	 * @param numeroGeni: numero di geni della codifica
//	 * @return una codifica pseudo-casuale
//	 */
//	public static ArrayList<Stato> generaCodifica(int numeroGeni) {
//		// Array contenente i giorni della settimana che vanno dal lunedì al venerdì.
//		ArrayList<String> giorniSettimana = new ArrayList<String>(
//				Arrays.asList("lunedì","martedì","mercoledì","giovedì","venerdì")
//		);
//		
//		PercorsoFormativoEntity percorso = new PercorsoFormativoEntity();
//		Random random = new Random();
//		ArrayList<Stato> codifica = new ArrayList<>();
//		
//		// Creo dei geni completamente pseudo-casuali.
//		for (int i = 0; i < numeroGeni; i++) {
//			// giorno pseudo-casuale tra lunedì e venerdì
//			String giorno = giorniSettimana.get(random.nextInt(5));
//			
//			// ora pseudo-casuale tra 0 e 18
//			int ora = random.nextInt(19);
//			
//			// minuto pseudo-casuale:
//			// - se ora == 18 allora minuto = 0
//			// - se ora < 18 allora 0 < minuto < 31
//			int minuto = 0;
//			if (ora < 18) {
//				minuto = random.nextInt(31);
//			}
//			
//			// converto l'ora e il minuto in 2 stringhe
//			String oraString    = Integer.toString(ora); 
//			String minutoString = Integer.toString(minuto);
//			
//			// se ora < 10 allora aggiungo uno 0 all'inizio della stringa ora
//			if (ora < 10) {
//				oraString = "0" + oraString;
//			}
//			
//			// se minuto < 10 allora aggiungo uno 0 all'inizio della stringa minuto
//			if (minuto < 10) {
//				minutoString = "0" + minutoString;
//			}
//			
//			// sia:
//			// - oo = ora;
//			// - mm = minuto
//			// allora creo un orario del tipo: orario = oo:mm
//			LocalTime orario = LocalTime.parse(oraString + ":" + minutoString);
//			
//			// Il percorso è un percorso vuoto. Da vedere e controllare
//			
//			// aggiungo alla codifica lo stato pseudo-casuale appena creato
//			codifica.add(new Stato(percorso, giorno, orario));
//		}
//		
//		// ritorno la codifica
//		return codifica;
//	}
//	
}
