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
import model.dao.PreferenzaStudenteDao;
import model.dao.StudenteDao;
import model.entity.CategoriaEntity;
import model.entity.PreferenzaStudenteEntity;
import model.entity.StudenteEntity;
import modelfia.dao.PianoFormativoPersonalizzatoDao;
import pianoformativopersonalizzato.geneticalgorithm.Soluzione;
import pianoformativopersonalizzato.geneticalgorithm.PianoFormativoPersonalizzatoGA;
import pianoformativopersonalizzato.geneticalgorithm.Stato;
import pianoformativopersonalizzato.service.PianoFormativoPersonalizzatoService;

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
		// Otteniamo la sessione corrente associata alla request
		HttpSession session = request.getSession(true);
		// Impostiamo il tipo di contenuto della risposta inviata al client
		response.setContentType("text.html");
	    
		// ottengo il tipo di azione
	    String action = request.getParameter("action");
	    
	    try {
	    	// se action != null
	    	if (action != null) {
	    		// se action = "ottieniPianoFormativoPersonalizzato"
	    		if (action.equalsIgnoreCase("ottieniPianoFormativoPersonalizzato")) {
	    			// intanzio un oggetto di tipo PianoFormativoPersonalizzatoService
	    			PianoFormativoPersonalizzatoService pianoFormativoPersonalizzatoService 
	    					= new PianoFormativoPersonalizzatoService();
	    			// ottengo la soluzione
	    			Soluzione soluzione = pianoFormativoPersonalizzatoService.ottieniPianoFormativoPersonalizzato(request);
	    			// setto l'attributo "soluzione" della sessione con il valore soluzione
	    			session.setAttribute("soluzione", soluzione);
	    			// stato = 200 (stato ok)
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
