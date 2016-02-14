extr.clear();

var pset = storage.getPageSet('Training');
var pages = pset.iterator();
while (pages.hasNext())
{
	var page = pages.next();
	var treeUris = storage.getAreaTreeURIs(page);
	for (var i = 0; i < treeUris.length; i++)
	{
		println(treeUris[i]);
		var atree = storage.loadAreaTree(treeUris[i], page);
		extr.extractInstances(atree.root);
	}
}

extr.save('/tmp/train.arff');
