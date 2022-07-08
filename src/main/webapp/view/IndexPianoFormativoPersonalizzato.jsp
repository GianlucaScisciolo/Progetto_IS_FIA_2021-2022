
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
	int idStudente = 3;
	StudenteDao studenteDao = new StudenteDao();
	StudenteEntity studente = (StudenteEntity) studenteDao.doRetrieveByKey(idStudente);
	session.setAttribute("studente", studente);
	
	PianoFormativoPersonalizzatoDao pianoFormativoPersonalizzatoDao = new PianoFormativoPersonalizzatoDao();
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

<!--  
		<form>
			<label>
				Inserisci il costo massimo che vuoi spendere (Facoltativo): 
				<input type="number" id="costoMax" name="costoMax" step="0.01"> 
				&euro;
			</label>
		</form>
-->		
		
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
		
		<button id="ottieniPercorsoFormativoButton" class="ottieniPercorsoFormativoButton">
	    	Ottieni un piano formativo personalizzato
		</button>
		
	</body>
</html>