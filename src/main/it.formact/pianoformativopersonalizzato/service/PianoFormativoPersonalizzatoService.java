package pianoformativopersonalizzato.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

import model.dao.CategoriaDao;
import model.dao.PreferenzaStudenteDao;
import model.dao.StudenteDao;
import model.entity.CategoriaEntity;
import model.entity.PreferenzaStudenteEntity;
import model.entity.StudenteEntity;
import modelfia.dao.PianoFormativoPersonalizzatoDao;
import pianoformativopersonalizzato.geneticalgorithm.Individuo;
import pianoformativopersonalizzato.geneticalgorithm.PianoFormativoPersonalizzatoGA;
import pianoformativopersonalizzato.geneticalgorithm.Stato;



public class PianoFormativoPersonalizzatoService {
	
	public Individuo ottieniPianoFormativoPersonalizzato (int idStudente) throws SQLException {
		StudenteDao studenteDao = new StudenteDao();
		StudenteEntity studente = (StudenteEntity) studenteDao.doRetrieveByKey(idStudente);
		PreferenzaStudenteDao preferenzaStudenteDao = new PreferenzaStudenteDao();
		ArrayList<PreferenzaStudenteEntity> preferenze = (ArrayList<PreferenzaStudenteEntity>) preferenzaStudenteDao.doRetrieveByStudent(idStudente);
//		for (int i = 0; i < preferenze.size(); i++) {
//			System.out.println(preferenze.get(i).getDisponibilita());
//		}
		PianoFormativoPersonalizzatoDao pianoFormativoPersonalizzatoDao = new PianoFormativoPersonalizzatoDao();
		ArrayList<String> giorniLiberi = pianoFormativoPersonalizzatoDao.doRetrieveGiorniLiberi(preferenze);
		
		// Mi ricavo tutti i percorsi formativi con i giorni liberi
		ArrayList<Stato> spazioStati = pianoFormativoPersonalizzatoDao.doRetrieveSpazioStati(giorniLiberi);
//		for(int i = 0; i < spazioStati.size(); i++) {
//			System.out.println(spazioStati.get(i).getPercorsoFormativo().getCosto());
//		}
		// Mi ricavo gli interessi di uno studente
		ArrayList<String> interessi = pianoFormativoPersonalizzatoDao.doRetrieveInteressiStudente(idStudente);
		
		Map<Integer, String> categorie = pianoFormativoPersonalizzatoDao.doRetrieveCategorie();
		Individuo individuo = null;
		int n = spazioStati.size();
		if (n <= 0) {
			individuo = new Individuo();
		}
		else if (n > 0 && n < 11) {
			individuo = new Individuo(spazioStati);
		}
		else {
			// Eseguo l'algoritmo;
			PianoFormativoPersonalizzatoGA ga = new PianoFormativoPersonalizzatoGA();
			//pianoFormativoPersonalizzato = new Individuo(spazioStati); 
			individuo = ga.getPianoFormativoPersonalizzato(spazioStati, giorniLiberi, interessi, categorie);
		}
		
		int numeroGeniDaOrdinare = 4;
		if (individuo.size() > 0) {
			individuo.deletePercorsiDuplicatiByNome();
			if (individuo.size() < 4) {
				numeroGeniDaOrdinare = individuo.size();
			}
			individuo.sortByGiornoAndOrario(numeroGeniDaOrdinare);
		}
		return individuo;
	}
}
