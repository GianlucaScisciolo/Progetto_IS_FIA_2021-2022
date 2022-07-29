<%@ 
	page language="java" 
	contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"
    
    import="pianoformativopersonalizzato.geneticalgorithm.Soluzione"
    import="pianoformativopersonalizzato.geneticalgorithm.Stato"
    import="model.entity.PercorsoFormativoEntity"
%>

<%


	Soluzione soluzione = (Soluzione) session.getAttribute("soluzione");
%>

<!doctype html>

<html lang="en">
	<head>
    	<!-- Required meta tags -->
    	<meta charset="utf-8">
    	<meta name="viewport" content="width=device-width, initial-scale=1">
		
	    <!-- Bootstrap CSS -->
  		<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    	
	    <link href="TemplateStyle.css" rel="stylesheet">
    	<title>Visualizzazione Profilo</title>
  	</head>
  	
  	<body>
		
		<%@include file="/view/fragments/Header.jsp" %>
		
		
		<div class="page">
			<br><br>
			<div class="container">
				<h1>Piano formativo consigliato</h1>
				<span class="row border border-primary">
<%
					int i = 0;
					while(i < soluzione.getSize()) {
						if (i == 4) {
							break;
						}
						
						Stato stato = soluzione.getStato(i);
%>
						<span class="col-sm border border-primary">
				    		<%//= stato.getPercorsoFormativo().getId_formatore() %><br>
				    		Nome<br>
				    		Cognome<br><br>
				    		<%= stato.getPercorsoFormativo().getNome() %><br><br>
							Descrizione: <%= stato.getPercorsoFormativo().getDescrizione() %><br><br>
							giorno: <%= stato.getGiorno() %><br>
							Ore: <%= stato.getOrario() %><br><br>
 							<%= stato.getPercorsoFormativo().getNum_lezioni() %> lezioni<br>
							Costo: <%= stato.getPercorsoFormativo().getCosto() %> &#x20AC;<br><br>
							<button id="infoButton">Info</button>
							<button id="iscrivitiButton">Iscriviti</button>
						</span>
<%
						i++;
					}
%>
  				</span>
  				 				
  				<br><br>
  				
  				<h1>Percorsi formativi consigliati</h1>
  				<span class="row border border-primary">
<%
					int j = 0;
					while(i < soluzione.getSize()) {
						Stato stato = soluzione.getStato(i);
						if (j == 3) {
%>
							</span>
							<span class="row border border-primary">
<%
						}
%>
						<span class="col-sm border border-primary">
				    		<%//= gene.getPercorsoFormativo().getId_formatore() %><br>
				    		<%= stato.getPercorsoFormativo().getNome() %><br><br>
				    		Nome<br>
				    		Cognome<br><br>
				    		Costo: <%= stato.getPercorsoFormativo().getCosto() %> &#x20AC;<br>
						</span>
<%
						j++;
						i++;
					}
%>
  				</span>
				
				<br>	
				
			</div>
		</div>
		
		<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
		
 	</body>
</html>