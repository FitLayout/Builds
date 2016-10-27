//common project-specific configuration

println("SEGM");

//CSSBox default redndering parametres
proc.setServiceParams("FitLayout.CSSBox", {
	width: 1200,
	height: 800,
	useVisualBounds: false
});

//Segmentation parametres
//see default_operators.js for the operators
proc.setServiceParams("FitLayout.Grouping", {
	preserveAuxAreas: true
});
