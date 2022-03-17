package pianoformativopersonalizzato.geneticalgorithm;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import model.entity.PercorsoFormativoEntity;

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

	public PercorsoFormativoEntity getPercorsoFormativo() {
		return percorsoFormativo;
	}

	public void setPercorsoFormativo(PercorsoFormativoEntity percorsoFormativo) {
		this.percorsoFormativo = percorsoFormativo;
	}

	public String getGiorno() {
		return giorno;
	}

	public void setGiorno(String giorno) {
		this.giorno = giorno;
	}

	public LocalTime getOrario() {
		return orario;
	}

	public void setOrario(LocalTime orario) {
		this.orario = orario;
	}
	
	public int getPunteggio() {
		return punteggio;
	}
	
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
