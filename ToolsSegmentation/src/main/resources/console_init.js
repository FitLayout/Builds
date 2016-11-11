//console initialization script

//println("Useful commands:");

//connect the storage immediately for testing
storage.connect("sesame:http://localhost:8080/openrdf-sesame/repositories/user");
//storage.connect("blazegraph:http://localhost:8080/blazegraph");


function processPage(url)
{
	println("");
	println("*** START " + url);
	
	//rendering
	var srcConfig = {
			width: 1200,
			height: 800,
			useVisualBounds: false,
			replaceImagesWithAlt: false
	};
	srcConfig.url = url;
	proc.renderPage('FitLayout.CSSBox', srcConfig);

	//BCS and VIPS output
	var vp = proc.boxProviders.get('FitLayout.CSSBox').viewport;
	eval.saveReference(url, vp);
	
	//segmentation
	proc.initAreaTree('FitLayout.Grouping', { preserveAuxAreas: false });
	proc.apply('FitLayout.Segm.FlattenTree', {});
	proc.apply('FitLayout.Segm.GroupByExample', {});
	proc.apply('FitLayout.Out.Groups', {});
	
	//save the result
	//saveCurrentPage();
	println("... DONE");
}

processPage('https://www.novinky.cz/zahranicni/amerika/419186-clintonove-hati-sanci-jeji-druha-dcera.html');
