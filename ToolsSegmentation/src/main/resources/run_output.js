println("RUN-OUTPUT");

var destdir = ".";

//CSSBox default redndering parametres
proc.setServiceParams("FitLayout.CSSBox", {
	width: 1200,
	height: 800,
	useVisualBounds: false,
	preserveAux: true,
	replaceImagesWithAlt: false
});

//Segmentation parametres
//see default_operators.js for the operators
proc.setServiceParams("FitLayout.Grouping", {
	preserveAuxAreas: false
});

function getBaseName(url)
{
	pathArray = url.split( '/' );
	host = pathArray[2];
	return host.replaceAll("[^a-zA-Z0-9\\-]+", "-");
}

function processPage(url)
{
	println("*** START " + url);
	
	//rendering
	var srcConfig = {
			width: 1200,
			height: 800,
			useVisualBounds: false,
			preserveAux: true,
			replaceImagesWithAlt: false
	};
	srcConfig.url = url;
	proc.renderPage('FitLayout.CSSBox', srcConfig);
	proc.drawToImage(destdir + "/"+getBaseName(url)+'-clean.png');

	//BCS and VIPS output
	var vp = proc.boxProviders.get('FitLayout.CSSBox').viewport;
	eval.saveReference(url, vp, destdir, getBaseName(url));
	
	println("... DONE");
}

processPage(app.inputUrl);
