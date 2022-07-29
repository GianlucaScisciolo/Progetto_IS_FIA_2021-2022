package pianoformativopersonalizzato.geneticalgorithm;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;

import model.entity.PercorsoFormativoEntity;
import pianoformativopersonalizzato.geneticalgorithm.Stato;

/**
 * 
 * Classe che rappresenta uno stato
 * 
 * @author GianlucaScisciolo
 *
 */
public class Stato implements Comparable<Stato> {
	private PercorsoFormativoEntity percorsoFormativo;
	private String giorno;
	private LocalTime orario;
	private int punteggio;
	
	/**
	 * Costruttore della classe Stato
	 */
	public Stato() {
		this.percorsoFormativo = new PercorsoFormativoEntity();
		this.giorno = "";
		this.orario = null;
		this.punteggio = 0;
	}
	
	/**
	 * Costruttore della classe Stato.
	 * 
	 * @param percorsoFormativo: percorso formativo
	 * @param giorno: uno dei giorni in cui viene insegnato il percorso formativo
	 * @param orario: un orario del giorno
	 */
	public Stato (PercorsoFormativoEntity percorsoFormativo, String giorno, LocalTime orario) {
		this.percorsoFormativo = percorsoFormativo;
		this.giorno = giorno;
		this.orario = orario;
		this.punteggio = 0;
	}
	
	/**
	 * Metodo che restituisce il percorso formativo dello stato
	 * 
	 * @return il percorso formativo dello stato
	 */
	public PercorsoFormativoEntity getPercorsoFormativo() {
		return percorsoFormativo;
	}
	
	/**
	 * Metodo che modifica il percorso formativo dello stato
	 * 
	 * @param percorsoFormativo: nuovo percorso formativo dello stato
	 */
	public void setPercorsoFormativo(PercorsoFormativoEntity percorsoFormativo) {
		this.percorsoFormativo = percorsoFormativo;
	}

	/**
	 * Metodo che ritorna il giorno dello stato
	 * 
	 * @return il giorno dello stato
	 */
	public String getGiorno() {
		return giorno;
	}
	
	/**
	 * Metodo che modifica il giorno dello stato.
	 * 
	 * @param giorno: nuovo giorno dello stato.
	 */
	public void setGiorno(String giorno) {
		this.giorno = giorno;
	}
	
	/**
	 * Metodo che ritorna l'orario dello stato
	 * 
	 * @return l'orario dello stato
	 */
	public LocalTime getOrario() {
		return orario;
	}
	
	/**
	 * Metodo che modifica l'orario dello stato
	 * 
	 * @param orario: nuovo orario dello stato
	 */
	public void setOrario(LocalTime orario) {
		this.orario = orario;
	}
	
	/**
	 * Metodo che ritorna il punteggio dello stato
	 * 
	 * @return il punteggio dello stato
	 */
	public int getPunteggio() {
		return punteggio;
	}
	
	/**
	 * Metodo che modifica il punteggio dello stato
	 * 
	 * @param punteggio: nuovo punteggio dello stato
	 */
	public void setPunteggio(int punteggio) {
		this.punteggio = punteggio;
	}
	
	@Override 
	public String toString() {
		String str = "";
		str +=    "Stato = [percorsoFormativo = " + this.getPercorsoFormativo().toString()
				+ ", giorno = " + this.getGiorno() + ", orario = " + this.getOrario() + "]";
		
		return str;
	}
	
