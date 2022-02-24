package pianoformativopersonalizzato.service;

import java.time.LocalTime;

import model.entity.PercorsoFormativoEntity;

public class Stato {
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
	
	
}
