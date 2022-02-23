package pianoformativopersonalizzato.control;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
	    
	    //try {
	    	if (action != null) {
	    		if (action.equalsIgnoreCase("ottieniPianoFormativoPersonalizzato")) {
	    			
	    			
	    			
	    			response.setStatus(200);
	    			
	    		}
	    	}
	    //}
	    //catch (SQLException e) {
	    //	System.out.println("Error:" + e.getMessage());
	    //}
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

}
