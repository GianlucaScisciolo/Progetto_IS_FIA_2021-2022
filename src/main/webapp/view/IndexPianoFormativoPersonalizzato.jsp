
<%@ 
	page language="java" 
	contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"
	
	import="java.util.ArrayList"
	
	import="model.dao.StudenteDao"
	import="modelfia.dao.PianoFormativoPersonalizzatoDao"
	
	import="model.entity.StudenteEntity"
	import="model.entity.InteresseEntity"
%>

<%
	// prendiamo come esempio lo studente con id = 3
	int idStudente = 3;
	StudenteDao studenteDao = new StudenteDao();
	// quindi, ci ricaviamo dal DB lo studente con id = 3
	StudenteEntity studente = (StudenteEntity) studenteDao.doRetrieveByKey(idStudente);
	session.setAttribute("studente", studente);
	
	PianoFormativoPersonalizzatoDao pianoFormativoPersonalizzatoDao = new PianoFormativoPersonalizzatoDao();
	// ci ricaviamo gli interessi dello studente
	ArrayList<String> interessi = pianoFormativoPersonalizzatoDao.doRetrieveInteressiStudente(idStudente);
%>

<!DOCTYPE html>

<html>
	<head>
		<meta charset="ISO-8859-1">
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
		<script src="OttieniPianoFormativoPersonalizzato.js"></script>
		<title>Index piano formativo personalizzato</title>
	</head>
	
	<body>
		
<%
		// Visualizziamo gli interessi dello studente
%>
		<h2>Interessi inseriti nel profilo</h2>
		<p>
<%
			for (String interesse : interessi) {
%>
				<%= interesse %><br>
<%				
			}
%>
			<br>
		</p>
		
<%
		// Premendo sul bottone "Ottieni un piano formativo personalizzato" 
		// visualizziamo un piano formativo personalizzato.
%>
		<button id="ottieniPercorsoFormativoButton" class="ottieniPercorsoFormativoButton">
	    	Ottieni un piano formativo personalizzato
		</button>
		
	</body>
</html>