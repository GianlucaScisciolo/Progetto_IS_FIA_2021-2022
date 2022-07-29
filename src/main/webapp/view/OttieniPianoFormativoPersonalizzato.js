$(document).ready(function() {
	$("#ottieniPercorsoFormativoButton").click(function() {
		var url = "http://localhost:8080/formAct_FIA/PianoFormativoPersonalizzatoServlet";
		$.post(url,
		{
			action: "ottieniPianoFormativoPersonalizzato",
		},
		function() {
    		window.location.href = 'http://localhost:8080/formAct_FIA/view/PianoFormativoPersonalizzato.jsp';
    	})
    	.fail(function() {
    		alert("Fallimento");
    	});
		
	});
});