	@Override
	public int compareTo(Stato stato2) {
		
		// array contenente i giorni della settimana
		ArrayList<String> giorniSettimana = new ArrayList<String>(
				Arrays.asList("lunedì","martedì","mercoledì","giovedì","venerdì")
		);
		
		//ottengo il giorno1 e il giorno2
		String giorno1 = this.getGiorno();
		String giorno2 = stato2.getGiorno();
		
		int indice1 = -1, indice2 = -1;
		
		// scorro l'array dei giorni della settimana per vedere se giorno1 e giorno 2 sono validi.
		for (int i = 0; i < giorniSettimana.size(); i++) {
			// se giorno1 è un giorno valido allora indice1 = 1
			if (giorniSettimana.get(i).equalsIgnoreCase(giorno1)) {
				indice1 = i;
			}
			// se giorno2 è un giorno valido allora indice2 = 2
			if (giorniSettimana.get(i).equalsIgnoreCase(giorno2)) {
				indice2 = i;
			}
			// se indice1 > -1 e indice2 > -1 allora entrambi i giorni sono validi ed esco dal ciclo for
			if (indice1 > -1 && indice2 > -1) {
				break;
			}
		}
		
		// controllo se giorno1 = giorno2:
		
		// se indice1 < 0 allora il giorno giorno1 non è valido e lancio un'eccezione
		if (indice1 < 0) {
			throw new IllegalStateException("giorno1 non è un giorno valido, valore riscontrato: " + giorno1);
		}
		
		// se indice2 < 0 allora il giorno giorno2 non è valido e lancio un'eccezione
		if (indice2 < 0) {
			throw new IllegalStateException("giorno2 non è un giorno valido, valore riscontrato: " + giorno2);
		}
		
		// se indice1 < indice2 allora significa che giorno1 è diverso da giorno2 e giorno1 viene prima di giorno2, 
		// quindi, stato1 è diverso da stato2. Ritorno -1
		if (indice1 < indice2) {
			return -1;
		}
		// se invece indice1 > indice2 allora significa che giorno1 è diverso da giorno2 e giorno1 viene dopo giorno2, 
		// quindi, stato1 è diverso da stato2. Ritorno +1;
		else if (indice1 > indice2) {
			return +1;
		}
		
		// arrivato a questo punto significa che giorno1 = giorno2
		
		// ottengo orario1 e orario2
		LocalTime orario1 = this.getOrario();
		LocalTime orario2 = stato2.getOrario();
		
		// controllo se orario1 = orario2:
		
		// se risultatoOrario < 0 allora orario1 viene prima di orario2
		// se risultatoOrario > 0 allora orario1 viene dopo orario2
		// se risultatoOrario = 0 allora orario1 = orario2
		int risultatoOrario = orario1.compareTo(orario2);
		
		// se risultatoOrario != 0 allora ritorno risultatoOrario poiché orario1 != orario2
		if(risultatoOrario != 0) {
			return risultatoOrario;
		}
		
		// ottengo il percorsoformativo1 e il percorsoFormativo2
		PercorsoFormativoEntity percorsoFormativo1 = this.getPercorsoFormativo();
		PercorsoFormativoEntity percorsoFormativo2 = stato2.getPercorsoFormativo();
		
		// se percorsoFormativo1 = percorsoFormativo2 allora stato1 = stato2 e ritorno 0
		if (Stato.equalsPercorsoFormativo(percorsoFormativo1, percorsoFormativo2)) {
			return 0;
		}
		
		// se percorsoFormativo1 != percorsoFormativo2
		if (percorsoFormativo1.getId() < percorsoFormativo2.getId()) {
			return -1;
		}
		else {
			return 1;
		}
	}
	
	/**
	 * Metodo che confronta 2 percorsi formativi e dice se sono uguali o diversi.
	 * 
	 * @param percorsoFormativo1: percorso formativo 1
	 * @param percorsoFormativo2: percorso formativo 2
	 * @return true se percorsoFormativo1 == percorsoformativo2, false se percorsoFormativo1 != percorsoFormativo2
	 */
	public static boolean equalsPercorsoFormativo 
			(PercorsoFormativoEntity percorsoFormativo1, PercorsoFormativoEntity percorsoFormativo2) {
		
		if (
				   (percorsoFormativo1.getId_formatore() == percorsoFormativo2.getId_formatore())
				&& (percorsoFormativo1.getNome().equalsIgnoreCase(percorsoFormativo2.getNome()))
				&& (percorsoFormativo1.getCategoria() == percorsoFormativo2.getCategoria())
				&& (percorsoFormativo1.getDescrizione().equalsIgnoreCase(percorsoFormativo2.getDescrizione()))
				&& (percorsoFormativo1.getIndice_contenuti().equalsIgnoreCase(percorsoFormativo2.getIndice_contenuti()))
				&& (percorsoFormativo1.getNum_lezioni() == percorsoFormativo2.getNum_lezioni())
				&& (percorsoFormativo1.getCosto() == percorsoFormativo2.getCosto())
		) {
			return true;
		}
		
		return false;
	}
}









