package pianoformativopersonalizzato.service;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;

import model.entity.PercorsoFormativoEntity;

public class Stato implements Comparable<Stato> {
	private PercorsoFormativoEntity percorsoFormativo;
	private String giorno;
	private LocalTime orario;
	
	public Stato() {
		
	}
	
	public Stato (PercorsoFormativoEntity percorsoFormativo, String giorno, LocalTime orario) {
		this.percorsoFormativo = percorsoFormativo;
		this.giorno = giorno;
		this.orario = orario;
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

	@Override
	public int compareTo(Stato stato) {
		ArrayList<String> giorniSettimana = (ArrayList<String>) Arrays.asList(
			"lunedì", "martedì", "mercoledì", "giovedì", "venerdì"
		);
		
		int i = 0, j = 0, k = 0;
		for(String giornoSettimana : giorniSettimana) {
			String giornoThis = this.getGiorno();
			String giornoStato = stato.getGiorno();
			if (giornoThis.equalsIgnoreCase(giornoSettimana) == false) {
				i++;
				k++;
			}
			else {
				k++;				
			}
			if (giornoStato.equalsIgnoreCase(giornoSettimana) == false) {
				j++;
			}
			if (k == 2) {
				break;
			}
		}
		
		// TODO Auto-generated method stub
		return 0;
	}
	
	
}
