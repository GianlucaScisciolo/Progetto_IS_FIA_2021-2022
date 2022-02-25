<%@ 
	page language="java" 
	contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"
    
    import="pianoformativopersonalizzato.service.Individuo"
    import="pianoformativopersonalizzato.service.Stato"
%>
<%
p

	Individuo pianoFormativoPersonalizzato = (Individuo) session.getAttribute("pianoFormativoPersonalizzato");
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
				for (int i = 0; i < pianoFormativoPersonalizzato.size(); i++) {
					Stato gene = pianoFormativoPersonalizzato.getGene(i);
%>
					<tr>
					    <td><%= gene.getPercorsoFormativo().getId() %></td>
				    	<td><%= gene.getPercorsoFormativo().getId_formatore() %></td>
				    	<td><%= gene.getPercorsoFormativo().getNome() %></td>
				    	<td><%= gene.getPercorsoFormativo().getCategoria() %></td>
				    	<td><%= gene.getPercorsoFormativo().getDescrizione() %></td>
				    	<td><%= gene.getPercorsoFormativo().getIndice_contenuti() %></td>
				    	<td><%= gene.getPercorsoFormativo().getNum_lezioni() %></td>
				    	<td><%= gene.getPercorsoFormativo().getCosto() %></td>
				    	<td><%= gene.getGiorno() %></td>
				    	<td><%= gene.getOrario() %></td>
					</tr>
<%
				}
%>
			</tbody>
		</table>
	</body>
</html>