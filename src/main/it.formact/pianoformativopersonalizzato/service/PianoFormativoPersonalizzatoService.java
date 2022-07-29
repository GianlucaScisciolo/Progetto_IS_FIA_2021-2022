package pianoformativopersonalizzato.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import model.dao.CategoriaDao;
import model.dao.InteresseStudenteDao;
import model.dao.IscrizioneDao;
import model.dao.PreferenzaStudenteDao;
import model.dao.StudenteDao;
import model.entity.CategoriaEntity;
import model.entity.InteresseStudenteEntity;
import model.entity.IscrizioneEntity;
import model.entity.PreferenzaStudenteEntity;
import model.entity.StudenteEntity;
import modelfia.dao.PianoFormativoPersonalizzatoDao;
import pianoformativopersonalizzato.geneticalgorithm.Soluzione;
import pianoformativopersonalizzato.geneticalgorithm.PianoFormativoPersonalizzatoGA;
import pianoformativopersonalizzato.geneticalgorithm.PunteggioComparator;
import pianoformativopersonalizzato.geneticalgorithm.Stato;



public class PianoFormativoPersonalizzatoService {
	
	public Soluzione ottieniPianoFormativoPersonalizzato (HttpServletRequest request) throws SQLException {
		// Ottengo la sessione corrente associata alla request
		HttpSession session = request.getSession(true);
		
		// ottengo lo studente della sessione
		StudenteEntity studente = (StudenteEntity) session.getAttribute("studente");
		
		// ottengo l'id dello studente
		int idStudente = studente.getId();
		
		PreferenzaStudenteDao preferenzaStudenteDao = new PreferenzaStudenteDao();
		
		// ottengo le preferenze dello studente
		ArrayList<PreferenzaStudenteEntity> preferenze = 
				(ArrayList<PreferenzaStudenteEntity>) preferenzaStudenteDao.doRetrieveByStudent(idStudente);
		
		PianoFormativoPersonalizzatoDao pianoFormativoPersonalizzatoDao = new PianoFormativoPersonalizzatoDao();		
		
		// ottengo i giorni liberi dello studente
		ArrayList<String> giorniLiberi = pianoFormativoPersonalizzatoDao.doRetrieveGiorniLiberi(preferenze);
		
		IscrizioneDao iscrizioneDao = new IscrizioneDao();
		
		// ottengo le iscrizioni dello studente
		ArrayList<IscrizioneEntity> iscrizioni = (ArrayList<IscrizioneEntity>) iscrizioneDao.doRetrieveByStudent(idStudente);
		
		// Ottengo lo spazio degli stati tramite i giorni liberi e le iscrizioni dello studente. 
		// Ogni stato avrà un percorso formativo tale che:
		// - è insegnato in un giorno libero dello studente;
		// - non fa parte delle sue iscrizioni.
		ArrayList<Stato> spazioStati = 
				pianoFormativoPersonalizzatoDao.doRetrieveSpazioStati(giorniLiberi, iscrizioni);
		
		// Mi ricavo gli interessi dello studente
		ArrayList<String> interessi = pianoFormativoPersonalizzatoDao.doRetrieveInteressiStudente(idStudente);
		
		// mi ricavo le categorie di percorsi formativi
		Map<Integer, String> categorie = pianoFormativoPersonalizzatoDao.doRetrieveCategorie();
		System.out.println(categorie.get(1).toString());
		
		PianoFormativoPersonalizzatoGA ga = new PianoFormativoPersonalizzatoGA();
		
		// ottengo la soluzione migliore della computazione.
		Soluzione soluzione = ga.getPianoFormativoPersonalizzato(spazioStati, giorniLiberi, interessi, categorie);
		
		// se |soluzione| < 1 allora ritorno una soluzione vuota
		if (soluzione.getSize() < 1) {
			return soluzione;
		}
				
		// elimino gli stati della soluzione con i percorsi formativi duplicati 
		soluzione.deletePercorsiDuplicatiByNome();
		
		// ordino la soluzione in base ai suoi punteggi
		Collections.sort(soluzione.getStati(), new PunteggioComparator(-1));
		
		int numeroStatiDaOrdinare = 0;
		// se 1 <= |soluzione| < 5 allora il numero dei primi stati da ordinare è uguale al |soluzione|
		if (soluzione.getSize() >= 1 && soluzione.getSize() < 5) {
			numeroStatiDaOrdinare = soluzione.getSize();
		}
		// se invece |soluzione| >= 5 allora il numero dei primi stati da ordinare è uguale a 4
		else if (soluzione.getSize() >= 5){
			numeroStatiDaOrdinare = 4;
		}
		
		// ordino i primi numeroStatiDaOrdinare stati della soluzione in base al giorno e all'orario
		soluzione.sortByGiornoAndOrario(numeroStatiDaOrdinare);
		
		// ritorno la soluzione
		return soluzione;
	}
}
