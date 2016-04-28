/*
	Creates a sample training page set
	Downloads and segments some sample pages and stores the model to the RDF storage.
	The training pages are stored in the 'Training' page set.
*/

var trainingUrls = [
   'http://www.reuters.com/article/us-usa-election-democrats-idUSMTZSAPEC2BDQPD6L',
   'http://www.reuters.com/article/space-gravitywaves-idUSKCN0VK1RT',
   'http://www.reuters.com/article/us-elonmusk-investors-insight-idUSKCN0VK2NL',
   'http://www.reuters.com/article/us-space-launch-mold-idUSKCN0VK014',
   //'http://www.reuters.com/article/us-tanzania-poaching-insight-idUSKCN0VJ0FZ',
   'http://www.reuters.com/article/us-usa-election-idUSKCN0XN12P'
];

var pageSetName = 'Training';

function processPage(url)
{
	println("");
	println("*** START " + url);
	
	//rendering
	var srcConfig = {
			width: 1200,
			height: 800,
			useVisualBounds: true
	};
	srcConfig.url = url;
	proc.renderPage('FitLayout.CSSBox', srcConfig);

	//segmentation
	proc.initAreaTree('FitLayout.Grouping', { preserveAuxAreas: false });
	//proc.apply('FitLayout.Segm.FindLines', {useConsistentStyle: false, maxLineEmSpace: 1.5});
	proc.apply('FitLayout.Segm.HomogeneousLeaves', {});
	//proc.apply('FitLayout.Segm.SuperAreas', {depthLimit: 2});
	//proc.apply('Ceur.Tag.Class', {});
	//proc.apply('FitLayout.Tag.Entities', {});
	
	//save the result
	saveCurrentPage();
	println("... DONE");
}

function saveCurrentPage()
{
	var savedPage = storage.insertPage(proc.page);
	storage.addPageToPageSet(savedPage, pageSetName);
	storage.saveAreaTree(proc.areaTree, null, savedPage);
}

//storage.connect("http://localhost:8080/openrdf-sesame/repositories/user");
if (storage.connected)
{
	var pset = storage.getPageSet(pageSetName);
	if (pset == null)
	{
		storage.createPageSet(pageSetName);
		for (var i = 0; i < trainingUrls.length; i++)
			processPage(trainingUrls[i]);
	}
	else
	{
		println("Training set " + pageSetName + " already exists; exiting.")
	}
}
else
	println("No storage is connected. Try storage.connect().");
