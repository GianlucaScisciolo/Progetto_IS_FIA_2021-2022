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
import pianoformativopersonalizzato.geneticalgorithm.Individuo;
import pianoformativopersonalizzato.geneticalgorithm.PianoFormativoPersonalizzatoGA;
import pianoformativopersonalizzato.geneticalgorithm.PunteggioComparator;
import pianoformativopersonalizzato.geneticalgorithm.Stato;



public class PianoFormativoPersonalizzatoService {
	
	public Individuo ottieniPianoFormativoPersonalizzato (HttpServletRequest request) throws SQLException {
		HttpSession session = request.getSession(true);
		StudenteDao studenteDao = new StudenteDao();
		StudenteEntity studente = (StudenteEntity) session.getAttribute("studente");
		int idStudente = studente.getId();
		PreferenzaStudenteDao preferenzaStudenteDao = new PreferenzaStudenteDao();
		ArrayList<PreferenzaStudenteEntity> preferenze = (ArrayList<PreferenzaStudenteEntity>) preferenzaStudenteDao.doRetrieveByStudent(idStudente);
//		for (int i = 0; i < preferenze.size(); i++) {
//			System.out.println(preferenze.get(i).getDisponibilita());
//		}
		PianoFormativoPersonalizzatoDao pianoFormativoPersonalizzatoDao = new PianoFormativoPersonalizzatoDao();
		ArrayList<String> giorniLiberi = pianoFormativoPersonalizzatoDao.doRetrieveGiorniLiberi(preferenze);
		
		// Mi ricavo tutti i percorsi formativi con i giorni liberi in cui lo studente non è iscritto
		IscrizioneDao iscrizioneDao = new IscrizioneDao();
		ArrayList<IscrizioneEntity> iscrizioni = (ArrayList<IscrizioneEntity>) iscrizioneDao.doRetrieveByStudent(idStudente);
//		String costoMaxString = request.getParameter("costoMax");
//		double costoMax = -1.0;
//		if (! costoMaxString.equals("")) {
//			costoMax = Double.parseDouble(costoMaxString);
//		}
		ArrayList<Stato> spazioStati = pianoFormativoPersonalizzatoDao.doRetrieveSpazioStati(giorniLiberi, iscrizioni/*, costoMax*/);
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
			for (int i = 0; i < individuo.getCodifica().size(); i++)
			System.out.println("|||| " + individuo.getCodifica().get(i).getPunteggio() + " ||||");
			System.out.println();
			Collections.sort(individuo.getCodifica(), new PunteggioComparator(-1));
			for (int i = 0; i < individuo.getCodifica().size(); i++)
				System.out.println("|||| " + individuo.getCodifica().get(i).getPunteggio() + " ||||");
			if (individuo.size() < 4) {
				numeroGeniDaOrdinare = individuo.size();
			}
			individuo.sortByGiornoAndOrario(numeroGeniDaOrdinare);
		}
		System.out.println("||||||||||");
		for(int i = 0; i < spazioStati.size(); i++) {
			System.out.println(spazioStati.get(i).getPercorsoFormativo().getId());
		}
		
		return individuo;
	}
}
