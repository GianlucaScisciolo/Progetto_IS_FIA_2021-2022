$(document).ready(function() {
	$("#ottieniPercorsoFormativoButton").click(function() {
		
		$.post("http://localhost:8080/formAct_FIA/PianoFormativoPersonalizzatoServlet",
		{
			action: "ottieniPianoFormativoPersonalizzato"
		},
		function() {
			//alert("Successo");
    		// reindirizzo alla view con il piano formativo personalizzato
    	})
    	.fail(function() {
    		alert("Fallimento");
    	});
		
	});
});