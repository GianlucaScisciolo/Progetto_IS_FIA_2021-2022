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
import pianoformativopersonalizzato.geneticalgorithm.Individuo;
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
		HttpSession session = request.getSession(true);
	    response.setContentType("text.html");
	    PrintWriter out = response.getWriter();
	    
	    String action = request.getParameter("action");
	    
	    try {
	    	if (action != null) {
	    		if (action.equalsIgnoreCase("ottieniPianoFormativoPersonalizzato")) {
	    			PianoFormativoPersonalizzatoService pianoFormativoPersonalizzatoService = new PianoFormativoPersonalizzatoService();
	    			Individuo individuo = pianoFormativoPersonalizzatoService.ottieniPianoFormativoPersonalizzato(request);
	    			session.setAttribute("individuo", individuo);
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
