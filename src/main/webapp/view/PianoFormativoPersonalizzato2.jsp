<%@ 
	page language="java" 
	contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"
    
    import="pianoformativopersonalizzato.geneticalgorithm.Soluzione"
    import="pianoformativopersonalizzato.geneticalgorithm.Stato"
    import="model.entity.PercorsoFormativoEntity"
%>
<%

	Soluzione pianoFormativoPersonalizzato = (Soluzione) session.getAttribute("pianoFormativoPersonalizzato");
%>

<!DOCTYPE html>

<html>
	<head>
		<meta charset="ISO-8859-1">
		<title>Piano formativo personalizzato</title>
	</head>
	
	<body>
		<table border="1" id="pianoFormativoPersonalizzato">
			<thead>
				<tr>
					<th>ID</th>
					<th>Formatore</th>
					<th>Nome</th>
					<th>Categoria</th>
					<th>Descrizione</th>
					<th>Indice contenuti</th>
					<th>numero lezioni</th>
					<th>Costo</th>
					<th>Giorno</th>
					<th>Orario</th>
				</tr>
			</thead>
			<tbody>
<%
				for (int i = 0; i < pianoFormativoPersonalizzato.getSize(); i++) {
					Stato stato = pianoFormativoPersonalizzato.getStato(i);
%>
					<tr>
					    <td><%= stato.getPercorsoFormativo().getId() %></td>
				    	<td><%= stato.getPercorsoFormativo().getId_formatore() %></td>
				    	<td><%= stato.getPercorsoFormativo().getNome() %></td>
				    	<td><%= stato.getPercorsoFormativo().getCategoria() %></td>
				    	<td><%= stato.getPercorsoFormativo().getDescrizione() %></td>
				    	<td><%= stato.getPercorsoFormativo().getIndice_contenuti() %></td>
				    	<td><%= stato.getPercorsoFormativo().getNum_lezioni() %></td>
				    	<td><%= stato.getPercorsoFormativo().getCosto() %></td>
				    	<td><%= stato.getGiorno() %></td>
				    	<td><%= stato.getOrario() %></td>
					</tr>
<%
				}
%>
			</tbody>
		</table>
	</body>
</html>