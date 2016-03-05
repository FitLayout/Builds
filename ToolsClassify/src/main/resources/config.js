//common project-specific configuration

//rhino compatibility workaround, println should be replaced by print in all occurences
if (typeof println == 'undefined') this.println = print;

println("WELCOME!");

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
