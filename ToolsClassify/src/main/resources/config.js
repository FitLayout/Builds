//common project-specific configuration

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

//configure the Annotator plugin GUI
//the default tag type for the assigned tags
annotator.tagType = "FitLayout.Annotate";

//the tag list
annotator.tags = ["h1", "h2", "h3", "paragraph"];
