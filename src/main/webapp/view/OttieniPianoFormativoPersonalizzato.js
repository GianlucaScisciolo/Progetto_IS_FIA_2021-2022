$(document).ready(function() {
	$("#ottieniPercorsoFormativoButton").click(function() {
		var url = "http://localhost:8080/formAct_FIA/PianoFormativoPersonalizzatoServlet";
//		var costoMax = $("#costoMax").val();
		$.post(url,
		{
			action: "ottieniPianoFormativoPersonalizzato",
//			costoMax: costoMax
		},
		function() {
    		window.location.href = 'http://localhost:8080/formAct_FIA/view/PianoFormativoPersonalizzato.jsp';
    	})
    	.fail(function() {
    		alert("Fallimento");
    	});
		
	});
});