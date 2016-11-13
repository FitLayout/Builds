//console initialization script

//println("Useful commands:");

//connect the storage immediately for testing
storage.connect("sesame:http://localhost:8080/openrdf-sesame/repositories/user");
//storage.connect("blazegraph:http://localhost:8080/blazegraph");

var DESTDIR = "/tmp";

function configureExample(pageSet, index)
{
	var pset = storage.getPageSet(pageSet);
	var pages = pset.iterator();
	var pi = 0;
	while (pages.hasNext())
	{
		var page = pages.next();
		if (pi == index)
		{
			println("Template page: " + page)
			var treeUris = storage.getAreaTreeURIs(page);
			println("Using template: " + treeUris[0]);
			var atree = storage.loadAreaTree(treeUris[0], page);
			var op = proc.operators.get('FitLayout.Segm.GroupByExample');
			op.setExampleTree(atree);
		}
		pi++;
	}
}

function getBaseName(url)
{
	pathArray = url.split( '/' );
	host = pathArray[2];
	return host.replaceAll("[^a-zA-Z0-9\\-]+", "-");
}

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
	eval.saveReference(url, vp, DESTDIR, getBaseName(url));
	
	//segmentation
	proc.initAreaTree('FitLayout.Grouping', { preserveAuxAreas: false });
	proc.apply('FitLayout.Segm.FlattenTree', {});
	proc.apply('FitLayout.Segm.GroupByExample', {});
	proc.apply('FitLayout.Out.Groups', { filename: DESTDIR+"/"+getBaseName(url)+'-groups.txt' });
	
	//save the result
	//saveCurrentPage();
	println("... DONE");
}

//configureExample('Segm', 0);
//processPage('https://www.novinky.cz/zahranicni/amerika/419186-clintonove-hati-sanci-jeji-druha-dcera.html');
