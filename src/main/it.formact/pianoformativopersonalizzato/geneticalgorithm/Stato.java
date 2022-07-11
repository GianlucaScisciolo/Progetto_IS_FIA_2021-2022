package pianoformativopersonalizzato.geneticalgorithm;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
//import java.util.List;

import model.entity.PercorsoFormativoEntity;

/**
 * 
 * @author GIANLUCA
 *
 */
public class Stato implements Comparable<Stato> {
	private PercorsoFormativoEntity percorsoFormativo;
	private String giorno;
	private LocalTime orario;
	private int punteggio;
	
	public Stato() {
		
	}
	
	public Stato (PercorsoFormativoEntity percorsoFormativo, String giorno, LocalTime orario) {
		this.percorsoFormativo = percorsoFormativo;
		this.giorno = giorno;
		this.orario = orario;
		this.punteggio = 0;
	}
	
	/**
	 * Questo metodo ritorna il percorso formativo dello stato.
	 * @return il percorso formativo dello stato
	 */
	public PercorsoFormativoEntity getPercorsoFormativo() {
		return percorsoFormativo;
	}
	
	/**
	 * Questo metodo modifica il percorso formativo dello stato.
	 * @param percorsoFormativo: nuovo percorso formativo
	 */
	public void setPercorsoFormativo(PercorsoFormativoEntity percorsoFormativo) {
		this.percorsoFormativo = percorsoFormativo;
	}
	
	/**
	 * Questo metodo ritorna il giorno dello stato.
	 * @return il giorno dello stato
	 */
	public String getGiorno() {
		return giorno;
	}
	
	/**
	 * Questo metodo modifica il giorno dello stato.
	 * @param giorno: giorno dello stato
	 */
	public void setGiorno(String giorno) {
		this.giorno = giorno;
	}
	
	/**
	 * Questo metodo ritorna l'orario dello stato.
	 * @return l'orario dello stato
	 */
	public LocalTime getOrario() {
		return orario;
	}
	
	/**
	 * Questo metodo modifica l'orario dello stato.
	 * @param orario: orario dello stato
	 */
	public void setOrario(LocalTime orario) {
		this.orario = orario;
	}
	
	/**
	 * Questo metodo ritorna il punteggio dello stato.
	 * @return il punteggio dello stato
	 */
	public int getPunteggio() {
		return punteggio;
	}
	
	/**
	 * Questo metodo modifica il punteggio dello stato.
	 * @param punteggio: punteggio dello stato
	 */
	public void setPunteggio(int punteggio) {
		this.punteggio = punteggio;
	}
	
	@Override
	public String toString() {
		String str = "";
		str +=    "Stato = [percorsoormativo = " + this.getPercorsoFormativo()
				+ ", giorno = " + this.getGiorno() + ", orario = " + this.getOrario() + "]";
		
		return str;
	}
	
	@Override
	public int compareTo(Stato stato) {
		ArrayList<String> giorniSettimana = new ArrayList<String>(
			Arrays.asList("lunedì","martedì","mercoledì","giovedì","venerdì")
		);
		
		String giornoThis = this.getGiorno();
		LocalTime orarioThis = this.getOrario();
		
		String giornoStato = stato.getGiorno();
		LocalTime orarioStato = stato.getOrario();
		
		int indiceThis = -1, indiceStato = -1;
		for (int i = 0; i < giorniSettimana.size(); i++) {
			if (giorniSettimana.get(i).equalsIgnoreCase(giornoThis)) {
				indiceThis = i;
			}
			if (giorniSettimana.get(i).equalsIgnoreCase(giornoStato)) {
				indiceStato = i;
			}
			if (indiceThis > -1 && indiceStato > -1) {
				break;
			}
		}
		
		if (indiceThis < 0) {
			throw new IllegalStateException("Giorno this non valido, valore riscontrato: " + giornoThis);
		}
		
		if (indiceStato < 0) {
			throw new IllegalStateException("Giorno stato non valido, valore riscontrato: " + giornoStato);
		}
		
		if (indiceThis < indiceStato) {
			return -1;
		}
		else if (indiceThis > indiceStato) {
			return +1;
		}
		else {
			return orarioThis.compareTo(orarioStato);
		}
	}
	
}
