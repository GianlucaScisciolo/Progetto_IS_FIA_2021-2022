<%@ page 
    language="java" 
	contentType="text/html; charset=ISO-8859-1"
	pageEncoding="UTF-8"
	
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
    <meta charset="UTF-8">
    
    <title>Piano formativo personalizzato</title>
    
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css">
  </head>
  
  <body>
    <!-- Visualizziamo gli interessi dello studente -->
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
    <!-- 
      Premendo sul bottone "Ottieni un piano formativo personalizzato
      visualizziamo un piano formativo personalizzato.
    -->
    <form action="${pageContext.request.contextPath}/PianoFormativoPersonalizzatoServlet/OttieniPianoFormativoPersonalizzatoService" method="POST">
      <input type="submit" class="btn btn-outline-info" value="Ottieni un piano formativo personalizzato">
      Tempo massimo impiegato: 7 secondi.
    </form>
</html>