//console initialization script

//println("Useful commands:");

//connect the storage immediately for testing
storage.connect("sesame:http://localhost:8080/openrdf-sesame/repositories/user");
//storage.connect("blazegraph:http://localhost:8080/blazegraph");

var DESTDIR = system.getProperty('user.home') + "/local/segm";


function getBaseName(url)
{
	pathArray = url.split( '/' );
	host = pathArray[2];
	return host.replaceAll("[^a-zA-Z0-9\\-]+", "-");
}

function processPage(url, destdir)
{
	println("");
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

	//BCS and VIPS output
	var vp = proc.boxProviders.get('FitLayout.CSSBox').viewport;
	eval.saveReference(url, vp, destdir, getBaseName(url));
	
	//segmentation
	proc.initAreaTree('FitLayout.Grouping', { preserveAuxAreas: false });
	proc.apply('FitLayout.Segm.FlattenTree', {});
	proc.apply('FitLayout.Segm.GroupByExample', {});
	proc.apply('FitLayout.Out.Groups', { filename: destdir+"/"+getBaseName(url)+'-groups.txt' });
	
	proc.drawToImageWithAreas(destdir+"/"+getBaseName(url)+'-groups.png', '<area');
	
	println("... DONE");
}

function processAllPages(listfile, destdir)
{
	var list = system.readLines(listfile);
	for (var i = 0; i < list.length; i++)
	{
		var dest = destdir + "/" + (i+1);
		system.mkdir(dest);
		processPage(list[i], dest)
	}
}

//configureExample('Segm', 6);
//var destdir = DESTDIR + "/bi2";
//processAllPages(destdir + "/urls", destdir);

//processPage('https://www.novinky.cz/zahranicni/amerika/419186-clintonove-hati-sanci-jeji-druha-dcera.html');
