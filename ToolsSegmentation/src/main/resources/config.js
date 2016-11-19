//common project-specific configuration

println("SEGM");

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

//common functions
function configureExample(pageSet, index)
{
	var pset = storage.getPageSet(pageSet);
	var page = pset.get(index);
	if (page != null)
	{
		println("Template page: " + page)
		var treeUris = storage.getAreaTreeURIs(page);
		println("Using template: " + treeUris[0]);
		var atree = storage.loadAreaTree(treeUris[0], page);
		var op = proc.operators.get('FitLayout.Segm.GroupByExample');
		op.setExampleTree(atree);
	}
	else
		println("Couldn't read the page");
}

