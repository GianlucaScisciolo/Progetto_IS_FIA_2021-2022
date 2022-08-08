package pianoformativopersonalizzato.control;

import java.text.ParseException;
import java.util.Map;

import javax.servlet.annotation.WebServlet;

import momentaneo.AbstractController;
import momentaneo.Service;
import pianoformativopersonalizzato.service.PianoFormativoPersonalizzatoService;

@WebServlet("/PianoFormativoPersonalizzatoServlet")
public class PianoFormativoPersonalizzatoServlet extends AbstractController {
	 
	@Override
	protected Map<String, Service> initServices() {
		java.util.Map<String, Service> m = new java.util.HashMap<String, Service>();
		
		try {
		    m.put("OTTIENIPIANOFORMATIVOPERSONALIZZATOSERVICE", new PianoFormativoPersonalizzatoService());
	    } 
		catch (ParseException e) {
		    e.printStackTrace();
		}
		
		return m;
	}
}










