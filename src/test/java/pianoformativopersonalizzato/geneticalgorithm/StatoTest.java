package pianoformativopersonalizzato.geneticalgorithm;

import static org.junit.Assert.*;

import java.time.LocalTime;

import org.junit.Test;

import model.entity.IscrizioneEntity;
import model.entity.PercorsoFormativoEntity;
import pianoformativopersonalizzato.geneticalgorithm.Stato;

public class StatoTest {

	@Test
	public void testStatoCostruction() {
		Stato stato1 = new Stato();
		assertNotNull("L'oggetto stato1 non deve essere nullo", stato1);
		
		PercorsoFormativoEntity percorso = new PercorsoFormativoEntity();
		percorso.setCategoria(2);
		percorso.setCosto(10.5);
		percorso.setDescrizione("Corso base di matematica");
		percorso.setId(1);
		percorso.setId_formatore(3);
		percorso.setIndice_contenuti("- algebra; - geometria");
		percorso.setNome("Concetti base di matematica");
		percorso.setNum_lezioni(20);
		percorso.setValidate(4);
		
		String giorno = "Mercoledì";
		LocalTime orario = LocalTime.now();
		Stato stato2 = new Stato(percorso, giorno, orario);
		assertNotNull("L'oggetto stato2 non deve essere nullo", stato2);
		
		assertNotNull("La categoria dello stato2 non deve essere nullo", stato2);
		assertNotNull("Il costo dello stato2 non deve essere nullo", stato2);
		assertNotNull("La descrizione dello stato2 non deve essere nullo", stato2);
		assertNotNull("L'id dello stato2 non deve essere nullo", stato2);
		assertNotNull("L'id_formatore dello stato2 non deve essere nullo", stato2);
		assertNotNull("L'indice_contenuti dello stato2 non deve essere nullo", stato2);
		assertNotNull("Il nome dello stato2 non deve essere nullo", stato2);
		assertNotNull("Il num_lezioni dello stato2 non deve essere nullo", stato2);
		assertNotNull("Il validate dello stato2 non deve essere nullo", stato2);
	}
	
	@Test
	public void testGetSetPercorsoFormativo() {
		PercorsoFormativoEntity percorso = new PercorsoFormativoEntity();
		percorso.setCategoria(2);
		percorso.setCosto(10.5);
		percorso.setDescrizione("Corso base di matematica");
		percorso.setId(1);
		percorso.setId_formatore(3);
		percorso.setIndice_contenuti("- algebra; - geometria");
		percorso.setNome("Concetti base di matematica");
		percorso.setNum_lezioni(20);
		percorso.setValidate(4);
		
		Stato stato = new Stato();
		stato.setPercorsoFormativo(percorso);
		assertEquals(percorso, stato.getPercorsoFormativo());
	}
	
	@Test
	public void testGetSetGiorno() {
		Stato stato = new Stato();
		String giorno = "Giovedì";
		
		stato.setGiorno(giorno);
		assertEquals(giorno, stato.getGiorno());
	}
	
	@Test
	public void testGetSetOrario() {
		Stato stato = new Stato();
		LocalTime orario = LocalTime.parse("16:00");
		
		stato.setOrario(orario);
		assertEquals(orario, stato.getOrario());
	}
	
	@Test
	public void testCompareTo() {
		PercorsoFormativoEntity percorso = new PercorsoFormativoEntity();
		percorso.setCategoria(2);
		percorso.setCosto(10.5);
		percorso.setDescrizione("Corso base di matematica");
		percorso.setId(1);
		percorso.setId_formatore(3);
		percorso.setIndice_contenuti("- algebra; - geometria");
		percorso.setNome("Concetti base di matematica");
		percorso.setNum_lezioni(20);
		percorso.setValidate(4);
		
		String giorno1 = "Giovedì";
		String giorno2 = "Martedì";
		
		LocalTime orario1 = LocalTime.parse("16:00");
		LocalTime orario2 = LocalTime.parse("17:30");
		
		Stato stato1 = new Stato();
		stato1.setPercorsoFormativo(percorso);
		stato1.setGiorno(giorno1);
		stato1.setOrario(orario1);
		
		Stato stato2 = new Stato();
		stato2.setPercorsoFormativo(percorso);
		stato2.setGiorno(giorno1);
		stato2.setOrario(orario1);
		
		Stato stato3 = new Stato();
		stato3.setPercorsoFormativo(percorso);
		stato3.setGiorno(giorno2);
		stato3.setOrario(orario1);
		
		assertEquals(0, stato1.compareTo(stato2));
		assertEquals(1, stato1.compareTo(stato3));
		assertEquals(-1, stato3.compareTo(stato2));
		
		
		stato2.setOrario(orario2);
		
		assertEquals(1, stato2.compareTo(stato1));
		assertEquals(-1, stato1.compareTo(stato2));
		
		assertEquals(0, stato1.compareTo(stato1));
		assertEquals(0, stato2.compareTo(stato2));
		assertEquals(0, stato3.compareTo(stato3));
		
	}
	
}
