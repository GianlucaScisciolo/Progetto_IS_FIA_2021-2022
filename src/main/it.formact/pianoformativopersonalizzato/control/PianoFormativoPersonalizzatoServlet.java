package pianoformativopersonalizzato.control;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.dao.CategoriaDao;
import model.dao.PianoFormativoPersonalizzatoDao;
import model.dao.PreferenzaStudenteDao;
import model.dao.StudenteDao;
import model.entity.CategoriaEntity;
import model.entity.PreferenzaStudenteEntity;
import model.entity.StudenteEntity;
import pianoformativopersonalizzato.service.Individuo;
import pianoformativopersonalizzato.service.PianoFormativoPersonalizzatoGA;
import pianoformativopersonalizzato.service.Stato;

/**
 * Servlet implementation class PianoFormativoPersonalizzatoServlet
 */
@WebServlet("/PianoFormativoPersonalizzatoServlet")
public class PianoFormativoPersonalizzatoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PianoFormativoPersonalizzatoServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(true);
	    response.setContentType("text.html");
	    PrintWriter out = response.getWriter();
	    
	    String action = request.getParameter("action");
	    
	    try {
	    	if (action != null) {
	    		if (action.equalsIgnoreCase("ottieniPianoFormativoPersonalizzato")) {
	    			
	    			StudenteDao studenteDao = new StudenteDao();
	    			StudenteEntity studente = (StudenteEntity) studenteDao.doRetrieveByKey(1);
	    			int idStudente = studente.getId();
	    			
	    			PreferenzaStudenteDao prefStudenteDao = new PreferenzaStudenteDao();    			
	    			ArrayList<PreferenzaStudenteEntity> preferenze = (ArrayList<PreferenzaStudenteEntity>) prefStudenteDao.doRetrieveByStudent(idStudente);
	    			
	    			PianoFormativoPersonalizzatoDao pianoFormativoPersonalizzatoDao = new PianoFormativoPersonalizzatoDao();
	    			ArrayList<String> giorniLiberi = pianoFormativoPersonalizzatoDao.doRetrieveGiorniLiberi(preferenze);
//	    			System.out.println(giorniLiberi);
	    			
	    			// Mi ricavo tutti i percorsi formativi con i giorni liberi
	    			ArrayList<Stato> spazioStati = pianoFormativoPersonalizzatoDao.doRetrieveSpazioStati(giorniLiberi);
//	    			for (int i = 0; i < spazioStati.size(); i++) {
//	    				System.out.println(spazioStati.get(i));
//	    			}
	    			
	    			// Mi ricavo gli interessi di uno studente
	    			ArrayList<String> interessi = pianoFormativoPersonalizzatoDao.doRetrieveInteressiStudente(idStudente);
//	    			System.out.println(interessi);
	    			
	    			CategoriaDao categoriaDao = new CategoriaDao();
	    			ArrayList<CategoriaEntity> categorie = categoriaDao.doRetrieveAll();
//	    			for (CategoriaEntity categoria : categorie) {
//	    				System.out.println(categoria);
//	    			}
	    			Individuo individuo = null;
	    			
	    			System.out.println(spazioStati.size());
	    			
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
	    			
	    			session.setAttribute("individuo", individuo);
	    			/*
	    			 	Giorni liberi: [giovedì, lunedì]
					 	Interessi:     [Java, Python, HTML, CSS]
	    			 */
	    			
	    			
	    		    response.setStatus(200);
	    			
	    		}
	    	}
	    }
	    catch (SQLException e) {
	    	System.out.println("Error:" + e.getMessage());
	    }
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}
}
