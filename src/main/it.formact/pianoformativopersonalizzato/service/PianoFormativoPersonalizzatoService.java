package pianoformativopersonalizzato.service;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.dao.IscrizioneDao;
import model.dao.PreferenzaStudenteDao;
import model.entity.IscrizioneEntity;
import model.entity.PreferenzaStudenteEntity;
import model.entity.StudenteEntity;
import modelfia.dao.PianoFormativoPersonalizzatoDao;
import momentaneo.Action;
import momentaneo.Service;
import pianoformativopersonalizzato.geneticalgorithm.PianoFormativoPersonalizzatoGA;
import pianoformativopersonalizzato.geneticalgorithm.PunteggioComparator;
import pianoformativopersonalizzato.geneticalgorithm.Soluzione;
import pianoformativopersonalizzato.geneticalgorithm.Stato;

public class PianoFormativoPersonalizzatoService implements Service {
	
	Action successPage = new Action("/formAct/view/PianoFormativoPersonalizzato.jsp", true, true);
	Action errorPage = new Action("/formAct/view/ErrorPage.jsp", true, true);
	
	public PianoFormativoPersonalizzatoService() throws ParseException {
		
	}
	
	@Override
	public Action process(String serviceName , HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
		if(serviceName.equalsIgnoreCase("OttieniPianoFormativoPersonalizzatoService")) {
			try {
				if(ottieniPercorsoFormativoPersonalizzato(req)) {
					// Richiesta effettuata con successo
					return successPage;
				}
				else {
					// Richiesta fallita
					return errorPage;
				}
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return errorPage;
	}
	
	@Override
	public Action getErrorAction() {
		return errorPage;
	}
	
	public boolean ottieniPercorsoFormativoPersonalizzato(HttpServletRequest req) throws SQLException {

		// ottengo lo studente della sessione
		StudenteEntity studente = (StudenteEntity) req.getSession().getAttribute("studente");
		
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
		
		// ottengo la soluzione della computazione.
		Soluzione soluzione = ga.getPianoFormativoPersonalizzato(spazioStati, giorniLiberi, interessi, categorie);
		
		// se |soluzione| < 1 allora ritorno una soluzione vuota
		if (soluzione.getSize() < 1) {
			req.getSession().setAttribute("soluzione", soluzione);
			return true;
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
		req.getSession().setAttribute("soluzione", soluzione);
		return true;
	}
	
}




//public class PianoFormativoPersonalizzatoService {
//	
//	public Soluzione ottieniPianoFormativoPersonalizzato (HttpServletRequest request) throws SQLException {
//		// Ottengo la sessione corrente associata alla request
//		HttpSession session = request.getSession(true);
//		
//		// ottengo lo studente della sessione
//		StudenteEntity studente = (StudenteEntity) session.getAttribute("studente");
//		
//		// ottengo l'id dello studente
//		int idStudente = studente.getId();
//		
//		PreferenzaStudenteDao preferenzaStudenteDao = new PreferenzaStudenteDao();
//		
//		// ottengo le preferenze dello studente
//		ArrayList<PreferenzaStudenteEntity> preferenze = 
//				(ArrayList<PreferenzaStudenteEntity>) preferenzaStudenteDao.doRetrieveByStudent(idStudente);
//		
//		PianoFormativoPersonalizzatoDao pianoFormativoPersonalizzatoDao = new PianoFormativoPersonalizzatoDao();		
//		
//		// ottengo i giorni liberi dello studente
//		ArrayList<String> giorniLiberi = pianoFormativoPersonalizzatoDao.doRetrieveGiorniLiberi(preferenze);
//		
//		IscrizioneDao iscrizioneDao = new IscrizioneDao();
//		
//		// ottengo le iscrizioni dello studente
//		ArrayList<IscrizioneEntity> iscrizioni = (ArrayList<IscrizioneEntity>) iscrizioneDao.doRetrieveByStudent(idStudente);
//		
//		// Ottengo lo spazio degli stati tramite i giorni liberi e le iscrizioni dello studente. 
//		// Ogni stato avrà un percorso formativo tale che:
//		// - è insegnato in un giorno libero dello studente;
//		// - non fa parte delle sue iscrizioni.
//		ArrayList<Stato> spazioStati = 
//				pianoFormativoPersonalizzatoDao.doRetrieveSpazioStati(giorniLiberi, iscrizioni);
//		
//		// Mi ricavo gli interessi dello studente
//		ArrayList<String> interessi = pianoFormativoPersonalizzatoDao.doRetrieveInteressiStudente(idStudente);
//		
//		// mi ricavo le categorie di percorsi formativi
//		Map<Integer, String> categorie = pianoFormativoPersonalizzatoDao.doRetrieveCategorie();
//		System.out.println(categorie.get(1).toString());
//		
//		PianoFormativoPersonalizzatoGA ga = new PianoFormativoPersonalizzatoGA();
//		
//		// ottengo la soluzione della computazione.
//		Soluzione soluzione = ga.getPianoFormativoPersonalizzato(spazioStati, giorniLiberi, interessi, categorie);
//		
//		// se |soluzione| < 1 allora ritorno una soluzione vuota
//		if (soluzione.getSize() < 1) {
//			return soluzione;
//		}
//				
//		// elimino gli stati della soluzione con i percorsi formativi duplicati 
//		soluzione.deletePercorsiDuplicatiByNome();
//		
//		// ordino la soluzione in base ai suoi punteggi
//		Collections.sort(soluzione.getStati(), new PunteggioComparator(-1));
//		
//		int numeroStatiDaOrdinare = 0;
//		// se 1 <= |soluzione| < 5 allora il numero dei primi stati da ordinare è uguale al |soluzione|
//		if (soluzione.getSize() >= 1 && soluzione.getSize() < 5) {
//			numeroStatiDaOrdinare = soluzione.getSize();
//		}
//		// se invece |soluzione| >= 5 allora il numero dei primi stati da ordinare è uguale a 4
//		else if (soluzione.getSize() >= 5){
//			numeroStatiDaOrdinare = 4;
//		}
//		
//		// ordino i primi numeroStatiDaOrdinare stati della soluzione in base al giorno e all'orario
//		soluzione.sortByGiornoAndOrario(numeroStatiDaOrdinare);
//		
//		// ritorno la soluzione
//		return soluzione;
//	}
//}
